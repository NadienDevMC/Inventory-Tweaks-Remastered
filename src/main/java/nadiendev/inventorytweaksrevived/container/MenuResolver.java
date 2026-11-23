package nadiendev.inventorytweaksrevived.container;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import nadiendev.inventorytweaksrevived.InventoryTweaksRevived;
import nadiendev.inventorytweaksrevived.api.container.ChestContainer;
import nadiendev.inventorytweaksrevived.api.container.ContainerSection;
import nadiendev.inventorytweaksrevived.api.container.ContainerSectionCallback;
import nadiendev.inventorytweaksrevived.api.container.IgnoreContainer;
import nadiendev.inventorytweaksrevived.api.container.InventoryContainer;
import nadiendev.inventorytweaksrevived.util.InvTweaksConst;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Tells what Inventory Tweaks can do with a menu. Replaces the 1.12 coremod, which injected this information into every
 * container class.
 * <p>
 * For each class of the menu's hierarchy, from the menu's own class up, the first match wins: (1) the
 * {@link IgnoreContainer}, {@link ChestContainer} and {@link InventoryContainer} annotations declared on that class,
 * (2) an entry for that class in {@code menu_compat.json}, (3) the vanilla table in {@link VanillaSlotMaps}; so an entry
 * for a base class also covers its subclasses. A menu with no match is unknown: no buttons, no chest, and its player
 * inventory is sortable if it holds all of it. Whatever the rule says, a section that is missing from the actual menu
 * cannot be sorted.
 * <p>
 * The rule is cached per menu class; the result itself is cached for the last menu instance only, since slot maps hold
 * that instance's slots (and some layouts, like the number of chest rows, depend on the instance).
 */
public final class MenuResolver {
    private static final String COMPAT_FILE_NAME = "menu_compat.json";
    private static final String BUNDLED_COMPAT_FILE = "/assets/" + InventoryTweaksRevived.MOD_ID + "/" + COMPAT_FILE_NAME;
    /**
     * {@code "rowSize": "auto"} in {@code menu_compat.json}: the columns are counted on the screen.
     */
    private static final int AUTO_ROW_SIZE = -1;
    private static final Rule UNKNOWN = Rule.cached(MenuResolver::unknown);
    private static final Map<Class<?>, Rule> RULES = new ConcurrentHashMap<>();
    private static final Set<Class<?>> WARNED = ConcurrentHashMap.newKeySet();
    @Nullable
    private static Map<String, CompatEntry> compatEntries;

    private static WeakReference<AbstractContainerMenu> lastMenu = new WeakReference<>(null);
    private static int lastSlotCount = -1;
    @Nullable
    private static MenuInfo lastInfo;

    private MenuResolver() {
    }

    /**
     * Never throws: anything that goes wrong while inspecting a menu is logged once per menu class and the menu is
     * treated as unknown.
     */
    @NotNull
    public static MenuInfo resolve(@Nullable AbstractContainerMenu menu) {
        if(menu == null) {
            return MenuInfo.NONE;
        }
        try {
            if(lastInfo != null && lastMenu.get() == menu && lastSlotCount == menu.slots.size()) {
                return lastInfo;
            }

            Rule rule = RULES.computeIfAbsent(menu.getClass(), MenuResolver::findRule);
            MenuInfo info = rule.resolver().apply(menu);
            if(rule.cacheable()) {
                lastMenu = new WeakReference<>(menu);
                lastSlotCount = menu.slots.size();
                lastInfo = info;
            } else {
                lastInfo = null;
            }
            return info;
        } catch(Exception | LinkageError e) {
            warnOnce(menu.getClass(), "Could not inspect menu {}, treating it as unknown", e);
            try {
                return unknown(menu);
            } catch(Exception | LinkageError e2) {
                return MenuInfo.NONE;
            }
        }
    }

    /**
     * What a menu nothing is known about gets: no buttons, no chest, sortable inventory only if all of it is there.
     */
    @NotNull
    static MenuInfo unknown(@NotNull AbstractContainerMenu menu) {
        Map<ContainerSection, List<Slot>> slotRefs = VanillaSlotMaps.unknownMenuSlots(menu);
        return new MenuInfo(false, slotRefs.containsKey(ContainerSection.INVENTORY), false, false,
                InvTweaksConst.INVENTORY_ROW_SIZE, slotRefs);
    }

    @NotNull
    private static Rule findRule(@NotNull Class<?> menuClass) {
        try {
            for(Class<?> c = menuClass; c != null && c != AbstractContainerMenu.class; c = c.getSuperclass()) {
                Rule rule = annotationRule(c);
                if(rule == null) {
                    rule = compatRule(c);
                }
                if(rule == null) {
                    rule = VanillaSlotMaps.ruleFor(c);
                }
                if(rule != null) {
                    if(c != menuClass) {
                        InventoryTweaksRevived.LOGGER.debug("Menu {} uses the rule of its superclass {}", menuClass.getName(), c.getName());
                    }
                    return rule;
                }
            }
        } catch(Exception | LinkageError e) {
            warnOnce(menuClass, "Could not read the Inventory Tweaks information of menu {}, treating it as unknown", e);
        }
        return UNKNOWN;
    }

    // Annotations (api.container)

    @Nullable
    private static Rule annotationRule(@NotNull Class<?> c) {
        if(c.getDeclaredAnnotation(IgnoreContainer.class) != null) {
            return Rule.cached(menu -> new MenuInfo(false, false, false, false, InvTweaksConst.INVENTORY_ROW_SIZE,
                    VanillaSlotMaps.unknownMenuSlots(menu)));
        }

        ChestContainer chest = c.getDeclaredAnnotation(ChestContainer.class);
        if(chest != null) {
            Method rowSizeCallback = findAnnotatedMethod(c, ChestContainer.RowSizeCallback.class);
            Method largeCallback = findAnnotatedMethod(c, ChestContainer.IsLargeCallback.class);
            Method slotsCallback = findAnnotatedMethod(c, ContainerSectionCallback.class);
            Function<AbstractContainerMenu, MenuInfo> resolver = menu -> {
                int rowSize = rowSizeCallback != null ? call(rowSizeCallback, menu, Integer.class, chest.rowSize()) : chest.rowSize();
                boolean large = largeCallback != null ? call(largeCallback, menu, Boolean.class, chest.isLargeChest()) : chest.isLargeChest();
                Map<ContainerSection, List<Slot>> callbackSlots = callbackSlotMap(slotsCallback, menu);
                Map<ContainerSection, List<Slot>> slotRefs = callbackSlots != null ? callbackSlots : VanillaSlotMaps.unknownMenuSlots(menu);
                return new MenuInfo(chest.showButtons(), false, isChestSortable(slotRefs, callbackSlots != null), large,
                        Math.max(1, rowSize), slotRefs);
            };
            // Callbacks may answer differently over the life of a menu, so they are asked every time (as in 1.12)
            boolean hasCallbacks = rowSizeCallback != null || largeCallback != null || slotsCallback != null;
            return hasCallbacks ? Rule.uncached(resolver) : Rule.cached(resolver);
        }

        InventoryContainer inventory = c.getDeclaredAnnotation(InventoryContainer.class);
        if(inventory != null) {
            Method slotsCallback = findAnnotatedMethod(c, ContainerSectionCallback.class);
            Function<AbstractContainerMenu, MenuInfo> resolver = menu -> {
                Map<ContainerSection, List<Slot>> callbackSlots = callbackSlotMap(slotsCallback, menu);
                Map<ContainerSection, List<Slot>> slotRefs = callbackSlots != null ? callbackSlots : VanillaSlotMaps.unknownMenuSlots(menu);
                return new MenuInfo(inventory.showOptions(), slotRefs.containsKey(ContainerSection.INVENTORY), false,
                        false, InvTweaksConst.INVENTORY_ROW_SIZE, slotRefs);
            };
            return slotsCallback != null ? Rule.uncached(resolver) : Rule.cached(resolver);
        }

        return null;
    }

    @Nullable
    private static Method findAnnotatedMethod(@NotNull Class<?> c, @NotNull Class<? extends Annotation> annotation) {
        for(Method method : c.getDeclaredMethods()) {
            if(method.isAnnotationPresent(annotation)) {
                method.setAccessible(true);
                return method;
            }
        }
        return null;
    }

    /**
     * Calls a callback method of a menu: an instance method without parameters, or a static method taking either nothing
     * or the menu. Falls back to {@code fallback} if it fails or answers something else than {@code type}.
     */
    private static <T> T call(@NotNull Method method, @NotNull AbstractContainerMenu menu, @NotNull Class<T> type, T fallback) {
        try {
            boolean isStatic = Modifier.isStatic(method.getModifiers());
            Object result;
            if(method.getParameterCount() == 0) {
                result = method.invoke(isStatic ? null : menu);
            } else if(isStatic && method.getParameterCount() == 1) {
                result = method.invoke(null, menu);
            } else {
                warnOnce(menu.getClass(), "Callback " + method.getName() + " of menu {} has an unsupported signature", null);
                return fallback;
            }
            if(type.isInstance(result)) {
                return type.cast(result);
            }
            warnOnce(menu.getClass(), "Callback " + method.getName() + " of menu {} returned " + result, null);
        } catch(ReflectiveOperationException | RuntimeException e) {
            warnOnce(menu.getClass(), "Callback " + method.getName() + " of menu {} failed", e);
        }
        return fallback;
    }

    /**
     * @return the slots answered by the {@link ContainerSectionCallback} method, or null if there is no callback or it
     * failed
     */
    @Nullable
    private static Map<ContainerSection, List<Slot>> callbackSlotMap(@Nullable Method callback, @NotNull AbstractContainerMenu menu) {
        if(callback == null) {
            return null;
        }
        Map<?, ?> result = call(callback, menu, Map.class, null);
        if(result == null) {
            return null;
        }
        Map<ContainerSection, List<Slot>> slotRefs = new EnumMap<>(ContainerSection.class);
        for(Map.Entry<?, ?> entry : result.entrySet()) {
            if(entry.getKey() instanceof ContainerSection section && entry.getValue() instanceof List<?> list) {
                List<Slot> slots = new ArrayList<>(list.size());
                for(Object slot : list) {
                    if(slot instanceof Slot s) {
                        slots.add(s);
                    }
                }
                if(!slots.isEmpty()) {
                    slotRefs.put(section, Collections.unmodifiableList(slots));
                }
            }
        }
        return Collections.unmodifiableMap(slotRefs);
    }

    /**
     * Whether the chest section of a slot map can be sorted. A chest made of every slot that is not the player's
     * {@link Inventory} (the layout of an unknown menu) is only trusted next to the player's inventory: without it, the
     * player's slots are kept in another container and would be sorted together with the chest.
     *
     * @param explicitChest whether the chest slots were told by the menu instead of guessed
     */
    static boolean isChestSortable(@NotNull Map<ContainerSection, List<Slot>> slotRefs, boolean explicitChest) {
        return slotRefs.containsKey(ContainerSection.CHEST) && (explicitChest || slotRefs.containsKey(ContainerSection.INVENTORY));
    }

    // menu_compat.json

    @Nullable
    private static Rule compatRule(@NotNull Class<?> c) {
        Map<String, CompatEntry> entries = getCompatEntries();
        CompatEntry entry = entries.get(c.getName());
        if(entry == null && c.getCanonicalName() != null) {
            entry = entries.get(c.getCanonicalName());
        }
        if(entry == null) {
            return null;
        }
        CompatEntry e = entry;
        Method chestSlotsMethod = e.chestSlotsMethod() != null ? findChestSlotsMethod(c, e.chestSlotsMethod()) : null;
        return Rule.cached(menu -> compatInfo(e, chestSlotsMethod, menu));
    }

    @Nullable
    private static Method findChestSlotsMethod(@NotNull Class<?> c, @NotNull String name) {
        try {
            return c.getMethod(name);
        } catch(NoSuchMethodException | SecurityException e) {
            warnOnce(c, "menu_compat.json: menu {} has no public method " + name + "() for its chest slots, guessing them", null);
            return null;
        }
    }

    @NotNull
    private static MenuInfo compatInfo(@NotNull CompatEntry e, @Nullable Method chestSlotsMethod, @NotNull AbstractContainerMenu menu) {
        Map<ContainerSection, List<Slot>> slotRefs = new EnumMap<>(ContainerSection.class);
        slotRefs.putAll(VanillaSlotMaps.unknownMenuSlots(menu));

        List<Slot> chestSlots = chestSlotsMethod != null ? callChestSlotsMethod(chestSlotsMethod, menu) : null;
        boolean explicitChest = chestSlots != null;
        if(explicitChest) {
            slotRefs.remove(ContainerSection.CHEST);
            if(!chestSlots.isEmpty()) {
                slotRefs.put(ContainerSection.CHEST, chestSlots);
            }
        }
        if(e.inventoryButtons()) {
            // Only the player's inventory is ever touched: the rest of the menu is not a section at all
            slotRefs.remove(ContainerSection.CHEST);
        }

        boolean validInventory = (e.validInventory() || e.inventoryButtons()) && slotRefs.containsKey(ContainerSection.INVENTORY);
        boolean validChest = e.validChest() && isChestSortable(slotRefs, explicitChest);
        int rowSize = e.rowSize() == AUTO_ROW_SIZE ? rowSizeOf(slotRefs.get(ContainerSection.CHEST)) : e.rowSize();
        return new MenuInfo(e.showButtons(), validInventory, validChest, e.largeChest(), rowSize,
                e.inventoryButtons() && validInventory, Collections.unmodifiableMap(slotRefs));
    }

    /**
     * Calls the method of a menu that tells its chest slots: either the number of slots at the start of the menu that
     * form the chest, or a collection of the chest's slots. Slots of the player's {@link Inventory} are never part of
     * the chest.
     *
     * @return the chest slots, or null if the method failed (the chest is then guessed)
     */
    @Nullable
    private static List<Slot> callChestSlotsMethod(@NotNull Method method, @NotNull AbstractContainerMenu menu) {
        Object result;
        try {
            result = method.invoke(menu);
        } catch(ReflectiveOperationException | RuntimeException e) {
            warnOnce(menu.getClass(), "menu_compat.json: " + method.getName() + "() of menu {} failed, guessing its chest slots", e);
            return null;
        }

        List<Slot> candidates;
        if(result instanceof Integer count && count >= 0 && count <= menu.slots.size()) {
            candidates = menu.slots.subList(0, count);
        } else if(result instanceof Collection<?> collection) {
            candidates = new ArrayList<>(collection.size());
            for(Object slot : collection) {
                if(slot instanceof Slot s && menu.slots.contains(s)) {
                    candidates.add(s);
                }
            }
        } else {
            warnOnce(menu.getClass(), "menu_compat.json: " + method.getName() + "() of menu {} returned " + result + ", guessing its chest slots", null);
            return null;
        }

        List<Slot> chest = new ArrayList<>(candidates.size());
        for(Slot slot : candidates) {
            if(!(slot.container instanceof Inventory)) {
                chest.add(slot);
            }
        }
        return Collections.unmodifiableList(chest);
    }

    /**
     * Columns of a chest, from where the screen placed its slots: the most slots that share a row. Slots moved out of
     * the screen (negative position, like slots hidden by a search) are left out.
     */
    private static int rowSizeOf(@Nullable List<Slot> chestSlots) {
        if(chestSlots == null) {
            return InvTweaksConst.INVENTORY_ROW_SIZE;
        }
        Map<Integer, Integer> slotsPerRow = new HashMap<>();
        int rowSize = 0;
        for(Slot slot : chestSlots) {
            if(slot.x >= 0 && slot.y >= 0) {
                rowSize = Math.max(rowSize, slotsPerRow.merge(slot.y, 1, Integer::sum));
            }
        }
        return rowSize > 0 ? rowSize : InvTweaksConst.INVENTORY_ROW_SIZE;
    }

    @NotNull
    private static synchronized Map<String, CompatEntry> getCompatEntries() {
        if(compatEntries == null) {
            Map<String, CompatEntry> entries = new HashMap<>();
            try(InputStream bundled = MenuResolver.class.getResourceAsStream(BUNDLED_COMPAT_FILE)) {
                if(bundled != null) {
                    readCompatFile(new InputStreamReader(bundled, StandardCharsets.UTF_8), BUNDLED_COMPAT_FILE, entries);
                }
            } catch(IOException e) {
                InventoryTweaksRevived.LOGGER.warn("Could not read {}", BUNDLED_COMPAT_FILE, e);
            }
            // The user's file is read last, so its entries win
            Path userFile = InvTweaksConst.INVTWEAKS_CONFIG_DIR.toPath().resolve(COMPAT_FILE_NAME);
            if(Files.isRegularFile(userFile)) {
                try(Reader reader = Files.newBufferedReader(userFile, StandardCharsets.UTF_8)) {
                    readCompatFile(reader, userFile.toString(), entries);
                } catch(IOException e) {
                    InventoryTweaksRevived.LOGGER.warn("Could not read {}", userFile, e);
                }
            }
            compatEntries = entries;
        }
        return compatEntries;
    }

    /**
     * Reads {@code { "menus": [ { "class": "fully.qualified.Menu", ... } ] }}. Each entry applies to its menu class and
     * to the subclasses that have no entry or rule of their own. Only {@code class} is required; other keys (like a
     * {@code "comment"}) are ignored, an invalid entry is skipped, an invalid file is ignored.
     * <ul>
     * <li>{@code showButtons} (default true): show the settings button, and the sorting buttons of the chest or of the
     * inventory.</li>
     * <li>{@code validInventory} (default false): the player's inventory can be sorted while the menu is open.</li>
     * <li>{@code validChest} (default false): the menu has a chest that can be sorted.</li>
     * <li>{@code largeChest} (default false): the buttons go down the side of the chest instead of above it.</li>
     * <li>{@code rowSize} (default 9): columns of the chest, or {@code "auto"} to count them on the screen.</li>
     * <li>{@code chestSlots}: name of a public method of the menu, without parameters, that returns either the number
     * of slots at the start of the menu that form the chest, or a collection of the chest's slots. Without it, every slot
     * that is not the player's inventory is the chest.</li>
     * <li>{@code inventoryButtons} (default false): the sorting buttons sort the player's inventory and nothing else of
     * the menu is ever touched (for menus whose other slots must not be sorted, like storage network terminals). Implies
     * {@code validInventory}; {@code validChest} is ignored.</li>
     * </ul>
     */
    private static void readCompatFile(@NotNull Reader reader, @NotNull String source, @NotNull Map<String, CompatEntry> entries) {
        try {
            JsonElement root = JsonParser.parseReader(reader);
            if(!root.isJsonObject() || !root.getAsJsonObject().has("menus") || !root.getAsJsonObject().get("menus").isJsonArray()) {
                InventoryTweaksRevived.LOGGER.warn("Ignoring {}: expected an object with a \"menus\" array", source);
                return;
            }
            for(JsonElement element : root.getAsJsonObject().getAsJsonArray("menus")) {
                try {
                    JsonObject menu = element.getAsJsonObject();
                    String className = menu.get("class").getAsString();
                    entries.put(className, new CompatEntry(
                            getBoolean(menu, "showButtons", true),
                            getBoolean(menu, "validInventory", false),
                            getBoolean(menu, "validChest", false),
                            getBoolean(menu, "largeChest", false),
                            getRowSize(menu),
                            menu.has("chestSlots") ? menu.get("chestSlots").getAsString() : null,
                            getBoolean(menu, "inventoryButtons", false)));
                } catch(RuntimeException e) {
                    InventoryTweaksRevived.LOGGER.warn("Ignoring invalid entry {} in {}", element, source);
                }
            }
        } catch(RuntimeException e) {
            InventoryTweaksRevived.LOGGER.warn("Ignoring {}: invalid JSON", source, e);
        }
    }

    private static boolean getBoolean(@NotNull JsonObject object, @NotNull String name, boolean fallback) {
        return object.has(name) ? object.get(name).getAsBoolean() : fallback;
    }

    /**
     * @return the {@code rowSize} of an entry, or {@link #AUTO_ROW_SIZE} for {@code "auto"}
     */
    private static int getRowSize(@NotNull JsonObject object) {
        if(!object.has("rowSize")) {
            return InvTweaksConst.INVENTORY_ROW_SIZE;
        }
        JsonElement rowSize = object.get("rowSize");
        if(rowSize.isJsonPrimitive() && rowSize.getAsJsonPrimitive().isString() && "auto".equalsIgnoreCase(rowSize.getAsString())) {
            return AUTO_ROW_SIZE;
        }
        return Math.max(1, rowSize.getAsInt());
    }

    private static void warnOnce(@NotNull Class<?> menuClass, @NotNull String message, @Nullable Throwable error) {
        if(WARNED.add(menuClass)) {
            if(error != null) {
                InventoryTweaksRevived.LOGGER.warn(message, menuClass.getName(), error);
            } else {
                InventoryTweaksRevived.LOGGER.warn(message, menuClass.getName());
            }
        }
    }

    /**
     * How to resolve the menus of one class.
     *
     * @param cacheable false if the result can change while the same menu instance stays open with the same slot count
     */
    record Rule(Function<AbstractContainerMenu, MenuInfo> resolver, boolean cacheable) {
        static Rule cached(Function<AbstractContainerMenu, MenuInfo> resolver) {
            return new Rule(resolver, true);
        }

        static Rule uncached(Function<AbstractContainerMenu, MenuInfo> resolver) {
            return new Rule(resolver, false);
        }
    }

    /**
     * @param rowSize          columns of the chest, or {@link #AUTO_ROW_SIZE}
     * @param chestSlotsMethod name of the menu's method that tells the chest slots, or null to guess them
     */
    private record CompatEntry(boolean showButtons, boolean validInventory, boolean validChest, boolean largeChest,
                               int rowSize, @Nullable String chestSlotsMethod, boolean inventoryButtons) {
    }
}

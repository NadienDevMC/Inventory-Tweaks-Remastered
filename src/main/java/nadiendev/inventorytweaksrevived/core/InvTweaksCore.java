package nadiendev.inventorytweaksrevived.core;

import com.mojang.blaze3d.platform.InputConstants;
import nadiendev.inventorytweaksrevived.InventoryTweaksRevived;
import nadiendev.inventorytweaksrevived.api.IItemTree;
import nadiendev.inventorytweaksrevived.api.IItemTreeItem;
import nadiendev.inventorytweaksrevived.api.IItemTreeListener;
import nadiendev.inventorytweaksrevived.api.InventoryTweaksRevivedAPI;
import nadiendev.inventorytweaksrevived.api.SortingMethod;
import nadiendev.inventorytweaksrevived.api.container.ContainerSection;
import nadiendev.inventorytweaksrevived.client.InvTweaksClient;
import nadiendev.inventorytweaksrevived.config.InvTweaksConfig;
import nadiendev.inventorytweaksrevived.config.InvTweaksConfigManager;
import nadiendev.inventorytweaksrevived.container.ContainerSectionManager;
import nadiendev.inventorytweaksrevived.container.DirectContainerManager;
import nadiendev.inventorytweaksrevived.container.IContainerManager;
import nadiendev.inventorytweaksrevived.gui.InvTweaksGuiIconButton;
import nadiendev.inventorytweaksrevived.gui.InvTweaksGuiSettingsButton;
import nadiendev.inventorytweaksrevived.gui.InvTweaksGuiSortingButton;
import nadiendev.inventorytweaksrevived.gui.InvTweaksGuiTransferButton;
import nadiendev.inventorytweaksrevived.handler.InvTweaksHandlerShortcuts;
import nadiendev.inventorytweaksrevived.handler.InvTweaksHandlerSorting;
import nadiendev.inventorytweaksrevived.handler.InvTweaksShortcutMapping;
import nadiendev.inventorytweaksrevived.handler.InvTweaksShortcutType;
import nadiendev.inventorytweaksrevived.integration.ItemListChecker;
import nadiendev.inventorytweaksrevived.itemtree.InvTweaksItemTreeLoader;
import nadiendev.inventorytweaksrevived.util.InvTweaksConst;
import nadiendev.inventorytweaksrevived.util.InvTweaksObfuscation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;


/**
 * Main class for Inventory Tweaks, which maintains various hooks and dispatches the events to the correct handlers.
 *
 * @author Jimeo Wan
 * <p>
 * Contact: jimeo.wan (at) gmail (dot) com Website: <a href="https://inventory-tweaks.readthedocs.org/">https://inventory-tweaks.readthedocs.org/</a>
 * Source code: <a href="https://github.com/kobata/inventory-tweaks">GitHub</a> License: MIT
 */
public class InvTweaksCore extends InvTweaksObfuscation implements InventoryTweaksRevivedAPI {

    private static InvTweaksCore instance;
    @NotNull
    private final ItemStack[] hotbarClone = new ItemStack[InvTweaksConst.INVENTORY_HOTBAR_SIZE];
    @NotNull
    private final List<String> queuedMessages = new ArrayList<>();
    /**
     * The configuration loader.
     */
    @Nullable
    private InvTweaksConfigManager cfgManager = null;
    /**
     * Attributes to remember the status of chest sorting while using middle clicks.
     */
    private SortingMethod chestAlgorithm = SortingMethod.DEFAULT;
    private long chestAlgorithmClickTimestamp = 0;
    private boolean chestAlgorithmButtonDown = false;
    /**
     * Various information concerning the context, stored on each tick to allow for certain features (auto-refill,
     * sorting on pick up...)
     */
    @NotNull
    private ItemStack storedStack = ItemStack.EMPTY;
    @Nullable
    private String storedStackId = null;
    private int storedStackDamage = InvTweaksConst.DAMAGE_WILDCARD, storedFocusedSlot = -1;
    private boolean wasInGUI = false;
    /**
     * Our buttons on the open container screen (added when it initializes, laid out on each tick).
     */
    @NotNull
    private final List<PlacedButton> layoutButtons = new ArrayList<>();
    @Nullable
    private AbstractContainerScreen<?> layoutScreen = null;
    /**
     * Pixels our buttons move at a time while looking for free space, the least space they keep on their sides from the
     * screen's own widgets, and the spacing of the points probed on widgets that only tell whether a point is over them.
     */
    private static final int LAYOUT_SHIFT_STEP = 1, LAYOUT_WIDGET_GAP = 1, LAYOUT_PROBE_STEP = 3;    /**
     * Stores when the sorting key was last pressed (allows to detect long key holding)
     */
    private long sortingKeyPressedDate = 0;
    private boolean sortKeyDown = false;
    private boolean sortKeyEnabled = true;
    private boolean textboxMode = false;
    private boolean itemPickupPending = false;
    private int itemPickupTimeout = 0;

    /**
     * Creates an instance of the mod, and loads the configuration from the files, creating them if necessary.
     */
    public InvTweaksCore(Minecraft mc_) {
        super(mc_);

        Arrays.fill(hotbarClone, ItemStack.EMPTY);

        // Store instance
        instance = this;

        // Load config files
        cfgManager = new InvTweaksConfigManager(mc);
        if(cfgManager.makeSureConfigurationIsLoaded()) {
            InventoryTweaksRevived.LOGGER.info("Mod initialized");
        } else {
            InventoryTweaksRevived.LOGGER.error("Mod failed to initialize!");
        }
    }

    public static void logInGameStatic(@NotNull String message) {
        if(instance == null) {
            InventoryTweaksRevived.LOGGER.info(message);
            return;
        }
        instance.logInGame(message);
    }

    public static void logInGameErrorStatic(@NotNull String message, @NotNull Exception e) {
        if(instance == null) {
            InventoryTweaksRevived.LOGGER.error(message, e);
            return;
        }
        instance.logInGameError(message, e);
    }

    /**
     * @return InvTweaksCore instance
     */
    public static InvTweaksCore getInstance() {
        return instance;
    }

    @Nullable
    public static InvTweaksConfigManager getConfigManager() {
        if(instance == null) { return null; }
        return instance.cfgManager;
    }

    @NotNull
    public static IContainerManager getContainerManager(@NotNull AbstractContainerMenu container) {
        return new DirectContainerManager(container);
    }

    @NotNull
    public static IContainerManager getCurrentContainerManager() {
        return getContainerManager(InvTweaksObfuscation.getCurrentContainer());
    }

    /**
     * Whether our chest buttons (settings and transfer arrows) are shown on the screen of this menu.
     */
    public static boolean showsChestButtons(@Nullable AbstractContainerMenu menu) {
        return menu != null && instance != null && !instance.isSpectatorOrNotInGame() && instance.cfgManager.getConfig() != null
                && showButtons(menu) && isValidChest(menu);
    }

    /**
     * Whether our chest sorting buttons are shown on the screen of this menu (they can be turned off in the settings).
     */
    public static boolean showsChestSortingButtons(@Nullable AbstractContainerMenu menu) {
        return showsChestButtons(menu)
                && !instance.cfgManager.getConfig().getProperty(InvTweaksConfig.PROP_SHOW_CHEST_BUTTONS).equals("false");
    }

    /**
     * Gives a left click over one of our visible buttons to it, before the screen's own widgets: some screens have
     * widgets under ours (the search box of Sophisticated Storage covers the whole title line while it is open).
     *
     * @return true if one of our buttons took the click
     */
    public boolean clickLayoutButton(@NotNull Screen screen, double mouseX, double mouseY, int button) {
        if(button != GLFW.GLFW_MOUSE_BUTTON_LEFT || screen != layoutScreen) {
            return false;
        }
        for(PlacedButton placed : layoutButtons) {
            InvTweaksGuiIconButton b = placed.button();
            if(b.visible && b.active && b.isMouseOver(mouseX, mouseY)) {
                return b.mouseClicked(new MouseButtonEvent(mouseX, mouseY, new MouseButtonInfo(button, 0)), false);
            }
        }
        return false;
    }

    private static int getContainerRowSize(@NotNull AbstractContainerScreen<?> guiContainer) {
        return getSpecialChestRowSize(guiContainer.getMenu());
    }

    @NotNull
    private static String buildLogString(boolean error, String message, @Nullable Exception e) {
        if(e != null) {
            StackTraceElement[] trace = e.getStackTrace();

            if(trace.length == 0) {
                return buildLogString(error, message) + ": " + e.getMessage();
            }

            StackTraceElement exceptionLine = trace[0];
            if(exceptionLine != null && exceptionLine.getFileName() != null) {
                return buildLogString(error, message) + ": " + e.getMessage() + " (l" + exceptionLine.getLineNumber() + " in " + exceptionLine.getFileName().replace("InvTweaks", "") + ")";
            } else {
                return buildLogString(error, message) + ": " + e.getMessage();
            }
        } else {
            return buildLogString(error, message);
        }
    }

    @NotNull
    private static String buildLogString(boolean error, String message) {
        return InvTweaksConst.INGAME_LOG_PREFIX + (error ? "[ERROR] " : "") + message;
    }

    @NotNull
    private static String getItemId(@NotNull ItemStack stack) {
        return BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
    }

    /**
     * Same item and same damage, ignoring the count and the other components (the 1.12 {@code areItemsEqual}).
     */
    private static boolean areItemsEqual(@NotNull ItemStack a, @NotNull ItemStack b) {
        return ItemStack.isSameItem(a, b) && a.getDamageValue() == b.getDamageValue();
    }

    private static int compareMaxDamage(ItemStack i, ItemStack j) {
        //Use durability to sort, favoring more durable items.
        int maxDamage1 = i.getMaxDamage() <= 0 ? Integer.MAX_VALUE : i.getMaxDamage();
        int maxDamage2 = j.getMaxDamage() <= 0 ? Integer.MAX_VALUE : j.getMaxDamage();
        return maxDamage2 - maxDamage1;
    }

    private static int compareCurDamage(ItemStack i, ItemStack j) {
        //Use remaining durability to sort, favoring more damaged.
        int curDamage1 = i.getDamageValue();
        int curDamage2 = j.getDamageValue();
        if(i.isDamageableItem() && !getConfigManager().getConfig().getProperty(InvTweaksConfig.PROP_INVERT_TOOL_DAMAGE).equals(InvTweaksConfig.VALUE_TRUE)) {
            return curDamage2 - curDamage1;
        } else {
            return curDamage1 - curDamage2;
        }
    }

    /**
     * Tool class of a stack, from the item tags. Swords are not "tools". An item in several tool tags (a paxel) is
     * listed under the first of pickaxe, axe, shovel, hoe.
     *
     * @return "pickaxe", "axe", "shovel", "hoe", "shears", "fishingrod", or "" if the stack is not a tool.
     */
    public static String getToolClass(ItemStack itemStack, Item item) {
        if(itemStack == null || item == null || itemStack.isEmpty()) { return ""; }
        if(itemStack.is(ItemTags.PICKAXES)) { return "pickaxe"; }
        if(itemStack.is(ItemTags.AXES)) { return "axe"; }
        if(itemStack.is(ItemTags.SHOVELS)) { return "shovel"; }
        if(itemStack.is(ItemTags.HOES)) { return "hoe"; }
        if(itemStack.is(Items.SHEARS) || item instanceof ShearsItem) { return "shears"; }
        if(item instanceof FishingRodItem) { return "fishingrod"; }
        return "";
    }

    /**
     * Stand-in for the old harvest level: the main-hand attack damage of the stack, which is the baseline of the tool
     * class plus the attack damage bonus of its material (wood and gold 0, stone 1, iron 2, diamond 3, netherite 4), so
     * tools of the same class sort by tier.
     */
    private static float getHarvestLevel(ItemStack stack) {
        @Nullable Double damage = getModifier(stack.getAttributeModifiers(), EquipmentSlot.MAINHAND, Attributes.ATTACK_DAMAGE);
        return damage == null ? 0.0F : damage.floatValue();
    }

    /**
     * @return the summed amount of the stack's main-hand modifiers for the attribute, or null if it has none.
     */
    @Nullable
    private static Double getMainHandModifier(@NotNull ItemAttributeModifiers modifiers, @NotNull Holder<Attribute> attribute) {
        return getModifier(modifiers, EquipmentSlot.MAINHAND, attribute);
    }

    /**
     * @return the summed amount of the modifiers of the slot for the attribute, or null if there are none.
     */
    @Nullable
    private static Double getModifier(@NotNull ItemAttributeModifiers modifiers, @NotNull EquipmentSlot slot, @NotNull Holder<Attribute> attribute) {
        double[] amount = {0.0D};
        boolean[] found = {false};
        modifiers.forEach(slot, (modifierAttribute, modifier) -> {
            if(modifierAttribute.value() == attribute.value()) {
                amount[0] += modifier.amount();
                found[0] = true;
            }
        });
        return found[0] ? amount[0] : null;
    }

    /**
     * The armor slot of an armor piece (the old {@code ArmorItem}): equippable in a humanoid armor slot, with an armor
     * value, and not an elytra.
     *
     * @return the slot, or null if the stack is not an armor piece.
     */
    @Nullable
    public static EquipmentSlot getArmorSlot(@NotNull ItemStack stack) {
        @Nullable Equippable equippable = stack.get(DataComponents.EQUIPPABLE);
        if(equippable == null || stack.has(DataComponents.GLIDER) || equippable.slot().getType() != EquipmentSlot.Type.HUMANOID_ARMOR) {
            return null;
        }
        return getModifier(stack.getAttributeModifiers(), equippable.slot(), Attributes.ARMOR) == null ? null : equippable.slot();
    }

    public static boolean isArmor(@NotNull ItemStack stack) {
        return getArmorSlot(stack) != null;
    }

    /**
     * Armor points of an armor piece (0 if it is not one).
     */
    public static int getArmorDefense(@NotNull ItemStack stack) {
        @Nullable EquipmentSlot slot = getArmorSlot(stack);
        @Nullable Double value = slot == null ? null : getModifier(stack.getAttributeModifiers(), slot, Attributes.ARMOR);
        return value == null ? 0 : (int) Math.round(value);
    }

    /**
     * Armor toughness of an armor piece (0 if it is not one).
     */
    public static float getArmorToughness(@NotNull ItemStack stack) {
        @Nullable EquipmentSlot slot = getArmorSlot(stack);
        @Nullable Double value = slot == null ? null : getModifier(stack.getAttributeModifiers(), slot, Attributes.ARMOR_TOUGHNESS);
        return value == null ? 0.0F : value.floatValue();
    }

    /**
     * Position of an armor slot from head to feet.
     */
    private static int armorSlotOrder(@NotNull EquipmentSlot slot) {
        return switch(slot) {
            case HEAD -> 0;
            case CHEST -> 1;
            case LEGS -> 2;
            default -> 3;
        };
    }

    @NotNull
    private static String getEnchantmentId(@NotNull Holder<Enchantment> enchantment) {
        return enchantment.unwrapKey().map(key -> key.identifier().toString()).orElse("");
    }

    public void addScheduledTask(Runnable task) {
        mc.execute(task);
    }

    /**
     * To be called on each tick during the game (except when in a menu). Handles the auto-refill.
     */
    public void onTickInGame() {
        synchronized(this) {
            if(!onTick()) {
                return;
            }
            handleAutoRefill();
            if(wasInGUI) {
                wasInGUI = false;
                textboxMode = false;
                clearGUILayout();
            }
        }
    }

    /**
     * To be called on each tick when a menu is open. Handles the GUI additions and the middle clicking.
     */
    public void onTickInGUI(Screen guiScreen) {
        if(isSpectatorOrNotInGame()) {
            onTick();
            return;
        }

        synchronized(this) {
            handleMiddleClick(guiScreen); // Called before the rest to be able to trigger config reload
            if(!onTick()) {
                return;
            }
            handleGUILayout(guiScreen);

            // Copy some info about current selected stack for auto-refill
            @NotNull ItemStack currentStack = getFocusedStack();

            storedStackId = (currentStack.isEmpty()) ? null : getItemId(currentStack);
            storedStackDamage = (currentStack.isEmpty()) ? 0 : currentStack.getDamageValue();
            if(!wasInGUI) {
                wasInGUI = true;
            }
        }
    }

    /**
     * To be called every time the sorting key is pressed. Sorts the inventory.
     */
    public final void onSortingKeyPressed() {
        synchronized(this) {

            // Nothing to sort for a spectator or without a player
            if(isSpectatorOrNotInGame()) {
                return;
            }

            // Check config loading success
            if(!cfgManager.makeSureConfigurationIsLoaded()) {
                return;
            }

            // Check current GUI
            @Nullable Screen guiScreen = getCurrentScreen();
            if(guiScreen == null || (guiScreen instanceof AbstractContainerScreen<?> containerScreen && (isValidChest(containerScreen.getMenu()) || isValidInventory(containerScreen.getMenu())))) {
                // Sorting!
                handleSorting();
            }
        }
    }

    /**
     * To be called everytime a stack has been picked up. Moves the picked up item in another slot that matches best the
     * current configuration.
     */
    public void onItemPickup() {

        if(!cfgManager.makeSureConfigurationIsLoaded()) {
            return;
        }
        @Nullable InvTweaksConfig config = cfgManager.getConfig();
        // Handle option to disable this feature
        if(cfgManager.getConfig().getProperty(InvTweaksConfig.PROP_ENABLE_SORTING_ON_PICKUP).equals("false")) {
            itemPickupPending = false;
            return;
        }

        try {
            @NotNull ContainerSectionManager containerMgr = new ContainerSectionManager(ContainerSection.INVENTORY);

            // Find stack slot (look in hotbar only).
            // We're looking for a brand new stack in the hotbar
            // (not an existing stack whose amount has been increased):
            // a slot that was empty when the hotbar was last cloned.
            int currentSlot = -1;
            for(int i = 0; i < InvTweaksConst.INVENTORY_HOTBAR_SIZE; i++) {
                @NotNull ItemStack currentHotbarStack = containerMgr.getItemStack(i + 27);
                // Don't move already started stacks
                if(!currentHotbarStack.isEmpty() && hotbarClone[i].isEmpty()) {
                    currentSlot = i + 27;
                }
            }

            if(currentSlot != -1) {
                itemPickupPending = false;

                // Find preferred slots
                IItemTree tree = config.getTree();
                @NotNull ItemStack stack = containerMgr.getItemStack(currentSlot);

                List<IItemTreeItem> items = tree.getItems(stack);

                List<Integer> preferredPositions = config.getRules().stream().filter(rule -> tree.matches(items, rule.getKeyword())).flatMapToInt(e -> Arrays.stream(e.getPreferredSlots())).boxed().collect(Collectors.toList());

                // Find best slot for stack
                boolean hasToBeMoved = true;
                for(int newSlot : preferredPositions) {
                    // Already in the best slot!
                    if(newSlot == currentSlot) {
                        hasToBeMoved = false;
                        break;
                    }
                    // Is the slot available?
                    else if(containerMgr.getItemStack(newSlot).isEmpty()) {
                        // TODO: Check rule level before to move
                        if(containerMgr.move(currentSlot, newSlot)) {
                            break;
                        }
                    }
                }

                // Else, put the slot anywhere
                if(hasToBeMoved) {
                    for(int i = 0; i < containerMgr.getSize(); i++) {
                        if(containerMgr.getItemStack(i).isEmpty()) {
                            if(containerMgr.move(currentSlot, i)) {
                                break;
                            }
                        }
                    }
                }

                // Sync after pickup movements.
                containerMgr.applyChanges();

            } else {
                if(--itemPickupTimeout == 0) {
                    itemPickupPending = false;
                }
            }

        } catch(Exception e) {
            logInGameError("Failed to move picked up stack", e);
            itemPickupPending = false;
        }
    }

    @Override
    public int compareItems(@NotNull ItemStack i, @NotNull ItemStack j) {
        return compareItems(i, j, getItemOrder(i), getItemOrder(j), false);
    }

    @Override
    public int compareItems(@NotNull ItemStack i, @NotNull ItemStack j, boolean onlyTreeSort) {
        return compareItems(i, j, getItemOrder(i), getItemOrder(j), onlyTreeSort);
    }

    public int compareItems(@NotNull ItemStack i, @NotNull ItemStack j, int orderI, int orderJ) {
        return compareItems(i, j, orderI, orderJ, false);
    }

    int compareItems(@NotNull ItemStack i, @NotNull ItemStack j, int orderI, int orderJ, boolean api) {
        if(i.isEmpty() && j.isEmpty()) {
            //Technically, if both are empty, they are equal.
            return 0;
        } else if(j.isEmpty()) {
            return -1;
        } else if(i.isEmpty() || orderI == -1) {
            return 1;
        } else {
            if(api) {
                int lastOrder = cfgManager.getConfig().getTree().getLastTreeOrder();
                if(orderI > lastOrder) { orderI = Integer.MAX_VALUE; }
                if(orderJ > lastOrder) { orderJ = Integer.MAX_VALUE; }
            }

            //If items are in different order slots, they are inherently comparator contract friendly.
            if(orderI != orderJ) {
                return orderI - orderJ;
            }

            //All items in the same sort slot need to be treated the same for the comparator contract.

            //Allow external sorting systems to take control of unsorted items not handled by the tree.
            if(orderI == Integer.MAX_VALUE && orderJ == Integer.MAX_VALUE && api) {
                return 0;
            }

            Item iItem = i.getItem(), jItem = j.getItem();
            //Sort By Tool type then Harvest Level, (Better first.)
            int cTool = compareTools(i, j, iItem, jItem);
            if(cTool != 0) { return cTool; }

            //Sort by main-hand damage capability:  (Higher first, faster first for same damage)
            //Most tools also do damage, so they were tested as tools first.
            //If a tool reaches here, it has the same max durabilty, harvest level, and tool class.
            int cSword = compareSword(i, j);
            if(cSword != 0) { return cSword; }

            //Sort By Armor utility:  (More First)
            int cArmor = compareArmor(i, j, iItem, jItem);
            if(cArmor != 0) { return cArmor; }

            //Sort my display name:
            int cName = compareNames(i, j);
            if(cName != 0) { return cName; }

            //Sort By enchantments:
            int cEnchant = compareEnchantment(i, j);
            if(cEnchant != 0) { return cEnchant; }

            //Use durability to sort, favoring more durable items.  (Non-Tools, Non-Armor, Non-Weapons.)
            int maxDamage = compareMaxDamage(i, j);
            if(maxDamage != 0) { return maxDamage; }

            //Use remaining durability to sort, favoring config option on damaged.
            int curDamage = compareCurDamage(i, j);
            if(curDamage != 0) { return curDamage; }

            //Use stack size to put bigger stacks first.
            if(j.getCount() != i.getCount()) {
                return j.getCount() - i.getCount();
            }

            //Final catch all:
            return getItemId(i).compareTo(getItemId(j));
        }
    }

    private int compareNames(ItemStack i, ItemStack j) {
        boolean iHasName = i.has(DataComponents.CUSTOM_NAME);
        boolean jHasName = j.has(DataComponents.CUSTOM_NAME);
        @NotNull String iDisplayName = i.getHoverName().getString();
        @NotNull String jDisplayName = j.getHoverName().getString();

        //Custom named items come first.
        if(iHasName || jHasName) {
            if(!iHasName) {
                return -1;
            } else if(!jHasName) {
                return 1;
            }
        }
        //Differently named items (either both custom or both default, like bees or resource chickens.)
        if(!iDisplayName.equals(jDisplayName)) {
            return iDisplayName.compareTo(jDisplayName);
        }

        return 0;
    }

    private int compareTools(ItemStack i, ItemStack j, Item iItem, Item jItem) {
        String toolClass1 = getToolClass(i, iItem);
        String toolClass2 = getToolClass(j, jItem);

        boolean isTool1 = !toolClass1.isEmpty();
        boolean isTool2 = !toolClass2.isEmpty();
        if(!isTool1 || !isTool2) {
            return Boolean.compare(isTool2, isTool1);
        } else {
            int toolClassComparison = toolClass1.compareTo(toolClass2);
            if(toolClassComparison != 0) {
                return toolClassComparison;
            }
            // If they were the same type, sort with the better harvest level first.
            float harvestLevel1 = getHarvestLevel(i);
            float harvestLevel2 = getHarvestLevel(j);
            int toolLevelComparison = Float.compare(harvestLevel2, harvestLevel1);
            if(toolLevelComparison != 0) {
                return toolLevelComparison;
            }
        }

        return compareMaxDamage(i, j);

    }

    private int compareSword(ItemStack itemStack1, ItemStack itemStack2) {
        ItemAttributeModifiers modifiers1 = itemStack1.getAttributeModifiers();
        ItemAttributeModifiers modifiers2 = itemStack2.getAttributeModifiers();

        @Nullable Double attackDamage1 = getMainHandModifier(modifiers1, Attributes.ATTACK_DAMAGE);
        @Nullable Double attackDamage2 = getMainHandModifier(modifiers2, Attributes.ATTACK_DAMAGE);
        @Nullable Double speed1 = getMainHandModifier(modifiers1, Attributes.ATTACK_SPEED);
        @Nullable Double speed2 = getMainHandModifier(modifiers2, Attributes.ATTACK_SPEED);

        boolean hasDamage1 = attackDamage1 != null;
        boolean hasDamage2 = attackDamage2 != null;


        if(!hasDamage1 || !hasDamage2) {
            return Boolean.compare(hasDamage2, hasDamage1);
        } else {
            int damageComparison = Double.compare(attackDamage2, attackDamage1);
            if(damageComparison == 0 && speed1 != null && speed2 != null) {
                // Same damage, sort faster weapon first.
                int speedComparison = Double.compare(speed2, speed1);
                if(speedComparison != 0) { return speedComparison; }

            } else if(damageComparison != 0) {
                // Higher damage first.
                return damageComparison;
            }
            return compareMaxDamage(itemStack1, itemStack2);
        }
    }

    private int compareArmor(ItemStack i, ItemStack j, Item iItem, Item jItem) {
        @Nullable EquipmentSlot slot1 = getArmorSlot(i);
        @Nullable EquipmentSlot slot2 = getArmorSlot(j);
        int isArmor1 = (slot1 != null) ? 1 : 0;
        int isArmor2 = (slot2 != null) ? 1 : 0;
        if(isArmor1 == 0 || isArmor2 == 0) {
            return isArmor2 - isArmor1;
        } else {
            int defense1 = getArmorDefense(i), defense2 = getArmorDefense(j);
            float toughness1 = getArmorToughness(i), toughness2 = getArmorToughness(j);
            if(slot1 != slot2) {
                // Head first, then chest, legs and feet.
                return Integer.compare(armorSlotOrder(slot1), armorSlotOrder(slot2));
            } else if(defense1 != defense2) {
                return defense2 - defense1;
            } else if(toughness1 != toughness2) {
                return Float.compare(toughness2, toughness1);
            }
            return compareMaxDamage(i, j);
        }
    }

    private int compareEnchantment(ItemStack i, ItemStack j) {
        // Stored enchantments for enchanted books, applied enchantments otherwise
        @NotNull ItemEnchantments iEnchs = EnchantmentHelper.getEnchantmentsForCrafting(i);
        @NotNull ItemEnchantments jEnchs = EnchantmentHelper.getEnchantmentsForCrafting(j);

        //Pick the item with the most enchantments first.
        if(iEnchs.size() != jEnchs.size()) {
            return jEnchs.size() - iEnchs.size();
        }

        String iEnchMaxId = "", jEnchMaxId = "";
        int iEnchMaxLvl = 0, jEnchMaxLvl = 0;

        // TODO: This is really arbitrary but there's not really a good way to do this generically.
        for(@NotNull Object2IntMap.Entry<Holder<Enchantment>> ench : iEnchs.entrySet()) {
            String enchId = getEnchantmentId(ench.getKey());
            if(ench.getIntValue() > iEnchMaxLvl) {
                iEnchMaxId = enchId;
                iEnchMaxLvl = ench.getIntValue();
            } else if(ench.getIntValue() == iEnchMaxLvl && enchId.compareTo(iEnchMaxId) > 0) {
                iEnchMaxId = enchId;
            }
        }

        for(@NotNull Object2IntMap.Entry<Holder<Enchantment>> ench : jEnchs.entrySet()) {
            String enchId = getEnchantmentId(ench.getKey());
            if(ench.getIntValue() > jEnchMaxLvl) {
                jEnchMaxId = enchId;
                jEnchMaxLvl = ench.getIntValue();
            } else if(ench.getIntValue() == jEnchMaxLvl && enchId.compareTo(jEnchMaxId) > 0) {
                jEnchMaxId = enchId;
            }
        }

        //The highest enchantment ID, (random actual enchantment.)
        if(!iEnchMaxId.equals(jEnchMaxId)) {
            return jEnchMaxId.compareTo(iEnchMaxId);
        }

        //Highest level if they both have the same coolest enchantment.
        if(iEnchMaxLvl != jEnchMaxLvl) {
            return jEnchMaxLvl - iEnchMaxLvl;
        }

        //Enchantments aren't different.
        return 0;
    }

    public void setItemPickupPending(boolean value) {
        itemPickupPending = value;
        itemPickupTimeout = 5;
    }

    @Override
    public void addOnLoadListener(IItemTreeListener listener) {
        InvTweaksItemTreeLoader.addOnLoadListener(listener);
    }

    @Override
    public boolean removeOnLoadListener(IItemTreeListener listener) {
        return InvTweaksItemTreeLoader.removeOnLoadListener(listener);
    }

    @Override
    public void setSortKeyEnabled(boolean enabled) {
        sortKeyEnabled = enabled;
    }

    @Override
    public void setTextboxMode(boolean enabled) {
        textboxMode = enabled;
    }

    /**
     * Sorts a section of the open menu (the player's inventory menu if no container screen is open).
     */
    @Override
    public void sort(ContainerSection section, SortingMethod method) {
        synchronized(this) {
            if(isSpectatorOrNotInGame() || !cfgManager.makeSureConfigurationIsLoaded()) {
                return;
            }
            try {
                new InvTweaksHandlerSorting(mc, cfgManager.getConfig(), section, method, getSpecialChestRowSize(getCurrentContainer())).sort();
            } catch(Exception e) {
                logInGameError("invtweaks.sort.chest.error", e);
            }
        }
    }

    public void logInGame(@NotNull String message) {
        logInGame(message, false);
    }

    /**
     * Chat messages need a player in a world; anything logged before that (config loading at startup) is kept until
     * then.
     */
    private boolean canShowChatMessages() {
        return mc.gui != null && mc.player != null;
    }

    public void printQueuedMessages() {
        if(canShowChatMessages() && !queuedMessages.isEmpty()) {
            queuedMessages.forEach(this::addChatMessage);
            queuedMessages.clear();
        }
    }

    public void logInGame(@NotNull String message, boolean alreadyTranslated) {
        @NotNull String formattedMsg = buildLogString(false, (alreadyTranslated) ? message : I18n.get(message));

        if(!canShowChatMessages()) {
            queuedMessages.add(formattedMsg);
        } else {
            addChatMessage(formattedMsg);
        }

        InventoryTweaksRevived.LOGGER.info(formattedMsg);
    }

    public void logInGameError(@NotNull String message, @NotNull Exception e) {
        @NotNull String formattedMsg = buildLogString(true, I18n.get(message), e);
        InventoryTweaksRevived.LOGGER.error(formattedMsg, e);

        if(!canShowChatMessages()) {
            queuedMessages.add(formattedMsg);
        } else {
            addChatMessage(formattedMsg);
        }
    }

    private boolean isSpectatorOrNotInGame() {
        return mc.player == null || mc.gameMode == null || mc.player.isSpectator();
    }

    private boolean onTick() {
        printQueuedMessages();

        if(isSpectatorOrNotInGame()) {
            return false;
        }

        // Not calling "cfgManager.makeSureConfigurationIsLoaded()" for performance reasons
        @Nullable InvTweaksConfig config = cfgManager.getConfig();
        if(config == null) {
            return false;
        }

        // Clone the hotbar to be able to monitor changes on it
        if(itemPickupPending) {
            onItemPickup();
        }
        @Nullable Screen currentScreen = getCurrentScreen();
        if(currentScreen == null || isGuiInventory(currentScreen)) {
            cloneHotbar();
        }

        // Handle sort key
        if(isSortingShortcutDown()) {
            if(!sortKeyDown) {
                sortKeyDown = true;
                onSortingKeyPressed();
            }
        } else {
            sortKeyDown = false;
        }

        // Handle config switch
        handleConfigSwitch();

        return true;

    }

    private void handleConfigSwitch() {

        @Nullable InvTweaksConfig config = cfgManager.getConfig();

        // Switch between configurations (shortcut)
        cfgManager.getShortcutsHandler().updatePressedKeys();
        @Nullable InvTweaksShortcutMapping switchMapping = cfgManager.getShortcutsHandler().isShortcutDown(InvTweaksShortcutType.MOVE_TO_SPECIFIC_HOTBAR_SLOT);
        if(isSortingShortcutDown() && switchMapping != null) {
            @Nullable String newRuleset = null;
            int pressedKey = switchMapping.getKeyCodes().get(0);
            if(pressedKey >= GLFW.GLFW_KEY_1 && pressedKey <= GLFW.GLFW_KEY_9) {
                newRuleset = config.switchConfig(pressedKey - GLFW.GLFW_KEY_1);
            } else if(pressedKey >= GLFW.GLFW_KEY_KP_1 && pressedKey <= GLFW.GLFW_KEY_KP_9) {
                newRuleset = config.switchConfig(pressedKey - GLFW.GLFW_KEY_KP_1);
            }

            if(newRuleset != null) {
                logInGame(I18n.get("invtweaks.loadconfig.enabled", newRuleset), true);
                // Hack to prevent 2nd way to switch configs from being enabled
                sortingKeyPressedDate = Integer.MAX_VALUE;
            }
        }

        // Switch between configurations (by holding the sorting key)
        if(isSortingShortcutDown()) {
            long currentTime = System.currentTimeMillis();
            if(sortingKeyPressedDate == 0) {
                sortingKeyPressedDate = currentTime;
            } else if(currentTime - sortingKeyPressedDate > InvTweaksConst.RULESET_SWAP_DELAY && sortingKeyPressedDate != Integer.MAX_VALUE) {
                @Nullable String previousRuleset = config.getCurrentRulesetName();
                @Nullable String newRuleset = config.switchConfig();
                // Log only if there is more than 1 ruleset
                if(previousRuleset != null && newRuleset != null && !previousRuleset.equals(newRuleset)) {
                    logInGame(I18n.get("invtweaks.loadconfig.enabled", newRuleset), true);
                    handleSorting();
                }
                sortingKeyPressedDate = currentTime;
            }
        } else {
            sortingKeyPressedDate = 0;
        }

    }

    private void handleSorting() {
        try {
            new InvTweaksHandlerSorting(mc, cfgManager.getConfig(), ContainerSection.INVENTORY, SortingMethod.INVENTORY, InvTweaksConst.INVENTORY_ROW_SIZE).sort();
        } catch(Exception e) {
            logInGameError("invtweaks.sort.inventory.error", e);
        }

        playClick();
    }

    private void handleAutoRefill() {
        @NotNull ItemStack currentStack = getFocusedStack();
        @NotNull ItemStack offhandStack = getOffhandStack();

        @Nullable String currentStackId = (currentStack.isEmpty()) ? null : getItemId(currentStack);

        int currentStackDamage = (currentStack.isEmpty()) ? 0 : currentStack.getDamageValue();
        int focusedSlot = getFocusedSlot() + 27; // Convert to container slots index
        @Nullable InvTweaksConfig config = cfgManager.getConfig();


        if(storedFocusedSlot != focusedSlot) { // Filter selection change
            storedFocusedSlot = focusedSlot;
        } else if(!areItemsEqual(currentStack, storedStack) && storedStackId != null) {
            if(!storedStack.isEmpty() && !ItemStack.matches(offhandStack, storedStack)) { // Checks not switched to offhand
                if(currentStack.isEmpty() || (currentStack.getItem() == Items.BOWL && Objects.equals(storedStackId, "minecraft:mushroom_stew"))
                        // Handle eaten mushroom soup
                        && (getCurrentScreen() == null || // Filter open inventory or other window
                        isGuiEditSign(getCurrentScreen()))) { // TODO: This should be more expandable on 'equivalent' items (API?) and allowed GUIs

                    if(config.isAutoRefillEnabled(storedStackId, storedStackDamage)) {
                        try {
                            cfgManager.getAutoRefillHandler().autoRefillSlot(focusedSlot, storedStackId, storedStackDamage);
                        } catch(Exception e) {
                            logInGameError("invtweaks.sort.autorefill.error", e);
                        }
                    }
                } else {
                    // Item
                    int itemMaxDamage = currentStack.getMaxDamage();
                    int autoRefillThreshhold = config.getIntProperty(InvTweaksConfig.PROP_AUTO_REFILL_DAMAGE_THRESHHOLD);
                    if(canToolBeReplaced(currentStackDamage, itemMaxDamage, autoRefillThreshhold) && config.getProperty(InvTweaksConfig.PROP_AUTO_REFILL_BEFORE_BREAK).equals(InvTweaksConfig.VALUE_TRUE) && config.isAutoRefillEnabled(storedStackId, storedStackDamage)) {
                        // Trigger auto-refill before the tool breaks
                        try {
                            cfgManager.getAutoRefillHandler().autoRefillSlot(focusedSlot, storedStackId, storedStackDamage);
                        } catch(Exception e) {
                            logInGameError("invtweaks.sort.autorefill.error", e);
                        }
                    }
                }
            }
        }

        // Copy some info about current selected stack for auto-refill
        storedStack = currentStack.copy();
        storedStackId = currentStackId;
        storedStackDamage = currentStackDamage;

    }

    private boolean canToolBeReplaced(int currentStackDamage, int itemMaxDamage, int autoRefillThreshhold) {
        return itemMaxDamage != 0 && itemMaxDamage - currentStackDamage < autoRefillThreshhold && itemMaxDamage - storedStackDamage >= autoRefillThreshhold;
    }

    /**
     * Polls a mouse button straight from GLFW: {@code MouseHandler.isLeftPressed()} and its siblings only track presses
     * made while no screen is open.
     */
    private boolean isMouseButtonDown(int button) {
        return GLFW.glfwGetMouseButton(mc.getWindow().handle(), button) == GLFW.GLFW_PRESS;
    }

    private void handleMiddleClick(Screen guiScreen) {
        if(isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_MIDDLE)) {

            if(!cfgManager.makeSureConfigurationIsLoaded()) {
                return;
            }
            @Nullable InvTweaksConfig config = cfgManager.getConfig();

            // Check that middle click sorting is allowed
            if(config.getProperty(InvTweaksConfig.PROP_ENABLE_MIDDLE_CLICK).equals(InvTweaksConfig.VALUE_TRUE) && guiScreen instanceof AbstractContainerScreen<?> guiContainer) {

                AbstractContainerMenu container = guiContainer.getMenu();

                if(!chestAlgorithmButtonDown) {
                    chestAlgorithmButtonDown = true;

                    @NotNull IContainerManager containerMgr = getContainerManager(container);
                    @Nullable Slot slotAtMousePosition = InvTweaksObfuscation.getSlotAtMousePosition(guiContainer);
                    @Nullable ContainerSection target = null;
                    if(slotAtMousePosition != null) {
                        target = containerMgr.getSlotSection(getSlotNumber(slotAtMousePosition));
                    }

                    if(isValidChest(container)) {

                        // Check if the middle click target the chest or the inventory

                        if(ContainerSection.CHEST.equals(target)) {

                            // Play click
                            playClick();

                            long timestamp = System.currentTimeMillis();
                            if(timestamp - chestAlgorithmClickTimestamp > InvTweaksConst.CHEST_ALGORITHM_SWAP_MAX_INTERVAL || getContainerRowSize(guiContainer) <= 0) {
                                chestAlgorithm = SortingMethod.DEFAULT;
                            }
                            try {
                                new InvTweaksHandlerSorting(mc, cfgManager.getConfig(), ContainerSection.CHEST, chestAlgorithm, getContainerRowSize(guiContainer)).sort();
                            } catch(Exception e) {
                                logInGameError("invtweaks.sort.chest.error", e);
                            }
                            // TODO: Better replacement for this.
                            chestAlgorithm = SortingMethod.values()[(chestAlgorithm.ordinal() + 1) % 3];
                            chestAlgorithmClickTimestamp = timestamp;

                        } else if(ContainerSection.CRAFTING_IN.equals(target) || ContainerSection.CRAFTING_IN_PERSISTENT.equals(target)) {
                            try {
                                new InvTweaksHandlerSorting(mc, cfgManager.getConfig(), target, SortingMethod.EVEN_STACKS, (containerMgr.getSize(target) == 9) ? 3 : 2).sort();
                            } catch(Exception e) {
                                logInGameError("invtweaks.sort.crafting.error", e);
                            }

                        } else if(ContainerSection.INVENTORY_HOTBAR.equals(target) || (ContainerSection.INVENTORY_NOT_HOTBAR.equals(target))) {
                            handleSorting();
                        }

                    } else if(isValidInventory(container)) {
                        if(ContainerSection.CRAFTING_IN.equals(target) || ContainerSection.CRAFTING_IN_PERSISTENT.equals(target)) {
                            // Crafting stacks evening
                            try {
                                new InvTweaksHandlerSorting(mc, cfgManager.getConfig(), target, SortingMethod.EVEN_STACKS, (containerMgr.getSize(target) == 9) ? 3 : 2).sort();
                            } catch(Exception e) {
                                logInGameError("invtweaks.sort.crafting.error", e);
                            }
                        } else if(!hasInventoryButtons(container) || ContainerSection.INVENTORY_HOTBAR.equals(target) || ContainerSection.INVENTORY_NOT_HOTBAR.equals(target)) {
                            // Sorting (menus with inventory buttons, like storage network terminals, may use middle
                            // clicks elsewhere for themselves)
                            handleSorting();
                        }
                    }
                }
            }
        } else {
            chestAlgorithmButtonDown = false;
        }
    }

    /**
     * One of our buttons and its offset from the layout anchor (the top right corner of the screen's image).
     */
    private static final int SIDE_BUTTON_SIZE = 14;

    private record PlacedButton(@NotNull InvTweaksGuiIconButton button, int dx, int dy, boolean sortingButton, boolean inventoryRow, int side) {
        static final int SIDE_PANEL = 1, SIDE_CHEST = 2, SIDE_INVENTORY = 3;

        PlacedButton(@NotNull InvTweaksGuiIconButton button, int dx, int dy, boolean sortingButton, boolean inventoryRow) {
            this(button, dx, dy, sortingButton, inventoryRow, 0);
        }

        PlacedButton(@NotNull InvTweaksGuiIconButton button, int dx, int dy, boolean sortingButton) {
            this(button, dx, dy, sortingButton, false, 0);
        }

        /** Whether the button sits in a column outside the right edge of the panel. */
        boolean sideColumn() {
            return side != 0;
        }
    }

    /**
     * Adds our buttons to a container screen. Called each time the screen (re)initializes, after its own widgets are in
     * place (a re-init clears every widget, ours included).
     *
     * @param addWidget adds a widget to the screen's renderables, narratables and children
     */
    public void initGUILayout(@NotNull AbstractContainerScreen<?> guiContainer, @NotNull Consumer<GuiEventListener> addWidget) {
        layoutButtons.clear();
        layoutScreen = guiContainer;

        if(isSpectatorOrNotInGame() || cfgManager.getConfig() == null) {
            return;
        }

        AbstractContainerMenu container = guiContainer.getMenu();
        // The creative screen only shows the button on its inventory tab, which can be opened later on.
        if(!showButtons(container) && !isGuiInventoryCreative(guiContainer)) {
            return;
        }

        int w = 10, h = 10;
        if(hasInventoryButtons(container)) {
            // Settings and sorting buttons of the player's inventory (such menus have no sortable chest), laid out like
            // the chest ones. Rows and columns only arrange the main inventory, so that the hotbar keeps its items.
            int rowSize = InvTweaksConst.INVENTORY_ROW_SIZE;
            placeButton(new InvTweaksGuiSettingsButton(cfgManager, 0, 0, w, h, InvTweaksGuiSettingsButton.LABEL, "invtweaks.button.settings.tooltip"), -1, 0, false, addWidget);
            placeButton(new InvTweaksGuiSortingButton(cfgManager, 0, 0, w, h, InvTweaksGuiSortingButton.LABEL_DEFAULT, "invtweaks.button.chest1.tooltip", ContainerSection.INVENTORY, SortingMethod.INVENTORY, rowSize), -49, 0, true, addWidget);
            placeButton(new InvTweaksGuiSortingButton(cfgManager, 0, 0, w, h, InvTweaksGuiSortingButton.LABEL_ROWS, "invtweaks.button.chest3.tooltip", ContainerSection.INVENTORY_NOT_HOTBAR, SortingMethod.VERTICAL, rowSize), -25, 0, true, addWidget);
            placeButton(new InvTweaksGuiSortingButton(cfgManager, 0, 0, w, h, InvTweaksGuiSortingButton.LABEL_COLUMNS, "invtweaks.button.chest2.tooltip", ContainerSection.INVENTORY_NOT_HOTBAR, SortingMethod.HORIZONTAL, rowSize), -37, 0, true, addWidget);
            placeButton(new InvTweaksGuiSortingButton(cfgManager, 0, 0, w, h, InvTweaksGuiSortingButton.LABEL_NSHAPE, "invtweaks.button.nshape.tooltip", ContainerSection.INVENTORY_NOT_HOTBAR, SortingMethod.N_SHAPE, rowSize), -13, 0, true, addWidget);
        } else if(!isValidChest(container)) {
            // Inventory button
            placeButton(new InvTweaksGuiSettingsButton(cfgManager, 0, 0, w, h, InvTweaksGuiSettingsButton.LABEL, "invtweaks.button.settings.tooltip"), 0, 0, false, addWidget);
            if(container instanceof net.minecraft.world.inventory.InventoryMenu || container instanceof net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen.ItemPickerMenu) {
                // Sorting buttons of the player's own inventory screen (and the creative one), to the left of the settings button
                int rowSize = InvTweaksConst.INVENTORY_ROW_SIZE;
                // Stacked in a column on the right side of the panel
                placeSideButton(new InvTweaksGuiSortingButton(cfgManager, 0, 0, w, h, InvTweaksGuiSortingButton.LABEL_DEFAULT, "invtweaks.button.chest1.tooltip", ContainerSection.INVENTORY, SortingMethod.INVENTORY, rowSize), 0, addWidget);
                placeSideButton(new InvTweaksGuiSortingButton(cfgManager, 0, 0, w, h, InvTweaksGuiSortingButton.LABEL_COLUMNS, "invtweaks.button.chest2.tooltip", ContainerSection.INVENTORY_NOT_HOTBAR, SortingMethod.HORIZONTAL, rowSize), 12, addWidget);
                placeSideButton(new InvTweaksGuiSortingButton(cfgManager, 0, 0, w, h, InvTweaksGuiSortingButton.LABEL_ROWS, "invtweaks.button.chest3.tooltip", ContainerSection.INVENTORY_NOT_HOTBAR, SortingMethod.VERTICAL, rowSize), 24, addWidget);
                placeSideButton(new InvTweaksGuiSortingButton(cfgManager, 0, 0, w, h, InvTweaksGuiSortingButton.LABEL_NSHAPE, "invtweaks.button.nshape.tooltip", ContainerSection.INVENTORY_NOT_HOTBAR, SortingMethod.N_SHAPE, rowSize), 36, addWidget);
            }
        } else {
            // Reset sorting algorithm selector
            chestAlgorithmClickTimestamp = 0;

            boolean isChestWayTooBig = isLargeChest(container);
            int rowSize = getContainerRowSize(guiContainer);

            // Settings button
            placeButton(new InvTweaksGuiSettingsButton(cfgManager, 0, 0, w, h, InvTweaksGuiSettingsButton.LABEL, "invtweaks.button.settings.tooltip"), (isChestWayTooBig) ? 22 : -1, (isChestWayTooBig) ? -3 : 0, false, addWidget);

            // Sorting buttons
            placeButton(new InvTweaksGuiSortingButton(cfgManager, 0, 0, w, h, InvTweaksGuiSortingButton.LABEL_DEFAULT, "invtweaks.button.chest1.tooltip", ContainerSection.CHEST, SortingMethod.DEFAULT, rowSize), (isChestWayTooBig) ? 22 : -49, (isChestWayTooBig) ? 38 : 0, true, addWidget);
            // Rows and columns need a grid: at least one full row of chest slots
            if(rowSize > 0 && getContainerManager(container).getSize(ContainerSection.CHEST) >= rowSize) {
                placeButton(new InvTweaksGuiSortingButton(cfgManager, 0, 0, w, h, InvTweaksGuiSortingButton.LABEL_ROWS, "invtweaks.button.chest3.tooltip", ContainerSection.CHEST, SortingMethod.VERTICAL, rowSize), (isChestWayTooBig) ? 22 : -25, (isChestWayTooBig) ? 12 : 0, true, addWidget);
                placeButton(new InvTweaksGuiSortingButton(cfgManager, 0, 0, w, h, InvTweaksGuiSortingButton.LABEL_COLUMNS, "invtweaks.button.chest2.tooltip", ContainerSection.CHEST, SortingMethod.HORIZONTAL, rowSize), (isChestWayTooBig) ? 22 : -37, (isChestWayTooBig) ? 25 : 0, true, addWidget);
                placeButton(new InvTweaksGuiSortingButton(cfgManager, 0, 0, w, h, InvTweaksGuiSortingButton.LABEL_NSHAPE, "invtweaks.button.nshape.tooltip", ContainerSection.CHEST, SortingMethod.N_SHAPE, rowSize), (isChestWayTooBig) ? 22 : -13, (isChestWayTooBig) ? 51 : 0, true, addWidget);
            }

            // Transfer arrows, on the player inventory's label row: up = inventory to container, down = container to
            // inventory (the container is drawn above the inventory)
            InvTweaksGuiTransferButton toChest = new InvTweaksGuiTransferButton(cfgManager, 0, 0, w, h, InvTweaksGuiTransferButton.LABEL_TO_CONTAINER, "invtweaks.button.fill.tooltip", true);
            InvTweaksGuiTransferButton toInventory = new InvTweaksGuiTransferButton(cfgManager, 0, 0, w, h, InvTweaksGuiTransferButton.LABEL_TO_INVENTORY, "invtweaks.button.empty.tooltip", false);
            layoutButtons.add(new PlacedButton(toChest, -13, 0, false, true));
            addWidget.accept(toChest);
            layoutButtons.add(new PlacedButton(toInventory, -1, 0, false, true));
            addWidget.accept(toInventory);
        }

        handleGUILayout(guiContainer);
    }

    /**
     * A sorting button of the column outside the right edge of the panel, {@code dy} pixels below the panel's top.
     */
    private void placeSideButton(@NotNull InvTweaksGuiIconButton button, int dy, @NotNull Consumer<GuiEventListener> addWidget) {
        placeSideButton(button, dy + 4, PlacedButton.SIDE_PANEL, true, addWidget);
    }

    /**
     * A button of a column outside the right edge of the panel, {@code dy} pixels below the top of {@code side}'s anchor.
     */
    private void placeSideButton(@NotNull InvTweaksGuiIconButton button, int dy, int side, boolean sortingButton, @NotNull Consumer<GuiEventListener> addWidget) {
        // Bigger than the other buttons, and spaced to match (callers give 12 pixel steps)
        button.setWidth(SIDE_BUTTON_SIZE);
        button.setHeight(SIDE_BUTTON_SIZE);
        dy = dy / 12 * (SIDE_BUTTON_SIZE + 2) + dy % 12;
        layoutButtons.add(new PlacedButton(button, 2, dy, sortingButton, false, side));
        addWidget.accept(button);
    }

    private void placeButton(@NotNull InvTweaksGuiIconButton button, int dx, int dy, boolean sortingButton, @NotNull Consumer<GuiEventListener> addWidget) {
        layoutButtons.add(new PlacedButton(button, dx, dy, sortingButton));
        addWidget.accept(button);
    }

    /**
     * Forgets the buttons of the previous screen (they went away with it).
     */
    public void clearGUILayout() {
        layoutButtons.clear();
        layoutScreen = null;
    }

    /**
     * Lays out our buttons on each tick: the screen's position moves when the recipe book opens or closes, and the item
     * list of JEI can be toggled. Buttons are only shown while the menu allows them (the creative screen only on its
     * inventory tab), and the sorting buttons only if enabled in the settings. The buttons of the player's inventory go
     * above it; any of our buttons that would cover a widget of the screen move left together until they do not.
     */
    private void handleGUILayout(@NotNull Screen guiScreen) {
        if(guiScreen != layoutScreen) {
            clearGUILayout();
            return;
        }
        if(layoutButtons.isEmpty()) {
            return;
        }

        AbstractContainerScreen<?> guiContainer = layoutScreen;
        AbstractContainerMenu container = guiContainer.getMenu();
        @Nullable InvTweaksConfig config = cfgManager.getConfig();

        boolean visible = showButtons(container);
        boolean sortingButtonsVisible = visible && config != null && !config.getProperty(InvTweaksConfig.PROP_SHOW_CHEST_BUTTONS).equals("false");

        int x = guiContainer.getLeftPos() + guiContainer.getImageWidth() - 16, y = guiContainer.getTopPos() + 5;
        // Above the player's inventory (the label row), where a chest has its buttons above the chest
        int inventoryX = x, inventoryY = y;
        @Nullable List<Slot> mainInventory = getSlotMap(container).get(ContainerSection.INVENTORY_NOT_HOTBAR);
        if(mainInventory != null && !mainInventory.isEmpty()) {
            int inventoryRight = Integer.MIN_VALUE, inventoryTop = Integer.MAX_VALUE;
            for(Slot slot : mainInventory) {
                inventoryRight = Math.max(inventoryRight, slot.x + 16);
                inventoryTop = Math.min(inventoryTop, slot.y);
            }
            inventoryX = guiContainer.getLeftPos() + inventoryRight - 8;
            inventoryY = guiContainer.getTopPos() + inventoryTop - 13;
        }
        if(hasInventoryButtons(container)) {
            x = inventoryX;
            y = inventoryY;
        }
        // NotEnoughItems/JustEnoughItems compatibility
        if(isValidChest(container) && isLargeChest(container) && ItemListChecker.isItemListVisible()) {
            x -= 20;
            y += 50;
        }

        // Tops of the chest and of the player inventory, where their side columns start
        int chestTop = guiContainer.getTopPos() + 17, inventoryTop = guiContainer.getTopPos() + 84;
        for(ContainerSection section : new ContainerSection[]{ContainerSection.CHEST, ContainerSection.INVENTORY_NOT_HOTBAR}) {
            @Nullable List<Slot> slots = getSlotMap(container).get(section);
            if(slots != null && !slots.isEmpty()) {
                int top = Integer.MAX_VALUE;
                for(Slot slot : slots) {
                    top = Math.min(top, slot.y);
                }
                if(section == ContainerSection.CHEST) {
                    chestTop = guiContainer.getTopPos() + top - 1;
                } else {
                    inventoryTop = guiContainer.getTopPos() + top - 1;
                }
            }
        }

        // Buttons of the title line and of the inventory row each look for free space on their own
        List<InvTweaksGuiIconButton> shownButtons = new ArrayList<>(layoutButtons.size());
        List<InvTweaksGuiIconButton> shownRowButtons = new ArrayList<>(2);
        for(PlacedButton placed : layoutButtons) {
            InvTweaksGuiIconButton button = placed.button();
            if(placed.sideColumn()) {
                int anchor = placed.side() == PlacedButton.SIDE_CHEST ? chestTop : placed.side() == PlacedButton.SIDE_INVENTORY ? inventoryTop : guiContainer.getTopPos();
                button.setPosition(guiContainer.getLeftPos() + guiContainer.getImageWidth() + placed.dx(), anchor + placed.dy());
            } else if(placed.inventoryRow()) {
                button.setPosition(inventoryX + placed.dx(), inventoryY + placed.dy());
            } else {
                button.setPosition(x + placed.dx(), y + placed.dy());
            }
            button.visible = placed.sortingButton() ? sortingButtonsVisible : visible;
            if(button.visible && !placed.sideColumn()) {
                (placed.inventoryRow() ? shownRowButtons : shownButtons).add(button);
            }
        }

        int shift = getFreeSpaceShift(guiContainer, shownButtons);
        int rowShift = getFreeSpaceShift(guiContainer, shownRowButtons);
        for(PlacedButton placed : layoutButtons) {
            int s = placed.inventoryRow() ? rowShift : shift;
            if(s != 0 && !placed.sideColumn()) {
                placed.button().setX(placed.button().getX() + s);
            }
        }
    }

    /**
     * How far to the left our shown buttons must move, all together, so that they cover no other widget of the screen
     * (some screens have their own buttons where ours go).
     *
     * @return 0 or a negative shift; 0 too if there is no free space before the left edge of the screen
     */
    private static int getFreeSpaceShift(@NotNull Screen screen, @NotNull List<InvTweaksGuiIconButton> shownButtons) {
        if(shownButtons.isEmpty()) {
            return 0;
        }
        List<GuiEventListener> otherWidgets = new ArrayList<>();
        for(GuiEventListener widget : screen.children()) {
            if(!(widget instanceof InvTweaksGuiIconButton)) {
                otherWidgets.add(widget);
            }
        }
        if(otherWidgets.isEmpty()) {
            return 0;
        }

        int left = Integer.MAX_VALUE;
        for(InvTweaksGuiIconButton button : shownButtons) {
            left = Math.min(left, button.getX());
        }
        for(int shift = 0; left + shift >= 0; shift -= LAYOUT_SHIFT_STEP) {
            if(!coversAnyWidget(shownButtons, otherWidgets, shift)) {
                return shift;
            }
        }
        return 0;
    }

    /**
     * Whether a button, moved by {@code shift}, covers one of the widgets or comes closer to it than
     * {@link #LAYOUT_WIDGET_GAP} pixels on its sides.
     */
    private static boolean coversAnyWidget(@NotNull List<InvTweaksGuiIconButton> buttons, @NotNull List<GuiEventListener> widgets, int shift) {
        for(InvTweaksGuiIconButton button : buttons) {
            int left = button.getX() + shift - LAYOUT_WIDGET_GAP, right = button.getX() + shift + button.getWidth() + LAYOUT_WIDGET_GAP;
            int top = button.getY(), bottom = button.getY() + button.getHeight();
            for(GuiEventListener widget : widgets) {
                if(widget instanceof AbstractWidget vanillaWidget) {
                    if(vanillaWidget.visible && vanillaWidget.getX() < right && left < vanillaWidget.getX() + vanillaWidget.getWidth()
                            && vanillaWidget.getY() < bottom && top < vanillaWidget.getY() + vanillaWidget.getHeight()) {
                        return true;
                    }
                } else if(isOverArea(widget, left, top, right, bottom)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Other widgets may only tell whether a point is over them: they are probed on a grid of points of the area, its
     * edges included.
     */
    private static boolean isOverArea(@NotNull GuiEventListener widget, int left, int top, int right, int bottom) {
        for(int px = left; ; px = Math.min(px + LAYOUT_PROBE_STEP, right - 1)) {
            for(int py = top; ; py = Math.min(py + LAYOUT_PROBE_STEP, bottom - 1)) {
                if(widget.isMouseOver(px + 0.5D, py + 0.5D)) {
                    return true;
                }
                if(py >= bottom - 1) {
                    break;
                }
            }
            if(px >= right - 1) {
                break;
            }
        }
        return false;
    }

    /**
     * Runs the configured shortcut for a click on a container screen. Called before the screen handles the click.
     *
     * @return true if a shortcut handled the click, which the screen must then ignore
     */
    public boolean handleShortcuts(@NotNull AbstractContainerScreen<?> guiScreen, int button) {
        if(button != GLFW.GLFW_MOUSE_BUTTON_LEFT && button != GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
            return false;
        }

        synchronized(this) {
            if(isSpectatorOrNotInGame()) {
                return false;
            }

            // Check open GUI
            AbstractContainerMenu container = guiScreen.getMenu();
            if(!(isValidChest(container) || isValidInventory(container))) {
                return false;
            }

            @Nullable InvTweaksConfig config = cfgManager.getConfig();
            if(config == null || !config.getProperty(InvTweaksConfig.PROP_ENABLE_SHORTCUTS).equals(InvTweaksConfig.VALUE_TRUE)) {
                return false;
            }

            // Same conditions as the shortcut handler: a shortcut is held and the click is on a known slot with an
            // item in it or in the cursor.
            InvTweaksHandlerShortcuts shortcutsHandler = cfgManager.getShortcutsHandler();
            if(shortcutsHandler.computeCurrentShortcut() == null) {
                return false;
            }
            @Nullable Slot slot = getSlotAtMousePosition(guiScreen);
            if(slot == null || (!slot.hasItem() && getHeldStack().isEmpty())) {
                return false;
            }
            IContainerManager containerMgr = getContainerManager(container);
            int slotNumber = getSlotNumber(slot);
            if(containerMgr.getSlotSection(slotNumber) == null || containerMgr.getSlotIndex(slotNumber) == -1) {
                return false;
            }

            shortcutsHandler.handleShortcut();
            return true;
        }
    }

    private int getItemOrder(@NotNull ItemStack itemStack) {
        List<IItemTreeItem> items = cfgManager.getConfig().getTree().getItems(itemStack);
        return (!items.isEmpty()) ? items.get(0).getOrder() : Integer.MAX_VALUE;
    }

    /**
     * Polls the sort key mapping directly (key mappings are not updated while a screen is open). Compares the binding
     * type, since a mouse binding's value is a button number, not a key code.
     */
    private boolean isSortingShortcutDown() {
        if(sortKeyEnabled && !textboxMode) {
            InputConstants.Key key = InvTweaksClient.KEY_SORT.getKey();
            if(key.equals(InputConstants.UNKNOWN)) {
                return false;
            }
            com.mojang.blaze3d.platform.Window window = mc.getWindow();
            return switch(key.getType()) {
                case KEYSYM -> InputConstants.isKeyDown(window, key.getValue());
                case MOUSE -> GLFW.glfwGetMouseButton(window.handle(), key.getValue()) == GLFW.GLFW_PRESS;
                case SCANCODE -> false;
            };
        } else {
            return false;
        }
    }

    /**
     * Allows to maintain a clone of the hotbar contents to track changes (especially needed by the "on pickup"
     * features).
     */
    private void cloneHotbar() {
        NonNullList<ItemStack> mainInventory = getMainInventory();
        for(int i = 0; i < InvTweaksConst.INVENTORY_HOTBAR_SIZE; i++) {
            hotbarClone[i] = mainInventory.get(i).copy();
        }
    }

    private void playClick() {
        if(!cfgManager.getConfig().getProperty(InvTweaksConfig.PROP_ENABLE_SOUNDS).equals(InvTweaksConfig.VALUE_FALSE)) {
            mc.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        }
    }

}

package nadiendev.inventorytweaksrevived.container;

import nadiendev.inventorytweaksrevived.api.container.ContainerSection;
import nadiendev.inventorytweaksrevived.util.InvTweaksConst;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.BeaconMenu;
import net.minecraft.world.inventory.BlastFurnaceMenu;
import net.minecraft.world.inventory.BrewingStandMenu;
import net.minecraft.world.inventory.CartographyTableMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.CrafterMenu;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.DispenserMenu;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.world.inventory.HopperMenu;
import net.minecraft.world.inventory.HorseInventoryMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.LoomMenu;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.inventory.SmokerMenu;
import net.minecraft.world.inventory.StonecutterMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static nadiendev.inventorytweaksrevived.api.container.ContainerSection.*;
import static nadiendev.inventorytweaksrevived.util.InvTweaksConst.HOTBAR_SIZE;
import static nadiendev.inventorytweaksrevived.util.InvTweaksConst.INVENTORY_SIZE;

/**
 * Slot layouts of the vanilla menus, taken from the {@code addSlot} order of each 1.21.1 menu class.
 * <p>
 * Every layout is checked against the actual menu (slot count, and the player-inventory part must really hold slots of
 * the player's {@link Inventory}); a menu that does not match, e.g. because another mod changed it, falls back to
 * {@link #unknownMenuSlots}.
 */
final class VanillaSlotMaps {
    private static final int ROW_SIZE = InvTweaksConst.INVENTORY_ROW_SIZE;
    private static final int DISPENSER_ROW_SIZE = 3;    /**
     * Saddle and body armor slots, always present at the start of {@link HorseInventoryMenu}.
     */
    private static final int HORSE_EQUIPMENT_SLOTS = 2;
    private static final int HORSE_CHEST_ROWS = 3;
    private static final int HORSE_DEFAULT_ROW_SIZE = 5;

    private static final Map<Class<?>, MenuResolver.Rule> RULES = Map.ofEntries(
            // Result 0, crafting grid 1-4, armor 5-8, inventory 9-44, offhand 45
            Map.entry(InventoryMenu.class, inventoryType(true, 46, l -> l
                    .section(CRAFTING_OUT, 0, 1).section(CRAFTING_IN, 1, 5).playerSection(ARMOR, 5, 9).inventory(9))),
            // Result 0, crafting grid 1-9, inventory 10-45
            Map.entry(CraftingMenu.class, inventoryType(true, 46, l -> l
                    .section(CRAFTING_OUT, 0, 1).section(CRAFTING_IN, 1, 10).inventory(10))),
            // Ingredient 0, fuel 1, result 2, inventory 3-38 (AbstractFurnaceMenu)
            Map.entry(FurnaceMenu.class, inventoryType(true, 39, VanillaSlotMaps::furnace)),
            Map.entry(BlastFurnaceMenu.class, inventoryType(true, 39, VanillaSlotMaps::furnace)),
            Map.entry(SmokerMenu.class, inventoryType(true, 39, VanillaSlotMaps::furnace)),
            // Bottles 0-2, ingredient 3, blaze powder 4 (no section), inventory 5-40
            Map.entry(BrewingStandMenu.class, inventoryType(true, 41, l -> l
                    .section(BREWING_BOTTLES, 0, 3).section(BREWING_INGREDIENT, 3, 4).inventory(5))),
            // Item 0, lapis 1 (no section), inventory 2-37; no buttons, as in 1.12
            Map.entry(EnchantmentMenu.class, inventoryType(false, 38, l -> l
                    .section(ENCHANTMENT, 0, 1).inventory(2))),
            // Inputs 0-1, result 2, inventory 3-38 (ItemCombinerMenu)
            Map.entry(AnvilMenu.class, inventoryType(true, 39, l -> l
                    .section(CRAFTING_IN, 0, 2).section(CRAFTING_OUT, 2, 3).inventory(3))),
            // Template 0, base 1, addition 2, result 3, inventory 4-39 (ItemCombinerMenu)
            Map.entry(SmithingMenu.class, inventoryType(true, 40, l -> l
                    .section(CRAFTING_IN, 0, 3).section(CRAFTING_OUT, 3, 4).inventory(4))),
            // Map 0, additional 1, result 2, inventory 3-38
            Map.entry(CartographyTableMenu.class, inventoryType(true, 39, l -> l
                    .section(CRAFTING_IN, 0, 2).section(CRAFTING_OUT, 2, 3).inventory(3))),
            // Inputs 0-1, result 2, inventory 3-38
            Map.entry(GrindstoneMenu.class, inventoryType(true, 39, l -> l
                    .section(CRAFTING_IN, 0, 2).section(CRAFTING_OUT, 2, 3).inventory(3))),
            // Banner 0, dye 1, pattern 2, result 3, inventory 4-39
            Map.entry(LoomMenu.class, inventoryType(true, 40, l -> l
                    .section(CRAFTING_IN, 0, 3).section(CRAFTING_OUT, 3, 4).inventory(4))),
            // Input 0, result 1, inventory 2-37
            Map.entry(StonecutterMenu.class, inventoryType(true, 38, l -> l
                    .section(CRAFTING_IN, 0, 1).section(CRAFTING_OUT, 1, 2).inventory(2))),
            // Grid 0-8 (kept by the block), inventory 9-44, non-interactive result 45 (no section)
            Map.entry(CrafterMenu.class, inventoryType(true, 46, l -> l
                    .section(CRAFTING_IN_PERSISTENT, 0, 9).inventory(9))),
            // Payments 0-1, result 2, inventory 3-38; generic layout, as in 1.12
            Map.entry(MerchantMenu.class, inventoryType(true, 39, l -> l.section(CHEST, 0, 3).inventory(3))),
            // Hopper 0-4, inventory 5-40; generic layout, as in 1.12
            Map.entry(HopperMenu.class, inventoryType(true, 41, l -> l.section(CHEST, 0, 5).inventory(5))),
            // Payment 0, inventory 1-36; generic layout, as in 1.12
            Map.entry(BeaconMenu.class, inventoryType(true, 37, l -> l.section(CHEST, 0, 1).inventory(1))),
            // Rows * 9 container slots, then the inventory
            Map.entry(ChestMenu.class, chestType(ROW_SIZE, -1)),
            // 27 container slots, inventory 27-62
            Map.entry(ShulkerBoxMenu.class, chestType(ROW_SIZE, 63)),
            // 3x3 container slots, inventory 9-44
            Map.entry(DispenserMenu.class, chestType(DISPENSER_ROW_SIZE, 45)),
            Map.entry(HorseInventoryMenu.class, MenuResolver.Rule.cached(VanillaSlotMaps::horse)),
            // Its slots change with the selected creative tab, so it is never cached
            Map.entry(CreativeModeInventoryScreen.ItemPickerMenu.class, MenuResolver.Rule.uncached(VanillaSlotMaps::creative))
    );

    private VanillaSlotMaps() {
    }

    /**
     * @return the rule for exactly this vanilla menu class, or null if it is not a known vanilla menu
     */
    @Nullable
    static MenuResolver.Rule ruleFor(@NotNull Class<?> menuClass) {
        return RULES.get(menuClass);
    }

    /**
     * Layout of a menu Inventory Tweaks knows nothing about: slots of the player's {@link Inventory} (main part and
     * hotbar, in inventory order) are the inventory sections, every other slot is the chest. Armor and offhand slots of
     * the player are left out.
     */
    @NotNull
    static Map<ContainerSection, List<Slot>> unknownMenuSlots(@NotNull AbstractContainerMenu menu) {
        List<Slot> main = new ArrayList<>();
        List<Slot> hotbar = new ArrayList<>();
        List<Slot> other = new ArrayList<>();

        for(Slot slot : menu.slots) {
            if(slot.container instanceof Inventory) {
                int index = slot.getContainerSlot();
                if(index >= 0 && index < HOTBAR_SIZE) {
                    hotbar.add(slot);
                } else if(index >= HOTBAR_SIZE && index < INVENTORY_SIZE) {
                    main.add(slot);
                }
            } else {
                other.add(slot);
            }
        }

        Map<ContainerSection, List<Slot>> slotRefs = new EnumMap<>(ContainerSection.class);
        if(!other.isEmpty()) {
            slotRefs.put(CHEST, Collections.unmodifiableList(other));
        }
        if(main.size() == INVENTORY_SIZE - HOTBAR_SIZE && hotbar.size() == HOTBAR_SIZE) {
            Comparator<Slot> byInventoryIndex = Comparator.comparingInt(Slot::getContainerSlot);
            main.sort(byInventoryIndex);
            hotbar.sort(byInventoryIndex);
            List<Slot> inventory = new ArrayList<>(main);
            inventory.addAll(hotbar);
            slotRefs.put(INVENTORY, Collections.unmodifiableList(inventory));
            slotRefs.put(INVENTORY_NOT_HOTBAR, Collections.unmodifiableList(main));
            slotRefs.put(INVENTORY_HOTBAR, Collections.unmodifiableList(hotbar));
        }
        return Collections.unmodifiableMap(slotRefs);
    }

    private static void furnace(@NotNull Layout layout) {
        layout.section(FURNACE_IN, 0, 1).section(FURNACE_FUEL, 1, 2).section(FURNACE_OUT, 2, 3).inventory(3);
    }

    /**
     * A menu with crafting-like inputs (or nothing chest-like) next to the player's inventory.
     */
    @NotNull
    private static MenuResolver.Rule inventoryType(boolean showButtons, int expectedSize, @NotNull Consumer<Layout> layout) {
        return fixed(showButtons, true, false, ROW_SIZE, expectedSize, layout);
    }

    /**
     * A menu whose slots are a chest-like storage followed by the player's inventory.
     *
     * @param expectedSize total slot count, or -1 when it depends on the menu (chest rows)
     */
    @NotNull
    private static MenuResolver.Rule chestType(int rowSize, int expectedSize) {
        return fixed(true, false, true, rowSize, expectedSize, l -> l
                .section(CHEST, 0, l.size() - INVENTORY_SIZE).inventory(l.size() - INVENTORY_SIZE));
    }

    @NotNull
    private static MenuResolver.Rule fixed(boolean showButtons, boolean validInventory, boolean validChest, int rowSize,
                                           int expectedSize, @NotNull Consumer<Layout> layout) {
        return MenuResolver.Rule.cached(menu -> {
            Layout l = new Layout(menu.slots);
            if(expectedSize < 0 || menu.slots.size() == expectedSize) {
                layout.accept(l);
            } else {
                l.invalidate();
            }
            if(l.isValid()) {
                return new MenuInfo(showButtons, validInventory, validChest, false, rowSize, l.build());
            }
            Map<ContainerSection, List<Slot>> slotRefs = unknownMenuSlots(menu);
            return new MenuInfo(showButtons, validInventory && slotRefs.containsKey(INVENTORY),
                    validChest && MenuResolver.isChestSortable(slotRefs, false), false, rowSize, slotRefs);
        });
    }

    /**
     * Saddle 0, body armor 1, then {@code 3 * columns} chest slots only if the animal carries a chest (columns depend on
     * the animal: 5 for donkeys and mules, the strength for llamas), then the inventory.
     */
    @NotNull
    private static MenuInfo horse(@NotNull AbstractContainerMenu menu) {
        int size = menu.slots.size();
        int chestSlots = size - HORSE_EQUIPMENT_SLOTS - INVENTORY_SIZE;
        boolean hasChest = chestSlots > 0;

        Layout l = new Layout(menu.slots);
        if(hasChest) {
            l.section(CHEST, HORSE_EQUIPMENT_SLOTS, size - INVENTORY_SIZE);
        }
        l.inventory(size - INVENTORY_SIZE);
        if(!l.isValid()) {
            return MenuResolver.unknown(menu);
        }

        int rowSize = hasChest ? Math.max(1, chestSlots / HORSE_CHEST_ROWS) : HORSE_DEFAULT_ROW_SIZE;
        return new MenuInfo(hasChest, true, hasChest, false, rowSize, l.build());
    }

    /**
     * The creative menu is only sortable on the inventory tab. That tab replaces the menu's slots with wrappers of the
     * player's {@link InventoryMenu} slots, in the same order (armor 5-8, inventory 9-44), plus a trailing destroy slot.
     * The other tabs show 45 item-picker slots followed by the hotbar.
     */
    @NotNull
    private static MenuInfo creative(@NotNull AbstractContainerMenu menu) {
        boolean inventoryTab = Minecraft.getInstance().screen instanceof CreativeModeInventoryScreen screen
                && screen.getMenu() == menu && screen.isInventoryOpen();

        Layout l = new Layout(menu.slots);
        if(inventoryTab) {
            l.playerSection(ARMOR, 5, 9).inventory(9);
        } else {
            l.playerSection(INVENTORY_HOTBAR, l.size() - HOTBAR_SIZE, l.size());
        }

        boolean sortable = inventoryTab && l.isValid();
        return new MenuInfo(sortable, sortable, false, false, ROW_SIZE, l.isValid() ? l.build() : Map.of());
    }

    /**
     * Builds a slot map from index ranges of a menu's slot list, remembering whether every range fit.
     */
    private static final class Layout {
        private final List<Slot> slots;
        private final Map<ContainerSection, List<Slot>> sections = new EnumMap<>(ContainerSection.class);
        private boolean valid = true;

        private Layout(@NotNull List<Slot> slots) {
            this.slots = slots;
        }

        int size() {
            return slots.size();
        }

        boolean isValid() {
            return valid;
        }

        void invalidate() {
            valid = false;
        }

        /**
         * Slots {@code from} (inclusive) to {@code to} (exclusive) form {@code section}.
         */
        Layout section(@NotNull ContainerSection section, int from, int to) {
            if(from < 0 || to > slots.size() || from >= to) {
                valid = false;
            } else if(valid) {
                sections.put(section, List.copyOf(slots.subList(from, to)));
            }
            return this;
        }

        /**
         * Like {@link #section}, but every slot of the range must belong to the player's {@link Inventory}.
         */
        Layout playerSection(@NotNull ContainerSection section, int from, int to) {
            if(from < 0 || to > slots.size() || from >= to) {
                valid = false;
                return this;
            }
            for(int i = from; i < to; i++) {
                if(!(slots.get(i).container instanceof Inventory)) {
                    valid = false;
                    return this;
                }
            }
            return section(section, from, to);
        }

        /**
         * The 36 slots starting at {@code start} are the player's inventory: 27 main slots, then the hotbar.
         */
        Layout inventory(int start) {
            int end = start + INVENTORY_SIZE;
            return playerSection(INVENTORY, start, end)
                    .playerSection(INVENTORY_NOT_HOTBAR, start, end - HOTBAR_SIZE)
                    .playerSection(INVENTORY_HOTBAR, end - HOTBAR_SIZE, end);
        }

        Map<ContainerSection, List<Slot>> build() {
            return Collections.unmodifiableMap(sections);
        }
    }
}

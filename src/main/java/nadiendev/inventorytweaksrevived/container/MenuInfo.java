package nadiendev.inventorytweaksrevived.container;

import nadiendev.inventorytweaksrevived.api.container.ContainerSection;
import nadiendev.inventorytweaksrevived.util.InvTweaksConst;
import net.minecraft.world.inventory.Slot;

import java.util.List;
import java.util.Map;

/**
 * What Inventory Tweaks knows about a menu: which buttons to show, whether its player inventory and/or chest part can
 * be sorted, and which slots make up each {@link ContainerSection}.
 *
 * @param showButtons      whether the settings button (and the sorting buttons, for chests) is shown
 * @param validInventory   whether the player's inventory can be sorted while this menu is open
 * @param validChest       whether the menu has a chest-like storage part that can be sorted
 * @param largeChest       whether the chest is too big for the buttons to fit above it (buttons go down the side)
 * @param rowSize          number of slots per row of the chest part
 * @param inventoryButtons whether the sorting buttons are shown for the player's inventory (menus without a sortable
 *                         chest part, like storage network terminals)
 * @param slotMap          slots of each section, in sorting order
 */
public record MenuInfo(boolean showButtons, boolean validInventory, boolean validChest, boolean largeChest, int rowSize,
                       boolean inventoryButtons, Map<ContainerSection, List<Slot>> slotMap) {
    /**
     * Result for a missing menu: nothing is sortable and there are no sections.
     */
    static final MenuInfo NONE =new MenuInfo(false, false, false, false, InvTweaksConst.INVENTORY_ROW_SIZE, Map.of());

    /**
     * A menu without sorting buttons for the player's inventory.
     */
    public MenuInfo(boolean showButtons, boolean validInventory, boolean validChest, boolean largeChest, int rowSize,
                    Map<ContainerSection, List<Slot>> slotMap) {
        this(showButtons, validInventory, validChest, largeChest, rowSize, false, slotMap);
    }
}

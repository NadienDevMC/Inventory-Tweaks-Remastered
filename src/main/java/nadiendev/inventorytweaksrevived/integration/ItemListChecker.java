package nadiendev.inventorytweaksrevived.integration;

import net.neoforged.fml.ModList;

/**
 * Tells whether an item list mod is loaded, so the buttons of large chests are moved out of the way of its list.
 */
public final class ItemListChecker {

    private ItemListChecker() {
    }

    public static boolean isItemListVisible() {
        return ModList.get().isLoaded("jei");
    }
}

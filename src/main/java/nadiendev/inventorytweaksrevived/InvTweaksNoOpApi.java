package nadiendev.inventorytweaksrevived;

import nadiendev.inventorytweaksrevived.api.IItemTreeListener;
import nadiendev.inventorytweaksrevived.api.InventoryTweaksRevivedAPI;
import nadiendev.inventorytweaksrevived.api.SortingMethod;
import nadiendev.inventorytweaksrevived.api.container.ContainerSection;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * The API where there is nothing to sort: on a dedicated server, every call does nothing. Loads no client class.
 */
public class InvTweaksNoOpApi implements InventoryTweaksRevivedAPI {

    @Override
    public void addOnLoadListener(IItemTreeListener listener) {
    }

    @Override
    public boolean removeOnLoadListener(IItemTreeListener listener) {
        return false;
    }

    @Override
    public void setSortKeyEnabled(boolean enabled) {
    }

    @Override
    public void setTextboxMode(boolean enabled) {
    }

    @Override
    public int compareItems(@NotNull ItemStack i, @NotNull ItemStack j) {
        return 0;
    }

    @Override
    public int compareItems(@NotNull ItemStack i, @NotNull ItemStack j, boolean onlyTreeSort) {
        return 0;
    }

    @Override
    public void sort(ContainerSection section, SortingMethod method) {
    }
}

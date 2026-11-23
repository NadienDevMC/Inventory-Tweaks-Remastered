package nadiendev.inventorytweaksrevived.compat;

import nadiendev.inventorytweaksrevived.InventoryTweaksRevived;
import nadiendev.inventorytweaksrevived.core.InvTweaksCore;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;

/**
 * Sophisticated Storage / Backpacks support (client side). Sophisticated is not a compile dependency: its classes are
 * only reached by name, through public methods, so nothing here fails when it is not installed.
 * Used by {@code StorageScreenBaseMixin}, which hides Sophisticated's sort button and transfer arrows while our buttons
 * are shown on the screen.
 */
public final class SophisticatedCompat {
    private static final String MENU_BASE = "net.p3pp3rf1y.sophisticatedcore.common.gui.StorageContainerMenuBase";
    /**
     * Where the search box of the title line must end at most (from the right edge of the screen's image), so that it
     * leaves room for our sorting and settings buttons left of Sophisticated's "sort by" button.
     */
    private static final int TITLE_RESERVE = 80;
    private static boolean warned = false;

    private SophisticatedCompat() {
    }

    public static boolean isStorageMenu(@Nullable AbstractContainerMenu menu) {
        for(Class<?> c = menu == null ? null : menu.getClass(); c != null; c = c.getSuperclass()) {
            if(c.getName().equals(MENU_BASE)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Our transfer arrows replace Sophisticated's ones whenever our chest buttons are shown.
     */
    public static boolean replacesTransferButtons(Object screen) {
        return screen instanceof AbstractContainerScreen<?> s && InvTweaksCore.showsChestButtons(s.getMenu());
    }

    /**
     * Our sorting buttons replace Sophisticated's sort button when they are shown (not turned off in the settings).
     */
    public static boolean replacesSortButton(Object screen) {
        return screen instanceof AbstractContainerScreen<?> s && InvTweaksCore.showsChestSortingButtons(s.getMenu());
    }

    /**
     * Hides a Sophisticated widget ({@code WidgetBase.setVisible(false)}): it is neither drawn nor clickable.
     */
    public static void hideWidget(@Nullable Object widget) {
        if(widget == null) {
            return;
        }
        try {
            widget.getClass().getMethod("setVisible", boolean.class).invoke(widget, false);
        } catch(ReflectiveOperationException | RuntimeException e) {
            warn("Could not hide a Sophisticated button", e);
        }
    }

    /**
     * Ends the search box of the title line before our buttons.
     */
    public static int titleLineEnd(Object screen, int original) {
        if(replacesSortButton(screen)) {
            return Math.min(original, ((AbstractContainerScreen<?>) screen).getXSize() - TITLE_RESERVE);
        }
        return original;
    }

    /**
     * Asks Sophisticated's server side to transfer items, exactly as its own arrows do
     * ({@code StorageContainerMenuBase.transferItemsToStorage / transferItemsToPlayerInventory(filterByContents)}).
     *
     * @return false if the request could not be sent (the caller then moves the items itself)
     */
    public static boolean transfer(AbstractContainerMenu menu, boolean toContainer, boolean filterByContents) {
        try {
            Method method = menu.getClass().getMethod(toContainer ? "transferItemsToStorage" : "transferItemsToPlayerInventory", boolean.class);
            method.invoke(menu, filterByContents);
            return true;
        } catch(ReflectiveOperationException | RuntimeException e) {
            warn("Could not ask Sophisticated to transfer items, using shift-clicks", e);
            return false;
        }
    }

    private static void warn(String message, Exception e) {
        if(!warned) {
            warned = true;
            InventoryTweaksRevived.LOGGER.warn(message, e);
        }
    }
}

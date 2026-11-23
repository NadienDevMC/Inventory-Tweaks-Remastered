package nadiendev.inventorytweaksrevived.util;

import nadiendev.inventorytweaksrevived.api.container.ContainerSection;
import nadiendev.inventorytweaksrevived.container.MenuResolver;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.AbstractSignEditScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * Thin access layer over Minecraft's classes, so that the rest of the mod does not depend on their shape.
 *
 * @author Jimeo Wan
 */
public class InvTweaksObfuscation {

    public Minecraft mc;

    public InvTweaksObfuscation(Minecraft mc_) {
        mc = mc_;
    }

    // Minecraft members

    @Nullable
    public static String getNamespacedID(@Nullable String id) {
        if(id == null) {
            return null;
        } else if(id.indexOf(':') == -1) {
            return "minecraft:" + id;
        }
        return id;
    }

    public static boolean areItemStacksEqual(@NotNull ItemStack itemStack1, @NotNull ItemStack itemStack2) {
        return ItemStack.isSameItem(itemStack1, itemStack2) && itemStack1.getDamageValue() == itemStack2.getDamageValue() && itemStack1.getCount() == itemStack2.getCount();
    }

    @NotNull
    public static ItemStack getSlotStack(@NotNull AbstractContainerMenu menu, int i) {
        Slot slot = menu.slots.get(i);
        return (slot == null) ? ItemStack.EMPTY : slot.getItem();
    }

    public static int getSlotNumber(@NotNull Slot slot) {
        // The player-inventory tab of the creative screen wraps the player's menu slots in slots that never get an
        // index assigned; the wrapper's container slot is the index of the slot it stands for.
        if(isCreativeInventorySlot(slot)) {
            return slot.getContainerSlot();
        }
        return slot.index;
    }

    @Nullable
    public static Slot getSlotAtMousePosition(@Nullable AbstractContainerScreen<?> screen) {
        return screen != null ? screen.getHoveredSlot() : null;
    }

    // Menu members, resolved through MenuResolver (annotations, menu_compat.json, vanilla table, heuristic)

    public static int getSpecialChestRowSize(AbstractContainerMenu menu) {
        return MenuResolver.resolve(menu).rowSize();
    }

    public static boolean isValidChest(AbstractContainerMenu menu) {
        return MenuResolver.resolve(menu).validChest();
    }

    public static boolean isLargeChest(AbstractContainerMenu menu) {
        return MenuResolver.resolve(menu).largeChest();
    }

    public static boolean isValidInventory(AbstractContainerMenu menu) {
        return MenuResolver.resolve(menu).validInventory();
    }

    public static boolean showButtons(AbstractContainerMenu menu) {
        return MenuResolver.resolve(menu).showButtons();
    }

    public static boolean hasInventoryButtons(AbstractContainerMenu menu) {
        return MenuResolver.resolve(menu).inventoryButtons();
    }

    public static Map<ContainerSection, List<Slot>> getSlotMap(AbstractContainerMenu menu) {
        return MenuResolver.resolve(menu).slotMap();
    }

    // Screen / item type checks

    public static boolean isGuiInventoryCreative(@Nullable Object o) {
        return o != null && o.getClass().equals(CreativeModeInventoryScreen.class);
    }

    public static boolean isGuiInventory(@Nullable Object o) {
        return o != null && o.getClass().equals(InventoryScreen.class);
    }

    public static boolean isGuiEditSign(@Nullable Object o) {
        return o instanceof AbstractSignEditScreen;
    }

    public static boolean isBasicSlot(@Nullable Object o) {
        return o instanceof Slot slot && (slot.getClass().equals(Slot.class) || isCreativeInventorySlot(slot));
    }

    // Container members

    public static AbstractContainerMenu getCurrentContainer() {
        Minecraft mc = Minecraft.getInstance();
        if(mc.gui.screen() instanceof AbstractContainerScreen<?> containerScreen) {
            return containerScreen.getMenu();
        }
        return mc.player.inventoryMenu;
    }

    // Slot members

    public static boolean areSameItemType(@NotNull ItemStack itemStack1, @NotNull ItemStack itemStack2) {
        return !itemStack1.isEmpty() && !itemStack2.isEmpty() && ItemStack.isSameItem(itemStack1, itemStack2);
    }

    public static boolean areItemsStackable(@NotNull ItemStack itemStack1, @NotNull ItemStack itemStack2) {
        return !itemStack1.isEmpty() && !itemStack2.isEmpty() && itemStack1.isStackable() && ItemStack.isSameItemSameComponents(itemStack1, itemStack2);
    }

    /**
     * Slots of the creative screen's player-inventory tab are plain {@code Slot} subclasses (the package-private
     * {@code SlotWrapper}) that only exist while that tab is shown.
     */
    private static boolean isCreativeInventorySlot(@NotNull Slot slot) {
        return Minecraft.getInstance().gui.screen() instanceof CreativeModeInventoryScreen creativeScreen
                && creativeScreen.isInventoryOpen()
                && slot.getClass().getSuperclass() == Slot.class;
    }

    public void addChatMessage(@NotNull String message) {
        if(mc.gui != null) {
            mc.gui.hud.getChat().addClientSystemMessage(Component.literal(message));
        }
    }

    public Player getThePlayer() {
        return mc.player;
    }

    @Nullable
    public Screen getCurrentScreen() {
        return mc.gui.screen();
    }

    public Options getGameSettings() {
        return mc.options;
    }

    public Inventory getInventoryPlayer() {
        return getThePlayer().getInventory();
    }

    public NonNullList<ItemStack> getMainInventory() {
        return getInventoryPlayer().getNonEquipmentItems();
    }

    @NotNull
    public ItemStack getHeldStack() {
        return getThePlayer().containerMenu.getCarried();
    }

    @NotNull
    public ItemStack getFocusedStack() {
        return getInventoryPlayer().getSelectedItem();
    }

    public int getFocusedSlot() {
        return getInventoryPlayer().getSelectedSlot();
    }

    @NotNull
    public ItemStack getOffhandStack() {
        return getInventoryPlayer().getItem(Inventory.SLOT_OFFHAND);
    }
}

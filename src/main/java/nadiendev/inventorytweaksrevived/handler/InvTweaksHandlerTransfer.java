package nadiendev.inventorytweaksrevived.handler;

import nadiendev.inventorytweaksrevived.api.container.ContainerSection;
import nadiendev.inventorytweaksrevived.compat.SophisticatedCompat;
import nadiendev.inventorytweaksrevived.config.InvTweaksConfig;
import nadiendev.inventorytweaksrevived.config.InvTweaksConfigManager;
import nadiendev.inventorytweaksrevived.core.InvTweaksCore;
import nadiendev.inventorytweaksrevived.network.InvTweaksNetwork;
import nadiendev.inventorytweaksrevived.util.InvTweaksObfuscation;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackLinkedSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Transfer arrows of chest screens, modelled on the ones of Sophisticated Storage / Backpacks
 * ({@code TransferItemsPayload}):
 * <ul>
 * <li>fill (to the container): the stacks of the player's main inventory go into the container; a plain click only
 * moves the items the container already holds (same item and components), shift/ctrl moves all of them;</li>
 * <li>empty (to the inventory): the container's stacks go into the player's main inventory; a plain click only moves
 * the items the player already carries, shift/ctrl moves all of them.</li>
 * </ul>
 * The hotbar is left out (not taken from when filling, not filled when emptying) unless the "Transfer arrows use
 * hotbar" setting is on. For shift/ctrl, Sophisticated menus get Sophisticated's own transfer request whenever it does exactly that
 * (so its memory and filter settings apply as with its arrows); otherwise the items are moved with slot clicks, which
 * also works on servers without the mod.
 */
public final class InvTweaksHandlerTransfer {
    private InvTweaksHandlerTransfer() {
    }

    public static void transfer(@NotNull Minecraft mc, boolean toContainer, boolean all) {
        Player player = mc.player;
        if(player == null || player.isSpectator()) {
            return;
        }
        AbstractContainerMenu menu = player.containerMenu;
        boolean useHotbar = useHotbar();

        Map<ContainerSection, List<Slot>> slotMap = InvTweaksObfuscation.getSlotMap(menu);
        List<Slot> chest = slotMap.get(ContainerSection.CHEST);
        List<Slot> main = slotMap.get(ContainerSection.INVENTORY_NOT_HOTBAR);
        List<Slot> hotbar = slotMap.get(ContainerSection.INVENTORY_HOTBAR);
        List<Slot> inventory = slotMap.get(ContainerSection.INVENTORY);
        if(chest == null || main == null || inventory == null) {
            return;
        }
        boolean sophisticated = SophisticatedCompat.isStorageMenu(menu);
        boolean moved = false;

        if(toContainer) {
            List<Slot> source = new ArrayList<>(main);
            // Shift/ctrl: Sophisticated moves everything of the main inventory (never the hotbar) itself
            if(all && sophisticated && SophisticatedCompat.transfer(menu, true, false)) {
                source.clear();
            }
            if(useHotbar && hotbar != null) {
                source.addAll(hotbar);
            }
            Set<ItemStack> present = all ? null : stacksIn(chest);
            for(Slot slot : source) {
                if(matches(slot, present, player)) {
                    InvTweaksNetwork.clickSlot(menu.containerId, InvTweaksObfuscation.getSlotNumber(slot), 0, ContainerInput.QUICK_MOVE, player);
                    moved = true;
                    if(!all) {
                        break; // a plain click moves one stack
                    }
                }
            }
        } else {
            // Shift/ctrl: Sophisticated moves everything into the main inventory (not the hotbar) itself
            if(all && !useHotbar && sophisticated && SophisticatedCompat.transfer(menu, false, false)) {
                return;
            }
            Set<ItemStack> present = all ? null : stacksIn(inventory);
            List<Slot> source = new ArrayList<>();
            for(Slot slot : chest) {
                if(matches(slot, present, player)) {
                    source.add(slot);
                    if(!all) {
                        break; // a plain click moves one stack
                    }
                }
            }
            if(useHotbar) {
                // Shift-clicks send a container stack to the player's whole inventory
                for(Slot slot : source) {
                    InvTweaksNetwork.clickSlot(menu.containerId, InvTweaksObfuscation.getSlotNumber(slot), 0, ContainerInput.QUICK_MOVE, player);
                    moved = true;
                }
            } else {
                moved = emptyIntoMainInventory(menu, player, source, main, present, all ? 64 : 1);
            }
        }
        if(moved) {
            InvTweaksNetwork.sortComplete();
        }
    }

    private static boolean useHotbar() {
        InvTweaksConfigManager manager = InvTweaksCore.getConfigManager();
        InvTweaksConfig config = manager == null ? null : manager.getConfig();
        return config != null && InvTweaksConfig.VALUE_TRUE.equals(config.getProperty(InvTweaksConfig.PROP_TRANSFER_USE_HOTBAR));
    }

    @NotNull
    private static Set<ItemStack> stacksIn(@NotNull List<Slot> slots) {
        Set<ItemStack> present = ItemStackLinkedSet.createTypeAndComponentsSet();
        for(Slot slot : slots) {
            if(slot.hasItem()) {
                present.add(slot.getItem());
            }
        }
        return present;
    }

    private static boolean matches(@NotNull Slot slot, @Nullable Set<ItemStack> present, @NotNull Player player) {
        return slot.hasItem() && slot.mayPickup(player) && (present == null || present.contains(slot.getItem()));
    }

    /**
     * Moves container stacks into the main inventory only (not the hotbar), with pickup clicks: first onto stacks of
     * the same item, then into empty slots; what does not fit goes back.
     */
    private static boolean emptyIntoMainInventory(@NotNull AbstractContainerMenu menu, @NotNull Player player, @NotNull List<Slot> chest,
                                                  @NotNull List<Slot> main, @Nullable Set<ItemStack> present, int maxPickups) {
        if(!menu.getCarried().isEmpty()) {
            return false;
        }
        boolean moved = false;
        for(Slot slot : chest) {
            // Oversized stacks (storage stack upgrades) are picked up one normal stack at a time
            for(int attempt = 0; attempt < maxPickups && matches(slot, present, player) && hasRoom(main, slot.getItem()); attempt++) {
                int before = slot.getItem().getCount();
                click(menu, player, slot);
                if(menu.getCarried().isEmpty()) {
                    break;
                }
                for(Slot target : main) {
                    if(target.hasItem() && ItemStack.isSameItemSameComponents(target.getItem(), menu.getCarried())
                            && target.getItem().getCount() < target.getMaxStackSize(target.getItem())) {
                        click(menu, player, target);
                        if(menu.getCarried().isEmpty()) {
                            break;
                        }
                    }
                }
                for(Slot target : main) {
                    if(menu.getCarried().isEmpty()) {
                        break;
                    }
                    if(!target.hasItem() && target.mayPlace(menu.getCarried())) {
                        click(menu, player, target);
                    }
                }
                moved = true;
                if(!menu.getCarried().isEmpty()) {
                    click(menu, player, slot); // what did not fit goes back
                    break;
                }
                if(!slot.hasItem() || slot.getItem().getCount() >= before) {
                    break;
                }
            }
        }
        return moved;
    }

    private static boolean hasRoom(@NotNull List<Slot> main, @NotNull ItemStack stack) {
        for(Slot target : main) {
            if(!target.hasItem() || (ItemStack.isSameItemSameComponents(target.getItem(), stack)
                    && target.getItem().getCount() < target.getMaxStackSize(target.getItem()))) {
                return true;
            }
        }
        return false;
    }

    private static void click(@NotNull AbstractContainerMenu menu, @NotNull Player player, @NotNull Slot slot) {
        InvTweaksNetwork.clickSlot(menu.containerId, InvTweaksObfuscation.getSlotNumber(slot), 0, ContainerInput.PICKUP, player);
    }
}

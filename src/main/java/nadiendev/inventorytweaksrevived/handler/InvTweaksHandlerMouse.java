package nadiendev.inventorytweaksrevived.handler;

import com.mojang.blaze3d.platform.InputConstants;
import nadiendev.inventorytweaksrevived.config.InvTweaksConfig;
import nadiendev.inventorytweaksrevived.config.InvTweaksConfigManager;
import nadiendev.inventorytweaksrevived.core.InvTweaksCore;
import nadiendev.inventorytweaksrevived.network.InvTweaksNetwork;
import nadiendev.inventorytweaksrevived.util.InvTweaksObfuscation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.ScreenEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

/**
 * Mouse behaviours of Mouse Tweaks, written from scratch:
 * <ul>
 * <li>shift + left click drag: quick-moves every stack the cursor passes over;</li>
 * <li>left click drag while carrying a stack: collects the stacks of the same item the cursor passes over;</li>
 * <li>mouse wheel over a stack: scroll up moves one item of it to the other inventory (the container if it is in
 * the player's inventory, the player's inventory otherwise), scroll down brings one item of the same kind from the
 * other inventory into it.</li>
 * </ul>
 * Everything is done with slot clicks, so it works on any server. Disabled by the "Mouse Tweaks" setting; the
 * creative item picker is left alone.
 */
public final class InvTweaksHandlerMouse {
    private static final Set<Slot> visited = new HashSet<>();
    private static int dragButton = -1;

    private InvTweaksHandlerMouse() {
    }

    private static boolean enabled(@NotNull Screen screen) {
        if(!(screen instanceof AbstractContainerScreen<?>) || screen instanceof CreativeModeInventoryScreen) {
            return false;
        }
        InvTweaksConfigManager manager = InvTweaksCore.getConfigManager();
        InvTweaksConfig config = manager == null ? null : manager.getConfig();
        Player player = Minecraft.getInstance().player;
        return config != null && player != null && !player.isSpectator()
                && !InvTweaksConfig.VALUE_FALSE.equals(config.getProperty(InvTweaksConfig.PROP_MOUSE_TWEAKS))
                && player.containerMenu == ((AbstractContainerScreen<?>) screen).getMenu();
    }

    public static void onMousePressed(@NotNull ScreenEvent.MouseButtonPressed.Post event) {
        visited.clear();
        dragButton = event.getButton();
        if(event.getScreen() instanceof AbstractContainerScreen<?> screen && screen.getHoveredSlot() != null) {
            visited.add(screen.getHoveredSlot());
        }
    }

    public static void onMouseReleased(@NotNull ScreenEvent.MouseButtonReleased.Post event) {
        visited.clear();
        dragButton = -1;
    }

    public static void onMouseDragged(@NotNull ScreenEvent.MouseDragged.Pre event) {
        Screen current = event.getScreen();
        if(dragButton != InputConstants.MOUSE_BUTTON_LEFT || event.getMouseButton() != InputConstants.MOUSE_BUTTON_LEFT || !enabled(current)) {
            return;
        }
        AbstractContainerScreen<?> screen = (AbstractContainerScreen<?>) current;
        Player player = Minecraft.getInstance().player;
        AbstractContainerMenu menu = screen.getMenu();
        Slot hovered = screen.getHoveredSlot();
        ItemStack carried = menu.getCarried();
        boolean shift = Minecraft.getInstance().hasShiftDown();

        if(carried.isEmpty() && shift) {
            // Quick-move everything on the way (the vanilla screen does nothing on a drag without a carried stack)
            if(hovered != null && visited.add(hovered) && hovered.hasItem() && hovered.mayPickup(player)) {
                InvTweaksNetwork.clickSlot(menu.containerId, InvTweaksObfuscation.getSlotNumber(hovered), 0, ContainerInput.QUICK_MOVE, player);
            }
        } else if(!carried.isEmpty() && !shift && hovered != null && carried.getCount() < carried.getMaxStackSize()
                && hovered.hasItem() && hovered.mayPickup(player) && ItemStack.isSameItemSameComponents(hovered.getItem(), carried)) {
            // Collect the same items into the carried stack, instead of spreading it over the slots
            if(visited.add(hovered)) {
                InvTweaksNetwork.clickSlot(menu.containerId, InvTweaksObfuscation.getSlotNumber(hovered), 0, ContainerInput.PICKUP, player);
            }
            event.setCanceled(true);
        }
    }

    public static void onMouseScrolled(@NotNull ScreenEvent.MouseScrolled.Pre event) {
        Screen current = event.getScreen();
        if(!enabled(current) || event.getScrollDeltaY() == 0) {
            return;
        }
        AbstractContainerScreen<?> screen = (AbstractContainerScreen<?>) current;
        Slot hovered = screen.getHoveredSlot();
        Player player = Minecraft.getInstance().player;
        AbstractContainerMenu menu = screen.getMenu();
        if(hovered == null || !menu.getCarried().isEmpty() || !hovered.mayPickup(player)) {
            return;
        }

        boolean handled;
        if(event.getScrollDeltaY() > 0) {
            handled = hovered.hasItem() && pushOne(menu, player, hovered);
        } else {
            handled = pullOne(menu, player, hovered);
        }
        if(handled) {
            event.setCanceled(true);
        }
    }

    private static boolean isPlayerSlot(@NotNull Slot slot) {
        return slot.container instanceof Inventory;
    }

    private static boolean accepts(@NotNull Slot target, @NotNull ItemStack stack) {
        if(!target.mayPlace(stack)) {
            return false;
        }
        ItemStack inside = target.getItem();
        return inside.isEmpty() || (ItemStack.isSameItemSameComponents(inside, stack) && inside.getCount() < Math.min(inside.getMaxStackSize(), target.getMaxStackSize(inside)));
    }

    /**
     * One item of the hovered stack goes to the other inventory: onto a stack of the same item first, then an empty slot.
     */
    private static boolean pushOne(@NotNull AbstractContainerMenu menu, @NotNull Player player, @NotNull Slot from) {
        ItemStack stack = from.getItem();
        boolean fromPlayer = isPlayerSlot(from);
        Slot empty = null, target = null;
        for(Slot slot : menu.slots) {
            if(slot == from || isPlayerSlot(slot) == fromPlayer || !isRegular(slot) || !accepts(slot, stack)) {
                continue;
            }
            if(slot.hasItem()) {
                target = slot;
                break;
            } else if(empty == null) {
                empty = slot;
            }
        }
        if(target == null) {
            target = empty;
        }
        if(target == null) {
            return false;
        }
        moveOne(menu, player, from, target);
        return true;
    }

    /**
     * One item of the same kind, taken from the other inventory, goes into the hovered slot.
     */
    private static boolean pullOne(@NotNull AbstractContainerMenu menu, @NotNull Player player, @NotNull Slot into) {
        ItemStack inside = into.getItem();
        if(inside.isEmpty() || !accepts(into, inside)) {
            return false;
        }
        boolean intoPlayer = isPlayerSlot(into);
        for(Slot slot : menu.slots) {
            if(slot == into || isPlayerSlot(slot) == intoPlayer || !isRegular(slot) || !slot.hasItem() || !slot.mayPickup(player)
                    || !ItemStack.isSameItemSameComponents(slot.getItem(), inside)) {
                continue;
            }
            moveOne(menu, player, slot, into);
            return true;
        }
        return false;
    }

    /**
     * Result slots and the like (items cannot be put into them) are not part of the other inventory.
     */
    private static boolean isRegular(@NotNull Slot slot) {
        return slot.isActive() && slot.getMaxStackSize() > 0;
    }

    /**
     * Takes the whole source stack, drops one item in the target and puts the rest back.
     */
    private static void moveOne(@NotNull AbstractContainerMenu menu, @NotNull Player player, @NotNull Slot from, @NotNull Slot to) {
        int fromNumber = InvTweaksObfuscation.getSlotNumber(from), toNumber = InvTweaksObfuscation.getSlotNumber(to);
        InvTweaksNetwork.clickSlot(menu.containerId, fromNumber, 0, ContainerInput.PICKUP, player);
        InvTweaksNetwork.clickSlot(menu.containerId, toNumber, 1, ContainerInput.PICKUP, player);
        InvTweaksNetwork.clickSlot(menu.containerId, fromNumber, 0, ContainerInput.PICKUP, player);
        InvTweaksNetwork.sortComplete();
    }
}

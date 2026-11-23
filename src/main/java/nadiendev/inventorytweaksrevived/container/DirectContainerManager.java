package nadiendev.inventorytweaksrevived.container;

import nadiendev.inventorytweaksrevived.core.InvTweaksCore;
import nadiendev.inventorytweaksrevived.util.InvTweaksObfuscation;
import nadiendev.inventorytweaksrevived.api.container.ContainerSection;
import nadiendev.inventorytweaksrevived.network.InvTweaksNetwork;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * Allows to perform various operations on the inventory and/or containers. Works in both single and multiplayer.
 *
 * @author Jimeo Wan
 */
public class DirectContainerManager implements IContainerManager {

    // TODO: Throw errors when the container isn't available anymore

    @NotNull
    private final AbstractContainerMenu container;
    @NotNull
    private final Map<ContainerSection, List<Slot>> slotRefs;

    public DirectContainerManager(@NotNull AbstractContainerMenu cont) {
        container = cont;
        slotRefs = InvTweaksObfuscation.getSlotMap(container);
    }

    /**
     * Moves a stack from source to destination, adapting the behavior according to the context: - If destination is
     * empty, the source stack is moved. - If the items can be merged, as much items are possible are put in the
     * destination, and the eventual remains go back to the source. - If the items cannot be merged, they are swapped.
     *
     * @param srcSection  The source section
     * @param srcIndex    The source slot
     * @param destSection The destination section
     * @param destIndex   The destination slot
     * @return false if the source slot is empty or the player is holding an item that couln't be put down.
     */
    // TODO: Server helper directly implementing this as a swap without the need for intermediate slots.
    @Override
    public boolean move(ContainerSection srcSection, int srcIndex, ContainerSection destSection, int destIndex) {
        @NotNull ItemStack srcStack = getItemStack(srcSection, srcIndex);
        @NotNull ItemStack destStack = getItemStack(destSection, destIndex);

        if(srcStack.isEmpty() && destIndex != DROP_SLOT) {
            return false;
        } else if(srcSection == destSection && srcIndex == destIndex) {
            return true;
        }

        // Mod support -- some mods play tricks with slots to display an item but not let it be interacted with.
        // (Specifically forestry backpack UI)
        if(destIndex != DROP_SLOT) {
            @Nullable Slot destSlot = getSlot(destSection, destIndex);
            if(destSlot == null || !destSlot.mayPlace(srcStack)) {
                return false;
            }
        }


        // Put hold item down
        if(!InvTweaksCore.getInstance().getHeldStack().isEmpty()) {
            int firstEmptyIndex = getFirstEmptyIndex(ContainerSection.INVENTORY);
            if(firstEmptyIndex != -1) {
                leftClick(ContainerSection.INVENTORY, firstEmptyIndex);
            } else {
                return false;
            }
        }

        // Use intermediate slot if we have to swap tools, maps, etc.
        assert !srcStack.isEmpty();
        if(!destStack.isEmpty() && !InvTweaksObfuscation.areItemsStackable(srcStack, destStack)) {
            int intermediateSlot = getFirstEmptyUsableSlotNumber();
            @Nullable ContainerSection intermediateSection = getSlotSection(intermediateSlot);
            int intermediateIndex = getSlotIndex(intermediateSlot);
            if(intermediateIndex != -1) {
                @Nullable Slot interSlot = getSlot(intermediateSection, intermediateIndex);
                if(interSlot == null || !interSlot.mayPlace(destStack)) {
                    return false;
                }
                @Nullable Slot srcSlot = getSlot(srcSection, srcIndex);
                if(srcSlot == null || !srcSlot.mayPlace(destStack)) {
                    return false;
                }
                // Step 1/3: Dest > Int
                leftClick(destSection, destIndex);
                leftClick(intermediateSection, intermediateIndex);
                // Step 2/3: Src > Dest
                leftClick(srcSection, srcIndex);
                leftClick(destSection, destIndex);
                // Step 3/3: Int > Src
                leftClick(intermediateSection, intermediateIndex);
                leftClick(srcSection, srcIndex);
            } else {
                return false;
            }
        }

        // Normal move
        else {
            leftClick(srcSection, srcIndex);
            leftClick(destSection, destIndex);
            if(!InvTweaksCore.getInstance().getHeldStack().isEmpty()) {
                // Only return to original slot if it can be placed in that slot.
                // (Ex. crafting/furnace outputs)
                @Nullable Slot srcSlot = getSlot(srcSection, srcIndex);
                if(srcSlot != null && srcSlot.mayPlace(InvTweaksCore.getInstance().getHeldStack())) {
                    leftClick(srcSection, srcIndex);
                } else {
                    // If the item cannot be placed in its original slot, move to an empty slot.
                    int firstEmptyIndex = getFirstEmptyIndex(ContainerSection.INVENTORY);
                    if(firstEmptyIndex != -1) {
                        leftClick(ContainerSection.INVENTORY, firstEmptyIndex);
                    }
                    // else leave there because we have nowhere to put it.
                }
            }
        }

        return true;
    }

    /**
     * Moves some items from source to destination.
     *
     * @param srcSection  The source section
     * @param srcIndex    The source slot
     * @param destSection The destination section
     * @param destIndex   The destination slot
     * @param amount      The amount of items to move. If <= 0, does nothing. If > to the source stack size, moves as
     *                    much as possible from the stack size. If not all can be moved to the destination, only moves
     *                    as much as possible.
     * @return false if the destination slot is already occupied by a different item (meaning items cannot be moved to
     * destination).
     */
    // TODO: Server helper directly implementing this.
    @Override
    public boolean moveSome(ContainerSection srcSection, int srcIndex, ContainerSection destSection, int destIndex, int amount) {
        @NotNull ItemStack source = getItemStack(srcSection, srcIndex);
        if(source.isEmpty() || srcSection == destSection && srcIndex == destIndex) {
            return true;
        }

        @NotNull ItemStack destination = getItemStack(srcSection, srcIndex);
        int sourceSize = source.getCount();
        int movedAmount = Math.min(amount, sourceSize);

        if(destination.isEmpty() || InvTweaksObfuscation.areItemStacksEqual(source, destination)) {

            leftClick(srcSection, srcIndex);
            for(int i = 0; i < movedAmount; i++) {
                rightClick(destSection, destIndex);
            }
            if(movedAmount < sourceSize) {
                leftClick(srcSection, srcIndex);
            }
            return true;
        } else {
            return false;
        }

    }

    /**
     * If an item is in hand (= attached to the cursor), puts it down.
     *
     * @return true unless the item could not be put down
     */
    @Override
    public boolean putHoldItemDown(ContainerSection destSection, int destIndex) {
        @NotNull ItemStack heldStack = InvTweaksCore.getInstance().getHeldStack();
        if(!heldStack.isEmpty()) {
            if(getItemStack(destSection, destIndex).isEmpty()) {
                click(destSection, destIndex, false);
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public void click(ContainerSection section, int index, boolean rightClick) {
        // Click! (we finally call the Minecraft code)
        int slot = indexToSlot(section, index);
        if(slot != -1) {
            int data = (rightClick) ? 1 : 0;
            InvTweaksCore core = InvTweaksCore.getInstance();
            InvTweaksNetwork.clickSlot(container.containerId, slot, data, ContainerInput.PICKUP, core.getThePlayer());
        }
    }

    @Override
    public boolean hasSection(ContainerSection section) {
        return slotRefs.containsKey(section);
    }

    @Override
    public List<Slot> getSlots(ContainerSection section) {
        return slotRefs.get(section);
    }

    /**
     * @return The size of the whole container
     */
    @Override
    public int getSize() {
        int result = 0;
        for(@NotNull List<Slot> slots : slotRefs.values()) {
            result += slots.size();
        }
        return result;
    }

    /**
     * Returns the size of a section of the container.
     *
     * @return The size, or 0 if there is no such section.
     */
    @Override
    public int getSize(ContainerSection section) {
        if(hasSection(section)) {
            return slotRefs.get(section).size();
        } else {
            return 0;
        }
    }

    /**
     * @return -1 if no slot is free
     */
    @Override
    public int getFirstEmptyIndex(ContainerSection section) {
        List<Slot> slots = slotRefs.get(section);
        if(slots == null) {
            return -1;
        }
        int i = 0;
        for(@NotNull Slot slot : slots) {
            if(!slot.hasItem()) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * @return true if the specified slot exists and is empty, false otherwise.
     */
    @Override
    public boolean isSlotEmpty(ContainerSection section, int slot) {
        return hasSection(section) && getItemStack(section, slot).isEmpty();
    }

    @Nullable
    @Override
    public Slot getSlot(ContainerSection section, int index) {
        List<Slot> slots = slotRefs.get(section);
        if(slots != null && index >= 0 && index < slots.size()) {
            return slots.get(index);
        } else {
            return null;
        }
    }

    /**
     * @param preferInventory Set to true if you prefer to have the index according to the whole inventory, instead of a
     *                        more specific section (hotbar/not hotbar)
     * @return Full index of slot in the container
     */
    @Override
    public int getSlotIndex(int slotNumber, boolean preferInventory) {
        // TODO Caching with getSlotSection
        for(ContainerSection section : slotRefs.keySet()) {
            if(!preferInventory && section != ContainerSection.INVENTORY || (preferInventory && section != ContainerSection.INVENTORY_NOT_HOTBAR && section != ContainerSection.INVENTORY_HOTBAR)) {
                int i = 0;
                for(Slot slot : slotRefs.get(section)) {
                    if(InvTweaksObfuscation.getSlotNumber(slot) == slotNumber) {
                        return i;
                    }
                    i++;
                }
            }
        }
        return -1;
    }

    /**
     * Note: Prefers INVENTORY_HOTBAR/NOT_HOTBAR instead of INVENTORY.
     *
     * @return null if the slot number is invalid.
     */
    @Nullable
    @Override
    public ContainerSection getSlotSection(int slotNumber) {
        // TODO Caching with getSlotIndex
        for(ContainerSection section : slotRefs.keySet()) {
            if(section != ContainerSection.INVENTORY) {
                for(Slot slot : slotRefs.get(section)) {
                    if(InvTweaksObfuscation.getSlotNumber(slot) == slotNumber) {
                        return section;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns an ItemStack from the wanted section and slot.
     *
     * @return An ItemStack or an empty stack.
     */
    @NotNull
    @Override
    public ItemStack getItemStack(ContainerSection section, int index) {
        int slot = indexToSlot(section, index);
        if(slot >= 0 && slot < container.slots.size()) {
            return InvTweaksObfuscation.getSlotStack(container, slot);
        } else {
            return ItemStack.EMPTY;
        }
    }

    @NotNull
    @Override
    public AbstractContainerMenu getContainer() {
        return container;
    }

    private int getFirstEmptyUsableSlotNumber() {
        for(ContainerSection section : slotRefs.keySet()) {
            for(@NotNull Slot slot : slotRefs.get(section)) {
                // Use only standard slot (to make sure
                // we can freely put and remove items there)
                if(InvTweaksObfuscation.isBasicSlot(slot) && !slot.hasItem()) {
                    return InvTweaksObfuscation.getSlotNumber(slot);
                }
            }
        }
        return -1;
    }

    /**
     * Converts section/index values to slot ID.
     *
     * @return -1 if not found
     */
    private int indexToSlot(ContainerSection section, int index) {
        if(index == DROP_SLOT) {
            return DROP_SLOT;
        }
        @Nullable Slot slot = getSlot(section, index);
        return slot != null ? InvTweaksObfuscation.getSlotNumber(slot) : -1;
    }

    @Override
    public void applyChanges() {
        InvTweaksNetwork.sortComplete();
    }
}

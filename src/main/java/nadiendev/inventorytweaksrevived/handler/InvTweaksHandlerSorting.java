package nadiendev.inventorytweaksrevived.handler;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenCustomHashMap;
import nadiendev.inventorytweaksrevived.InventoryTweaksRevived;
import nadiendev.inventorytweaksrevived.api.IItemTreeItem;
import nadiendev.inventorytweaksrevived.api.SortingMethod;
import nadiendev.inventorytweaksrevived.api.container.ContainerSection;
import nadiendev.inventorytweaksrevived.config.InvTweaksConfig;
import nadiendev.inventorytweaksrevived.config.InvTweaksConfigSortingRule;
import nadiendev.inventorytweaksrevived.container.ContainerSectionManager;
import nadiendev.inventorytweaksrevived.container.IContainerManager;
import nadiendev.inventorytweaksrevived.core.InvTweaksCore;
import nadiendev.inventorytweaksrevived.itemtree.InvTweaksItemTree;
import nadiendev.inventorytweaksrevived.util.InvTweaksObfuscation;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackLinkedSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.*;
import java.util.Map.Entry;

/**
 * Core of the sorting behaviour. Allows to move items in a container (inventory or chest) with respect to the mod's
 * configuration.
 *
 * @author Jimeo Wan
 */
public class InvTweaksHandlerSorting extends InvTweaksObfuscation {
    private static final Logger log = InventoryTweaksRevived.LOGGER;
    private static final int MAX_CONTAINER_SIZE = 999;    @Nullable
    private static int[] DEFAULT_LOCK_PRIORITIES = null;
    @Nullable
    private static boolean[] DEFAULT_FROZEN_SLOTS = null;
    private ContainerSectionManager containerMgr;
    private SortingMethod algorithm;
    private int size;
    private boolean sortArmorParts;

    private InvTweaksItemTree tree;
    private List<InvTweaksConfigSortingRule> rules;
    private int[] rulePriority;
    private int[] keywordOrder;
    @Nullable
    private int[] lockPriorities;
    @Nullable
    private boolean[] frozenSlots;
    /**
     * Row/column sorting: columns of the grid (0 = not a line sort) and whether the lines are rows or columns.
     */
    private int lineRowSize = 0;
    private int lineShape = LINE_ROWS;
    private static final int LINE_ROWS = 0, LINE_COLUMNS = 1, LINE_N = 2;

    public InvTweaksHandlerSorting(Minecraft mc_, @NotNull InvTweaksConfig config, ContainerSection section, SortingMethod algorithm_, int rowSize) throws Exception {
        super(mc_);

        // Init constants

        if(DEFAULT_LOCK_PRIORITIES == null) {
            DEFAULT_LOCK_PRIORITIES = new int[MAX_CONTAINER_SIZE];
            for(int i = 0; i < MAX_CONTAINER_SIZE; i++) {
                DEFAULT_LOCK_PRIORITIES[i] = 0;
            }
        }
        if(DEFAULT_FROZEN_SLOTS == null) {
            DEFAULT_FROZEN_SLOTS = new boolean[MAX_CONTAINER_SIZE];
            for(int i = 0; i < MAX_CONTAINER_SIZE; i++) {
                DEFAULT_FROZEN_SLOTS[i] = false;
            }
        }

        // Init attributes

        containerMgr = new ContainerSectionManager(section);
        size = containerMgr.getSize();
        sortArmorParts = config.getProperty(InvTweaksConfig.PROP_ENABLE_AUTO_EQUIP_ARMOR).equals(InvTweaksConfig.VALUE_TRUE) && !isGuiInventoryCreative(getCurrentScreen()); // FIXME Armor parts disappear when sorting in creative mode while holding an item

        rules = config.getRules();
        tree = config.getTree();
        if(section == ContainerSection.INVENTORY) {
            lockPriorities = config.getLockPriorities();
            frozenSlots = config.getFrozenSlots();
            algorithm = SortingMethod.INVENTORY;
        } else {
            // The main inventory starts the inventory section, so the locked and frozen slots of the rules apply
            boolean mainInventory = section == ContainerSection.INVENTORY_NOT_HOTBAR;
            lockPriorities = mainInventory ? config.getLockPriorities() : DEFAULT_LOCK_PRIORITIES;
            frozenSlots = mainInventory ? config.getFrozenSlots() : DEFAULT_FROZEN_SLOTS;
            algorithm = algorithm_;
            if(algorithm == SortingMethod.VERTICAL || algorithm == SortingMethod.HORIZONTAL || algorithm == SortingMethod.N_SHAPE) {
                // Rows (VERTICAL, the rows button), columns (HORIZONTAL, the columns button) or N shape: laid out by sortLines()
                lineRowSize = rowSize;
                lineShape = algorithm == SortingMethod.VERTICAL ? LINE_ROWS : algorithm == SortingMethod.HORIZONTAL ? LINE_COLUMNS : LINE_N;
                rules = new ArrayList<>();
            }
        }
        frozenSlots = withUnmovableSlotsFrozen(frozenSlots);

        rulePriority = new int[size];
        keywordOrder = new int[size];
        for(int i = 0; i < size; i++) {
            rulePriority[i] = -1;
            @NotNull ItemStack stack = containerMgr.getItemStack(i);
            if(!stack.isEmpty()) {
                keywordOrder[i] = getItemOrder(stack);
            } else {
                keywordOrder[i] = -1;
            }
        }

        // Initialize rule priority for currently matching items
        rules.stream().filter(rule -> (rule.getContainerSize() == size && rule.getPreferredSlots() != null)).forEach(rule -> {
            int priority = rule.getPriority();
            for(int slot : rule.getPreferredSlots()) {
                @NotNull ItemStack stack = containerMgr.getItemStack(slot);
                if(!stack.isEmpty()) {
                    @NotNull List<IItemTreeItem> items = tree.getItems(stack);
                    if(rulePriority[slot] < priority && tree.matches(items, rule.getKeyword())) {
                        rulePriority[slot] = priority;
                    }
                }
            }
        });
    }

    /**
     * Slots whose item cannot be taken (like the slot of a held storage item that the menu locks) are never moved.
     * Only slots holding an item are checked: item handler slots (Sophisticated Storage, many modded chests) answer
     * {@code mayPickup == false} for every EMPTY slot, which froze all the free slots and made the sort do nothing.
     * Stacks bigger than their item's maximum (storage stack upgrades) are left where they are too, since a pickup
     * click only takes a normal stack of them.
     *
     * @return {@code frozen}, or a copy of it with those slots frozen too
     */
    private boolean[] withUnmovableSlotsFrozen(boolean[] frozen) {
        boolean[] result = frozen;
        if(getThePlayer() == null) {
            return result;
        }
        List<Slot> slots = containerMgr.getSlots();
        for(int i = 0; i < slots.size() && i < frozen.length; i++) {
            Slot slot = slots.get(i);
            if(!frozen[i] && slot.hasItem()
                    && (!slot.mayPickup(getThePlayer()) || slot.getItem().getCount() > slot.getItem().getMaxStackSize())) {
                if(result == frozen) {
                    result = frozen.clone();
                }
                result[i] = true;
            }
        }
        return result;
    }

    private static boolean canMergeStacks(@NotNull ItemStack from, @NotNull ItemStack to) {
        if(areItemsStackable(from, to)) {
            // We will not merge from a stack that exceeds its maximum size already, as these cannot be normally obtained.
            if(from.getCount() > from.getMaxStackSize()) {
                return false;
            }

            // If the destination stack has any room left, we can add to it.
            if(to.getCount() < to.getMaxStackSize()) {
                return true;
            }
        }
        return false;
    }

    public void sort() {
        // Nothing to sort for a spectator or without a player
        if(getThePlayer() == null || getThePlayer().isSpectator()) {
            return;
        }

        long timer = System.nanoTime();
        @NotNull IContainerManager globalContainer = InvTweaksCore.getCurrentContainerManager();

        // Put hold item down
        if(!getHeldStack().isEmpty()) {
            int emptySlot = globalContainer.getFirstEmptyIndex(ContainerSection.INVENTORY);
            if(emptySlot != -1) {
                globalContainer.putHoldItemDown(ContainerSection.INVENTORY, emptySlot);
            } else {
                return; // Not enough room to work, abort
            }
        }

        if(lineRowSize > 0 && size / lineRowSize > 0) {
            sortLines();
        } else {
            if(algorithm != SortingMethod.DEFAULT) {
                if(algorithm == SortingMethod.EVEN_STACKS) {
                    sortEvenStacks();
                } else if(algorithm == SortingMethod.INVENTORY) {
                    sortInventory(globalContainer);
                }
                sortWithRules();
            }

            //// Sort remaining
            defaultSorting();
        }

        if(log.isDebugEnabled()) {
            timer = System.nanoTime() - timer;
            log.debug("Sorting done in " + timer + "ns");
        }

        //// Put hold item down, just in case
        if(!getHeldStack().isEmpty()) {
            int emptySlot = globalContainer.getFirstEmptyIndex(ContainerSection.INVENTORY);
            if(emptySlot != -1) {
                globalContainer.putHoldItemDown(ContainerSection.INVENTORY, emptySlot);
            }
        }

        globalContainer.applyChanges();
    }

    private void sortWithRules() {
        //// Apply rules
        log.debug("Applying rules.");

        // Sorts rule by rule, themselves being already sorted by decreasing priority
        for(@NotNull InvTweaksConfigSortingRule rule : rules) {
            int priority = rule.getPriority();

            log.debug("Rule : {}({})", rule.getKeyword(), priority);

            // For every item in the inventory
            for(int i = 0; i < size; i++) {
                @NotNull ItemStack from = containerMgr.getItemStack(i);

                // If the rule is strong enough to move the item and it matches the item, move it
                if(hasToBeMoved(i, priority) && lockPriorities[i] < priority) {
                    @NotNull List<IItemTreeItem> fromItems = tree.getItems(from);
                    if(tree.matches(fromItems, rule.getKeyword())) {

                        // Test preferred slots
                        int[] preferredSlots = rule.getPreferredSlots();
                        int stackToMove = i;
                        for(int k : preferredSlots) {
                            // Move the stack!
                            int moveResult = move(stackToMove, k, priority);
                            if(moveResult != -1) {
                                if(moveResult == k) {
                                    break;
                                } else {
                                    from = containerMgr.getItemStack(moveResult);
                                    fromItems = tree.getItems(from);
                                    if(tree.matches(fromItems, rule.getKeyword())) {
                                        if(i >= moveResult) {
                                            // Current or already-processed slot.
                                            stackToMove = moveResult;
                                            //j = -1; // POSSIBLE INFINITE LOOP. But having this missing may cause sorting to take a few tries to stabilize in specific situations.
                                        } else {
                                            // The item will be processed later
                                            break;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //// Don't move locked stacks
        log.debug("Locking stacks.");

        for(int i = 0; i < size; i++) {
            if(hasToBeMoved(i, 1) && lockPriorities[i] > 0) {
                markAsMoved(i, 1);
            }
        }
    }

    private void sortInventory(@NotNull IContainerManager globalContainer) {
        //// Move items out of the crafting slots
        log.debug("Handling crafting slots.");
        if(globalContainer.hasSection(ContainerSection.CRAFTING_IN)) {
            List<Slot> craftingSlots = globalContainer.getSlots(ContainerSection.CRAFTING_IN);
            int emptyIndex = globalContainer.getFirstEmptyIndex(ContainerSection.INVENTORY);
            if(emptyIndex != -1) {
                for(@NotNull Slot craftingSlot : craftingSlots) {
                    if(craftingSlot.hasItem()) {
                        globalContainer.move(ContainerSection.CRAFTING_IN, globalContainer.getSlotIndex(getSlotNumber(craftingSlot)), ContainerSection.INVENTORY, emptyIndex);
                        emptyIndex = globalContainer.getFirstEmptyIndex(ContainerSection.INVENTORY);
                        if(emptyIndex == -1) {
                            break;
                        }
                    }
                }
            }
        }

        sortMergeArmor(globalContainer);
    }

    private void sortMergeArmor(@NotNull IContainerManager globalContainer) {
        //// Merge stacks to fill the ones in locked slots
        //// + Move armor parts to the armor slots
        log.debug("Merging stacks.");
        for(int i = size - 1; i >= 0; i--) {
            @NotNull ItemStack from = containerMgr.getItemStack(i);
            if(!from.isEmpty()) {
                // Move armor parts
                // Item
                @NotNull Item fromItem = from.getItem();
                if(from.getMaxDamage() > 0) {
                    moveArmor(globalContainer, i, from, fromItem);
                }
                // Stackable objects are never damageable
                else {
                    mergeItem(i, from);
                }
            }
        }
    }

    private void mergeItem(int i, @NotNull ItemStack from) {
        int j = 0;
        for(Integer lockPriority : lockPriorities) {
            if(lockPriority > 0) {
                @NotNull ItemStack to = containerMgr.getItemStack(j);
                if(!to.isEmpty() && areItemsStackable(from, to)) {
                    move(i, j, Integer.MAX_VALUE);
                    markAsNotMoved(j);
                    if(containerMgr.getItemStack(i).isEmpty()) {
                        break;
                    }
                }
            }
            j++;
        }
    }

    private void moveArmor(@NotNull IContainerManager globalContainer, int i, @NotNull ItemStack from, Item fromItem) {
        if(sortArmorParts) {
            if(fromItem instanceof ArmorItem fromItemArmor) {
                if(globalContainer.hasSection(ContainerSection.ARMOR)) {
                    List<Slot> armorSlots = globalContainer.getSlots(ContainerSection.ARMOR);
                    for(@NotNull Slot slot : armorSlots) {
                        boolean move = false;
                        if(!slot.hasItem()) {
                            move = true;
                        } else {
                            @NotNull Item currentArmor = slot.getItem().getItem();
                            if(currentArmor instanceof ArmorItem currentArmorItem) {
                                int armorLevel = currentArmorItem.getDefense();
                                if(armorLevel < fromItemArmor.getDefense() || (armorLevel == fromItemArmor.getDefense() && slot.getItem().getDamageValue() < from.getDamageValue())) {
                                    move = true;
                                }
                            } else {
                                move = true;
                            }
                        }
                        if(slot.mayPlace(from) && move) {
                            globalContainer.move(ContainerSection.INVENTORY, i, ContainerSection.ARMOR, globalContainer.getSlotIndex(getSlotNumber(slot)));
                        }
                    }
                }
            }
        }
    }

    private void sortEvenStacks() {
        log.debug("Distributing items.");

        //item and slot counts for each unique item (same item and components; the key is a one-item copy)
        @NotNull Map<ItemStack, int[]> itemCounts = new Object2ObjectLinkedOpenCustomHashMap<>(ItemStackLinkedSet.TYPE_AND_TAG);
        for(int i = 0; i < size; i++) {
            @NotNull ItemStack stack = containerMgr.getItemStack(i);
            if(!stack.isEmpty()) {
                int[] count = itemCounts.get(stack);
                if(count == null) {
                    @NotNull int[] newCount = {stack.getCount(), 1};
                    itemCounts.put(stack.copyWithCount(1), newCount);
                } else {
                    count[0] += stack.getCount(); //amount of item
                    count[1]++;                      //slots with item
                }
            }
        }

        //handle each unique item separately
        for(@NotNull Entry<ItemStack, int[]> entry : itemCounts.entrySet()) {
            ItemStack item = entry.getKey();
            int[] count = entry.getValue();
            int numPerSlot = count[0] / count[1];  //totalNumber/numberOfSlots

            //skip hacked itemstacks that are larger than their max size
            //no idea why they would be here, but may as well account for them anyway
            if(numPerSlot <= item.getMaxStackSize()) {
                //linkedlists to store which stacks have too many/few items
                @NotNull LinkedList<Integer> smallStacks = new LinkedList<>();
                @NotNull LinkedList<Integer> largeStacks = new LinkedList<>();
                for(int i = 0; i < size; i++) {
                    @NotNull ItemStack stack = containerMgr.getItemStack(i);
                    if(!stack.isEmpty() && ItemStack.isSameItemSameComponents(stack, item)) {
                        int stackSize = stack.getCount();
                        if(stackSize > numPerSlot) {
                            largeStacks.offer(i);
                        } else if(stackSize < numPerSlot) {
                            smallStacks.offer(i);
                        }
                    }
                }

                //move items from stacks with too many to those with too little
                while((!smallStacks.isEmpty())) {
                    int largeIndex = largeStacks.peek();
                    int largeSize = containerMgr.getItemStack(largeIndex).getCount();
                    int smallIndex = smallStacks.peek();
                    int smallSize = containerMgr.getItemStack(smallIndex).getCount();
                    containerMgr.moveSome(largeIndex, smallIndex, Math.min(numPerSlot - smallSize, largeSize - numPerSlot));

                    //update stack lists
                    largeSize = containerMgr.getItemStack(largeIndex).getCount();
                    smallSize = containerMgr.getItemStack(smallIndex).getCount();
                    if(largeSize == numPerSlot) {
                        largeStacks.remove();
                    }
                    if(smallSize == numPerSlot) {
                        smallStacks.remove();
                    }
                }

                //put all leftover into one stack for easy removal
                while(largeStacks.size() > 1) {
                    int largeIndex = largeStacks.poll();
                    int largeSize = containerMgr.getItemStack(largeIndex).getCount();
                    containerMgr.moveSome(largeIndex, largeStacks.peek(), largeSize - numPerSlot);
                }
            }
        }

        //mark all items as moved. (is there a better way?)
        for(int i = 0; i < size; i++) {
            markAsMoved(i, 1);
        }
    }

    private void defaultSorting() {
        log.debug("Default sorting.");

        @NotNull ArrayList<Integer> remaining = new ArrayList<>(), nextRemaining = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            if(hasToBeMoved(i, 1)) {
                remaining.add(i);
                nextRemaining.add(i);
            }
        }

        int iterations = 0;
        while(remaining.size() > 0 && iterations++ < 50) {
            for(int i : remaining) {
                if(hasToBeMoved(i, 1)) {
                    for(int j = 0; j < size; j++) {
                        if(move(i, j, 1) != -1) {
                            nextRemaining.remove((Integer) j);
                            break;
                        }
                    }
                } else {
                    nextRemaining.remove((Integer) i);
                }
            }
            remaining.clear();
            remaining.addAll(nextRemaining);
        }
    }

    private boolean canSwapSlots(int i, int j, int priority) {
        return lockPriorities[j] <= priority && (rulePriority[j] < priority || (rulePriority[j] == priority && isOrderedBefore(i, j)));
    }

    /**
     * Tries to move a stack from i to j, and swaps them if j is already occupied but i is of greater priority (even if
     * they are of same ID).
     *
     * @param i        from slot
     * @param j        to slot
     * @param priority The rule priority. Use 1 if the stack was not moved using a rule.
     * @return -1 if it failed, j if the stacks were merged into one, n if the j stack has been moved to the n slot.
     */
    private int move(int i, int j, int priority) {
        @NotNull ItemStack from = containerMgr.getItemStack(i), to = containerMgr.getItemStack(j);

        if(from.isEmpty() || frozenSlots[j] || frozenSlots[i]) {
            return -1;
        }

        if(lockPriorities[i] <= priority) {

            if(i == j) {
                markAsMoved(i, priority);
                return j;
            }

            // Move to empty slot
            if(to.isEmpty() && lockPriorities[j] <= priority && !frozenSlots[j]) {
                rulePriority[i] = -1;
                keywordOrder[i] = -1;
                rulePriority[j] = priority;
                keywordOrder[j] = getItemOrder(from);
                if(containerMgr.move(i, j)) {
                    return j;
                } else {
                    return -1;
                }
            }

            // Try to swap/merge
            else if(!to.isEmpty()) {
                if(canSwapSlots(i, j, priority) || canMergeStacks(from, to)) {
                    keywordOrder[j] = keywordOrder[i];
                    rulePriority[j] = priority;
                    rulePriority[i] = -1;
                    boolean success = containerMgr.move(i, j);

                    if(success) {
                        @NotNull ItemStack remains = containerMgr.getItemStack(i);

                        if(!remains.isEmpty()) {
                            int dropSlot = i;
                            if(lockPriorities[j] > lockPriorities[i]) {
                                for(int k = 0; k < size; k++) {
                                    if(containerMgr.getItemStack(k).isEmpty() && lockPriorities[k] == 0) {
                                        dropSlot = k;
                                        break;
                                    }
                                }
                            }
                            if(dropSlot != i) {
                                if(!containerMgr.move(i, dropSlot)) {
                                    // TODO: This is a potentially bad situation: One move succeeded, then the rest failed.
                                    return -1;
                                }
                            }
                            rulePriority[dropSlot] = -1;
                            keywordOrder[dropSlot] = getItemOrder(remains);
                            return dropSlot;
                        } else {
                            return j;
                        }
                    } else {
                        return -1;
                    }
                }
            }

        }

        return -1;
    }

    private void markAsMoved(int i, int priority) {
        rulePriority[i] = priority;
    }

    private void markAsNotMoved(int i) {
        rulePriority[i] = -1;
    }

    private boolean hasToBeMoved(int slot, int priority) {
        return !containerMgr.getItemStack(slot).isEmpty() && rulePriority[slot] <= priority;
    }

    private boolean isOrderedBefore(int i, int j) {
        @NotNull ItemStack iStack = containerMgr.getItemStack(i), jStack = containerMgr.getItemStack(j);

        return InvTweaksCore.getInstance().compareItems(iStack, jStack, keywordOrder[i], keywordOrder[j]) < 0;
    }

    private int getItemOrder(@NotNull ItemStack itemStack) {
        @NotNull List<IItemTreeItem> items = tree.getItems(itemStack);
        return (!items.isEmpty()) ? items.get(0).getOrder() : Integer.MAX_VALUE;
    }

    /**
     * One stack to lay out by the row/column sort.
     */
    private record LineEntry(int slot, @NotNull ItemStack stack, @NotNull String category, int order, @NotNull String id, int componentsHash) {
    }

    /**
     * Rows / columns / N shape sort (lines from {@link #linePaths}). Stacks are grouped by their innermost item tree category (all planks, all slabs, all logs...)
     * and ordered by the tree order inside a group. Each group fills one line (a row for the rows button, a column for
     * the columns button) and continues on the next lines if it does not fit; the next group starts on a new line as
     * long as that leaves enough free slots for every remaining stack, otherwise it continues right after the previous
     * one so nothing is left out. Locked/frozen slots keep their item and are skipped.
     */
    private void sortLines() {
        int rowSize = lineRowSize, rows = size / rowSize;

        boolean[] fixed = new boolean[size];
        for(int i = 0; i < size; i++) {
            fixed[i] = frozenSlots[i] || lockPriorities[i] > 0;
        }

        mergePartialStacks(fixed);

        // Stacks to place, sorted like the default sort, then grouped by category (groups in tree order too)
        List<LineEntry> entries = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            ItemStack stack = containerMgr.getItemStack(i);
            if(!fixed[i] && !stack.isEmpty()) {
                List<IItemTreeItem> items = tree.getItems(stack);
                String path = items.isEmpty() ? "" : items.get(0).getPath();
                int separator = path.lastIndexOf('\\');
                String category = separator >= 0 ? path.substring(0, separator) : path;
                int order = items.isEmpty() ? Integer.MAX_VALUE : items.get(0).getOrder();
                entries.add(new LineEntry(i, stack.copy(), category, order,
                        BuiltInRegistries.ITEM.getKey(stack.getItem()).toString(), stack.getComponentsPatch().hashCode()));
            }
        }
        if(entries.isEmpty()) {
            return;
        }
        Map<String, Integer> groupOrder = new HashMap<>();
        for(LineEntry e : entries) {
            groupOrder.merge(e.category(), e.order(), Math::min);
        }
        entries.sort(Comparator.<LineEntry>comparingInt(e -> groupOrder.get(e.category()))
                .thenComparing(LineEntry::category)
                .thenComparingInt(LineEntry::order)
                .thenComparing(LineEntry::id)
                .thenComparingInt(LineEntry::componentsHash)
                .thenComparing(Comparator.comparingInt((LineEntry e) -> e.stack().getCount()).reversed()));

        // Lines (strokes) of free slots, in filling order; a group can start a new one
        List<List<Integer>> lines = new ArrayList<>();
        for(List<Integer> path : linePaths(rowSize, rows)) {
            List<Integer> slots = new ArrayList<>(path.size());
            for(int slot : path) {
                if(!fixed[slot]) {
                    slots.add(slot);
                }
            }
            lines.add(slots);
        }

        int freeSlots = 0;
        for(List<Integer> slots : lines) {
            freeSlots += slots.size();
        }

        // Layout: target stack of each slot
        ItemStack[] target = new ItemStack[size];
        List<Integer> layoutOrder = new ArrayList<>(entries.size());
        int line = 0, position = 0, used = 0, remaining = entries.size();
        String currentCategory = null;
        for(LineEntry e : entries) {
            if(!e.category().equals(currentCategory)) {
                currentCategory = e.category();
                // New group: it continues right after the previous one, leaving no empty slot between groups
                // (the order of the groups is kept, only the gaps are gone)
            }
            while(line < lines.size() && position >= lines.get(line).size()) {
                line++;
                position = 0;
            }
            if(line >= lines.size()) {
                break; // cannot happen: there are at least as many free slots as stacks
            }
            int slot = lines.get(line).get(position++);
            target[slot] = e.stack();
            layoutOrder.add(slot);
            used++;
            remaining--;
        }

        // Move the stacks to their slots, in layout order; a slot is final once it holds its target
        boolean[] done = new boolean[size];
        for(int t : layoutOrder) {
            ItemStack want = target[t];
            if(sameStack(containerMgr.getItemStack(t), want)) {
                done[t] = true;
                continue;
            }
            int from = -1;
            for(int u = 0; u < size; u++) {
                if(u == t || done[u] || fixed[u]) {
                    continue;
                }
                ItemStack candidate = containerMgr.getItemStack(u);
                if(ItemStack.isSameItemSameComponents(candidate, want) && !candidate.isEmpty()) {
                    from = u;
                    if(candidate.getCount() == want.getCount()) {
                        break;
                    }
                }
            }
            if(from != -1 && containerMgr.move(from, t)) {
                done[t] = true;
            }
        }
    }

    /**
     * Every slot of the section exactly once, split in lines filled in order:
     * <ul>
     * <li>rows: each row left to right;</li>
     * <li>columns: each column top to bottom;</li>
     * <li>N shape: the first column top to bottom, the diagonal from the top-left corner down to the bottom-right one
     * (one cell per column, rows interpolated), the last column top to bottom, then every other slot row by row.</li>
     * </ul>
     * Slots past the last full row come last.
     */
    @NotNull
    private List<List<Integer>> linePaths(int rowSize, int rows) {
        List<List<Integer>> lines = new ArrayList<>();
        boolean[] used = new boolean[size];
        if(lineShape == LINE_N) {
            List<Integer> first = new ArrayList<>(), diagonal = new ArrayList<>(), last = new ArrayList<>();
            for(int r = 0; r < rows; r++) {
                first.add(r * rowSize);
            }
            for(int c = 1; c < rowSize - 1; c++) {
                int r = Math.round((float) c * (rows - 1) / (rowSize - 1));
                diagonal.add(r * rowSize + c);
            }
            if(rowSize > 1) {
                for(int r = 0; r < rows; r++) {
                    last.add(r * rowSize + rowSize - 1);
                }
            }
            List<Integer> rest = new ArrayList<>();
            for(List<Integer> stroke : List.of(first, diagonal, last)) {
                lines.add(stroke);
                stroke.forEach(slot -> used[slot] = true);
            }
            for(int slot = 0; slot < size; slot++) {
                if(!used[slot]) {
                    rest.add(slot);
                }
            }
            lines.add(rest);
            return lines;
        }
        boolean isRow = lineShape == LINE_ROWS;
        int lineCount = isRow ? rows : rowSize, lineLength = isRow ? rowSize : rows;
        for(int line = 0; line < lineCount; line++) {
            List<Integer> slots = new ArrayList<>(lineLength);
            for(int k = 0; k < lineLength; k++) {
                slots.add(isRow ? line * rowSize + k : k * rowSize + line);
            }
            lines.add(slots);
        }
        List<Integer> extra = new ArrayList<>();
        for(int slot = rows * rowSize; slot < size; slot++) {
            extra.add(slot);
        }
        lines.add(extra);
        return lines;
    }

    private static boolean sameStack(@NotNull ItemStack a, @NotNull ItemStack b) {
        return !a.isEmpty() && a.getCount() == b.getCount() && ItemStack.isSameItemSameComponents(a, b);
    }

    /**
     * Merges the partial stacks of a same item (same components), so each item has full stacks and at most one partial
     * stack before the row/column layout.
     */
    private void mergePartialStacks(boolean[] fixed) {
        for(int i = 0; i < size; i++) {
            ItemStack into = containerMgr.getItemStack(i);
            if(fixed[i] || into.isEmpty() || into.getCount() >= into.getMaxStackSize()) {
                continue;
            }
            for(int j = i + 1; j < size; j++) {
                ItemStack from = containerMgr.getItemStack(j);
                if(!fixed[j] && !from.isEmpty() && from.getCount() < from.getMaxStackSize() && areItemsStackable(into, from)) {
                    containerMgr.move(j, i);
                    into = containerMgr.getItemStack(i);
                    if(into.isEmpty() || into.getCount() >= into.getMaxStackSize()) {
                        break;
                    }
                }
            }
        }
    }

}

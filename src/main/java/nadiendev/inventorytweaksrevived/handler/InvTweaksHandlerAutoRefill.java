package nadiendev.inventorytweaksrevived.handler;

import nadiendev.inventorytweaksrevived.InventoryTweaksRevived;
import nadiendev.inventorytweaksrevived.api.IItemTreeItem;
import nadiendev.inventorytweaksrevived.api.container.ContainerSection;
import nadiendev.inventorytweaksrevived.config.InvTweaksConfig;
import nadiendev.inventorytweaksrevived.config.InvTweaksConfigSortingRule;
import nadiendev.inventorytweaksrevived.config.InvTweaksConfigSortingRuleType;
import nadiendev.inventorytweaksrevived.container.ContainerSectionManager;
import nadiendev.inventorytweaksrevived.core.InvTweaksCore;
import nadiendev.inventorytweaksrevived.itemtree.InvTweaksItemTree;
import nadiendev.inventorytweaksrevived.util.InvTweaksConst;
import nadiendev.inventorytweaksrevived.util.InvTweaksObfuscation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Handles the auto-refilling of the hotbar.
 *
 * @author Jimeo Wan
 */
public class InvTweaksHandlerAutoRefill extends InvTweaksObfuscation {

    @NotNull
    private final InvTweaksConfig config;

    public InvTweaksHandlerAutoRefill(Minecraft mc_, @NotNull InvTweaksConfig config_) {
        super(mc_);
        config = config_;
    }

    @NotNull
    private static String getItemId(@NotNull ItemStack stack) {
        return BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
    }

    /**
     * Auto-refill
     *
     * @param slot         The container index of the hotbar slot to refill
     * @param wantedId     The registry id ({@code namespace:path}) of the item that was in the slot
     * @param wantedDamage Ignored: items are matched by id, the damage of a tool does not tell items apart
     * @throws Exception
     */
    public void autoRefillSlot(int slot, @NotNull String wantedId, int wantedDamage) throws Exception {

        @NotNull ContainerSectionManager container = new ContainerSectionManager(ContainerSection.INVENTORY);
        @NotNull ItemStack candidateStack, replacementStack = ItemStack.EMPTY;
        int replacementStackSlot = -1;
        boolean refillBeforeBreak = config.getProperty(InvTweaksConfig.PROP_AUTO_REFILL_BEFORE_BREAK).equals(InvTweaksConfig.VALUE_TRUE);

        @NotNull List<InvTweaksConfigSortingRule> matchingRules = new ArrayList<>();
        List<InvTweaksConfigSortingRule> rules = config.getRules();
        InvTweaksItemTree tree = config.getTree();

        @Nullable Identifier wantedLocation = Identifier.tryParse(InvTweaksObfuscation.getNamespacedID(wantedId));

        // Check that the item is in the tree
        if(wantedLocation != null && !tree.isItemUnknown(wantedLocation)) {

            //// Search replacement

            @NotNull List<IItemTreeItem> items = tree.getItems(wantedId, wantedDamage);

            // Find rules that match the slot
            for(@NotNull IItemTreeItem item : items) {
                // Since we search a matching item using rules,
                // create a fake one that matches the exact item first
                matchingRules.add(new InvTweaksConfigSortingRule(tree, "D" + (slot - 26), item.getName(), InvTweaksConst.INVENTORY_SIZE, InvTweaksConst.INVENTORY_ROW_SIZE));
            }

            for(@NotNull InvTweaksConfigSortingRule rule : rules) {
                if(rule.getType() == InvTweaksConfigSortingRuleType.SLOT || rule.getType() == InvTweaksConfigSortingRuleType.COLUMN) {
                    for(int preferredSlot : rule.getPreferredSlots()) {
                        if(slot == preferredSlot) {
                            matchingRules.add(rule);
                            break;
                        }
                    }
                }
            }

            // Look only for a matching stack
            // First, look for the same item,
            // else one that matches the slot's rules
            for(@NotNull InvTweaksConfigSortingRule rule : matchingRules) {
                for(int i = 0; i < InvTweaksConst.INVENTORY_SIZE; i++) {
                    candidateStack = container.getItemStack(i);
                    if(!candidateStack.isEmpty()) {
                        @NotNull List<IItemTreeItem> candidateItems = tree.getItems(candidateStack);
                        if(tree.matches(candidateItems, rule.getKeyword())) {
                            // Choose tool of highest damage value
                            if(candidateStack.getMaxStackSize() == 1) {
                                // Item
                                if((replacementStack.isEmpty() || candidateStack.getDamageValue() > replacementStack.getDamageValue()) && (!refillBeforeBreak || candidateStack.getMaxDamage() - candidateStack.getDamageValue() > config.getIntProperty(InvTweaksConfig.PROP_AUTO_REFILL_DAMAGE_THRESHHOLD))) {
                                    replacementStack = candidateStack;
                                    replacementStackSlot = i;
                                }
                            }
                            // Choose stack of lowest size
                            else if(replacementStack.isEmpty() || candidateStack.getCount() < replacementStack.getCount()) {
                                replacementStack = candidateStack;
                                replacementStackSlot = i;
                            }
                        }
                    }
                }
            }
        }

        // If item is unknown, look for exact same item
        else {
            for(int i = 0; i < InvTweaksConst.INVENTORY_SIZE; i++) {
                candidateStack = container.getItemStack(i);
                if(!candidateStack.isEmpty() && Objects.equals(getItemId(candidateStack), wantedId)) {
                    replacementStack = candidateStack;
                    replacementStackSlot = i;
                    break;
                }
            }
        }

        //// Proceed to replacement

        if(!replacementStack.isEmpty() || (refillBeforeBreak && !container.getSlot(slot).getItem().isEmpty())) {

            InventoryTweaksRevived.LOGGER.info("Automatic stack replacement.");

            /*
             * This allows to have a short feedback
             * that the stack/tool is empty/broken.
             */
            InvTweaksCore.getInstance().addScheduledTask(new Runnable() {

                private ContainerSectionManager containerMgr;
                private int targetedSlot;
                private int i;
                @Nullable
                private String expectedItemId;
                private boolean refillBeforeBreak;

                @NotNull
                public Runnable init(int i_, int currentItem, boolean refillBeforeBreak_) throws Exception {
                    containerMgr = new ContainerSectionManager(ContainerSection.INVENTORY);
                    targetedSlot = currentItem;
                    if(i_ != -1) {
                        i = i_;
                        expectedItemId = getItemId(containerMgr.getItemStack(i));
                    } else {
                        i = containerMgr.getFirstEmptyIndex();
                        expectedItemId = null;
                    }
                    refillBeforeBreak = refillBeforeBreak_;
                    return this;
                }

                public void run() {
                    if(i < 0 || targetedSlot < 0) {
                        return;
                    }

                    // TODO: Look for better update detection now that this runs tick-based. It'll probably fail a bit if latency is > 50ms (1 tick)
                    // Since last tick, things might have changed
                    @NotNull ItemStack stack = containerMgr.getItemStack(i);

                    if(!stack.isEmpty() && StringUtils.equals(getItemId(stack), expectedItemId) || this.refillBeforeBreak) {
                        if(containerMgr.move(targetedSlot, i) || containerMgr.move(i, targetedSlot)) {
                            if(!config.getProperty(InvTweaksConfig.PROP_ENABLE_SOUNDS).equals(InvTweaksConfig.VALUE_FALSE)) {
                                mc.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.CHICKEN_EGG, 1.0F));
                            }
                            // If item are swapped (like for mushroom soups),
                            // put the item back in the inventory if it is in the hotbar
                            if(!containerMgr.getItemStack(i).isEmpty() && i >= 27) {
                                for(int j = 0; j < InvTweaksConst.INVENTORY_SIZE; j++) {
                                    if(containerMgr.getItemStack(j).isEmpty()) {
                                        containerMgr.move(i, j);
                                        break;
                                    }
                                }
                            }

                            // Make sure the inventory resyncs
                            containerMgr.applyChanges();
                        } else {
                            InventoryTweaksRevived.LOGGER.warn("Failed to move stack for autoreplace, despite of prior tests.");
                        }
                    }
                }

            }.init(replacementStackSlot, slot, refillBeforeBreak));

        }
    }

}

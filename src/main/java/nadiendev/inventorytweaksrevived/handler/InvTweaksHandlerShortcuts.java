package nadiendev.inventorytweaksrevived.handler;

import nadiendev.inventorytweaksrevived.core.InvTweaksCore;
import nadiendev.inventorytweaksrevived.config.InvTweaksConfig;
import nadiendev.inventorytweaksrevived.util.InvTweaksObfuscation;
import nadiendev.inventorytweaksrevived.api.container.ContainerSection;
import nadiendev.inventorytweaksrevived.container.IContainerManager;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.*;
import java.util.concurrent.TimeoutException;

/**
 * @author Jimeo Wan
 */
public class InvTweaksHandlerShortcuts extends InvTweaksObfuscation {
    private InvTweaksConfig config;
    private IContainerManager container;
    /**
     * Stores all pressed keys (only the one that are related to shortcuts)
     */
    private Map<Integer, Boolean> pressedKeys;
    /**
     * Stores the shortcuts mappings
     */
    private Map<InvTweaksShortcutType, List<InvTweaksShortcutMapping>> shortcuts;
    /**
     * Key codes of Minecraft's forward/back controls the Up/Down shortcuts were loaded with
     */
    private int loadedUpKeyCode, loadedDownKeyCode;

    public InvTweaksHandlerShortcuts(Minecraft mc_, InvTweaksConfig config_) {
        super(mc_);
        config = config_;
        pressedKeys = new HashMap<>();
        shortcuts = new HashMap<>();
    }

    public void loadShortcuts() {
        pressedKeys.clear();
        shortcuts.clear();

        // Register shortcut mappings
        @NotNull Map<String, String> keys = config.getProperties(InvTweaksConfig.PROP_SHORTCUT_PREFIX);
        for(String key : keys.keySet()) {
            @NotNull String[] keyMappings = keys.get(key).split("[ ]*,[ ]*");
            @Nullable InvTweaksShortcutType shortcutType = InvTweaksShortcutType.fromConfigKey(key);
            if(shortcutType != null) {
                for(@NotNull String keyMapping : keyMappings) {
                    @NotNull String[] keysToHold = keyMapping.split("\\+");
                    @NotNull InvTweaksShortcutMapping mapping = new InvTweaksShortcutMapping(keysToHold);
                    if(mapping.isValid()) {
                        registerShortcutMapping(shortcutType, mapping);
                    }
                }
            }
        }

        // Add Minecraft's Up & Down mappings
        loadedUpKeyCode = getKeyboardKeyCode(getGameSettings().keyUp);
        loadedDownKeyCode = getKeyboardKeyCode(getGameSettings().keyDown);

        if(loadedUpKeyCode > 0) {
            registerShortcutMapping(InvTweaksShortcutType.MOVE_UP, new InvTweaksShortcutMapping(loadedUpKeyCode));
        }
        if(loadedDownKeyCode > 0) {
            registerShortcutMapping(InvTweaksShortcutType.MOVE_DOWN, new InvTweaksShortcutMapping(loadedDownKeyCode));
        }

        // Add hotbar shortcuts (1-9) mappings
        for(int i = 0; i < 9; i++) {
            registerShortcutMapping(InvTweaksShortcutType.MOVE_TO_SPECIFIC_HOTBAR_SLOT, new InvTweaksShortcutMapping(GLFW.GLFW_KEY_1 + i));
        }
        for(int i = 0; i < 9; i++) {
            registerShortcutMapping(InvTweaksShortcutType.MOVE_TO_SPECIFIC_HOTBAR_SLOT, new InvTweaksShortcutMapping(GLFW.GLFW_KEY_KP_1 + i));
        }

        // Register (L/R)SHIFT to allow to filter them
        pressedKeys.put(GLFW.GLFW_KEY_LEFT_SHIFT, false);
        pressedKeys.put(GLFW.GLFW_KEY_RIGHT_SHIFT, false);
    }

    private void registerShortcutMapping(InvTweaksShortcutType type, @NotNull InvTweaksShortcutMapping mapping) {
        // Register shortcut
        if(shortcuts.containsKey(type)) {
            shortcuts.get(type).add(mapping);
        } else {
            @NotNull List<InvTweaksShortcutMapping> newMappingList = new LinkedList<>();
            newMappingList.add(mapping);
            shortcuts.put(type, newMappingList);
        }
        // Register key status listeners
        for(int keyCode : mapping.getKeyCodes()) {
            pressedKeys.put(keyCode, false);
        }
    }

    public void handleShortcut() {
        try {
            // Init shortcut
            @Nullable ShortcutConfig shortcutToTrigger = computeShortcutToTrigger();
            if(shortcutToTrigger != null) {
                // GO!
                runShortcut(shortcutToTrigger);
            }
        } catch(Exception e) {
            InvTweaksCore.logInGameErrorStatic("invtweaks.shortcut.error", e);
        }
    }

    @Nullable
    public ShortcutSpecification computeCurrentShortcut() {
        @NotNull ShortcutSpecification.Action action = ShortcutSpecification.Action.MOVE;
        @NotNull ShortcutSpecification.Target target = ShortcutSpecification.Target.UNSPECIFIED;
        @NotNull ShortcutSpecification.Scope scope = ShortcutSpecification.Scope.ONE_STACK;

        updatePressedKeys();

        boolean validAction = false;
        if(isShortcutDown(InvTweaksShortcutType.DROP) != null) {
            action = ShortcutSpecification.Action.DROP;
            validAction = true;
        }

        if(action != ShortcutSpecification.Action.DROP) {
            if(isShortcutDown(InvTweaksShortcutType.MOVE_TO_SPECIFIC_HOTBAR_SLOT) != null) {
                target = ShortcutSpecification.Target.HOTBAR_SLOT;
                validAction = true;
            } else if(isShortcutDown(InvTweaksShortcutType.MOVE_UP) != null) {
                target = ShortcutSpecification.Target.UP;
                validAction = true;
            } else if(isShortcutDown(InvTweaksShortcutType.MOVE_DOWN) != null) {
                target = ShortcutSpecification.Target.DOWN;
                validAction = true;
            }
        }

        if(isShortcutDown(InvTweaksShortcutType.MOVE_ALL_ITEMS) != null) {
            scope = ShortcutSpecification.Scope.ALL_ITEMS;
            validAction = true;
        } else if(isShortcutDown(InvTweaksShortcutType.MOVE_EVERYTHING) != null) {
            scope = ShortcutSpecification.Scope.EVERYTHING;
            validAction = true;
        } else if(isShortcutDown(InvTweaksShortcutType.MOVE_ONE_ITEM) != null) {
            scope = ShortcutSpecification.Scope.ONE_ITEM;
            validAction = true;
        }

        if(validAction) {
            return new ShortcutSpecification(action, target, scope);
        } else {
            return null;
        }
    }

    private ShortcutConfig computeShortcutToTrigger() {
        @Nullable ShortcutSpecification shortcut = computeCurrentShortcut();

        @NotNull ShortcutConfig shortcutConfig = new ShortcutConfig();

        container = InvTweaksCore.getCurrentContainerManager();
        @Nullable Slot slot = InvTweaksObfuscation.getSlotAtMousePosition(getCurrentContainerScreen());
        // If a valid and not empty slot is clicked
        if(shortcut != null && slot != null && (slot.hasItem() || !getHeldStack().isEmpty())) {
            int slotNumber = getSlotNumber(slot);

            // Set shortcut origin
            shortcutConfig.fromSection = container.getSlotSection(slotNumber);
            shortcutConfig.fromIndex = container.getSlotIndex(slotNumber);
            shortcutConfig.fromStack = slot.hasItem() ? slot.getItem().copy() : getHeldStack().copy();

            // Compute shortcut type
            // Ensure the item currently in the slot can be placed back into it for one-item shortcuts.
            if(!slot.mayPlace(slot.getItem()) && shortcut.getScope() == ShortcutSpecification.Scope.ONE_ITEM) {
                shortcut.setScope(ShortcutSpecification.Scope.ONE_STACK);
            }

            if(shortcutConfig.fromSection != null && shortcutConfig.fromIndex != -1) {
                if(shortcut.getAction() != ShortcutSpecification.Action.DROP) {
                    // Compute shortcut target
                    if(shortcut.getTarget() == ShortcutSpecification.Target.HOTBAR_SLOT) {
                        shortcutConfig.toSection = ContainerSection.INVENTORY_HOTBAR;
                        @Nullable InvTweaksShortcutMapping hotbarShortcut = isShortcutDown(InvTweaksShortcutType.MOVE_TO_SPECIFIC_HOTBAR_SLOT);
                        if(hotbarShortcut != null && !hotbarShortcut.getKeyCodes().isEmpty()) {
                            int hotbarKeyCode = hotbarShortcut.getKeyCodes().get(0);
                            if(hotbarKeyCode >= GLFW.GLFW_KEY_KP_1 && hotbarKeyCode <= GLFW.GLFW_KEY_KP_9) {
                                shortcutConfig.toIndex = hotbarKeyCode - GLFW.GLFW_KEY_KP_1;
                            } else {
                                shortcutConfig.toIndex = hotbarKeyCode - GLFW.GLFW_KEY_1;
                            }
                        }
                    } else {
                        // Compute targetable sections in order
                        @NotNull List<ContainerSection> orderedSections = new ArrayList<>();

                        // (Top part)
                        if(container.hasSection(ContainerSection.CHEST)) {
                            orderedSections.add(ContainerSection.CHEST);
                        } else if(container.hasSection(ContainerSection.CRAFTING_IN)) {
                            orderedSections.add(ContainerSection.CRAFTING_IN);
                        } else if(container.hasSection(ContainerSection.CRAFTING_IN_PERSISTENT)) {
                            orderedSections.add(ContainerSection.CRAFTING_IN_PERSISTENT);
                        } else if(container.hasSection(ContainerSection.FURNACE_IN)) {
                            orderedSections.add(ContainerSection.FURNACE_IN);
                        } else if(container.hasSection(ContainerSection.BREWING_INGREDIENT)) {
                            if(!shortcutConfig.fromStack.isEmpty()) {
                                if(shortcutConfig.fromStack.getItem() instanceof PotionItem) {
                                    orderedSections.add(ContainerSection.BREWING_BOTTLES);
                                } else {
                                    orderedSections.add(ContainerSection.BREWING_INGREDIENT);
                                }
                            }
                        } else if(container.hasSection(ContainerSection.ENCHANTMENT)) {
                            orderedSections.add(ContainerSection.ENCHANTMENT);
                        }

                        // (Inventory part)
                        orderedSections.add(ContainerSection.INVENTORY_NOT_HOTBAR);
                        orderedSections.add(ContainerSection.INVENTORY_HOTBAR);

                        // Choose target section
                        if(shortcut.getTarget() != ShortcutSpecification.Target.UNSPECIFIED) { // Explicit section (up/down shortcuts)
                            int sectionOffset = 0;
                            if(shortcut.getTarget() == ShortcutSpecification.Target.UP) {
                                sectionOffset--;
                            } else if(shortcut.getTarget() == ShortcutSpecification.Target.DOWN) {
                                sectionOffset++;
                            }
                            int fromSectionIndex = orderedSections.indexOf(shortcutConfig.fromSection);
                            if(fromSectionIndex != -1) {
                                shortcutConfig.toSection = orderedSections.get((orderedSections.size() + fromSectionIndex + sectionOffset) % orderedSections.size());
                            } else {
                                shortcutConfig.toSection = ContainerSection.INVENTORY;
                            }
                        } else { // Implicit section
                            switch(shortcutConfig.fromSection) {
                                case CHEST:
                                    shortcutConfig.toSection = ContainerSection.INVENTORY;
                                    break;
                                case INVENTORY_HOTBAR:
                                    if(orderedSections.contains(ContainerSection.CHEST)) {
                                        shortcutConfig.toSection = ContainerSection.CHEST;
                                    } else {
                                        shortcutConfig.toSection = ContainerSection.INVENTORY_NOT_HOTBAR;
                                    }
                                    break;
                                case CRAFTING_IN:
                                case CRAFTING_IN_PERSISTENT:
                                case FURNACE_IN:
                                    shortcutConfig.toSection = ContainerSection.INVENTORY_NOT_HOTBAR;
                                    break;
                                default:
                                    if(orderedSections.contains(ContainerSection.CHEST)) {
                                        shortcutConfig.toSection = ContainerSection.CHEST;
                                    } else {
                                        shortcutConfig.toSection = ContainerSection.INVENTORY_HOTBAR;
                                    }
                            }
                        }
                    }
                }

                // Shortcut modifiers
                shortcutConfig.forceEmptySlot = GLFW.glfwGetMouseButton(mc.getWindow().handle(), GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;
                shortcutConfig.action = shortcut.getAction();
                shortcutConfig.scope = shortcut.getScope();

                return shortcutConfig;
            }
        }

        return null;
    }

    // XXX Bad API
    public void updatePressedKeys() {
        if(haveControlsChanged()) {
            loadShortcuts(); // Reset mappings
        }
        for(int keyCode : pressedKeys.keySet()) {
            if(InvTweaksShortcutMapping.isKeyDown(keyCode)) {
                if(!pressedKeys.get(keyCode)) {
                    pressedKeys.put(keyCode, true);
                }
            } else {
                pressedKeys.put(keyCode, false);
            }
        }
    }

    /**
     * Checks if the Up/Down controls that are listened are outdated
     *
     * @return true if the shortuts listeners have to be reset
     */
    private boolean haveControlsChanged() {
        return loadedUpKeyCode != getKeyboardKeyCode(getGameSettings().keyUp) || loadedDownKeyCode != getKeyboardKeyCode(getGameSettings().keyDown);
    }

    /**
     * @return The GLFW key code of the key a control is bound to, or -1 if it is unbound or bound to a mouse button
     * (whose value is a button number, not a key code).
     */
    private static int getKeyboardKeyCode(@NotNull KeyMapping keyMapping) {
        InputConstants.Key key = keyMapping.getKey();
        return key.getType() == InputConstants.Type.KEYSYM ? key.getValue() : -1;
    }

    @Nullable
    private AbstractContainerScreen<?> getCurrentContainerScreen() {
        return getCurrentScreen() instanceof AbstractContainerScreen<?> containerScreen ? containerScreen : null;
    }

    private void runShortcut(@NotNull ShortcutConfig shortcut) throws TimeoutException {
        // Try to put held item down
        if(!getHeldStack().isEmpty()) {
            @Nullable Slot slot = InvTweaksObfuscation.getSlotAtMousePosition(getCurrentContainerScreen());
            if(slot != null) {
                int slotNumber = getSlotNumber(slot);
                container.putHoldItemDown(container.getSlotSection(slotNumber), container.getSlotIndex(slotNumber));
                if(!getHeldStack().isEmpty()) {
                    return;
                }
            } else {
                return;
            }
        }

        synchronized(this) {
            if(shortcut.toSection == ContainerSection.INVENTORY_HOTBAR && shortcut.toIndex != -1) {
                container.move(shortcut.fromSection, shortcut.fromIndex, shortcut.toSection, shortcut.toIndex);
            } else {
                switch(shortcut.action) {
                    case DROP: {
                        switch(shortcut.scope) {
                            case ONE_ITEM:
                                container.dropSome(shortcut.fromSection, shortcut.fromIndex, 1);
                                break;
                            case ONE_STACK:
                                container.drop(shortcut.fromSection, shortcut.fromIndex);
                                break;
                            case ALL_ITEMS:
                                dropAll(shortcut, shortcut.fromStack);
                                break;
                            case EVERYTHING:
                                dropAll(shortcut, ItemStack.EMPTY);
                                break;
                        }
                        break;
                    }
                    case MOVE: {
                        int toIndex;
                        boolean success;
                        int newIndex;

                        switch(shortcut.scope) {
                            case ONE_STACK: {
                                @Nullable Slot slot = container.getSlot(shortcut.fromSection, shortcut.fromIndex);
                                if(slot.hasItem()) {
                                    toIndex = getNextTargetIndex(shortcut, slot.getItem());
                                    if(shortcut.fromSection != ContainerSection.CRAFTING_OUT && shortcut.toSection != ContainerSection.ENCHANTMENT) {
                                        while(slot.hasItem() && toIndex != -1) {
                                            success = container.move(shortcut.fromSection, shortcut.fromIndex, shortcut.toSection, toIndex);
                                            newIndex = getNextTargetIndex(shortcut, slot.getItem());
                                            toIndex = (success || newIndex != toIndex) ? newIndex : -1; // Needed when we can't put items in the target slot
                                        }
                                    } else {
                                        // Move only once, since the crafting output might be refilled
                                        container.move(shortcut.fromSection, shortcut.fromIndex, shortcut.toSection, toIndex);
                                    }
                                }
                                break;

                            }

                            case ONE_ITEM: {
                                @Nullable Slot slot = container.getSlot(shortcut.fromSection, shortcut.fromIndex);
                                if(slot.hasItem()) {
                                    toIndex = getNextTargetIndex(shortcut, slot.getItem());
                                    container.moveSome(shortcut.fromSection, shortcut.fromIndex, shortcut.toSection, toIndex, 1);
                                }
                                break;
                            }

                            case ALL_ITEMS: {
                                moveAll(shortcut, shortcut.fromStack);
                                if(shortcut.fromSection == ContainerSection.INVENTORY_NOT_HOTBAR && shortcut.toSection == ContainerSection.CHEST) {
                                    shortcut.fromSection = ContainerSection.INVENTORY_HOTBAR;
                                    moveAll(shortcut, shortcut.fromStack);
                                }
                                break;
                            }

                            case EVERYTHING: {
                                moveAll(shortcut, ItemStack.EMPTY);
                                if(shortcut.fromSection == ContainerSection.INVENTORY_HOTBAR && shortcut.toSection == ContainerSection.CHEST) {
                                    shortcut.fromSection = ContainerSection.INVENTORY_HOTBAR;
                                    moveAll(shortcut, ItemStack.EMPTY);
                                }
                                break;
                            }
                        }
                    }
                }
            }

        }
    }

    private void dropAll(@NotNull ShortcutConfig shortcut, @NotNull ItemStack stackToMatch) {
        container.getSlots(shortcut.fromSection).stream().filter(slot -> slot.hasItem() && (stackToMatch.isEmpty() || areSameItemType(stackToMatch, slot.getItem()))).forEach(slot -> {
            int fromIndex = container.getSlotIndex(getSlotNumber(slot));
            while(slot.hasItem()) {
                container.drop(shortcut.fromSection, fromIndex);
            }
        });
    }

    private void moveAll(@NotNull ShortcutConfig shortcut, @NotNull ItemStack stackToMatch) {
        int toIndex = Integer.MIN_VALUE; // This will always get overwritten before being used, but -1 caused a rapid break.
        int newIndex;

        boolean success;

        for(@NotNull Slot slot : container.getSlots(shortcut.fromSection)) {
            if(slot.hasItem() && (stackToMatch.isEmpty() || areSameItemType(stackToMatch, slot.getItem()))) {
                int fromIndex = container.getSlotIndex(getSlotNumber(slot));
                toIndex = getNextTargetIndex(shortcut, slot.getItem());

                // Move while current slot has item, and there is a valid target that is not the same slot we're trying
                // to move from.
                while(slot.hasItem() && toIndex != -1 && !(shortcut.fromSection == shortcut.toSection && fromIndex == toIndex)) {
                    success = container.move(shortcut.fromSection, fromIndex, shortcut.toSection, toIndex);
                    newIndex = getNextTargetIndex(shortcut, slot.getItem());

                    // This can lead to an infinite loop, but represents some part of the process having gone wrong.
                    // So we want information on why.
                    if(success && newIndex == toIndex && slot.hasItem()) {
                        throw new RuntimeException("Inventory in invalid sate after move");
                    }

                    // Continue if movement succeeded or there is another slot to try.
                    if(success || (newIndex != toIndex)) {
                        toIndex = newIndex;
                    } else {
                        toIndex = -1;
                    }
                }
            }
            if(toIndex == -1) {
                break;
            }
        }
    }

    private int getNextTargetIndex(@NotNull ShortcutConfig shortcut, @NotNull ItemStack current) {
        int result = -1;

        // Try to merge with existing slot
        if(!shortcut.forceEmptySlot) {
            int i = 0;
            for(@NotNull Slot slot : container.getSlots(shortcut.toSection)) {
                if(slot.hasItem()) {
                    @NotNull ItemStack stack = slot.getItem();
                    if(InvTweaksObfuscation.areItemsStackable(current, stack) && stack.getCount() < stack.getMaxStackSize()) {
                        result = i;
                        break;
                    }
                }
                i++;
            }
        }

        // Else find empty slot
        if(result == -1) {
            result = container.getFirstEmptyIndex(shortcut.toSection);
        }

        // Switch from FURNACE_IN to FURNACE_FUEL if the slot is taken
        // TODO Better furnace shortcuts
        if(result == -1 && shortcut.toSection == ContainerSection.FURNACE_IN) {
            shortcut.toSection = ContainerSection.FURNACE_FUEL;
            result = container.getFirstEmptyIndex(shortcut.toSection);
        }

        return result;
    }

    /**
     * Checks if shortcut has been triggered
     *
     * @return The mapping that triggered the shortcut
     */
    @Nullable
    public InvTweaksShortcutMapping isShortcutDown(InvTweaksShortcutType type) {
        List<InvTweaksShortcutMapping> mappings = shortcuts.get(type);
        if(mappings != null) {
            for(@NotNull InvTweaksShortcutMapping mapping : mappings) {
                if(mapping.isTriggered(pressedKeys)) {
                    return mapping;
                }
            }
        }
        return null;
    }

    private static class ShortcutConfig {
        @NotNull
        public ShortcutSpecification.Action action = ShortcutSpecification.Action.MOVE;
        @NotNull
        public ShortcutSpecification.Scope scope = ShortcutSpecification.Scope.ONE_STACK;
        @Nullable
        public ContainerSection fromSection = null;
        public int fromIndex = -1;
        @NotNull
        public ItemStack fromStack = ItemStack.EMPTY;
        @Nullable
        public ContainerSection toSection = null;
        public int toIndex = -1;
        public boolean forceEmptySlot = false;
    }

}

package nadiendev.inventorytweaksrevived.data.lang;

import net.minecraft.data.PackOutput;

public class EnUsLangProvider extends InvTweaksLangProvider {
    public EnUsLangProvider(PackOutput output) {
        super(output, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("invtweaks.loadconfig.enabled", "'%s' enabled");
        add("invtweaks.loadconfig.refresh.error", "[1] Failed to refresh properties from file");
        add("invtweaks.loadconfig.invalidkeywords", "Invalid keywords found");
        add("invtweaks.loadconfig.filemissing", "missing, creating default one.");
        add("invtweaks.loadconfig.done", "Configuration loaded.");
        add("invtweaks.propsfile.loaded", "Mod properties loaded");
        add("invtweaks.propsfile.error", "[14] Failed to create the mod properties file");
        add("invtweaks.extract.find.error", "[15] The mod won't work, because %s could not be found!");
        add("invtweaks.extract.create.error", "[16] The mod won't work, because %s creation failed!");
        add("invtweaks.sort.inventory.error", "[2] Failed to sort inventory");
        add("invtweaks.sort.chest.error", "[3] Failed to sort container");
        add("invtweaks.sort.autorefill.error", "[4] Failed to trigger auto-refill");
        add("invtweaks.sort.releaseitem.error", "[5] Failed to release item");
        add("invtweaks.shortcut.error", "[6] Failed to trigger shortcut");
        add("invtweaks.pickup.error", "[7] Failed to move picked up stack");
        add("invtweaks.button.settings.tooltip", "Inventory settings");
        add("invtweaks.button.chest1.tooltip", "Default sorting");
        add("invtweaks.button.chest2.tooltip", "Sort in columns");
        add("invtweaks.button.chest3.tooltip", "Sort in rows");
        add("invtweaks.button.nshape.tooltip", "Sort in N shape");
        add("invtweaks.button.fill.tooltip", "Fill: click moves one stack of an item the container already has\nShift/Ctrl+click: move all");
        add("invtweaks.button.empty.tooltip", "Empty: click moves one stack of an item you already carry\nShift/Ctrl+click: move all");
        add("invtweaks.transfer.error", "[18] Failed to move items");
        add("invtweaks.settings.title", "Inventory and chests settings");
        add("invtweaks.settings.on", "ON");
        add("invtweaks.settings.off", "OFF");
        add("invtweaks.settings.key", "Sort key:");
        add("invtweaks.settings.disableci", "Disable CI");
        add("invtweaks.settings.disableci.tooltip", "In conflict with Convenient Inventory");
        add("invtweaks.settings.middleclick", "Middle click");
        add("invtweaks.settings.middleclick.tooltip", "Allows to sort using\nthe middle click");
        add("invtweaks.settings.chestbuttons", "Chest buttons");
        add("invtweaks.settings.chestbuttons.tooltip", "Adds three buttons\non chests to sort them");
        add("invtweaks.settings.shortcuts", "Shortcuts");
        add("invtweaks.settings.shortcuts.tooltip", "Enables various shortcuts\nto move items around");
        add("invtweaks.settings.autorefill", "Auto-refill");
        add("invtweaks.settings.autorefill.tooltip", "Replaces broken tools & emptied stacks\nwith others from your inventory");
        add("invtweaks.settings.beforebreak", "Repair-friendly refill");
        add("invtweaks.settings.beforebreak.tooltip", "Replaces tools just before they break");
        add("invtweaks.settings.moreoptions", "More options...");
        add("invtweaks.settings.moreoptions.tooltip", "Even more cool stuff");
        add("invtweaks.settings.rulesfile", "Open the sorting rules file...");
        add("invtweaks.settings.rulesfile.error", "[8] Failed to open rules file");
        add("invtweaks.settings.treefile", "Open the item tree file...");
        add("invtweaks.settings.treefile.error", "[9] Failed to open tree file");
        add("invtweaks.settings.moddedtreefile", "Browse modded tree files");
        add("invtweaks.settings.moddedtreefile.error", "[17] Failed to open Modded Tree Repository");
        add("invtweaks.settings.onlinehelp", "Open help in browser...");
        add("invtweaks.settings.onlinehelp.error", "[10] Failed to open help");
        add("invtweaks.help.bugsorting", "Not working?");
        add("invtweaks.help.bugsorting.pt1", "If your server seems to 'cancel' what this mod does,");
        add("invtweaks.help.bugsorting.pt2", "it's probably caused by it using the NoCheatPlus plugin.");
        add("invtweaks.help.bugsorting.pt3", "Ask your admin to disable the following options:");
        add("invtweaks.help.bugsorting.pt4", "\"inventory.fastclick\" and \"combined.improbable\"");
        add("invtweaks.settings.pvpwarning.pt1", "Note: On PvP servers, the auto-refill & auto-equip armor");
        add("invtweaks.settings.pvpwarning.pt2", "options are often considered cheating!");
        add("invtweaks.settings.exit", "Done");
        add("invtweaks.settings.advanced.sortonpickup", "Sort on pickup");
        add("invtweaks.settings.advanced.sortonpickup.soloonly", "SP only");
        add("invtweaks.settings.advanced.sortonpickup.tooltip", "Avoids to fill the hotbar with\neverything that you pickup");
        add("invtweaks.settings.advanced.sortonpickup.tooltip.soloonly", "Only in single player mode");
        add("invtweaks.settings.advanced.autoequip", "Auto-equip armor");
        add("invtweaks.settings.advanced.autoequip.tooltip", "Equips the best available\narmor when sorting");
        add("invtweaks.settings.advanced.sounds", "Enable sound");
        add("invtweaks.settings.advanced.sounds.tooltip", "Play sounds when sorting\nor when auto-refill is triggered");
        add("invtweaks.settings.advanced.mappingsfile", "Open the shortcuts mappings file...");
        add("invtweaks.settings.advanced.mappingsfile.error", "[11] Failed to open mappings file");
        add("invtweaks.settings.advanced.serverassist", "Server Item Movement");
        add("invtweaks.settings.advanced.serverassist.tooltip", "Enable using server assistance for item movement while sorting.");
        add("invtweaks.help.shortcuts.title", "Shortcuts help");
        add("invtweaks.help.shortcuts.click", "Click");
        add("invtweaks.help.shortcuts.or", "or");
        add("invtweaks.help.shortcuts.onestack", "Move one stack");
        add("invtweaks.help.shortcuts.oneitem", "Move one item");
        add("invtweaks.help.shortcuts.allitems", "Move all items of same type");
        add("invtweaks.help.shortcuts.everything", "Move everything");
        add("invtweaks.help.shortcuts.hotbar", "Move to hotbar");
        add("invtweaks.help.shortcuts.emptyslot", "Move to empty slot");
        add("invtweaks.help.shortcuts.rightclick", "(Any shortcut) + Right instead of left click");
        add("invtweaks.help.shortcuts.drop", "Drop");
        add("invtweaks.help.shortcuts.craftall", "Craft all to inventory");
        add("invtweaks.help.shortcuts.craftone", "Craft to inventory");
        add("invtweaks.help.shortcuts.selectconfig", "Select sorting configuration");
        add("invtweaks.help.shortcuts.forward", "(Forward)");
        add("invtweaks.help.shortcuts.backward", "(Backwards)");
        add("invtweaks.keyboardfix.error", "[12] I'm having troubles with the keyboard");
        add("invtweaks.keyboardfix.recover", "Ok it's repaired, sorry about that.");
        add("invtweaks.modcompat.ci.error", "[13] Failed to manage 'Convenient Inventory' compatibility");
        add("key.category.inventorytweaksrevived.invtweaks", "Inventory Tweaks");
        add("invtweaks.key.sort", "Inventory Sorting");
        add("invtweaks.settings.advanced.transferhotbar", "Arrows use hotbar");
        add("invtweaks.settings.advanced.transferhotbar.tooltip", "Fill also takes items from the hotbar,\nand empty also puts items into it");
        add("invtweaks.settings.advanced.mousetweaks", "Mouse Tweaks");
        add("invtweaks.settings.advanced.mousetweaks.tooltip", "Shift+drag quick-moves every stack, dragging with a stack collects the same items,\nand the mouse wheel moves one item between the inventories");
        add("invtweaks.settings.displaytooltip", "Display Path Tooltip");
        add("invtweaks.settings.displaytooltip.tooltip", "Show the Item Tree XPaths\nthat apply to the item in\nthe tool tip.\n(Handy for JEI filtering.)");
    }
}

package nadiendev.inventorytweaksrevived.gui;

import nadiendev.inventorytweaksrevived.config.InvTweaksConfig;
import nadiendev.inventorytweaksrevived.util.InvTweaksConst;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

/**
 * The inventory and chest settings menu.
 *
 * @author Jimeo Wan
 */
public class InvTweaksGuiSettings extends InvTweaksGuiSettingsAbstract {

    public InvTweaksGuiSettings(@Nullable Screen parentScreen_) {
        super(parentScreen_);
    }

    @Override
    protected void init() {
        super.init();

        int i = 0;

        // Create large buttons

        int largeX = buttonX(1) + 55;
        addButton(largeX, height / 6 + 96, 200, Component.translatable("invtweaks.settings.rulesfile"), null,
                button -> openFile(InvTweaksConst.CONFIG_RULES_FILE, "invtweaks.settings.rulesfile.error"));
        addButton(largeX, height / 6 + 144, 200, Component.translatable("invtweaks.settings.onlinehelp"), null,
                button -> openUri(InvTweaksConst.HELP_URL, "invtweaks.settings.onlinehelp.error"));

        addButton(buttonX(11), buttonY(11), 150, Component.translatable("invtweaks.settings.treefile"), null,
                button -> openFile(InvTweaksConst.CONFIG_TREE_FILE, "invtweaks.settings.treefile.error"));
        addButton(buttonX(10), buttonY(10), 150, Component.translatable("invtweaks.settings.moddedtreefile"), null,
                button -> openUri(InvTweaksConst.TREE_URL, "invtweaks.settings.moddedtreefile.error"));

        // Create settings buttons

        int x = buttonX(i);
        int y = buttonY(i++);
        addButton(x + 130, y, 20, Component.literal("?"), Component.translatable("invtweaks.help.shortcuts.title"),
                button -> minecraft.setScreenAndShow(new InvTweaksGuiShortcutsHelp(this)));
        addBooleanButton(x, y, 130, InvTweaksConfig.PROP_ENABLE_SHORTCUTS, "invtweaks.settings.shortcuts", "invtweaks.settings.shortcuts.tooltip");

        addBooleanButton(buttonX(i), buttonY(i++), 150, InvTweaksConfig.PROP_AUTO_REFILL_BEFORE_BREAK, "invtweaks.settings.beforebreak", "invtweaks.settings.beforebreak.tooltip");

        addBooleanButton(buttonX(i), buttonY(i++), 150, InvTweaksConfig.PROP_ENABLE_AUTO_REFILL, "invtweaks.settings.autorefill", "invtweaks.settings.autorefill.tooltip");

        // The advanced screen goes back to the main settings screen, which in turn goes back to the original parent
        addButton(buttonX(i), buttonY(i++), 150, Component.translatable("invtweaks.settings.moreoptions"), Component.translatable("invtweaks.settings.moreoptions.tooltip"),
                button -> minecraft.setScreenAndShow(new InvTweaksGuiSettingsAdvanced(parentScreen)));

        addButton(5, height - 20, 100, Component.translatable("invtweaks.help.bugsorting"), null,
                button -> minecraft.setScreenAndShow(new InvTweaksGuiModNotWorking(parentScreen)));

        addBooleanButton(buttonX(i), buttonY(i), 150, InvTweaksConfig.PROP_ENABLE_MIDDLE_CLICK, "invtweaks.settings.middleclick", "invtweaks.settings.middleclick.tooltip");
    }

}

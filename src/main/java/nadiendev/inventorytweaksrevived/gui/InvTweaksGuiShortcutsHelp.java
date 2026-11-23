package nadiendev.inventorytweaksrevived.gui;

import nadiendev.inventorytweaksrevived.client.InvTweaksClient;
import nadiendev.inventorytweaksrevived.config.InvTweaksConfig;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class InvTweaksGuiShortcutsHelp extends Screen {

    @Nullable
    private final Screen parentScreen;
    private final InvTweaksConfig config;

    public InvTweaksGuiShortcutsHelp(@Nullable Screen parentScreen_) {
        super(Component.translatable("invtweaks.help.shortcuts.title"));
        parentScreen = parentScreen_;
        config = InvTweaksGuiSettingsAbstract.loadConfig();
    }

    @Override
    protected void init() {
        // Create Done button
        addRenderableWidget(Button.builder(Component.translatable("invtweaks.settings.exit"), button -> onClose())
                .bounds(width / 2 - 100, height / 6 + 168, 200, 20).build());
    }

    @Override
    public void extractRenderState(@NotNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);

        // Note: 0xFF0000EE = blue color (currently unused)

        guiGraphics.centeredText(font, "WARNING: Since 1.3.1, shortcuts won't work as expected. Looking for a workaround...", width / 2, 5, 0xFFFF0000);
        guiGraphics.centeredText(font, title, width / 2, 20, 0xFFFFFFFF);
        @NotNull String clickLabel = I18n.get("invtweaks.help.shortcuts.click");

        int y = height / 6 - 2;

        drawShortcutLine(guiGraphics, I18n.get("invtweaks.help.shortcuts.onestack"), "LSHIFT " + I18n.get("invtweaks.help.shortcuts.or") + " RSHIFT + " + clickLabel, 0xFFFFFF00, y);
        y += 12;
        drawShortcutLine(guiGraphics, "", buildUpOrDownLabel(InvTweaksConfig.PROP_SHORTCUT_UP, minecraft.options.keyUp, I18n.get("invtweaks.help.shortcuts.forward")) + " + " + clickLabel, 0xFFFFFF00, y);
        y += 12;
        drawShortcutLine(guiGraphics, "", buildUpOrDownLabel(InvTweaksConfig.PROP_SHORTCUT_DOWN, minecraft.options.keyDown, I18n.get("invtweaks.help.shortcuts.backward")) + " + " + clickLabel, 0xFFFFFF00, y);
        y += 12;
        drawShortcutLine(guiGraphics, I18n.get("invtweaks.help.shortcuts.oneitem"), config.getProperty(InvTweaksConfig.PROP_SHORTCUT_ONE_ITEM) + " + " + clickLabel, 0xFFFFFF00, y);
        y += 12;
        drawShortcutLine(guiGraphics, I18n.get("invtweaks.help.shortcuts.allitems"), config.getProperty(InvTweaksConfig.PROP_SHORTCUT_ALL_ITEMS) + " + " + clickLabel, 0xFFFFFF00, y);
        y += 12;
        drawShortcutLine(guiGraphics, I18n.get("invtweaks.help.shortcuts.everything"), config.getProperty(InvTweaksConfig.PROP_SHORTCUT_EVERYTHING) + " + " + clickLabel, 0xFFFFFF00, y);
        y += 19;

        drawShortcutLine(guiGraphics, I18n.get("invtweaks.help.shortcuts.hotbar"), "0-9 + " + clickLabel, 0xFF00FF33, y);
        y += 12;
        drawShortcutLine(guiGraphics, I18n.get("invtweaks.help.shortcuts.emptyslot"), I18n.get("invtweaks.help.shortcuts.rightclick"), 0xFF00FF33, y);
        y += 12;
        drawShortcutLine(guiGraphics, I18n.get("invtweaks.help.shortcuts.drop"), config.getProperty(InvTweaksConfig.PROP_SHORTCUT_DROP) + " + " + clickLabel, 0xFF00FF33, y);
        y += 19;

        drawShortcutLine(guiGraphics, I18n.get("invtweaks.help.shortcuts.craftall"), "LSHIFT, RSHIFT + " + clickLabel, 0xFFFF8800, y);
        y += 12;
        drawShortcutLine(guiGraphics, I18n.get("invtweaks.help.shortcuts.craftone"), config.getProperty(InvTweaksConfig.PROP_SHORTCUT_ONE_ITEM) + " + " + clickLabel, 0xFFFF8800, y);
        y += 19;

        drawShortcutLine(guiGraphics, I18n.get("invtweaks.help.shortcuts.selectconfig"), "0-9 + " + InvTweaksClient.KEY_SORT.getTranslatedKeyMessage().getString(), 0xFF88FFFF, y);
    }

    @Override
    public void onClose() {
        minecraft.setScreenAndShow(parentScreen);
    }

    private String buildUpOrDownLabel(@NotNull String shortcutProp, @NotNull KeyMapping keyMapping, String defaultKeyName) {
        @NotNull String shortcutLabel = config.getProperty(shortcutProp);
        String keyLabel = keyMapping.isUnbound() ? defaultKeyName : keyMapping.getTranslatedKeyMessage().getString();
        if(keyLabel.equals(shortcutLabel)) {
            return keyLabel;
        } else {
            return keyLabel + "/" + shortcutLabel;
        }
    }

    private void drawShortcutLine(@NotNull GuiGraphicsExtractor guiGraphics, @NotNull String label, @Nullable String value, int color, int y) {
        guiGraphics.text(font, label, 30, y, -1);
        if(value != null) {
            guiGraphics.text(font, value.contains("DEFAULT") ? "-" : value.replaceAll(", ", " " + I18n.get("invtweaks.help.shortcuts.or") + " "), width / 2 - 30, y, color);
        }
    }

}

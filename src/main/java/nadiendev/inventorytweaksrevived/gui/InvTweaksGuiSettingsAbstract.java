package nadiendev.inventorytweaksrevived.gui;

import nadiendev.inventorytweaksrevived.config.InvTweaksConfig;
import nadiendev.inventorytweaksrevived.config.InvTweaksConfigManager;
import nadiendev.inventorytweaksrevived.core.InvTweaksCore;
import net.minecraft.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * The inventory and chest settings menu.
 *
 * @author Jimeo Wan
 */
public abstract class InvTweaksGuiSettingsAbstract extends Screen {

    protected final InvTweaksConfig config;
    @Nullable
    protected final Screen parentScreen;

    protected InvTweaksGuiSettingsAbstract(@Nullable Screen parentScreen_) {
        super(Component.translatable("invtweaks.settings.title"));
        parentScreen = parentScreen_;
        config = loadConfig();
    }

    /**
     * The mod's core only exists once a world has been joined, but the settings can also be opened from the main menu
     * (mod list), so in that case the configuration is loaded on its own from the same files.
     */
    static InvTweaksConfig loadConfig() {
        @Nullable InvTweaksConfigManager manager = InvTweaksCore.getConfigManager();
        @Nullable InvTweaksConfig config = (manager == null) ? null : manager.getConfig();
        if(config == null) {
            manager = new InvTweaksConfigManager(Minecraft.getInstance());
            manager.makeSureConfigurationIsLoaded();
            config = manager.getConfig();
        }
        return config;
    }

    @Override
    protected void init() {
        addRenderableWidget(Button.builder(Component.translatable("invtweaks.settings.exit"), button -> onDone())
                .bounds(buttonX(1) + 55, height / 6 + 168, 200, 20).build());
    }

    @Override
    public void extractRenderState(@NotNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.centeredText(font, title, width / 2, 20, 0xFFFFFFFF);
    }

    /**
     * Escape (and the default Done button) goes back to the screen that opened the settings.
     */
    @Override
    public void onClose() {
        minecraft.setScreenAndShow(parentScreen);
    }

    protected void onDone() {
        onClose();
    }

    protected int buttonX(int buttonOrder) {
        return width / 2 - 155 + ((buttonOrder + 1) % 2) * 160;
    }

    protected int buttonY(int buttonOrder) {
        return height / 6 + (buttonOrder / 2) * 24;
    }

    protected Button addButton(int x, int y, int buttonWidth, Component label, @Nullable Component tooltip, Button.OnPress onPress) {
        Button button = Button.builder(label, onPress).bounds(x, y, buttonWidth, 20).build();
        if(tooltip != null) {
            button.setTooltip(Tooltip.create(tooltip));
        }
        return addRenderableWidget(button);
    }

    protected Button addBooleanButton(int x, int y, int buttonWidth, String property, String labelKey, String tooltipKey) {
        return addBooleanButton(x, y, buttonWidth, property, labelKey, tooltipKey, () -> {
        });
    }

    protected Button addBooleanButton(int x, int y, int buttonWidth, String property, String labelKey, String tooltipKey, Runnable afterToggle) {
        Component label = Component.translatable(labelKey);
        return addButton(x, y, buttonWidth, computeBooleanButtonLabel(property, label), Component.translatable(tooltipKey), button -> {
            toggleBooleanButton(button, property, label);
            afterToggle.run();
        });
    }

    protected void toggleBooleanButton(@NotNull Button button, @NotNull String property, Component label) {
        @NotNull Boolean enabled = !Boolean.valueOf(config.getProperty(property));
        config.setProperty(property, enabled.toString());
        button.setMessage(computeBooleanButtonLabel(property, label));
    }

    @NotNull
    protected Component computeBooleanButtonLabel(@NotNull String property, Component label) {
        boolean enabled = Boolean.parseBoolean(config.getProperty(property));
        return Component.empty().append(label).append(": ").append(Component.translatable(enabled ? "invtweaks.settings.on" : "invtweaks.settings.off"));
    }

    protected void openFile(@NotNull File file, @NotNull String errorKey) {
        // The platform helper only logs a failure to launch, so a missing file is reported here
        if(!file.isFile()) {
            InvTweaksCore.logInGameErrorStatic(errorKey, new FileNotFoundException(file.getPath()));
            return;
        }
        Util.getPlatform().openFile(file);
    }

    protected void openUri(@NotNull String uri, @NotNull String errorKey) {
        try {
            Util.getPlatform().openUri(new URI(uri));
        } catch(URISyntaxException e) {
            InvTweaksCore.logInGameErrorStatic(errorKey, e);
        }
    }

}

package nadiendev.inventorytweaksrevived.gui;

import nadiendev.inventorytweaksrevived.client.InvTweaksClient;
import nadiendev.inventorytweaksrevived.config.InvTweaksConfig;
import nadiendev.inventorytweaksrevived.util.InvTweaksConst;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The inventory and chest advanced settings menu.
 *
 * @author Jimeo Wan
 */
public class InvTweaksGuiSettingsAdvanced extends InvTweaksGuiSettingsAbstract {

    public InvTweaksGuiSettingsAdvanced(@Nullable Screen parentScreen_) {
        super(parentScreen_);
    }

    @Override
    protected void init() {
        super.init();

        int i = 0;

        // Create large buttons

        addButton(buttonX(1) + 55, height / 6 + 144, 200, Component.translatable("invtweaks.settings.advanced.mappingsfile"), null,
                button -> openFile(InvTweaksConst.CONFIG_PROPS_FILE, "invtweaks.settings.advanced.mappingsfile.error"));

        // Create settings buttons

        i += 2;
        addBooleanButton(buttonX(i), buttonY(i++), 150, InvTweaksConfig.PROP_ENABLE_SORTING_ON_PICKUP, "invtweaks.settings.advanced.sortonpickup", "invtweaks.settings.advanced.sortonpickup.tooltip");

        addBooleanButton(buttonX(i), buttonY(i++), 150, InvTweaksConfig.PROP_ENABLE_SOUNDS, "invtweaks.settings.advanced.sounds", "invtweaks.settings.advanced.sounds.tooltip");

        addBooleanButton(buttonX(i), buttonY(i++), 150, InvTweaksConfig.PROP_SHOW_CHEST_BUTTONS, "invtweaks.settings.chestbuttons", "invtweaks.settings.chestbuttons.tooltip");

        addBooleanButton(buttonX(i), buttonY(i++), 150, InvTweaksConfig.PROP_ENABLE_AUTO_EQUIP_ARMOR, "invtweaks.settings.advanced.autoequip", "invtweaks.settings.advanced.autoequip.tooltip");

        addBooleanButton(buttonX(i), buttonY(i++), 150, InvTweaksConfig.PROP_ENABLE_SERVER_ITEMSWAP, "invtweaks.settings.advanced.serverassist", "invtweaks.settings.advanced.serverassist.tooltip",
                () -> InvTweaksClient.serverSupportEnabled = InvTweaksClient.serverSupportDetected
                        && !config.getProperty(InvTweaksConfig.PROP_ENABLE_SERVER_ITEMSWAP).equals(InvTweaksConfig.VALUE_FALSE));

        addBooleanButton(buttonX(i), buttonY(i++), 150, InvTweaksConfig.PROP_TOOLTIP_PATH, "invtweaks.settings.displaytooltip", "invtweaks.settings.displaytooltip.tooltip");

        // Right below the path tooltip button (left column, next row)
        addBooleanButton(buttonX(i + 1), buttonY(i + 1), 150, InvTweaksConfig.PROP_TRANSFER_USE_HOTBAR, "invtweaks.settings.advanced.transferhotbar", "invtweaks.settings.advanced.transferhotbar.tooltip");

        // Right below the server item movement button (right column, next row)
        addBooleanButton(buttonX(i), buttonY(i), 150, InvTweaksConfig.PROP_MOUSE_TWEAKS, "invtweaks.settings.advanced.mousetweaks", "invtweaks.settings.advanced.mousetweaks.tooltip");
    }

    @Override
    public void extractRenderState(@NotNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);

        int x = width / 2;
        guiGraphics.centeredText(font, Component.translatable("invtweaks.settings.pvpwarning.pt1"), x, 40, 0xFF999999);
        guiGraphics.centeredText(font, Component.translatable("invtweaks.settings.pvpwarning.pt2"), x, 50, 0xFF999999);
    }

    /**
     * Back to the main settings screen.
     */
    @Override
    protected void onDone() {
        minecraft.setScreenAndShow(new InvTweaksGuiSettings(parentScreen));
    }

}

package nadiendev.inventorytweaksrevived.gui;

import nadiendev.inventorytweaksrevived.config.InvTweaksConfigManager;
import nadiendev.inventorytweaksrevived.core.InvTweaksCore;
import nadiendev.inventorytweaksrevived.handler.InvTweaksHandlerTransfer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;

/**
 * Transfer arrow of a chest screen: moves items between the player's inventory and the container. A click moves only
 * the items the other side already holds; shift- or ctrl-click moves everything.
 */
public class InvTweaksGuiTransferButton extends InvTweaksGuiIconButton {

    /**
     * "Fill", upwards double arrow (U+21D1): towards the container, which is drawn above the inventory.
     */
    public static final String LABEL_TO_CONTAINER = "⇑";
    /**
     * "Empty", downwards double arrow (U+21D3): towards the player's inventory.
     */
    public static final String LABEL_TO_INVENTORY = "⇓";

    private final boolean toContainer;

    public InvTweaksGuiTransferButton(InvTweaksConfigManager cfgManager, int x, int y, int w, int h, @NotNull String label, String tooltipKey, boolean toContainer) {
        super(cfgManager, x, y, w, h, label, tooltipKey);
        this.toContainer = toContainer;
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
        Minecraft mc = Minecraft.getInstance();
        guiGraphics.drawString(mc.font, getLabel(), getX() + (getWidth() - mc.font.width(getLabel())) / 2,
                getY() + (getHeight() - 8) / 2, getTextColor(), false);
    }

    @Override
    public void onPress() {
        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null || mc.player.isSpectator()) {
            return;
        }
        try {
            InvTweaksHandlerTransfer.transfer(mc, toContainer, Screen.hasShiftDown() || Screen.hasControlDown());
        } catch(Exception e) {
            InvTweaksCore.logInGameErrorStatic("invtweaks.transfer.error", e);
        }
    }
}

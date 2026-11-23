package nadiendev.inventorytweaksrevived.gui;

import nadiendev.inventorytweaksrevived.config.InvTweaksConfigManager;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.jetbrains.annotations.NotNull;

/**
 * Icon-size button, which gets drawn in a specific way to fit its small size.
 *
 * @author Jimeo Wan
 */
public abstract class InvTweaksGuiIconButton extends InvTweaksGuiTooltipButton {

    protected final InvTweaksConfigManager cfgManager;

    public InvTweaksGuiIconButton(InvTweaksConfigManager cfgManager, int x, int y, int w, int h, @NotNull String label, String tooltipKey) {
        super(x, y, w, h, label, tooltipKey, NO_ACTION);
        this.cfgManager = cfgManager;
    }

    /**
     * Draws the vanilla button sprite (normal, highlighted or disabled); subclasses draw their symbol on top.
     */
    @Override
    protected void extractContents(@NotNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        extractDefaultSprite(guiGraphics);
    }

    protected int getTextColor() {
        if(!active) {
            return 0xFFA0A0A0;
        }
        // Coloured symbols (InvTweaksButtonColors)
        int color = InvTweaksButtonColors.of(getLabel(), isHoveredOrFocused());
        if(color != -1) {
            return color;
        }
        if(isHoveredOrFocused()) {
            return 0xFFFFFFA0;
        }
        return 0xFFE0E0E0;
    }

}

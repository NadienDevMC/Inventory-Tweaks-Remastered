package nadiendev.inventorytweaksrevived.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import nadiendev.inventorytweaksrevived.config.InvTweaksConfigManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * Icon-size button, which gets drawn in a specific way to fit its small size.
 *
 * @author Jimeo Wan
 */
public class InvTweaksGuiIconButton extends InvTweaksGuiTooltipButton {

    private static final ResourceLocation SPRITE_NORMAL = ResourceLocation.withDefaultNamespace("widget/button");
    private static final ResourceLocation SPRITE_HIGHLIGHTED = ResourceLocation.withDefaultNamespace("widget/button_highlighted");
    private static final ResourceLocation SPRITE_DISABLED = ResourceLocation.withDefaultNamespace("widget/button_disabled");

    protected final InvTweaksConfigManager cfgManager;

    public InvTweaksGuiIconButton(InvTweaksConfigManager cfgManager, int x, int y, int w, int h, @NotNull String label, String tooltipKey) {
        super(x, y, w, h, label, tooltipKey, NO_ACTION);
        this.cfgManager = cfgManager;
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        ResourceLocation sprite = !active ? SPRITE_DISABLED : isHoveredOrFocused() ? SPRITE_HIGHLIGHTED : SPRITE_NORMAL;
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        guiGraphics.blitSprite(sprite, getX(), getY(), getWidth(), getHeight());
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
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

package nadiendev.inventorytweaksrevived.gui;

import nadiendev.inventorytweaksrevived.InventoryTweaksRevived;
import nadiendev.inventorytweaksrevived.api.container.ContainerSection;
import nadiendev.inventorytweaksrevived.config.InvTweaksConfigManager;
import nadiendev.inventorytweaksrevived.container.ContainerSectionManager;
import nadiendev.inventorytweaksrevived.util.InvTweaksObfuscation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.InputWithModifiers;
import org.jetbrains.annotations.NotNull;

/**
 * Button that opens the inventory and chest settings screen.
 *
 * @author Jimeo Wan
 */
public class InvTweaksGuiSettingsButton extends InvTweaksGuiIconButton {

    /**
     * Pencil (U+270E).
     */
    public static final String LABEL = "✎";

    public InvTweaksGuiSettingsButton(InvTweaksConfigManager cfgManager, int x, int y, int w, int h, @NotNull String label, String tooltipKey) {
        super(cfgManager, x, y, w, h, label, tooltipKey);
    }

    @Override
    protected void extractContents(@NotNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractContents(guiGraphics, mouseX, mouseY, partialTick);

        // Display string
        // The old "..." label sat on the baseline; a symbol is centered
        int textY = "...".equals(getLabel()) ? getY() - 1 : getY() + (getHeight() - 8) / 2 + 1;
        guiGraphics.centeredText(Minecraft.getInstance().font, getMessage(), getX() + getWidth() / 2, textY, getTextColor());
    }

    /**
     * Displays inventory settings GUI
     */
    @Override
    public void onPress(@NotNull InputWithModifiers input) {
        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null || mc.player.isSpectator()) {
            return;
        }

        // Put hold item down if necessary
        @NotNull InvTweaksObfuscation obf = new InvTweaksObfuscation(mc);
        try {
            ContainerSectionManager containerMgr = new ContainerSectionManager(ContainerSection.INVENTORY);
            if(!obf.getHeldStack().isEmpty()) {
                for(int k = containerMgr.getSize() - 1; k >= 0; k--) {
                    if(containerMgr.getItemStack(k).isEmpty()) {
                        containerMgr.leftClick(k);
                        break;
                    }
                }
            }
        } catch(Exception e) {
            InventoryTweaksRevived.LOGGER.error("onPress", e);
        }

        // Refresh config
        cfgManager.makeSureConfigurationIsLoaded();

        // Display menu
        mc.setScreenAndShow(new InvTweaksGuiSettings(mc.gui.screen()));
    }

}

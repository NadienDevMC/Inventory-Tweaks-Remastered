package nadiendev.inventorytweaksrevived.gui;

import nadiendev.inventorytweaksrevived.api.SortingMethod;
import nadiendev.inventorytweaksrevived.api.container.ContainerSection;
import nadiendev.inventorytweaksrevived.config.InvTweaksConfigManager;
import nadiendev.inventorytweaksrevived.core.InvTweaksCore;
import nadiendev.inventorytweaksrevived.handler.InvTweaksHandlerSorting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

/**
 * Sorting button of a section of the open menu (the chest, or the player's inventory)
 *
 * @author Jimeo Wan
 */
public class InvTweaksGuiSortingButton extends InvTweaksGuiIconButton {

    /**
     * Labels: default sorting (U+21BA), columns (U+2632), rows (U+2637) and N shape (U+265B).
     */
    public static final String LABEL_DEFAULT = "↺", LABEL_COLUMNS = "☲", LABEL_ROWS = "☷", LABEL_NSHAPE = "♛";

    private final ContainerSection section;

    private final SortingMethod algorithm;
    private final int rowSize;

    public InvTweaksGuiSortingButton(InvTweaksConfigManager cfgManager, int x, int y, int w, int h, @NotNull String label, String tooltipKey, @NotNull ContainerSection section, SortingMethod algorithm, int rowSize) {
        super(cfgManager, x, y, w, h, label, tooltipKey);
        this.section = section;
        this.algorithm = algorithm;
        this.rowSize = rowSize;
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);

        // Display symbol
        int x = getX(), y = getY(), width = getWidth(), height = getHeight();
        // The label is a symbol, drawn as text
        Font font = Minecraft.getInstance().font;
        // The default-sorting symbol sits low in its glyph cell, so it is lifted to look centered
        int yOffset = "↺".equals(getLabel()) ? -2 : 1;
        guiGraphics.drawString(font, getLabel(), x + (width - font.width(getLabel())) / 2 + 1, y + (height - 8) / 2 + yOffset, getTextColor(), false);
    }

    /**
     * Sort the section
     */
    @Override
    public void onPress() {
        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null || mc.player.isSpectator()) {
            return;
        }

        try {
            new InvTweaksHandlerSorting(mc, cfgManager.getConfig(), section, algorithm, rowSize).sort();
        } catch(Exception e) {
            InvTweaksCore.logInGameErrorStatic(section == ContainerSection.CHEST ? "invtweaks.sort.chest.error" : "invtweaks.sort.inventory.error", e);
        }
    }

}

package nadiendev.inventorytweaksrevived.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A help menu for the NoCheatPlus conflict.
 *
 * @author Jimeo Wan
 */
public class InvTweaksGuiModNotWorking extends InvTweaksGuiSettingsAbstract {

    public InvTweaksGuiModNotWorking(@Nullable Screen parentScreen_) {
        super(parentScreen_);
    }

    @Override
    public void extractRenderState(@NotNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);

        int x = width / 2;
        guiGraphics.centeredText(font, Component.translatable("invtweaks.help.bugsorting.pt1"), x, 80, 0xFFBBBBBB);
        guiGraphics.centeredText(font, Component.translatable("invtweaks.help.bugsorting.pt2"), x, 95, 0xFFBBBBBB);
        guiGraphics.centeredText(font, Component.translatable("invtweaks.help.bugsorting.pt3"), x, 110, 0xFFBBBBBB);
        guiGraphics.centeredText(font, Component.translatable("invtweaks.help.bugsorting.pt4"), x, 150, 0xFFFFFF99);
    }

    /**
     * Back to the main settings screen.
     */
    @Override
    protected void onDone() {
        minecraft.setScreen(new InvTweaksGuiSettings(parentScreen));
    }

}

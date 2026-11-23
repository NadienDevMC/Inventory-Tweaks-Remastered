package nadiendev.inventorytweaksrevived.gui;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Button with a vanilla tooltip.
 *
 * @author Jimeo Wan
 */
public class InvTweaksGuiTooltipButton extends Button {

    /**
     * For subclasses that override {@link #onPress()} instead of taking a callback.
     */
    protected static final OnPress NO_ACTION = button -> {
    };

    public InvTweaksGuiTooltipButton(int x, int y, int w, int h, @NotNull String label, @Nullable String tooltipKey, @NotNull OnPress onPress) {
        super(x, y, w, h, Component.literal(label), onPress, DEFAULT_NARRATION);
        if(tooltipKey != null) {
            setTooltip(Tooltip.create(Component.translatable(tooltipKey)));
        }
    }

    @NotNull
    public String getLabel() {
        return getMessage().getString();
    }

}

package nadiendev.inventorytweaksrevived.mixin;

import nadiendev.inventorytweaksrevived.client.InvTweaksClient;
import net.minecraft.client.gui.components.EditBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Tells the mod when a text field gains or loses focus, so typing in it (anvil, creative search, sign...) does not
 * trigger the sort key. {@code EditBox} overrides {@code setFocused(boolean)} itself, so the injection targets it
 * directly.
 */
@Mixin(EditBox.class)
public abstract class EditBoxMixin {

    @Inject(method = "setFocused", at = @At("TAIL"))
    private void invtweaks$onSetFocused(boolean focused, CallbackInfo ci) {
        InvTweaksClient.setTextboxMode(((EditBox) (Object) this).isFocused());
    }
}

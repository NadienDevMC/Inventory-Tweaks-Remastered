package nadiendev.inventorytweaksrevived.mixin.sophisticatedcorecompat;

import nadiendev.inventorytweaksrevived.compat.SophisticatedCompat;
import net.minecraft.client.gui.components.events.GuiEventListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Sophisticated Storage / Backpacks screens: while our chest buttons are shown, Sophisticated's own sort button and its
 * two transfer arrows are hidden (its search box, "sort by" button and settings stay), and the search box of the title
 * line ends before our buttons. {@code @Pseudo} and the optional config: nothing happens when Sophisticated is absent.
 * Member names checked with javap on sophisticatedcore 26.1.2-1.5.42.
 */
@Pseudo
@Mixin(targets = "net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase", remap = false)
public abstract class StorageScreenBaseMixin {

    /**
     * The first widget {@code addSortButtons()} adds is the sort button (the second one, "sort by", is kept).
     */
    @ModifyArg(method = "addSortButtons",
            at = @At(value = "INVOKE", target = "addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;", ordinal = 0),
            index = 0, require = 0)
    private GuiEventListener invtweaks$hideSortButton(GuiEventListener sortButton) {
        if(SophisticatedCompat.replacesSortButton(this)) {
            SophisticatedCompat.hideWidget(sortButton);
        }
        return sortButton;
    }

    /**
     * Without the transfer buttons Sophisticated keeps both fields null, which every use of them checks.
     */
    @Inject(method = "addTransferButtons", at = @At("HEAD"), cancellable = true, require = 0)
    private void invtweaks$skipTransferButtons(CallbackInfo ci) {
        if(SophisticatedCompat.replacesTransferButtons(this)) {
            ci.cancel();
        }
    }

    @Inject(method = "getTitleLineEndBeforeSortButtons", at = @At("RETURN"), cancellable = true, require = 0)
    private void invtweaks$titleLineEnd(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(SophisticatedCompat.titleLineEnd(this, cir.getReturnValueI()));
    }
}

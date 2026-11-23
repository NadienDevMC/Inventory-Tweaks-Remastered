package nadiendev.inventorytweaksrevived.data.lang;

import nadiendev.inventorytweaksrevived.InventoryTweaksRevived;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public abstract class InvTweaksLangProvider extends LanguageProvider {
    protected InvTweaksLangProvider(PackOutput output, String locale) {
        super(output, InventoryTweaksRevived.MOD_ID, locale);
    }
}

package nadiendev.inventorytweaksrevived.data;

import nadiendev.inventorytweaksrevived.data.lang.*;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class InvTweaksData {
    private InvTweaksData() {}

    public static void gatherData(GatherDataEvent.Client event) {
        PackOutput output = event.getGenerator().getPackOutput();

        event.addProvider(new CsCzLangProvider(output));
        event.addProvider(new DaDkLangProvider(output));
        event.addProvider(new DeDeLangProvider(output));
        event.addProvider(new EnUsLangProvider(output));
        event.addProvider(new EsEsLangProvider(output));
        event.addProvider(new EtEeLangProvider(output));
        event.addProvider(new FrCaLangProvider(output));
        event.addProvider(new FrFrLangProvider(output));
        event.addProvider(new HuHuLangProvider(output));
        event.addProvider(new ItItLangProvider(output));
        event.addProvider(new JaJpLangProvider(output));
        event.addProvider(new KoKrLangProvider(output));
        event.addProvider(new LtLtLangProvider(output));
        event.addProvider(new NlNlLangProvider(output));
        event.addProvider(new NoNoLangProvider(output));
        event.addProvider(new PlPlLangProvider(output));
        event.addProvider(new PtBrLangProvider(output));
        event.addProvider(new PtPtLangProvider(output));
        event.addProvider(new RuRuLangProvider(output));
        event.addProvider(new SvSeLangProvider(output));
        event.addProvider(new TrTrLangProvider(output));
        event.addProvider(new UkUaLangProvider(output));
        event.addProvider(new ZhCnLangProvider(output));
        event.addProvider(new ZhTwLangProvider(output));
    }
}

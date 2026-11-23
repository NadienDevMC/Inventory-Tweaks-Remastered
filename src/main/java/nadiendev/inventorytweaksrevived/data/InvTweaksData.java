package nadiendev.inventorytweaksrevived.data;

import nadiendev.inventorytweaksrevived.data.lang.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class InvTweaksData {
    private InvTweaksData() {}

    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        boolean client = event.includeClient();

        generator.addProvider(client, new CsCzLangProvider(output));
        generator.addProvider(client, new DaDkLangProvider(output));
        generator.addProvider(client, new DeDeLangProvider(output));
        generator.addProvider(client, new EnUsLangProvider(output));
        generator.addProvider(client, new EsEsLangProvider(output));
        generator.addProvider(client, new EtEeLangProvider(output));
        generator.addProvider(client, new FrCaLangProvider(output));
        generator.addProvider(client, new FrFrLangProvider(output));
        generator.addProvider(client, new HuHuLangProvider(output));
        generator.addProvider(client, new ItItLangProvider(output));
        generator.addProvider(client, new JaJpLangProvider(output));
        generator.addProvider(client, new KoKrLangProvider(output));
        generator.addProvider(client, new LtLtLangProvider(output));
        generator.addProvider(client, new NlNlLangProvider(output));
        generator.addProvider(client, new NoNoLangProvider(output));
        generator.addProvider(client, new PlPlLangProvider(output));
        generator.addProvider(client, new PtBrLangProvider(output));
        generator.addProvider(client, new PtPtLangProvider(output));
        generator.addProvider(client, new RuRuLangProvider(output));
        generator.addProvider(client, new SvSeLangProvider(output));
        generator.addProvider(client, new TrTrLangProvider(output));
        generator.addProvider(client, new UkUaLangProvider(output));
        generator.addProvider(client, new ZhCnLangProvider(output));
        generator.addProvider(client, new ZhTwLangProvider(output));
    }
}

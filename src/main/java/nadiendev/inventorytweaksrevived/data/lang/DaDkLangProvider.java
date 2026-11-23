package nadiendev.inventorytweaksrevived.data.lang;

import net.minecraft.data.PackOutput;

public class DaDkLangProvider extends InvTweaksLangProvider {
    public DaDkLangProvider(PackOutput output) {
        super(output, "da_dk");
    }

    @Override
    protected void addTranslations() {
        add("invtweaks.loadconfig.enabled", "'%s' aktiveret");
        add("invtweaks.loadconfig.refresh.error", "[1]Kunne ikke opdatere egenskaber fra filen");
        add("invtweaks.loadconfig.invalidkeywords", "Ugyldig nøgleord fundet");
        add("invtweaks.loadconfig.filemissing", "mangler, opretter den standarde.");
        add("invtweaks.loadconfig.done", "Konfiguration indlæst.");
        add("invtweaks.propsfile.loaded", "Tilføjelsets egenskaber indlæst");
        add("invtweaks.propsfile.error", "[14]Kunne ikke lave egenskaberfilen");
        add("invtweaks.extract.find.error", "[15]Moddet vile ikke virke, fordi %s ikke kan findes!");
        add("invtweaks.extract.create.error", "[16]Moddet vil ikke virke, fordi %s ikke kunne oprettes!");
        add("invtweaks.sort.inventory.error", "[2]Kunne ikke sortere dine ting");
        add("invtweaks.sort.chest.error", "[3]Kunne ikke sortere beholder");
        add("invtweaks.sort.autorefill.error", "[4]Kunne ikke udløse auto-fyld");
        add("invtweaks.sort.releaseitem.error", "[5]Kunne ikke slippe tingen");
        add("invtweaks.shortcut.error", "[6]Kunne ikke udløse genvejen");
        add("invtweaks.pickup.error", "[7]Kunne ikke flytte den valgte bunke");
        add("invtweaks.button.settings.tooltip", "Indstillinger for taske");
        add("invtweaks.button.chest1.tooltip", "Standard sortering");
        add("invtweaks.button.chest2.tooltip", "Sorter i kolonner");
        add("invtweaks.button.chest3.tooltip", "Sorter i rækker");
        add("invtweaks.settings.title", "Beholder- og kisteindstillinger");
        add("invtweaks.settings.on", "TÆND");
        add("invtweaks.settings.off", "SLUK");
        add("invtweaks.settings.key", "Sorteringstast:");
        add("invtweaks.settings.disableci", "Deaktiver CI");
        add("invtweaks.settings.disableci.tooltip", "I konflikt med Convenient Inventory");
        add("invtweaks.settings.middleclick", "Musehjulsklik");
        add("invtweaks.settings.middleclick.tooltip", "Giver mulighed for at sortere\nmed musehjulsklik");
        add("invtweaks.settings.chestbuttons", "Kisteknapper");
        add("invtweaks.settings.chestbuttons.tooltip", "Tilføjer 3 knapper \nved kister for at sortere dem");
        add("invtweaks.settings.shortcuts", "Genveje");
        add("invtweaks.settings.shortcuts.tooltip", "Aktiverer forskellige genveje\ntil at flytte ting rundt");
        add("invtweaks.settings.autorefill", "Auto-genopfyld");
        add("invtweaks.settings.autorefill.tooltip", "Erstatter ødelagte værktøjer & tømte bundter\nmed nye fra din beholder");
        add("invtweaks.settings.beforebreak", "Reparationsvenlig genopfyldning");
        add("invtweaks.settings.beforebreak.tooltip", "Erstat værktøj, lige inden de går i stykker");
        add("invtweaks.settings.moreoptions", "Flere indstillinger...");
        add("invtweaks.settings.moreoptions.tooltip", "Endnu flere seje ting");
        add("invtweaks.settings.rulesfile", "Åbn sorterings regelfilen...");
        add("invtweaks.settings.rulesfile.error", "Kunne ikke åbne regelfilen");
        add("invtweaks.settings.treefile", "Åbn filen for hvordan ting skal sorteres...");
        add("invtweaks.settings.treefile.error", "[9]Kunne ikke åbne træ-fil");
        add("invtweaks.settings.onlinehelp", "Åbn hjælp i browseren...");
        add("invtweaks.settings.onlinehelp.error", "[10]Kunne ikke åbne hjælpen");
        add("invtweaks.help.bugsorting", "Virker det ikke?");
        add("invtweaks.help.bugsorting.pt1", "Hvis din server har tendens til at 'forhindre' hvad dette mod gør,");
        add("invtweaks.help.bugsorting.pt2", "er det sikkert fordi du bruger NoCheatPlus plugin'et.");
        add("invtweaks.help.bugsorting.pt3", "Bed din admin deaktivere følgende indstillinger:");
        add("invtweaks.help.bugsorting.pt4", "\"inventory.fastclick\" og \"combined.improbable\"");
        add("invtweaks.settings.pvpwarning.pt1", "Bemærk: På PvP servere, auto-genopfyld & auto-iklæd rustning");
        add("invtweaks.settings.pvpwarning.pt2", "indstillinger betragtes ofte for snyd!");
        add("invtweaks.settings.exit", "Færdig");
        add("invtweaks.settings.advanced.sortonpickup", "Sorter ved opsamling");
        add("invtweaks.settings.advanced.sortonpickup.soloonly", "Kun SP");
        add("invtweaks.settings.advanced.sortonpickup.tooltip", "Undgår at fylde hurtigbaren med\nting du samler op");
        add("invtweaks.settings.advanced.sortonpickup.tooltip.soloonly", "Kun når du spiller alene");
        add("invtweaks.settings.advanced.autoequip", "Auto-udstyr rustning");
        add("invtweaks.settings.advanced.autoequip.tooltip", "Iklæder den bedste rustning tilgængelig\nnår du sorterer");
        add("invtweaks.settings.advanced.sounds", "Aktiver lyd");
        add("invtweaks.settings.advanced.sounds.tooltip", "Afspil lyde når sortering\neller når auto-refill udløses");
        add("invtweaks.settings.advanced.mappingsfile", "Åbn genvejsfilen");
        add("invtweaks.settings.advanced.mappingsfile.error", "[11]Fejl ved åbning af \"mappings\" fil");
        add("invtweaks.settings.advanced.serverassist", "Server genstand bevægelse");
        add("invtweaks.settings.advanced.serverassist.tooltip", "Aktiver brug af server assistent for genstandsbevægelse samtidig med sortering.");
        add("invtweaks.help.shortcuts.title", "Hjælp til genveje");
        add("invtweaks.help.shortcuts.click", "Klik på");
        add("invtweaks.help.shortcuts.or", "eller");
        add("invtweaks.help.shortcuts.onestack", "Flyt en bunke");
        add("invtweaks.help.shortcuts.oneitem", "Flyt en ting");
        add("invtweaks.help.shortcuts.allitems", "Flyt alle ting af samme type");
        add("invtweaks.help.shortcuts.everything", "Flyt alt");
        add("invtweaks.help.shortcuts.hotbar", "Flyt til hurtigskiftbar");
        add("invtweaks.help.shortcuts.emptyslot", "Flyt til tom plads");
        add("invtweaks.help.shortcuts.rightclick", "(Enhver genvej)+Højreklik i stedet for venstreklik");
        add("invtweaks.help.shortcuts.drop", "Slip");
        add("invtweaks.help.shortcuts.craftall", "Lav alle til beholder");
        add("invtweaks.help.shortcuts.craftone", "Lav til beholder");
        add("invtweaks.help.shortcuts.selectconfig", "Vælg sorteringskonfiguration");
        add("invtweaks.help.shortcuts.forward", "(Frem)");
        add("invtweaks.help.shortcuts.backward", "(Tilbage)");
        add("invtweaks.keyboardfix.error", "[12]Jeg har problemer med tastaturet");
        add("invtweaks.keyboardfix.recover", "Ok, det er repareret, undskyld for det.");
        add("invtweaks.modcompat.ci.error", "[13]Kunne ikke styre kompatibilitet med \"Convenient Inventory\"");
        add("key.category.inventorytweaksrevived.invtweaks", "Lager Tweaks");
        add("invtweaks.key.sort", "Lager Sortering");
    }
}

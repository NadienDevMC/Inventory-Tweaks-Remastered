package nadiendev.inventorytweaksrevived.data.lang;

import net.minecraft.data.PackOutput;

public class NoNoLangProvider extends InvTweaksLangProvider {
    public NoNoLangProvider(PackOutput output) {
        super(output, "no_no");
    }

    @Override
    protected void addTranslations() {
        add("invtweaks.loadconfig.enabled", "'%s' aktivert");
        add("invtweaks.loadconfig.refresh.error", "[1] Kunne ikke oppdatere egenskaper fra fil");
        add("invtweaks.loadconfig.invalidkeywords", "Ugyldige nøkkelord funnet");
        add("invtweaks.loadconfig.filemissing", "Mangler, oppretter standard.");
        add("invtweaks.loadconfig.done", "Konfigurasjon lastet.");
        add("invtweaks.propsfile.loaded", "Mod egenskaper lastet");
        add("invtweaks.propsfile.error", "[14] Feilet med og lage mod egenskapsfil");
        add("invtweaks.extract.find.error", "[15] Mod vil ikke fungere, fordi %s kunne ikke bli funnet!");
        add("invtweaks.extract.create.error", "[16] Mod vil ikke fungere, fordi oppretting av %s mislyktes!");
        add("invtweaks.sort.inventory.error", "[2] Kan ikke sortere lager");
        add("invtweaks.sort.chest.error", "[3] Klarte ikke å sortere beholder");
        add("invtweaks.sort.autorefill.error", "[4] Kunne ikke slå på automatisk fylling");
        add("invtweaks.sort.releaseitem.error", "[5] Klarte ikke å løsne element");
        add("invtweaks.shortcut.error", "[6] Kunne ikke utløse snarvei");
        add("invtweaks.pickup.error", "[7] Feilet med og flytte opp plukket stabel");
        add("invtweaks.button.settings.tooltip", "Lager instillinger");
        add("invtweaks.button.chest1.tooltip", "Standard sortering");
        add("invtweaks.button.chest2.tooltip", "Sorter i kolonner");
        add("invtweaks.button.chest3.tooltip", "Sorter i rader");
        add("invtweaks.settings.title", "Lager og kiste innstillinger");
        add("invtweaks.settings.on", "PÅ");
        add("invtweaks.settings.off", "AV");
        add("invtweaks.settings.key", "Tast for sortering:");
        add("invtweaks.settings.disableci", "Deaktivere CI");
        add("invtweaks.settings.disableci.tooltip", "I konflikt med Convenient Inventory");
        add("invtweaks.settings.middleclick", "Mellom-klikk");
        add("invtweaks.settings.middleclick.tooltip", "Tillater å sortere ved å bruke midt-knappen");
        add("invtweaks.settings.chestbuttons", "Kisteknapper");
        add("invtweaks.settings.chestbuttons.tooltip", "Legger til tre knapper på kister for å sortere dem");
        add("invtweaks.settings.shortcuts", "Snarveier");
        add("invtweaks.settings.shortcuts.tooltip", "Tillater flere snarveier for å bevege ting rundt");
        add("invtweaks.settings.autorefill", "Auto-påfylling");
        add("invtweaks.settings.autorefill.tooltip", "Erstatter ødelagte redskaper & tomme stabler med andre fra lageret ditt");
        add("invtweaks.settings.beforebreak", "Reparerings Vennlig påfylling");
        add("invtweaks.settings.beforebreak.tooltip", "Erstatter redskaper rett før de ødelegges");
        add("invtweaks.settings.moreoptions", "Flere innstillinger...");
        add("invtweaks.settings.moreoptions.tooltip", "Enda flere kule ting");
        add("invtweaks.settings.rulesfile", "Åpne filen for sorteringsregler...");
        add("invtweaks.settings.rulesfile.error", "[8] Feilet å åpne filen for regler");
        add("invtweaks.settings.treefile", "Åpne element-tre filen...");
        add("invtweaks.settings.treefile.error", "[8] Kunne ikke åpne elemtent-tre filen");
        add("invtweaks.settings.onlinehelp", "Åpne hjelp i nettleser...");
        add("invtweaks.settings.onlinehelp.error", "[10] Feilet med å åpne hjelp");
        add("invtweaks.help.bugsorting", "Fungerer ikke?");
        add("invtweaks.help.bugsorting.pt1", "Dersom serveren ser ut til å 'Avbryte' hva modden gjør,");
        add("invtweaks.help.bugsorting.pt2", "det skyldes sannsynligvis av NoCheatPlus pluginen.");
        add("invtweaks.help.bugsorting.pt3", "Spør din Admin til å deaktivere følgende alternativer:");
        add("invtweaks.help.bugsorting.pt4", "\"inventory.fastclick\" og \"combined.improbable\"");
        add("invtweaks.settings.pvpwarning.pt1", "Merk: På PvP servere kan auto-fylling & auto-utstyre rustning");
        add("invtweaks.settings.pvpwarning.pt2", "innstillinger regnes ofte som juksing!");
        add("invtweaks.settings.exit", "Ferdig");
        add("invtweaks.settings.advanced.sortonpickup", "Sortèr når elementer plukkes opp");
        add("invtweaks.settings.advanced.sortonpickup.soloonly", "Kun Single Player");
        add("invtweaks.settings.advanced.sortonpickup.tooltip", "Unngår å fylle hotbaren med alt du plukker opp");
        add("invtweaks.settings.advanced.sortonpickup.tooltip.soloonly", "Kun i enspiller modus");
        add("invtweaks.settings.advanced.autoequip", "Auto-utstyre rustning");
        add("invtweaks.settings.advanced.autoequip.tooltip", "Utstyrer den beste type rustning når lagring sorteres");
        add("invtweaks.settings.advanced.sounds", "Aktiver lyd");
        add("invtweaks.settings.advanced.sounds.tooltip", "Spill lyd når du sorterer eller når auto-refill aktiveres");
        add("invtweaks.settings.advanced.mappingsfile", "Åpne snarvei til kartleggings fil...");
        add("invtweaks.settings.advanced.mappingsfile.error", "[11] Feilet med å åpne karleggingsfil");
        add("invtweaks.settings.advanced.serverassist", "Server ting bevegelse");
        add("invtweaks.settings.advanced.serverassist.tooltip", "Aktiver bruk av Server assistanse for Item forflyttelse under sortering");
        add("invtweaks.help.shortcuts.title", "Hjelp for snarveier");
        add("invtweaks.help.shortcuts.click", "Klikk");
        add("invtweaks.help.shortcuts.or", "eller");
        add("invtweaks.help.shortcuts.onestack", "Flytt en stabel");
        add("invtweaks.help.shortcuts.oneitem", "Flytt ett element");
        add("invtweaks.help.shortcuts.allitems", "Flytt alle elementer av samme type");
        add("invtweaks.help.shortcuts.everything", "Flytt alt");
        add("invtweaks.help.shortcuts.hotbar", "Flytt til hotbar");
        add("invtweaks.help.shortcuts.emptyslot", "Flytt til en tom plass");
        add("invtweaks.help.shortcuts.rightclick", "(Hvilken som helst snarvei) + Høyre istedenfor venstre-klikk");
        add("invtweaks.help.shortcuts.drop", "Slipp");
        add("invtweaks.help.shortcuts.craftall", "Lag alt til lageret");
        add("invtweaks.help.shortcuts.craftone", "Lag til lageret");
        add("invtweaks.help.shortcuts.selectconfig", "Velg sorteringsinnstillinger");
        add("invtweaks.help.shortcuts.forward", "(Fremover)");
        add("invtweaks.help.shortcuts.backward", "(Bakover)");
        add("invtweaks.keyboardfix.error", "[12] Jeg har problemer med tastaturet");
        add("invtweaks.keyboardfix.recover", "Ok det er reparert, beklager det.");
        add("invtweaks.modcompat.ci.error", "[13] Feilet å administrere 'Convenient Inventory' kompatibilitet");
        add("key.category.inventorytweaksrevived.invtweaks", "Lager Tweaks");
        add("invtweaks.key.sort", "Lager sortering");
    }
}

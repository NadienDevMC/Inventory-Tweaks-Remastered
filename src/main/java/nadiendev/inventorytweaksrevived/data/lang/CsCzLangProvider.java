package nadiendev.inventorytweaksrevived.data.lang;

import net.minecraft.data.PackOutput;

public class CsCzLangProvider extends InvTweaksLangProvider {
    public CsCzLangProvider(PackOutput output) {
        super(output, "cs_cz");
    }

    @Override
    protected void addTranslations() {
        add("invtweaks.loadconfig.enabled", "'%s' zapnuto");
        add("invtweaks.loadconfig.refresh.error", "[1] Nepodařilo se obnovit nastavení ze souboru");
        add("invtweaks.loadconfig.invalidkeywords", "Nalezeny nesprávné klíčové slova");
        add("invtweaks.loadconfig.filemissing", "chybí, vytvářím defaultní.");
        add("invtweaks.loadconfig.done", "Nastavení načteno.");
        add("invtweaks.propsfile.loaded", "Nastavení módu načteno");
        add("invtweaks.propsfile.error", "[14] Nepodařilo se vytvořit soubor s nastavením módu");
        add("invtweaks.extract.find.error", "[15] Mód nebude fungovat, protože %s nebyl nalezen!");
        add("invtweaks.extract.create.error", "[16] Mód nebude fungovat, protože vytvoření %s selhalo!");
        add("invtweaks.sort.inventory.error", "[2] Nepodařilo se seřadit inventář");
        add("invtweaks.sort.chest.error", "[3] Nepodařilo se seřadit kontejner");
        add("invtweaks.sort.autorefill.error", "[4] Nepodařilo se spustit auto-doplnění");
        add("invtweaks.sort.releaseitem.error", "[5] Nepodařilo se uvolnit předmět");
        add("invtweaks.shortcut.error", "[6] Nepodařilo se spustit zkratku");
        add("invtweaks.pickup.error", "[7] Nepodařilo se přesunout sebraný stak");
        add("invtweaks.button.settings.tooltip", "Nastavení inventáře");
        add("invtweaks.button.chest1.tooltip", "Defaultní řazení");
        add("invtweaks.button.chest2.tooltip", "Řadit ve sloupcích");
        add("invtweaks.button.chest3.tooltip", "Řadit v řádkách");
        add("invtweaks.settings.title", "Nastavení inventáře a truhel");
        add("invtweaks.settings.on", "ZAPNUTO");
        add("invtweaks.settings.off", "VYPNUTO");
        add("invtweaks.settings.key", "Klíč řazení:");
        add("invtweaks.settings.disableci", "Zakázat CI");
        add("invtweaks.settings.disableci.tooltip", "V konfliktu s Convenient Inventory");
        add("invtweaks.settings.middleclick", "Kliknutí prostředním tlačítkem");
        add("invtweaks.settings.middleclick.tooltip", "Umožňuje řazení s použitím\nprostředního tlačítka myši");
        add("invtweaks.settings.chestbuttons", "Tlačítka truhly");
        add("invtweaks.settings.chestbuttons.tooltip", "Přidává tři tlačítka\n na truhly pro jejich řazení");
        add("invtweaks.settings.shortcuts", "Zkratky");
        add("invtweaks.settings.shortcuts.tooltip", "Povolí různé zkratky\n pro přesun předmětů");
        add("invtweaks.settings.autorefill", "Auto-doplnění");
        add("invtweaks.settings.autorefill.tooltip", "Nahradí zničené nástroje a prázdné stacky\nostatními z inventáře");
        add("invtweaks.settings.beforebreak", "Opravné-přátelské doplnění");
        add("invtweaks.settings.beforebreak.tooltip", "Nahradí nástroje přímo před tím, než se rozbijí");
        add("invtweaks.settings.moreoptions", "Více voleb...");
        add("invtweaks.settings.moreoptions.tooltip", "Ještě více super věcí");
        add("invtweaks.settings.rulesfile", "Otevřít soubor pravidel řazení...");
        add("invtweaks.settings.rulesfile.error", "[8] Nepodařilo se otevřít soubor pravidel řazení");
        add("invtweaks.settings.treefile", "Otevřít soubor stromu předmětů...");
        add("invtweaks.settings.treefile.error", "[9] Nepodařilo se otevřít soubor stromu");
        add("invtweaks.settings.onlinehelp", "Otevřít nápovědu v prohlížeči...");
        add("invtweaks.settings.onlinehelp.error", "[10] Nepodařilo se otevřít nápovědu");
        add("invtweaks.help.bugsorting", "Nefunguje?");
        add("invtweaks.help.bugsorting.pt1", "Pokud to vypadá, že server 'ruší' to, co tento mód dělá,");
        add("invtweaks.help.bugsorting.pt2", "je to nejspíš způsobené používáním pluginu NoCheatPlus.");
        add("invtweaks.help.bugsorting.pt3", "Požádej svého admina o zakázání následujících možností:");
        add("invtweaks.help.bugsorting.pt4", "\"inventory.fastclick\" a \"combined.improbable\"");
        add("invtweaks.settings.pvpwarning.pt1", "Poznámka: Na PvP serverech je auto-doplnění a auto-nasazení brnění");
        add("invtweaks.settings.pvpwarning.pt2", "často považováno za podvádění!");
        add("invtweaks.settings.exit", "Hotovo");
        add("invtweaks.settings.advanced.sortonpickup", "Seřadit při sebrání");
        add("invtweaks.settings.advanced.sortonpickup.soloonly", "SP pouze");
        add("invtweaks.settings.advanced.sortonpickup.tooltip", "Vyhne se naplnění hotbaru vším\nco sebereš");
        add("invtweaks.settings.advanced.sortonpickup.tooltip.soloonly", "Pouze v módu jednoho hráče");
        add("invtweaks.settings.advanced.autoequip", "Auto-nasadit brnění");
        add("invtweaks.settings.advanced.autoequip.tooltip", "Nasadí nejlepší dostupné\nbrnění při řazení");
        add("invtweaks.settings.advanced.sounds", "Povolit zvuk");
        add("invtweaks.settings.advanced.sounds.tooltip", "Přehrát zvuk při řazení\nnebo spuštění auto-doplnění");
        add("invtweaks.settings.advanced.mappingsfile", "Otevřít soubor mapování zkratek...");
        add("invtweaks.settings.advanced.mappingsfile.error", "[11] Nepodařilo se otevřít soubor mapování");
        add("invtweaks.settings.advanced.serverassist", "Serverový pohyb předmětů");
        add("invtweaks.settings.advanced.serverassist.tooltip", "Povolí používání serverové pomoci pro pohyb předmětů při řazení.");
        add("invtweaks.help.shortcuts.title", "Nápověda zkratek");
        add("invtweaks.help.shortcuts.click", "Klikni");
        add("invtweaks.help.shortcuts.or", "nebo");
        add("invtweaks.help.shortcuts.onestack", "Přesunout jeden stack");
        add("invtweaks.help.shortcuts.oneitem", "Přesunout jeden předmět");
        add("invtweaks.help.shortcuts.allitems", "Přesunout všechny předměty stejného typu");
        add("invtweaks.help.shortcuts.everything", "Přesunout vše");
        add("invtweaks.help.shortcuts.hotbar", "Přesunout na hotbar");
        add("invtweaks.help.shortcuts.emptyslot", "Přesunout pro prázdného slotu");
        add("invtweaks.help.shortcuts.rightclick", "(Jakákoliv zkratka) + Pravé kliknutí myší místo levého");
        add("invtweaks.help.shortcuts.drop", "Upustit");
        add("invtweaks.help.shortcuts.craftall", "Vyrobit vše do inventáře");
        add("invtweaks.help.shortcuts.craftone", "Vyrobit do inventáře");
        add("invtweaks.help.shortcuts.selectconfig", "Vyber nastavení řazení");
        add("invtweaks.help.shortcuts.forward", "(Dopředu)");
        add("invtweaks.help.shortcuts.backward", "(Dozadu)");
        add("invtweaks.keyboardfix.error", "[12] Mám potíže s klávesnicí");
        add("invtweaks.keyboardfix.recover", "Ok, opraveno, omlouvám se za to.");
        add("invtweaks.modcompat.ci.error", "[13] Nepodařilo se zajistit kompatibilitu s 'Convenient Inventory'");
        add("key.category.inventorytweaksrevived.invtweaks", "Inventory Tweaks");
        add("invtweaks.key.sort", "Řazení inventáře");
    }
}

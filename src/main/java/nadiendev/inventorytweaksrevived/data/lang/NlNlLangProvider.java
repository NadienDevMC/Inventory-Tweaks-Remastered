package nadiendev.inventorytweaksrevived.data.lang;

import net.minecraft.data.PackOutput;

public class NlNlLangProvider extends InvTweaksLangProvider {
    public NlNlLangProvider(PackOutput output) {
        super(output, "nl_nl");
    }

    @Override
    protected void addTranslations() {
        add("invtweaks.loadconfig.enabled", "'%s' ingeschakeld");
        add("invtweaks.loadconfig.refresh.error", "[1] Eigenschappen uit bestand vernieuwen mislukt");
        add("invtweaks.loadconfig.invalidkeywords", "Ongeldige trefwoorden gevonden");
        add("invtweaks.loadconfig.filemissing", "ontbreekt, standaard aan het aanmaken.");
        add("invtweaks.loadconfig.done", "Configuratie geladen.");
        add("invtweaks.propsfile.loaded", "Mod-eigenschappen geladen");
        add("invtweaks.propsfile.error", "[14] Aanmaken van bestand met mod-instellingen mislukt");
        add("invtweaks.extract.find.error", "[15] De mod zal niet werken, omdat %s niet kon worden gevonden!");
        add("invtweaks.extract.create.error", "[16] De mod zal niet werken, omdat het aanmaken van %s is mislukt!");
        add("invtweaks.sort.inventory.error", "[2] Sorteren inventaris mislukt");
        add("invtweaks.sort.chest.error", "[3] Sorteren container mislukt");
        add("invtweaks.sort.autorefill.error", "[4] Automatisch hervullen mislukt");
        add("invtweaks.sort.releaseitem.error", "[5] Vrijlaten voorwerp mislukt");
        add("invtweaks.shortcut.error", "[6] Sneltoets activeren mislukt");
        add("invtweaks.pickup.error", "[7] Verplaatsen opgepakte stapel mislukt");
        add("invtweaks.button.settings.tooltip", "Inventarisinstellingen");
        add("invtweaks.button.chest1.tooltip", "Standaardsortering");
        add("invtweaks.button.chest2.tooltip", "Sorteren in kolommen");
        add("invtweaks.button.chest3.tooltip", "Sorteren in rijen");
        add("invtweaks.settings.title", "Inventaris- en kistinstellingen");
        add("invtweaks.settings.on", "AAN");
        add("invtweaks.settings.off", "UIT");
        add("invtweaks.settings.key", "Sorteerknop:");
        add("invtweaks.settings.disableci", "CI uitschakelen");
        add("invtweaks.settings.disableci.tooltip", "Conflict met Convenient Inventory");
        add("invtweaks.settings.middleclick", "Middelklik");
        add("invtweaks.settings.middleclick.tooltip", "Staat het sorteren met middel-klik toe");
        add("invtweaks.settings.chestbuttons", "Kistknoppen");
        add("invtweaks.settings.chestbuttons.tooltip", "Voegt drie knoppen aan kisten toe om ze te sorteren");
        add("invtweaks.settings.shortcuts", "Sneltoetsen");
        add("invtweaks.settings.shortcuts.tooltip", "Schakelt verschillende sneltoetsen in om voorwerpen te verplaatsen");
        add("invtweaks.settings.autorefill", "Automatisch hervullen");
        add("invtweaks.settings.autorefill.tooltip", "Vervangt kapot gereedschap en leeggemaakte stapels met andere uit je inventaris");
        add("invtweaks.settings.beforebreak", "Reparatievriendelijk hervullen");
        add("invtweaks.settings.beforebreak.tooltip", "Vervangt gereedschap net voor het kapot gaat");
        add("invtweaks.settings.moreoptions", "Meer opties...");
        add("invtweaks.settings.moreoptions.tooltip", "Nog meer coole dingen");
        add("invtweaks.settings.rulesfile", "Sorteerregels openen...");
        add("invtweaks.settings.rulesfile.error", "[8] Openen van regelsbestand mislukt");
        add("invtweaks.settings.treefile", "Item tree openen...");
        add("invtweaks.settings.treefile.error", "[9] Tree-bestand openen mislukt");
        add("invtweaks.settings.onlinehelp", "Hulp openen in browser...");
        add("invtweaks.settings.onlinehelp.error", "[10] Hulp openen mislukt");
        add("invtweaks.help.bugsorting", "Werkt niet?");
        add("invtweaks.help.bugsorting.pt1", "Als je server lijkt te 'annuleren' wat deze mod doet,");
        add("invtweaks.help.bugsorting.pt2", "komt dat waarschijnlijk doordat deze gebruik maakt van de NoCheatPlus-plugin.");
        add("invtweaks.help.bugsorting.pt3", "Vraag je admin de volgende opties uit te zetten:");
        add("invtweaks.help.bugsorting.pt4", "\"inventory.fastclick\" en \"combined.improbable\"");
        add("invtweaks.settings.pvpwarning.pt1", "Opmerking: Op een PvP-server kan automatisch hervullen en auto-uitrusten van pantser");
        add("invtweaks.settings.pvpwarning.pt2", "gezien worden als valsspelen!");
        add("invtweaks.settings.exit", "Klaar");
        add("invtweaks.settings.advanced.sortonpickup", "Sorteren bij oppakken");
        add("invtweaks.settings.advanced.sortonpickup.soloonly", "Alleen SP");
        add("invtweaks.settings.advanced.sortonpickup.tooltip", "Vermijdt het vullen van de hotbar met alles wat je oppakt");
        add("invtweaks.settings.advanced.sortonpickup.tooltip.soloonly", "Alleen in singleplayer-modus");
        add("invtweaks.settings.advanced.autoequip", "Auto-uitrusten pantser");
        add("invtweaks.settings.advanced.autoequip.tooltip", "Draagt het beste armor bij het sorteren");
        add("invtweaks.settings.advanced.sounds", "Geluid aanzetten");
        add("invtweaks.settings.advanced.sounds.tooltip", "Geluid afspelen bij sorteren of automatisch hervullen");
        add("invtweaks.settings.advanced.mappingsfile", "Sneltoetsenbestand openen...");
        add("invtweaks.settings.advanced.mappingsfile.error", "[11] Sneltoetsenbestand openen mislukt");
        add("invtweaks.settings.advanced.serverassist", "Voorwerpverplaatsing netwerk");
        add("invtweaks.settings.advanced.serverassist.tooltip", "Serverassistentie bij voorwerpverplaatsing tijdens het sorteren aanzetten.");
        add("invtweaks.help.shortcuts.title", "Sneltoetsen");
        add("invtweaks.help.shortcuts.click", "Klik");
        add("invtweaks.help.shortcuts.or", "of");
        add("invtweaks.help.shortcuts.onestack", "Een stapel verplaatsen");
        add("invtweaks.help.shortcuts.oneitem", "Een voorwerp verplaatsen");
        add("invtweaks.help.shortcuts.allitems", "Alle voorwerpen van hetzelfde type verplaatsen");
        add("invtweaks.help.shortcuts.everything", "Alles verplaatsen");
        add("invtweaks.help.shortcuts.hotbar", "Naar hotbar verplaatsen");
        add("invtweaks.help.shortcuts.emptyslot", "Naar leeg vak verplaatsen");
        add("invtweaks.help.shortcuts.rightclick", "(Elke snelkoppeling) + rechter in plaats van linker muisknop");
        add("invtweaks.help.shortcuts.drop", "Laten vallen");
        add("invtweaks.help.shortcuts.craftall", "Alles naar inventaris vervaardigen");
        add("invtweaks.help.shortcuts.craftone", "Naar inventaris vervaardigen");
        add("invtweaks.help.shortcuts.selectconfig", "Soorteerconfiguratie selecteren");
        add("invtweaks.help.shortcuts.forward", "(Voorwaarts)");
        add("invtweaks.help.shortcuts.backward", "(Achterwaarts)");
        add("invtweaks.keyboardfix.error", "[12] Ik heb problemen met het toetsenbord");
        add("invtweaks.keyboardfix.recover", "Oké, het is gerepareerd, sorry daarvoor.");
        add("invtweaks.modcompat.ci.error", "[13] 'Convenient Inventory' comptabiliteit beheren mislukt");
        add("key.category.inventorytweaksrevived.invtweaks", "Inventarisaanpassingen");
        add("invtweaks.key.sort", "Inventarissortering");
    }
}

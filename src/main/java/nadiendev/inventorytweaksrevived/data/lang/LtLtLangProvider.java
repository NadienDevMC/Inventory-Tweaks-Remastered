package nadiendev.inventorytweaksrevived.data.lang;

import net.minecraft.data.PackOutput;

public class LtLtLangProvider extends InvTweaksLangProvider {
    public LtLtLangProvider(PackOutput output) {
        super(output, "lt_lt");
    }

    @Override
    protected void addTranslations() {
        add("invtweaks.loadconfig.enabled", "'%s' įjungtas");
        add("invtweaks.loadconfig.refresh.error", "[1] Nepavyko atnaujinti savybių iš failo");
        add("invtweaks.loadconfig.invalidkeywords", "Rasti negaliojantys raktažodžiai");
        add("invtweaks.loadconfig.filemissing", "nerasta, sukuriama numatytoji.");
        add("invtweaks.loadconfig.done", "Konfigūracija įkrauta.");
        add("invtweaks.propsfile.loaded", "Modifikacijos savybes įkrautos");
        add("invtweaks.propsfile.error", "[14] Nepavyko sukurti modifikacijos savybių failo");
        add("invtweaks.extract.find.error", "[15] Modifikacija neveiks, nes neįmanoma rasti %s!");
        add("invtweaks.extract.create.error", "[16] Modifikacija neveikia, nes %s sukurti nepavyko!");
        add("invtweaks.sort.inventory.error", "[2] Nepavyko surūšiuoti inventoriaus");
        add("invtweaks.sort.chest.error", "[3] Nepavyko surūšiuoti konteinerio");
        add("invtweaks.sort.autorefill.error", "[4] Nepavyko aktyvuoti automatinio pildymo");
        add("invtweaks.sort.releaseitem.error", "[5] Nepavyko paleisti daikto");
        add("invtweaks.shortcut.error", "[6] Nepavyko aktyvuoti nuorodos");
        add("invtweaks.pickup.error", "[7] Nepavyko perkelti surinktos krūvos");
        add("invtweaks.button.settings.tooltip", "Inventoriaus nustatymai");
        add("invtweaks.button.chest1.tooltip", "Numatytasis rūšiavimas");
        add("invtweaks.button.chest2.tooltip", "Rūšiuoti stulpeliais");
        add("invtweaks.button.chest3.tooltip", "Rūšiuoti eilėmis");
        add("invtweaks.settings.title", "Inventoriaus ir skrynių nustatymai");
        add("invtweaks.settings.on", "ĮJUNGTI");
        add("invtweaks.settings.off", "IŠJUNGTI");
        add("invtweaks.settings.key", "Rūšiavimo mygtukas:");
        add("invtweaks.settings.disableci", "Išjungti CI");
        add("invtweaks.settings.disableci.tooltip", "Prieštarauja patogiam inventoriui");
        add("invtweaks.settings.middleclick", "Vidurinis mygtukas");
        add("invtweaks.settings.middleclick.tooltip", "Leidžia rūšiuoti naudojant\n vidurinį mygtuką");
        add("invtweaks.settings.chestbuttons", "Skrynios mygtukai");
        add("invtweaks.settings.chestbuttons.tooltip", "Prideda tris mygtukus\n skryniose joms rūšiuoti");
        add("invtweaks.settings.shortcuts", "Spartieji klavišai");
        add("invtweaks.settings.shortcuts.tooltip", "Įgalina įvairias nuorodas\n, kad perkelti daiktus aplink");
        add("invtweaks.settings.autorefill", "Automatinis pildymas");
        add("invtweaks.settings.autorefill.tooltip", "Pakeičia sugadintų daiktų ir tuščių krūvų\n su kitais daiktais iš jūsų inventoriaus");
        add("invtweaks.settings.beforebreak", "Repair-friendly užpildymas");
        add("invtweaks.settings.beforebreak.tooltip", "Pakeičia įrankius prieš jiems sugendant");
        add("invtweaks.settings.moreoptions", "Daugiau nustatymų...");
        add("invtweaks.settings.moreoptions.tooltip", "Dar daugiau įdomių dalykų");
        add("invtweaks.settings.rulesfile", "Atidaryti rūšiavimo taisyklių failą...");
        add("invtweaks.settings.rulesfile.error", "[8] Nepavyko atidaryti taisyklių failo");
        add("invtweaks.settings.treefile", "Atidaryti daiktų medžio failą...");
        add("invtweaks.settings.treefile.error", "[9] Nepavyko atidaryti daiktų medžio failo");
        add("invtweaks.settings.onlinehelp", "Atidaryti pagalbą naršyklėje...");
        add("invtweaks.settings.onlinehelp.error", "[10] Nepavyko atidaryti pagalbos");
        add("invtweaks.help.bugsorting", "Neveikia?");
        add("invtweaks.help.bugsorting.pt1", "Jūsų serveris gali 'atšaukti' ką ši modifikacija daro,");
        add("invtweaks.help.bugsorting.pt2", "tai tikriausiai dėl NoCheatPlus įskiepio.");
        add("invtweaks.help.bugsorting.pt3", "Paprašykite administratoriaus išjungti šias parinktis:");
        add("invtweaks.help.bugsorting.pt4", "\"inventory.fastclick\" ir \"combined.improbable\"");
        add("invtweaks.settings.pvpwarning.pt1", "Pastaba: PvP serveriuose, automatinis užpildymas ir automatinis aprūpinimas šarvais");
        add("invtweaks.settings.pvpwarning.pt2", "parinktys yra dažnai laikomos sukčiavimu!");
        add("invtweaks.settings.exit", "Atlikta");
        add("invtweaks.settings.advanced.sortonpickup", "Rūšiuoti paėmus");
        add("invtweaks.settings.advanced.sortonpickup.soloonly", "Tik SP");
        add("invtweaks.settings.advanced.sortonpickup.tooltip", "Išvengia užpildyti greitąją juostą su\n bet kuo, ką jūs paėmėte");
        add("invtweaks.settings.advanced.sortonpickup.tooltip.soloonly", "Tik vieno žaidėjo režime");
        add("invtweaks.settings.advanced.autoequip", "Auto-aprūpinimas šarvais");
        add("invtweaks.settings.advanced.autoequip.tooltip", "Parenka geriausius galimus\n šarvus rūšiuojant");
        add("invtweaks.settings.advanced.sounds", "Įgalinti garsą");
        add("invtweaks.settings.advanced.sounds.tooltip", "Groti garsus kai rūšiuojama\narba kai suaktyvinamas automatinis pildymas");
        add("invtweaks.settings.advanced.mappingsfile", "Atidaryti nuorodų rodymo failą...");
        add("invtweaks.settings.advanced.mappingsfile.error", "[11] Nepavyko atidaryti rodymo failo");
        add("invtweaks.settings.advanced.serverassist", "Serverio daiktų judėjimas");
        add("invtweaks.settings.advanced.serverassist.tooltip", "Įgalina naudojant serverio pagalbininką perkėlimui kai rūšiuojama.");
        add("invtweaks.help.shortcuts.title", "Nuorodų pagalba");
        add("invtweaks.help.shortcuts.click", "Spustelėkite");
        add("invtweaks.help.shortcuts.or", "arba");
        add("invtweaks.help.shortcuts.onestack", "Perkelti vieną krūvą");
        add("invtweaks.help.shortcuts.oneitem", "Perkelti vieną daiktą");
        add("invtweaks.help.shortcuts.allitems", "Perkelti visus to pačio tipo daiktus");
        add("invtweaks.help.shortcuts.everything", "Perkelti viską");
        add("invtweaks.help.shortcuts.hotbar", "Perkelti į greitąją juostą");
        add("invtweaks.help.shortcuts.emptyslot", "Perkelti į tuščią langelį");
        add("invtweaks.help.shortcuts.rightclick", "(Bet kokia nuoroda) + Spustelėti dešinėje vietoje kairės");
        add("invtweaks.help.shortcuts.drop", "Išmesti");
        add("invtweaks.help.shortcuts.craftall", "Pagaminti viską į inventorių");
        add("invtweaks.help.shortcuts.craftone", "Pagaminti į inventorių");
        add("invtweaks.help.shortcuts.selectconfig", "Pasirinkite rūšiavimo konfigūraciją");
        add("invtweaks.help.shortcuts.forward", "(Į priekį)");
        add("invtweaks.help.shortcuts.backward", "(Atgal)");
        add("invtweaks.keyboardfix.error", "[12] Aš turiu problemų su klaviatūra");
        add("invtweaks.keyboardfix.recover", "Gerai, jis yra pataisomas, atsiprašau dėl to.");
        add("invtweaks.modcompat.ci.error", "[13] Nepavyko valdyti 'Patogus inventorius' suderinamumo");
        add("key.category.inventorytweaksrevived.invtweaks", "Inventory Tweaks");
        add("invtweaks.key.sort", "Inventoriaus rūšiavimas");
    }
}

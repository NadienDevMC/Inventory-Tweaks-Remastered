package nadiendev.inventorytweaksrevived.data.lang;

import net.minecraft.data.PackOutput;

public class EtEeLangProvider extends InvTweaksLangProvider {
    public EtEeLangProvider(PackOutput output) {
        super(output, "et_ee");
    }

    @Override
    protected void addTranslations() {
        add("invtweaks.loadconfig.enabled", "\"%s\" lubatud");
        add("invtweaks.loadconfig.refresh.error", "[1] Faili abil atribuutide värskendamine nurjus");
        add("invtweaks.loadconfig.invalidkeywords", "Sobimatud märksõnad leitud");
        add("invtweaks.loadconfig.filemissing", "puudub, loon uue vaikimisi väärtustega.");
        add("invtweaks.loadconfig.done", "Konfiguratsioon laaditud.");
        add("invtweaks.propsfile.loaded", "Mod'i atribuudid laaditud");
        add("invtweaks.propsfile.error", "[14] Mod'i atribuutide faili loomine nurjus");
        add("invtweaks.extract.find.error", "[15] Mod ei tööta, sest %s ei leitud!");
        add("invtweaks.extract.create.error", "[16] Mod ei tööta, sest %s loomine nurjus!");
        add("invtweaks.sort.inventory.error", "[2] Seljakoti sorteerimine nurjus");
        add("invtweaks.sort.chest.error", "[3] Anuma sorteerimine nurjus");
        add("invtweaks.sort.autorefill.error", "[4] Automaatse täitmise käivitamine nurjus");
        add("invtweaks.sort.releaseitem.error", "[5] Eseme vabastamine nurjus");
        add("invtweaks.shortcut.error", "[6] Otsetee käivitamine nurjus");
        add("invtweaks.pickup.error", "[7] Ülesvõetud kuhja liigutamine nurjus");
        add("invtweaks.button.settings.tooltip", "Seljakoti seaded");
        add("invtweaks.button.chest1.tooltip", "Vaikimisi sorteerimine");
        add("invtweaks.button.chest2.tooltip", "Sorteeri veergudes");
        add("invtweaks.button.chest3.tooltip", "Sorteeri ridades");
        add("invtweaks.settings.title", "Seljakoti ja kirstude seaded");
        add("invtweaks.settings.on", "SEES");
        add("invtweaks.settings.off", "VÄLJAS");
        add("invtweaks.settings.key", "Sorteerimisklahv:");
        add("invtweaks.settings.disableci", "Keela CI");
        add("invtweaks.settings.disableci.tooltip", "Konfliktis Convenient Inventory'ga");
        add("invtweaks.settings.middleclick", "Keskmine klõps");
        add("invtweaks.settings.middleclick.tooltip", "Võimaldab sorteerida\nkeskmise klõpsu abil");
        add("invtweaks.settings.chestbuttons", "Kirstu nupud");
        add("invtweaks.settings.chestbuttons.tooltip", "Lisab kirstudesse kolm nuppu\nnende sorteerimiseks");
        add("invtweaks.settings.shortcuts", "Otseteed");
        add("invtweaks.settings.shortcuts.tooltip", "Lubab erinevad otseteed\nesemete ringi liigutamiseks");
        add("invtweaks.settings.autorefill", "Automaatne täitmine");
        add("invtweaks.settings.autorefill.tooltip", "Asendab katkised tööriistad ja tühjendatud kuhjad\nteiste seljakotis leiduvatega");
        add("invtweaks.settings.beforebreak", "Parandussõbralik täitmine");
        add("invtweaks.settings.beforebreak.tooltip", "Asendab tööriistad enne nende purunemist");
        add("invtweaks.settings.moreoptions", "Rohkem valikuid...");
        add("invtweaks.settings.moreoptions.tooltip", "Veel rohkem lahedat kraami");
        add("invtweaks.settings.rulesfile", "Ava sorteerimise reeglite fail...");
        add("invtweaks.settings.rulesfile.error", "[8] Reeglite faili avamine nurjus");
        add("invtweaks.settings.treefile", "Ava esemepuufail...");
        add("invtweaks.settings.treefile.error", "[9] Esemepuufaili avamine nurjus");
        add("invtweaks.settings.onlinehelp", "Ava abimaterjal brauseris...");
        add("invtweaks.settings.onlinehelp.error", "[10] Abi avamine nurjus");
        add("invtweaks.help.bugsorting", "Ei tööta?");
        add("invtweaks.help.bugsorting.pt1", "Kui su server tundub \"tühistavat\" selle mod'i tegusid,");
        add("invtweaks.help.bugsorting.pt2", "on see arvatavasti põhjustatud NoCheatPlus'i plugina tööst.");
        add("invtweaks.help.bugsorting.pt3", "Palu oma adminil keelata järgnevad valikud:");
        add("invtweaks.help.bugsorting.pt4", "\"inventory.fastclick\" ja \"combined.improbable\"");
        add("invtweaks.settings.pvpwarning.pt1", "Märkus: PvP-serverites automaatse täitmise ja automaatse rüü varustamise");
        add("invtweaks.settings.pvpwarning.pt2", "valikuid peetakse tihti sohitegemiseks!");
        add("invtweaks.settings.exit", "Valmis");
        add("invtweaks.settings.advanced.sortonpickup", "Sorteeri ülesvõtmisel");
        add("invtweaks.settings.advanced.sortonpickup.soloonly", "Ainult ÜM");
        add("invtweaks.settings.advanced.sortonpickup.tooltip", "Väldib plokiriba täitmist kõigega,\nmida üles korjad");
        add("invtweaks.settings.advanced.sortonpickup.tooltip.soloonly", "Ainult üksikmängus");
        add("invtweaks.settings.advanced.autoequip", "Automaatne rüü varustamine");
        add("invtweaks.settings.advanced.autoequip.tooltip", "Varustab sind sorteerides\nparima saadava rüüga");
        add("invtweaks.settings.advanced.sounds", "Luba helid");
        add("invtweaks.settings.advanced.sounds.tooltip", "Mängi helisid, kui sorteerimine\nvõi automaatne täitmine on käivitatud");
        add("invtweaks.settings.advanced.mappingsfile", "Ava otseteede kaardistusfail...");
        add("invtweaks.settings.advanced.mappingsfile.error", "[11] Otseteede kaardistusfaili avamine nurjus");
        add("invtweaks.settings.advanced.serverassist", "Serveripoolne esemete liigutamine");
        add("invtweaks.settings.advanced.serverassist.tooltip", "Luba serveri abi kasutamine sorteeritavate esemete liigutamisel.");
        add("invtweaks.help.shortcuts.title", "Otseteede abi");
        add("invtweaks.help.shortcuts.click", "Klõpsa");
        add("invtweaks.help.shortcuts.or", "või");
        add("invtweaks.help.shortcuts.onestack", "Liiguta üht kuhja");
        add("invtweaks.help.shortcuts.oneitem", "Liiguta üht eset");
        add("invtweaks.help.shortcuts.allitems", "Liiguta kõiki sama tüüpi esemeid");
        add("invtweaks.help.shortcuts.everything", "Liiguta kõike");
        add("invtweaks.help.shortcuts.hotbar", "Liiguta plokiribale");
        add("invtweaks.help.shortcuts.emptyslot", "Liiguta tühja lahtrisse");
        add("invtweaks.help.shortcuts.rightclick", "(Mistahes otsetee) + paremklõps vasaku asemel");
        add("invtweaks.help.shortcuts.drop", "Viska maha");
        add("invtweaks.help.shortcuts.craftall", "Meisterda kõik seljakotti");
        add("invtweaks.help.shortcuts.craftone", "Meisterda seljakotti");
        add("invtweaks.help.shortcuts.selectconfig", "Vali sorteerimise konfiguratsioon");
        add("invtweaks.help.shortcuts.forward", "(Edasi)");
        add("invtweaks.help.shortcuts.backward", "(Tagasi)");
        add("invtweaks.keyboardfix.error", "[12] Mul on probleeme klaviatuuriga");
        add("invtweaks.keyboardfix.recover", "OK, see on parandatud, vabandame selle pärast.");
        add("invtweaks.modcompat.ci.error", "[13] \"Convenient Inventory\" ühilduvuse haldamine ebaõnnestus");
        add("key.category.inventorytweaksrevived.invtweaks", "Inventory Tweaks");
        add("invtweaks.key.sort", "Seljakoti sorteerimine");
    }
}

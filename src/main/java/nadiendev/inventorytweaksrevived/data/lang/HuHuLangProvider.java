package nadiendev.inventorytweaksrevived.data.lang;

import net.minecraft.data.PackOutput;

public class HuHuLangProvider extends InvTweaksLangProvider {
    public HuHuLangProvider(PackOutput output) {
        super(output, "hu_hu");
    }

    @Override
    protected void addTranslations() {
        add("invtweaks.loadconfig.enabled", "'%s' engedélyezve");
        add("invtweaks.loadconfig.refresh.error", "[1] Tulajdonságok betöltése a fájlból sikertelen");
        add("invtweaks.loadconfig.invalidkeywords", "Érvénytelen kulcsszavakat találtam");
        add("invtweaks.loadconfig.filemissing", "hiányzik, alapértelmezett létrehozva.");
        add("invtweaks.loadconfig.done", "Konfiguráció betöltve.");
        add("invtweaks.propsfile.loaded", "Mod beállítások betöltve");
        add("invtweaks.propsfile.error", "[14] Mod tulajdonságok fájl létrehozása sikertelen");
        add("invtweaks.extract.find.error", "[15] A mod nem fog működni, mert a %s nem található!");
        add("invtweaks.extract.create.error", "[16] A mod nem fog működni, mert a %s létrehozása sikertelen!");
        add("invtweaks.sort.inventory.error", "[2] Nem sikerült rendezni a leltárt");
        add("invtweaks.sort.chest.error", "[3] Nem sikerült rendezni a tárolót");
        add("invtweaks.sort.autorefill.error", "[4] Nem sikerült kiváltani az Auto-kitöltőt");
        add("invtweaks.sort.releaseitem.error", "[5] Nem sikerült a tárgy kiadása");
        add("invtweaks.shortcut.error", "[6] Nem sikerült a parancsikont kiváltani");
        add("invtweaks.pickup.error", "[7] Nem sikerült áthelyezni a felvett köteget");
        add("invtweaks.button.settings.tooltip", "Leltár beállítások");
        add("invtweaks.button.chest1.tooltip", "Alapértelmezett rendezés");
        add("invtweaks.button.chest2.tooltip", "Rendezés oszlopokban");
        add("invtweaks.button.chest3.tooltip", "Rendezés sorokban");
        add("invtweaks.settings.title", "Leltár és ládák beállításai");
        add("invtweaks.settings.on", "Bekapcsolás");
        add("invtweaks.settings.off", "Kikapcsolás");
        add("invtweaks.settings.key", "Rendezési gomb:");
        add("invtweaks.settings.disableci", "CI letiltása");
        add("invtweaks.settings.disableci.tooltip", "Kompatibilitási hiba a Convenient Inventory-val");
        add("invtweaks.settings.middleclick", "Egér középső gombjának kattintása");
        add("invtweaks.settings.middleclick.tooltip", "Lehetővéteszi rendezni a\n  az egér középső gombjának kattintásával");
        add("invtweaks.settings.chestbuttons", "Láda gombok");
        add("invtweaks.settings.chestbuttons.tooltip", "Hozzáad három gombot\na a ládákhoz, hogy rendezni lehessen őket");
        add("invtweaks.settings.shortcuts", "Gyorsbillentyűk");
        add("invtweaks.settings.shortcuts.tooltip", "Lehetővé teszi gyorsbillentyűk\nhasználatát a tárgyak mozgatására");
        add("invtweaks.settings.autorefill", "Auto-újratöltés");
        add("invtweaks.settings.autorefill.tooltip", "Kicseréli az eltört tárgyaidat\n és az elfogyott kötegeket, ha van a leltáradban");
        add("invtweaks.settings.beforebreak", "Javítás-Barát újratöltés");
        add("invtweaks.settings.beforebreak.tooltip", "Kicseréli a tárgyakat éppen az eltörésük előtt");
        add("invtweaks.settings.moreoptions", "További beállítások...");
        add("invtweaks.settings.moreoptions.tooltip", "További király dolgok");
        add("invtweaks.settings.rulesfile", "Rendezési szabályok fájl megnyitása...");
        add("invtweaks.settings.rulesfile.error", "[8] Hiba a szabályok megnyitásakor");
        add("invtweaks.settings.treefile", "A tárgy-fa megnyitása...");
        add("invtweaks.settings.treefile.error", "[9] Hiba a tárgy-fa megnyitásakor");
        add("invtweaks.settings.onlinehelp", "A súgó megnyitása a böngészőben...");
        add("invtweaks.settings.onlinehelp.error", "[10] Hiba a súgó megnyitásakor");
        add("invtweaks.help.bugsorting", "Nem működik?");
        add("invtweaks.help.bugsorting.pt1", "Ha szervered látszólag \"visszacsinálja\", amit a mod csinál,");
        add("invtweaks.help.bugsorting.pt2", "ezt valószínűleg a NoCheatPlus kiegészítő okozza.");
        add("invtweaks.help.bugsorting.pt3", "Kérd az adminisztrátortól, hogy tiltsa le a következő lehetőségeket:");
        add("invtweaks.help.bugsorting.pt4", "\"inventory.fastclick\" és \"combined.improbable\"");
        add("invtweaks.settings.pvpwarning.pt1", "Megjegyzés: PvP szervereken az auto-újratöltés és a páncél automatikus felvétele");
        add("invtweaks.settings.pvpwarning.pt2", "beállításokat néha csalásnak tekintik!");
        add("invtweaks.settings.exit", "Kész");
        add("invtweaks.settings.advanced.sortonpickup", "Felvétel alapján rendez");
        add("invtweaks.settings.advanced.sortonpickup.soloonly", "Csak SP");
        add("invtweaks.settings.advanced.sortonpickup.tooltip", "Elkerüli, hogy minden, amit felveszel\na gyors-sávba kerüljön");
        add("invtweaks.settings.advanced.sortonpickup.tooltip.soloonly", "Csak egyjátékos módban");
        add("invtweaks.settings.advanced.autoequip", "Automatán felveszi a Páncél-t");
        add("invtweaks.settings.advanced.autoequip.tooltip", "Felveszi automatán az elérhető legjobb\n páncélt");
        add("invtweaks.settings.advanced.sounds", "Hang engedélyezése");
        add("invtweaks.settings.advanced.sounds.tooltip", "Hang lejátszása sorba rakáskor\n vagy mikor automata kitöltés aktív");
        add("invtweaks.settings.advanced.mappingsfile", "A gyors-gomb beállítás fájl megnyitása...");
        add("invtweaks.settings.advanced.mappingsfile.error", "[11] Hiba a gyorsgomb fájl megnyitásával");
        add("invtweaks.settings.advanced.serverassist", "Server Tárgyak Mozgatása");
        add("invtweaks.settings.advanced.serverassist.tooltip", "Engedélyezze a szerver támogatását, mikor a tárgyakat rendezi.");
        add("invtweaks.help.shortcuts.title", "Gyorsgomb segítség");
        add("invtweaks.help.shortcuts.click", "Kattintás");
        add("invtweaks.help.shortcuts.or", "vagy");
        add("invtweaks.help.shortcuts.onestack", "Köteget mozgat");
        add("invtweaks.help.shortcuts.oneitem", "Tárgyat mozgat");
        add("invtweaks.help.shortcuts.allitems", "Az összes ugyanolyan tárgy mozgatása");
        add("invtweaks.help.shortcuts.everything", "Mindent mozgat");
        add("invtweaks.help.shortcuts.hotbar", "Mozgatás a gyorssávra");
        add("invtweaks.help.shortcuts.emptyslot", "Mozgatás üres helyre -gyorssávon-");
        add("invtweaks.help.shortcuts.rightclick", "(Bármilyen gyors billentyű) + jobb helyett a BAL gombbal");
        add("invtweaks.help.shortcuts.drop", "Eldob");
        add("invtweaks.help.shortcuts.craftall", "Mindent berakni a leltárból");
        add("invtweaks.help.shortcuts.craftone", "Belerakni a leltásba");
        add("invtweaks.help.shortcuts.selectconfig", "Válassza ki a rendezési beállítást");
        add("invtweaks.help.shortcuts.forward", "(Előre)");
        add("invtweaks.help.shortcuts.backward", "(Vissza)");
        add("invtweaks.keyboardfix.error", "[12] Hiba a billentyűzettel");
        add("invtweaks.keyboardfix.recover", "Rendben helyreállítottam a problémát, bocs miatta.");
        add("invtweaks.modcompat.ci.error", "[13] 'Convenient Inventory' kompatibilitás megoldása sikertelen");
        add("key.category.inventorytweaksrevived.invtweaks", "Leltár finomhangolás");
        add("invtweaks.key.sort", "Leltár Rendezés");
    }
}

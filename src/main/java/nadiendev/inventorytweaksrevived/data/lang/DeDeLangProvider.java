package nadiendev.inventorytweaksrevived.data.lang;

import net.minecraft.data.PackOutput;

public class DeDeLangProvider extends InvTweaksLangProvider {
    public DeDeLangProvider(PackOutput output) {
        super(output, "de_de");
    }

    @Override
    protected void addTranslations() {
        add("invtweaks.loadconfig.enabled", "'%s' aktiviert");
        add("invtweaks.loadconfig.refresh.error", "[1] Fehler beim Aktualisieren der Eigenschaften-Datei");
        add("invtweaks.loadconfig.invalidkeywords", "Ungültige Stichworte gefunden");
        add("invtweaks.loadconfig.filemissing", "fehlt, erstelle Standard.");
        add("invtweaks.loadconfig.done", "Konfiguration geladen.");
        add("invtweaks.propsfile.loaded", "Mod Eigenschaften geladen");
        add("invtweaks.propsfile.error", "[14]Fehler bei der Erstellung der Konfigurations-Datei");
        add("invtweaks.extract.find.error", "[15] Die Mod wird nicht funktionieren, da %s nicht gefunden werden konnte!");
        add("invtweaks.extract.create.error", "[16] Die Mod wird nicht funktionieren, weil %s fehlgeschlug!");
        add("invtweaks.sort.inventory.error", "[2] Konnte Inventar nicht sortieren");
        add("invtweaks.sort.chest.error", "[3] Fehler beim Sortieren des Behälters");
        add("invtweaks.sort.autorefill.error", "[4] Fehler beim Ausführen des automatischen Auffüllens");
        add("invtweaks.sort.releaseitem.error", "[5] Fehler beim Element freigeben");
        add("invtweaks.shortcut.error", "[6] Fehler bei der Auslösung der Tastenkombi");
        add("invtweaks.pickup.error", "[7] Konnte aufgenommenen Stapel nicht bewegen");
        add("invtweaks.button.settings.tooltip", "Inventar Einstellungen");
        add("invtweaks.button.chest1.tooltip", "Standard Sortierung");
        add("invtweaks.button.chest2.tooltip", "Sortieren in Spalten");
        add("invtweaks.button.chest3.tooltip", "In Zeilen sortieren");
        add("invtweaks.settings.title", "Inventar und Truhen Einstellungen");
        add("invtweaks.settings.on", "AN");
        add("invtweaks.settings.off", "AUS");
        add("invtweaks.settings.key", "Sortierungstaste:");
        add("invtweaks.settings.disableci", "CI deaktivieren");
        add("invtweaks.settings.disableci.tooltip", "In Konflikt mit dem Mod Convenient Inventory");
        add("invtweaks.settings.middleclick", "mittlere Maustaste");
        add("invtweaks.settings.middleclick.tooltip", "Ermöglicht das Sortieren\nmit der mittleren Maustaste");
        add("invtweaks.settings.chestbuttons", "Truhen-Schaltflächen");
        add("invtweaks.settings.chestbuttons.tooltip", "Fügt drei Schaltflächen\nauf Truhen hinzu, um sie zu sortieren");
        add("invtweaks.settings.shortcuts", "Verknüpfungen");
        add("invtweaks.settings.shortcuts.tooltip", "Ermöglicht verschiedenen Tasten,\nItems zu bewegen");
        add("invtweaks.settings.autorefill", "Automatisches Auffüllen");
        add("invtweaks.settings.autorefill.tooltip", "Ersetzt kaputte Werkzeuge & verbrauchte\nStapel mit neuen aus dem Inventar");
        add("invtweaks.settings.beforebreak", "Zur Reperatur aufheben");
        add("invtweaks.settings.beforebreak.tooltip", "Tauscht Werkzeuge aus, bevor sie zerbrechen");
        add("invtweaks.settings.moreoptions", "Mehr Optionen...");
        add("invtweaks.settings.moreoptions.tooltip", "Noch mehr coole Sachen");
        add("invtweaks.settings.rulesfile", "Sortierungsregeln öffnen...");
        add("invtweaks.settings.rulesfile.error", "[8] Fehler beim Öffnen der Regel-Datei");
        add("invtweaks.settings.treefile", "Öffne die Item-Baum-Datei...");
        add("invtweaks.settings.treefile.error", "[9] Fehler beim Baum-Datei öffnen");
        add("invtweaks.settings.onlinehelp", "Öffnen der Hilfe im Browser...");
        add("invtweaks.settings.onlinehelp.error", "[10] Fehler beim Öffnen der Hilfe");
        add("invtweaks.help.bugsorting", "Funktioniert es nicht?");
        add("invtweaks.help.bugsorting.pt1", "Falls dein Server diese Mod blockiert,");
        add("invtweaks.help.bugsorting.pt2", "benutzt er vermutlich NoCheatPlus.");
        add("invtweaks.help.bugsorting.pt3", "Bitte deinen Admin, diese Einstellungen zu deaktivieren:");
        add("invtweaks.help.bugsorting.pt4", "\"inventory.fastclick\" und \"combined.improbable\"");
        add("invtweaks.settings.pvpwarning.pt1", "Hinweis: Auf PvP-Servern werden die Auto-Auffüllen und Auto-Ausrüsten-");
        add("invtweaks.settings.pvpwarning.pt2", "Funktionen oft als Schummeln angesehen!");
        add("invtweaks.settings.exit", "Fertig");
        add("invtweaks.settings.advanced.sortonpickup", "Beim Aufnehmen sortieren");
        add("invtweaks.settings.advanced.sortonpickup.soloonly", "Nur SP");
        add("invtweaks.settings.advanced.sortonpickup.tooltip", "Vermeidet, dass sich die Auswahlleiste\n mit jedem Gegenstand füllt, den du aufnimmst");
        add("invtweaks.settings.advanced.sortonpickup.tooltip.soloonly", "Nur im Einzelspieler-Modus");
        add("invtweaks.settings.advanced.autoequip", "Rüstung automatisch anziehen");
        add("invtweaks.settings.advanced.autoequip.tooltip", "Zieht die bestmögliche\nRüstung beim Sortieren an");
        add("invtweaks.settings.advanced.sounds", "Sound aktivieren");
        add("invtweaks.settings.advanced.sounds.tooltip", "Spiele beim Sortieren und\nbeim automatischen Nachfüllen Töne ab");
        add("invtweaks.settings.advanced.mappingsfile", "Öffnen Sie die Verknüpfungs-Zuordnungs-Datei ...");
        add("invtweaks.settings.advanced.mappingsfile.error", "[11] Fehler beim Öffnen der Zuordnungsdatei");
        add("invtweaks.settings.advanced.serverassist", "Server-Item-Bewegung");
        add("invtweaks.settings.advanced.serverassist.tooltip", "Nutze Hilfe des Servers beim Sortieren von Gegenständen.");
        add("invtweaks.help.shortcuts.title", "Tastenkombinationen-Hilfe");
        add("invtweaks.help.shortcuts.click", "Klick");
        add("invtweaks.help.shortcuts.or", "oder");
        add("invtweaks.help.shortcuts.onestack", "Bewegen Sie einen Stapel");
        add("invtweaks.help.shortcuts.oneitem", "Verschieben eines Elements");
        add("invtweaks.help.shortcuts.allitems", "Alle Items gleichen Typs verschieben");
        add("invtweaks.help.shortcuts.everything", "Bewegen Sie alles");
        add("invtweaks.help.shortcuts.hotbar", "zur Hotbar verschieben");
        add("invtweaks.help.shortcuts.emptyslot", "In leeren Platz verschieben");
        add("invtweaks.help.shortcuts.rightclick", "(Jede Tastenkombi)+ rechts statt links klicken");
        add("invtweaks.help.shortcuts.drop", "Fallen lassen");
        add("invtweaks.help.shortcuts.craftall", "Alles herstellen und ins Inventar legen");
        add("invtweaks.help.shortcuts.craftone", "Herstellen und im Inventar ablegen");
        add("invtweaks.help.shortcuts.selectconfig", "Wählen Sie Ihre Sortier-Konfiguration");
        add("invtweaks.help.shortcuts.forward", "(Nächstes)");
        add("invtweaks.help.shortcuts.backward", "(Vorheriges)");
        add("invtweaks.keyboardfix.error", "[12] Ich habe Probleme mit der Tastatur");
        add("invtweaks.keyboardfix.recover", "OK, es ist repariert, Entschuldigung.");
        add("invtweaks.modcompat.ci.error", "[13] Konnte Kompatibilität mit 'Convenient Inventory' nicht verwalten");
        add("key.category.inventorytweaksrevived.invtweaks", "Inventory Tweaks");
        add("invtweaks.key.sort", "Inventarsortierung");
    }
}

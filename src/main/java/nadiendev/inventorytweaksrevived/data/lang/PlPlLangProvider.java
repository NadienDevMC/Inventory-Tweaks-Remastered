package nadiendev.inventorytweaksrevived.data.lang;

import net.minecraft.data.PackOutput;

public class PlPlLangProvider extends InvTweaksLangProvider {
    public PlPlLangProvider(PackOutput output) {
        super(output, "pl_pl");
    }

    @Override
    protected void addTranslations() {
        add("invtweaks.loadconfig.enabled", "'%s' włączone");
        add("invtweaks.loadconfig.refresh.error", "Nie udało się odświeżyć ustawień z pliku");
        add("invtweaks.loadconfig.invalidkeywords", "Znaleziono nieprawidłowe słowa kluczowe");
        add("invtweaks.loadconfig.filemissing", "Brakuje, tworzę nowy plik domyślny");
        add("invtweaks.loadconfig.done", "Konfiguracja załadowana.");
        add("invtweaks.propsfile.loaded", "Właściwości moda załadowane");
        add("invtweaks.propsfile.error", "Nie udało się utworzyć pliku właściwości modyfikacji");
        add("invtweaks.extract.find.error", "Mod nie zadziała, ponieważ nie można odnaleźć %s!");
        add("invtweaks.extract.create.error", "Mod nie zadziała, ponieważ tworzenie %s się nie powiodło!");
        add("invtweaks.sort.inventory.error", "Nie udało się posortować ekwipunku");
        add("invtweaks.sort.chest.error", "Nie udało się posortować skrzyni");
        add("invtweaks.sort.autorefill.error", "Nie udało się uruchomić auto-podmiany");
        add("invtweaks.sort.releaseitem.error", "[5] Nie udało się puścić przedmiotu");
        add("invtweaks.shortcut.error", "Nie udało się wywołać skrótu");
        add("invtweaks.pickup.error", "Nie można przenieść podniesionego stosu");
        add("invtweaks.button.settings.tooltip", "Ustawienia ekwipunku");
        add("invtweaks.button.chest1.tooltip", "Domyślne sortowanie");
        add("invtweaks.button.chest2.tooltip", "Sortuj kolumnami");
        add("invtweaks.button.chest3.tooltip", "Sortuj rzędami");
        add("invtweaks.settings.title", "Ustawienia ekwipunku i skrzyń");
        add("invtweaks.settings.on", "Włączone");
        add("invtweaks.settings.off", "Wyłączone");
        add("invtweaks.settings.key", "Klawisz sortowania:");
        add("invtweaks.settings.disableci", "Wyłącz CI");
        add("invtweaks.settings.disableci.tooltip", "Konfliktuje z Convenient Inventory");
        add("invtweaks.settings.middleclick", "Środkowy przycisk");
        add("invtweaks.settings.middleclick.tooltip", "Pozwala sortować używając środkowego przycisku myszy");
        add("invtweaks.settings.chestbuttons", "Przyciski w skrzyni");
        add("invtweaks.settings.chestbuttons.tooltip", "Dodaje trzy guziki \n do skrzyni aby sortować ekwipunek");
        add("invtweaks.settings.shortcuts", "Skróty klawiaturowe");
        add("invtweaks.settings.shortcuts.tooltip", "Pozwala używać różnych skrótów klawiaturowych, ułatwiających przenoszenie przedmiotów");
        add("invtweaks.settings.autorefill", "Autopodmiana");
        add("invtweaks.settings.autorefill.tooltip", "Podmienia zniszczone narzędzia i zużyte stacki innymi z Twojego ekwipunku");
        add("invtweaks.settings.beforebreak", "Autopodmiana pomagająca w naprawie");
        add("invtweaks.settings.beforebreak.tooltip", "Podmienia narzędzia tuż przed ich zniszczeniem się");
        add("invtweaks.settings.moreoptions", "Więcej opcji...");
        add("invtweaks.settings.moreoptions.tooltip", "Jeszcze więcej świetnych rzeczy");
        add("invtweaks.settings.rulesfile", "Otwórz plik zasad sortowania...");
        add("invtweaks.settings.rulesfile.error", "[8] Nie udało się otworzyć pliku zasad sortowania");
        add("invtweaks.settings.treefile", "Otwórz plik drzewa przedmiotów...");
        add("invtweaks.settings.treefile.error", "[9] Nie udało się otworzyć pliku drzewa przedmiotów");
        add("invtweaks.settings.onlinehelp", "Otwórz pomoc w domyślnej przeglądarce...");
        add("invtweaks.settings.onlinehelp.error", "[10] Nie udało się otworzyć pomocy");
        add("invtweaks.help.bugsorting", "Nie działa?");
        add("invtweaks.help.bugsorting.pt1", "Jeśli wygląda na to, że Twój serwer \"anuluje\" działania tego moda,");
        add("invtweaks.help.bugsorting.pt2", "jest to prawdopodobnie spowodowane używaniem pluginu NoCheatPlus.");
        add("invtweaks.help.bugsorting.pt3", "Poproś administratora, aby wyłączył następujące opcje:");
        add("invtweaks.help.bugsorting.pt4", "\"inventory.fastclick\" oraz \"combined.improbable\"");
        add("invtweaks.settings.pvpwarning.pt1", "Uwaga: na serwerach PvP, auto-podmiana & auto-zakładanie zbroi");
        add("invtweaks.settings.pvpwarning.pt2", "jest uznawane za oszustwo!");
        add("invtweaks.settings.exit", "Gotowe");
        add("invtweaks.settings.advanced.sortonpickup", "Sortuj po podniesieniu");
        add("invtweaks.settings.advanced.sortonpickup.soloonly", "Tylko tryb jednoosobowy");
        add("invtweaks.settings.advanced.sortonpickup.tooltip", "Unika wypełniania paska akcji \n wszystkim co podniesiesz");
        add("invtweaks.settings.advanced.sortonpickup.tooltip.soloonly", "Tylko w trybie dla jednego gracza");
        add("invtweaks.settings.advanced.autoequip", "Auto-zakładanie zbroi");
        add("invtweaks.settings.advanced.autoequip.tooltip", "Ubiera najlepszą dostępną \n zbroje gdy sortuje");
        add("invtweaks.settings.advanced.sounds", "Włącz dźwięk");
        add("invtweaks.settings.advanced.sounds.tooltip", "Odtwarzanie dźwięku gdy sortuje \n lub gdy wypełnia automatycznie");
        add("invtweaks.settings.advanced.mappingsfile", "Otwórz plik mapowania skrótów...");
        add("invtweaks.settings.advanced.mappingsfile.error", "Nie udało się otworzyć pliku mapowania");
        add("invtweaks.settings.advanced.serverassist", "Ruch przedm. przy pomocy serwera");
        add("invtweaks.settings.advanced.serverassist.tooltip", "Korzystaj z pomocy serwera do przenoszenia przedmiotów podczas sortowania");
        add("invtweaks.help.shortcuts.title", "Pomoc");
        add("invtweaks.help.shortcuts.click", "Kliknięcie");
        add("invtweaks.help.shortcuts.or", "lub");
        add("invtweaks.help.shortcuts.onestack", "Przenieś jeden stack");
        add("invtweaks.help.shortcuts.oneitem", "Przenieś jeden przedmiot");
        add("invtweaks.help.shortcuts.allitems", "Przenieś wszystkie przedmioty tego samego typu");
        add("invtweaks.help.shortcuts.everything", "Przenieś wszystko");
        add("invtweaks.help.shortcuts.hotbar", "Przenieś na pasek szybkiego wyboru");
        add("invtweaks.help.shortcuts.emptyslot", "Przenieś na wolny slot");
        add("invtweaks.help.shortcuts.rightclick", "(każdy skrót) + prawy przycisk myszy zamiast lewego");
        add("invtweaks.help.shortcuts.drop", "Upuść");
        add("invtweaks.help.shortcuts.craftall", "Wytwórz wszystko do ekwipunku");
        add("invtweaks.help.shortcuts.craftone", "Wytwórz do ekwipunku");
        add("invtweaks.help.shortcuts.selectconfig", "Wybierz konfigurację sortowania");
        add("invtweaks.help.shortcuts.forward", "(Do przodu)");
        add("invtweaks.help.shortcuts.backward", "(Do tyłu)");
        add("invtweaks.keyboardfix.error", "[12] Mam problemy z klawiaturą");
        add("invtweaks.keyboardfix.recover", "OK, już naprawione, przepraszam za tamto");
        add("invtweaks.modcompat.ci.error", "[13] Nie udało się zaradzić niezgodności z modem Convenient Inventory");
        add("key.category.inventorytweaksrevived.invtweaks", "Inventory Tweaks");
        add("invtweaks.key.sort", "Sortowanie ekwipunku");
    }
}

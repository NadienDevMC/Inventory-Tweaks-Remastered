package nadiendev.inventorytweaksrevived.data.lang;

import net.minecraft.data.PackOutput;

public class ItItLangProvider extends InvTweaksLangProvider {
    public ItItLangProvider(PackOutput output) {
        super(output, "it_it");
    }

    @Override
    protected void addTranslations() {
        add("invtweaks.loadconfig.enabled", "'%s' abilitato");
        add("invtweaks.loadconfig.refresh.error", "[1] Impossibile aggiornare le proprietà dal file");
        add("invtweaks.loadconfig.invalidkeywords", "Trovate parole chiavi non valide");
        add("invtweaks.loadconfig.filemissing", "mancante, creazione di default.");
        add("invtweaks.loadconfig.done", "Configurazione caricata.");
        add("invtweaks.propsfile.loaded", "Proprietà mod caricate");
        add("invtweaks.propsfile.error", "[14] Non è riuscito a creare il file delle proprietà della mod");
        add("invtweaks.extract.find.error", "[15] La mod non funziona, perché %s non è stato trovato!");
        add("invtweaks.extract.create.error", "[15] La mod non funziona, perché la creazione di%s non è riuscita!");
        add("invtweaks.sort.inventory.error", "[2] Fallito l'ordinamento dell'inventario");
        add("invtweaks.sort.chest.error", "[3] Fallito l'ordinamento del contenitore");
        add("invtweaks.sort.autorefill.error", "[4] Fallita la ricarica automatica");
        add("invtweaks.sort.releaseitem.error", "[5] Fallito il rilascio dell'oggetto");
        add("invtweaks.shortcut.error", "[6] Fallito l'utilizzo del comando di scelta rapida");
        add("invtweaks.pickup.error", "[7] Fallito lo spostamento dello stack");
        add("invtweaks.button.settings.tooltip", "Impostazioni dell'inventario");
        add("invtweaks.button.chest1.tooltip", "Ordinamento di default");
        add("invtweaks.button.chest2.tooltip", "Ordina per colonne");
        add("invtweaks.button.chest3.tooltip", "Ordina per righe");
        add("invtweaks.settings.title", "Impostazioni di inventario e cassapanche");
        add("invtweaks.settings.on", "ON");
        add("invtweaks.settings.off", "OFF");
        add("invtweaks.settings.key", "Chiave di ordinamento:");
        add("invtweaks.settings.disableci", "Disabilitare CI");
        add("invtweaks.settings.disableci.tooltip", "In conflitto con 'Convenient Inventory'");
        add("invtweaks.settings.middleclick", "Click centrale");
        add("invtweaks.settings.middleclick.tooltip", "Permette di ordinare\ntramite il clic centrale");
        add("invtweaks.settings.chestbuttons", "Pulsanti nelle cassapanche");
        add("invtweaks.settings.chestbuttons.tooltip", "Aggiunge tre pulsanti sulle ceste per ordinarle");
        add("invtweaks.settings.shortcuts", "Tasti di scelta rapida");
        add("invtweaks.settings.shortcuts.tooltip", "Abilitare varie scorciatoie\nper spostare gli elementi");
        add("invtweaks.settings.autorefill", "Ricarica automatica");
        add("invtweaks.settings.autorefill.tooltip", "Sostituisce gli strumenti rotti e gli stack svuotati\ncon gli altri dal vostro inventario");
        add("invtweaks.settings.beforebreak", "Riparazione amichevole");
        add("invtweaks.settings.beforebreak.tooltip", "Sostituisce gli strumenti appena prima che si rompono");
        add("invtweaks.settings.moreoptions", "Più opzioni...");
        add("invtweaks.settings.moreoptions.tooltip", "Opzioni ulteriori");
        add("invtweaks.settings.rulesfile", "Aprire il file delle regole di ordinamento...");
        add("invtweaks.settings.rulesfile.error", "[8] Impossibile aprire il file delle regole");
        add("invtweaks.settings.treefile", "Aprire il file ad albero degli elementi...");
        add("invtweaks.settings.treefile.error", "[9] Impossibile aprire il file ad albero");
        add("invtweaks.settings.onlinehelp", "Aprire l'help nel browser...");
        add("invtweaks.settings.onlinehelp.error", "[10] Non è riuscito ad aprire la guida");
        add("invtweaks.help.bugsorting", "Non funziona?");
        add("invtweaks.help.bugsorting.pt1", "Se ti sembra che il server annulli ciò che fa questa mod,");
        add("invtweaks.help.bugsorting.pt2", "è probabilmente causato dall'utilizzo del plugin \"NoCheatPlus\".");
        add("invtweaks.help.bugsorting.pt3", "Chiedi all'admin di disabilitare le seguentii opzioni:");
        add("invtweaks.help.bugsorting.pt4", "\"inventory.fastclick\" e \"combined.improbable\"");
        add("invtweaks.settings.pvpwarning.pt1", "NOta: Nei server PvP, l'auto-ricarica e l'auto-equipaggiamento dell'armatura");
        add("invtweaks.settings.pvpwarning.pt2", "spesso sono considerati barare!");
        add("invtweaks.settings.exit", "Fatto");
        add("invtweaks.settings.advanced.sortonpickup", "Ordina dopo aver raccolto");
        add("invtweaks.settings.advanced.sortonpickup.soloonly", "solo SP");
        add("invtweaks.settings.advanced.sortonpickup.tooltip", "Evita di riempire la hotbar con\ntutto quello che viene raccolto");
        add("invtweaks.settings.advanced.sortonpickup.tooltip.soloonly", "Solo in modalità giocatore singolo");
        add("invtweaks.settings.advanced.autoequip", "Auto-equipaggiamento armatura");
        add("invtweaks.settings.advanced.autoequip.tooltip", "Equipaggia la migliore armatura\ndisponibile quando ordini");
        add("invtweaks.settings.advanced.sounds", "Abilitare il suono");
        add("invtweaks.settings.advanced.sounds.tooltip", "Riprodurre i suoni durante l'ordinamento\no quando viene attivata la ricarica automatica");
        add("invtweaks.settings.advanced.mappingsfile", "Aprire il file di mapping dei tasti di scelta rapida...");
        add("invtweaks.settings.advanced.mappingsfile.error", "[11] Impossibile aprire il file di mapping");
        add("invtweaks.settings.advanced.serverassist", "Movimento degli oggetti nel server");
        add("invtweaks.settings.advanced.serverassist.tooltip", "Abilita l'assistenza server per l'ordine degli elementi durante lo spostamento.");
        add("invtweaks.help.shortcuts.title", "Aiuto Tasti di scelta rapida");
        add("invtweaks.help.shortcuts.click", "Click");
        add("invtweaks.help.shortcuts.or", "o");
        add("invtweaks.help.shortcuts.onestack", "Muovi un blocco");
        add("invtweaks.help.shortcuts.oneitem", "Muovi un singolo oggetto");
        add("invtweaks.help.shortcuts.allitems", "Muovi tutti gli oggetti dello stesso tipo");
        add("invtweaks.help.shortcuts.everything", "Muovi tutto");
        add("invtweaks.help.shortcuts.hotbar", "Sposta nella barra veloce");
        add("invtweaks.help.shortcuts.emptyslot", "Sposta in uno slot libero");
        add("invtweaks.help.shortcuts.rightclick", "(Qualsiasi scorciatoia) + click destro anziché sinistro");
        add("invtweaks.help.shortcuts.drop", "Fai cadere");
        add("invtweaks.help.shortcuts.craftall", "Costruisci tutto nell'inventario");
        add("invtweaks.help.shortcuts.craftone", "Costruisci nell'inventario");
        add("invtweaks.help.shortcuts.selectconfig", "Selezionare configurazione di ordinamento");
        add("invtweaks.help.shortcuts.forward", "(Avanti)");
        add("invtweaks.help.shortcuts.backward", "(Indietro)");
        add("invtweaks.keyboardfix.error", "[12] Sto avendo problemi con la tastiera");
        add("invtweaks.keyboardfix.recover", "OK è riparato, mi dispiace.");
        add("invtweaks.modcompat.ci.error", "[13] Non è riuscito a gestire la compatibilità 'Inventario conveniente'");
        add("invtweaks.key.category", "Inventory Tweaks");
        add("invtweaks.key.sort", "Ordinamento dell'inventario");
    }
}

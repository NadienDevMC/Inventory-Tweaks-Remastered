package nadiendev.inventorytweaksrevived.data.lang;

import net.minecraft.data.PackOutput;

public class FrCaLangProvider extends InvTweaksLangProvider {
    public FrCaLangProvider(PackOutput output) {
        super(output, "fr_ca");
    }

    @Override
    protected void addTranslations() {
        add("invtweaks.loadconfig.enabled", "\"%s\" activé");
        add("invtweaks.loadconfig.refresh.error", "[1] Impossible de recharger la configuration à partir du fichier");
        add("invtweaks.loadconfig.invalidkeywords", "Mots-clés invalides détectés");
        add("invtweaks.loadconfig.filemissing", "est manquant. Création et chargement de celui par défaut...");
        add("invtweaks.loadconfig.done", "Configuration chargée avec succès");
        add("invtweaks.propsfile.loaded", "Paramètres du mod chargés");
        add("invtweaks.propsfile.error", "[14] Impossible de créer le fichier de paramètres du mod");
        add("invtweaks.extract.find.error", "[15] Le mod ne fonctionnera pas, car le fichier %s est introuvable !");
        add("invtweaks.extract.create.error", "[16] Le mod ne fonctionnera pas, car la création du fichier %s a échoué !");
        add("invtweaks.sort.inventory.error", "[2] Impossible de trier l'inventaire");
        add("invtweaks.sort.chest.error", "[3] Impossible de trier le contenant");
        add("invtweaks.sort.autorefill.error", "[4] Impossible d'effectuer l'auto-remplacement");
        add("invtweaks.sort.releaseitem.error", "[5] Impossible de déposer l'objet tenu");
        add("invtweaks.shortcut.error", "[6] Impossible d'activer le raccourci");
        add("invtweaks.pickup.error", "[7] Impossible de déplacer l'objet ou le stack d'objets ramassé");
        add("invtweaks.button.settings.tooltip", "Options de l'inventaire");
        add("invtweaks.button.chest1.tooltip", "Tri par défaut");
        add("invtweaks.button.chest2.tooltip", "Trier en colonnes");
        add("invtweaks.button.chest3.tooltip", "Trier en lignes");
        add("invtweaks.settings.title", "Options pour les coffres et l'inventaire");
        add("invtweaks.settings.on", "Oui");
        add("invtweaks.settings.off", "Non");
        add("invtweaks.settings.key", "Touche de tri :");
        add("invtweaks.settings.disableci", "Désactiver CI ");
        add("invtweaks.settings.disableci.tooltip", "En conflit avec Convenient Inventory");
        add("invtweaks.settings.middleclick", "Clic central ");
        add("invtweaks.settings.middleclick.tooltip", "Permet de trier en\nutilisant le clic central");
        add("invtweaks.settings.chestbuttons", "Boutons de coffres ");
        add("invtweaks.settings.chestbuttons.tooltip", "Ajoute trois boutons sur\nles coffres pour les trier");
        add("invtweaks.settings.shortcuts", "Raccourcis ");
        add("invtweaks.settings.shortcuts.tooltip", "Active divers raccourcis\npour déplacer les objets");
        add("invtweaks.settings.autorefill", "Auto-remplacement ");
        add("invtweaks.settings.autorefill.tooltip", "Remplace les outils cassés et\nles stacks vidés par d'autres\nprovenant de votre inventaire");
        add("invtweaks.settings.beforebreak", "Remplacement d'usure ");
        add("invtweaks.settings.beforebreak.tooltip", "Remplace les outils juste\navant qu'ils ne cassent");
        add("invtweaks.settings.moreoptions", "Plus d'options...");
        add("invtweaks.settings.moreoptions.tooltip", "Encore plus de trucs pratiques !");
        add("invtweaks.settings.rulesfile", "Ouvrir le fichier des règles de tri...");
        add("invtweaks.settings.rulesfile.error", "[8] Impossible d'ouvrir le fichier des règles de tri");
        add("invtweaks.settings.treefile", "Ouvrir le fichier d'arbre d'objets...");
        add("invtweaks.settings.treefile.error", "[9] Impossible d'ouvrir le fichier d'arbre d'objets");
        add("invtweaks.settings.onlinehelp", "Ouvrir l'aide dans le navigateur...");
        add("invtweaks.settings.onlinehelp.error", "[10] Impossible d'ouvrir l'aide en ligne");
        add("invtweaks.help.bugsorting", "Ça ne marche pas ?");
        add("invtweaks.help.bugsorting.pt1", "Si votre serveur semble \"annuler\" ce que ce mod fait,");
        add("invtweaks.help.bugsorting.pt2", "c'est probablement à cause qu'il utilise le plugin \"NoCheatPlus\".");
        add("invtweaks.help.bugsorting.pt3", "Demandez à votre administrateur de désactiver les options suivantes :");
        add("invtweaks.help.bugsorting.pt4", "\"inventory.fastclick\" et \"combined.improbable\"");
        add("invtweaks.settings.pvpwarning.pt1", "Note : sur les serveurs PvP, les options d'auto-remplacement et");
        add("invtweaks.settings.pvpwarning.pt2", "d'auto-équipement d'armure sont souvent considérées comme de la triche !");
        add("invtweaks.settings.exit", "Terminé");
        add("invtweaks.settings.advanced.sortonpickup", "Trier les objets pris ");
        add("invtweaks.settings.advanced.sortonpickup.soloonly", "Solo seulement ");
        add("invtweaks.settings.advanced.sortonpickup.tooltip", "Évite de remplir la barre d'action\navec tout ce que vous ramassez");
        add("invtweaks.settings.advanced.sortonpickup.tooltip.soloonly", "Seulement en mode solo");
        add("invtweaks.settings.advanced.autoequip", "Auto-équip. d'armure ");
        add("invtweaks.settings.advanced.autoequip.tooltip", "Équipe la meilleure armure\ndisponible durant le tri");
        add("invtweaks.settings.advanced.sounds", "Activer les sons ");
        add("invtweaks.settings.advanced.sounds.tooltip", "Joue un son durant le tri\nou l'auto-remplacement");
        add("invtweaks.settings.advanced.mappingsfile", "Ouvrir le fichier des raccourcis...");
        add("invtweaks.settings.advanced.mappingsfile.error", "[11] Impossible d'ouvrir le fichier des raccourcis clavier");
        add("invtweaks.settings.advanced.serverassist", "Assistance serveur ");
        add("invtweaks.settings.advanced.serverassist.tooltip", "Active l'utilisation de l'assistance\nserveur pour le déplacement\nd'objet lors du tri");
        add("invtweaks.help.shortcuts.title", "Guide des raccourcis clavier");
        add("invtweaks.help.shortcuts.click", "clic");
        add("invtweaks.help.shortcuts.or", "ou");
        add("invtweaks.help.shortcuts.onestack", "Déplacer un stack");
        add("invtweaks.help.shortcuts.oneitem", "Déplacer un objet");
        add("invtweaks.help.shortcuts.allitems", "Déplacer les objets pareils");
        add("invtweaks.help.shortcuts.everything", "Déplacer tout");
        add("invtweaks.help.shortcuts.hotbar", "Déplacer à la barre d'action");
        add("invtweaks.help.shortcuts.emptyslot", "Déplacer vers une case vide");
        add("invtweaks.help.shortcuts.rightclick", "(Raccourci) + Clic droit au-lieu de gauche");
        add("invtweaks.help.shortcuts.drop", "Jeter");
        add("invtweaks.help.shortcuts.craftall", "Tout fabriquer à l'inventaire");
        add("invtweaks.help.shortcuts.craftone", "En fabriquer 1 à l'inventaire");
        add("invtweaks.help.shortcuts.selectconfig", "Choisir la configuration de tri");
        add("invtweaks.help.shortcuts.forward", "(Vers l'avant)");
        add("invtweaks.help.shortcuts.backward", "(Vers l'arrière)");
        add("invtweaks.keyboardfix.error", "[12] J'ai des problèmes avec le clavier");
        add("invtweaks.keyboardfix.recover", "Ok, c'est réparé, désolé pour ça.");
        add("invtweaks.modcompat.ci.error", "[13] Impossible de gérer la compatibilité avec 'Convenient Inventory'");
        add("key.category.inventorytweaksrevived.invtweaks", "Inventory Tweaks");
        add("invtweaks.key.sort", "Trier l'inventaire");
    }
}

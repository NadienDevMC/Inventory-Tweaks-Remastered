package nadiendev.inventorytweaksrevived.data.lang;

import net.minecraft.data.PackOutput;

public class FrFrLangProvider extends InvTweaksLangProvider {
    public FrFrLangProvider(PackOutput output) {
        super(output, "fr_fr");
    }

    @Override
    protected void addTranslations() {
        add("invtweaks.loadconfig.enabled", "'%s' activé");
        add("invtweaks.loadconfig.refresh.error", "[1] Impossible de recharger la configuration");
        add("invtweaks.loadconfig.invalidkeywords", "Mots-clés invalides détectés");
        add("invtweaks.loadconfig.filemissing", "manquant, chargement du fichier par défaut.");
        add("invtweaks.loadconfig.done", "Configuration rechargée.");
        add("invtweaks.propsfile.loaded", "Paramètres du mod chargés");
        add("invtweaks.propsfile.error", "[14] Impossible de créer le fichier de paramétrage du mod");
        add("invtweaks.extract.find.error", "[15] Le mod ne marchera pas, car %s est introuvable !");
        add("invtweaks.extract.create.error", "[16] Le mod ne marchera pas, car %s n'a pas pu être créé !");
        add("invtweaks.sort.inventory.error", "[2] Impossible de trier l'inventaire");
        add("invtweaks.sort.chest.error", "[3] Impossible de trier le conteneur");
        add("invtweaks.sort.autorefill.error", "[4] Impossible de remplacer l'objet automatiquement");
        add("invtweaks.sort.releaseitem.error", "[5] Impossible de poser l'objet tenu");
        add("invtweaks.shortcut.error", "[6] Impossible d'activer le raccourci");
        add("invtweaks.pickup.error", "[7] Impossible de déplacer l'objet ramassé");
        add("invtweaks.button.settings.tooltip", "Options de l'inventaire");
        add("invtweaks.button.chest1.tooltip", "Tri par défaut");
        add("invtweaks.button.chest2.tooltip", "Tri en colonnes");
        add("invtweaks.button.chest3.tooltip", "Tri en lignes");
        add("invtweaks.settings.title", "Options pour les coffres et l'inventaire");
        add("invtweaks.settings.on", "OUI");
        add("invtweaks.settings.off", "NON");
        add("invtweaks.settings.key", "Touche de tri :");
        add("invtweaks.settings.disableci", "Désactivez CI");
        add("invtweaks.settings.disableci.tooltip", "En conflit avec Convenient Inventory");
        add("invtweaks.settings.middleclick", "Clic milieu");
        add("invtweaks.settings.middleclick.tooltip", "Permet de trier en utilisant\nle clic milieu");
        add("invtweaks.settings.chestbuttons", "Boutons coffres");
        add("invtweaks.settings.chestbuttons.tooltip", "Ajoute trois boutons sur les coffres\npour les trier");
        add("invtweaks.settings.shortcuts", "Raccourcis");
        add("invtweaks.settings.shortcuts.tooltip", "Ajoute divers raccourcis pour déplacer les objets");
        add("invtweaks.settings.autorefill", "Remplacement auto.");
        add("invtweaks.settings.autorefill.tooltip", "Remplace les outils cassés & les stacks vides\navec d'autres depuis votre inventaire");
        add("invtweaks.settings.beforebreak", "Remplace avant casse");
        add("invtweaks.settings.beforebreak.tooltip", "Remplacer les outils juste avant qu'ils ne se cassent");
        add("invtweaks.settings.moreoptions", "Plus d'options...");
        add("invtweaks.settings.moreoptions.tooltip", "Encore plus de trucs pratiques !");
        add("invtweaks.settings.rulesfile", "Ouvrir le fichier des règles de tri...");
        add("invtweaks.settings.rulesfile.error", "[8] Impossible d'ouvrir le fichier des règles de tri");
        add("invtweaks.settings.treefile", "Ouvrir le fichier d'arborescence des objets...");
        add("invtweaks.settings.treefile.error", "[9] Impossible d'ouvrir l'arborescence des objets");
        add("invtweaks.settings.onlinehelp", "Ouvrir l'aide en ligne...");
        add("invtweaks.settings.onlinehelp.error", "[10] Impossible d'ouvrir l'aide");
        add("invtweaks.help.bugsorting", "Problème de fonctionnement ?");
        add("invtweaks.help.bugsorting.pt1", "Si votre serveur semble annuler les actions de ce mod,");
        add("invtweaks.help.bugsorting.pt2", "c'est probablement causé par le fait que ce dernier utilise le plugin \"NoCheatPlus\".");
        add("invtweaks.help.bugsorting.pt3", "Demandez à votre administrateur de désactiver les options suivantes :");
        add("invtweaks.help.bugsorting.pt4", "\"inventory.fastclick\" et \"combined.improbable\"");
        add("invtweaks.settings.pvpwarning.pt1", "Note: Sur les serveurs PvP, le remplacement et l'équipement automatique");
        add("invtweaks.settings.pvpwarning.pt2", "d'armure automatiques sont souvent vus comme de la triche !");
        add("invtweaks.settings.exit", "Fermer");
        add("invtweaks.settings.advanced.sortonpickup", "Ranger l'objet ramassé");
        add("invtweaks.settings.advanced.sortonpickup.soloonly", "Solo uniq.");
        add("invtweaks.settings.advanced.sortonpickup.tooltip", "Évite de remplir la barre d'action\navec tout ce que vous ramassez");
        add("invtweaks.settings.advanced.sortonpickup.tooltip.soloonly", "Seulement en mode solo");
        add("invtweaks.settings.advanced.autoequip", "Equip. armure auto.");
        add("invtweaks.settings.advanced.autoequip.tooltip", "Equipe la meilleure armure disponible\npendant le tri");
        add("invtweaks.settings.advanced.sounds", "Activer les sons");
        add("invtweaks.settings.advanced.sounds.tooltip", "Joue un son pendant le tri ou\nle remplacement automatique");
        add("invtweaks.settings.advanced.mappingsfile", "Ouvrir le fichier des raccourcis clavier...");
        add("invtweaks.settings.advanced.mappingsfile.error", "[11] Impossible d'ouvrir le fichier des raccourcis ");
        add("invtweaks.settings.advanced.serverassist", "Assistance serveur");
        add("invtweaks.settings.advanced.serverassist.tooltip", "Utiliser l'assistance serveur pour le déplacement d'objets lors du tri.");
        add("invtweaks.help.shortcuts.title", "Guide des raccourcis clavier");
        add("invtweaks.help.shortcuts.click", "Clic");
        add("invtweaks.help.shortcuts.or", "ou");
        add("invtweaks.help.shortcuts.onestack", "Bouger une pile");
        add("invtweaks.help.shortcuts.oneitem", "Bouger un objet");
        add("invtweaks.help.shortcuts.allitems", "Bouger tout du même type");
        add("invtweaks.help.shortcuts.everything", "Bouger tout");
        add("invtweaks.help.shortcuts.hotbar", "Bouger vers l'accès rapide");
        add("invtweaks.help.shortcuts.emptyslot", "Bouger vers une case vide");
        add("invtweaks.help.shortcuts.rightclick", "(Autre raccourci) + Clic droit à la place du gauche");
        add("invtweaks.help.shortcuts.drop", "Lâcher");
        add("invtweaks.help.shortcuts.craftall", "Fabriquer au maximum");
        add("invtweaks.help.shortcuts.craftone", "Fabriquer une fois");
        add("invtweaks.help.shortcuts.selectconfig", "Choisir une config. de tri");
        add("invtweaks.help.shortcuts.forward", "(En avant)");
        add("invtweaks.help.shortcuts.backward", "(En arrière)");
        add("invtweaks.keyboardfix.error", "[12] Il y a un souci avec le clavier...");
        add("invtweaks.keyboardfix.recover", "Oh c'est réparé, mille excuses.");
        add("invtweaks.modcompat.ci.error", "[13] Impossible de gérer la compatibilité avec 'Convenient Inventory'");
        add("invtweaks.key.category", "Inventory Tweaks");
        add("invtweaks.key.sort", "Tri de l'inventaire");
    }
}

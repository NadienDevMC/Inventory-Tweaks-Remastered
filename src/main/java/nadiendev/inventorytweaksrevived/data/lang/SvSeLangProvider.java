package nadiendev.inventorytweaksrevived.data.lang;

import net.minecraft.data.PackOutput;

public class SvSeLangProvider extends InvTweaksLangProvider {
    public SvSeLangProvider(PackOutput output) {
        super(output, "sv_se");
    }

    @Override
    protected void addTranslations() {
        add("invtweaks.loadconfig.enabled", "'%s' aktiverat");
        add("invtweaks.loadconfig.refresh.error", "[1] Misslyckades med att uppdatera egenskaper från fil");
        add("invtweaks.loadconfig.invalidkeywords", "Ogiltiga nyckelord hittades");
        add("invtweaks.loadconfig.filemissing", "saknas, skapar en standard.");
        add("invtweaks.loadconfig.done", "Konfiguration laddad.");
        add("invtweaks.propsfile.loaded", "Modegenskaper laddat");
        add("invtweaks.propsfile.error", "[14] Misslyckades med att skapa egenskapsfilen för moddet");
        add("invtweaks.extract.find.error", "[15] Moddet kommer inte att fungera, eftersom %s inte kunde hittas!");
        add("invtweaks.extract.create.error", "[16] Moddet kommer inte att fungera, eftersom %s inte gick att skapa!");
        add("invtweaks.sort.inventory.error", "[2] Misslyckades med att sortera inventering");
        add("invtweaks.sort.chest.error", "[3] Misslyckades att sortera behållare");
        add("invtweaks.sort.autorefill.error", "[4] Misslyckades med att utlösa auto-påfyllning");
        add("invtweaks.sort.releaseitem.error", "[5] Misslyckades med att frigöra objekt");
        add("invtweaks.shortcut.error", "[6] Misslyckades med att utlösa genväg");
        add("invtweaks.pickup.error", "[7] Misslyckades att flytta den upplockade stacken");
        add("invtweaks.button.settings.tooltip", "Inställningar för inventering");
        add("invtweaks.button.chest1.tooltip", "Standardsortering");
        add("invtweaks.button.chest2.tooltip", "Sortera i kolumner");
        add("invtweaks.button.chest3.tooltip", "Sortera i rader");
        add("invtweaks.settings.title", "Inställningar för inventering och kistor");
        add("invtweaks.settings.on", "PÅ");
        add("invtweaks.settings.off", "AV");
        add("invtweaks.settings.key", "Sorteringsknapp:");
        add("invtweaks.settings.disableci", "Inaktivera CI");
        add("invtweaks.settings.disableci.tooltip", "I konflikt med Convenient Inventory");
        add("invtweaks.settings.middleclick", "Mellanklick");
        add("invtweaks.settings.middleclick.tooltip", "Sorterar med\nmittenmusknappen");
        add("invtweaks.settings.chestbuttons", "Lådknappar");
        add("invtweaks.settings.chestbuttons.tooltip", "Lägger till tre knappar\npå kistor för att sortera dem");
        add("invtweaks.settings.shortcuts", "Kortkommandon");
        add("invtweaks.settings.shortcuts.tooltip", "Aktiverar kortkommandon\nför att flytta omkring föremål");
        add("invtweaks.settings.autorefill", "Auto-påfyllning");
        add("invtweaks.settings.autorefill.tooltip", "Ersätter trasiga verktyg och tomma stackar\nmed andra från din utrustning");
        add("invtweaks.settings.beforebreak", "Reparationsvänlig påfyllning");
        add("invtweaks.settings.beforebreak.tooltip", "Ersätter verktyg precis innan de förstörs");
        add("invtweaks.settings.moreoptions", "Mer inställningar...");
        add("invtweaks.settings.moreoptions.tooltip", "Ännu mer coola grejer");
        add("invtweaks.settings.rulesfile", "Öppna filen sorteringregler...");
        add("invtweaks.settings.rulesfile.error", "[8] Misslyckades med att öppna regelfilen");
        add("invtweaks.settings.treefile", "Öppna objektträd filen...");
        add("invtweaks.settings.treefile.error", "[9] Misslyckades att öppna träd-fil");
        add("invtweaks.settings.onlinehelp", "Öppna hjälpen i webbläsaren...");
        add("invtweaks.settings.onlinehelp.error", "[10] Misslyckades med att öppna hjälpen");
        add("invtweaks.help.bugsorting", "Fungerar inte?");
        add("invtweaks.help.bugsorting.pt1", "Om servern verkar 'avbryta' det som detta mod gör,");
        add("invtweaks.help.bugsorting.pt2", "så orsakas det troligen utav att servern använder NoCheatPlus.");
        add("invtweaks.help.bugsorting.pt3", "Be din admin att inaktivera de följande tillvalen:");
        add("invtweaks.help.bugsorting.pt4", "\"inventory.fastclick\" och \"combined.improbable\"");
        add("invtweaks.settings.pvpwarning.pt1", "Obs: På PvP servrar, så kommer auto-påfyllning & auto-ekipera rustning");
        add("invtweaks.settings.pvpwarning.pt2", "tillvalen anses ofta vara fusk!");
        add("invtweaks.settings.exit", "Färdig");
        add("invtweaks.settings.advanced.sortonpickup", "Sortera på upplockning");
        add("invtweaks.settings.advanced.sortonpickup.soloonly", "Endast SP");
        add("invtweaks.settings.advanced.sortonpickup.tooltip", "Undviker att fylla utrustningsfältet med\nallting du plockar upp");
        add("invtweaks.settings.advanced.sortonpickup.tooltip.soloonly", "Endast i enspelarläget");
        add("invtweaks.settings.advanced.autoequip", "Auto-ekipera rustning");
        add("invtweaks.settings.advanced.autoequip.tooltip", "Tar på den bästa tillgängliga\nrustningen vid sortering");
        add("invtweaks.settings.advanced.sounds", "Aktivera ljud");
        add("invtweaks.settings.advanced.sounds.tooltip", "Spelar upp ljud när sortering\neller när auto-påfyllning aktiveras");
        add("invtweaks.settings.advanced.mappingsfile", "Öppna genvägkartläggningsfilen...");
        add("invtweaks.settings.advanced.mappingsfile.error", "[11] Misslyckades med att öppna kartläggningsfilen");
        add("invtweaks.settings.advanced.serverassist", "Serverföremålsförflyttning");
        add("invtweaks.settings.advanced.serverassist.tooltip", "Aktivera användandet utav serverstöd för förflyttning av föremål.");
        add("invtweaks.help.shortcuts.title", "Genvägshjälp");
        add("invtweaks.help.shortcuts.click", "Klicka");
        add("invtweaks.help.shortcuts.or", "eller");
        add("invtweaks.help.shortcuts.onestack", "Flytta en stack");
        add("invtweaks.help.shortcuts.oneitem", "Flytta en sak");
        add("invtweaks.help.shortcuts.allitems", "Flytta alla likadana saker");
        add("invtweaks.help.shortcuts.everything", "Flytta allt");
        add("invtweaks.help.shortcuts.hotbar", "Flytta till snabbfält");
        add("invtweaks.help.shortcuts.emptyslot", "Flytta till tom plats");
        add("invtweaks.help.shortcuts.rightclick", "(Någon genväg) + höger- i stället för vänsterklick");
        add("invtweaks.help.shortcuts.drop", "Släpp");
        add("invtweaks.help.shortcuts.craftall", "Snickra alla till inventeringen");
        add("invtweaks.help.shortcuts.craftone", "Snickra till inventeringen");
        add("invtweaks.help.shortcuts.selectconfig", "Välj sorteringskonfiguration");
        add("invtweaks.help.shortcuts.forward", "(Framåt)");
        add("invtweaks.help.shortcuts.backward", "(Bakåt)");
        add("invtweaks.keyboardfix.error", "[12] Jag har problem med tangentbordet");
        add("invtweaks.keyboardfix.recover", "Ok det är reparerat, ledsen för det.");
        add("invtweaks.modcompat.ci.error", "[13] Misslyckades med att hantera kompatibilitet med 'Convenient Inventory'");
        add("key.category.inventorytweaksrevived.invtweaks", "Inventeringsanpassning");
        add("invtweaks.key.sort", "Utrustningssortering");
    }
}

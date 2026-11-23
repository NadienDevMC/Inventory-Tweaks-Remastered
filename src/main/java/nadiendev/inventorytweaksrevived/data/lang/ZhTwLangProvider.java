package nadiendev.inventorytweaksrevived.data.lang;

import net.minecraft.data.PackOutput;

public class ZhTwLangProvider extends InvTweaksLangProvider {
    public ZhTwLangProvider(PackOutput output) {
        super(output, "zh_tw");
    }

    @Override
    protected void addTranslations() {
        add("invtweaks.loadconfig.enabled", "'%s' 已啟用");
        add("invtweaks.loadconfig.refresh.error", "[1] 從檔案重新讀取屬性檔失敗");
        add("invtweaks.loadconfig.invalidkeywords", "發現無效的關鍵字");
        add("invtweaks.loadconfig.filemissing", "遺失，建立一個預設的。");
        add("invtweaks.loadconfig.done", "已載入設定檔。");
        add("invtweaks.propsfile.loaded", "已載入模組屬性檔");
        add("invtweaks.propsfile.error", "[14] 無法建立模組屬性檔");
        add("invtweaks.extract.find.error", "[15] 找不到 %s，故無法執行模組！");
        add("invtweaks.extract.create.error", "[16] 由於 %s 建立失敗，故無法執行模組！");
        add("invtweaks.sort.inventory.error", "[2] 物品欄排序失敗");
        add("invtweaks.sort.chest.error", "[3] 無法排序儲物箱");
        add("invtweaks.sort.autorefill.error", "[4] 無法觸發自動裝填");
        add("invtweaks.sort.releaseitem.error", "[5] 無法釋出物品");
        add("invtweaks.shortcut.error", "[6] 無法觸發捷徑");
        add("invtweaks.pickup.error", "[7] 無法移動拿起的堆疊");
        add("invtweaks.button.settings.tooltip", "物品欄設定");
        add("invtweaks.button.chest1.tooltip", "預設排序");
        add("invtweaks.button.chest2.tooltip", "以列排序");
        add("invtweaks.button.chest3.tooltip", "以行排序");
        add("invtweaks.settings.title", "物品欄與儲物箱設定");
        add("invtweaks.settings.on", "開啟");
        add("invtweaks.settings.off", "關閉");
        add("invtweaks.settings.key", "排序鍵：");
        add("invtweaks.settings.disableci", "停用 CI");
        add("invtweaks.settings.disableci.tooltip", "與 Convenient Inventory 衝突");
        add("invtweaks.settings.middleclick", "中鍵點擊");
        add("invtweaks.settings.middleclick.tooltip", "允許使用中鍵\n來進行排序");
        add("invtweaks.settings.chestbuttons", "儲物箱按鈕");
        add("invtweaks.settings.chestbuttons.tooltip", "在儲物箱的右上角\n新增三個排序按鈕");
        add("invtweaks.settings.shortcuts", "快捷鍵");
        add("invtweaks.settings.shortcuts.tooltip", "啟用各種快捷鍵\n來移動物品");
        add("invtweaks.settings.autorefill", "自動裝填");
        add("invtweaks.settings.autorefill.tooltip", "自動從你的物品欄中\n遞補損壞的工具或用完的物品");
        add("invtweaks.settings.beforebreak", "保留式重裝");
        add("invtweaks.settings.beforebreak.tooltip", "在工具即將損毀前替換它們");
        add("invtweaks.settings.moreoptions", "更多選項...");
        add("invtweaks.settings.moreoptions.tooltip", "更多更酷的東西");
        add("invtweaks.settings.rulesfile", "開啟排序規則檔案...");
        add("invtweaks.settings.rulesfile.error", "[8] 無法開啟規則檔案");
        add("invtweaks.settings.treefile", "開啟物品樹狀檔案...");
        add("invtweaks.settings.treefile.error", "[9] 無法開啟樹狀檔案");
        add("invtweaks.settings.onlinehelp", "在瀏覽器中開啟說明...");
        add("invtweaks.settings.onlinehelp.error", "[10] 無法開啟說明");
        add("invtweaks.help.bugsorting", "無法使用？");
        add("invtweaks.help.bugsorting.pt1", "如果伺服器貌似“取消”掉了此Mod的操作，");
        add("invtweaks.help.bugsorting.pt2", "這可能是因為它使用 NoCheatPlus 外掛程式。");
        add("invtweaks.help.bugsorting.pt3", "詢問您的伺服器管理員可否停用以下選項：");
        add("invtweaks.help.bugsorting.pt4", "\"inventory.fastclick\" 和 \"combined.improbable\"");
        add("invtweaks.settings.pvpwarning.pt1", "注意：在 PvP 伺服器中，自動裝填與自動裝備鎧甲");
        add("invtweaks.settings.pvpwarning.pt2", "的選項通常會被視為作弊！");
        add("invtweaks.settings.exit", "完成");
        add("invtweaks.settings.advanced.sortonpickup", "拾取排序");
        add("invtweaks.settings.advanced.sortonpickup.soloonly", "只限單人遊戲");
        add("invtweaks.settings.advanced.sortonpickup.tooltip", "撿起物品時\n不放進快捷欄");
        add("invtweaks.settings.advanced.sortonpickup.tooltip.soloonly", "只用於單人遊戲模式");
        add("invtweaks.settings.advanced.autoequip", "自動裝備鎧甲");
        add("invtweaks.settings.advanced.autoequip.tooltip", "進行排序時自動\n裝備最佳盔甲");
        add("invtweaks.settings.advanced.sounds", "啟用音效");
        add("invtweaks.settings.advanced.sounds.tooltip", "在進行排序或是切換\n自動排序時播放音效");
        add("invtweaks.settings.advanced.mappingsfile", "開啟快捷鍵映射檔案...");
        add("invtweaks.settings.advanced.mappingsfile.error", "[11] 無法開啟映射檔案");
        add("invtweaks.settings.advanced.serverassist", "伺服端物品移動");
        add("invtweaks.settings.advanced.serverassist.tooltip", "開啟伺服端的物品移動輔助功能");
        add("invtweaks.help.shortcuts.title", "快捷鍵說明");
        add("invtweaks.help.shortcuts.click", "點擊");
        add("invtweaks.help.shortcuts.or", "或");
        add("invtweaks.help.shortcuts.onestack", "移動一個堆疊");
        add("invtweaks.help.shortcuts.oneitem", "移動一個物品");
        add("invtweaks.help.shortcuts.allitems", "移動所有相同類型物品");
        add("invtweaks.help.shortcuts.everything", "移動所有");
        add("invtweaks.help.shortcuts.hotbar", "移動到快捷欄");
        add("invtweaks.help.shortcuts.emptyslot", "移動到空格");
        add("invtweaks.help.shortcuts.rightclick", "（任意快捷鍵）+ 右鍵，而不使用左鍵點擊");
        add("invtweaks.help.shortcuts.drop", "丟棄");
        add("invtweaks.help.shortcuts.craftall", "製作全部並放入物品欄");
        add("invtweaks.help.shortcuts.craftone", "製作並放入物品欄");
        add("invtweaks.help.shortcuts.selectconfig", "選擇排序設定檔");
        add("invtweaks.help.shortcuts.forward", "（往前）");
        add("invtweaks.help.shortcuts.backward", "（往後）");
        add("invtweaks.keyboardfix.error", "[12] 我的鍵盤有問題");
        add("invtweaks.keyboardfix.recover", "Ok 已修復，為此向您致歉。");
        add("invtweaks.modcompat.ci.error", "[13] 'Convenient Inventory' 相容性管理失敗");
        add("invtweaks.key.category", "Inventory Tweaks");
        add("invtweaks.key.sort", "物品欄排序");
    }
}

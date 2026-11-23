package nadiendev.inventorytweaksrevived.data.lang;

import net.minecraft.data.PackOutput;

public class JaJpLangProvider extends InvTweaksLangProvider {
    public JaJpLangProvider(PackOutput output) {
        super(output, "ja_jp");
    }

    @Override
    protected void addTranslations() {
        add("invtweaks.loadconfig.enabled", "'%s' を有効にしました");
        add("invtweaks.loadconfig.refresh.error", "[1] ファイルからのプロパティを更新できませんでした");
        add("invtweaks.loadconfig.invalidkeywords", "無効なキーワードを発見しました");
        add("invtweaks.loadconfig.filemissing", "コンフィグが見つからないので、デフォルトを作成します。");
        add("invtweaks.loadconfig.done", "コンフィグが読み込まれました。");
        add("invtweaks.propsfile.loaded", "Modプロパティが読み込まれました");
        add("invtweaks.propsfile.error", "[14] Modのプロパティファイルの作成に失敗しました");
        add("invtweaks.extract.find.error", "[15] %sが見つからなかったため、このModは動作しません");
        add("invtweaks.extract.create.error", "[16] %sの作成に失敗したため、このModは動作しません");
        add("invtweaks.sort.inventory.error", "[2] インベントリのソートに失敗しました");
        add("invtweaks.sort.chest.error", "[3] コンテナのソートに失敗しました");
        add("invtweaks.sort.autorefill.error", "[4] 自動補充に失敗しました");
        add("invtweaks.sort.releaseitem.error", "[5] アイテムの開放に失敗しました");
        add("invtweaks.shortcut.error", "[6] ショートカットの実行に失敗しました");
        add("invtweaks.pickup.error", "[7] 選択したスタックの移動に失敗しました");
        add("invtweaks.button.settings.tooltip", "インベントリの設定");
        add("invtweaks.button.chest1.tooltip", "デフォルト ソート");
        add("invtweaks.button.chest2.tooltip", "列でソート");
        add("invtweaks.button.chest3.tooltip", "行でソート");
        add("invtweaks.settings.title", "インベントリとチェストの設定");
        add("invtweaks.settings.on", "オン");
        add("invtweaks.settings.off", "オフ");
        add("invtweaks.settings.key", "ソート キー：");
        add("invtweaks.settings.disableci", "CIを無効にする");
        add("invtweaks.settings.disableci.tooltip", "Convenient Inventoryと競合する");
        add("invtweaks.settings.middleclick", "ホイールクリック");
        add("invtweaks.settings.middleclick.tooltip", "ホイールクリックでソートできるようにする");
        add("invtweaks.settings.chestbuttons", "チェストのボタン");
        add("invtweaks.settings.chestbuttons.tooltip", "チェストの中身をソートする3つのボタンを追加する");
        add("invtweaks.settings.shortcuts", "ショートカット");
        add("invtweaks.settings.shortcuts.tooltip", "アイテムを移動するショートカットを有効にする");
        add("invtweaks.settings.autorefill", "自動補充");
        add("invtweaks.settings.autorefill.tooltip", "壊れたツールや空になったスタックをインベントリから他のものに置き換える");
        add("invtweaks.settings.beforebreak", "壊れる前に交換");
        add("invtweaks.settings.beforebreak.tooltip", "ツールが壊れる直前に同種のツールと交換する");
        add("invtweaks.settings.moreoptions", "その他のオプション...");
        add("invtweaks.settings.moreoptions.tooltip", "さらにもっとクールなもの");
        add("invtweaks.settings.rulesfile", "ソートルールファイルを開く...");
        add("invtweaks.settings.rulesfile.error", "[8] ルールファイルを開けませんでした");
        add("invtweaks.settings.treefile", "アイテムツリーファイルを開く...");
        add("invtweaks.settings.treefile.error", "[9] ツリーファイルを開けませんでした。");
        add("invtweaks.settings.onlinehelp", "ブラウザーでヘルプを開く...");
        add("invtweaks.settings.onlinehelp.error", "[10] ヘルプを開くのを失敗しました");
        add("invtweaks.help.bugsorting", "うまく動作しませんか？");
        add("invtweaks.help.bugsorting.pt1", "もしサーバーがこのModの動作を「キャンセル」しているように思われる場合、");
        add("invtweaks.help.bugsorting.pt2", "NoCheatPlusプラグインが原因かもしれません。");
        add("invtweaks.help.bugsorting.pt3", "次のオプションを無効にするよう管理人に問い合わせてください。");
        add("invtweaks.help.bugsorting.pt4", "\"inventory.fastclick\" と \"combined.improbable\"");
        add("invtweaks.settings.pvpwarning.pt1", "注: PvPサーバーで、自動補充 ＆ 鎧の自動装備");
        add("invtweaks.settings.pvpwarning.pt2", "オプションはよく不正行為と見なされます！");
        add("invtweaks.settings.exit", "完了");
        add("invtweaks.settings.advanced.sortonpickup", "アイテムを拾う時ソートする");
        add("invtweaks.settings.advanced.sortonpickup.soloonly", "SP のみ");
        add("invtweaks.settings.advanced.sortonpickup.tooltip", "アイテム取得時、アイテムスロットを埋めないようにする");
        add("invtweaks.settings.advanced.sortonpickup.tooltip.soloonly", "シングルプレイ時のみ");
        add("invtweaks.settings.advanced.autoequip", "鎧の自動装備");
        add("invtweaks.settings.advanced.autoequip.tooltip", "ソートした時に一番良い鎧を装備する");
        add("invtweaks.settings.advanced.sounds", "サウンドを有効にする");
        add("invtweaks.settings.advanced.sounds.tooltip", "ソートや自動補充が有効の時、サウンドを再生する");
        add("invtweaks.settings.advanced.mappingsfile", "ショートカット配置ファイルを開く...");
        add("invtweaks.settings.advanced.mappingsfile.error", "[11] マッピングファイルを開けませんでした");
        add("invtweaks.settings.advanced.serverassist", "サーバーでのアイテム移動");
        add("invtweaks.settings.advanced.serverassist.tooltip", "サーバー側のアイテム移動機能を使用する");
        add("invtweaks.help.shortcuts.title", "ショートカット ヘルプ");
        add("invtweaks.help.shortcuts.click", "クリック");
        add("invtweaks.help.shortcuts.or", "または");
        add("invtweaks.help.shortcuts.onestack", "１スタックを移動します");
        add("invtweaks.help.shortcuts.oneitem", "１個のアイテムを移動します");
        add("invtweaks.help.shortcuts.allitems", "同じ種類の全アイテムを移動します");
        add("invtweaks.help.shortcuts.everything", "全アイテムを移動します");
        add("invtweaks.help.shortcuts.hotbar", "ホットバーに移動します");
        add("invtweaks.help.shortcuts.emptyslot", "空のスロットに移動します");
        add("invtweaks.help.shortcuts.rightclick", "(任意のショートカット) + 右クリック");
        add("invtweaks.help.shortcuts.drop", "捨てる");
        add("invtweaks.help.shortcuts.craftall", "全部インベントリにクラフトする");
        add("invtweaks.help.shortcuts.craftone", "インベントリにクラフトする");
        add("invtweaks.help.shortcuts.selectconfig", "ソート設定を選択します");
        add("invtweaks.help.shortcuts.forward", "(前)");
        add("invtweaks.help.shortcuts.backward", "(後ろ)");
        add("invtweaks.keyboardfix.error", "[12] キーボードにトラブルがあります");
        add("invtweaks.keyboardfix.recover", "修復されました、ごめんなさい");
        add("invtweaks.modcompat.ci.error", "[13] 「Convenient Inventory」との互換性の確保に失敗しました");
        add("invtweaks.key.category", "Inventory Tweaks");
        add("invtweaks.key.sort", "インベントリのソート");
    }
}

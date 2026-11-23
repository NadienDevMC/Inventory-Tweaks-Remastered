package nadiendev.inventorytweaksrevived.data.lang;

import net.minecraft.data.PackOutput;

public class ZhCnLangProvider extends InvTweaksLangProvider {
    public ZhCnLangProvider(PackOutput output) {
        super(output, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add("invtweaks.loadconfig.enabled", "'%s'已启用");
        add("invtweaks.loadconfig.refresh.error", "[1] 无法从文件中更新配置");
        add("invtweaks.loadconfig.invalidkeywords", "关键词无效");
        add("invtweaks.loadconfig.filemissing", "缺失，正在创建一个默认配置");
        add("invtweaks.loadconfig.done", "配置文件已载入");
        add("invtweaks.propsfile.loaded", "Mod配置文件已载入");
        add("invtweaks.propsfile.error", "[14] 无法创建Mod配置文件");
        add("invtweaks.extract.find.error", "[15] Mod无法工作，因为找不到%s！");
        add("invtweaks.extract.create.error", "[16] Mod无法工作，因为%s创建失败！");
        add("invtweaks.sort.inventory.error", "[2] 整理物品栏失败");
        add("invtweaks.sort.chest.error", "[3] 整理容器失败");
        add("invtweaks.sort.autorefill.error", "[4] 无法触发自动装填");
        add("invtweaks.sort.releaseitem.error", "[5] 无法放下物品");
        add("invtweaks.shortcut.error", "[6] 无法触发快捷键");
        add("invtweaks.pickup.error", "[7] 无法移动拾取的堆叠");
        add("invtweaks.button.settings.tooltip", "物品栏设置");
        add("invtweaks.button.chest1.tooltip", "默认排序方式");
        add("invtweaks.button.chest2.tooltip", "按列整理");
        add("invtweaks.button.chest3.tooltip", "按行整理");
        add("invtweaks.settings.title", "物品栏和箱子设置");
        add("invtweaks.settings.on", "开启");
        add("invtweaks.settings.off", "关闭");
        add("invtweaks.settings.key", "整理键：");
        add("invtweaks.settings.disableci", "禁用CI");
        add("invtweaks.settings.disableci.tooltip", "与Convenient Inventory冲突");
        add("invtweaks.settings.middleclick", "鼠标中键");
        add("invtweaks.settings.middleclick.tooltip", "使用鼠标中键来排序整理");
        add("invtweaks.settings.chestbuttons", "箱子按钮");
        add("invtweaks.settings.chestbuttons.tooltip", "在箱子上添加三个按钮来排序整理");
        add("invtweaks.settings.shortcuts", "快捷键");
        add("invtweaks.settings.shortcuts.tooltip", "启用各种快捷键来移动物品");
        add("invtweaks.settings.autorefill", "自动装填");
        add("invtweaks.settings.autorefill.tooltip", "自动替换损坏的工具和用尽的堆叠");
        add("invtweaks.settings.beforebreak", "保留式重装");
        add("invtweaks.settings.beforebreak.tooltip", "在工具坏掉前的一瞬间替换它们");
        add("invtweaks.settings.moreoptions", "更多选项...");
        add("invtweaks.settings.moreoptions.tooltip", "更多炫酷的东西");
        add("invtweaks.settings.rulesfile", "打开排序规则文件...");
        add("invtweaks.settings.rulesfile.error", "[8] 无法打开规则文件");
        add("invtweaks.settings.treefile", "打开物品树文件...");
        add("invtweaks.settings.treefile.error", "[9] 无法打开树文件");
        add("invtweaks.settings.onlinehelp", "在浏览器中打开帮助...");
        add("invtweaks.settings.onlinehelp.error", "[10] 无法打开帮助文件");
        add("invtweaks.help.bugsorting", "不起作用？");
        add("invtweaks.help.bugsorting.pt1", "如果服务器似乎“取消”了此Mod的操作，");
        add("invtweaks.help.bugsorting.pt2", "那么可能是因为它使用了NoCheatPlus插件。");
        add("invtweaks.help.bugsorting.pt3", "请求你的服务器管理员取消掉以下选项：");
        add("invtweaks.help.bugsorting.pt4", "\"inventory.fastclick\" 和 \"combined.improbable\"");
        add("invtweaks.settings.pvpwarning.pt1", "提示：在PVP服务器上，自动填装和自动装备盔甲");
        add("invtweaks.settings.pvpwarning.pt2", "的选项通常被视作为作弊！");
        add("invtweaks.settings.exit", "完成");
        add("invtweaks.settings.advanced.sortonpickup", "拾取物品时自动整理");
        add("invtweaks.settings.advanced.sortonpickup.soloonly", "仅限单人游戏");
        add("invtweaks.settings.advanced.sortonpickup.tooltip", "避免用你拾到的物品来填充快捷栏");
        add("invtweaks.settings.advanced.sortonpickup.tooltip.soloonly", "仅限单人游戏模式");
        add("invtweaks.settings.advanced.autoequip", "自动装备盔甲");
        add("invtweaks.settings.advanced.autoequip.tooltip", "整理时自动装备上最好的盔甲");
        add("invtweaks.settings.advanced.sounds", "开启音效");
        add("invtweaks.settings.advanced.sounds.tooltip", "在整理或自动装填时播放音效");
        add("invtweaks.settings.advanced.mappingsfile", "打开快捷键映射文件");
        add("invtweaks.settings.advanced.mappingsfile.error", "[11] 无法打开映像文件");
        add("invtweaks.settings.advanced.serverassist", "服务端物品移动");
        add("invtweaks.settings.advanced.serverassist.tooltip", "使用服务端的物品移动协助功能");
        add("invtweaks.help.shortcuts.title", "快捷键帮助");
        add("invtweaks.help.shortcuts.click", "点击");
        add("invtweaks.help.shortcuts.or", "或");
        add("invtweaks.help.shortcuts.onestack", "移动一叠物品");
        add("invtweaks.help.shortcuts.oneitem", "移动一个物品");
        add("invtweaks.help.shortcuts.allitems", "移动所有同类型物品");
        add("invtweaks.help.shortcuts.everything", "移动所有物品");
        add("invtweaks.help.shortcuts.hotbar", "移动至快捷栏");
        add("invtweaks.help.shortcuts.emptyslot", "移动至空栏位");
        add("invtweaks.help.shortcuts.rightclick", "(任意快捷键) + 鼠标右键");
        add("invtweaks.help.shortcuts.drop", "丢弃");
        add("invtweaks.help.shortcuts.craftall", "全部合成到物品栏");
        add("invtweaks.help.shortcuts.craftone", "合成到物品栏");
        add("invtweaks.help.shortcuts.selectconfig", "选择整理方案");
        add("invtweaks.help.shortcuts.forward", "(前进)");
        add("invtweaks.help.shortcuts.backward", "(后退)");
        add("invtweaks.keyboardfix.error", "[12] 我的键盘有问题");
        add("invtweaks.keyboardfix.recover", "问题已修复，抱歉");
        add("invtweaks.modcompat.ci.error", "[13] 无法与'Convenient Inventory'达成兼容");
        add("key.category.inventorytweaksrevived.invtweaks", "背包一键整理");
        add("invtweaks.key.sort", "物品栏整理");
    }
}

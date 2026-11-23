package nadiendev.inventorytweaksrevived.data.lang;

import net.minecraft.data.PackOutput;

public class UkUaLangProvider extends InvTweaksLangProvider {
    public UkUaLangProvider(PackOutput output) {
        super(output, "uk_ua");
    }

    @Override
    protected void addTranslations() {
        add("invtweaks.loadconfig.enabled", "'%s' увімкнено");
        add("invtweaks.loadconfig.refresh.error", "[1] Не вдалось оновити опції з файлу");
        add("invtweaks.loadconfig.invalidkeywords", "Виявлено недопустимі ключові слова");
        add("invtweaks.loadconfig.filemissing", "відсутньо, створення стандартного.");
        add("invtweaks.loadconfig.done", "Конфігурацію завантажено.");
        add("invtweaks.propsfile.loaded", "Налаштування моду завантажено");
        add("invtweaks.propsfile.error", "[14] Не вдалось створити файл налаштування моду");
        add("invtweaks.extract.find.error", "[15] Мод не не буде працювати, %s не знайдено!");
        add("invtweaks.extract.create.error", "[16] Мод не буде працювати, не вдалось створити %s!");
        add("invtweaks.sort.inventory.error", "[2] Не вдалось відсортувати інвентар");
        add("invtweaks.sort.chest.error", "[3] Не вдалось відсортувати скриню");
        add("invtweaks.sort.autorefill.error", "[4] Не вдалось переключити автозаміну");
        add("invtweaks.sort.releaseitem.error", "[5] Не вдалось звільнити предмет");
        add("invtweaks.shortcut.error", "[6] Не вдалось переключити комбінацію клавіш");
        add("invtweaks.pickup.error", "[7] Не вдалось перемістити піднятий стек");
        add("invtweaks.button.settings.tooltip", "Налаштування інвентарю");
        add("invtweaks.button.chest1.tooltip", "Стандартне сортування");
        add("invtweaks.button.chest2.tooltip", "Сортування по стовбцях");
        add("invtweaks.button.chest3.tooltip", "Сортування по рядках");
        add("invtweaks.settings.title", "Налаштування інвентарю і скринь");
        add("invtweaks.settings.on", "УВІМК");
        add("invtweaks.settings.off", "ВИМК");
        add("invtweaks.settings.key", "Клавіша сортування:");
        add("invtweaks.settings.disableci", "Відключити CI");
        add("invtweaks.settings.disableci.tooltip", "Конфліктує з Convenient Inventory");
        add("invtweaks.settings.middleclick", "Клік середньою кнопкою мишки");
        add("invtweaks.settings.middleclick.tooltip", "Дозволяє виконувати сортування\nсередньою кнопкою миші");
        add("invtweaks.settings.chestbuttons", "Кнопки скрині");
        add("invtweaks.settings.chestbuttons.tooltip", "Додає три кнопки\nдля сортування речей в скрині");
        add("invtweaks.settings.shortcuts", "Гарячі клавіші");
        add("invtweaks.settings.shortcuts.tooltip", "Вмикає різні сполучення\nдля переміщення предметів");
        add("invtweaks.settings.autorefill", "Автопідміна");
        add("invtweaks.settings.autorefill.tooltip", "Замінює зламані інструменти та спорожнілі стеки\nіншими з вашого інвентарю");
        add("invtweaks.settings.beforebreak", "Замінювати перед поломкою");
        add("invtweaks.settings.beforebreak.tooltip", "Замінює інструмент, перш ніж він зламається");
        add("invtweaks.settings.moreoptions", "Більше налаштувань...");
        add("invtweaks.settings.moreoptions.tooltip", "Ще більше крутих налаштувать");
        add("invtweaks.settings.rulesfile", "Відкрити файл правил сортування...");
        add("invtweaks.settings.rulesfile.error", "[8] Не вдалось відкрити файл правил");
        add("invtweaks.settings.treefile", "Відкрити файл дерева речей...");
        add("invtweaks.settings.treefile.error", "[9] Не вдалось відкрити файл дерева");
        add("invtweaks.settings.onlinehelp", "Відкрити довідку в браузері...");
        add("invtweaks.settings.onlinehelp.error", "[10] Не вдалось відкрити довідку");
        add("invtweaks.help.bugsorting", "Не працює?");
        add("invtweaks.help.bugsorting.pt1", "Якщо сервер 'відкатує' дії моду,");
        add("invtweaks.help.bugsorting.pt2", "це, скоріш за все, викликано плагіном NoCheatPlus.");
        add("invtweaks.help.bugsorting.pt3", "Спитайте адміністратора серверу відключити наступні параметри:");
        add("invtweaks.help.bugsorting.pt4", "\"inventory.fastclick\" та \"combined.improbable\"");
        add("invtweaks.settings.pvpwarning.pt1", "Важливо: На PvP серверах автопідміна та автоекіпіровка");
        add("invtweaks.settings.pvpwarning.pt2", "часто приймаються за махлювання!");
        add("invtweaks.settings.exit", "Готово");
        add("invtweaks.settings.advanced.sortonpickup", "Сортувати при підбиранні");
        add("invtweaks.settings.advanced.sortonpickup.soloonly", "Тільки для одиночної гри");
        add("invtweaks.settings.advanced.sortonpickup.tooltip", "Уникає заповнення швидкої панелі\nвсім, що ви підбираєте");
        add("invtweaks.settings.advanced.sortonpickup.tooltip.soloonly", "Лише для одиночної гри");
        add("invtweaks.settings.advanced.autoequip", "Автоматичне екіпіровка обладунків");
        add("invtweaks.settings.advanced.autoequip.tooltip", "Одягти кращу доступну броню\nпри сортуванні");
        add("invtweaks.settings.advanced.sounds", "Увімкнути звуки");
        add("invtweaks.settings.advanced.sounds.tooltip", "Звуки при сортуванні\nчи автопідміні");
        add("invtweaks.settings.advanced.mappingsfile", "Відкрити файл відповідностей клавіш...");
        add("invtweaks.settings.advanced.mappingsfile.error", "[11] Не вдалось відкрити файл відповідностей");
        add("invtweaks.settings.advanced.serverassist", "Переміщення предметів на сервері");
        add("invtweaks.settings.advanced.serverassist.tooltip", "Увімкнути використання допомоги серверу для переміщення предметів під час сортування.");
        add("invtweaks.help.shortcuts.title", "Допомога по відповідності клавіш");
        add("invtweaks.help.shortcuts.click", "Клік мишки");
        add("invtweaks.help.shortcuts.or", "або");
        add("invtweaks.help.shortcuts.onestack", "Перемістити один стек");
        add("invtweaks.help.shortcuts.oneitem", "Перемістити один пердмет");
        add("invtweaks.help.shortcuts.allitems", "Перемістити всі предмети одного виду");
        add("invtweaks.help.shortcuts.everything", "Перемістити все");
        add("invtweaks.help.shortcuts.hotbar", "Перемістити на панель швидкого доступу");
        add("invtweaks.help.shortcuts.emptyslot", "Перемістити у вільний слот");
        add("invtweaks.help.shortcuts.rightclick", "(Будь яка комбінація клавіш) + Права кнопки мишки замість лівої");
        add("invtweaks.help.shortcuts.drop", "Викинути");
        add("invtweaks.help.shortcuts.craftall", "Скрафтити все в інвентар");
        add("invtweaks.help.shortcuts.craftone", "Скрафтити і перенести в інвентар");
        add("invtweaks.help.shortcuts.selectconfig", "Виберіть опції сортування");
        add("invtweaks.help.shortcuts.forward", "(наст.)");
        add("invtweaks.help.shortcuts.backward", "(попер.)");
        add("invtweaks.keyboardfix.error", "[12] В мене проблеми з клавіатурою");
        add("invtweaks.keyboardfix.recover", "Відновлено. Вибачте за незручності.");
        add("invtweaks.modcompat.ci.error", "[13] Не вдалось визначити сумісність з Convenient Inventory");
        add("key.category.inventorytweaksrevived.invtweaks", "Налаштування інвентарю");
        add("invtweaks.key.sort", "Впорядкування інвертарю");
    }
}

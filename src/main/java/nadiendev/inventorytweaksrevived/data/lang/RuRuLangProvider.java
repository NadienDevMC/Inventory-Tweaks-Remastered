package nadiendev.inventorytweaksrevived.data.lang;

import net.minecraft.data.PackOutput;

public class RuRuLangProvider extends InvTweaksLangProvider {
    public RuRuLangProvider(PackOutput output) {
        super(output, "ru_ru");
    }

    @Override
    protected void addTranslations() {
        add("invtweaks.loadconfig.enabled", "'%s' включен");
        add("invtweaks.loadconfig.refresh.error", "[1] Не удалось обновить настройки из файла");
        add("invtweaks.loadconfig.invalidkeywords", "Найдены недопустимые ключевые слова");
        add("invtweaks.loadconfig.filemissing", "не найден, создаю по умолчанию.");
        add("invtweaks.loadconfig.done", "Настройки загружены.");
        add("invtweaks.propsfile.loaded", "Настройки мода загружены");
        add("invtweaks.propsfile.error", "[14] Не удалось создать файл настроек мода");
        add("invtweaks.extract.find.error", "[15] Мод не будет работать, потому что %s не найден!");
        add("invtweaks.extract.create.error", "[16] Мод не будет работать, потому что создание %s не удалось!");
        add("invtweaks.sort.inventory.error", "[2] Не удалось отсортировать инвентарь");
        add("invtweaks.sort.chest.error", "[3] Не удалось отсортировать хранилище");
        add("invtweaks.sort.autorefill.error", "[4] Не удалось переключить автоподмену");
        add("invtweaks.sort.releaseitem.error", "[5] Не удалось освободить предмет");
        add("invtweaks.shortcut.error", "[6] Не удалось переключить комбинацию клавиш");
        add("invtweaks.pickup.error", "[7] Не удалось переместить поднятый стак");
        add("invtweaks.button.settings.tooltip", "Параметры инвентаря");
        add("invtweaks.button.chest1.tooltip", "Стандартная сортировка");
        add("invtweaks.button.chest2.tooltip", "Сортировка по столбцам");
        add("invtweaks.button.chest3.tooltip", "Сортировка по рядам");
        add("invtweaks.settings.title", "Настройки инвентаря и сундуков");
        add("invtweaks.settings.on", "Вкл.");
        add("invtweaks.settings.off", "Выкл.");
        add("invtweaks.settings.key", "Клавиша сортировки:");
        add("invtweaks.settings.disableci", "Отключить CI");
        add("invtweaks.settings.disableci.tooltip", "Конфликтует с Convenient Inventory");
        add("invtweaks.settings.middleclick", "Клик средней кнопкой мыши");
        add("invtweaks.settings.middleclick.tooltip", "Позволяет сортировать\nсредней кнопкой мыши");
        add("invtweaks.settings.chestbuttons", "Кнопки сундука");
        add("invtweaks.settings.chestbuttons.tooltip", "Добавляет 3 кнопки\nдля сортировки вещей в сундуке");
        add("invtweaks.settings.shortcuts", "Сочетания клавиш");
        add("invtweaks.settings.shortcuts.tooltip", "Включает различные комбинации\nклавиш для перемещения");
        add("invtweaks.settings.autorefill", "Автоподмена");
        add("invtweaks.settings.autorefill.tooltip", "Заменяет сломанные инструменты и\nопустошенные стаки другими из инвентаря");
        add("invtweaks.settings.beforebreak", "Заменять перед поломкой");
        add("invtweaks.settings.beforebreak.tooltip", "Заменяет инструмент перед его поломкой");
        add("invtweaks.settings.moreoptions", "Доп. параметры...");
        add("invtweaks.settings.moreoptions.tooltip", "Больше крутых вещей");
        add("invtweaks.settings.rulesfile", "Открыть файл правил сортировки...");
        add("invtweaks.settings.rulesfile.error", "[8] Не удалось открыть файл правил");
        add("invtweaks.settings.treefile", "Открыть файл древа вещей...");
        add("invtweaks.settings.treefile.error", "[9] Не удалось открыть файл древа");
        add("invtweaks.settings.onlinehelp", "Открыть помощь в браузере...");
        add("invtweaks.settings.onlinehelp.error", "[10] Не удалось открыть помощь");
        add("invtweaks.help.bugsorting", "Не работает?");
        add("invtweaks.help.bugsorting.pt1", "Если сервер блокирует действия мода,");
        add("invtweaks.help.bugsorting.pt2", "то это, скорее всего, вызвано плагином NoCheatPlus.");
        add("invtweaks.help.bugsorting.pt3", "Попросите администратора сервера отключить следующие параметры:");
        add("invtweaks.help.bugsorting.pt4", "\"inventory.fastclick\" и \"combined.improbable\"");
        add("invtweaks.settings.pvpwarning.pt1", "Важно: На PvP серверах автоподмена и автоэкипирование");
        add("invtweaks.settings.pvpwarning.pt2", "зачастую принимаются за читерство!");
        add("invtweaks.settings.exit", "Готово");
        add("invtweaks.settings.advanced.sortonpickup", "Сортировать при поднимании");
        add("invtweaks.settings.advanced.sortonpickup.soloonly", "Только для одиночной игры");
        add("invtweaks.settings.advanced.sortonpickup.tooltip", "Избегает заполнения панели быстрого доступа\nподнятыми предметами");
        add("invtweaks.settings.advanced.sortonpickup.tooltip.soloonly", "Только в одиночной игре");
        add("invtweaks.settings.advanced.autoequip", "Автоэкипирование брони");
        add("invtweaks.settings.advanced.autoequip.tooltip", "Экипирует лучшую броню\nпри сортировке");
        add("invtweaks.settings.advanced.sounds", "Включить звуки");
        add("invtweaks.settings.advanced.sounds.tooltip", "Воспроизводить звук\nпри сортировке или автоподмене");
        add("invtweaks.settings.advanced.mappingsfile", "Открыть файл соответствия клавиш...");
        add("invtweaks.settings.advanced.mappingsfile.error", "[11] Не удалось открыть файл соответствий");
        add("invtweaks.settings.advanced.serverassist", "Передвижение предметов на сервере");
        add("invtweaks.settings.advanced.serverassist.tooltip", "Включить использование помощи сервера для передвижения предметов во время сортировки.");
        add("invtweaks.help.shortcuts.title", "Помощь по сочетаниям клавиш");
        add("invtweaks.help.shortcuts.click", "Клик");
        add("invtweaks.help.shortcuts.or", "или");
        add("invtweaks.help.shortcuts.onestack", "Переместить один стак");
        add("invtweaks.help.shortcuts.oneitem", "Переместить один предмет");
        add("invtweaks.help.shortcuts.allitems", "Переместить все предметы одного вида");
        add("invtweaks.help.shortcuts.everything", "Переместить все");
        add("invtweaks.help.shortcuts.hotbar", "Переместить на панель быстрого доступа");
        add("invtweaks.help.shortcuts.emptyslot", "Переместить в свободный слот");
        add("invtweaks.help.shortcuts.rightclick", "(Любая комбинация клавиш) + Правая вместо левой кнопки мыши");
        add("invtweaks.help.shortcuts.drop", "Выбросить");
        add("invtweaks.help.shortcuts.craftall", "Создать и перенести всё в инвентарь");
        add("invtweaks.help.shortcuts.craftone", "Создать и перенести в инвентарь");
        add("invtweaks.help.shortcuts.selectconfig", "Выберите настройки сортировки");
        add("invtweaks.help.shortcuts.forward", "(След.)");
        add("invtweaks.help.shortcuts.backward", "(Пред.)");
        add("invtweaks.keyboardfix.error", "[12] У меня проблемы с клавиатурой");
        add("invtweaks.keyboardfix.recover", "Отлично, починено. Сожалею о произошедшем.");
        add("invtweaks.modcompat.ci.error", "[13] Не удалось определить совместимость с 'Convenient Inventory'");
        add("key.category.inventorytweaksrevived.invtweaks", "Inventory Tweaks");
        add("invtweaks.key.sort", "Сортировка инвентаря");
    }
}

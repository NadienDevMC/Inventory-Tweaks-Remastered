package nadiendev.inventorytweaksrevived.data.lang;

import net.minecraft.data.PackOutput;

public class KoKrLangProvider extends InvTweaksLangProvider {
    public KoKrLangProvider(PackOutput output) {
        super(output, "ko_kr");
    }

    @Override
    protected void addTranslations() {
        add("invtweaks.loadconfig.enabled", "'%s' 활성화");
        add("invtweaks.loadconfig.refresh.error", "[1] 파일로부터 설정 불러오기 실패");
        add("invtweaks.loadconfig.invalidkeywords", "유효하지 않은 키워드 발견");
        add("invtweaks.loadconfig.filemissing", "파일 찾기 실패, 기본 설정으로 복구.");
        add("invtweaks.loadconfig.done", "구성 불러오기 완료.");
        add("invtweaks.propsfile.loaded", "모드 속성 불러오기 완료");
        add("invtweaks.propsfile.error", "[14] 모드 설정 파일 만들기 실패");
        add("invtweaks.extract.find.error", "[15] 모드 활성화 실패. %s가 발견되지 않음!");
        add("invtweaks.extract.create.error", "[16] 모드 활성화 실패. %s 만들기 실패!");
        add("invtweaks.sort.inventory.error", "[2] 인벤토리 정렬 실패");
        add("invtweaks.sort.chest.error", "[3] 컨테이너 정렬 실패");
        add("invtweaks.sort.autorefill.error", "[4] 자동 채우기 기능 실행 실패");
        add("invtweaks.sort.releaseitem.error", "[5] 아이템 릴리즈 실패");
        add("invtweaks.shortcut.error", "[6] 단축키 작동 실패");
        add("invtweaks.pickup.error", "[7] 들고 있는 스택 옮기기 실패");
        add("invtweaks.button.settings.tooltip", "인벤토리 설정");
        add("invtweaks.button.chest1.tooltip", "기본 정렬");
        add("invtweaks.button.chest2.tooltip", "열 정렬");
        add("invtweaks.button.chest3.tooltip", "행 정렬");
        add("invtweaks.settings.title", "인벤토리와 창고 설정");
        add("invtweaks.settings.on", "켜기");
        add("invtweaks.settings.off", "끄기");
        add("invtweaks.settings.key", "정렬 버튼:");
        add("invtweaks.settings.disableci", "CI 비활성화");
        add("invtweaks.settings.disableci.tooltip", "Convenient Inventory와 충돌 시 켜세요.");
        add("invtweaks.settings.middleclick", "마우스 휠 버튼 클릭");
        add("invtweaks.settings.middleclick.tooltip", "마우스 휠 버튼의 클릭으로 인벤토리를 정렬합니다.");
        add("invtweaks.settings.chestbuttons", "상자 버튼");
        add("invtweaks.settings.chestbuttons.tooltip", "상자 창에 3개의 아이템\n정렬 버튼을 추가합니다.");
        add("invtweaks.settings.shortcuts", "단축키");
        add("invtweaks.settings.shortcuts.tooltip", "아이템을 쉽게 정렬하고 이동하기 위한 단축키를 설정할 수 있습니다.");
        add("invtweaks.settings.autorefill", "자동 채우기");
        add("invtweaks.settings.autorefill.tooltip", "부서진 도구들과 비워진 스택을 인벤토리에서의 같은 아이템으로 교체합니다.");
        add("invtweaks.settings.beforebreak", "수리가 가능하도록 교체");
        add("invtweaks.settings.beforebreak.tooltip", "수리가 가능하도록 도구가 부서지기 직전에 교체합니다.");
        add("invtweaks.settings.moreoptions", "기타 옵션...");
        add("invtweaks.settings.moreoptions.tooltip", "그 외 많은 옵션들이 있습니다!");
        add("invtweaks.settings.rulesfile", "정렬 규칙 파일 열기...");
        add("invtweaks.settings.rulesfile.error", "[8] 규칙 파일 열기 실패");
        add("invtweaks.settings.treefile", "아이템 트리 파일 열기...");
        add("invtweaks.settings.treefile.error", "[9] 트리 파일 열기 실패");
        add("invtweaks.settings.onlinehelp", "브라우저에서 도움말 보기...");
        add("invtweaks.settings.onlinehelp.error", "[10] 도움말 열기 실패");
        add("invtweaks.help.bugsorting", "문제가 있나요?");
        add("invtweaks.help.bugsorting.pt1", "만약 서버가 이 모드의 행동을 '취소'하는것처럼 보인다면,");
        add("invtweaks.help.bugsorting.pt2", "아마 NoCheatPlus 플러그인에 의한 일이였을 수 있습니다.");
        add("invtweaks.help.bugsorting.pt3", "서버 관리자에게 다음의 옵션들을 비활성화할 수 있는지 물어보세요 :");
        add("invtweaks.help.bugsorting.pt4", "\"inventory.fastclick\" 및 \"combined.improbable\"");
        add("invtweaks.settings.pvpwarning.pt1", "참고 : PvP 서버에서, 아이템 자동 채우기 및 방어구 자동 착용 기능은");
        add("invtweaks.settings.pvpwarning.pt2", "대부분 부정 행위로 간주됩니다!");
        add("invtweaks.settings.exit", "완료");
        add("invtweaks.settings.advanced.sortonpickup", "픽업 시 정렬");
        add("invtweaks.settings.advanced.sortonpickup.soloonly", "싱글 플레이어 전용");
        add("invtweaks.settings.advanced.sortonpickup.tooltip", "막 주운 아이템이 주요 아이템 바를 채우지 않도록 방지합니다.");
        add("invtweaks.settings.advanced.sortonpickup.tooltip.soloonly", "싱글 플레이어에서만 가능한 기능입니다.");
        add("invtweaks.settings.advanced.autoequip", "방어구 자동 착용");
        add("invtweaks.settings.advanced.autoequip.tooltip", "정렬 시 가장 좋은 방어구가 자동 착용됩니다.");
        add("invtweaks.settings.advanced.sounds", "효과음 사용");
        add("invtweaks.settings.advanced.sounds.tooltip", "자동 채우기 트리거가 실행되거나 정렬할 때 효과음을 발생시킵니다.");
        add("invtweaks.settings.advanced.mappingsfile", "단축키 매핑 파일 열기...");
        add("invtweaks.settings.advanced.mappingsfile.error", "[11] 매핑 파일을 열지 못하였습니다.");
        add("invtweaks.settings.advanced.serverassist", "서버에서 아이템 이동");
        add("invtweaks.settings.advanced.serverassist.tooltip", "서버 도우미를 이용하여 아이템 이동 및 정렬을 활성화합니다.");
        add("invtweaks.help.shortcuts.title", "단축키 도움말");
        add("invtweaks.help.shortcuts.click", "클릭");
        add("invtweaks.help.shortcuts.or", "또는");
        add("invtweaks.help.shortcuts.onestack", "한 스택 이동");
        add("invtweaks.help.shortcuts.oneitem", "한 아이템 이동");
        add("invtweaks.help.shortcuts.allitems", "같은 아이템 전부 이동");
        add("invtweaks.help.shortcuts.everything", "모두 이동");
        add("invtweaks.help.shortcuts.hotbar", "주요 아이템 바로 이동");
        add("invtweaks.help.shortcuts.emptyslot", "빈 슬롯으로 이동");
        add("invtweaks.help.shortcuts.rightclick", "(아무 단축키) + 마우스 오른쪽 클릭");
        add("invtweaks.help.shortcuts.drop", "드롭");
        add("invtweaks.help.shortcuts.craftall", "제작되는 아이템 모두 인벤토리로 이동");
        add("invtweaks.help.shortcuts.craftone", "제작되는 아이템 하나만 인벤토리로 이동");
        add("invtweaks.help.shortcuts.selectconfig", "정렬 설정 선택");
        add("invtweaks.help.shortcuts.forward", "(앞으로)");
        add("invtweaks.help.shortcuts.backward", "(뒤로)");
        add("invtweaks.keyboardfix.error", "[12] 키보드 사용에 문제가 있습니다.");
        add("invtweaks.keyboardfix.recover", "복구했습니다. 불편을 끼쳐드려 죄송합니다.");
        add("invtweaks.modcompat.ci.error", "[13] Convenient Inventory 와의 호환성을 유지하지 못했습니다.");
        add("key.category.inventorytweaksrevived.invtweaks", "Inventory Tweaks");
        add("invtweaks.key.sort", "인벤토리 정렬");
    }
}

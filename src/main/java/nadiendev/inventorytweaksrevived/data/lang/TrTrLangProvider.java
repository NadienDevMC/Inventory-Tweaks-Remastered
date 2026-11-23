package nadiendev.inventorytweaksrevived.data.lang;

import net.minecraft.data.PackOutput;

public class TrTrLangProvider extends InvTweaksLangProvider {
    public TrTrLangProvider(PackOutput output) {
        super(output, "tr_tr");
    }

    @Override
    protected void addTranslations() {
        add("invtweaks.loadconfig.enabled", "'%s' aktif");
        add("invtweaks.loadconfig.refresh.error", "[1] Özellikler dosyadan yenilenemedi");
        add("invtweaks.loadconfig.invalidkeywords", "Geçersiz anahtar kelimeler bulundu");
        add("invtweaks.loadconfig.filemissing", "bulunamadı, yenisi oluşturuluyor.");
        add("invtweaks.loadconfig.done", "Ayarlar yüklendi.");
        add("invtweaks.propsfile.loaded", "Mod özellikleri yüklendi");
        add("invtweaks.propsfile.error", "[14] Mod ayarları dosyası oluşturulurulamadı");
        add("invtweaks.extract.find.error", "[15] Mod çalışamayacaktır, çünkü %s bulunamadı!");
        add("invtweaks.extract.create.error", "[16] Mod çalışamaz, çünkü %s oluşturulmadı!");
        add("invtweaks.sort.inventory.error", "[2] Envanter sıralanamadı");
        add("invtweaks.sort.chest.error", "[3] Konteynır sıralanamadı");
        add("invtweaks.sort.autorefill.error", "[4] Otomatik dolum tetiklenemedi");
        add("invtweaks.sort.releaseitem.error", "[5] Eşya bırakılamadı");
        add("invtweaks.shortcut.error", "[6] Kısayol tetiklenemedi");
        add("invtweaks.pickup.error", "[7] Seçilen yığın taşınamadı");
        add("invtweaks.button.settings.tooltip", "Envanter Ayarları");
        add("invtweaks.button.chest1.tooltip", "Varsayılan sıralama");
        add("invtweaks.button.chest2.tooltip", "Sütunlar halinde sırala");
        add("invtweaks.button.chest3.tooltip", "Satırlar halinde sırala");
        add("invtweaks.settings.title", "Envanter ve sandık ayarları");
        add("invtweaks.settings.on", "Açık");
        add("invtweaks.settings.off", "Kapalı");
        add("invtweaks.settings.key", "Sıralama kısayolu:");
        add("invtweaks.settings.disableci", "CI'yi devre dışı bırak");
        add("invtweaks.settings.disableci.tooltip", "Convenient Inventory ile çakışıyor");
        add("invtweaks.settings.middleclick", "Orta düğme");
        add("invtweaks.settings.middleclick.tooltip", "Orta tıkı kullanarak\nsıralamaya izin verir");
        add("invtweaks.settings.chestbuttons", "Sandık tuşları");
        add("invtweaks.settings.chestbuttons.tooltip", "Sandıklara onları sıralamak\niçin 3 tuş ekler");
        add("invtweaks.settings.shortcuts", "Kısayollar");
        add("invtweaks.settings.shortcuts.tooltip", "Etraftaki eşyaları hareket ettirmek \niçin birçok kısayol aktif eder");
        add("invtweaks.settings.autorefill", "Otomatik-dolum");
        add("invtweaks.settings.autorefill.tooltip", "Kırılan aletleri ve biten yığınları\nenvanterdeki diğerleri ile değiştirir");
        add("invtweaks.settings.beforebreak", "Onarım-dostu dolum");
        add("invtweaks.settings.beforebreak.tooltip", "Araçları kırılmadan hemen önce değiştirir");
        add("invtweaks.settings.moreoptions", "Daha fazla seçenek...");
        add("invtweaks.settings.moreoptions.tooltip", "Daha da havalı şeyler");
        add("invtweaks.settings.rulesfile", "Sıralama kuralları dosyasını aç...");
        add("invtweaks.settings.rulesfile.error", "[8] Kurallar dosyası açılamadı");
        add("invtweaks.settings.treefile", "Öğe ağacı dosyasını aç...");
        add("invtweaks.settings.treefile.error", "[9] Ağaç dosyası açılamadı");
        add("invtweaks.settings.onlinehelp", "Tarayıcda yardım aç...");
        add("invtweaks.settings.onlinehelp.error", "[10] Yardım açılamadı");
        add("invtweaks.help.bugsorting", "Çalışmıyor mu?");
        add("invtweaks.help.bugsorting.pt1", "Eğer sizin sunucunuz bu modun yaptığını 'iptal' ediyorsa,");
        add("invtweaks.help.bugsorting.pt2", "Bu muhtemelen NoCheatPlus eklentisinden dolayıdır.");
        add("invtweaks.help.bugsorting.pt3", "Yöneticinizden bu seçenekleri kapatmasını isteyin:");
        add("invtweaks.help.bugsorting.pt4", "\"inventory.fastclick\" ve \"combined.improbable\"");
        add("invtweaks.settings.pvpwarning.pt1", "Not: PvP sunucularında, otomatik dolum & atomatik zırh donatma");
        add("invtweaks.settings.pvpwarning.pt2", "seçenekleri, genellikle hile olarak kabul edilir!");
        add("invtweaks.settings.exit", "Bitti");
        add("invtweaks.settings.advanced.sortonpickup", "Alırken sırala");
        add("invtweaks.settings.advanced.sortonpickup.soloonly", "Yalnızca tek oyuncu");
        add("invtweaks.settings.advanced.sortonpickup.tooltip", "Hızlı erişim çubuğunun aldığınız her\nşey ile dolmasını önler");
        add("invtweaks.settings.advanced.sortonpickup.tooltip.soloonly", "Sadece tek oyunculu modda");
        add("invtweaks.settings.advanced.autoequip", "Zırhı oto-giyme");
        add("invtweaks.settings.advanced.autoequip.tooltip", "Sıralama yapılırken mevcut\nolan en iyi zırhı giydirir");
        add("invtweaks.settings.advanced.sounds", "Sesi etkinleştir");
        add("invtweaks.settings.advanced.sounds.tooltip", "Sıralama ya da otomatik yeniden\ndoldurma tetiklendiğinde ses oynatır");
        add("invtweaks.settings.advanced.mappingsfile", "Kısayol tuş ayarları dosyasını aç...");
        add("invtweaks.settings.advanced.mappingsfile.error", "[11] Kısayol tuşu ayarları dosyası açılamadı");
        add("invtweaks.settings.advanced.serverassist", "Sunucu Eşya Hareketi");
        add("invtweaks.settings.advanced.serverassist.tooltip", "Sıralama yaparken öğe hareketi için sunucudan yardım alarak etkinleştir.");
        add("invtweaks.help.shortcuts.title", "Kısayol yardımı");
        add("invtweaks.help.shortcuts.click", "Tıkla");
        add("invtweaks.help.shortcuts.or", "veya");
        add("invtweaks.help.shortcuts.onestack", "Bir stok taşı");
        add("invtweaks.help.shortcuts.oneitem", "Bir öğeyi taşı");
        add("invtweaks.help.shortcuts.allitems", "Aynı türdeki tüm öğeleri taşı");
        add("invtweaks.help.shortcuts.everything", "Her şeyi taşı");
        add("invtweaks.help.shortcuts.hotbar", "Hotbar'a taşı");
        add("invtweaks.help.shortcuts.emptyslot", "Boş slota taşı");
        add("invtweaks.help.shortcuts.rightclick", "(Herhangi bir kısayol) + sağ yerine sol tık");
        add("invtweaks.help.shortcuts.drop", "Bırak");
        add("invtweaks.help.shortcuts.craftall", "Bütün enventere üret");
        add("invtweaks.help.shortcuts.craftone", "Envertere üret");
        add("invtweaks.help.shortcuts.selectconfig", "Sıralama yapılandırmasını seç");
        add("invtweaks.help.shortcuts.forward", "(İleri)");
        add("invtweaks.help.shortcuts.backward", "(Geri)");
        add("invtweaks.keyboardfix.error", "[12] Klavye ile sorun yaşıyorum");
        add("invtweaks.keyboardfix.recover", "Tamam, düzeltildi, bunun için üzgünüz.");
        add("invtweaks.modcompat.ci.error", "[13] 'Convenient Inventory' uyumluluğunu yönetilemedi");
        add("invtweaks.key.category", "Inventory Tweaks");
        add("invtweaks.key.sort", "Envanter Sıralama");
    }
}

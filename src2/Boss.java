/**
 * Boss adalah Monster yang lebih kuat.
 *
 * [Syarat 4 - Inheritance] Boss extends Monster (bukan langsung Karakter). Rantai
 * pewarisannya: Karakter -> Monster -> Boss. Boss mewarisi semua atribut dan method
 * dari Monster dan Karakter.
 */
public class Boss extends Monster {
    // Atribut khusus Boss: damageApi = damage semburan api, giliran = penghitung giliran boss.
    private int damageApi;
    private int giliran;

    // [Syarat 5 - Super Chaining] super(...) memanggil constructor Monster 5 parameter.
    // Atribut milik Boss diisi setelahnya.
    public Boss(String id, String nama, String jenis, int hp, int attack) {
        super(id, nama, jenis, hp, attack);
        this.damageApi = 15;
        this.giliran = 0;
    }

    // Getter dan setter
    public int getDamageApi() {
        return damageApi;
    }
    public void setDamageApi(int damageApi) {
        this.damageApi = Math.max(0, damageApi);
    }
    public int getGiliran() {
        return giliran;
    }
    public void setGiliran(int giliran) {
        this.giliran = Math.max(0, giliran);
    }

    // Overriding aksiKhusus: perilaku Monster + semburan api setiap 3 giliran
    // [Syarat 6 + 5] Boss menimpa (override) aksiKhusus() milik Monster.
    // super.aksiKhusus(target) menjalankan perilaku Monster (termasuk mode mengamuk),
    // lalu Boss menambah semburan api setiap giliran kelipatan 3.
    // "giliran % 3 == 0" memakai operator modulus (sisa bagi): benar saat giliran 3, 6, 9, ...
    @Override
    public void aksiKhusus(Karakter target) {
        giliran++;
        super.aksiKhusus(target);
        if (giliran % 3 == 0 && isHidup() && target.isHidup()) {
            System.out.println(getNama() + " menyemburkan api!");
            target.terimaDamage(damageApi);
            System.out.println("Damage api: " + damageApi);
        }
    }

    // [Syarat 5] super.tampilkanInfo() milik Monster (yang memanggil Karakter), lalu
    // Boss menambah info damage api.
    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.println("Damage Api    : " + damageApi);
    }
}

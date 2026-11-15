/**
 * Subclass dari Karakter yang mewakili karakter yang dimainkan pengguna.
 *
 * [Syarat 4 - Inheritance] "extends Karakter": mewarisi hp, attack, serang(), dst.
 * [Syarat 10 - Multiple Interface] "implements BisaBertarung, BisaMenggunakanItem":
 * mengikuti dua kontrak sekaligus, jadi wajib menulis bertarung() dan gunakanItem().
 */
public class Pemain extends Karakter implements BisaBertarung, BisaMenggunakanItem {

    // Konstanta (menggantikan angka 50 dan 100 yang sebelumnya tertulis langsung)
    // [Syarat 2] Konstanta public static final. Diberi nama supaya tidak ada "angka
    // ajaib" (50 dan 100) yang tersebar di kode, dan mudah diubah di satu tempat.
    public static final int MAX_STAMINA = 50;
    public static final int EXP_LEVEL_UP = 100;

    // Atribut tambahan khusus Pemain (Karakter tidak punya level/exp/gold/stamina).
    // Semuanya private [Syarat 3].
    private int level;
    private int exp;
    private int gold;
    private int stamina;

    // Constructor pertama
    // [Syarat 3 - Overloading] Dua constructor Pemain. Constructor 2 parameter ini
    // memanggil constructor 4 parameter lewat this(...) dengan HP 100 dan attack 20.
    public Pemain(String id, String nama) {
        this(id, nama, 100, 20);
    }

    // Constructor kedua (overloading)
    // [Syarat 5 - Super Chaining] super(id, nama, hp, attack) memanggil constructor
    // milik Karakter (parent) untuk mengisi id, nama, hp, attack. super(...) harus
    // menjadi baris PERTAMA di constructor. Setelah itu baru atribut milik Pemain
    // diisi nilai awalnya.
    public Pemain(String id, String nama, int hp, int attack) {
        super(id, nama, hp, attack);
        this.level = 1;
        this.exp = 0;
        this.gold = 50;
        this.stamina = MAX_STAMINA;
    }

    // Getter dan setter
    // Getter dan setter atribut milik Pemain. Setter memberi validasi (tidak boleh
    // minus, stamina tidak boleh melebihi MAX_STAMINA).
    public int getLevel() {
        return level;
    }

    public int getExp() {
        return exp;
    }
    public void setLevel(int level) {
        this.level = Math.max(1, level);
    }
    public void setExp(int exp) {
        this.exp = Math.max(0, exp);
    }
    public int getGold() {
        return gold;
    }
    public void setGold(int gold) {
        this.gold = Math.max(0, gold);
    }
    public int getStamina() {
        return stamina;
    }
    public void setStamina(int stamina) {
        if (stamina < 0) {
            this.stamina = 0;
        } else if (stamina > MAX_STAMINA) {
            this.stamina = MAX_STAMINA;
        } else {
            this.stamina = stamina;
        }
    }

    // Implementasi interface BisaBertarung
    // [Syarat 6] @Override menandakan method ini menulis ulang/mengisi method dari
    // parent atau interface. Jika nama method salah ketik, compiler langsung memberi error.
    // Di sini Pemain bertarung dengan memanggil serang().
    @Override
    public void bertarung(Karakter target) {
        serang(target);
    }

    // Implementasi interface BisaMenggunakanItem
    // Mengembalikan true/false dari item.gunakan(this). "this" = objek Pemain yang sedang
    // dijalankan. Nilai ini dipakai Main untuk menentukan apakah monster mendapat giliran.
    @Override
    public boolean gunakanItem(Item item) {
        if (item != null && isHidup()) {
            return item.gunakan(this);
        } else {
            System.out.println("Item tidak dapat digunakan!");
            return false;
        }
    }

    // Method overriding
    // [Syarat 6 - Method Overriding] Pemain menulis ulang serang() milik Karakter agar
    // menampilkan teks serangan. [Syarat 5] super.serang(target) memanggil versi
    // Karakter, yang melakukan pengurangan HP sebenarnya.
    @Override
    public void serang(Karakter target) {
        System.out.println(getNama() + " menyerang "
                + target.getNama() + "!");
        super.serang(target);
        System.out.println("Damage: " + getAttack());
    }

    // Implementasi abstract method
    // [Syarat 7] Mengisi abstract method aksiKhusus() dari Karakter.
    // [Syarat 8 - Logika State] if/else MEMBACA state (stamina) dan MENGUBAH state:
    //   - stamina cukup  : stamina berkurang 20, target menerima damage spesial
    //   - stamina kurang : tidak menyerang, stamina dipulihkan 10
    // Tanda && artinya "dan": semua kondisi harus benar.
    @Override
    public void aksiKhusus(Karakter target) {
        if (stamina >= 20 && isHidup() && target.isHidup()) {
            stamina -= 20;
            int damage = getAttack() + 10;
            System.out.println(getNama()
                    + " menggunakan serangan spesial!");
            target.terimaDamage(damage);
            System.out.println("Damage spesial: " + damage);
        } else {
            System.out.println("Stamina tidak cukup!");
            setStamina(stamina + 10);
            System.out.println("Stamina dipulihkan 10 poin.");
        }
    }

    // Menambahkan EXP dan gold
    // "this.exp += exp": this.exp adalah atribut objek, exp di kanan adalah parameter.
    // Perulangan while dipakai (bukan if) agar jika EXP sangat besar, pemain tetap
    // bisa naik beberapa level sekaligus.
    public void dapatHadiah(int exp, int gold) {
        this.exp += exp;
        this.gold += gold;
        System.out.println("Mendapatkan " + exp + " EXP");
        System.out.println("Mendapatkan " + gold + " Gold");
        while (this.exp >= EXP_LEVEL_UP) {
            this.exp -= EXP_LEVEL_UP;
            level++;
            setAttack(getAttack() + 5);
            setHp(MAX_HP);
            System.out.println("LEVEL UP!");
            System.out.println("Level sekarang: " + level);
        }
    }

    // Overriding tampilkanInfo
    // [Syarat 5] super.tampilkanInfo() menampilkan ID, nama, HP, attack dari Karakter,
    // lalu Pemain menambah info level, EXP, gold, dan stamina.
    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.println("Level   : " + level);
        System.out.println("EXP     : " + exp);
        System.out.println("Gold    : " + gold);
        System.out.println("Stamina : " + stamina);
    }
}

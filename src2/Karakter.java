/**
 * SUPERCLASS ABSTRACT: induk dari semua makhluk di game (Pemain dan Monster).
 *
 * [Syarat 7 - Abstract Class] Class bertanda "abstract" tidak boleh dibuat
 * objeknya langsung (new Karakter(...) akan error). Ia berfungsi sebagai cetakan
 * umum yang diturunkan ke subclass. Class abstract boleh punya method biasa (ada
 * isinya) dan method abstract (tanpa isi, wajib ditulis ulang oleh subclass).
 *
 * [Syarat 4 - Inheritance] Pemain dan Monster memakai "extends Karakter", jadi
 * otomatis punya semua atribut dan method di sini (hp, attack, serang(), dst).
 */
public abstract class Karakter {

    // Konstanta global
    // [Syarat 2 - Immutability] KONSTANTA GLOBAL.
    //   public : bisa dibaca dari class mana saja (Karakter.MAX_HP)
    //   static : milik class, bukan milik objek (tanpa perlu new)
    //   final  : nilainya tidak bisa diubah setelah ditetapkan
    // Penamaan konstanta memakai HURUF_BESAR_DENGAN_UNDERSCORE.
    public static final int MAX_HP = 100;

    // Atribut
    // [Syarat 2 - Immutability] ATRIBUT INSTANS KEKAL. "private final" artinya nilainya
    // diisi sekali di constructor lalu tidak bisa diubah. Karena itu tidak ada
    // setIdKarakter() di bawah, hanya getter.
    // [Syarat 1 - State] Atribut (variabel) menyimpan keadaan/state objek.
    // [Syarat 3 - Enkapsulasi] Semuanya "private": hanya bisa diakses dari dalam
    // class ini. Class lain harus lewat getter/setter, sehingga nilainya bisa
    // divalidasi (lihat setHp).
    private final String idKarakter;
    private String nama;
    private int hp;
    private int attack;

    // Constructor pertama
    // [Syarat 3 - Overloading] Ada dua constructor dengan nama sama tetapi parameter
    // berbeda. Java memilih yang cocok dari jumlah/tipe argumen saat memanggil new.
    //
    // this(...) memanggil constructor LAIN di class yang sama. Constructor 3 parameter
    // ini meneruskan ke constructor 4 parameter dengan attack bawaan 10, sehingga
    // logika pengisian atribut cukup ditulis di satu tempat.
    public Karakter(String idKarakter, String nama, int hp) {
        this(idKarakter, nama, hp, 10);
    }

    // Constructor kedua (overloading)
    // "this.nama = nama" : this menunjuk atribut milik objek (kiri), sedangkan "nama"
    // di kanan adalah parameter. this dibutuhkan karena namanya sama [Syarat 3].
    public Karakter(String idKarakter, String nama, int hp, int attack) {
        this.idKarakter = idKarakter;
        this.nama = nama;
        // Memakai setter (bukan this.hp = hp) supaya nilai langsung divalidasi: tidak
        // boleh minus dan tidak boleh melebihi MAX_HP.
        setHp(hp);
        setAttack(attack);
    }

    // Getter dan setter
    // [Syarat 3 - Enkapsulasi] GETTER dan SETTER.
    //   Getter (getXxx) : membaca nilai atribut private.
    //   Setter (setXxx) : mengubah nilai atribut private, bisa disertai validasi.
    // idKarakter hanya punya getter karena bersifat final (tidak boleh diubah).
    public String getIdKarakter() {
        return idKarakter;
    }
    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public int getHp() {
        return hp;
    }
    // Setter dengan validasi: HP dijaga tetap di rentang 0 sampai MAX_HP. Inilah
    // keuntungan enkapsulasi, nilai tidak valid dicegah di satu tempat.
    public void setHp(int hp) {
        if (hp < 0) {
            this.hp = 0;
        } else if (hp > MAX_HP) {
            this.hp = MAX_HP;
        } else {
            this.hp = hp;
        }
    }
    public int getAttack() {
        return attack;
    }
    public void setAttack(int attack) {
        this.attack = Math.max(0, attack);
    }

    // Mengecek kondisi hidup
    // [Syarat 1 - Behavior] METHOD FUNGSI: punya tipe kembalian (boolean) dan memakai
    // "return" untuk mengirim nilai ke pemanggilnya.
    public boolean isHidup() {
        return hp > 0;
    }

    // Method untuk menyerang
    // [Syarat 1 - Behavior] METHOD PROSEDUR: bertipe void, hanya melakukan aksi dan
    // tidak mengembalikan nilai. Parameternya bertipe Karakter, jadi targetnya bisa
    // Pemain maupun Monster. Method ini di-override oleh Pemain dan Monster.
    public void serang(Karakter target) {
        if (isHidup() && target.isHidup()) {
            target.terimaDamage(attack);
        }
    }

    // Method menerima damage
    // Mengurangi HP lewat setHp() supaya validasi tetap berlaku (HP tidak bisa di
    // bawah 0). Dipanggil oleh serang() dan aksiKhusus().
    public void terimaDamage(int damage) {
        setHp(hp - damage);
    }

    // Method memulihkan HP
    // Menambah HP lewat setHp() (otomatis dibatasi MAX_HP). Dipanggil oleh Item.gunakan().
    public void pulihkanHp(int jumlah) {
        setHp(hp + jumlah);
    }

    // Menampilkan informasi karakter
    // Di-override oleh Pemain, Monster, dan Boss. Masing-masing memanggil
    // super.tampilkanInfo() lalu menambahkan info miliknya sendiri.
    public void tampilkanInfo() {
        System.out.println("ID     : " + idKarakter);
        System.out.println("Nama   : " + nama);
        System.out.println("HP     : " + hp);
        System.out.println("Attack : " + attack);
    }

    // Abstract method
    // [Syarat 7 - Abstract Method] Hanya berisi tanda tangan method, TANPA isi (tidak
    // ada kurung kurawal) dan diakhiri titik koma. Setiap subclass non-abstract WAJIB
    // menulis isinya dengan @Override. Pemain dan Monster punya aksi khusus berbeda.
    public abstract void aksiKhusus(Karakter target);
}

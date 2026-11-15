/**
 * Subclass dari Karakter yang mewakili musuh.
 *
 * Monster juga implements BisaBertarung, jadi Pemain dan Monster bisa disimpan
 * dalam satu array bertipe BisaBertarung (lihat serangan pembuka di Main).
 */
public class Monster extends Karakter implements BisaBertarung {
    // Atribut khusus Monster. mengamuk adalah state boolean (true = mode mengamuk aktif).
    private String jenisMonster;
    private boolean mengamuk;
    // [Syarat 3 - Overloading] Constructor pertama (5 parameter).
    // [Syarat 5 - Super Chaining] super(...) memanggil constructor Karakter 4 parameter.
    public Monster(String id, String nama, String jenis, int hp, int attack) {
        super(id, nama, hp, attack);
        this.jenisMonster = jenis;
        this.mengamuk = false;
    }

    // Constructor kedua (overloading): attack memakai nilai bawaan Karakter
    // super(id, nama, hp) memanggil constructor Karakter 3 parameter, sehingga attack
    // otomatis bernilai bawaan 10.
    public Monster(String id, String nama, String jenis, int hp) {
        super(id, nama, hp);
        this.jenisMonster = jenis;
        this.mengamuk = false;
    }

    // Getter dan setter
    // Getter dan setter atribut milik Monster. Untuk boolean, getter bernama isXxx().
    public String getJenisMonster() {
        return jenisMonster;
    }
    public void setJenisMonster(String jenisMonster) {
        this.jenisMonster = jenisMonster;
    }
    public boolean isMengamuk() {
        return mengamuk;
    }
    public void setMengamuk(boolean mengamuk) {
        this.mengamuk = mengamuk;
    }

    // Implementasi interface BisaBertarung
    // Monster ikut menulis bertarung() agar bisa diperlakukan sebagai BisaBertarung.
    @Override
    public void bertarung(Karakter target) {
        serang(target);
    }

    // Overriding method serang
    // [Syarat 6] Override serang() milik Karakter. [Syarat 5] super.serang(target)
    // memanggil versi Karakter untuk pengurangan HP.
    @Override
    public void serang(Karakter target) {
        System.out.println(getNama()
                + " menyerang " + target.getNama() + "!");
        super.serang(target);
        System.out.println("Damage: " + getAttack());
    }

    // Implementasi abstract method
    // [Syarat 7 + 8] Mengisi aksiKhusus() dengan logika state monster:
    //   - HP <= 30 dan belum mengamuk : masuk mode mengamuk (attack +5), hanya sekali
    //   - selain itu                  : serangan biasa
    // Setelah itu monster menyerang target lewat serang().
    @Override
    public void aksiKhusus(Karakter target) {
        if (getHp() <= 30 && !mengamuk && isHidup()) {
            mengamuk = true;
            setAttack(getAttack() + 5);
            System.out.println(getNama()
                    + " memasuki MODE MENGAMUK!");
            System.out.println("Attack monster meningkat!");
        } else if (isHidup()) {
            System.out.println(getNama()
                    + " melakukan serangan biasa.");
        }
        if (isHidup() && target.isHidup()) {
            serang(target);
        }
    }

    // [Syarat 5] super.tampilkanInfo() lalu menambah info jenis monster dan status mengamuk.
    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.println("Jenis Monster : " + jenisMonster);
        System.out.println("Mengamuk      : " + mengamuk);
    }
}

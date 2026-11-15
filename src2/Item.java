/**
 * Item pemulih HP (Health Potion) yang bisa dipakai Pemain.
 *
 * Item bukan turunan Karakter (tidak ada "extends"), jadi ini class biasa yang
 * berdiri sendiri. Pemain memakainya lewat method gunakanItem().
 */
public class Item {
    // Atribut private (enkapsulasi). "jumlah" adalah stok item yang tersisa.
    private String namaItem;
    private int pemulihan;
    private int jumlah;

    // Constructor. setJumlah() dipakai supaya stok awal tidak bisa bernilai negatif.
    public Item(String namaItem, int pemulihan, int jumlah) {
        this.namaItem = namaItem;
        this.pemulihan = pemulihan;
        setJumlah(jumlah);
    }

    // Getter dan setter
    // Getter membaca atribut, setter mengubahnya (dengan validasi Math.max agar tidak minus).
    public String getNamaItem() {
        return namaItem;
    }
    public void setNamaItem(String namaItem) {
        this.namaItem = namaItem;
    }
    public int getPemulihan() {
        return pemulihan;
    }
    public void setPemulihan(int pemulihan) {
        this.pemulihan = Math.max(0, pemulihan);
    }
    public int getJumlah() {
        return jumlah;
    }
    public void setJumlah(int jumlah) {
        this.jumlah = Math.max(0, jumlah);
    }

    // Menggunakan item (true jika berhasil dipakai)
    // METHOD FUNGSI bertipe boolean: true = item berhasil dipakai, false = gagal.
    // Nilai ini dipakai Main untuk memutuskan apakah monster mendapat giliran menyerang.
    public boolean gunakan(Pemain pemain) {
        // Tiga kemungkinan (if / else if / else):
        //   1) stok habis           -> gagal (false)
        //   2) HP belum penuh       -> potion dipakai, stok berkurang (true)
        //   3) HP sudah penuh       -> gagal (false), potion tidak terbuang
        if (jumlah <= 0) {
            System.out.println(namaItem + " sudah habis!");
            return false;
        } else if (pemain.getHp() < Karakter.MAX_HP) {
            System.out.println("Menggunakan " + namaItem);
            // jumlah-- artinya jumlah = jumlah - 1 (stok berkurang satu).
            jumlah--;
            pemain.pulihkanHp(pemulihan);
            System.out.println("HP berhasil dipulihkan!");
            System.out.println("HP sekarang: " + pemain.getHp());
            System.out.println("Sisa " + namaItem + ": " + jumlah);
            return true;
        } else {
            System.out.println("HP masih penuh!");
            return false;
        }
    }
}

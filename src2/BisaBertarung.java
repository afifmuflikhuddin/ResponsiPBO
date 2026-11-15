/**
 * INTERFACE = "kontrak" berisi daftar method yang WAJIB dimiliki class yang
 * meng-implement-nya. Interface hanya menyebut nama method, tanpa isi.
 *
 * Class yang menulis "implements BisaBertarung" harus menulis isi method
 * bertarung(). Di proyek ini: Pemain dan Monster.
 *
 * [Syarat 9 - Polimorfisme Interface] Objek boleh disimpan di variabel bertipe
 * interface, misalnya: BisaBertarung petarung = pemain; (lihat Main.java).
 */
public interface BisaBertarung {
    // Hanya "tanda tangan" method (tanpa kurung kurawal). Isinya ditulis oleh
    // Pemain dan Monster dengan cara masing-masing.
    void bertarung(Karakter target);
}

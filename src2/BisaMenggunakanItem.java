/**
 * INTERFACE kedua. Dipakai untuk karakter yang bisa memakai Item (potion).
 * Saat ini hanya Pemain yang meng-implement-nya.
 *
 * [Syarat 10 - Multiple Interface] Pemain meng-implement DUA interface sekaligus
 * (BisaBertarung dan BisaMenggunakanItem). Satu class boleh implements banyak
 * interface, dipisah koma. Berbeda dengan extends yang hanya boleh satu class.
 */
public interface BisaMenggunakanItem {
    // Mengembalikan true jika item benar-benar terpakai
    boolean gunakanItem(Item item);
}

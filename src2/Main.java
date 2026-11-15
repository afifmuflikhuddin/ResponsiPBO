import java.util.Scanner;
/**
 * Class Main dipisah dari class lain [Syarat 4]. Isinya hanya alur permainan:
 * membuat objek, menampilkan menu, dan menjalankan pertarungan.
 *
 * Urutan belajar yang disarankan: BisaBertarung -> Karakter -> Pemain -> Monster
 * -> Boss -> Item -> Main.
 */
public class Main {
    public static void main(String[] args) {
        // Scanner dipakai untuk membaca ketikan pengguna dari terminal.
        Scanner input = new Scanner(System.in);

        // Membuat objek pemain
        System.out.println("================================");
        System.out.println("       PERBURUAN MONSTER");
        System.out.println("================================");
        System.out.print("Masukkan nama pemain: ");
        String nama = input.nextLine();
        // Membuat objek Pemain. Constructor yang dipakai adalah yang 2 parameter
        // (HP dan attack memakai nilai bawaan lewat this(...)).
        Pemain pemain = new Pemain("P001", nama);

        // Membuat daftar monster (memakai dua konstruktor Monster yang berbeda)
        // Array bertipe Monster boleh berisi Monster maupun Boss (Boss adalah Monster).
        // Saat monster.aksiKhusus(...) dipanggil, Java menjalankan versi milik objek
        // aslinya, jadi untuk Boss yang berjalan adalah Boss.aksiKhusus().
        // Ini disebut polimorfisme (dynamic dispatch).
        // Dua constructor Monster dipakai di sini: 5 parameter (Goblin) dan 4 parameter (Slime).
        Monster[] daftarMonster = {
                new Monster("M001", "Goblin", "Monster Hutan", 80, 15),
                new Monster("M002", "Slime", "Monster Lendir", 60),
                new Boss("M003", "Naga Api", "Boss Gua", 100, 18)
        };

        // Membuat item
        // Item("Health Potion", pemulihan 30 HP, stok 3).
        Item potion = new Item("Health Potion", 30, 3);

        // Polimorfisme melalui interface
        // [Syarat 9 - Polimorfisme Interface] Variabel petarung bertipe interface, tetapi
        // objeknya Pemain. Lewat petarung hanya method milik interface (bertarung) yang
        // bisa dipanggil, tetapi yang dijalankan adalah isi method milik Pemain.
        BisaBertarung petarung = pemain;
        System.out.println("\nSelamat datang, "
                + pemain.getNama() + "!");
        System.out.println("\n===== STATUS PEMAIN =====");
        pemain.tampilkanInfo();
        // Penanda (flag) permainan. Jika diubah jadi false (kalah, menang, atau kabur),
        // semua perulangan berhenti.
        boolean bermain = true;

        // Perulangan monster (satu per satu)
        // Perulangan LUAR (for): mengambil monster satu per satu dari array daftarMonster.
        // Berhenti jika monster habis, pemain kalah, atau pemain memilih kabur.
        for (int i = 0; i < daftarMonster.length
                && bermain && pemain.isHidup(); i++) {

            Monster monster = daftarMonster[i];
            System.out.println("\n===== MONSTER " + (i + 1)
                    + " MUNCUL =====");
            monster.tampilkanInfo();

            // Serangan pembuka: Pemain dan Monster sama-sama BisaBertarung
            // (polimorfisme interface melalui array)
            BisaBertarung[] peserta = { pemain, monster };
            Karakter[] lawan = { monster, pemain };
            System.out.println("\n--- SERANGAN PEMBUKA ---");
            // Indeks yang sama dipasangkan: peserta[0] (pemain) menyerang lawan[0] (monster),
            // peserta[1] (monster) menyerang lawan[1] (pemain). Method bertarung() dipanggil
            // lewat tipe interface, tetapi yang dijalankan sesuai objek aslinya.
            for (int k = 0; k < peserta.length; k++) {
                peserta[k].bertarung(lawan[k]);
            }

            // Perulangan pertarungan
            // Perulangan DALAM (while): satu putaran = satu giliran pemain memilih aksi.
            // Berhenti jika pemain kabur, pemain kalah, atau monster ini kalah.
            while (bermain && pemain.isHidup()
                    && monster.isHidup()) {

                System.out.println("\n================================");
                System.out.println("             MENU");
                System.out.println("================================");
                System.out.println("1. Serang Monster");
                System.out.println("2. Serangan Spesial");
                System.out.println("3. Gunakan Potion (sisa: "
                        + potion.getJumlah() + ")");
                System.out.println("4. Lihat Status");
                System.out.println("5. Kabur");
                System.out.println("================================");
                System.out.print("Pilih aksi: ");
                // try/catch menangkap kesalahan input. Jika pengguna mengetik huruf, parseInt()
                // melempar NumberFormatException, lalu "continue" mengulang menu tanpa crash.
                int pilihan;
                try {
                    pilihan = Integer.parseInt(input.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Masukkan angka yang benar!");
                    continue;
                }
                // Penanda apakah monster boleh membalas di akhir putaran. Hanya aksi yang
                // "memakan giliran" (menu 1, 2, dan 3 jika berhasil) yang mengubahnya jadi true.
                boolean giliranMonster = false;
                // switch memilih blok kode sesuai angka menu. "break" mengakhiri tiap case.
                switch (pilihan) {
                    // Menu 1: serangan biasa lewat variabel bertipe interface BisaBertarung.
                    case 1:
                        petarung.bertarung(monster);
                        giliranMonster = true;
                        break;
                    // Menu 2: serangan spesial, memanggil aksiKhusus() milik Pemain (cek stamina).
                    case 2:
                        pemain.aksiKhusus(monster);
                        giliranMonster = true;
                        break;
                    // Menu 3: memakai potion. gunakanItem() mengembalikan boolean yang menentukan
                    // apakah monster mendapat giliran.
                    case 3:
                        // Giliran hanya terpakai jika potion benar-benar dipakai
                        giliranMonster = pemain.gunakanItem(potion);
                        break;
                    // Menu 4: hanya menampilkan status, tidak memakan giliran (giliranMonster tetap false).
                    // Perhatikan: tampilkanInfo() milik Pemain, Monster, dan Boss berbeda (override).
                    case 4:
                        System.out.println("\n===== STATUS PEMAIN =====");
                        pemain.tampilkanInfo();
                        System.out.println("\n===== STATUS MONSTER =====");
                        monster.tampilkanInfo();
                        break;
                    // Menu 5: kabur. bermain = false menghentikan semua perulangan.
                    case 5:
                        System.out.println("Kamu berhasil kabur!");
                        bermain = false;
                        break;
                    // default berjalan jika angka yang diketik tidak ada di daftar menu.
                    default:
                        System.out.println("Pilihan tidak tersedia!");
                        break;
                }
                // Giliran monster
                // Monster membalas jika: pemain memakai aksi yang memakan giliran, dan keduanya
                // masih hidup. monster.aksiKhusus() menjalankan versi milik objek aslinya
                // (Monster atau Boss).
                if (giliranMonster && monster.isHidup()
                        && pemain.isHidup()) {
                    System.out.println("\n--- GILIRAN MONSTER ---");
                    monster.aksiKhusus(pemain);
                    System.out.println("HP Pemain: "
                            + pemain.getHp());
                }
                // Kondisi monster dikalahkan
                // Jika monster mati: tampilkan pesan dan beri hadiah. Jika itu monster terakhir
                // (i == panjang array - 1), permainan selesai dengan kemenangan.
                if (!monster.isHidup()) {
                    System.out.println("\n================================");
                    System.out.println("   " + monster.getNama().toUpperCase()
                            + " DIKALAHKAN!");
                    System.out.println("================================");
                    pemain.dapatHadiah(60, 30);
                    if (i == daftarMonster.length - 1) {
                        System.out.println("\nSEMUA MONSTER DIKALAHKAN!");
                        bermain = false;
                    }
                }
                // Kondisi kekalahan
                // Jika HP pemain habis: tampilkan GAME OVER dan hentikan permainan.
                if (!pemain.isHidup()) {
                    System.out.println("\n================================");
                    System.out.println("       GAME OVER!");
                    System.out.println("================================");
                    System.out.println("Pemain telah dikalahkan.");
                    bermain = false;
                }
            }
        }
        System.out.println("\n================================");
        System.out.println("       PERMAINAN SELESAI");
        System.out.println("================================");
        input.close();
    }
}

package kendaraan_UTS;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;


/**
 * Class induk (superclass) yang menyimpan data umum kendaraan.
 */
class Kendaraan {
    private String namaKendaraan;
    private String nomorPlat;
    private double hargaSewa;   // per hari (Rupiah)
    private int lamaSewa;       // dalam hari

    // Constructor: urutan parameter sesuai ketentuan soal
    public Kendaraan(String namaKendaraan, String nomorPlat, double hargaSewa, int lamaSewa) {
        // Memakai setter agar validasi ikut berjalan
        setNamaKendaraan(namaKendaraan);
        setNomorPlat(nomorPlat);
        setHargaSewa(hargaSewa);
        setLamaSewa(lamaSewa);
    }

    // ===== Getter & Setter =====
    public String getNamaKendaraan() {
        return namaKendaraan;
    }

    public void setNamaKendaraan(String namaKendaraan) {
        if (namaKendaraan == null || namaKendaraan.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama kendaraan tidak boleh kosong.");
        }
        this.namaKendaraan = namaKendaraan.trim();
    }

    public String getNomorPlat() {
        return nomorPlat;
    }

    public void setNomorPlat(String nomorPlat) {
        if (nomorPlat == null || nomorPlat.trim().isEmpty()) {
            throw new IllegalArgumentException("Nomor plat tidak boleh kosong.");
        }
        this.nomorPlat = nomorPlat.trim().toUpperCase();
    }

    public double getHargaSewa() {
        return hargaSewa;
    }

    public void setHargaSewa(double hargaSewa) {
        // Validasi: tidak boleh negatif atau nol
        if (hargaSewa <= 0) {
            throw new IllegalArgumentException("Harga sewa harus lebih dari 0.");
        }
        this.hargaSewa = hargaSewa;
    }

    public int getLamaSewa() {
        return lamaSewa;
    }

    public void setLamaSewa(int lamaSewa) {
        // Validasi: minimal 1 hari
        if (lamaSewa < 1) {
            throw new IllegalArgumentException("Lama sewa minimal 1 hari.");
        }
        this.lamaSewa = lamaSewa;
    }

    // ===== Method utama =====

    /** Total biaya sewa = hargaSewa x lamaSewa */
    public double hitungBiaya() {
        return hargaSewa * lamaSewa;
    }

    /** Nama jenis kendaraan; di-override oleh subclass. */
    public String getJenis() {
        return "Kendaraan";
    }

    /** Menampilkan data umum kendaraan. Di-override pada Mobil dan Motor. */
    public void tampilkanInfo() {
        System.out.println("Jenis Kendaraan : " + getJenis());
        System.out.println("Nama            : " + namaKendaraan);
        System.out.println("Nomor Plat      : " + nomorPlat);
        System.out.println("Harga Sewa/Hari : " + formatRupiah(hargaSewa));
        System.out.println("Lama Sewa       : " + lamaSewa + " hari");
    }

    /** Memformat angka menjadi format Rupiah, contoh: Rp1.050.000 */
    public static String formatRupiah(double nilai) {
        DecimalFormatSymbols simbol = new DecimalFormatSymbols(new Locale("id", "ID"));
        simbol.setGroupingSeparator('.');
        simbol.setDecimalSeparator(',');
        DecimalFormat df = new DecimalFormat("#,##0.##", simbol);
        return "Rp" + df.format(nilai);
    }
}

/**
 * Class turunan (subclass) untuk kendaraan jenis mobil.
 */
class Mobil extends Kendaraan {
    private int jumlahKursi;

    public Mobil(String namaKendaraan, String nomorPlat, double hargaSewa,
                 int lamaSewa, int jumlahKursi) {
        super(namaKendaraan, nomorPlat, hargaSewa, lamaSewa);
        setJumlahKursi(jumlahKursi);
    }

    public int getJumlahKursi() {
        return jumlahKursi;
    }

    public void setJumlahKursi(int jumlahKursi) {
        // Validasi: harus lebih dari 0
        if (jumlahKursi <= 0) {
            throw new IllegalArgumentException("Jumlah kursi harus lebih dari 0.");
        }
        this.jumlahKursi = jumlahKursi;
    }

    @Override
    public String getJenis() {
        return "Mobil";
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo(); // data umum dari class induk
        System.out.println("Jumlah Kursi    : " + jumlahKursi);
    }
}

/**
 * Class turunan (subclass) untuk kendaraan jenis motor.
 */
class Motor extends Kendaraan {
    private String jenisMotor;

    public Motor(String namaKendaraan, String nomorPlat, double hargaSewa,
                 int lamaSewa, String jenisMotor) {
        super(namaKendaraan, nomorPlat, hargaSewa, lamaSewa);
        setJenisMotor(jenisMotor);
    }

    public String getJenisMotor() {
        return jenisMotor;
    }

    public void setJenisMotor(String jenisMotor) {
        // Validasi: hanya Matic, Manual, atau Sport (tidak peka huruf besar/kecil)
        if (jenisMotor != null) {
            String j = jenisMotor.trim();
            if (j.equalsIgnoreCase("Matic")) {
                this.jenisMotor = "Matic";
                return;
            } else if (j.equalsIgnoreCase("Manual")) {
                this.jenisMotor = "Manual";
                return;
            } else if (j.equalsIgnoreCase("Sport")) {
                this.jenisMotor = "Sport";
                return;
            }
        }
        throw new IllegalArgumentException("Jenis motor harus Matic, Manual, atau Sport.");
    }

    @Override
    public String getJenis() {
        return "Motor";
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo(); // data umum dari class induk
        System.out.println("Jenis Motor     : " + jenisMotor);
    }
}


/**
 * Class utama: menjalankan aplikasi dan menampilkan menu (Console).
 */
public class Kendaraan_UTS {
    private static final ArrayList<Kendaraan> daftarKendaraan = new ArrayList<>();
    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int pilihan;
        do {
            tampilkanMenu();
            pilihan = bacaInt("Pilih menu: ");
            System.out.println();

            switch (pilihan) {
                case 1: tambahKendaraan(); break;
                case 2: tampilkanKendaraan(); break;
                case 3: ubahData(); break;
                case 4: hitungBiaya(); break;
                case 5: detailKendaraan(); break;
                case 0: System.out.println("Terima kasih. Program selesai."); break;
                default: System.out.println("Menu tidak valid, coba lagi.");
            }
            System.out.println();
        } while (pilihan != 0);
    }

    private static void tampilkanMenu() {
        System.out.println("===== RENTAL KENDARAAN =====");
        System.out.println("1. Tambah Kendaraan");
        System.out.println("2. Tampilkan Kendaraan");
        System.out.println("3. Ubah Data");
        System.out.println("4. Hitung Biaya");
        System.out.println("5. Detail Kendaraan");
        System.out.println("0. Keluar");
    }

    // ===== Fitur 1: Tambah Kendaraan =====
    private static void tambahKendaraan() {
        System.out.println("--- Tambah Kendaraan ---");
        System.out.println("1. Mobil");
        System.out.println("2. Motor");
        int jenis = bacaInt("Pilih jenis kendaraan: ");
        if (jenis != 1 && jenis != 2) {
            System.out.println("Jenis kendaraan tidak valid.");
            return;
        }

        String nama = bacaString("Nama kendaraan   : ");
        String plat = bacaString("Nomor plat       : ");
        double harga = bacaDouble("Harga sewa/hari  : ");
        int lama = bacaInt("Lama sewa (hari)  : ");

        try {
            if (jenis == 1) {
                int kursi = bacaInt("Jumlah kursi      : ");
                daftarKendaraan.add(new Mobil(nama, plat, harga, lama, kursi));
            } else {
                String jenisMotor = bacaString("Jenis motor (Matic/Manual/Sport): ");
                daftarKendaraan.add(new Motor(nama, plat, harga, lama, jenisMotor));
            }
            System.out.println("Kendaraan berhasil ditambahkan.");
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal menambah kendaraan: " + e.getMessage());
        }
    }

    // ===== Fitur 2: Tampilkan Kendaraan =====
    private static void tampilkanKendaraan() {
        System.out.println("--- Daftar Kendaraan ---");
        if (daftarKendaraan.isEmpty()) {
            System.out.println("Belum ada data kendaraan.");
            return;
        }
        for (int i = 0; i < daftarKendaraan.size(); i++) {
            Kendaraan k = daftarKendaraan.get(i);
            System.out.println((i + 1) + ". " + k.getNamaKendaraan()
                    + " | " + k.getNomorPlat()
                    + " | " + k.getJenis()
                    + " | " + Kendaraan.formatRupiah(k.getHargaSewa()) + "/hari");
        }
    }

    // ===== Fitur 3: Ubah Data =====
    private static void ubahData() {
        System.out.println("--- Ubah Data ---");
        Kendaraan k = pilihKendaraan();
        if (k == null) return;

        System.out.println("1. Nama kendaraan");
        System.out.println("2. Nomor plat");
        System.out.println("3. Harga sewa");
        System.out.println("4. Lama sewa");
        if (k instanceof Mobil) {
            System.out.println("5. Jumlah kursi");
        } else if (k instanceof Motor) {
            System.out.println("5. Jenis motor");
        }
        int pilih = bacaInt("Data yang diubah: ");

        try {
            switch (pilih) {
                case 1: k.setNamaKendaraan(bacaString("Nama baru: ")); break;
                case 2: k.setNomorPlat(bacaString("Nomor plat baru: ")); break;
                case 3: k.setHargaSewa(bacaDouble("Harga sewa baru: ")); break;
                case 4: k.setLamaSewa(bacaInt("Lama sewa baru (hari): ")); break;
                case 5:
                    if (k instanceof Mobil) {
                        ((Mobil) k).setJumlahKursi(bacaInt("Jumlah kursi baru: "));
                    } else if (k instanceof Motor) {
                        ((Motor) k).setJenisMotor(bacaString("Jenis motor baru (Matic/Manual/Sport): "));
                    }
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
                    return;
            }
            System.out.println("Data berhasil diubah.");
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal mengubah data: " + e.getMessage());
        }
    }

    // ===== Fitur 4: Hitung Biaya =====
    private static void hitungBiaya() {
        System.out.println("--- Hitung Biaya ---");
        Kendaraan k = pilihKendaraan();
        if (k == null) return;

        System.out.println("Kendaraan       : " + k.getNamaKendaraan());
        System.out.println("Harga Sewa/Hari : " + Kendaraan.formatRupiah(k.getHargaSewa()));
        System.out.println("Lama Sewa       : " + k.getLamaSewa() + " hari");
        System.out.println("Total Biaya     : " + Kendaraan.formatRupiah(k.hitungBiaya()));
    }

    // ===== Fitur 5: Detail Kendaraan =====
    private static void detailKendaraan() {
        System.out.println("--- Detail Kendaraan ---");
        Kendaraan k = pilihKendaraan();
        if (k == null) return;

        k.tampilkanInfo(); // polimorfisme: versi Mobil / Motor dipanggil otomatis
        System.out.println("Total Biaya     : " + Kendaraan.formatRupiah(k.hitungBiaya()));
    }

    // ===== Helper =====

    /** Menampilkan daftar lalu meminta pengguna memilih nomor urut. */
    private static Kendaraan pilihKendaraan() {
        if (daftarKendaraan.isEmpty()) {
            System.out.println("Belum ada data kendaraan.");
            return null;
        }
        tampilkanKendaraan();
        int no = bacaInt("Pilih nomor kendaraan: ");
        if (no < 1 || no > daftarKendaraan.size()) {
            System.out.println("Nomor kendaraan tidak valid.");
            return null;
        }
        System.out.println();
        return daftarKendaraan.get(no - 1);
    }

    private static String bacaString(String pesan) {
        System.out.print(pesan);
        return input.nextLine();
    }

    private static int bacaInt(String pesan) {
        while (true) {
            System.out.print(pesan);
            try {
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa bilangan bulat.");
            }
        }
    }

    private static double bacaDouble(String pesan) {
        while (true) {
            System.out.print(pesan);
            try {
                return Double.parseDouble(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka.");
            }
        }
    }
}
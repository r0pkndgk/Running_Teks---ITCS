# ☕ IT Student Pride — Java Swing Desktop Edition

> **Kalimat Tugas:** `"aku bangga menjadi mahasiswa IT"`  
> **Teknologi:** Java murni (Java Swing GUI & AWT Graphics2D)  
> **File Program:** `RunningTeksIT.java` (Ringkas & Mandiri dalam 1 File)  
> **Konsep Desain:** *Minimalist Monochromatic Neo-Brutalism & Modern Dark Terminal*

---

## 1. Deskripsi Singkat Aplikasi

Aplikasi ini adalah program desktop berbasis **Java murni (Java Swing & Java AWT)** yang dirancang dengan estetika visual modern monokromatik. Menghilangkan kesan tampilan Java jadul (*retro/grey look*), aplikasi ini mengusung antarmuka bertema gelap (*dark mode*) dengan palet hitam pekat, aksen garis kisi teknikal, dan tipografi putih tegas yang menyala.

### Fitur Utama:
* **Teks Sentral:** Menampilkan kalimat utama **`"AKU BANGGA MENJADI MAHASISWA IT"`** sebagai pusat perhatian (*focal point*).
* **Smooth 60 FPS Running Text:** Teks bergerak mulus dari kanan ke kiri tanpa terputus (*seamless continuous loop*) menggunakan mesin `javax.swing.Timer`.
* **Graphics2D Rendering:** Mengaktifkan *Antialiasing* dan *Fractional Metrics* untuk memastikan teks dan grafis tajam dan halus.
* **Ambient Glow & Edge Vignette:** Dilengkapi efek bayangan neon monokromatik halus (*layered glow*) serta efek *gradient mask* di sisi kiri dan kanan agar teks tidak terpotong kaku.
* **Interactive Control Deck:** Tersedia tombol interaktif di bagian bawah jendela untuk:
  * **PAUSE / PLAY** (menghentikan dan melanjutkan animasi)
  * **ARAH** (membalik arah pergeseran: Kanan ke Kiri / Kiri ke Kanan)
  * **SPD** (mengatur pengali kecepatan pergeseran: 1X, 2X, 3X)
  * **GLOW** (mengaktifkan/menonaktifkan efek bayangan cahaya)

---

## 2. Persyaratan Sistem

* **Java Development Kit (JDK):** Versi 11 atau lebih baru (Kompatibel dengan JDK 11, JDK 17, JDK 21 LTS, hingga JDK 25+).
* **Sistem Operasi:** Windows, macOS, atau Linux (Mendukung semua OS yang memiliki JRE/JDK).
* **Dependensi Eksternal:** **0% (Tidak butuh file JAR eksternal)**. Program hanya menggunakan pustaka standar bawaan Java (`javax.swing.*` dan `java.awt.*`).

---

## 3. Cara Compile dan Run Program

Buka terminal (Command Prompt, PowerShell, atau Terminal VS Code) dan arahkan ke folder proyek:

```bash
cd "d:\Vscode folder\ITCS\Runningteks_Java"
```

### Langkah 1: Compile File Java
Ketik perintah berikut untuk mengompilasi kode sumber:
```bash
javac RunningTeksIT.java
```
Perintah ini akan menghasilkan file bytecode: `RunningTeksIT.class` dan `RunningTeksIT$MarqueeCanvas.class`.

### Langkah 2: Jalankan (Run) Aplikasi
Ketik perintah berikut untuk membuka jendela aplikasi desktop:
```bash
java RunningTeksIT
```
Jendela aplikasi desktop modern monokromatik akan langsung muncul di tengah layar monitor Anda.

---

## 4. Penjelasan Cara Kerja Kode (Materi Presentasi 100%)

Gunakan materi terstruktur di bawah ini saat mempresentasikan logika program kepada dosen atau penguji.

### A. Arsitektur Tiga Komponen Utama
1. **`JFrame (RunningTeksIT)`**: Berfungsi sebagai jendela utama aplikasi yang membungkus seluruh tata letak (*BorderLayout*), termasuk Header HUD, Panggung Marquee, dan Tombol Kontrol.
2. **`MarqueeCanvas extends JPanel`**: Komponen kanvas kustom tempat teks digambar dan dianimasikan menggunakan `paintComponent(Graphics g)`.
3. **`javax.swing.Timer`**: Jantung animasi yang memicu pembaruan koordinat piksel secara berkala.

---

### B. Bagaimana `javax.swing.Timer` dan Koordinat X Menggerakkan Teks

Animasi komputer pada dasarnya adalah ilusi optik dari gambar diam yang diperbarui dengan sangat cepat (frame per detik / FPS).

```
Timer Berdetak Tiap 16ms (~60 FPS)
       │
       ▼
[ Koordinat xPos Berkurang: xPos -= speed ]
       │
       ▼
[ Panggil repaint() -> Memanggil paintComponent(Graphics g) ]
       │
       ▼
[ Graphics2D Menggambar Teks di Posisi (xPos, yPos) yang Baru ]
```

#### 1. Inisialisasi Timer 60 FPS:
```java
// 16 milidetik ≈ 60 frame per detik (1000ms / 60 ≈ 16.6ms)
ActionListener tickAction = e -> {
    if (!isRunning) return;

    if (moveRTL) {
        xPos -= speed; // Geser ke kiri sebesar 'speed' piksel
    } else {
        xPos += speed; // Geser ke kanan
    }

    repaint(); // Memicu pemanggilan ulang method paintComponent()
};
animationTimer = new Timer(16, tickAction);
animationTimer.start();
```

* **Penjelasan:** Setiap $16\text{ milidetik}$, timer mengeksekusi *Action Listener*. Nilai variabel `xPos` dikurangi sebanyak $2\text{ piksel}$ (`xPos -= speed`). Kemudian method `repaint()` dipanggil agar Java menjadwalkan penggambaran ulang kanvas di layar.

---

### C. Logika Seamless Modulo Loop (Looping Tanpa Celah)

Jika kita hanya menggambar satu teks dan membiarkannya berjalan dari kanan ke kiri, akan ada jeda kosong yang panjang sebelum teks tersebut muncul kembali. Untuk mengatasinya, kita menggunakan logika **Continuous Modulo Translation**:

```java
FontMetrics fm = g2d.getFontMetrics(primaryFont);
String unitText = PRIMARY_TEXT + "   ///   ";
int unitWidth = fm.stringWidth(unitText); // Mengukur panjang teks dalam satuan piksel

// Logika Modulo: Jaga xPos selalu dalam rentang [ -unitWidth, 0 ]
if (xPos <= -unitWidth) {
    xPos += unitWidth; // Reset posisi secara instan tanpa jeda visual
}

// Gambar teks berulang kali dari kiri ke kanan sampai melampaui lebar kanvas
int currentX = xPos;
while (currentX < width) {
    g2d.drawString(unitText, currentX, yPos);
    currentX += unitWidth; // Lanjut ke salinan teks berikutnya
}
```

#### 🔍 Mengapa Mata Manusia Tidak Melihat Titik Sambungan?
1. Kita mengukur lebar satu siklus teks (`unitWidth`) menggunakan `FontMetrics`.
2. Kita merender teks yang sama secara berulang di koordinat `currentX`, `currentX + unitWidth`, `currentX + (2 * unitWidth)`, dan seterusnya hingga memenuhi seluruh lebar jendela.
3. Saat `xPos` mencapai nilai negatif $-(\text{unitWidth})$, posisinya ditambah kembali sebesar `unitWidth` (kembali mendekati $0$). Karena teks kedua berada persis di posisi teks pertama sebelum di-reset, **pergantian posisi terjadi 100% mulus tanpa kedipan atau celah**.

---

### D. Pengaturan Rendering & Styling Grafis

1. **Anti-Aliasing (Grafis Halus):**
   ```java
   g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
   g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
   ```
   Mencegah teks dan garis terlihat pecah atau bergerigi (*pixelated*).

2. **Layered Ambient Glow (Efek Bayangan Cahaya):**
   Teks digambar beberapa kali dengan tingkat transparansi warna putih yang rendah (`Color(255, 255, 255, 30)`) pada offset koordinat $\pm 1\text{ piksel}$, sebelum teks putih solid digambar di atasnya. Ini menghasilkan bayangan bersinar (*glowing aura*).

3. **Gradient Edge Mask:**
   Menggunakan `GradientPaint` di ujung kiri ($0$ sampai $40\text{ px}$) dan ujung kanan kanvas untuk menciptakan gradasi warna dari pekat ke transparan, sehingga teks memudar secara sinematik saat masuk dan keluar jendela.

---

## 5. Cheatsheet Tanya-Jawab untuk Presentasi

* **Tanya:** *“Mengapa menggunakan `javax.swing.Timer` dan bukan `Thread.sleep()` di dalam loop while?”*  
  **Jawab:** “Menggunakan `Thread.sleep()` pada thread utama akan memblokir *Event Dispatch Thread* (EDT) Java, menyebabkan jendela aplikasi membeku (*freeze*), tidak bisa diklik, dan tidak responsif. Sebaliknya, `javax.swing.Timer` berjalan secara asinkron dan secara aman memicu event pada EDT tanpa membebani sistem.”

* **Tanya:** *“Bagaimana antialiasing bekerja pada teks Swing?”*  
  **Jawab:** “Dengan menyetel rendering hint `KEY_TEXT_ANTIALIASING`, Java memanfaatkan teknik sub-pixel rendering kartu grafis untuk memperhalus kontur huruf pada font monospaced berukuran besar, sehingga teks terbaca sangat tajam dan profesional.”


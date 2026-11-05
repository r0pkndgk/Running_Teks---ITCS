# IT Student Pride — Java Swing Desktop Edition

---

**Nama  :** M. Nabil Akmal Qolbi  
**NIM   :** 260605110208  
**Kelas :** G  

---

## 1. Deskripsi Singkat Aplikasi

Program desktop berbasis **Java murni (Java Swing + AWT Graphics2D)** yang menampilkan kalimat **"aku bangga menjadi mahasiswa IT"** dengan desain monokromatik minimalis modern — tanpa dependensi JAR eksternal.

### Fitur Utama
| Fitur | Keterangan |
|---|---|
| **Running Text** | Teks berjalan mulus kanan → kiri via `javax.swing.Timer` (~60 FPS) |
| **Kartu Identitas** | Nama, NIM, Kelas ditampilkan statis & elegan di bagian bawah |
| **Antialiasing** | `Graphics2D` RenderingHints agar teks tajam, tidak bergerigi |
| **Layered Glow** | Bayangan difus berlapis untuk efek cahaya neon monokromatik |
| **Gradient Edge Mask** | `GradientPaint` memudarkan teks di tepi kiri & kanan kanvas |
| **Control Deck** | Tombol interaktif: Play/Pause, Arah, Kecepatan, Glow |

---

## 2. Persyaratan Sistem

- **JDK** versi **11 atau lebih baru** (JDK 11, 17, 21, 25 — semua kompatibel)
- **OS**: Windows / macOS / Linux (apapun yang punya JRE/JDK)
- **Dependensi eksternal**: **Tidak ada** — hanya pustaka standar Java

---

## 3. Cara Compile dan Run

Buka terminal di folder proyek:
```
cd "d:\Vscode folder\ITCS\Runningteks_Java"
```

### Langkah 1 — Compile
```bash
javac RunningTeksIT.java
```
Menghasilkan: `RunningTeksIT.class` dan `RunningTeksIT$MarqueeCanvas.class`

### Langkah 2 — Jalankan
```bash
java RunningTeksIT
```
Jendela aplikasi muncul di tengah layar monitor.

---

## 4. Penjelasan Cara Kerja Kode (Materi Presentasi)

### A. Tiga Komponen Utama

```
JFrame (RunningTeksIT)          ← Jendela utama & tata letak
  ├─ createHeaderPanel()        ← HUD Header
  ├─ MarqueeCanvas (JPanel)     ← Kanvas animasi running text
  ├─ createTerminalPanel()      ← Output teks statis gaya terminal
  ├─ createIdentityPanel()      ← Nama, NIM, Kelas
  └─ createControlDeck()        ← Tombol interaktif
```

---

### B. Logika Timer → Koordinat X → Repaint (Jantung Animasi)

```
Timer berdetak tiap 16 ms (~60 FPS)
        │
        ▼
  xPos -= speed;   ← koordinat X berkurang (bergeser ke kiri)
        │
        ▼
  repaint();        ← Swing menjadwalkan paintComponent()
        │
        ▼
  g2.drawString(unit, xPos, yPos);  ← Teks digambar di posisi baru
```

**Kode Timer:**
```java
timer = new Timer(16, e -> {
    if (!isRunning) return;
    xPos += moveRTL ? -speed : speed;  // -speed = gerak ke kiri
    repaint();                          // minta render ulang
});
```

> **Mengapa Timer, bukan `Thread.sleep()` di loop `while`?**  
> `Thread.sleep()` memblokir *Event Dispatch Thread* (EDT) → jendela **membeku**, tidak bisa diklik.  
> `javax.swing.Timer` berjalan asinkron dan aman di EDT.

---

### C. Logika Seamless Modulo Loop (Tanpa Jeda)

```java
String unit  = PRIMARY_TEXT + "   ///   ";
int    unitW = fm.stringWidth(unit);  // ukur lebar teks dalam piksel

// MODULO RESET: jaga xPos di dalam rentang [-unitW, 0]
if (xPos <= -unitW) xPos += unitW;
if (xPos >  0)      xPos -= unitW;

// Render berulang sampai memenuhi lebar kanvas
int cx = xPos;
while (cx < width) {
    g2.drawString(unit, cx, yPos);
    cx += unitW;
}
```

**Mengapa tidak ada celah?**
1. `FontMetrics` mengukur lebar satu siklus teks dengan presisi piksel.
2. Teks dirender berulang tiap `unitW` piksel — salinan 2 selalu tepat setelah salinan 1.
3. Saat `xPos` reset dari `-unitW` ke `0`, posisi salinan berikutnya **identik** dengan posisi sebelumnya → mata tidak melihat lompatan.

---

### D. Efek Glow Berlapis

```java
// Lapisan 1: bayangan sangat transparan (blur simulasi)
g2.setColor(new Color(255, 255, 255, 28));
g2.drawString(unit, cx-1, yPos-1);
g2.drawString(unit, cx+1, yPos+1);

// Lapisan 2: sedikit lebih terang
g2.setColor(new Color(255, 255, 255, 65));
g2.drawString(unit, cx, yPos+1);

// Lapisan 3: teks putih solid penuh
g2.setColor(Color.WHITE);
g2.drawString(unit, cx, yPos);
```

Teknik "layered overdraw" ini mensimulasikan glow/aura tanpa perlu pustaka grafis tambahan.

---

### E. Gradient Edge Mask (Teks Memudar di Tepi)

```java
// Tepi kiri: dari warna stage (pekat) → transparan
g2.setPaint(new GradientPaint(0, 0, COLOR_STAGE, 42, 0,
    new Color(r, g, b, 0)));
g2.fillRect(0, 0, 42, height);

// Tepi kanan: dari transparan → warna stage (pekat)
g2.setPaint(new GradientPaint(w-42, 0, new Color(r, g, b, 0), w, 0, COLOR_STAGE));
g2.fillRect(w-42, 0, 42, height);
```

Persegi panjang semi-transparan ini "menutupi" tepi teks agar terlihat memudar lembut — efek visual seperti layar studio.

---

## 5. Cheatsheet Jawaban Presentasi

**T: Mengapa `timer = new Timer(16, ...)` dan angkanya 16?**  
J: "16 milidetik ≈ 1/60 detik, yaitu target 60 frame per detik (60 FPS). Ini standar refresh rate monitor modern."

**T: Bagaimana teks bisa looping tanpa jeda?**  
J: "Teknik Continuous Modulo Translation. Kita ukur lebar satu siklus teks dengan `FontMetrics`, lalu reset koordinat xPos secara instan saat mencapai batas. Karena salinan teks berikutnya selalu ada tepat di sebelahnya, titik reset tidak terlihat oleh mata."

**T: Mengapa Swing bukan JavaFX?**  
J: "Java Swing tersedia di semua JDK tanpa dependensi tambahan, sangat ringan, dan cukup untuk program demonstrasi ini. Tidak perlu modul JavaFX yang harus diinstal terpisah di JDK 11+."

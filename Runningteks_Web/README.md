# 🌐 IT Student Pride — Vanilla Web Edition (HTML, CSS, JS)

> **Kalimat Tugas:** `"aku bangga menjadi mahasiswa IT"`  
> **Teknologi:** Pure Vanilla HTML5, Modern CSS3, & JavaScript (Single File: `index.html`)  
> **Konsep Desain:** *Minimalist Monochromatic Neo-Brutalism & Cybernetic Terminal*

---

## 1. Penjelasan Singkat Program

Aplikasi ini adalah program web lintas platform (*cross-platform*) yang sangat ringan dan portabel. Dibuat murni menggunakan **HTML5, CSS3, dan JavaScript (Vanilla)** tanpa memerlukan pustaka (*library*), framework berat, maupun koneksi internet.

Program ini menampilkan kalimat **`"aku bangga menjadi mahasiswa IT"`** dalam format visual yang memukau:
* **Tema Monokromatik Berani (Bold & Solid):** Menggunakan palet kontras tinggi (latar hitam *deep slate*, garis kisi teknikal, dan tipografi putih menyala).
* **Running Text (Marquee Modern):** Animasi teks berjalan mulus dari kanan ke kiri menggunakan **CSS `@keyframes`** murni (menghindari penggunaan tag `<marquee>` jadul yang sudah usang dan tidak standar).
* **Gradient Feathering Mask:** Menggunakan properti modern `mask-image` sehingga teks memudar lembut saat masuk dan keluar di tepi panggung, bukan terpotong kasar.
* **Typewriter Terminal Card:** Animasi mesin tik otomatis dengan kursor balok berkedip (`█`) sebagai variasi tampilan.
* **Interactive Control Deck:** Tombol interaktif untuk Play/Pause, pembalik arah (RTL/LTR), pengatur kecepatan, toggle glow aura, dan mode outline teks.

---

## 2. Cara Menjalankan Program di Desktop & Mobile

Karena seluruh kode (struktur, style, dan script) digabungkan menjadi satu file mandiri bernama `index.html`, program ini dapat langsung dijalankan di semua perangkat:

### A. Menjalankan di Desktop (Laptop / PC Windows, Mac, Linux)
1. Buka File Explorer dan temukan file `index.html` di dalam folder:
   `d:\Vscode folder\ITCS\Runningteks_Web\`
2. **Cukup klik dua kali (double-click)** pada file `index.html`.
3. File akan langsung terbuka di browser default Anda (Google Chrome, Microsoft Edge, Firefox, Safari, atau Opera).
4. *Alternatif:* Drag & drop file `index.html` langsung ke tab browser yang sedang terbuka.

### B. Menjalankan di Mobile (Smartphone Android / iPhone)
* **Cara 1 (Transfer File Langsung):**  
  Kirim file `index.html` ke smartphone Anda (melalui WhatsApp, Google Drive, email, atau kabel data). Buka file tersebut di smartphone dan pilih buka dengan browser (Chrome / Safari).
* **Cara 2 (Akses Jaringan Lokal / Wi-Fi yang Sama):**  
  Jika laptop Anda menjalankan server lokal sederhana (misal Live Server di VS Code, atau `python -m http.server 8000`), Anda cukup membuka browser HP dan mengetikkan alamat IP laptop (contoh: `http://192.168.1.5:8000/index.html`).

Tampilan sudah **100% Responsif** dan otomatis menyesuaikan ukuran font serta tata letak antara layar monitor PC dan layar ponsel vertikal.

---

## 3. Penjelasan Cara Kerja Logika CSS Keyframes (Materi Presentasi 100%)

Bagian ini dirancang agar Anda dapat menjelaskan mekanisme animasi *running text* secara runtut dan mudah dipahami di depan dosen atau audiens.

### A. Mengapa Menghindari Tag `<marquee>` Bawaan HTML?
Tag `<marquee>` adalah tag HTML zaman kuno yang sudah dinyatakan **deprecated** (usang) oleh konsorsium web dunia (W3C). Tag tersebut tidak mendukung akselerasi GPU, gerakannya sering tersendat (*laggy*), dan tidak bisa dikontrol kecepatannya secara presisi.

### B. Logika Animasi: Teknik Double-Buffer Seamless Loop
Kunci utama dari running text yang tidak pernah putus (*seamless*) adalah menduplikasi teks menjadi dua salinan identik di dalam sebuah track horizontal:

```html
<div class="marquee-track">
  <div class="marquee-text">AKU BANGGA MENJADI MAHASISWA IT /// </div>
  <div class="marquee-text">AKU BANGGA MENJADI MAHASISWA IT /// </div>
</div>
```

```
[ Panggung Viewport (Layar) ]
┌──────────────────────────────────────────────┐
│ ... MENJADI MAHASISWA IT /// AKU BANGGA ... │
└──────────────────────────────────────────────┘
  [====== Salinan Teks 1 ======]  [====== Salinan Teks 2 ======]
  |<------------------ 100% Lebar Track --------------------->|
  Offset X bergeser dari 0% ke -50%
```

### C. Penjelasan Baris demi Baris CSS:

```css
/* 1. Track Pembungkus Teks */
.marquee-track {
  display: flex;                /* Menyusun Salinan 1 dan Salinan 2 berdampingan horizontal */
  width: max-content;           /* Lebar track otomatis mengikuti total panjang kedua teks */
  animation: marqueeScroll 14s linear infinite; /* Menjalankan animasi marqueeScroll selama 14 detik, kecepatan konstan, tanpa henti */
  will-change: transform;       /* Menginstruksikan browser untuk memindahkan render ke GPU agar 60 FPS mulus */
}
```

```css
/* 2. Definisi Animasi @keyframes */
@keyframes marqueeScroll {
  0% {
    transform: translateX(0);    /* Posisi awal: Teks berada di koordinat normal (0px) */
  }
  100% {
    transform: translateX(-50%); /* Posisi akhir: Track bergeser ke kiri tepat sebesar 50% dari total lebarnya */
  }
}
```

#### 🔍 Rahasia Ilusi Tanpa Celah (*Zero-Seam Illusion*):
1. Karena track berisi **2 salinan teks yang identik**, maka $50\%$ lebar track sama persis dengan lebar dari **1 salinan teks penuh**.
2. Saat animasi mencapai $100\%$ (`translateX(-50%)`), posisi Salinan Teks 2 berada persis di koordinat awal Salinan Teks 1.
3. Pada milidetik berikutnya, animasi me-reset kembali ke `0%`. Karena tampilan di titik $0\%$ dan $-50\%$ adalah **kembar identik**, mata manusia tidak dapat melihat adanya lompatan atau kedipan (*flicker*).
4. Hasilnya adalah teks yang terus mengalir dari kanan ke kiri secara abadi dan sempurna.

---

### D. Fitur Grafis Tambahan: Gradient Feathering Mask

```css
.marquee-viewport {
  mask-image: linear-gradient(to right, transparent, black 10%, black 90%, transparent);
  -webkit-mask-image: linear-gradient(to right, transparent, black 10%, black 90%, transparent);
}
```
* **Penjelasan:** Properti ini berfungsi seperti klise foto transparan. Pada koordinat $0\%$ (tepi paling kiri) dan $100\%$ (tepi paling kanan), nilainya adalah `transparent` (tembus pandang). Teks yang melintas akan memudar secara bertahap (*feathering fade*), memberikan kesan visual seperti panggung display studio modern.

---

## 4. Rangkuman Poin Jawaban Ujian / Presentasi

* **Tanya:** *“Mengapa menggunakan `transform: translateX()` dan bukan `margin-left` atau `left`?”*  
  **Jawab:** “`transform: translateX()` dieksekusi langsung pada *Compositor Thread* oleh kartu grafis (GPU), sehingga tidak memicu proses *Reflow* atau *Repaint* yang berat pada CPU browser. Ini menjamin animasi tetap stabil di 60 FPS bahkan di smartphone dengan spesifikasi rendah.”

* **Tanya:** *“Bagaimana tombol Pause bekerja tanpa merusak posisi teks?”*  
  **Jawab:** “JavaScript hanya mengubah nilai properti CSS `animation-play-state: paused;`. Ini membekukan animasi tepat di koordinat piksel saat tombol ditekan tanpa me-reset posisi awal.”


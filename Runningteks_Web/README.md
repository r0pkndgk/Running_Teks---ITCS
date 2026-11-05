# IT Student Pride — Vanilla Web Edition

---

**Nama  :** M. Nabil Akmal Qolbi  
**NIM   :** 260605110208  
**Kelas :** G  

---

## 1. Penjelasan Singkat Program

Program web satu halaman (`index.html`) yang menampilkan kalimat **"aku bangga menjadi mahasiswa IT"** dengan desain monokromatik minimalis modern. Dibuat murni menggunakan **HTML5, CSS3, dan JavaScript (Vanilla)** — tanpa framework, tanpa instalasi, tanpa koneksi internet.

### Fitur utama:
| Fitur | Keterangan |
|---|---|
| **Running Text (Marquee)** | Teks berjalan mulus kanan → kiri via CSS `@keyframes`, 60 FPS GPU-accelerated |
| **Gradient Edge Mask** | `mask-image` membuat teks memudar halus di tepi kiri & kanan |
| **Typewriter Terminal** | Efek ketik otomatis + kursor berkedip (`█`) via JavaScript |
| **Kartu Identitas** | Nama, NIM, Kelas ditampilkan statis & elegan di tengah layar |
| **Control Deck** | Tombol interaktif: Play/Pause, Arah, Kecepatan, Glow, Outline |
| **Responsif** | Otomatis menyesuaikan tampilan Desktop maupun Smartphone |

---

## 2. Cara Menjalankan Program

### Desktop (Windows / macOS / Linux)
1. Temukan file `index.html` di folder `Runningteks_Web\`.
2. **Klik dua kali** → browser default akan membukanya langsung.
3. Tidak perlu install apapun. Bekerja 100% *offline*.

### Mobile (Android / iPhone)
- **Cara 1:** Kirim `index.html` ke HP via WhatsApp / Google Drive / kabel, lalu buka dengan browser (Chrome / Safari).
- **Cara 2 (Wi-Fi sama):** Jalankan `python -m http.server 8080` di folder proyek, buka `http://<IP-Laptop>:8080` dari browser HP.

---

## 3. Penjelasan Cara Kerja CSS Keyframes (Materi Presentasi)

### A. Mengapa TIDAK memakai tag `<marquee>`?
Tag `<marquee>` sudah **deprecated** (usang) sejak HTML5. Tidak mendukung GPU acceleration, gerakannya tersendat, dan tidak bisa dikontrol secara presisi. Kita menggunakan CSS `@keyframes` yang jauh lebih modern, mulus, dan efisien.

---

### B. Teknik Double-Buffer Seamless Loop

Kunci animasi tanpa jeda (seamless) adalah menduplikasi teks menjadi **dua salinan identik** dalam satu track:

```html
<div class="marquee-track">          <!-- Track berjalan -->
  <div class="marquee-text">AKU BANGGA MENJADI MAHASISWA IT ///</div>  <!-- Salinan 1 -->
  <div class="marquee-text">AKU BANGGA MENJADI MAHASISWA IT ///</div>  <!-- Salinan 2 -->
</div>
```

```
[ Layar / Viewport ]
┌───────────────────────────────────┐
│  ...MENJADI MAHASISWA IT /// AKU...│
└───────────────────────────────────┘
  [===== Salinan 1 =====][===== Salinan 2 =====]
  |<---------- 100% lebar track ---------->|
  Offset X bergerak dari 0% → -50%
```

---

### C. Penjelasan Baris demi Baris CSS

```css
/* TRACK: Wadah dua salinan teks */
.marquee-track {
  display: flex;                /* Salinan 1 dan 2 berdampingan horizontal */
  width: max-content;           /* Lebar otomatis = total panjang kedua teks */
  animation: marqueeScroll      /* Nama animasi yang dijalankan */
             var(--marquee-speed) /* Durasi: default 14 detik */
             linear              /* Kecepatan konstan, tidak ada percepatan */
             infinite;           /* Berulang tanpa henti selamanya */
  will-change: transform;        /* Instruksi ke browser: render via GPU */
}
```

```css
/* KEYFRAMES: Definisi gerakan dari titik awal ke akhir */
@keyframes marqueeScroll {
  from { transform: translateX(0);    } /* Posisi awal : track di koordinat 0 */
  to   { transform: translateX(-50%); } /* Posisi akhir: track bergeser ke kiri 50% */
}
```

**Mengapa -50% dan bukan -100%?**  
Karena track berisi **2 salinan**, maka `50%` lebar track = panjang **1 salinan penuh**.  
Saat animasi mencapai `-50%`, posisi Salinan 2 berada persis di tempat Salinan 1 semula. Ketika animasi reset ke `0%`, tampilannya **identik** → mata tidak melihat lompatan → loop mulus sempurna.

---

### D. Gradient Feathering Mask (Teks Memudar di Tepi)

```css
.marquee-viewport {
  /* Masker alfa: transparan di tepi kiri & kanan, penuh di tengah */
  mask-image: linear-gradient(
    to right,
    transparent,    /* 0%  — tepi kiri tak terlihat */
    black 10%,      /* 10% — mulai terlihat penuh   */
    black 90%,      /* 90% — masih terlihat penuh   */
    transparent     /* 100%— tepi kanan tak terlihat */
  );
}
```

Teks yang melintas akan memudar lembut di tepi kiri/kanan, bukan terpotong kaku — efek visual seperti layar studio modern.

---

### E. Mengapa `transform: translateX()` dan bukan `margin-left`?

| | `margin-left` / `left` | `transform: translateX()` |
|---|---|---|
| Proses di | CPU (lambat) | GPU (cepat, 60 FPS stabil) |
| Memicu Reflow? | **Ya** (mahal) | **Tidak** |
| Performa mobile | Bisa tersendat | Mulus di HP low-end |

---

### F. Tombol Pause Bekerja Tanpa Reset Posisi

```js
// Hanya mengubah satu properti CSS — posisi teks TIDAK berubah
marqueeTrack.style.animationPlayState = isPlaying ? "running" : "paused";
```

---

## 4. Struktur File

```
Runningteks_Web/
└── index.html   ← satu-satunya file (HTML + CSS + JS digabung)
└── README.md    ← dokumentasi ini
```

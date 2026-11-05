import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * =============================================================================
 * Aplikasi Desktop Running Text Monokromatik - IT Student Pride
 * Bahasa Pemrograman : Java (Java Swing Murni)
 * Konsep Desain      : Minimalis Monokromatik, Bold, Solid, Modern
 * -----------------------------------------------------------------------------
 * Pembuat  : M. Nabil Akmal Qolbi
 * NIM      : 260605110208
 * Kelas    : G
 * =============================================================================
 */
public class RunningTeksIT extends JFrame {

    // ---- KONSTANTA TEKS ----
    private static final String PRIMARY_TEXT   = "AKU BANGGA MENJADI MAHASISWA IT";
    private static final String SECONDARY_TEXT = "// CODE • ARCHITECTURE • INNOVATION • CYBERNETICS //";

    // ---- IDENTITAS PEMBUAT ----
    private static final String NAMA  = "M. Nabil Akmal Qolbi";
    private static final String NIM   = "260605110208";
    private static final String KELAS = "G";

    // ---- PALET WARNA MONOKROMATIK ----
    private static final Color COLOR_BG          = new Color(0x09, 0x09, 0x0B);
    private static final Color COLOR_SURFACE      = new Color(0x12, 0x12, 0x16);
    private static final Color COLOR_STAGE        = new Color(0x0E, 0x0E, 0x12);
    private static final Color COLOR_BORDER       = new Color(0x27, 0x27, 0x2A);
    private static final Color COLOR_BORDER_GLOW  = new Color(0x44, 0x44, 0x52);
    private static final Color COLOR_TEXT_WHITE   = Color.WHITE;
    private static final Color COLOR_TEXT_MUTED   = new Color(0x9E, 0x9E, 0xA8);
    private static final Color COLOR_TEXT_DIM     = new Color(0x52, 0x52, 0x5B);

    // ---- KOMPONEN ----
    private final MarqueeCanvas marqueeCanvas;
    private boolean isPlaying      = true;
    private boolean moveRightToLeft = true;
    private int     speedMultiplier = 1;

    // =========================================================================
    // KONSTRUKTOR — Merakit seluruh tata letak jendela
    // =========================================================================
    public RunningTeksIT() {
        super("IT Student Pride — M. Nabil Akmal Qolbi | " + NIM + " | Kelas " + KELAS);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 560);
        setMinimumSize(new Dimension(680, 450));
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);
        setLayout(new BorderLayout(0, 0));

        // Header HUD
        add(createHeaderPanel(), BorderLayout.NORTH);

        // Area tengah: Panggung Marquee + Terminal Output
        marqueeCanvas = new MarqueeCanvas();
        JPanel centerWrapper = new JPanel(new BorderLayout(0, 10));
        centerWrapper.setOpaque(false);
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(14, 22, 0, 22));
        centerWrapper.add(marqueeCanvas, BorderLayout.CENTER);
        centerWrapper.add(createTerminalPanel(), BorderLayout.SOUTH);
        add(centerWrapper, BorderLayout.CENTER);

        // Area bawah: Identitas + Kontrol + Footer
        JPanel bottomWrapper = new JPanel(new BorderLayout(0, 6));
        bottomWrapper.setOpaque(false);
        bottomWrapper.setBorder(BorderFactory.createEmptyBorder(10, 22, 14, 22));
        bottomWrapper.add(createIdentityPanel(), BorderLayout.NORTH);
        bottomWrapper.add(createControlDeck(),   BorderLayout.CENTER);
        bottomWrapper.add(createFooterPanel(),   BorderLayout.SOUTH);
        add(bottomWrapper, BorderLayout.SOUTH);

        marqueeCanvas.startAnimation();
    }

    // =========================================================================
    // HUD HEADER
    // =========================================================================
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(true);
        header.setBackground(COLOR_SURFACE);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_BORDER),
            BorderFactory.createEmptyBorder(11, 22, 11, 22)
        ));

        JLabel brand = new JLabel("●  IT.PRIDE // JAVA_SWING_CORE");
        brand.setFont(new Font("Monospaced", Font.BOLD, 13));
        brand.setForeground(COLOR_TEXT_WHITE);
        header.add(brand, BorderLayout.WEST);

        JPanel chips = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        chips.setOpaque(false);
        chips.add(createChip("DESKTOP"));
        chips.add(createChip("JAVAX.SWING.TIMER"));
        chips.add(createChip("~60 FPS"));
        header.add(chips, BorderLayout.EAST);

        return header;
    }

    private JLabel createChip(String text) {
        JLabel chip = new JLabel(text);
        chip.setFont(new Font("Monospaced", Font.BOLD, 10));
        chip.setForeground(COLOR_TEXT_MUTED);
        chip.setOpaque(true);
        chip.setBackground(COLOR_BG);
        chip.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDER, 1),
            BorderFactory.createEmptyBorder(3, 8, 3, 8)
        ));
        return chip;
    }

    // =========================================================================
    // TERMINAL OUTPUT PANEL (teks statis mirip terminal)
    // =========================================================================
    private JPanel createTerminalPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 4));
        panel.setOpaque(true);
        panel.setBackground(COLOR_SURFACE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDER, 1),
            BorderFactory.createEmptyBorder(10, 16, 10, 16)
        ));

        JLabel cmd = new JLabel("TERMINAL_LOG: > echo \"$MAHASISWA_STATEMENT\"");
        cmd.setFont(new Font("Monospaced", Font.PLAIN, 11));
        cmd.setForeground(COLOR_TEXT_DIM);

        JLabel out = new JLabel("[STDOUT]: \"aku bangga menjadi mahasiswa IT\"  █");
        out.setFont(new Font("Monospaced", Font.BOLD, 15));
        out.setForeground(COLOR_TEXT_WHITE);

        panel.add(cmd, BorderLayout.NORTH);
        panel.add(out, BorderLayout.SOUTH);
        return panel;
    }

    // =========================================================================
    // KARTU IDENTITAS PEMBUAT (Nama, NIM, Kelas) — STATIS & ELEGAN
    // =========================================================================
    private JPanel createIdentityPanel() {
        JPanel card = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 6));
        card.setOpaque(true);
        card.setBackground(COLOR_SURFACE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDER, 1),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));

        // Blok Nama
        card.add(createIdBlock("NAMA", NAMA));

        // Separator vertikal
        card.add(createSeparator());

        // Blok NIM
        card.add(createIdBlock("NIM", NIM));

        // Separator vertikal
        card.add(createSeparator());

        // Blok Kelas
        card.add(createIdBlock("KELAS", KELAS));

        return card;
    }

    /** Membuat blok label + nilai identitas dalam susunan vertikal */
    private JPanel createIdBlock(String label, String value) {
        JPanel block = new JPanel(new BorderLayout(0, 2));
        block.setOpaque(false);

        JLabel lbl = new JLabel(label, SwingConstants.CENTER);
        lbl.setFont(new Font("Monospaced", Font.PLAIN, 10));
        lbl.setForeground(COLOR_TEXT_DIM);

        JLabel val = new JLabel(value, SwingConstants.CENTER);
        val.setFont(new Font("Monospaced", Font.BOLD, 13));
        val.setForeground(COLOR_TEXT_WHITE);

        block.add(lbl, BorderLayout.NORTH);
        block.add(val, BorderLayout.SOUTH);
        return block;
    }

    /** Pemisah vertikal monokromatik tipis antar blok identitas */
    private JPanel createSeparator() {
        JPanel sep = new JPanel();
        sep.setOpaque(true);
        sep.setBackground(COLOR_BORDER);
        sep.setPreferredSize(new Dimension(1, 30));
        return sep;
    }

    // =========================================================================
    // CONTROL DECK (Tombol interaktif)
    // =========================================================================
    private JPanel createControlDeck() {
        JPanel deck = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 4));
        deck.setOpaque(true);
        deck.setBackground(COLOR_SURFACE);
        deck.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDER, 1),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));

        // Play / Pause
        JButton btnPP = createBtn("PAUSE");
        btnPP.addActionListener(e -> {
            isPlaying = !isPlaying;
            marqueeCanvas.setRunning(isPlaying);
            btnPP.setText(isPlaying ? "PAUSE" : "PLAY");
        });

        // Arah
        JButton btnDir = createBtn("ARAH: KANAN → KIRI");
        btnDir.addActionListener(e -> {
            moveRightToLeft = !moveRightToLeft;
            marqueeCanvas.setDirection(moveRightToLeft);
            btnDir.setText(moveRightToLeft ? "ARAH: KANAN → KIRI" : "ARAH: KIRI → KANAN");
        });

        // Kecepatan
        JButton btnSpd = createBtn("SPD: 1×");
        btnSpd.addActionListener(e -> {
            speedMultiplier = (speedMultiplier == 1) ? 2 : (speedMultiplier == 2) ? 3 : 1;
            marqueeCanvas.setSpeed(speedMultiplier * 2);
            btnSpd.setText("SPD: " + speedMultiplier + "×");
        });

        // Glow
        JButton btnGlow = createBtn("GLOW: ON");
        btnGlow.addActionListener(e -> {
            boolean g = marqueeCanvas.isGlowEnabled();
            marqueeCanvas.setGlowEnabled(!g);
            btnGlow.setText(!g ? "GLOW: ON" : "GLOW: OFF");
        });

        deck.add(btnPP);
        deck.add(btnDir);
        deck.add(btnSpd);
        deck.add(btnGlow);
        return deck;
    }

    private JButton createBtn(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Monospaced", Font.BOLD, 11));
        btn.setForeground(COLOR_TEXT_WHITE);
        btn.setBackground(new Color(0x1A, 0x1A, 0x22));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDER, 1),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // =========================================================================
    // FOOTER
    // =========================================================================
    private JPanel createFooterPanel() {
        JPanel f = new JPanel(new FlowLayout(FlowLayout.CENTER));
        f.setOpaque(false);
        JLabel lbl = new JLabel("JAVA SWING RUNTIME  •  MONOCHROMATIC MINIMALISM  •  KELAS G");
        lbl.setFont(new Font("Monospaced", Font.PLAIN, 10));
        lbl.setForeground(COLOR_TEXT_DIM);
        f.add(lbl);
        return f;
    }

    // =========================================================================
    // MARQUEE CANVAS — Mesin Render Running Text
    // =========================================================================
    private static class MarqueeCanvas extends JPanel {

        /*
         * xPos    : koordinat X piksel posisi teks saat ini (berubah tiap tick)
         * speed   : jumlah piksel yang digeser per 1 tick timer (~16ms)
         * moveRTL : true = kanan ke kiri (xPos berkurang), false = sebaliknya
         */
        private int     xPos        = 0;
        private int     speed       = 2;
        private boolean isRunning   = true;
        private boolean moveRTL     = true;
        private boolean glowEnabled = true;

        private final Font fontPrimary   = new Font("Monospaced", Font.BOLD, 36);
        private final Font fontSecondary = new Font("Monospaced", Font.BOLD, 12);
        private final Timer timer;

        public MarqueeCanvas() {
            setOpaque(true);
            setBackground(COLOR_STAGE);
            setBorder(BorderFactory.createLineBorder(COLOR_BORDER_GLOW, 1));
            setPreferredSize(new Dimension(0, 160));

            /*
             * Timer ~60 FPS (16 ms per tick).
             * Setiap tick:
             *   1. xPos digeser sebesar 'speed' piksel.
             *   2. repaint() dipanggil → Java menjadwalkan paintComponent().
             */
            timer = new Timer(16, (ActionListener) e -> {
                if (!isRunning) return;
                xPos += moveRTL ? -speed : speed;
                repaint();
            });
        }

        public void startAnimation()          { timer.start(); }
        public void setRunning(boolean r)     { this.isRunning = r; }
        public void setDirection(boolean rtl) { this.moveRTL = rtl; }
        public void setSpeed(int px)          { this.speed = px; }
        public void setGlowEnabled(boolean g) { this.glowEnabled = g; repaint(); }
        public boolean isGlowEnabled()        { return glowEnabled; }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            // --- Aktifkan Antialiasing ---
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING,         RenderingHints.VALUE_RENDER_QUALITY);

            int w = getWidth(), h = getHeight();

            // --- Kisi-kisi latar ---
            g2.setColor(new Color(0x14, 0x14, 0x1B));
            for (int x = 0; x < w; x += 32) g2.drawLine(x, 0, x, h);
            for (int y = 0; y < h; y += 32) g2.drawLine(0, y, w, y);

            // --- Reticle sudut (+) ---
            g2.setColor(COLOR_TEXT_DIM);
            g2.setFont(new Font("Monospaced", Font.PLAIN, 13));
            g2.drawString("+", 10, 18);
            g2.drawString("+", w - 20, 18);
            g2.drawString("+", 10, h - 8);
            g2.drawString("+", w - 20, h - 8);

            // --- Counter teks atas ---
            g2.setFont(fontSecondary);
            g2.setColor(COLOR_TEXT_DIM);
            g2.drawString(SECONDARY_TEXT, 30, 22);

            // --- RUNNING TEXT UTAMA ---
            g2.setFont(fontPrimary);
            FontMetrics fm = g2.getFontMetrics();
            String unit  = PRIMARY_TEXT + "   ///   ";
            int unitW    = fm.stringWidth(unit);

            /*
             * LOGIKA SEAMLESS MODULO LOOP:
             * Ketika xPos melebihi -unitW, tambahkan kembali unitW.
             * Karena teks dirender berulang tiap unitW piksel, titik
             * reset tidak terlihat oleh mata → loop tanpa celah.
             */
            if (unitW > 0) {
                if (xPos <= -unitW) xPos += unitW;
                if (xPos > 0)       xPos -= unitW;
            }

            int yPos = h / 2 + fm.getAscent() / 2 - 4;
            int cx   = xPos;

            while (cx < w) {
                // Lapisan glow (bayangan difus berlapis)
                if (glowEnabled) {
                    g2.setColor(new Color(255, 255, 255, 28));
                    g2.drawString(unit, cx - 1, yPos - 1);
                    g2.drawString(unit, cx + 1, yPos + 1);
                    g2.setColor(new Color(255, 255, 255, 65));
                    g2.drawString(unit, cx, yPos + 1);
                }
                // Teks putih solid
                g2.setColor(COLOR_TEXT_WHITE);
                g2.drawString(unit, cx, yPos);
                cx += unitW;
            }

            // --- Gradient Mask tepi kiri & kanan ---
            g2.setPaint(new GradientPaint(0, 0, COLOR_STAGE, 42, 0,
                new Color(COLOR_STAGE.getRed(), COLOR_STAGE.getGreen(), COLOR_STAGE.getBlue(), 0)));
            g2.fillRect(0, 0, 42, h);

            g2.setPaint(new GradientPaint(w - 42, 0,
                new Color(COLOR_STAGE.getRed(), COLOR_STAGE.getGreen(), COLOR_STAGE.getBlue(), 0), w, 0, COLOR_STAGE));
            g2.fillRect(w - 42, 0, 42, h);
        }
    }

    // =========================================================================
    // ENTRY POINT
    // =========================================================================
    public static void main(String[] args) {
        // Selalu buat GUI di Event Dispatch Thread (EDT) — standar Java Swing
        SwingUtilities.invokeLater(() -> new RunningTeksIT().setVisible(true));
    }
}

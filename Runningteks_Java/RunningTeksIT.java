import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * =============================================================================
 * Aplikasi Desktop Running Text Monokromatik - IT Student Pride
 * Bahasa Pemrograman: Java (Java Swing Murni)
 * Konsep Desain: Minimalis Monokromatik, Bold, Solid, dan Modern
 * =============================================================================
 */
public class RunningTeksIT extends JFrame {

    // Konstanta Kalimat Tugas
    private static final String PRIMARY_TEXT = "AKU BANGGA MENJADI MAHASISWA IT";
    private static final String SECONDARY_TEXT = "// CODE • ARCHITECTURE • INNOVATION • CYBERNETICS //";

    // Palet Warna Monokromatik
    private static final Color COLOR_BG = new Color(0x09, 0x09, 0x0B);        // Deep Slate Black
    private static final Color COLOR_SURFACE = new Color(0x12, 0x12, 0x16);   // Dark Card Surface
    private static final Color COLOR_STAGE = new Color(0x0E, 0x0E, 0x12);     // Stage Canvas
    private static final Color COLOR_BORDER = new Color(0x27, 0x27, 0x2A);    // Border Monokrom
    private static final Color COLOR_BORDER_GLOW = new Color(0x44, 0x44, 0x52);
    private static final Color COLOR_TEXT_WHITE = Color.WHITE;
    private static final Color COLOR_TEXT_MUTED = new Color(0x9E, 0x9E, 0xA8);
    private static final Color COLOR_TEXT_DIM = new Color(0x52, 0x52, 0x5B);

    // Komponen Panggung Animasi
    private final MarqueeCanvas marqueeCanvas;
    private boolean isPlaying = true;
    private boolean moveRightToLeft = true;
    private int speedMultiplier = 1; // 1x, 2x, 3x

    public RunningTeksIT() {
        super("IT Student Pride — Monochromatic Running Text (Java Swing)");

        // 1. Konfigurasi Dasar Jendela (JFrame)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 520);
        setMinimumSize(new Dimension(680, 420));
        setLocationRelativeTo(null); // Posisikan di tengah layar monitor
        getContentPane().setBackground(COLOR_BG);
        setLayout(new BorderLayout(0, 16));

        // 2. Technical HUD Header
        add(createHeaderPanel(), BorderLayout.NORTH);

        // 3. Area Tengah: Panggung Utama Animasi Running Text
        marqueeCanvas = new MarqueeCanvas();
        JPanel centerWrapper = new JPanel(new BorderLayout(0, 12));
        centerWrapper.setOpaque(false);
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(0, 24, 0, 24));
        centerWrapper.add(marqueeCanvas, BorderLayout.CENTER);
        centerWrapper.add(createTypewriterStatusPanel(), BorderLayout.SOUTH);
        add(centerWrapper, BorderLayout.CENTER);

        // 4. Bagian Bawah: Deck Tombol Kontrol Interaktif & Footer
        JPanel bottomWrapper = new JPanel(new BorderLayout(0, 8));
        bottomWrapper.setOpaque(false);
        bottomWrapper.setBorder(BorderFactory.createEmptyBorder(0, 24, 16, 24));
        bottomWrapper.add(createControlDeck(), BorderLayout.CENTER);
        bottomWrapper.add(createFooterPanel(), BorderLayout.SOUTH);
        add(bottomWrapper, BorderLayout.SOUTH);

        // Mulai animasi
        marqueeCanvas.startAnimation();
    }

    /**
     * Membuat Header bergaya Technical HUD terminal
     */
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(true);
        header.setBackground(COLOR_SURFACE);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_BORDER),
                BorderFactory.createEmptyBorder(12, 24, 12, 24)
        ));

        // Brand Title
        JLabel brandLabel = new JLabel("●  IT.PRIDE // JAVA_SWING_CORE");
        brandLabel.setFont(new Font("Monospaced", Font.BOLD, 13));
        brandLabel.setForeground(COLOR_TEXT_WHITE);
        header.add(brandLabel, BorderLayout.WEST);

        // Tech Chips
        JPanel chipsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        chipsPanel.setOpaque(false);
        chipsPanel.add(createChipLabel("TARGET: DESKTOP"));
        chipsPanel.add(createChipLabel("ENGINE: JAVAX.SWING.TIMER"));
        chipsPanel.add(createChipLabel("FPS: ~60"));
        header.add(chipsPanel, BorderLayout.EAST);

        return header;
    }

    private JLabel createChipLabel(String text) {
        JLabel chip = new JLabel(text);
        chip.setFont(new Font("Monospaced", Font.BOLD, 10));
        chip.setForeground(COLOR_TEXT_MUTED);
        chip.setOpaque(true);
        chip.setBackground(COLOR_BG);
        chip.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER, 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        return chip;
    }

    /**
     * Panel Status Konsol Bergaya Terminal
     */
    private JPanel createTypewriterStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 4));
        panel.setOpaque(true);
        panel.setBackground(COLOR_SURFACE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER, 1),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));

        JLabel title = new JLabel("TERMINAL_LOG: > echo \"$MAHASISWA_STATEMENT\"");
        title.setFont(new Font("Monospaced", Font.PLAIN, 11));
        title.setForeground(COLOR_TEXT_DIM);

        JLabel output = new JLabel("[STDOUT]: \"aku bangga menjadi mahasiswa IT\"  █");
        output.setFont(new Font("Monospaced", Font.BOLD, 15));
        output.setForeground(COLOR_TEXT_WHITE);

        panel.add(title, BorderLayout.NORTH);
        panel.add(output, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Deck Tombol Pengaturan Interaktif
     */
    private JPanel createControlDeck() {
        JPanel deck = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 4));
        deck.setOpaque(true);
        deck.setBackground(COLOR_SURFACE);
        deck.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        // Tombol Play / Pause
        JButton btnPlayPause = createStyledButton("PAUSE");
        btnPlayPause.addActionListener(e -> {
            isPlaying = !isPlaying;
            marqueeCanvas.setRunning(isPlaying);
            btnPlayPause.setText(isPlaying ? "PAUSE" : "PLAY");
        });

        // Tombol Balik Arah
        JButton btnDirection = createStyledButton("ARAH: KANAN → KIRI");
        btnDirection.addActionListener(e -> {
            moveRightToLeft = !moveRightToLeft;
            marqueeCanvas.setDirection(moveRightToLeft);
            btnDirection.setText(moveRightToLeft ? "ARAH: KANAN → KIRI" : "ARAH: KIRI → KANAN");
        });

        // Tombol Kecepatan
        JButton btnSpeed = createStyledButton("SPD: 1X");
        btnSpeed.addActionListener(e -> {
            if (speedMultiplier == 1) {
                speedMultiplier = 2;
            } else if (speedMultiplier == 2) {
                speedMultiplier = 3;
            } else {
                speedMultiplier = 1;
            }
            marqueeCanvas.setSpeed(speedMultiplier * 2);
            btnSpeed.setText("SPD: " + speedMultiplier + "X");
        });

        // Tombol Toggle Glow
        JButton btnGlow = createStyledButton("GLOW: ON");
        btnGlow.addActionListener(e -> {
            boolean current = marqueeCanvas.isGlowEnabled();
            marqueeCanvas.setGlowEnabled(!current);
            btnGlow.setText(!current ? "GLOW: ON" : "GLOW: OFF");
        });

        deck.add(btnPlayPause);
        deck.add(btnDirection);
        deck.add(btnSpeed);
        deck.add(btnGlow);

        return deck;
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Monospaced", Font.BOLD, 11));
        btn.setForeground(COLOR_TEXT_WHITE);
        btn.setBackground(new Color(0x1A, 0x1A, 0x22));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER, 1),
                BorderFactory.createEmptyBorder(6, 14, 6, 14)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JPanel createFooterPanel() {
        JPanel footer = new JPanel();
        footer.setOpaque(false);
        JLabel label = new JLabel("JAVA DESKTOP RUNTIME // JAVAX.SWING // MONOCHROMATIC MINIMALISM");
        label.setFont(new Font("Monospaced", Font.PLAIN, 10));
        label.setForeground(COLOR_TEXT_DIM);
        footer.add(label);
        return footer;
    }

    // =========================================================================
    // KANVAS MARQUEE: MESIN RENDER RUNNING TEXT SWING
    // =========================================================================
    private static class MarqueeCanvas extends JPanel {

        private int xPos = 0;              // Koordinat X pergeseran teks
        private int speed = 2;             // Piksel yang digeser per tick
        private boolean isRunning = true;
        private boolean moveRTL = true;    // Kanan ke Kiri (Right to Left)
        private boolean glowEnabled = true;

        private final Font primaryFont = new Font("Monospaced", Font.BOLD, 36);
        private final Font secondaryFont = new Font("Monospaced", Font.BOLD, 12);
        private final Timer animationTimer;

        public MarqueeCanvas() {
            setOpaque(true);
            setBackground(COLOR_STAGE);
            setBorder(BorderFactory.createLineBorder(COLOR_BORDER_GLOW, 1));

            // Inisialisasi Timer animasi (~60 FPS -> 16 milidetik per frame)
            ActionListener tickAction = e -> {
                if (!isRunning) return;

                if (moveRTL) {
                    xPos -= speed;
                } else {
                    xPos += speed;
                }

                // Meminta Swing untuk merender ulang panel (memanggil paintComponent)
                repaint();
            };

            animationTimer = new Timer(16, tickAction);
        }

        public void startAnimation() {
            animationTimer.start();
        }

        public void setRunning(boolean running) {
            this.isRunning = running;
        }

        public void setDirection(boolean rightToLeft) {
            this.moveRTL = rightToLeft;
        }

        public void setSpeed(int pxPerTick) {
            this.speed = pxPerTick;
        }

        public void setGlowEnabled(boolean glow) {
            this.glowEnabled = glow;
            repaint();
        }

        public boolean isGlowEnabled() {
            return glowEnabled;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // 1. Aktifkan Antialiasing untuk Teks dan Grafis Halus
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            int width = getWidth();
            int height = getHeight();

            // 2. Gambar Kisi-kisi Vektor Minimalis di Latar Panggung
            g2d.setColor(new Color(0x14, 0x14, 0x1B));
            for (int x = 0; x < width; x += 32) {
                g2d.drawLine(x, 0, x, height);
            }
            for (int y = 0; y < height; y += 32) {
                g2d.drawLine(0, y, width, y);
            }

            // 3. Gambar Reticle Sudut (+) ala HUD
            g2d.setColor(COLOR_TEXT_DIM);
            g2d.setFont(new Font("Monospaced", Font.PLAIN, 14));
            g2d.drawString("+", 10, 20);
            g2d.drawString("+", width - 20, 20);
            g2d.drawString("+", 10, height - 10);
            g2d.drawString("+", width - 20, height - 10);

            // 4. Gambar Ambient Counter Teks di bagian atas
            g2d.setFont(secondaryFont);
            g2d.setColor(COLOR_TEXT_DIM);
            g2d.drawString(SECONDARY_TEXT, 30, 24);

            // 5. Gambar Running Text Utama (Marquee)
            g2d.setFont(primaryFont);
            FontMetrics fm = g2d.getFontMetrics(primaryFont);
            String unitText = PRIMARY_TEXT + "   ///   ";
            int unitWidth = fm.stringWidth(unitText);

            // LOGIKA SEAMLESS MODULO LOOPING:
            // Jaga nilai xPos selalu berada dalam rentang [ -unitWidth, 0 ]
            if (unitWidth > 0) {
                if (xPos <= -unitWidth) {
                    xPos += unitWidth;
                } else if (xPos > 0) {
                    xPos -= unitWidth;
                }
            }

            // Hitung koordinat Y agar teks berada persis di tengah panggung
            int yPos = (height / 2) + (fm.getAscent() / 2) - 4;

            // Render duplikasi teks secara beruntun dari kiri hingga melampaui lebar panggung
            int currentX = xPos;
            while (currentX < width) {
                // A. Efek Layered Drop Shadow / Glow Halus
                if (glowEnabled) {
                    g2d.setColor(new Color(255, 255, 255, 30));
                    g2d.drawString(unitText, currentX - 1, yPos - 1);
                    g2d.drawString(unitText, currentX + 1, yPos + 1);
                    g2d.drawString(unitText, currentX, yPos + 2);

                    g2d.setColor(new Color(255, 255, 255, 70));
                    g2d.drawString(unitText, currentX, yPos + 1);
                }

                // B. Teks Utama Solid Putih Bersih
                g2d.setColor(COLOR_TEXT_WHITE);
                g2d.drawString(unitText, currentX, yPos);

                currentX += unitWidth;
            }

            // 6. Efek Vignette / Gradient Mask Halus di Sisi Kiri & Kanan
            // Memudarkan teks saat mendekati tepi kanvas
            Paint leftGradient = new GradientPaint(0, 0, COLOR_STAGE, 40, 0, new Color(COLOR_STAGE.getRed(), COLOR_STAGE.getGreen(), COLOR_STAGE.getBlue(), 0));
            g2d.setPaint(leftGradient);
            g2d.fillRect(0, 0, 40, height);

            Paint rightGradient = new GradientPaint(width - 40, 0, new Color(COLOR_STAGE.getRed(), COLOR_STAGE.getGreen(), COLOR_STAGE.getBlue(), 0), width, 0, COLOR_STAGE);
            g2d.setPaint(rightGradient);
            g2d.fillRect(width - 40, 0, 40, height);
        }
    }

    /**
     * Entry Point Utama Aplikasi
     */
    public static void main(String[] args) {
        // Jalankan GUI pada Event Dispatch Thread (EDT) sesuai standar Java Swing
        SwingUtilities.invokeLater(() -> {
            RunningTeksIT app = new RunningTeksIT();
            app.setVisible(true);
        });
    }
}

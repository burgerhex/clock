import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.time.LocalDateTime;

public class ClockPanel extends JPanel {

    private static final int SIZE = 500;
    private static final int PAD = 5;

    private static final int RADIUS = SIZE / 2 - PAD;

    private static final boolean SHOW_CIRCLE      = true;
    private static final boolean SHOW_TICKS       = true;
    private static final boolean SHOW_MINOR_TICKS = true;
    private static final boolean SHOW_HOURS       = true;
    private static final boolean SHOW_MINUTES     = true;
    private static final boolean SHOW_SECONDS     = true;

    private static final int       TICKS = 12;
    private static final int MINOR_TICKS = 60;

    private static final int       TICKS_LENGTH = 20;
    private static final int MINOR_TICKS_LENGTH = 10;
    private static final int       HOURS_LENGTH = 75;
    private static final int     MINUTES_LENGTH = 150;
    private static final int     SECONDS_LENGTH = 200;

    private static final Color       HOURS_COLOR = Color.DARK_GRAY;
    private static final Color     MINUTES_COLOR = Color.DARK_GRAY;
    private static final Color     SECONDS_COLOR = Color.RED;
    private static final Color      CIRCLE_COLOR = Color.BLACK;
    private static final Color       TICKS_COLOR = Color.BLACK;
    private static final Color MINOR_TICKS_COLOR = Color.BLACK;
    private static final Color  CLOCK_FACE_COLOR = Color.decode("#eeeeee");
    private static final Color          BG_COLOR = Color.decode("#dddddd");

    private static final int    HOURS_PER_CYCLE = 12;
    private static final int   MINUTES_PER_HOUR = 60;
    private static final int SECONDS_PER_MINUTE = 60;

    private LocalDateTime now;

    public ClockPanel() {
        this(SIZE);
    }

    public ClockPanel(int size) {
        this.setPreferredSize(new Dimension(size, size));
        this.now = LocalDateTime.now();
    }

    public void tick() throws InterruptedException {
        tick(1);
    }

    public void tick(int millis) throws InterruptedException {
        Thread.sleep(millis);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        updateTime();

        drawBG(g);

        drawCircle(g);

        drawMinorTicks(g);

        drawTicks(g);

        drawHands(g);
    }

    private void updateTime() {
        now = LocalDateTime.now();
    }

    private void drawBG(Graphics g) {
        Color before = g.getColor();

        g.setColor(BG_COLOR);
        g.fillRect(0, 0, SIZE, SIZE);

        g.setColor(before);
    }

    private void drawCircle(Graphics g) {
        if (!SHOW_CIRCLE) return;

        Color before = g.getColor();

        g.setColor(CLOCK_FACE_COLOR);
        g.fillOval(PAD, PAD, SIZE - 2 * PAD, SIZE - 2 * PAD);
        g.setColor(CIRCLE_COLOR);
        g.drawOval(PAD, PAD, SIZE - 2 * PAD, SIZE - 2 * PAD);

        g.setColor(before);
    }

    private void drawTicks(Graphics g) {
        if (!SHOW_TICKS) return;

        Color before = g.getColor();

        g.setColor(TICKS_COLOR);
        // draw lines from center

        for (int i = 0; i < TICKS; i++) {

            // trig calculations, if you draw the frame out they're easy to find out
            double angle = 2 * Math.PI / TICKS * i;

            int x = (int) Math.round(SIZE / 2.0 + RADIUS * Math.sin(angle));
            int y = (int) Math.round(PAD + RADIUS * (1 - Math.cos(angle)));

            g.drawLine(SIZE / 2, SIZE / 2, x, y);
        }

        // hide middle part of lines
        g.setColor(CLOCK_FACE_COLOR);
        int hidingCoord = PAD + TICKS_LENGTH;
        int hidingSize  = 2 * (RADIUS - TICKS_LENGTH);
        g.fillOval(hidingCoord, hidingCoord, hidingSize, hidingSize);

        g.setColor(before);
    }

    private void drawMinorTicks(Graphics g) {
        if (!SHOW_MINOR_TICKS) return;

        Color before = g.getColor();

        g.setColor(MINOR_TICKS_COLOR);
        // draw lines from center

        for (int i = 0; i < MINOR_TICKS; i++) {

            // trig calculations, if you draw the frame out they're easy to find out
            double angle = 2 * Math.PI / MINOR_TICKS * i;

            int x = (int) Math.round(SIZE / 2.0 + RADIUS * Math.sin(angle));
            int y = (int) Math.round(PAD + RADIUS * (1 - Math.cos(angle)));

            g.drawLine(SIZE / 2, SIZE / 2, x, y);
        }

        // hide middle part of lines
        g.setColor(CLOCK_FACE_COLOR);
        int hidingCoord = PAD + MINOR_TICKS_LENGTH;
        int hidingSize  = 2 * (RADIUS - MINOR_TICKS_LENGTH);
        g.fillOval(hidingCoord, hidingCoord, hidingSize, hidingSize);

        g.setColor(before);
    }

    private void drawHands(Graphics g) {
        drawHours(g);
        drawMinutes(g);
        drawSeconds(g);
    }

    private void drawHours(Graphics g) {
        if (!SHOW_HOURS) return;

        Color before = g.getColor();

        g.setColor(HOURS_COLOR);

        double angle = 2 * Math.PI * getHours() / HOURS_PER_CYCLE;

        int x = (int) Math.round(SIZE / 2.0 + HOURS_LENGTH * Math.sin(angle));
        int y = (int) Math.round(PAD + RADIUS - HOURS_LENGTH * Math.cos(angle));

        g.drawLine(SIZE / 2, SIZE / 2, x, y);

        g.setColor(before);
    }

    private void drawMinutes(Graphics g) {
        if (!SHOW_MINUTES) return;

        Color before = g.getColor();

        g.setColor(MINUTES_COLOR);

        double angle = 2 * Math.PI * getMinutes() / MINUTES_PER_HOUR;

        int x = (int) Math.round(SIZE / 2.0 + MINUTES_LENGTH * Math.sin(angle));
        int y = (int) Math.round(PAD + RADIUS - MINUTES_LENGTH * Math.cos(angle));

        g.drawLine(SIZE / 2, SIZE / 2, x, y);

        g.setColor(before);
    }

    private void drawSeconds(Graphics g) {
        if (!SHOW_SECONDS) return;

        Color before = g.getColor();

        g.setColor(SECONDS_COLOR);

        double angle = 2 * Math.PI * getSeconds() / SECONDS_PER_MINUTE;

        int x = (int) Math.round(SIZE / 2.0 + SECONDS_LENGTH * Math.sin(angle));
        int y = (int) Math.round(PAD + RADIUS - SECONDS_LENGTH * Math.cos(angle));

        g.drawLine(SIZE / 2, SIZE / 2, x, y);

        g.setColor(before);
    }

    private double getHours() {
        int hours = now.getHour();
        double minutes_part = getMinutes() / MINUTES_PER_HOUR;
        return hours + minutes_part;
    }

    private double getMinutes() {
        int minutes = now.getMinute();
        double seconds_part = getSeconds() / SECONDS_PER_MINUTE;
        return minutes + seconds_part;
    }

    private double getSeconds() {
        int seconds = now.getSecond();
        double nanos_part = now.getNano() / Math.pow(10, 9);
        return seconds + nanos_part;
    }
}

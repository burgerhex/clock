import javax.swing.SwingUtilities;
import javax.swing.JFrame;

public class Main {

    private static final JFrame frame = new JFrame();
    private static final ClockPanel clock = new ClockPanel();

    public static void main(String[] args) throws InterruptedException {

        SwingUtilities.invokeLater(Main::createAndShowGUI);

        while (true) {
            clock.tick();
        }
    }

    private static void createAndShowGUI() {
        frame.setTitle("Clock");
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(clock);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}

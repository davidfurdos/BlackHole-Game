package blackhole;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class Launcher extends JFrame {

    public Launcher() {
        setTitle("Black Hole: Escape");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                showExitConfirmation();
            }
        });
        URL url = Window.class.getResource("blackhole.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(url));
    }

    private void showExitConfirmation() {
        int exit = JOptionPane.showConfirmDialog(this, "Are you sure, you want to quit?",
                "Exit", JOptionPane.YES_NO_OPTION);
        if (exit == JOptionPane.YES_OPTION) {
            doUponExit();
        }
    }

    protected void doUponExit() {
        dispose();
    }
}

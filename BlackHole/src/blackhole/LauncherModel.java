package blackhole;

import java.awt.BorderLayout;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class LauncherModel extends Launcher {

    private final List<Window> gameWindows;

    public LauncherModel() {
        gameWindows = new ArrayList<>();
        setSize(300, 200);
        setLocationRelativeTo(null);
        JPanel top = new JPanel();
        JPanel center = new JPanel();
        JLabel label = new JLabel();
        label.setText("Choose table size");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        JButton small = new JButton("5 x 5");
        small.addActionListener(getBoardSize(5));
        JButton medium = new JButton("7 x 7");
        medium.addActionListener(getBoardSize(7));
        JButton large = new JButton("9 x 9");
        large.addActionListener(getBoardSize(9));
        top.add(label, BorderLayout.CENTER);
        center.add(small);
        center.add(medium);
        center.add(large);
        BorderLayout border = new BorderLayout();
        border.setVgap(30);
        getContentPane().setLayout(border);
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(center, BorderLayout.CENTER);
    }

    private ActionListener getBoardSize(final int size) {
        return (ActionEvent e) -> {
            Window window = new Window(size, LauncherModel.this);
            window.setVisible(true);
            switch (size) {
                case 5 -> {
                    window.setSize(500, 400);
                    window.setLocationRelativeTo(null);
                }
                case 7 -> {
                    window.setSize(700,500);
                    window.setLocationRelativeTo(null);
                }
                default -> {
                    window.setSize(900,600);
                    window.setLocationRelativeTo(null);
                }
            }
            gameWindows.add(window);
            dispose();
        };
    }

    public List<Window> getWindowList() {
        return gameWindows;
    }
    
    @Override
    protected void doUponExit() {
        System.exit(0);
    }
}

package view;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class TableChooser extends JFrame {

    public TableChooser() {
        setTitle("Black Hole: Escape");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(TableChooser.this,
                        "Are you sure, you want to quit?", "Exit",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) dispose();
            }
        });
        setIconImage(Toolkit.getDefaultToolkit().getImage("res/blackHole.png"));
        setResizable(false);
    }
}

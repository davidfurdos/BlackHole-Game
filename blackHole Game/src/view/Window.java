package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window extends JFrame {
    private final int size;
    private final int blackHole;
    private int redShipID;
    private int blueShipID;
    private String text;
    private boolean hasYellow;
    private int x;
    private int y;
    private final JButton[][] boardButtons;
    private final GameLogic gamelogic;
    private final LauncherModel mainWindow;
    private JLabel gameStatus;
    private JLabel currentPlayer;
    private JLabel shipsRed;
    private JLabel shipsBlue;
    private JLabel separator;
    private JLabel shipsLeft;

    public Window(int size, LauncherModel mainWindow) {
        this.size = size;
        blackHole = size / 2;
        redShipID = 1;
        blueShipID = 1;
        hasYellow = false;
        x = size;
        y = size;
        text = null;
        boardButtons = new JButton[size][size];
        this.mainWindow = mainWindow;
        mainWindow.getWindowList().add(this);
        gamelogic = new GameLogic(size);
        createMenu();
        createUI();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage("res/blackHole.png"));
        setVisible(true);
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("Game");

        JMenuItem newGame = new JMenuItem(new AbstractAction("New Game") {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });

        JMenuItem change = new JMenuItem(new AbstractAction("Change board size") {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                mainWindow.setVisible(true);
            }
        });

        JMenuItem menuExit = new JMenuItem(new AbstractAction("Exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                showExitConfirmation();
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                showExitConfirmation();
            }
        });
        menuFile.add(newGame);
        menuFile.addSeparator();
        menuFile.add(change);
        menuFile.addSeparator();
        menuFile.add(menuExit);
        menuBar.add(menuFile);
        setJMenuBar(menuBar);
    }

    private void createUI() {
        JPanel top = new JPanel();
        gameStatus = new JLabel();
        currentPlayer = new JLabel();
        shipsLeft = new JLabel();
        shipsRed = new JLabel();
        separator = new JLabel();
        shipsBlue = new JLabel();
        top.add(gameStatus);
        top.add(currentPlayer);
        currentPlayer.setPreferredSize(new Dimension(switch (size) {
            case 5 -> 200;
            case 7 -> 400;
            case 9 -> 600;
            default -> throw new IllegalStateException("Unexpected value: " + size);
        }, 15));
        top.add(shipsLeft);
        top.add(shipsRed);
        top.add(separator);
        top.add(shipsBlue);
        updateGameStatus();

        JPanel bottom = new JPanel();
        JLabel label = new JLabel("Move ship to direction:");
        bottom.add(label);

        for (Directions direction : Directions.values()) {
            JButton button = new JButton(direction.toString());
            button.addActionListener(directionActionListener(direction));
            bottom.add(button);
        }

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(size, size));
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                addButton(mainPanel, i, j);
            }
        }

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.PAGE_START);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        getContentPane().add(bottom, BorderLayout.AFTER_LAST_LINE);
    }

    private void addButton(JPanel panel, final int i, final int j) {
        boardButtons[i][j] = new JButton();
        boardButtons[i][j].addActionListener((ActionEvent e) -> {
            if (!hasYellow && gamelogic.getTable(i, j).equals(gamelogic.getActualPlayer())) {
                boardButtons[i][j].setBackground(Color.YELLOW);
                hasYellow = !hasYellow;
                x = i;
                y = j;
                text = boardButtons[i][j].getText();
            } else if (hasYellow && gamelogic.getTable(i, j).equals(gamelogic.getActualPlayer())) {
                for (int k = 0; k < size; k++) {
                    for (int l = 0; l < size; l++) {
                        if (boardButtons[k][l].getBackground().equals(Color.YELLOW)) {
                            boardButtons[k][l].setBackground((gamelogic.getTable(i, j).equals(Board.RED)) ? Color.RED : Color.BLUE);
                        }
                    }
                    hasYellow = !hasYellow;
                }
                boardButtons[i][j].setBackground(Color.YELLOW);
                hasYellow = !hasYellow;
                x = i;
                y = j;
                text = boardButtons[i][j].getText();
            }
        });
        if (i == blackHole && j == blackHole) {
            boardButtons[i][j].setBackground(Color.BLACK);
        } else if (gamelogic.getTable(i, j).equals(Board.RED)) {
            boardButtons[i][j].setBackground(Color.RED);
            boardButtons[i][j].setText(Integer.toString(redShipID));
            redShipID++;
        } else if (gamelogic.getTable(i, j).equals(Board.BLUE)) {
            boardButtons[i][j].setBackground(Color.BLUE);
            boardButtons[i][j].setText(Integer.toString(blueShipID));
            blueShipID++;

        } else {
            boardButtons[i][j].setBackground(Color.WHITE);
        }
        panel.add(boardButtons[i][j]);
    }

    private ActionListener directionActionListener(Directions direction) {
        return (ActionEvent e) -> {
            if (hasYellow && x != size && y != size) {
                int[] result;
                result = gamelogic.move(x, y, direction);
                int cx = result[0];
                int cy = result[1];
                hasYellow = !hasYellow;
                boardButtons[x][y].setBackground(Color.WHITE);
                boardButtons[x][y].setText(null);
                if (gamelogic.getTable(result[0], result[1]) != Board.BLACKHOLE) {
                    boardButtons[cx][cy].setBackground((gamelogic.getActualPlayer().equals(Board.RED)) ? Color.RED : Color.BLUE);
                    boardButtons[cx][cy].setText(text);
                }
                if (gamelogic.getActualPlayer().equals(Board.RED)) {
                    gamelogic.setActualPlayer(Board.BLUE);
                } else {
                    gamelogic.setActualPlayer(Board.RED);
                }
                updateGameStatus();
                if (gamelogic.getBlueShips() == (size - 1) / 2) {
                    showGameOverMessage(Board.BLUE);
                } else if (gamelogic.getRedShips() == (size - 1) / 2) {
                    showGameOverMessage(Board.RED);
                }
                x = size;
                y = size;
                text = null;
            }
        };
    }

    private void showExitConfirmation() {
        if (JOptionPane.showConfirmDialog(this, "Are you sure, you want to quit?",
                "Exit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            mainWindow.getWindowList().remove(this);
            System.exit(0);
        }
    }

    public void showGameOverMessage(Board winner) {
        JOptionPane.showMessageDialog(this,
                "Game over. The winner is: " + winner);
        newGame();
    }

    private void startNewGame() {
        if (JOptionPane.showConfirmDialog(this, "Are you sure, you want to start a new game?",
                "Exit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            newGame();
        }
    }

    private void newGame() {
        Window newWindow = new Window(size, mainWindow);
        switch (size) {
            case 5 -> newWindow.setSize(500, 400);
            case 7 -> newWindow.setSize(700, 500);
            case 9 -> newWindow.setSize(900, 600);
        }
        newWindow.setLocationRelativeTo(null);
        newWindow.setVisible(true);
        this.dispose();
        mainWindow.getWindowList().remove(this);
    }

    private void updateGameStatus() {
        gameStatus.setText("Current player:");
        currentPlayer.setText(gamelogic.getActualPlayer().name());
        currentPlayer.setForeground(gamelogic.getActualPlayer().equals(Board.RED) ? Color.RED : Color.BLUE);
        shipsLeft.setText("Ships left: ");
        shipsRed.setText(Board.RED.name() + " (" + gamelogic.getRedShips() + ")");
        shipsRed.setForeground(Color.RED);
        separator.setText(" | ");
        shipsBlue.setText(Board.BLUE.name() + " (" + gamelogic.getBlueShips() + ")");
        shipsBlue.setForeground(Color.BLUE);
    }

}

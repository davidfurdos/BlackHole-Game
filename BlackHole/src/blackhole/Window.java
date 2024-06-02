package blackhole;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class Window extends Launcher {

    private final int size;
    private final int blackHole;
    private int redShipID;
    private int blueShipID;
    private String text;
    private boolean hasYellow;
    private int x;
    private int y;
    private final JLabel label;
    private final JLabel gameStatus;
    private final JButton[][] boardButtons;
    private final Gamelogic gameLogic;
    private final LauncherModel mainWindow;

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
        gameLogic = new Gamelogic(size);
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("Game");
        JMenuItem newGame = new JMenuItem(new AbstractAction("New Game") {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });
        JMenuItem change = new JMenuItem(new AbstractAction("Change boardsize") {
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
        menuFile.add(newGame);
        menuFile.addSeparator();
        menuFile.add(change);
        menuFile.addSeparator();
        menuFile.add(menuExit);
        menuBar.add(menuFile);
        setJMenuBar(menuBar);
        JPanel top = new JPanel();
        gameStatus = new JLabel("");
        updateGameStatus();
        top.add(gameStatus);
        JPanel bottom = new JPanel();
        label = new JLabel("Move ship to direction:");
        bottom.add(label);
        JButton up = new JButton("Up");
        up.addActionListener(directionActionListener("up"));
        JButton down = new JButton("Down");
        down.addActionListener(directionActionListener("down"));
        JButton left = new JButton("Left");
        left.addActionListener(directionActionListener("left"));
        JButton right = new JButton("Right");
        right.addActionListener(directionActionListener("right"));
        bottom.add(up);
        bottom.add(down);
        bottom.add(left);
        bottom.add(right);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(size, size));
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                addButton(mainPanel, i, j);
            }
        }
        setLocationRelativeTo(null);
        setVisible(true);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.PAGE_START);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        getContentPane().add(bottom, BorderLayout.AFTER_LAST_LINE);
    }

    private void addButton(JPanel panel, final int i, final int j) {
        boardButtons[i][j] = new JButton();
        boardButtons[i][j].addActionListener((ActionEvent e) -> {
            if (!hasYellow && gameLogic.getTable(i, j) == gameLogic.getActualPlayer()) {
                boardButtons[i][j].setBackground(Color.YELLOW);
                hasYellow = !hasYellow;
                x = i;
                y = j;
                text = boardButtons[i][j].getText();
            } else if (hasYellow && gameLogic.getTable(i, j) == gameLogic.getActualPlayer()) {
                for (int k = 0; k < size; k++) {
                    for (int l = 0; l < size; l++) {
                        if (boardButtons[k][l].getBackground() == Color.YELLOW) {
                            boardButtons[k][l].setBackground((gameLogic.getTable(i, j) == Board.Red) ? Color.RED : Color.BLUE);
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
        } else if (gameLogic.getTable(i, j) == Board.Red) {
            boardButtons[i][j].setBackground(Color.RED);
            boardButtons[i][j].setText(Integer.toString(redShipID));
            redShipID++;
        } else if (gameLogic.getTable(i, j) == Board.Blue) {
            boardButtons[i][j].setBackground(Color.BLUE);
            boardButtons[i][j].setText(Integer.toString(blueShipID));
            blueShipID++;

        } else {
            boardButtons[i][j].setBackground(Color.WHITE);
        }
        panel.add(boardButtons[i][j]);
    }

    private ActionListener directionActionListener(String getdirection) {
        return (ActionEvent e) -> {
            if (hasYellow && x != size && y != size) {
                int[] result;
                result = gameLogic.move(x, y, getdirection);
                int cx = result[0];
                int cy = result[1];
                hasYellow = !hasYellow;
                boardButtons[x][y].setBackground(Color.WHITE);
                boardButtons[x][y].setText(null);
                if (gameLogic.getTable(result[0], result[1]) != Board.Blackhole) {
                    boardButtons[cx][cy].setBackground((gameLogic.getActualPlayer() == Board.Red) ? Color.RED : Color.BLUE);
                    boardButtons[cx][cy].setText(text);
                }
                if (gameLogic.getActualPlayer() == Board.Red) {
                    gameLogic.setActualPlayer(Board.Blue);
                } else {
                    gameLogic.setActualPlayer(Board.Red);
                }
                updateGameStatus();
                if (gameLogic.getBlueships() == (size-1)/2) {
                    showGameOverMessage(Board.Blue);
                } else if (gameLogic.getRedships() == (size-1)/2) {
                    showGameOverMessage(Board.Red);
                }
                x = size;
                y = size;
                text = null;
            }
        };
    }
    
    private void showExitConfirmation() {
        int exit = JOptionPane.showConfirmDialog(this, "Are you sure, you want to quit?",
                "Exit", JOptionPane.YES_NO_OPTION);
        if (exit == JOptionPane.YES_OPTION) {
            doUponExit();
        }
    }

    public void showGameOverMessage(Board winner) {
        JOptionPane.showMessageDialog(this,
                "Game over. The winner is: " + winner);
        newGame();
    }

    private void newGame() {
        Window newWindow = new Window(size, mainWindow);
                    switch (size) {
                case 5 -> {
                    newWindow.setSize(500, 400);
                    newWindow.setLocationRelativeTo(null);
                }
                case 7 -> {
                    newWindow.setSize(700,500);
                    newWindow.setLocationRelativeTo(null);
                }
                default -> {
                    newWindow.setSize(900,600);
                    newWindow.setLocationRelativeTo(null);
                }
            }
        newWindow.setVisible(true);
        this.dispose();
        mainWindow.getWindowList().remove(this);
    }

    private void updateGameStatus() {
        gameStatus.setText("Current player: "
                + gameLogic.getActualPlayer().name() + "  |  "
                + "Ships left:  Red(" + gameLogic.getRedships()
                + ")  -  Blue(" + gameLogic.getBlueships() + ")");
    }

    @Override
    protected void doUponExit() {
        super.doUponExit();
        mainWindow.getWindowList().remove(this);
    }

    public JButton[][] getBoardButtons() {
        return boardButtons;
    }

    public void setBoardbutton(int i, int j, Board color, String id) {
        this.boardButtons[i][j].setBackground((color == Board.Red) ? Color.RED : Color.BLUE);
        this.boardButtons[i][j].setText(id);
    }
}

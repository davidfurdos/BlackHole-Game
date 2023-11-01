package model;

public class GameLogic {
    private final int size;
    private final int blackHole;
    private int redShipsLeft;
    private int blueShipsLeft;
    private Board actualPlayer;
    private final Board[][] table;

    public GameLogic(int size) {
        this.size = size;
        blackHole = (size / 2);
        redShipsLeft = size - 1;
        blueShipsLeft = size - 1;
        actualPlayer = Board.BLUE;
        table = new Board[size][size];
        initializeTable(size);
    }
    private void initializeTable (int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                table[i][j] = Board.WHITE;
            }
        }
        switch (size) {
            case 5 -> {
                int j = 5;
                for (int i = 0; i < 2; i++) {
                    table[i][i] = Board.RED;
                    table[i][j - 1] = Board.RED;
                    j--;
                }
                j = 1;
                for (int i = 3; i < 5; i++) {
                    table[i][j] = Board.BLUE;
                    table[i][i] = Board.BLUE;
                    j--;
                }
            }
            case 7 -> {
                int j = 7;
                for (int i = 0; i < 3; i++) {
                    table[i][i] = Board.RED;
                    table[i][j - 1] = Board.RED;
                    j--;
                }
                j = 2;
                for (int i = 4; i < 7; i++) {
                    table[i][j] = Board.BLUE;
                    table[i][i] = Board.BLUE;
                    j--;
                }
            }
            case 9 -> {
                int j = 9;
                for (int i = 0; i < 4; i++) {
                    table[i][i] = Board.RED;
                    table[i][j - 1] = Board.RED;
                    j--;
                }
                j = 3;
                for (int i = 5; i < 9; i++) {
                    table[i][j] = Board.BLUE;
                    table[i][i] = Board.BLUE;
                    j--;
                }
            }
        }
    }
    public int[] move(int x, int y, Directions direction) {
        int[] result = {x, y};
        setTable(x, y, Board.WHITE);
        table[blackHole][blackHole] = Board.BLACKHOLE;
        switch (direction) {
            case UP -> {
                for (int i = x - 1; i > -1; i--) {
                    if (getTable(i, y).equals(Board.WHITE)) {
                        result[0] = i;
                    } else if (getTable(i, y).equals(Board.RED) || getTable(i, y).equals(Board.BLUE)) {
                        result[0] = i + 1;
                        break;
                    } else {
                        decreaseShips(getActualPlayer());
                        result[0] = blackHole;
                        result[1] = blackHole;
                        break;
                    }
                }
                if (!getTable(result[0], y).equals(Board.BLACKHOLE)) {
                    setTable(result[0], y, getActualPlayer());
                }
            }
            case DOWN -> {
                for (int i = x + 1; i < size; i++) {
                    if (getTable(i, y).equals(Board.WHITE)) {
                        result[0] = i;
                    } else if (getTable(i, y).equals(Board.RED) || getTable(i, y).equals(Board.BLUE)) {
                        result[0] = i - 1;
                        break;
                    } else {
                        decreaseShips(getActualPlayer());
                        result[0] = blackHole;
                        result[1] = blackHole;
                        break;
                    }
                }
                if (!getTable(result[0], y).equals(Board.BLACKHOLE)) {
                    setTable(result[0], y, getActualPlayer());
                }
            }
            case LEFT -> {
                for (int i = y - 1; i > -1; i--) {
                    if (getTable(x, i).equals(Board.WHITE)) {
                        result[1] = i;
                    } else if (getTable(x, i).equals(Board.RED) || getTable(x, i).equals(Board.BLUE)) {
                        result[1] = i + 1;
                        break;
                    } else {
                        decreaseShips(getActualPlayer());
                        result[0] = blackHole;
                        result[1] = blackHole;
                        break;
                    }
                }
                if (!getTable(x, result[1]).equals(Board.BLACKHOLE)) {
                    setTable(x, result[1], getActualPlayer());
                }
            }
            case RIGHT -> {
                for (int i = y + 1; i < size; i++) {
                    if (getTable(x, i).equals(Board.WHITE)) {
                        result[1] = i;
                    } else if (getTable(x, i).equals(Board.RED) || getTable(x, i).equals(Board.BLUE)) {
                        result[1] = i - 1;
                        break;
                    } else {
                        decreaseShips(getActualPlayer());
                        result[0] = blackHole;
                        result[1] = blackHole;
                        break;
                    }
                }
                if (getTable(x, result[1]) != Board.BLACKHOLE) {
                    setTable(x, result[1], getActualPlayer());
                }
            }
        }
        return result;
    }
    public void decreaseShips(Board color) {
        if (color.equals(Board.RED)) {
            redShipsLeft--;
        } else if (color.equals(Board.BLUE)) {
            blueShipsLeft--;
        }
    }

    public Board getTable(int i, int j) {
        return table[i][j];
    }

    public void setTable(int i, int j, Board color) {
        this.table[i][j] = color;
    }

    public Board getActualPlayer() {
        return actualPlayer;
    }

    public int getRedShips() {
        return redShipsLeft;
    }

    public int getBlueShips() {
        return blueShipsLeft;
    }

    public void setActualPlayer(Board actualPlayer) {
        this.actualPlayer = actualPlayer;
    }
}

package blackhole;

public class Gamelogic {

    private final int size;
    private final int blackHole;
    private int redShipsLeft;
    private int blueShipsLeft;
    private Board actualPlayer;
    private Board[][] table;

    public Gamelogic(int size) {
        this.size = size;
        blackHole = (size / 2);
        redShipsLeft = size - 1;
        blueShipsLeft = size - 1;
        actualPlayer = Board.Blue;
        table = new Board[size][size];
        actualPlayer = Board.Blue;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                table[i][j] = Board.White;
            }
        }
        switch (size) {
            case 5 -> {

                int j = 5;
                for (int i = 0; i < 2; i++) {
                    table[i][i] = Board.Red;
                    table[i][j - 1] = Board.Red;
                    j--;

                }
                j = 1;
                for (int i = 3; i < 5; i++) {
                    table[i][j] = Board.Blue;
                    table[i][i] = Board.Blue;
                    j--;
                }
            }
            case 7 -> {
                int j = 7;
                for (int i = 0; i < 3; i++) {
                    table[i][i] = Board.Red;
                    table[i][j - 1] = Board.Red;
                    j--;
                }
                j = 2;
                for (int i = 4; i < 7; i++) {
                    table[i][j] = Board.Blue;
                    table[i][i] = Board.Blue;
                    j--;
                }
            }
            case 9 -> {
                int j = 9;
                for (int i = 0; i < 4; i++) {
                    table[i][i] = Board.Red;
                    table[i][j - 1] = Board.Red;
                    j--;
                }
                j = 3;
                for (int i = 5; i < 9; i++) {
                    table[i][j] = Board.Blue;
                    table[i][i] = Board.Blue;
                    j--;
                }
            }
        }
    }

    public int[] move(int x, int y, String direction) {
        int[] result = {x, y};
        setTable(x, y, Board.White);
        table[blackHole][blackHole] = Board.Blackhole;
        switch (direction) {
            case "up" -> {
                for (int i = x - 1; i > -1; i--) {
                    if (getTable(i, y) == Board.White) {
                        result[0] = i;
                    } else if (getTable(i, y) == Board.Red || getTable(i, y) == Board.Blue) {
                        result[0] = i + 1;
                        result[1] = y;
                        break;
                    } else {
                        decraseShips(getActualPlayer());
                        result[0] = blackHole;
                        result[1] = blackHole;
                        break;
                    }
                }
                if (getTable(result[0], y) != Board.Blackhole) {
                    setTable(result[0], y, getActualPlayer());
                }
            }
            case "down" -> {
                for (int i = x + 1; i < size; i++) {
                    if (getTable(i, y) == Board.White) {
                        result[0] = i;
                    } else if (getTable(i, y) == Board.Red || getTable(i, y) == Board.Blue) {
                        result[0] = i - 1;
                        result[1] = y;
                        break;
                    } else {
                        decraseShips(getActualPlayer());
                        result[0] = blackHole;
                        result[1] = blackHole;
                        break;
                    }
                }
                if (getTable(result[0], y) != Board.Blackhole) {
                    setTable(result[0], y, getActualPlayer());
                }
            }
            case "left" -> {
                for (int i = y - 1; i > -1; i--) {
                    if (getTable(x, i) == Board.White) {
                        result[1] = i;
                    } else if (getTable(x, i) == Board.Red || getTable(x, i) == Board.Blue) {
                        result[0] = x;
                        result[1] = i + 1;
                        break;
                    } else {
                        decraseShips(getActualPlayer());
                        result[0] = blackHole;
                        result[1] = blackHole;
                        break;
                    }
                }
                if (getTable(x, result[1]) != Board.Blackhole) {
                    setTable(x, result[1], getActualPlayer());
                }
            }
            case "right" -> {
                for (int i = y + 1; i < size; i++) {
                    if (getTable(x, i) == Board.White) {
                        result[1] = i;
                    } else if (getTable(x, i) == Board.Red || getTable(x, i) == Board.Blue) {
                        result[0] = x;
                        result[1] = i - 1;
                        break;
                    } else {
                        decraseShips(getActualPlayer());
                        result[0] = blackHole;
                        result[1] = blackHole;
                        break;
                    }
                }
                if (getTable(x, result[1]) != Board.Blackhole) {
                    setTable(x, result[1], getActualPlayer());
                }
            }

        }
        return result;
    }
    
    public void decraseShips(Board color) {
        if (color == Board.Red) {
            redShipsLeft--;
        } else if (color == Board.Blue) {
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

    public int getRedships() {
        return redShipsLeft;
    }

    public int getBlueships() {
        return blueShipsLeft;
    }

    public void setActualPlayer(Board actualPlayer) {
        this.actualPlayer = actualPlayer;
    }
}

package models;

public class Game {
    public static boolean checkWin(byte[][] field, byte symbol) {
        for (int i = 0; i < 3; i++) {
            if ((field[i][0] == symbol && field[i][1] == symbol &&
                    field[i][2] == symbol) ||
                    (field[0][i] == symbol && field[1][i] == symbol &&
                            field[2][i] == symbol))
                return true;
        }
        if ((field[0][0] == symbol && field[1][1] == symbol &&
                field[2][2] == symbol) ||
                (field[2][0] == symbol && field[1][1] == symbol &&
                        field[0][2] == symbol))
            return true;
        return false;
    }
}

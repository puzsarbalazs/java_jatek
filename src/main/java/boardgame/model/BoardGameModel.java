package boardgame.model;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.Alert;

import static boardgame.model.Square.NONE;

public class BoardGameModel {

    //public static int BOARD_SIZE = 5;
    public static int BOARD_SIZE = 3;

    private ReadOnlyObjectWrapper<Square>[][] board = new ReadOnlyObjectWrapper[BOARD_SIZE][BOARD_SIZE];

    public BoardGameModel() {
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new ReadOnlyObjectWrapper<Square>(NONE);
            }
        }
    }

    public ReadOnlyObjectProperty<Square> squareProperty(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }

    public Square getSquare(int i, int j) {
        return board[i][j].get();
    }

    /*public void move(int i, int j) {
        if (board[i][j].get()==NONE) {
            board[i][j].set(
                    switch (board[i][j].get()) {
                        case NONE -> nextPlayer()==1 ? Square.RED : Square.BLUE;
                        case RED -> Square.BLUE;
                        case BLUE -> NONE;
                    }
            );
            System.out.println(winner());
        } else {
            System.out.println("Incorrect move");
        }
    }
    */

    public void move(int i, int j) {
        if (board[i][j].get()==NONE) {
            board[i][j].set(nextPlayer()==1 ? Square.RED : Square.BLUE);
            //System.out.println(winner()); //ezek elvileg logolva vannak
            //System.out.println(isEnd()); //ellenőrzés
            //ezt a controllerbe kéne átteni, bind-olni az isEndhez

            /*if (isEnd()) {
                var alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Game Over");
                alert.setContentText("Congratulations, you have solved the puzzle!");
                alert.showAndWait();
                //resetGame();
            }

             */
        } else {
            System.out.println("Incorrect move");
        }
    }

    /*rest game, ennek is a controllerben lesz majd a helye
    public void resetGame() {
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new ReadOnlyObjectWrapper<Square>(NONE);
            }
        }
    }

     */

    public int moveCounter() {
        int movesDid = 0;
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j].get() != NONE) {
                    movesDid++;
                }
            }
        }
        return movesDid;
    }

    public int nextPlayer() {
        if (moveCounter() % 2 == 0) {
            return 1;
        } else {
            return 2;
        }
    }

    public boolean isEnd() {
        if (winner() != null ||  moveCounter() == 9) {
            return true;
        } else {
            return false;
        }
    }

    public Square winner() {
        if (board[0][0].get() == board[1][1].get() && board[1][1].get() == board[2][2].get() && board[0][0].get() != NONE)
            //return nextPlayer();
            return board[0][0].get();
        if (board[2][0].get() == board[1][1].get() && board[1][1].get() == board[0][2].get() && board[2][0].get() != NONE)
            //return nextPlayer();
            return board[2][0].get();
        for (int i = 0; i < 3; i++)
            if (board[i][1].get() == board[i][2].get() && board[i][2].get() == board[i][0].get() && board[i][0].get() != NONE)
                //return nextPlayer();
                return board[i][1].get();
        for (int j = 0; j < 3; j++)
            if (board[0][j].get() == board[1][j].get() && board[1][j].get() == board[2][j].get() && board[0][j].get() != NONE)
                //return nextPlayer();
                return board[0][j].get();
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                sb.append(board[i][j].get().ordinal()).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }



    public static void main(String[] args) {
        var model = new BoardGameModel();
        System.out.println(model);
    }

}

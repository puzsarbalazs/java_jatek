package boardgame.model;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.Alert;
import org.tinylog.Logger;

import static boardgame.model.Square.NONE;
import static boardgame.model.Square.RED;

/**
 * Class representing the model of the game.
 */
public class BoardGameModel {

    /**
     * The size of the board.
     */
    public static int BOARD_SIZE = 5;

    private ReadOnlyObjectWrapper<Square>[][] board = new ReadOnlyObjectWrapper[BOARD_SIZE][BOARD_SIZE];

    /**
     * Creates the initial board game model, every position has {@code NONE} value
     */
    public BoardGameModel() {
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new ReadOnlyObjectWrapper<Square>(NONE);
            }
        }
    }


    /**
     * @param i the row of the position
     * @param j is the column of the position
     * @return the {@code ReadOnlyObjectProperty} of the Square in the given position
     */
    public ReadOnlyObjectProperty<Square> squareProperty(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }

    /**
     * @param i the row of the position
     * @param j is the column of the position
     * @return the value of the {@code Square} in the given position
     */
    public Square getSquare(int i, int j) {
        return board[i][j].get();
    }

    /**
     * Decides if the specified move is a valid move or not.
     * The move is valid, if nobody made a move on that position before, and the position is at the bottom of the board,
     * or somebody already made a move to the position right under the given position.
     *
     * @param i the row of the position
     * @param j is the column of the position
     * @return a boolean, {@code true} if the specified move is valid, {@code false} otherwise.
     */
    public boolean isValid(int i, int j) {
        return board[i][j].get() == NONE && (i==4 || board[i+1][j].get()!=NONE) ;
    }

    /**
     * Make a move to the given position if it is a valid move.
     * @param i the row of the position
     * @param j is the column of the position
     */
    public void move(int i, int j) {
        if (isValid( i, j)) {
            board[i][j].set(nextPlayer()==1 ? Square.RED : Square.BLUE);
        }
    }

    /**
     * {@return an integer, that shows how many steps have been taken in the game}
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

    /**
     * Decides which player will take a move in this turn, blue or red.
     * @return an integer, 1, if the red player will move in this turn, 2 otherwise.
     */
    public int nextPlayer() {
        if (moveCounter() % 2 == 0) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * {@return a boolen, {@code true} if the game is over, {@code false} otherwise}
     */
    public boolean isEnd() {
        if (winner() != null ||  moveCounter() == 25) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * {@return the winner of the game, {@code RED} if the red player won, {@code BLUE} if the blue player won,
     * null if there is no winner}
     */
    public Square winner() {

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (board[i][j].get() == board[i+1][j+1].get() && board[i+1][j+1].get() == board[i+2][j+2].get() &&
                        board[i+2][j+2].get() == board[i+3][j+3].get() && board[i][j].get() != NONE)  {
                    return board[i][j].get();
                }
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 4; j > 2; j--) {
                if (board[i][j].get() == board[i+1][j-1].get() && board[i+1][j-1].get() == board[i+2][j-2].get() &&
                        board[i+2][j-2].get() == board[i+3][j-3].get() && board[i][j].get() != NONE)  {
                    return board[i][j].get();
                }
            }
        }
        for (int i = 0; i<BOARD_SIZE; i++) {
            for (int j = 0; j<2; j++) {
                if (board[i][j].get() == board[i][j+1].get() && board[i][j+1].get() == board[i][j+2].get() &&
                        board[i][j+2].get() == board[i][j+3].get() && board[i][j].get() !=NONE) {
                    return board[i][j].get();
                }
            }
        }
        for (int i = 0; i<2; i++) {
            for (int j = 0; j<BOARD_SIZE; j++) {
                if (board[i][j].get() == board[i+1][j].get() && board[i+1][j].get() == board[i+2][j].get() &&
                        board[i+2][j].get() == board[i+3][j].get() && board[i][j].get() !=NONE) {
                    return board[i][j].get();
                }
            }
        }
        return null;
    }

    /**
     * Returns a {@link String} representation of the {@code BoardGameModel} object.
     *
     * @return a String describing the object
     */
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

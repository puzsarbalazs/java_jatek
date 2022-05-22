package boardgame.model;

import javafx.beans.property.ReadOnlyObjectWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static boardgame.model.Square.*;
import static org.junit.jupiter.api.Assertions.*;


public class BoardGameModelTest {
    private BoardGameModel board;
    private BoardGameModel testModel2;
    private BoardGameModel testModel3;



    @BeforeEach
    private void setUp() {
        board = new BoardGameModel();

        testModel2 = new BoardGameModel();
        testModel2.move(4,0);
        testModel2.move(4,4);
        testModel2.move(4,1);
        testModel2.move(3,4);
        testModel2.move(4,2);
        testModel2.move(2,4);
        testModel2.move(3,0);
        testModel2.move(3,2);
        testModel2.move(3,1);
        testModel2.move(2,0);
        testModel2.move(2,2);
        testModel2.move(2,1);
        testModel2.move(1,1);
        testModel2.move(1,0);
    }

    private ReadOnlyObjectWrapper<Square>[][] testBoard1;



    public void endWithDraw() {
    }

    @Test
    void testNextPlayer() {
        assertEquals(board.nextPlayer(), 1);
        board.move(4,0);
        assertEquals(board.nextPlayer(),2);
    }

    @Test
    void testMoveCounter() {
        assertEquals(board.moveCounter(),0);
        board.move(4,4);
        assertEquals(board.moveCounter(), 1);
        assertEquals(testModel2.moveCounter(), 14);
    }

    @Test
    void testIsValid() {
        assertFalse(board.isValid(0,0));
        assertTrue(board.isValid(4,0));
    }

    @Test
    void testIsEnd() {
        assertFalse(board.isEnd());
        testModel2.move(4,3);
        assertTrue(testModel2.isEnd());
    }

    @Test
    void testWinner() {
        assertNull(board.winner());
    }

    @Test
    void testVerticalWinner() {
        testModel2.move(1,2);
        testModel2.move(1,4);
        assertEquals(testModel2.winner(), BLUE);
    }

    @Test
    void testHorizontalWinner() {
        testModel2.move(4,3);
        assertEquals(testModel2.winner(), RED);
    }

    @Test
    void testDiagonalRightDownWinner() {
        testModel2.move(1,2);
        testModel2.move(4,3);
        assertEquals(testModel2.winner(), BLUE);
    }

    @Test
    void testDiagonalLeftDownWinner() {
        testModel3 = new BoardGameModel();
        testModel3.move(4,0);
        testModel3.move(4,1);
        testModel3.move(3,1);
        testModel3.move(4,2);
        testModel3.move(3,2);
        testModel3.move(4,3);
        testModel3.move(2,2);
        testModel3.move(3,3);
        testModel3.move(2,3);
        testModel3.move(4,4);
        testModel3.move(1,3);
        assertEquals(testModel3.winner(), RED);
    }

    @Test
    void testGetSquare() {
        assertEquals(testModel2.getSquare(4,0), RED);
        assertEquals(testModel2.getSquare(4,4), BLUE);
        assertEquals(testModel2.getSquare(0,0), NONE);
    }

    @Test
    void testToString() {
        assertEquals(board.toString(), "0 0 0 0 0 \n0 0 0 0 0 \n0 0 0 0 0 \n0 0 0 0 0 \n0 0 0 0 0 \n");
    }

}

package boardgame.model;

import javafx.beans.property.ReadOnlyObjectWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static boardgame.model.Square.NONE;
import static org.junit.jupiter.api.Assertions.*;


public class BoardGameModelTest {
    private BoardGameModel board;



    @BeforeEach
    private void setUp() {
        board = new BoardGameModel();
    }

    @Test
    void testNextPlayer() {
        assertEquals(board.nextPlayer(), 1);
        board.move(0,0);
        assertEquals(board.nextPlayer(),2);
    }


}

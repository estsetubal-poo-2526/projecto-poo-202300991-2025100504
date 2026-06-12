package tictactoe.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void newBoardShouldBeEmpty() throws InvalidMoveException {
        Board board = new Board();

        for (int row = 0; row < Board.SIZE; row++) {
            for (int column = 0; column < Board.SIZE; column++) {
                assertTrue(board.isCellFree(row, column));
                assertEquals(Symbol.EMPTY, board.getCell(row, column).getSymbol());
            }
        }
    }

    @Test
    void shouldMarkCellWithSymbol() throws InvalidMoveException {
        Board board = new Board();

        board.markCell(0, 0, Symbol.X);

        assertFalse(board.isCellFree(0, 0));
        assertEquals(Symbol.X, board.getCell(0, 0).getSymbol());
    }

    @Test
    void shouldNotMarkOccupiedCell() throws InvalidMoveException {
        Board board = new Board();

        board.markCell(0, 0, Symbol.X);

        assertThrows(InvalidMoveException.class, () -> {
            board.markCell(0, 0, Symbol.O);
        });

        assertEquals(Symbol.X, board.getCell(0, 0).getSymbol());
    }

    @Test
    void shouldNotMarkCellWithEmptySymbol() {
        Board board = new Board();

        assertThrows(InvalidMoveException.class, () -> {
            board.markCell(0, 0, Symbol.EMPTY);
        });
    }

    @Test
    void shouldNotMarkCellWithNullSymbol() {
        Board board = new Board();

        assertThrows(InvalidMoveException.class, () -> {
            board.markCell(0, 0, null);
        });
    }

    @Test
    void shouldNotAllowInvalidPosition() {
        Board board = new Board();

        assertThrows(InvalidMoveException.class, () -> {
            board.markCell(-1, 0, Symbol.X);
        });

        assertThrows(InvalidMoveException.class, () -> {
            board.markCell(0, 3, Symbol.X);
        });
    }

    @Test
    void shouldDetectWinnerInRow() throws InvalidMoveException {
        Board board = new Board();

        board.markCell(0, 0, Symbol.X);
        board.markCell(0, 1, Symbol.X);
        board.markCell(0, 2, Symbol.X);

        assertTrue(board.checkWinner(Symbol.X));
    }

    @Test
    void shouldDetectWinnerInColumn() throws InvalidMoveException {
        Board board = new Board();

        board.markCell(0, 1, Symbol.O);
        board.markCell(1, 1, Symbol.O);
        board.markCell(2, 1, Symbol.O);

        assertTrue(board.checkWinner(Symbol.O));
    }

    @Test
    void shouldDetectWinnerInMainDiagonal() throws InvalidMoveException {
        Board board = new Board();

        board.markCell(0, 0, Symbol.X);
        board.markCell(1, 1, Symbol.X);
        board.markCell(2, 2, Symbol.X);

        assertTrue(board.checkWinner(Symbol.X));
    }

    @Test
    void shouldDetectWinnerInSecondDiagonal() throws InvalidMoveException {
        Board board = new Board();

        board.markCell(0, 2, Symbol.O);
        board.markCell(1, 1, Symbol.O);
        board.markCell(2, 0, Symbol.O);

        assertTrue(board.checkWinner(Symbol.O));
    }

    @Test
    void shouldReturnFalseWhenThereIsNoWinner() throws InvalidMoveException {
        Board board = new Board();

        board.markCell(0, 0, Symbol.X);
        board.markCell(0, 1, Symbol.O);
        board.markCell(0, 2, Symbol.X);

        assertFalse(board.checkWinner(Symbol.X));
        assertFalse(board.checkWinner(Symbol.O));
    }

    @Test
    void shouldDetectFullBoard() throws InvalidMoveException {
        Board board = new Board();

        board.markCell(0, 0, Symbol.X);
        board.markCell(0, 1, Symbol.O);
        board.markCell(0, 2, Symbol.X);

        board.markCell(1, 0, Symbol.X);
        board.markCell(1, 1, Symbol.O);
        board.markCell(1, 2, Symbol.O);

        board.markCell(2, 0, Symbol.O);
        board.markCell(2, 1, Symbol.X);
        board.markCell(2, 2, Symbol.X);

        assertTrue(board.isFull());
    }

    @Test
    void resetShouldClearBoard() throws InvalidMoveException {
        Board board = new Board();

        board.markCell(0, 0, Symbol.X);
        board.markCell(1, 1, Symbol.O);

        board.reset();

        for (int row = 0; row < Board.SIZE; row++) {
            for (int column = 0; column < Board.SIZE; column++) {
                assertTrue(board.isCellFree(row, column));
                assertEquals(Symbol.EMPTY, board.getCell(row, column).getSymbol());
            }
        }
    }
}
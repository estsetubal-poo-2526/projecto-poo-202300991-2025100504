package tictactoe.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void newGameShouldStartAsNotStarted() {
        Player playerX = new HumanPlayer("Artem", Symbol.X);
        Player playerO = new HumanPlayer("Josebe", Symbol.O);

        Game game = new Game(playerX, playerO);

        assertEquals(GameState.NOT_STARTED, game.getState());
        assertEquals(playerX, game.getCurrentPlayer());
        assertNull(game.getWinner());
        assertFalse(game.isFinished());
    }

    @Test
    void startShouldPutGameRunning() {
        Player playerX = new HumanPlayer("Artem", Symbol.X);
        Player playerO = new HumanPlayer("Josebe", Symbol.O);

        Game game = new Game(playerX, playerO);
        game.start();

        assertEquals(GameState.RUNNING, game.getState());
        assertEquals(playerX, game.getCurrentPlayer());
        assertNull(game.getWinner());
        assertFalse(game.isFinished());
    }

    @Test
    void shouldNotCreateGameWithNullPlayers() {
        Player playerX = new HumanPlayer("Artem", Symbol.X);
        Player playerO = new HumanPlayer("Josebe", Symbol.O);

        assertThrows(IllegalArgumentException.class, () -> {
            new Game(null, playerO);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Game(playerX, null);
        });
    }

    @Test
    void shouldNotCreateGameWithWrongSymbols() {
        Player wrongX = new HumanPlayer("Artem", Symbol.O);
        Player correctO = new HumanPlayer("Josebe", Symbol.O);

        Player correctX = new HumanPlayer("Artem", Symbol.X);
        Player wrongO = new HumanPlayer("Josebe", Symbol.X);

        assertThrows(IllegalArgumentException.class, () -> {
            new Game(wrongX, correctO);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Game(correctX, wrongO);
        });
    }

    @Test
    void shouldNotPlayBeforeStart() {
        Player playerX = new HumanPlayer("Artem", Symbol.X);
        Player playerO = new HumanPlayer("Josebe", Symbol.O);

        Game game = new Game(playerX, playerO);

        assertThrows(InvalidMoveException.class, () -> {
            game.playMove(0, 0);
        });
    }

    @Test
    void firstMoveShouldBePlayerX() throws InvalidMoveException {
        Player playerX = new HumanPlayer("Artem", Symbol.X);
        Player playerO = new HumanPlayer("Josebe", Symbol.O);

        Game game = new Game(playerX, playerO);
        game.start();

        game.playMove(0, 0);

        assertEquals(Symbol.X, game.getBoard().getCell(0, 0).getSymbol());
        assertEquals(playerO, game.getCurrentPlayer());
        assertEquals(GameState.RUNNING, game.getState());
    }

    @Test
    void shouldSwitchPlayersAfterEachValidMove() throws InvalidMoveException {
        Player playerX = new HumanPlayer("Artem", Symbol.X);
        Player playerO = new HumanPlayer("Josebe", Symbol.O);

        Game game = new Game(playerX, playerO);
        game.start();

        assertEquals(playerX, game.getCurrentPlayer());

        game.playMove(0, 0);
        assertEquals(playerO, game.getCurrentPlayer());

        game.playMove(1, 0);
        assertEquals(playerX, game.getCurrentPlayer());
    }

    @Test
    void shouldNotAllowMoveInOccupiedCell() throws InvalidMoveException {
        Player playerX = new HumanPlayer("Artem", Symbol.X);
        Player playerO = new HumanPlayer("Josebe", Symbol.O);

        Game game = new Game(playerX, playerO);
        game.start();

        game.playMove(0, 0);

        assertThrows(InvalidMoveException.class, () -> {
            game.playMove(0, 0);
        });

        assertEquals(Symbol.X, game.getBoard().getCell(0, 0).getSymbol());
    }

    @Test
    void shouldDetectWinForPlayerX() throws InvalidMoveException {
        Player playerX = new HumanPlayer("Artem", Symbol.X);
        Player playerO = new HumanPlayer("Josebe", Symbol.O);

        Game game = new Game(playerX, playerO);
        game.start();

        game.playMove(0, 0); // X
        game.playMove(1, 0); // O
        game.playMove(0, 1); // X
        game.playMove(1, 1); // O
        game.playMove(0, 2); // X wins

        assertEquals(GameState.WIN, game.getState());
        assertEquals(playerX, game.getWinner());
        assertTrue(game.isFinished());
    }

    @Test
    void shouldDetectDraw() throws InvalidMoveException {
        Player playerX = new HumanPlayer("Artem", Symbol.X);
        Player playerO = new HumanPlayer("Josebe", Symbol.O);

        Game game = new Game(playerX, playerO);
        game.start();

        game.playMove(0, 0); // X
        game.playMove(0, 1); // O
        game.playMove(0, 2); // X

        game.playMove(1, 1); // O
        game.playMove(1, 0); // X
        game.playMove(1, 2); // O

        game.playMove(2, 1); // X
        game.playMove(2, 0); // O
        game.playMove(2, 2); // X

        assertEquals(GameState.DRAW, game.getState());
        assertNull(game.getWinner());
        assertTrue(game.isFinished());
    }

    @Test
    void startShouldResetPreviousGame() throws InvalidMoveException {
        Player playerX = new HumanPlayer("Artem", Symbol.X);
        Player playerO = new HumanPlayer("Josebe", Symbol.O);

        Game game = new Game(playerX, playerO);
        game.start();

        game.playMove(0, 0);
        game.playMove(1, 0);
        game.playMove(0, 1);
        game.playMove(1, 1);
        game.playMove(0, 2);

        assertEquals(GameState.WIN, game.getState());

        game.start();

        assertEquals(GameState.RUNNING, game.getState());
        assertNull(game.getWinner());
        assertEquals(playerX, game.getCurrentPlayer());

        for (int row = 0; row < Board.SIZE; row++) {
            for (int column = 0; column < Board.SIZE; column++) {
                assertTrue(game.getBoard().isCellFree(row, column));
            }
        }
    }
}
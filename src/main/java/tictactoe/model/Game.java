package tictactoe.model;

public class Game {
    private final Board board;
    private final Player playerX;
    private final Player playerO;

    private Player currentPlayer;
    private GameState state;
    private Player winner;

    public Game(Player playerX, Player playerO) {
        if (playerX == null || playerO == null) {
            throw new IllegalArgumentException("Jogador nao pode ser null.");
        }

        if (playerX.getSymbol() != Symbol.X) {
            throw new IllegalArgumentException("Primeiro jogador deve ter o simbolo X.");
        }

        if (playerO.getSymbol() != Symbol.O) {
            throw new IllegalArgumentException("Segundo jogador deve ter o simbolo O.");
        }

        this.board = new Board();
        this.playerX = playerX;
        this.playerO = playerO;
        this.currentPlayer = playerX;
        this.state = GameState.NOT_STARTED;
        this.winner = null;
    }

    public void start() {
        board.reset();
        currentPlayer = playerX;
        state = GameState.RUNNING;
        winner = null;
    }

    public void playMove(int row, int column) throws InvalidMoveException {
        if (state != GameState.RUNNING) {
            throw new InvalidMoveException("O jogo ainda não está em execução.");
        }

        board.markCell(row, column, currentPlayer.getSymbol());

        if (board.checkWinner(currentPlayer.getSymbol())) {
            state = GameState.WIN;
            winner = currentPlayer;
        } else if (board.isFull()) {
            state = GameState.DRAW;
            winner = null;
        } else {
            switchPlayer();
        }
    }

    private void switchPlayer() {
        if (currentPlayer == playerX) {
            currentPlayer = playerO;
        } else {
            currentPlayer = playerX;
        }
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayerX() {
        return playerX;
    }

    public Player getPlayerO() {
        return playerO;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GameState getState() {
        return state;
    }

    public Player getWinner() {
        return winner;
    }

    public boolean isFinished() {
        return state == GameState.WIN || state == GameState.DRAW;
    }
}

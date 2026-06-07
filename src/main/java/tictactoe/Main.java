package tictactoe;

import tictactoe.model.Game;
import tictactoe.model.HumanPlayer;
import tictactoe.model.InvalidMoveException;
import tictactoe.model.Player;
import tictactoe.model.Symbol;

public class Main {
    public static void main(String[] args) {
        Player playerX = new HumanPlayer("Artem", Symbol.X);
        Player playerO = new HumanPlayer("Josebe", Symbol.O);

        Game game = new Game(playerX, playerO);
        game.start();

        try {
            game.playMove(0, 0); // X
            game.playMove(0, 1); // O
            game.playMove(0, 2); // X

            game.playMove(1, 1); // O
            game.playMove(1, 0); // X
            game.playMove(1, 2); // O

            game.playMove(2, 1); // X
            game.playMove(2, 0); // O
            game.playMove(2, 2); // X

            System.out.println("Estado: " + game.getState());

            if (game.getWinner() != null) {
                System.out.println("Vencedor: " + game.getWinner().getName());
                System.out.println("Simbolo: " + game.getWinner().getSymbol());
            } else {
                System.out.println("Empate.");
            }

        } catch (InvalidMoveException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
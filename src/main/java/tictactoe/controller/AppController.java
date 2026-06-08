package tictactoe.controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import tictactoe.model.Game;
import tictactoe.model.HumanPlayer;
import tictactoe.model.Player;
import tictactoe.model.Symbol;
import tictactoe.view.ConfigView;
import tictactoe.view.GameView;
import tictactoe.view.MenuView;
import tictactoe.view.ResultView;

public class AppController {
    private final Stage stage;
    private Game gameModel;

    public AppController(Stage stage) {
        this.stage = stage;
        this.stage.setTitle("Tic-Tac-Toe IPS");
        this.stage.setResizable(false);
    }

    public void showMenu() {
        MenuView menuView = new MenuView(this);
        Scene scene = new Scene(menuView, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    public void showConfig() {
        ConfigView configView = new ConfigView(this);
        Scene scene = new Scene(configView, 400, 400);
        stage.setScene(scene);
    }

    public void showGame(String player1Name, String player2Name) {
        Player playerX = new HumanPlayer(player1Name, Symbol.X);
        Player playerO = new HumanPlayer(player2Name, Symbol.O);

        this.gameModel = new Game(playerX, playerO);

        this.gameModel.start();

        GameView gameView = new GameView(this, gameModel);
        Scene scene = new Scene(gameView, 450, 500);
        stage.setScene(scene);
    }

    public void showResult(String resultMessage) {
        ResultView resultView = new ResultView(this, resultMessage);
        Scene scene = new Scene(resultView, 400, 400);
        stage.setScene(scene);
    }

    public void quitApp() {
        stage.close();
    }
}
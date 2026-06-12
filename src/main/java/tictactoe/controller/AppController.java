package tictactoe.controller;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tictactoe.model.Game;
import tictactoe.model.HumanPlayer;
import tictactoe.model.Player;
import tictactoe.model.Symbol;
import tictactoe.view.AnimatedBackground;
import tictactoe.view.ConfigView;
import tictactoe.view.GameView;
import tictactoe.view.MenuView;
import tictactoe.view.ResultView;

public class AppController {
    private final Stage stage;
    private Game gameModel;

    private final StackPane root = new StackPane();
    private final AnimatedBackground background = new AnimatedBackground();
    private Scene scene;

    public AppController(Stage stage) {
        this.stage = stage;
        this.stage.setTitle("Tic-Tac-Toe IPS");
        this.stage.setResizable(true);

        root.getChildren().add(background);
        scene = new Scene(root, 800, 800);
        stage.setScene(scene);
    }

    private void setContent(Region view) {
        
        view.setStyle((view.getStyle() == null ? "" : view.getStyle())
                + ";-fx-background-color: transparent;");
        if (root.getChildren().size() > 1) {
            root.getChildren().set(1, view);
        } else {
            root.getChildren().add(view);
        }
    }

    // Добавил метод
    public void restartGame() {
        if(gameModel != null) {
            gameModel.start();
            setContent(new GameView(this, gameModel));
        }
    }

    public void showMenu() {
        setContent(new MenuView(this));
        stage.show();
    }

    public void showConfig() {
        setContent(new ConfigView(this));
    }

    public void showGame(String player1Name, String player2Name) {
        Player playerX = new HumanPlayer(player1Name, Symbol.X);
        Player playerO = new HumanPlayer(player2Name, Symbol.O);
        this.gameModel = new Game(playerX, playerO);
        this.gameModel.start();
        setContent(new GameView(this, gameModel));
    }

    public void showResult(String resultMessage) {
        setContent(new ResultView(this, resultMessage));
    }

    public void quitApp() {
        background.stop();
        stage.close();
    }
}

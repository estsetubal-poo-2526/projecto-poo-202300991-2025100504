package tictactoe.controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import tictactoe.view.MenuView;
import tictactoe.view.ConfigView;
import tictactoe.view.GameView;
import tictactoe.view.ResultView;

public class AppController {
    private final Stage stage;

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
        System.out.println("Navegando para o ecrã de Configuração...");
        // Depois vamos instanciar aqui o ConfigView
    }

    public void quitApp() {
        stage.close();
    }
}
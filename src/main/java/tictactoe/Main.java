package tictactoe;

import javafx.application.Application;
import javafx.stage.Stage;
import tictactoe.controller.AppController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        AppController controller = new AppController(primaryStage);
        controller.showMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
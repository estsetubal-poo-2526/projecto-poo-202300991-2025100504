package tictactoe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tictactoe.view.MenuView;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        MenuView menuView = new MenuView();

        Scene scene = new Scene(menuView, 500, 400);

        stage.setTitle("Tic Tac Toe");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {

        launch(args);

    }

}
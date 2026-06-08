package tictactoe.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import tictactoe.controller.AppController;

public class MenuView extends BorderPane {

    private Button newGameButton;
    private Button exitButton;
    private AppController controller;

    public MenuView(AppController controller) {
        this.controller = controller;

        createControls();
        organizeLayout();
        registerHandlers();
    }

    private void createControls() {
        newGameButton = new Button("Novo Jogo");
        exitButton = new Button("Sair");

        newGameButton.setPrefWidth(200);
        exitButton.setPrefWidth(200);
    }

    private void organizeLayout() {
        Label titleLabel = new Label("TIC TAC TOE");

        titleLabel.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;"
        );

        VBox centerBox = new VBox(20, titleLabel, newGameButton, exitButton);
        centerBox.setAlignment(Pos.CENTER);

        setCenter(centerBox);
        setPadding(new Insets(20));
    }

    private void registerHandlers() {
        newGameButton.setOnAction(event -> handleNewGame());
        exitButton.setOnAction(event -> handleExit());
    }

    private void handleNewGame() {
        controller.showConfig();
    }

    private void handleExit() {
        controller.quitApp();
    }
}
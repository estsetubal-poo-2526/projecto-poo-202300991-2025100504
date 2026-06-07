package tictactoe.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MenuView extends BorderPane {

    private Button newGameButton;
    private Button exitButton;

    public MenuView() {

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

        VBox centerBox =
                new VBox(
                        20,
                        titleLabel,
                        newGameButton,
                        exitButton
                );

        centerBox.setAlignment(Pos.CENTER);

        setCenter(centerBox);

        setPadding(new Insets(20));

    }

    private void registerHandlers() {

        newGameButton.setOnAction(
                event -> handleNewGame()
        );

        exitButton.setOnAction(
                event -> handleExit()
        );

    }

    private void handleNewGame() {

        System.out.println("Novo jogo selecionado.");

    }

    private void handleExit() {

        System.exit(0);

    }

}
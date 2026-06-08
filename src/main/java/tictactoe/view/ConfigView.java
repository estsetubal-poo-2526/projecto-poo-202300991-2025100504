package tictactoe.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tictactoe.controller.AppController;

public class ConfigView extends BorderPane {

    private TextField player1Input;
    private TextField player2Input;
    private Button startButton;
    private Button backButton;
    private AppController controller;

    public ConfigView(AppController controller) {
        this.controller = controller;

        createControls();
        organizeLayout();
        registerHandlers();
    }

    private void createControls() {
        player1Input = new TextField("Jogador 1");
        player2Input = new TextField("Jogador 2");

        player1Input.setMaxWidth(200);
        player2Input.setMaxWidth(200);

        startButton = new Button("Começar Jogo");
        backButton = new Button("Voltar");

        startButton.setPrefWidth(150);
        backButton.setPrefWidth(150);
    }

    private void organizeLayout() {
        Label titleLabel = new Label("Configuração dos Jogadores");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        VBox p1Box = new VBox(5, new Label("Nome do Jogador 1 (X):"), player1Input);
        p1Box.setAlignment(Pos.CENTER);

        VBox p2Box = new VBox(5, new Label("Nome do Jogador 2 (O):"), player2Input);
        p2Box.setAlignment(Pos.CENTER);

        HBox buttonsBox = new HBox(20, startButton, backButton);
        buttonsBox.setAlignment(Pos.CENTER);

        VBox centerBox = new VBox(30, titleLabel, p1Box, p2Box, buttonsBox);
        centerBox.setAlignment(Pos.CENTER);

        this.setCenter(centerBox);
        this.setPadding(new Insets(20));
    }

    private void registerHandlers() {
        backButton.setOnAction(event -> controller.showMenu());
        startButton.setOnAction(event -> handleStartGame());
    }

    private void handleStartGame() {
        String p1Name = player1Input.getText().trim().isEmpty() ? "Jogador 1" : player1Input.getText().trim();
        String p2Name = player2Input.getText().trim().isEmpty() ? "Jogador 2" : player2Input.getText().trim();

        controller.showGame(p1Name, p2Name);
    }
}
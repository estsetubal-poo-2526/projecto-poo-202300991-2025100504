package tictactoe.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import tictactoe.controller.AppController;

public class ResultView extends BorderPane {

    private final AppController controller;
    private final String resultMessage;

    public ResultView(AppController controller, String resultMessage) {
        this.controller = controller;
        this.resultMessage = resultMessage;

        this.setStyle("-fx-background-color: transparent;");
        this.setPadding(new Insets(30));

        createView();
    }

    private void createView() {
        Label trophyLabel = new Label("\uD83C\uDFC6");
        trophyLabel.setStyle("-fx-font-size: 120px;");

        Label mainResultLabel = new Label(resultMessage);
        mainResultLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: white; -fx-alignment: center;");

        Label congratsLabel = new Label(resultMessage.contains("Empate") ? "Bom jogo! 🤝" : "Parabéns! 🎉");
        congratsLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #94a3b8;");

        Button playAgainButton = new Button("⟳  Jogar Novamente");
        playAgainButton.setStyle(
                "-fx-background-color: linear-gradient(to right, #0077b6, #00b4d8);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 15px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 12px 0;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-cursor: hand;"
        );
        playAgainButton.setPrefWidth(220);
        playAgainButton.setOnAction(event -> controller.showConfig());

        Button menuButton = new Button("🏠  Menu");
        menuButton.setStyle(
                "-fx-background-color: #0f1a2c;" +
                        "-fx-border-color: #334155;" +
                        "-fx-border-radius: 8px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 15px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 12px 0;" +
                        "-fx-cursor: hand;"
        );
        menuButton.setPrefWidth(220);
        menuButton.setOnAction(event -> controller.showMenu());

        
        VBox buttonsBox = new VBox(12, playAgainButton, menuButton);
        buttonsBox.setAlignment(Pos.CENTER);

        
        boolean isTie = resultMessage.contains("Empate");
        VBox layout = isTie
                ? new VBox(25, mainResultLabel, congratsLabel, buttonsBox)
                : new VBox(25, trophyLabel, mainResultLabel, congratsLabel, buttonsBox);
        layout.setAlignment(Pos.CENTER);

        this.setCenter(layout);
    }
}

package tictactoe.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tictactoe.controller.AppController;

public class ResultView extends BorderPane {

    private final AppController controller;
    private final String resultMessage;

    private Label titleLabel;
    private Label messageLabel;
    private Button playAgainButton;
    private Button menuButton;

    public ResultView(AppController controller, String resultMessage) {
        this.controller = controller;
        this.resultMessage = resultMessage;

        createControls();
        organizeLayout();
        registerHandlers();
    }

    private void createControls() {
        titleLabel = new Label("Fim de Jogo!");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        messageLabel = new Label(resultMessage);
        messageLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #34495e;");

        playAgainButton = new Button("Jogar Novamente");
        menuButton = new Button("Voltar ao Menu");

        playAgainButton.setPrefWidth(160);
        menuButton.setPrefWidth(160);
    }

    private void organizeLayout() {
        HBox buttonsBox = new HBox(20, playAgainButton, menuButton);
        buttonsBox.setAlignment(Pos.CENTER);

        VBox centerBox = new VBox(30, titleLabel, messageLabel, buttonsBox);
        centerBox.setAlignment(Pos.CENTER);

        this.setCenter(centerBox);
        this.setPadding(new Insets(20));
    }

    private void registerHandlers() {
        playAgainButton.setOnAction(event -> controller.showConfig());

        menuButton.setOnAction(event -> controller.showMenu());
    }
}
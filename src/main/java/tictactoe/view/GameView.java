package tictactoe.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tictactoe.controller.AppController;
import tictactoe.model.Game;
import tictactoe.model.GameState;
import tictactoe.model.InvalidMoveException;
import tictactoe.model.Symbol;

public class GameView extends BorderPane {

    private final AppController controller;
    private final Game gameModel;

    // Matriz de 3x3 botões para o tabuleiro
    private final Button[][] boardButtons = new Button[3][3];
    private Label turnLabel;
    private Button backButton;

    public GameView(AppController controller, Game gameModel) {
        this.controller = controller;
        this.gameModel = gameModel;

        createControls();
        organizeLayout();
        registerHandlers();
        updateTurnLabel();
    }

    private void createControls() {
        turnLabel = new Label();
        turnLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Criar a matriz 3x3 de botões
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button btn = new Button("");
                btn.setPrefSize(90, 90);
                btn.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-focus-color: transparent;");

                btn.getProperties().put("row", row);
                btn.getProperties().put("col", col);

                boardButtons[row][col] = btn;
            }
        }

        backButton = new Button("Voltar ao Menu");
        backButton.setPrefWidth(150);
    }

    private void organizeLayout() {
        HBox topBox = new HBox(turnLabel);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(15, 0, 10, 0));
        setTop(topBox);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10); // Espaçamento horizontal entre botões
        gridPane.setVgap(10); // Espaçamento vertical entre botões

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                gridPane.add(boardButtons[row][col], col, row);
            }
        }
        setCenter(gridPane);

        HBox bottomBox = new HBox(backButton);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(15, 0, 15, 0));
        setBottom(bottomBox);

        setPadding(new Insets(10));
    }

    private void registerHandlers() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button btn = boardButtons[row][col];
                btn.setOnAction(event -> handleCellClick(btn));
            }
        }

        backButton.setOnAction(event -> controller.showMenu());
    }

    private void handleCellClick(Button clickedButton) {
        int row = (int) clickedButton.getProperties().get("row");
        int col = (int) clickedButton.getProperties().get("col");

        try {
            Symbol currentSymbol = gameModel.getCurrentPlayer().getSymbol();
            gameModel.playMove(row, col);
            clickedButton.setText(currentSymbol.toString());
            clickedButton.setDisable(true);
            if (currentSymbol == Symbol.X) {
                clickedButton.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2980b9;");
            } else {
                clickedButton.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");
            }
            if (gameModel.isFinished()) {
                handleGameEnd();
            } else {
                updateTurnLabel();
            }

        } catch (InvalidMoveException e) {
            System.out.println("Movimento inválido: " + e.getMessage());
        }
    }
    private void updateTurnLabel() {
        turnLabel.setText("Jogador: " + gameModel.getCurrentPlayer().getName());
    }

    private void handleGameEnd() {
        String message;
        if (gameModel.getState() == GameState.WIN) {
            message = "Parabéns! O vencedor é " + gameModel.getWinner().getName() + " (" + gameModel.getWinner().getSymbol() + ")!";
        } else {
            message = "O jogo terminou! Foi um empate!";
        }
        controller.showResult(message);
    }
}
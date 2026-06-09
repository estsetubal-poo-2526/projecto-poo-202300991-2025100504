package tictactoe.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Circle;
import tictactoe.controller.AppController;
import tictactoe.model.Game;
import tictactoe.model.GameState;
import tictactoe.model.InvalidMoveException;
import tictactoe.model.Symbol;

public class GameView extends StackPane {

    private final AppController controller;
    private final Game gameModel;
    private final Button[][] boardButtons = new Button[3][3];
    private Label turnLabel;
    private Button backButton;
    private Button restartButton;
    private boolean handlersRegistered = false;
    private AudioClip popSound;

    public GameView(AppController controller, Game gameModel) {
        this.controller = controller;
        this.gameModel = gameModel;

        this.setStyle("-fx-background-color: transparent;");
        this.setPadding(new Insets(20));

        loadPopSound();
        createControls();
        organizeLayout();
        updateTurnLabel();
    }

    private void loadPopSound() {
        try {
            java.net.URL url = getClass().getResource("/resources/sounds/pop.wav");
            if (url != null) {
                popSound = new AudioClip(url.toExternalForm());
                popSound.setVolume(0.55);
            }
        } catch (Exception ignored) {
        }
    }

        private void createControls() {
            turnLabel = new Label();
            turnLabel.setStyle(
                    "-fx-background-color: #0a1220;" +
                            "-fx-text-fill: #3b82f6;" +
                            "-fx-padding: 8px 22px;" +
                            "-fx-background-radius: 22px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-font-size: 14px;" +
                            "-fx-border-color: #1e3a5f;" +
                            "-fx-border-radius: 22px;"
            );

            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    Button btn = new Button("");
                    btn.setPrefSize(95, 95);
                    btn.setStyle(
                            "-fx-background-color: #0a1220;" +
                                    "-fx-border-color: #1e3a5f;" +
                                    "-fx-border-width: 1.5px;" +
                                    "-fx-border-radius: 6px;" +
                                    "-fx-background-radius: 6px;" +
                                    "-fx-font-size: 38px;" +
                                    "-fx-font-weight: 900;" +
                                    "-fx-cursor: hand;"
                    );
                    btn.getProperties().put("row", row);
                    btn.getProperties().put("col", col);
                    boardButtons[row][col] = btn;
                }
            }

            restartButton = buildSecondaryBtn("⟳   Reiniciar");
            backButton = buildSecondaryBtn("←   Voltar");
        }

        private Button buildSecondaryBtn(String text) {
            Button b = new Button(text);
            b.setStyle(
                    "-fx-background-color: #0a1220;" +
                            "-fx-border-color: #1e3a5f;" +
                            "-fx-border-radius: 8px;" +
                            "-fx-background-radius: 8px;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-font-size: 13px;" +
                            "-fx-padding: 10px 22px;" +
                            "-fx-cursor: hand;"
            );
            return b;
        }

        private void organizeLayout() {
            

            VBox topBox = new VBox(turnLabel);
            topBox.setAlignment(Pos.TOP_CENTER);
            topBox.setPadding(new Insets(10, 0, 20, 0));

            GridPane gridPane = new GridPane();
            gridPane.setHgap(6);
            gridPane.setVgap(6);
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setPadding(new Insets(8));
            gridPane.setStyle("-fx-background-color: transparent;");
            for (int row = 0; row < 3; row++)
                for (int col = 0; col < 3; col++)
                    gridPane.add(boardButtons[row][col], col, row);

            HBox bottomBox = new HBox(20, restartButton, backButton);
            bottomBox.setAlignment(Pos.CENTER);
            bottomBox.setPadding(new Insets(20, 0, 10, 0));

            VBox main = new VBox(15, topBox, gridPane, bottomBox);
            main.setAlignment(Pos.CENTER);

            this.getChildren().add(main);
        }

        private Pane buildDecorations() {
            Pane p = new Pane();
            p.setMouseTransparent(true);
            addSym(p, "X", 40, 100, 50);
            addSym(p, "O", 80, 320, 38);
            addSym(p, "X", 600, 80, 52);
            addSym(p, "X", 640, 380, 48);
            for (int i = 0; i < 8; i++) {
                Circle c = new Circle(2, Color.web("#3b82f6", 0.35));
                c.setLayoutX(60 + (i * 79) % 600);
                c.setLayoutY(60 + (i * 61) % 400);
                p.getChildren().add(c);
            }
            return p;
        }

        private void addSym(Pane p, String s, double x, double y, double size) {
            Label l = new Label(s);
            l.setStyle("-fx-font-size: " + size + "px; -fx-font-weight: 900; -fx-text-fill: #1e3a5f;");
            l.setLayoutX(x);
            l.setLayoutY(y);
            l.setOpacity(0.5);
            p.getChildren().add(l);
        }

        private void registerHandlers() {
            for (int row = 0; row < 3; row++)
                for (int col = 0; col < 3; col++) {
                    Button btn = boardButtons[row][col];
                    btn.setOnAction(event -> handleCellClick(btn));
                }
            backButton.setOnAction(event -> controller.showMenu());
            restartButton.setOnAction(event -> handleRestart());
        }

        @Override
        protected void layoutChildren() {
            super.layoutChildren();
            if (!handlersRegistered) { registerHandlers(); handlersRegistered = true; }
        }

        private void handleCellClick(Button clickedButton) {
            int row = (int) clickedButton.getProperties().get("row");
            int col = (int) clickedButton.getProperties().get("col");
            try {
                Symbol currentSymbol = gameModel.getCurrentPlayer().getSymbol();
                gameModel.playMove(row, col);
                if (popSound != null) popSound.play();
                clickedButton.setText(currentSymbol.toString());
                clickedButton.setDisable(true);
                String color = currentSymbol == Symbol.X ? "#3b82f6" : "#06d6a0";
                clickedButton.setStyle(
                        "-fx-background-color: #0a1220;" +
                                "-fx-border-color: #1e3a5f;" +
                                "-fx-border-width: 1.5px;" +
                                "-fx-border-radius: 6px;" +
                                "-fx-background-radius: 6px;" +
                                "-fx-text-fill: " + color + ";" +
                                "-fx-font-size: 38px;" +
                                "-fx-font-weight: 900;" +
                                "-fx-opacity: 1.0;"
                );
                if (gameModel.isFinished()) handleGameEnd(); else updateTurnLabel();
            } catch (InvalidMoveException e) {
                System.out.println("Movimento inválido.");
            }
        }

        private void handleRestart() {
            gameModel.start();
            for (int row = 0; row < 3; row++)
                for (int col = 0; col < 3; col++) {
                    Button btn = boardButtons[row][col];
                    btn.setText("");
                    btn.setDisable(false);
                    btn.setStyle(
                            "-fx-background-color: #0a1220;" +
                                    "-fx-border-color: #1e3a5f;" +
                                    "-fx-border-width: 1.5px;" +
                                    "-fx-border-radius: 6px;" +
                                    "-fx-background-radius: 6px;" +
                                    "-fx-font-size: 38px;" +
                                    "-fx-font-weight: 900;" +
                                    "-fx-cursor: hand;"
                    );
                }
            updateTurnLabel();
        }

        private void updateTurnLabel() {
            turnLabel.setText("👤  Jogador atual: " + gameModel.getCurrentPlayer().getSymbol());
        }

        private void handleGameEnd() {
            String message = gameModel.getState() == GameState.WIN
                    ? "Vencedor: " + gameModel.getWinner().getName()
                    : "O jogo terminou em Empate!";
            controller.showResult(message);
        }
    }

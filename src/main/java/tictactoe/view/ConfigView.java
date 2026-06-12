package tictactoe.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import tictactoe.controller.AppController;

public class ConfigView extends BorderPane {

    private final AppController controller;
    private TextField playerXInput;
    private TextField playerOInput;
    private HBox playerXInputBox;
    private HBox playerOInputBox;

    public ConfigView(AppController controller) {
        this.controller = controller;
        this.setStyle("-fx-background-color: transparent;");
        this.setPadding(new Insets(40));
        createView();
    }

    private void createView() {
        Label gearIcon = new Label("⚙");
        gearIcon.setStyle("-fx-font-size: 28px; -fx-text-fill: #3b82f6; -fx-background-color: #0a1220; -fx-background-radius: 50%; -fx-padding: 8px 12px; -fx-border-color: #1e3a5f; -fx-border-radius: 50%;");
        gearIcon.setEffect(new DropShadow(10, Color.web("#3b82f6", 0.6)));

        Label title = new Label("Configurações");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: white;");

        HBox header = new HBox(15, gearIcon, title);
        header.setAlignment(Pos.CENTER);


        VBox card = new VBox(18);
        card.setPadding(new Insets(25, 30, 25, 30));
        card.setStyle(
                "-fx-background-color: rgba(15, 26, 44, 0.6);" +
                        "-fx-background-radius: 14px;" +
                        "-fx-border-color: #1e3a5f;" +
                        "-fx-border-radius: 14px;" +
                        "-fx-border-width: 1px;"
        );
        card.setMaxWidth(520);

        HBox rowX = buildRow("Jogador X", "", true);
        HBox rowO = buildRow("Jogador O", "", false);

        Region sep = new Region();
        sep.setPrefHeight(1);
        sep.setStyle("-fx-background-color: #1e3a5f;");

        Button startBtn = new Button("▶   Começar");
        startBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #3b82f6, #1d4ed8);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 12px 0;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-cursor: hand;"
        );
        startBtn.setMaxWidth(Double.MAX_VALUE);
        startBtn.setEffect(new DropShadow(20, Color.web("#3b82f6", 0.6)));
        Label errorLabel = new Label();
        errorLabel.setStyle(
                "-fx-text-fill: #ef4444;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;"
        );
        errorLabel.setMaxWidth(Double.MAX_VALUE);
        errorLabel.setAlignment(Pos.CENTER);
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        startBtn.setOnAction(e -> {
            String nameX = playerXInput.getText().trim();
            String nameO = playerOInput.getText().trim();

            String normalBoxStyle =
                    "-fx-background-color: #0a1220;" +
                            "-fx-background-radius: 8px;" +
                            "-fx-border-color: #1e3a5f;" +
                            "-fx-border-radius: 8px;" +
                            "-fx-border-width: 1.5px;" +
                            "-fx-padding: 0 10px;";

            String errorBoxStyle =
                    "-fx-background-color: #0a1220;" +
                            "-fx-background-radius: 8px;" +
                            "-fx-border-color: #ef4444;" +
                            "-fx-border-radius: 8px;" +
                            "-fx-border-width: 2px;" +
                            "-fx-padding: 0 10px;";

            playerXInputBox.setStyle(normalBoxStyle);
            playerOInputBox.setStyle(normalBoxStyle);

            if (nameX.isEmpty() || nameO.isEmpty()) {
                errorLabel.setText("Preencha o nome dos dois jogadores.");
                errorLabel.setVisible(true);
                errorLabel.setManaged(true);

                if (nameX.isEmpty()) {
                    playerXInputBox.setStyle(errorBoxStyle);
                }

                if (nameO.isEmpty()) {
                    playerOInputBox.setStyle(errorBoxStyle);
                }

                return;
            }

            if (nameX.equalsIgnoreCase(nameO)) {
                errorLabel.setText("Os nomes dos jogadores devem ser diferentes.");
                errorLabel.setVisible(true);
                errorLabel.setManaged(true);

                playerXInputBox.setStyle(errorBoxStyle);
                playerOInputBox.setStyle(errorBoxStyle);

                return;
            }

            errorLabel.setVisible(false);
            errorLabel.setManaged(false);

            controller.showGame(nameX, nameO);
        });

        card.getChildren().addAll(rowX, rowO, sep, errorLabel, startBtn);

        Label footer = new Label("ⓘ  Digite os nomes dos jogadores para começar.");
        footer.setStyle("-fx-text-fill: #64748b; -fx-font-size: 12px;");

        VBox content = new VBox(28, header, card, footer);
        content.setAlignment(Pos.CENTER);
        this.setCenter(content);
    }

    private HBox buildRow(String labelText, String defaultValue, boolean isX) {
        Label icon = new Label("👤");
        icon.setStyle("-fx-font-size: 18px; -fx-text-fill: #3b82f6; -fx-background-color: #0a1220; -fx-background-radius: 50%; -fx-padding: 6px 9px; -fx-border-color: #1e3a5f; -fx-border-radius: 50%;");

        Label label = new Label(labelText);
        label.setStyle("-fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; -fx-min-width: 110px;");

        TextField input = new TextField(defaultValue);
        input.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-padding: 10px 0;" +
                        "-fx-border-color: transparent;" +
                        "-fx-border-width: 0;"
        );
        HBox.setHgrow(input, Priority.ALWAYS);

        Label userIco = new Label("👤");
        userIco.setStyle("-fx-text-fill: #475569; -fx-font-size: 13px;");
        HBox inputBox = new HBox(8, userIco, input);
        inputBox.setAlignment(Pos.CENTER_LEFT);
        inputBox.setStyle(
                "-fx-background-color: #0a1220;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-border-color: #1e3a5f;" +
                        "-fx-border-radius: 8px;" +
                        "-fx-border-width: 1.5px;" +
                        "-fx-padding: 0 10px;"
        );

        HBox.setHgrow(inputBox, Priority.ALWAYS);
        input.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-padding: 10px 0;" +
                        "-fx-border-color: transparent;" +
                        "-fx-border-width: 0;"
        );

        if (isX) {
            playerXInput = input;
            playerXInputBox = inputBox;
        } else {
            playerOInput = input;
            playerOInputBox = inputBox;
        }

        HBox row = new HBox(15, icon, label, inputBox);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }
}

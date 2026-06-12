package tictactoe.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import tictactoe.controller.AppController;

public class MenuView extends StackPane {

    private final AppController controller;

    public MenuView(AppController controller) {
        this.controller = controller;
        this.setStyle("-fx-background-color: transparent;");
        this.setPadding(new Insets(20));
        buildView();
    }

    private void buildView() {
        

        
        VBox logoBox = buildLogo();

        
        HBox titleBox = new HBox(0);
        titleBox.setAlignment(Pos.CENTER);
        Label part1 = new Label("Tic Tac ");
        part1.setStyle("-fx-font-size: 64px; -fx-font-weight: 900; -fx-text-fill: white;");
        Label part2 = new Label("Toe");
        part2.setStyle("-fx-font-size: 64px; -fx-font-weight: 900; -fx-text-fill: #3b82f6;");
        part2.setEffect(new DropShadow(20, Color.web("#3b82f6", 0.6)));
        titleBox.getChildren().addAll(part1, part2);

        
        Label subtitle = new Label("\u2605  Cl\u00e1ssico  \u00b7  Simples  \u00b7  Divertido  \u2605");
        subtitle.setStyle("-fx-font-size: 15px; -fx-text-fill: #94a3b8; -fx-font-style: italic;");

        
        Button startBtn = new Button("\u25b6   Iniciar Jogo");
        startBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #3b82f6, #1d4ed8);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 14px 50px;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-cursor: hand;"
        );
        startBtn.setEffect(new DropShadow(25, Color.web("#3b82f6", 0.7)));
        startBtn.setOnAction(e -> controller.showConfig());

        VBox content = new VBox(22, logoBox, titleBox, subtitle, startBtn);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(30));

        this.getChildren().add(content);
    }

    private VBox buildLogo() {
        ImageView logoView = new ImageView();
        try {
            Image img = new Image(
                    getClass().getResourceAsStream("/resources/images/tictactoe_logo.png")
            );
            logoView.setImage(img);
        } catch (Exception ignored) {
            
        }
        logoView.setFitWidth(160);
        logoView.setFitHeight(160);
        logoView.setPreserveRatio(true);
        logoView.setSmooth(true);
        logoView.setEffect(new DropShadow(35, Color.web("#3b82f6", 0.6)));

        VBox box = new VBox(logoView);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private Pane buildDecorations() {
        Pane p = new Pane();
        p.setMouseTransparent(true);
        addDecorSymbol(p, "X", 60, 80, 48, "#1e3a5f", 0.5);
        addDecorSymbol(p, "O", 160, 200, 36, "#1e3a5f", 0.4);
        addDecorSymbol(p, "X", 100, 380, 56, "#1e3a5f", 0.45);
        addDecorSymbol(p, "X", 520, 90, 50, "#1e3a5f", 0.5);
        addDecorSymbol(p, "O", 600, 280, 42, "#1e3a5f", 0.4);
        addDecorSymbol(p, "X", 680, 420, 60, "#1e3a5f", 0.45);
        addDecorSymbol(p, "O", 300, 60, 30, "#1e3a5f", 0.35);
        for (int i = 0; i < 12; i++) {
            Circle dot = new Circle(2.5, Color.web("#3b82f6", 0.4));
            dot.setLayoutX(40 + (i * 67) % 720);
            dot.setLayoutY(50 + (i * 53) % 460);
            p.getChildren().add(dot);
        }
        return p;
    }

    private void addDecorSymbol(Pane p, String s, double x, double y, double size, String color, double opacity) {
        Label l = new Label(s);
        l.setStyle("-fx-font-size: " + size + "px; -fx-font-weight: 900; -fx-text-fill: " + color + ";");
        l.setLayoutX(x);
        l.setLayoutY(y);
        l.setOpacity(opacity);
        p.getChildren().add(l);
    }
}

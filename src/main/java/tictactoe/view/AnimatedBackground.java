package tictactoe.view;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class AnimatedBackground extends Pane {

    private final Canvas canvas = new Canvas(800, 800);
    private final List<Particle> particles = new ArrayList<>();
    private final Random rnd = new Random(42);
    private AnimationTimer timer;

    private static final int PARTICLE_COUNT = 110;
    private static final double FOCAL = 320.0;
    private static final double SPEED = 0.9;

    public AnimatedBackground() {
        getChildren().add(canvas);
        setMouseTransparent(true);
        canvas.widthProperty().bind(widthProperty());
        canvas.heightProperty().bind(heightProperty());

        for (int i = 0; i < PARTICLE_COUNT; i++) particles.add(newParticle(true));

        timer = new AnimationTimer() {
            @Override public void handle(long now) { render(); }
        };
        timer.start();
    }

    private Particle newParticle(boolean initial) {
        Particle p = new Particle();
        p.x = (rnd.nextDouble() - 0.5) * 800;
        p.y = (rnd.nextDouble() - 0.5) * 800;
        
        p.z = initial ? rnd.nextDouble() * 900 + 50 : 900;
        int r = rnd.nextInt(10);
        if (r < 4) p.kind = 'X';
        else if (r < 8) p.kind = 'O';
        else p.kind = '.';
        p.rot = rnd.nextDouble() * 360;
        p.rotSpeed = (rnd.nextDouble() - 0.5) * 1.2;
        p.baseSize = 28 + rnd.nextDouble() * 30;
        
        p.color = rnd.nextInt(5) == 0 ? Color.web("#06d6a0") : Color.web("#3b82f6");
        return p;
    }

    private void render() {
        double w = canvas.getWidth();
        double h = canvas.getHeight();
        GraphicsContext g = canvas.getGraphicsContext2D();

        
        LinearGradient bg = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#050b14")),
                new Stop(0.5, Color.web("#0a1628")),
                new Stop(1, Color.web("#0d1b2a")));
        g.setFill(bg);
        g.fillRect(0, 0, w, h);

        double cx = w / 2.0;
        double cy = h / 2.0;
        g.setTextAlign(TextAlignment.CENTER);

        for (Particle p : particles) {
            p.z -= SPEED * 4.0;
            p.rot += p.rotSpeed;
            if (p.z < 1) {
                
                p.x = (rnd.nextDouble() - 0.5) * 800;
                p.y = (rnd.nextDouble() - 0.5) * 800;
                p.z = 900;
                p.rot = rnd.nextDouble() * 360;
            }
            double scale = FOCAL / p.z;
            double sx = cx + p.x * scale;
            double sy = cy + p.y * scale;
            if (sx < -80 || sx > w + 80 || sy < -80 || sy > h + 80) continue;

            
            double prevScale = FOCAL / (p.z + SPEED * 14);
            double psx = cx + p.x * prevScale;
            double psy = cy + p.y * prevScale;

            double depth = Math.max(0, Math.min(1, 1 - p.z / 900.0));
            double alpha = 0.15 + depth * 0.55;

            if (p.kind == '.') {
                double r = 1.2 + depth * 2.4;
                g.setStroke(p.color.deriveColor(0, 1, 1, alpha * 0.6));
                g.setLineWidth(r * 0.7);
                g.strokeLine(psx, psy, sx, sy);
                g.setFill(p.color.deriveColor(0, 1, 1, alpha));
                g.fillOval(sx - r, sy - r, r * 2, r * 2);
            } else {
                double size = p.baseSize * scale;
                if (size < 4) continue;
                
                g.setStroke(p.color.deriveColor(0, 1, 1, alpha * 0.18));
                g.setLineWidth(Math.max(1, size * 0.08));
                g.strokeLine(psx, psy, sx, sy);

                g.save();
                g.translate(sx, sy);
                g.rotate(p.rot);
                g.setFill(p.color.deriveColor(0, 1, 1, alpha));
                g.setFont(Font.font("System", FontWeight.BLACK, size));
                g.fillText(String.valueOf(p.kind), 0, size * 0.35);
                g.restore();
            }
        }
    }

    public void stop() { if (timer != null) timer.stop(); }

    private static class Particle {
        double x, y, z;
        double rot, rotSpeed;
        double baseSize;
        char kind;
        Color color;
    }
}

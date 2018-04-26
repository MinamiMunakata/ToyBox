package sample;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball {
    private Canvas canvas;
    private int ballPosX;
    private int ballPosY;
    private int ballXDir;
    private int ballYDir;
    private final int BALL_SIZE = 20;
    private final long startNanoTIme = System.nanoTime();

    public Ball(Canvas canvas) {
        this.ballPosX = 120;
        this.ballPosY = 350;
        this.ballXDir = -1;
        this.ballYDir = -2;
        this.canvas = canvas;
    }

    public void creatBall(GraphicsContext g) {
        g.setFill(Color.rgb(255, 5, 100));
        g.fillOval(ballPosX, ballPosY, BALL_SIZE, BALL_SIZE);
    }

    public void moveBall(GraphicsContext g) {
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                if (Controller.canPlay) {
                    System.out.println("Hey!");
                    ballPosX += ballXDir;
                    ballPosY += ballYDir;
                    g.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
                    creatBall(g);
                }
            }
        }.start();
    }
}

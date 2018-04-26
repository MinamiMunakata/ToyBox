package sample;

import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Controller {
    private final double WIDTH = 710;
    private final double HEIGHT = 600;
    public static final Group group = new Group();
    private final Canvas canvas;
    public static AnimationTimer timer;
    private GraphicsContext g;
    private int playerX = 310;
    private final int PLAYER_Y = 550;
    private int ballPosX = 120;
    private int ballPosY = 350;
    private final double BALL_SIZE = 20;
    private double ballXDir = -1;
    private double ballYDir = -2;
    private int lengthOfPaddle = 100;
    private final int HEIGHT_OF_PADDLE = 8;
    public static boolean canPlay = false;
    private int score = 0;
    private int totalBricks = 50;
    Brick brick = new Brick(5,10);

    public Controller( ) {
//        this.group = new Group();
        this.canvas = new Canvas(WIDTH,HEIGHT);
        group.getChildren().add(canvas);
//        this.g =canvas.getGraphicsContext2D();
//        ballPos_X.set(300);
//        ballPos_Y.set(300);
    }

    public void gamePlay() {
//        moveBall();
        animation();
        timer.start();
//        timeline.play();
    }

    public void draw() {
        g = canvas.getGraphicsContext2D();
        // background
        g.setFill(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // bricks
        brick.drawBricks(g);

        // paddle
        g.setFill(Color.rgb(2,0,86));
        g.fillRect(playerX,PLAYER_Y,lengthOfPaddle,HEIGHT_OF_PADDLE);
        // ball
//        createBall(g);
        g.setFill(Color.rgb(255,5,100));
        g.fillOval(ballPosX,ballPosY,BALL_SIZE,BALL_SIZE);
//        moveBall();
    }

//    public void moveBall() {
//        timeline = new Timeline(
//                new KeyFrame(Duration.seconds(0),
//                        new KeyValue(ballPos_Y, 0),
//                        new KeyValue(ballPos_Y, 0)
//                ),
//                new KeyFrame(Duration.seconds(10),
//                        new KeyValue(ballPos_X, WIDTH),
//                        new KeyValue(ballPos_Y, HEIGHT)
//                )
//        );
//        timeline.setAutoReverse(true);
//        timeline.setCycleCount(Timeline.INDEFINITE);
//    }

    public void animation() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                GraphicsContext g = canvas.getGraphicsContext2D();
                   if (Controller.canPlay) {
                            ballPosX += ballXDir;
                            ballPosY += ballYDir;
                            whenBallHitsPaddle();
                            whenBallHitsWall();
                            whenBallHitsBricks();
//                            g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                            draw();
                   }
            }
        };
    }

    private void whenBallHitsPaddle() {
        if (new Rectangle(ballPosX, ballPosY, BALL_SIZE, BALL_SIZE).getBoundsInParent().intersects(new Rectangle(playerX, 550, lengthOfPaddle, HEIGHT_OF_PADDLE).getBoundsInParent())) {
            ballYDir = -ballYDir;
        }
    }
    private void whenBallHitsWall() {
        if (ballPosX < 0 || ballPosX > 690) {
            ballXDir = -ballXDir;
        }
        if (ballPosY < 0) {
            ballYDir = -ballYDir;
        }
    }

    private void whenBallHitsBricks() {
        for (int row = 0; row < brick.bricks.length ; row++) {
            for (int col = 0; col < brick.bricks[0].length; col++) {
                if (brick.bricks[row][col] > 0) {
                    int brickX = col * brick.getBrickWidth() + 80;
                    int brickY = row * brick.getBrickHeight() + 50;
                    Rectangle brickRect = new Rectangle(brickX, brickY, brick.getBrickWidth(), brick.getBrickHeight());
                    Rectangle ballRect = new Rectangle(ballPosX, ballPosY, BALL_SIZE, BALL_SIZE);
                    if (ballRect.getBoundsInParent().intersects(brickRect.getBoundsInParent())) {
                        if (brick.bricks[row][col] == 2) {
                            brick.setBrickValue(0, row, col);
                            totalBricks--;
                            score += 10;
//                            hitDoublePoint = true;
                        } else {
                            brick.setBrickValue(0, row, col);
                            totalBricks--;
                            score += 5;
                        }
                        if (ballPosX + 19 <= brickRect.getX() || ballPosX + 1 >= brickX + brick.getBrickWidth()) {
                            ballXDir = -ballXDir;
                        } else {
                            ballYDir = -ballYDir;
                        }
                        // change the length of the paddle
                        if (totalBricks % 10 == 0 && totalBricks != 0) {
                            lengthOfPaddle -= 4;
                        }
                        break;
                    }
                }
            }
        }
    }

    public void setEventHandler(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.LEFT) {
                if (playerX < 10) {
                    playerX = 10;
                } else {
                    moveLeft();
                }
            }
            if (event.getCode() == KeyCode.RIGHT) {
                if (playerX >= 610) {
                    playerX = 600;
                } else {
                    moveRight();
                }
            }
//            g.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
//            draw();
        });
    }

//    private void gameOver() {
//        if (ballPos_Y < HEIGHT - BALL_SIZE) {
//            canPlay = false;
//        }
//    }

    public void moveRight() {
        canPlay = true;
        playerX += 20;
    }

    public void moveLeft() {
        canPlay = true;
        playerX -= 20;
    }
}

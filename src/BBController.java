import javafx.animation.AnimationTimer;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 * @author Minami Munakata
 */

public class BBController {
    // player's info
    private String name;
    private int score = 0;
    private String date;
    // basic info
    private final Group group = new Group();
    private final Canvas canvas;
    private GraphicsContext g;
    private final double WIDTH = 710;
    private final double HEIGHT = 600;
    private static AnimationTimer timer;
    // info of paddle
    private int playerX = 310;
    private final int PLAYER_Y = 580;
    private int lengthOfPaddle = 100;
    private final int HEIGHT_OF_PADDLE = 8;
    // info about a ball
    private int ballPosX = 120;
    private int ballPosY = 350;
    private double ballXDir = -1;
    private double ballYDir = -2;
    private final double BALL_SIZE = 20;
    // flags for count time
    private int moment;
    private int momentForDoublePoint;
    private long highSpeedTimeMillis;
    private long currentTimeMillis;
    private boolean hitDoublePoint;
    private long hitDoubleTimeMillis;
    // other config
    public static boolean canPlay = false;
    private int totalBricks = 50;
    private Brick brick = new Brick(5,10);
    public static boolean highSpeed;
    public static final Color DARK_BLUE = Color.rgb(2, 0, 86);

    /**
     * Constructor
     * create a canvas and set into a group
     */
    public BBController(String name, String date) {
        this.canvas = new Canvas(WIDTH,HEIGHT);
        group.getChildren().add(canvas);
        this.name = name;
        this.date = date;
    }

    /**
     * start animation timer
     */
    public void gamePlay() {
        animation();
        timer.start();
    }

    /**
     * Draw background, bricks, scores, a paddle, a ball, and some messages on the canvas.
     */
    public void draw() {
        g = canvas.getGraphicsContext2D();
        final Color MY_YELLOW = Color.rgb(255,200,0);
        final Color MY_SKY_BLUE = Color.rgb(70, 220,255);

        // background
        if (highSpeed) {
            g.setFill(DARK_BLUE);
            g.fillRect(0,0,WIDTH,HEIGHT);
        } else {
            g.setFill(DARK_BLUE);
            g.fillRect(0,0,WIDTH,HEIGHT);
            g.setFill(Color.WHITE);
            g.fillRect(2,2,706,600);
        }

        // bricks
        brick.drawBricks(g);

        // scores
        if (highSpeed) {
            g.setFill(Color.WHITE);
        }else {
            g.setFill(DARK_BLUE);
        }
        g.setFont(Font.font("serif", FontWeight.BOLD, 30));
        g.fillText("" + score,600,35); // casting to string from int


        // paddle
        if (highSpeed) {
            g.setFill(Color.WHITE);
        } else {
            g.setFill(DARK_BLUE);
        }
        g.fillRect(playerX,PLAYER_Y,lengthOfPaddle,HEIGHT_OF_PADDLE);

        // ball
        g.setFill(Color.rgb(255,5,100));
        g.fillOval(ballPosX,ballPosY,BALL_SIZE,BALL_SIZE);
        g.setFill(MY_YELLOW);
        // show some message
        showDoublePointMessage();
        showSpeedUpMessage(MY_SKY_BLUE);
        // When game ended or before start the game.
        if (totalBricks <= 0) {
            gameOver();
            showMessageWhenGameEnds(MY_YELLOW, "You Won!! Score: ");
            recordScore();
        } else if (ballPosY > 600) {
            gameOver();
            showMessageWhenGameEnds(MY_YELLOW, "Game Over... Score: ");
            recordScore();
        } else if (!canPlay) {
            g.setFill(MY_YELLOW);
            g.setTextAlign(TextAlignment.CENTER);
            g.setTextBaseline(VPos.CENTER);
            g.fillText("Press Enter to start", Math.round(WIDTH / 2), Math.round(HEIGHT / 2));
            g.setStroke(Color.ORANGE);
            g.strokeText("Press Enter to start", Math.round(WIDTH / 2), Math.round(HEIGHT / 2));
        }
    }

    /**
     * record a score to database
     */
    private void recordScore() {
        GameHistory.addPlayRecord(name, String.valueOf(score), date);
        timer.stop();
    }

    /**
     * Show a message "Speed up!" when player breaks more than 25 bricks.
     * @param MY_SKY_BLUE The color of the message.
     */
    private void showSpeedUpMessage(Color MY_SKY_BLUE) {
        if (highSpeed) {
            if (moment == 0) {
                highSpeedTimeMillis = System.currentTimeMillis();
                moment = 1;
            }
        }
        if (moment > 0) {
            if (canPlay) {
                g.setFill(MY_SKY_BLUE);
                g.setTextAlign(TextAlignment.CENTER);
                g.setTextBaseline(VPos.CENTER);
                g.setFont(Font.font("serif", FontWeight.BOLD,30));
                g.fillText("Speed Up!", Math.round(WIDTH / 2), Math.round(HEIGHT / 2));
                if (currentTimeMillis > highSpeedTimeMillis + 2500) {
                    moment = -1;
                }
            }
        }
    }

    /**
     * Show a message "Double Points!" when player breaks specific bricks.
     */
    private void showDoublePointMessage() {
        if (hitDoublePoint) {
            hitDoubleTimeMillis = System.currentTimeMillis();
            hitDoublePoint = false;
            momentForDoublePoint = 1;
        }
        if (momentForDoublePoint > 0) {
            if (canPlay) {
                g.setFill(Color.PINK);
                g.setFont(Font.font("serif", FontWeight.BOLD, 20));
                g.fillText("Double Points!", 450, 35);
                if (currentTimeMillis > hitDoubleTimeMillis + 800) {
                    momentForDoublePoint = -1;
                }
            }
        }
    }

    /**
     * Show a message when player won or game over.
     * @param MY_YELLOW The color of the message.
     * @param s The message.
     */
    private void showMessageWhenGameEnds(Color MY_YELLOW, String s) {
        g.setFill(MY_YELLOW);
        g.setTextAlign(TextAlignment.CENTER);
        g.setTextBaseline(VPos.CENTER);
        g.fillText(s + score + "\nPress Enter to restart", Math.round(WIDTH / 2), Math.round(HEIGHT / 2));
        if (!highSpeed) {
            g.setStroke(Color.ORANGE);
            g.strokeText(s + score + "\nPress Enter to restart", Math.round(WIDTH / 2), Math.round(HEIGHT / 2));
        }
    }

    /**
     * Animation of the game.
     */
    public void animation() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                GraphicsContext g = canvas.getGraphicsContext2D();
                draw();
                if (canPlay) {
                    currentTimeMillis = System.currentTimeMillis();
                    motionOfBall();
                }
            }
        };
    }

    /**
     * Let ball move around.
     */
    private void motionOfBall() {
        if (totalBricks > 25) { // when bricks are more than 25
            ballPosX += ballXDir;
            ballPosY += ballYDir;
            // change the speed of the ball
        } else { // bricks are 25 or less than 25
            highSpeed = true;
            if (ballPosX + ballXDir * 2 > 690 || ballPosX + ballXDir * 2 < 0 || ballPosY + ballYDir * 2 < 0) {
                ballPosX += ballXDir;
                ballPosY += ballYDir;
            } else {
                ballPosX += ballXDir * 2;
                ballPosY += ballYDir * 2;
            }
        }
        // change the direction of the ball
        whenBallHitsPaddle();
        whenBallHitsWall();
        whenBallHitsBricks();
    }

    /**
     * change the direction of the ball when it hits a paddle.
     */
    private void whenBallHitsPaddle() {
        if (new Rectangle(ballPosX, ballPosY, BALL_SIZE, BALL_SIZE).getBoundsInParent().intersects(new Rectangle(playerX, PLAYER_Y, lengthOfPaddle, HEIGHT_OF_PADDLE).getBoundsInParent())) {
            ballYDir = -ballYDir;
        }
    }

    /**
     * change the direction of the ball when it hits a wall.
     */
    private void whenBallHitsWall() {
        if (ballPosX < 0 || ballPosX > 690) {
            ballXDir = -ballXDir;
        }
        if (ballPosY < 0) {
            ballYDir = -ballYDir;
        }
    }

    /**
     * When the ball hits bricks, change the direction of the ball, change the length of the paddle, and player gets scores.
     * Also, bricks which the ball hits disappear from the canvas.
     */
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
                            hitDoublePoint = true;
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

    /**
     * move the paddle when a player pressed specific keys, and start the game when a player pressed an enter key.
     * @param scene
     */
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
            if (event.getCode() == KeyCode.ENTER) {
                if (!canPlay) {
                    timer.start();
                    canPlay = true;
                    ballPosX = 120;
                    ballPosY = 350;
                    ballXDir = -1;
                    ballYDir = -2;
                    playerX = 310;
                    score = 0;
                    highSpeed = false;
                    moment = 0;
                    totalBricks = 50;
                    brick = new Brick(5,10);
                }
            }
        });
    }

    /**
     * Close the game.
     */
    private void gameOver() {
        canPlay = false;
        ballXDir = 0;
        ballYDir = 0;
    }

    /**
     * move the paddle to right.
     */
    public void moveRight() {
        canPlay = true;
        playerX += 20;
    }

    /**
     * move the paddle to left.
     */
    public void moveLeft() {
        canPlay = true;
        playerX -= 20;
    }

    /**
     * get a width of a canvas
     * @return width of a canvas
     */
    public double getWIDTH() {
        return WIDTH;
    }

    /**
     * get a height of a canvas
     * @return height of a canvas
     */
    public double getHEIGHT() {
        return HEIGHT;
    }

    /**
     * get a group
     * @return a group
     */
    public Group getGroup() {
        return group;
    }

}

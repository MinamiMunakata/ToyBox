package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick {

    public int[][] bricks;
    private int brickWidth;
    private int brickHeight;
    private GraphicsContext g;

    public Brick(int row, int col) {
        bricks = new int[row][col];
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[0].length; j++) {
                if (j % 3 == 0) {
                    bricks[i][j] = 2;
                } else {
                    bricks[i][j] = 1;  // represent that it exist. 0 == no brick.
                }
            }
        }
        brickWidth = 540 / col;
        brickHeight = 150 / row;
    }

    public void drawBricks(GraphicsContext g) {
        for (int row = 0; row < bricks.length; row++) {
            for (int col = 0; col < bricks[0].length; col++) {
                if (bricks[row][col] > 0) {
                    if (bricks[row][col] == 2) {
                        g.setFill(Color.rgb(255, 255, 147));
                        g.fillRect(col * brickWidth + 80, row * brickHeight + 50, brickWidth, brickHeight);
                    } else {
                        g.setFill(Color.rgb(56, 255, 168));
                        g.fillRect(col * brickWidth + 80, row * brickHeight + 50, brickWidth, brickHeight);
                    }
                    // border
                    g.setStroke(Color.WHITE);
                    g.strokeRect(col * brickWidth + 80, row * brickHeight + 50, brickWidth, brickHeight);

                }
            }
        }
    }

    public int getBrickWidth() {
        return brickWidth;
    }

    public int getBrickHeight() {
        return brickHeight;
    }

    public void setBrickValue(int value, int row, int col) {
        bricks[row][col] = value;
    }
}

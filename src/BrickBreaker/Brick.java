package BrickBreaker;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author Minami Munakata
 */

public class Brick {

    public int[][] bricks;
    private int brickWidth;
    private int brickHeight;
    private GraphicsContext g;

    /**
     * Constructor
     * @param row the number of rows of bricks.
     * @param col the number of columns of bricks.
     */
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
        brickWidth = 550 / col;
        brickHeight = 150 / row;
    }

    /**
     * draw bricks.
     * @param g Graphics Context.
     */
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
                    if (Controller.highSpeed) {
                        g.setStroke(Controller.DARK_BLUE);
                        g.strokeRect(col * brickWidth + 80, row * brickHeight + 50, brickWidth, brickHeight);
                    } else {
                        g.setStroke(Color.WHITE);
                        g.strokeRect(col * brickWidth + 80, row * brickHeight + 50, brickWidth, brickHeight);
                    }
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

    /**
     * set a value to an array bricks.
     * @param value draw bricks if the value is greater than zero. If the value is 2,
     *              draw yellow bricks and player can gets double points when the one breaks them.
     * @param row the number of rows of bricks.
     * @param col the number of columns of bricks.
     */
    public void setBrickValue(int value, int row, int col) {
        bricks[row][col] = value;
    }
}

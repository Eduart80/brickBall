package brickBall;

import java.awt.*;

public class mapGenerator {
    public int[][] map;
    public int brickWidth;
    public int brickHeight;

    public mapGenerator(int row, int col) {
        map = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                map[i][j] = 1;
            }
        }
        brickWidth = 540 / col;
        brickHeight = 150 / row;
    }

    public void draw(Graphics2D g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    // Gradient bricks
                    g.setPaint(new GradientPaint(
                            j * brickWidth + 80, i * brickHeight + 50, Color.WHITE,
                            j * brickWidth + 80 + brickWidth, i * brickHeight + 50, Color.LIGHT_GRAY
                    ));
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                    // Brick outline
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.BLACK);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    public void setBrickValue(int value, int row, int col) {
        map[row][col] = value;
    }
}
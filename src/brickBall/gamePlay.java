package brickBall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class gamePlay extends JPanel implements KeyListener, ActionListener {
    private boolean play = true; // Changed to true to start immediately
    private int score = 0;
    private int totalBricks = 24;
    private Timer timer;
    private int delay = 8;

    private int playerX = 310;
    private int ballposX = 120;
    private int ballposY = 520;
    private double ballXdir = -1.5;
    private double ballYdir = -2.5;

    private mapGenerator map;

    public gamePlay() {
        map = new mapGenerator(3, 8);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(new GradientPaint(0, 0, Color.BLACK, 692, 592, Color.DARK_GRAY));
        g2d.fillRect(1, 1, 692, 592);

        map.draw(g2d);

        g.setColor(Color.CYAN);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(683, 0, 3, 592);

        g.setColor(Color.CYAN);
        g.setFont(new Font("Roboto", Font.BOLD, 25));
        g.drawString("Score: " + score, 550, 30);

        g2d.setPaint(new GradientPaint(playerX, 550, Color.RED, playerX + 100, 550, Color.ORANGE));
        g2d.fillRect(playerX, 550, 100, 9);

        g.setColor(Color.YELLOW);
        g.fillOval(ballposX, ballposY, 20, 20);
        g.setColor(new Color(255, 255, 0, 100));
        g.fillOval(ballposX - 5, ballposY - 5, 30, 30);

        if (totalBricks <= 0) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.GREEN);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("You Won!", 260, 300);
            g.setFont(new Font("Serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }

        if (ballposY > 570) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("Game Over", 260, 300);
            g.setFont(new Font("Serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 9))) {
                ballYdir = -Math.abs(ballYdir);
            }

            A: for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);

                        if (ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            ballXdir *= 1.02;
                            ballYdir *= 1.02;

                            if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;

            if (ballposX < 0) ballXdir = -ballXdir;
            if (ballposY < 0) ballYdir = -ballYdir;
            if (ballposX > 670) ballXdir = -ballXdir;
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 583) playerX = 583;
            else moveRight();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX <= 3) playerX = 3;
            else moveLeft();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER && !play) { // Only restart when play is false
            play = true;
            ballposX = 120;
            ballposY = 520;
            ballXdir = -1.5;
            ballYdir = -2.5;
            playerX = 310;
            score = 0;
            totalBricks = 24;
            map = new mapGenerator(3, 8);
            repaint();
        }
    }

    private void moveRight() {
        play = true;
        playerX += 25;
    }

    private void moveLeft() {
        play = true;
        playerX -= 25;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
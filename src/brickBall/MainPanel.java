package brickBall;

import javax.swing.*;

public class MainPanel {
    public static void main(String[] args) {
        JFrame obj = new JFrame();
        gamePlay gameplay = new gamePlay();
        obj.setBounds(10,10,700,600);
        obj.setTitle("Break the Wall");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gameplay);
    }

}

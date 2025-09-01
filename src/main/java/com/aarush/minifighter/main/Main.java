package com.aarush.minifighter.main;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setTitle(Game.GAME_NAME);
        window.setResizable(false);

        GamePanel panel = new GamePanel();
        window.add(panel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        panel.startGameThread();
    }
}
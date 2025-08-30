package com.aarush.minifighter.main;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Color;

public class GamePanel extends JPanel implements Runnable {

    // Screen settings
    public final int ORIGINAL_TILE_SIZE = 16; // 16 x 16
    public final int SCALE = 3;
    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48 x 48
    public final int MAX_SCREEN_COL = 9;
    public final int MAX_SCREEN_ROW = 9;
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 432px
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 432px
    public final int FPS = 60;

    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000.0 / FPS;
        double nextFrameTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();

            try {
                double sleepTimeNanos = nextFrameTime - System.nanoTime();
                double sleepTimeMillis = sleepTimeNanos / 1_000_000;

                if (sleepTimeMillis < 0) sleepTimeMillis = 0;

                Thread.sleep((long) sleepTimeMillis);
                nextFrameTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {}

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.dispose();
    }
}

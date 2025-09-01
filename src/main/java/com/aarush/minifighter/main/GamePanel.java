package com.aarush.minifighter.main;

import com.aarush.minifighter.collision.CollisionDetector;
import com.aarush.minifighter.entity.Player;
import com.aarush.minifighter.handler.KeyHandler;
import com.aarush.minifighter.object.GameObjectManager;
import com.aarush.minifighter.setup.ObjectSetter;
import com.aarush.minifighter.tile.TileManager;
import com.aarush.minifighter.ui.UI;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {

    // Screen settings
    public final int ORIGINAL_TILE_SIZE = 16; // 16x16
    public final int SCALE = 3;
    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48 px
    public final int MAX_SCREEN_COL = 19;
    public final int MAX_SCREEN_ROW = 11;
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 912 px
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 528 px

    // FPS settings
    public final int FPS = 60;
    private int frameCount = 0;
    private long lastFpsTime = System.currentTimeMillis();
    public int currentFPS = 0;

    Thread gameThread;

    KeyHandler keyHandler = new KeyHandler(this);
    ObjectSetter objectSetter = new ObjectSetter(this);
    UI ui = new UI(this);

    public TileManager tileManager = new TileManager(this);
    public CollisionDetector collisionDetector = new CollisionDetector(this);
    public GameObjectManager gameObjectManager = new GameObjectManager(this);
    public Player player = new Player(this, keyHandler);

    public boolean debug = false;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(80, 155, 102));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame() {
        objectSetter.setupAllGameObjects();
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

            frameCount++;
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastFpsTime >= 1000) {
                currentFPS = frameCount;
                frameCount = 0;
                lastFpsTime = currentTime;
            }

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

    public void update() {
        player.update();

        if (keyHandler.debugPressed) {
            debug = !debug;
            keyHandler.debugPressed = false;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);
        player.draw(g2);
        gameObjectManager.draw(g2);
        ui.draw(g2);

        g2.dispose();
    }
}

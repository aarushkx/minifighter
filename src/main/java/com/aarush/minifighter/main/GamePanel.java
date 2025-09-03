package com.aarush.minifighter.main;

import com.aarush.minifighter.collision.CollisionDetector;
import com.aarush.minifighter.entity.EntityManager;
import com.aarush.minifighter.entity.player.Player;
import com.aarush.minifighter.handler.KeyHandler;
import com.aarush.minifighter.object.GameObjectManager;
import com.aarush.minifighter.setup.EntitySetter;
import com.aarush.minifighter.setup.ObjectSetter;
import com.aarush.minifighter.tile.TileManager;
import com.aarush.minifighter.ui.UI;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {

    // Tile
    public final int ORIGINAL_TILE_SIZE = 16; // 16x16
    public final int SCALE = 3;
    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48 px

    // Screen
    public final int MAX_SCREEN_COL = 16;
    public final int MAX_SCREEN_ROW = 9;
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 768 px
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 432 px

    // World
    public final int MAX_WORLD_COL = 39;
    public final int MAX_WORLD_ROW = 31;
    public final int WORLD_WIDTH = TILE_SIZE * MAX_WORLD_COL;
    public final int WORLD_HEIGHT = TILE_SIZE * MAX_WORLD_ROW;

    // Camera
    public int cameraX = 0;
    public int cameraY = 0;

    // FPS
    public final int FPS = 60;
    private int frameCount = 0;
    private long lastFpsTime = System.currentTimeMillis();
    public int currentFPS = 0;

    Thread gameThread;

    KeyHandler keyHandler = new KeyHandler(this);
    ObjectSetter objectSetter = new ObjectSetter(this);
    EntitySetter entitySetter = new EntitySetter(this);
    UI ui = new UI(this);

    public TileManager tileManager = new TileManager(this);
    public CollisionDetector collisionDetector = new CollisionDetector(this);
    public GameObjectManager gameObjectManager = new GameObjectManager(this);
    public EntityManager entityManager = new EntityManager(this);
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
        entitySetter.setupAllEntities();
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
        entityManager.update();
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
        entityManager.draw(g2);
        player.draw(g2);
        gameObjectManager.draw(g2);
        ui.draw(g2);

        g2.dispose();
    }
}
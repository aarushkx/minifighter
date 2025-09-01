package com.aarush.minifighter.entity;

import com.aarush.minifighter.handler.KeyHandler;
import com.aarush.minifighter.main.GamePanel;
import com.aarush.minifighter.utils.ImageScaler;
import com.aarush.minifighter.utils.ImageFlipper;
import com.aarush.minifighter.utils.SliceSpritesheet;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.IOException;

public class Player extends Entity {

    KeyHandler keyHandler;

    public Player(GamePanel panel, KeyHandler keyHandler) {
        super(panel);

        this.keyHandler = keyHandler;

        setupCollisionArea();
        setupInitialState();
        loadImages();
    }

    private void setupCollisionArea() {
        collisionArea = new Rectangle();
        collisionArea.x = 32;
        collisionArea.y = 56;
        collisionArea.width = 32;
        collisionArea.height = 24;
    }

    private void setupInitialState() {
        x = panel.TILE_SIZE * 3;
        y = panel.TILE_SIZE * 5;
        speed = 3;
        direction = "RIGHT";
    }

    private void loadImages() {
        String imagePath = "/player/player.png";
        BufferedImage sheet;

        try (InputStream in = getClass().getResourceAsStream(imagePath)) {
            if (in == null) throw new IOException("Image not found: " + imagePath);
            sheet = ImageIO.read(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final int W = panel.TILE_SIZE;
        final int H = panel.TILE_SIZE;
        final int TARGET = panel.TILE_SIZE * 2;

        ImageScaler scaler = new ImageScaler();
        ImageFlipper flipper = new ImageFlipper();
        SliceSpritesheet slice = new SliceSpritesheet();

        // Moving sprites
        upSprites = slice.sliceRow(sheet, 5, 6, W, H, TARGET, scaler);
        downSprites = slice.sliceRow(sheet, 3, 6, W, H, TARGET, scaler);
        rightSprites = slice.sliceRow(sheet, 4, 6, W, H, TARGET, scaler);
        for (int i = 0; i < 6; i++) {
            leftSprites[i] = flipper.flipHorizontally(rightSprites[i]);
        }

        // Idle sprites
        upIdleSprites = slice.sliceRow(sheet, 2, 6, W, H, TARGET, scaler);
        downIdleSprites = slice.sliceRow(sheet, 0, 6, W, H, TARGET, scaler);
        rightIdleSprites = slice.sliceRow(sheet, 1, 6, W, H, TARGET, scaler);
        for (int i = 0; i < 6; i++) {
            leftIdleSprites[i] = flipper.flipHorizontally(rightIdleSprites[i]);
        }
    }

    @Override
    public void update() {
        isMoving = keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed;

        if (isMoving) {
            if (keyHandler.upPressed) direction = "UP";
            else if (keyHandler.downPressed) direction = "DOWN";
            else if (keyHandler.leftPressed) direction = "LEFT";
            else if (keyHandler.rightPressed) direction = "RIGHT";

            isCollisionDetected = false;
            panel.collisionDetector.checkTile(this);
            panel.collisionDetector.checkObject(this);

            if (!isCollisionDetected) {
                switch (direction) {
                    case "UP" -> y -= speed;
                    case "DOWN" -> y += speed;
                    case "LEFT" -> x -= speed;
                    case "RIGHT" -> x += speed;
                }
            }
        }
        spriteAnimationCounter++;
        if (spriteAnimationCounter > animationSpeed) {
            currentSpriteIndex = (currentSpriteIndex + 1) % 6;
            spriteAnimationCounter = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        if (isMoving) {
            switch (direction) {
                case "UP" -> image = upSprites[currentSpriteIndex];
                case "DOWN" -> image = downSprites[currentSpriteIndex];
                case "LEFT" -> image = leftSprites[currentSpriteIndex];
                case "RIGHT" -> image = rightSprites[currentSpriteIndex];
            }
        } else {
            switch (direction) {
                case "UP" -> image = upIdleSprites[currentSpriteIndex];
                case "DOWN" -> image = downIdleSprites[currentSpriteIndex];
                case "LEFT" -> image = leftIdleSprites[currentSpriteIndex];
                case "RIGHT" -> image = rightIdleSprites[currentSpriteIndex];
            }
        }

        g2.drawImage(image, x, y, null);

        if (panel.debug) {
            g2.setColor(new Color(255, 0, 0, 100));
            g2.fillRect(x + collisionArea.x, y + collisionArea.y, collisionArea.width, collisionArea.height);

            g2.setColor(Color.RED);
            g2.drawRect(x + collisionArea.x, y + collisionArea.y, collisionArea.width, collisionArea.height);
        }
    }
}

package com.aarush.minifighter.entity;

import com.aarush.minifighter.handler.KeyHandler;
import com.aarush.minifighter.main.GamePanel;
import com.aarush.minifighter.utils.ImageScaler;
import com.aarush.minifighter.utils.ImageFlipper;
import com.aarush.minifighter.utils.SliceSpritesheet;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.IOException;

public class Player extends Entity {

    KeyHandler keyHandler;

    public Player(GamePanel panel, KeyHandler keyHandler) {
        super(panel);

        this.keyHandler = keyHandler;

        setInitialState();
        loadImages();
    }

    public void setInitialState() {
        x = panel.TILE_SIZE * 4;
        y = panel.TILE_SIZE * 7;
        speed = 3;
        direction = "DOWN";
    }

    public void loadImages() {
        String imagePath = "/player/spritesheet.png";
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

            switch (direction) {
                case "UP" -> y -= speed;
                case "DOWN" -> y += speed;
                case "LEFT" -> x -= speed;
                case "RIGHT" -> x += speed;
            }

        }
        spriteCounter++;
        if (spriteCounter > animationSpeed) {
            spriteNum = (spriteNum + 1) % 6;
            spriteCounter = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        if (isMoving) {
            switch (direction) {
                case "UP" -> image = upSprites[spriteNum];
                case "DOWN" -> image = downSprites[spriteNum];
                case "LEFT" -> image = leftSprites[spriteNum];
                case "RIGHT" -> image = rightSprites[spriteNum];
            }
        } else {
            switch (direction) {
                case "UP" -> image = upIdleSprites[spriteNum];
                case "DOWN" -> image = downIdleSprites[spriteNum];
                case "LEFT" -> image = leftIdleSprites[spriteNum];
                case "RIGHT" -> image = rightIdleSprites[spriteNum];
            }
        }

        g2.drawImage(image, x, y, null);
    }
}

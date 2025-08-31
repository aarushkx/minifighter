package com.aarush.minifighter.entity;

import com.aarush.minifighter.main.GamePanel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Entity {

    public BufferedImage[] upSprites = new BufferedImage[6];
    public BufferedImage[] downSprites = new BufferedImage[6];
    public BufferedImage[] leftSprites = new BufferedImage[6];
    public BufferedImage[] rightSprites = new BufferedImage[6];

    public BufferedImage[] upIdleSprites = new BufferedImage[6];
    public BufferedImage[] downIdleSprites = new BufferedImage[6];
    public BufferedImage[] leftIdleSprites = new BufferedImage[6];
    public BufferedImage[] rightIdleSprites = new BufferedImage[6];

    public String direction = "DOWN";
    public int spriteNum = 0;
    public int spriteCounter = 0;
    public int x, y;
    public int speed;
    public int animationSpeed = 4;
    public boolean isMoving = false;

    GamePanel panel;

    public Entity(GamePanel panel) {
        this.panel = panel;
    }

    public void update() {
        if (isMoving) {
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

        g2.drawImage(image, x, y, panel.TILE_SIZE, panel.TILE_SIZE, null);
    }
}

package com.aarush.minifighter.entity;

import com.aarush.minifighter.main.GamePanel;

import java.awt.Rectangle;
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
    public int currentSpriteIndex = 0;
    public int spriteAnimationCounter = 0;
    public int x, y;
    public int speed;
    public int animationSpeed = 4;
    public boolean isMoving = false;

    public Rectangle collisionArea = new Rectangle(0, 0, 48, 48);
    public int collisionAreaDefaultX, collisionAreaDefaultY;
    public boolean isCollisionDetected = false;

    GamePanel panel;

    public Entity(GamePanel panel) {
        this.panel = panel;
    }

    public void update() {
        isCollisionDetected = false;

        panel.collisionDetector.checkTile(this);
        panel.collisionDetector.checkObject(this);

        boolean contactPlayer = panel.collisionDetector.checkPlayer(this);

        if (contactPlayer) {
            System.out.println("PLAYER CONTACT!");
        }

        if (!isCollisionDetected) {
            if (isMoving) {
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

        g2.drawImage(image, x, y, panel.TILE_SIZE, panel.TILE_SIZE, null);
    }
}

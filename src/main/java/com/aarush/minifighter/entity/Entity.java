package com.aarush.minifighter.entity;

import com.aarush.minifighter.main.GamePanel;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {

    public BufferedImage[] upSprites;
    public BufferedImage[] downSprites;
    public BufferedImage[] leftSprites;
    public BufferedImage[] rightSprites;

    public BufferedImage[] upIdleSprites;
    public BufferedImage[] downIdleSprites;
    public BufferedImage[] leftIdleSprites;
    public BufferedImage[] rightIdleSprites;

    public BufferedImage[] upAttackSprites;
    public BufferedImage[] downAttackSprites;
    public BufferedImage[] leftAttackSprites;
    public BufferedImage[] rightAttackSprites;

    public boolean isAttacking = false;
    public int attackAnimationCounter = 0;
    public int attackSpriteIndex = 0;
    public int attackAnimationSpeed = 4;

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

    public int aiDecisionTimer = 0;

    GamePanel panel;

    public Entity(GamePanel panel) {
        this.panel = panel;
    }

    public void setupCollisionArea() {}

    public void setupInitialState() {}

    public void loadSprite() {}

    public void updateAI() {}

    public void update() {
        updateAI();

        isCollisionDetected = false;
        panel.collisionDetector.checkTile(this);
        panel.collisionDetector.checkObject(this);
        panel.collisionDetector.checkEntity(this);
        panel.collisionDetector.checkPlayer(this);

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

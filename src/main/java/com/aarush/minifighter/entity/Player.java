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

    public final int screenX;
    public final int screenY;

    KeyHandler keyHandler;

    public Player(GamePanel panel, KeyHandler keyHandler) {
        super(panel);
        this.keyHandler = keyHandler;

        setupCollisionArea();

        int xOffset = collisionArea.x + (collisionArea.width / 2);   // 32 + 16 = 48
        int yOffset = collisionArea.y + (collisionArea.height / 2);  // 56 + 12 = 68

        screenX = (panel.SCREEN_WIDTH / 2) - xOffset; // 384 - 48 = 336 px
        screenY = (panel.SCREEN_HEIGHT / 2) - yOffset; // 216 - 68 = 148 px

        setupInitialState();
        loadSprite();
    }

    private void attack() {
        isAttacking = true;
        attackAnimationCounter = 0;
        attackSpriteIndex = 0;
        System.out.println("ATTACK!");
    }

    @Override
    public void setupCollisionArea() {
        collisionArea = new Rectangle();
        collisionArea.x = 32;
        collisionArea.y = 56;
        collisionArea.width = 32;
        collisionArea.height = 24;
        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;
    }

    @Override
    public void setupInitialState() {
        x = panel.TILE_SIZE * 13;
        y = panel.TILE_SIZE * 15;
        speed = 3;
        direction = "RIGHT";

        panel.cameraX = x - screenX;
        panel.cameraY = y - screenY;
    }

    @Override
    public void loadSprite() {
        String imagePath = "/player/player.png";
        BufferedImage sheet;

        try (InputStream in = getClass().getResourceAsStream(imagePath)) {
            if (in == null) throw new IOException("Image not found: " + imagePath);
            sheet = ImageIO.read(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        upSprites = new BufferedImage[6];
        downSprites = new BufferedImage[6];
        leftSprites = new BufferedImage[6];
        rightSprites = new BufferedImage[6];

        upIdleSprites = new BufferedImage[6];
        downIdleSprites = new BufferedImage[6];
        leftIdleSprites = new BufferedImage[6];
        rightIdleSprites = new BufferedImage[6];

        upAttackSprites = new BufferedImage[4];
        downAttackSprites = new BufferedImage[4];
        leftAttackSprites = new BufferedImage[4];
        rightAttackSprites = new BufferedImage[4];

        final int W = panel.TILE_SIZE;
        final int H = panel.TILE_SIZE;
        final int TARGET = panel.TILE_SIZE * 2;

        ImageScaler scaler = new ImageScaler();
        ImageFlipper flipper = new ImageFlipper();
        SliceSpritesheet slice = new SliceSpritesheet();

        // Moving Sprites
        upSprites = slice.sliceRow(sheet, 5, 6, W, H, TARGET, scaler);
        downSprites = slice.sliceRow(sheet, 3, 6, W, H, TARGET, scaler);
        rightSprites = slice.sliceRow(sheet, 4, 6, W, H, TARGET, scaler);
        for (int i = 0; i < 6; i++) {
            leftSprites[i] = flipper.flipHorizontally(rightSprites[i]);
        }

        // Idle Sprites
        upIdleSprites = slice.sliceRow(sheet, 2, 6, W, H, TARGET, scaler);
        downIdleSprites = slice.sliceRow(sheet, 0, 6, W, H, TARGET, scaler);
        rightIdleSprites = slice.sliceRow(sheet, 1, 6, W, H, TARGET, scaler);
        for (int i = 0; i < 6; i++) {
            leftIdleSprites[i] = flipper.flipHorizontally(rightIdleSprites[i]);
        }

        // Attack Sprites
        downAttackSprites = slice.sliceRow(sheet, 6, 4, W, H, TARGET, scaler);
        rightAttackSprites = slice.sliceRow(sheet, 7, 4, W, H, TARGET, scaler);
        upAttackSprites = slice.sliceRow(sheet, 8, 4, W, H, TARGET, scaler);
        for (int i = 0; i < 4; i++) {
            leftAttackSprites[i] = flipper.flipHorizontally(rightAttackSprites[i]);
        }
    }

    @Override
    public void update() {
        if (keyHandler.enterPressed && !isAttacking) {
            attack();
        }

        if (isAttacking) {
            attackAnimationCounter++;
            if (attackAnimationCounter > attackAnimationSpeed) {
                attackSpriteIndex++;
                attackAnimationCounter = 0;

                if (attackSpriteIndex >= 4) {
                    isAttacking = false;
                    attackSpriteIndex = 0;
                }
            }
            return;
        }

        isMoving = keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed;
        if (isMoving) {
            if (keyHandler.upPressed) direction = "UP";
            else if (keyHandler.downPressed) direction = "DOWN";
            else if (keyHandler.leftPressed) direction = "LEFT";
            else if (keyHandler.rightPressed) direction = "RIGHT";

            isCollisionDetected = false;
            panel.collisionDetector.checkTile(this);
            panel.collisionDetector.checkObject(this);
            panel.collisionDetector.checkEntity(this);

            if (!isCollisionDetected) {
                switch (direction) {
                    case "UP" -> {
                        y -= speed;
                        panel.cameraY -= speed;
                    }
                    case "DOWN" -> {
                        y += speed;
                        panel.cameraY += speed;
                    }
                    case "LEFT" -> {
                        x -= speed;
                        panel.cameraX -= speed;
                    }
                    case "RIGHT" -> {
                        x += speed;
                        panel.cameraX += speed;
                    }
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

        if (isAttacking) {
            // Attack Animation
            switch (direction) {
                case "UP" -> image = upAttackSprites[attackSpriteIndex];
                case "DOWN" -> image = downAttackSprites[attackSpriteIndex];
                case "LEFT" -> image = leftAttackSprites[attackSpriteIndex];
                case "RIGHT" -> image = rightAttackSprites[attackSpriteIndex];
            }
        } else if (isMoving) {
            // Movement Animation
            switch (direction) {
                case "UP" -> image = upSprites[currentSpriteIndex];
                case "DOWN" -> image = downSprites[currentSpriteIndex];
                case "LEFT" -> image = leftSprites[currentSpriteIndex];
                case "RIGHT" -> image = rightSprites[currentSpriteIndex];
            }
        } else {
            // Idle Animation
            switch (direction) {
                case "UP" -> image = upIdleSprites[currentSpriteIndex];
                case "DOWN" -> image = downIdleSprites[currentSpriteIndex];
                case "LEFT" -> image = leftIdleSprites[currentSpriteIndex];
                case "RIGHT" -> image = rightIdleSprites[currentSpriteIndex];
            }
        }

        g2.drawImage(image, screenX, screenY, null);

        if (panel.debug) {
            g2.setColor(new Color(255, 0, 0, 100));
            g2.fillRect(screenX + collisionArea.x, screenY + collisionArea.y, collisionArea.width, collisionArea.height);
            g2.setColor(Color.RED);
            g2.drawRect(screenX + collisionArea.x, screenY + collisionArea.y, collisionArea.width, collisionArea.height);
        }
    }
}

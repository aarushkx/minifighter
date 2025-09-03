package com.aarush.minifighter.entity.monster;

import com.aarush.minifighter.entity.Entity;
import com.aarush.minifighter.main.GamePanel;
import com.aarush.minifighter.utils.ImageFlipper;
import com.aarush.minifighter.utils.ImageScaler;
import com.aarush.minifighter.utils.SliceSpritesheet;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Skeleton extends Entity {

    GamePanel panel;

    public Skeleton(GamePanel panel) {
        super(panel);
        this.panel = panel;

        setupCollisionArea();
        setupInitialState();
        loadSprite();
    }

    @Override
    public void setupCollisionArea() {
        collisionArea = new Rectangle();
        collisionArea.x = 24;
        collisionArea.y = 26;
        collisionArea.width = 48;
        collisionArea.height = 44;
        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;
    }

    @Override
    public void setupInitialState() {
        speed = 2;
        direction = "DOWN";
    }

    @Override
    public void loadSprite() {
        try {
            ImageScaler scaler = new ImageScaler();
            ImageFlipper flipper = new ImageFlipper();
            SliceSpritesheet slice = new SliceSpritesheet();

            final int W = 48;
            final int H = 48;
            final int TARGET = panel.TILE_SIZE * 2;

            upSprites = new BufferedImage[5];
            downSprites = new BufferedImage[5];
            leftSprites = new BufferedImage[5];
            rightSprites = new BufferedImage[5];

            upIdleSprites = new BufferedImage[5];
            downIdleSprites = new BufferedImage[5];
            leftIdleSprites = new BufferedImage[5];
            rightIdleSprites = new BufferedImage[5];

            BufferedImage downSheet = ImageIO.read(getClass().getResourceAsStream("/monsters/skeleton/skeleton_down.png"));
            if (downSheet == null) throw new IOException("skeleton_down.png not found");

            BufferedImage rightSheet = ImageIO.read(getClass().getResourceAsStream("/monsters/skeleton/skeleton_right.png"));
            if (rightSheet == null) throw new IOException("skeleton_right.png not found");

            BufferedImage upSheet = ImageIO.read(getClass().getResourceAsStream("/monsters/skeleton/skeleton_up.png"));
            if (upSheet == null) throw new IOException("skeleton_up.png not found");

            // Idle Sprites
            downIdleSprites = slice.sliceRow(downSheet, 0, 5, W, H, TARGET, scaler);
            rightIdleSprites = slice.sliceRow(rightSheet, 0, 5, W, H, TARGET, scaler);
            upIdleSprites = slice.sliceRow(upSheet, 0, 5, W, H, TARGET, scaler);

            for (int i = 0; i < 5; i++) {
                leftIdleSprites[i] = flipper.flipHorizontally(rightIdleSprites[i]);
            }

            // Moving Sprites
            downSprites = slice.sliceRow(downSheet, 1, 5, W, H, TARGET, scaler);
            rightSprites = slice.sliceRow(rightSheet, 1, 5, W, H, TARGET, scaler);
            upSprites = slice.sliceRow(upSheet, 1, 5, W, H, TARGET, scaler);

            for (int i = 0; i < 5; i++) {
                leftSprites[i] = flipper.flipHorizontally(rightSprites[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAI() {
        aiDecisionTimer++;
        if (aiDecisionTimer == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) direction = "UP";
            else if (i <= 50) direction = "DOWN";
            else if (i <= 75) direction = "LEFT";
            else direction = "RIGHT";

            isMoving = true;
            aiDecisionTimer = 0;
        }
    }

    @Override
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
            currentSpriteIndex = (currentSpriteIndex + 1) % 5;
            spriteAnimationCounter = 0;
        }
    }


    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        int screenX = x - panel.cameraX;
        int screenY = y - panel.cameraY;

        if (isMoving) {
            switch (direction) {
                case "UP" -> image = upSprites[currentSpriteIndex];
                case "DOWN" -> image = downSprites[currentSpriteIndex];
                case "LEFT" -> image = leftSprites[currentSpriteIndex];
                case "RIGHT" -> image = rightSprites[currentSpriteIndex];
            }
        } else {
            int idleIndex = currentSpriteIndex % 5;
            switch (direction) {
                case "UP" -> image = upIdleSprites[idleIndex];
                case "DOWN" -> image = downIdleSprites[idleIndex];
                case "LEFT" -> image = leftIdleSprites[idleIndex];
                case "RIGHT" -> image = rightIdleSprites[idleIndex];
            }
        }

        if (screenX > -panel.TILE_SIZE * 2 && screenX < panel.SCREEN_WIDTH && screenY > -panel.TILE_SIZE * 2 && screenY < panel.SCREEN_HEIGHT) {
            g2.drawImage(image, screenX, screenY, null);

            if (panel.debug) {
                g2.setColor(new Color(100, 0, 0, 100));
                g2.fillRect(screenX + collisionArea.x, screenY + collisionArea.y, collisionArea.width, collisionArea.height);
                g2.setColor(Color.RED);
                g2.drawRect(screenX + collisionArea.x, screenY + collisionArea.y, collisionArea.width, collisionArea.height);
            }
        }
    }
}

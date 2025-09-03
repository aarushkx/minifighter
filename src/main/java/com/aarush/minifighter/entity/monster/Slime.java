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
import java.io.InputStream;
import java.util.Random;

public class Slime extends Entity {

    GamePanel panel;

    public Slime(GamePanel panel) {
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
        collisionArea.y = 32;
        collisionArea.width = 48;
        collisionArea.height = 36;
        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;
    }

    @Override
    public void setupInitialState() {
        speed = 1;
        direction = "DOWN";
    }

    @Override
    public void loadSprite() {
        String imagePath = "/monsters/slime/slime.png";
        BufferedImage sheet;

        try (InputStream in = getClass().getResourceAsStream(imagePath)) {
            if (in == null) throw new IOException("Image not found: " + imagePath);
            sheet = ImageIO.read(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final int W = 32;
        final int H = 32;
        final int TARGET = panel.TILE_SIZE * 2;

        ImageScaler scaler = new ImageScaler();
        ImageFlipper flipper = new ImageFlipper();
        SliceSpritesheet slice = new SliceSpritesheet();

        upSprites = new BufferedImage[6];
        downSprites = new BufferedImage[6];
        leftSprites = new BufferedImage[6];
        rightSprites = new BufferedImage[6];

        upIdleSprites = new BufferedImage[4];
        rightIdleSprites = new BufferedImage[4];
        downIdleSprites = new BufferedImage[4];
        leftIdleSprites = new BufferedImage[4];

        // Idle Sprites
        upIdleSprites = slice.sliceRow(sheet, 0, 4, W, H, TARGET, scaler);
        rightIdleSprites = slice.sliceRow(sheet, 1, 4, W, H, TARGET, scaler);
        downIdleSprites = slice.sliceRow(sheet, 2, 4, W, H, TARGET, scaler);

        for (int i = 0; i < 4; i++) {
            leftIdleSprites[i] = flipper.flipHorizontally(rightIdleSprites[i]);
        }

        // Moving Sprites
        downSprites = slice.sliceRow(sheet, 3, 6, W, H, TARGET, scaler);
        rightSprites = slice.sliceRow(sheet, 4, 6, W, H, TARGET, scaler);
        upSprites = slice.sliceRow(sheet, 5, 6, W, H, TARGET, scaler);

        for (int i = 0; i < 6; i++) {
            leftSprites[i] = flipper.flipHorizontally(rightSprites[i]);
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
            int idleIndex = currentSpriteIndex % 4;
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

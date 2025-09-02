package com.aarush.minifighter.object;

import com.aarush.minifighter.main.GamePanel;

import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class GameObject {

    public String name;
    public BufferedImage image;
    public boolean collision = false;
    public int worldX, worldY;

    public Rectangle collisionArea = new Rectangle(0, 0, 48, 48);
    public int collisionAreaDefaultX = 0;
    public int collisionAreaDefaultY = 0;

    public String getName() {
        return name;
    }

    public boolean hasCollision() {
        return collision;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public Rectangle getCollisionArea() {
        return collisionArea;
    }

    public void loadImage(GamePanel panel) {}

    public void setupCollisionArea() {}

    public void draw(Graphics2D g2, GamePanel panel) {
        int screenX = worldX - panel.cameraX;
        int screenY = worldY - panel.cameraY;

        if (image != null) {
            g2.drawImage(image, screenX, screenY, null);
        }

        if (panel.debug && collision) {
            g2.setColor(new Color(255, 0, 0, 100));
            g2.fillRect(screenX + collisionArea.x, screenY + collisionArea.y,
                    collisionArea.width, collisionArea.height);
            g2.setColor(Color.RED);
            g2.drawRect(screenX + collisionArea.x, screenY + collisionArea.y,
                    collisionArea.width, collisionArea.height);
        }
    }
}

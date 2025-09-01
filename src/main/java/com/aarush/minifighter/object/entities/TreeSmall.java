package com.aarush.minifighter.object.entities;

import com.aarush.minifighter.main.GamePanel;
import com.aarush.minifighter.object.GameObject;
import com.aarush.minifighter.utils.ImageScaler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TreeSmall extends GameObject {

    public TreeSmall(GamePanel panel, int worldX, int worldY) {
        this.name = "TREE_SMALL";
        this.worldX = worldX;
        this.worldY = worldY;
        collision = true;

        loadImage(panel);
        setupCollisionArea();
    }

    @Override
    public void loadImage(GamePanel panel) {
        String imagePath = "/tiles/objects.png";

        try {
            BufferedImage objectsSpriteSheet = ImageIO.read(getClass().getResourceAsStream(imagePath));
            BufferedImage treeSmallImage = objectsSpriteSheet.getSubimage(16 * 6, 16 * 7, 16 * 2, 16 * 2);

            image = new ImageScaler().scale(treeSmallImage, panel.TILE_SIZE * 2, panel.TILE_SIZE * 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setupCollisionArea() {
        collisionArea.setBounds(8, 70, 76, 32);
        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;
    }
}

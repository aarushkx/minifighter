package com.aarush.minifighter.object.entities;

import com.aarush.minifighter.main.GamePanel;
import com.aarush.minifighter.object.GameObject;
import com.aarush.minifighter.utils.ImageScaler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TreeStump extends GameObject {

    public TreeStump(GamePanel panel, int worldX, int worldY) {
        this.name = "TREE_STUMP";
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
            BufferedImage treeStumpImage = objectsSpriteSheet.getSubimage(16 * 10, 16 * 7, 16 * 2, 16 * 2);

            image = new ImageScaler().scale(treeStumpImage, panel.TILE_SIZE * 2, panel.TILE_SIZE * 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setupCollisionArea() {
        collisionArea.setBounds(20, 44, 60, 40);
        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;
    }
}

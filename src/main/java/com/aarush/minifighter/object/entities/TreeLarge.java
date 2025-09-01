package com.aarush.minifighter.object.entities;

import com.aarush.minifighter.main.GamePanel;
import com.aarush.minifighter.object.GameObject;
import com.aarush.minifighter.utils.ImageScaler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TreeLarge extends GameObject {

    public TreeLarge(GamePanel panel, int worldX, int worldY) {
        this.name = "TREE_LARGE";
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
            BufferedImage treeLargeImage = objectsSpriteSheet.getSubimage(0, 16 * 5, 16 * 3, 16 * 4);

            image = new ImageScaler().scale(treeLargeImage, panel.TILE_SIZE * 3, panel.TILE_SIZE * 4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setupCollisionArea() {
        collisionArea.setBounds(40, 128, 60, 52);
        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;
    }
}

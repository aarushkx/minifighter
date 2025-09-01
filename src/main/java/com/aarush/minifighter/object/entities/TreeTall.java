package com.aarush.minifighter.object.entities;

import com.aarush.minifighter.main.GamePanel;
import com.aarush.minifighter.object.GameObject;
import com.aarush.minifighter.utils.ImageScaler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TreeTall extends GameObject {

    public TreeTall(GamePanel panel, int worldX, int worldY) {
        this.name = "TREE_TALL";
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
            BufferedImage treeSmallImage = objectsSpriteSheet.getSubimage(16 * 8, 16 * 6, 16 * 2, 16 * 3);

            image = new ImageScaler().scale(treeSmallImage, panel.TILE_SIZE * 2, panel.TILE_SIZE * 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setupCollisionArea() {
        collisionArea.setBounds(32, 116, 36, 26);
        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;
    }
}

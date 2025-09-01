package com.aarush.minifighter.object.entities;

import com.aarush.minifighter.main.GamePanel;
import com.aarush.minifighter.object.GameObject;
import com.aarush.minifighter.utils.ImageScaler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Board extends GameObject {

    public Board(GamePanel panel, int worldX, int worldY) {
        this.name = "ROCK";
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
            BufferedImage boardImage = objectsSpriteSheet.getSubimage(0, 0, 16, 16);

            image = new ImageScaler().scale(boardImage, panel.TILE_SIZE, panel.TILE_SIZE);
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    @Override
    public void setupCollisionArea() {
        collisionArea.setBounds(8, 32, 32, 16);
        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;
    }
}

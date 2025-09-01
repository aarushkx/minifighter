package com.aarush.minifighter.object;

import com.aarush.minifighter.main.GamePanel;
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

        loadBoardImage(panel);
        setupCollisionArea();
    }

    private void loadBoardImage(GamePanel panel) {
        String imagePath = "/tiles/objects.png";

        try {
            BufferedImage objectsSpriteSheet = ImageIO.read(getClass().getResourceAsStream(imagePath));
            BufferedImage rockSprite = objectsSpriteSheet.getSubimage(0, 0, 16, 16);

            image = new ImageScaler().scale(rockSprite, panel.TILE_SIZE, panel.TILE_SIZE);

        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    private void setupCollisionArea() {
        collisionArea.setBounds(8, 32, 32, 8);
        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;
    }
}

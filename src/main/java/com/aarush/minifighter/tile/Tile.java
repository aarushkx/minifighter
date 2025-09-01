package com.aarush.minifighter.tile;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Tile {

    public BufferedImage image;
    public boolean collision = false;
    public Rectangle collisionArea;

    public Tile() {
        this.collisionArea = new Rectangle(0, 0, 48, 48);
    }
}

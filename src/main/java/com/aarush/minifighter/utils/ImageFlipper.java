package com.aarush.minifighter.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageFlipper {

    public BufferedImage flipHorizontally(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage flipped = new BufferedImage(width, height, image.getType());
        Graphics2D g2d = flipped.createGraphics();

        g2d.drawImage(image, width, 0, -width, height, null);
        g2d.dispose();

        return flipped;
    }
}

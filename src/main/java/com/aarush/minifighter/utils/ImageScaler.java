package com.aarush.minifighter.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageScaler {

    public BufferedImage scale(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage scaledImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());

        Graphics2D graphics = scaledImage.createGraphics();
        graphics.drawImage(originalImage,0,0, targetWidth, targetHeight, null);
        graphics.dispose();

        return scaledImage;
    }
}
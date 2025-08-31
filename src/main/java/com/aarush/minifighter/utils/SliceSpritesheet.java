package com.aarush.minifighter.utils;

import java.awt.image.BufferedImage;

public class SliceSpritesheet {

    public BufferedImage[] sliceRow(BufferedImage sheet, int rowIndex, int frameCount, int frameWidth, int frameHeight, int targetSize, ImageScaler scaler) {
        BufferedImage[] frames = new BufferedImage[frameCount];

        for (int i = 0; i < frameCount; i++) {
            BufferedImage frame = sheet.getSubimage(i * frameWidth, rowIndex * frameHeight, frameWidth, frameHeight);
            frames[i] = scaler.scale(frame, targetSize, targetSize);
        }
        return frames;
    }
}

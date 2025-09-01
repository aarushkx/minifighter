package com.aarush.minifighter.ui;

import com.aarush.minifighter.main.GamePanel;
import com.aarush.minifighter.main.Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.InputStream;
import java.io.IOException;

public class UI {

    GamePanel panel;
    Font font;
    Graphics2D g2;

    public UI(GamePanel panel) {
        this.panel = panel;

        loadFont();
    }

    private void loadFont() {
        String path = "/font/ReplaceTheSun.ttf";

        try {
            InputStream is = getClass().getResourceAsStream(path);
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    private void drawDebugMetrics() {
        g2.setFont(font.deriveFont(Font.PLAIN, 18f));
        g2.setColor(Color.WHITE);

        int x = 10;
        int y = 20;

        g2.drawString(Game.GAME_NAME + " <DEBUG_MODE>", x, y);
        g2.drawString("by " + Game.AUTHOR, x, y += 25);
        g2.drawString("FPS: " + panel.currentFPS, x, y += 25);
        g2.drawString("Speed: " + panel.player.speed, x, y += 25);
        g2.drawString("X: " + panel.player.x + ", Y: " + panel.player.y, x, y + 25);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(font);
        g2.setColor(Color.WHITE);

        if (panel.debug) {
            drawDebugMetrics();
        }
    }
}

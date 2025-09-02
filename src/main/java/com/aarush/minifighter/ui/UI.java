package com.aarush.minifighter.ui;

import com.aarush.minifighter.main.GamePanel;
import com.aarush.minifighter.main.Game;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Color;
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
        g2.setFont(font.deriveFont(Font.PLAIN, 16f));
        g2.setColor(Color.WHITE);

        String[] lines = {
                Game.GAME_NAME + " <DEBUG_MODE>",
                "by " + Game.AUTHOR,
                "FPS: " + panel.currentFPS,
                "Display: " + panel.MAX_SCREEN_COL + "x" + panel.MAX_SCREEN_ROW,
                "Resolution: " + panel.SCREEN_WIDTH + "x" + panel.SCREEN_HEIGHT,
                "Tile Size: " + panel.TILE_SIZE + " px",
                "Tiles Drawn: " + panel.tileManager.tilesDrawnLastFrame + "/" + panel.tileManager.visibleTileArea,
                "Total Tiles: " + panel.tileManager.totalTiles,
                "Objects Drawn: " + panel.gameObjectManager.objectsDrawnLastFrame + "/" + panel.gameObjectManager.getTotalObjectCount(),
                "Camera: (" + panel.cameraX + ", " + panel.cameraY + ")",
                "Player: (" + panel.player.x + ", " + panel.player.y + ")",
                "Player Screen: (" + panel.player.screenX + ", " + panel.player.screenY + ")"
        };

        int x = 10, y = 25, padding = 10;
        int maxWidth = 0;

        FontMetrics fm = g2.getFontMetrics();
        for (String line : lines) {
            maxWidth = Math.max(maxWidth, fm.stringWidth(line));
        }

        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, maxWidth + padding * 2, lines.length * 20 + padding * 2);

        g2.setColor(Color.WHITE);
        for (int i = 0; i < lines.length; i++) {
            g2.drawString(lines[i], x, y + i * 20);
        }
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

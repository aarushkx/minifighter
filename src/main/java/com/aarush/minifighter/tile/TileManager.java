package com.aarush.minifighter.tile;

import com.aarush.minifighter.main.GamePanel;
import com.aarush.minifighter.utils.ImageScaler;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class TileManager {

    public int[][] tileMap;
    public Tile[] tiles = new Tile[100];

    GamePanel panel;

    public TileManager(GamePanel panel) {
        this.panel = panel;

        tileMap = new int[panel.MAX_SCREEN_COL][panel.MAX_SCREEN_ROW];

        loadTiles();
        loadMap();
    }

    private void loadTiles() {
        ImageScaler scaler = new ImageScaler();

        try {
            // Grass
            putSingle(0, "/tiles/grass.png", scaler, false, null);
            // Walls
            putSheetSlice(1, "/tiles/walls.png", new int[][]{{3, 1}, {3, 2}, {4, 0}, {4, 2}}, 16, 16, panel.TILE_SIZE, scaler, true, new Rectangle(0, 0, panel.TILE_SIZE, panel.TILE_SIZE));

            if (tiles[1] != null) {
                tiles[1].collisionArea = new Rectangle(0, 28, panel.TILE_SIZE, 24);
            }
            if (tiles[2] != null) {
                tiles[2].collisionArea = new Rectangle(0, 28, panel.TILE_SIZE, 24);
            }

            // Plains
            putSheetSlice(5, "/tiles/plains.png", grid(0, 3, 0, 5), 16, 16, panel.TILE_SIZE, scaler, false, null);
            // Decors
            putSheetSlice(30, "/tiles/decor_8x8.png", grid(0, 3, 0, 3), 8, 8, panel.TILE_SIZE / 3, scaler, false, null);
            // Objects (Rocks)
            putSheetSlice(46, "/tiles/objects.png", new int[][]{{0, 0}, {1, 0}}, 16, 16, panel.TILE_SIZE / 2, scaler, false, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void putSingle(int index, String imagePath, ImageScaler scaler, boolean coll, Rectangle collisionArea) {
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream(imagePath));

            Tile t = new Tile();
            t.image = scaler.scale(image, panel.TILE_SIZE, panel.TILE_SIZE);
            t.collision = coll;
            t.collisionArea = collisionArea != null ? collisionArea : (coll ? new Rectangle(0, 0, panel.TILE_SIZE, panel.TILE_SIZE) : new Rectangle(0, 0, 0, 0));
            tiles[index] = t;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void putSheetSlice(int startIndex, String sheetPath, int[][] rcArray, int srcW, int srcH, int target, ImageScaler scaler, boolean coll, Rectangle collisionArea) {
        try {
            BufferedImage sheet = ImageIO.read(getClass().getResourceAsStream(sheetPath));
            int index = startIndex;

            for (int[] rc : rcArray) {
                int r = rc[0], c = rc[1];
                BufferedImage sub = sheet.getSubimage(c * srcW, r * srcH, srcW, srcH);

                Tile t = new Tile();
                t.image = scaler.scale(sub, target, target);
                t.collision = coll;
                t.collisionArea = collisionArea != null ? new Rectangle(collisionArea) : (coll ? new Rectangle(0, 0, target, target) : new Rectangle(0, 0, 0, 0));
                tiles[index++] = t;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int[][] grid(int r0, int r1, int c0, int c1) {
        int total = (r1 - r0 + 1) * (c1 - c0 + 1);
        int[][] out = new int[total][2];

        int n = 0;
        for (int r = r0; r <= r1; r++) {
            for (int c = c0; c <= c1; c++) {
                out[n++] = new int[]{r, c};
            }
        }
        return out;
    }

    private void loadMap() {
        String path = "/map/world.txt";

        try (InputStream is = getClass().getResourceAsStream(path);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            for (int row = 0; row < panel.MAX_SCREEN_ROW; row++) {
                String[] nums = br.readLine().split(" ");
                for (int col = 0; col < panel.MAX_SCREEN_COL; col++)
                    tileMap[col][row] = Integer.parseInt(nums[col]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        for (int row = 0, y = 0; row < panel.MAX_SCREEN_ROW; row++, y += panel.TILE_SIZE) {
            for (int col = 0, x = 0; col < panel.MAX_SCREEN_COL; col++, x += panel.TILE_SIZE) {
                int index = tileMap[col][row];

                if (index < tiles.length && tiles[index] != null) {
                    g2.drawImage(tiles[index].image, x, y, null);

                    if (panel.debug && tiles[index].collision) {
                        g2.setColor(new Color(255, 0, 0, 100));
                        Rectangle collArea = tiles[index].collisionArea;
                        g2.fillRect(x + collArea.x, y + collArea.y, collArea.width, collArea.height);

                        g2.setColor(Color.RED);
                        g2.drawRect(x + collArea.x, y + collArea.y, collArea.width, collArea.height);
                    }
                }
            }
        }
    }
}

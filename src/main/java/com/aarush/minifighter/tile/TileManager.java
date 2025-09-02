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
    public int tilesDrawnLastFrame = 0;
    public int totalTiles;
    public int visibleTileArea;

    GamePanel panel;

    public TileManager(GamePanel panel) {
        this.panel = panel;

        tileMap = new int[panel.MAX_WORLD_COL][panel.MAX_WORLD_ROW];

        loadTiles();
        loadMap();
        calculateTileStats();
    }

    private void loadTiles() {
        ImageScaler scaler = new ImageScaler();

        try {
            // Tile 0: Grass
            BufferedImage grassImage = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
            tiles[0] = new Tile();
            tiles[0].image = scaler.scale(grassImage, panel.TILE_SIZE, panel.TILE_SIZE);
            tiles[0].collision = false;

            // Tile 1: Wall Edge
            BufferedImage wallSheet = ImageIO.read(getClass().getResourceAsStream("/tiles/walls.png"));
            tiles[1] = new Tile();
            tiles[1].image = scaler.scale(wallSheet.getSubimage(16, 3 * 16, 16, 16), panel.TILE_SIZE, panel.TILE_SIZE);
            tiles[1].collision = true;
            tiles[1].collisionArea = new Rectangle(0, 28, panel.TILE_SIZE, 24);

            // Tile 2: Wall Top
            tiles[2] = new Tile();
            tiles[2].image = scaler.scale(wallSheet.getSubimage(2 * 16, 3 * 16, 16, 16), panel.TILE_SIZE, panel.TILE_SIZE);
            tiles[2].collision = true;
            tiles[2].collisionArea = new Rectangle(0, 28, panel.TILE_SIZE, 24);

            // Tile 3: Wall Vertical
            tiles[3] = new Tile();
            tiles[3].image = scaler.scale(wallSheet.getSubimage(0, 4 * 16, 16, 16), panel.TILE_SIZE, panel.TILE_SIZE);
            tiles[3].collision = true;
            tiles[3].collisionArea = new Rectangle(0, 0, panel.TILE_SIZE, panel.TILE_SIZE);

            // Tile 4: Wall Bottom
            tiles[4] = new Tile();
            tiles[4].image = scaler.scale(wallSheet.getSubimage(2 * 16, 4 * 16, 16, 16), panel.TILE_SIZE, panel.TILE_SIZE);
            tiles[4].collision = true;
            tiles[4].collisionArea = new Rectangle(0, 0, panel.TILE_SIZE, panel.TILE_SIZE);

            // Tile (5-36): Plains (4x4 grid) x 2
            BufferedImage plainsSheet = ImageIO.read(getClass().getResourceAsStream("/tiles/plains.png"));

            int plainsIndex = 5;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 4; j++) {
                    tiles[plainsIndex] = new Tile();
                    tiles[plainsIndex].image = scaler.scale(plainsSheet.getSubimage(j * 16, i * 16, 16, 16), panel.TILE_SIZE, panel.TILE_SIZE);
                    tiles[plainsIndex].collision = false;
                    tiles[plainsIndex].collisionArea = new Rectangle(0, 0, 0, 0);
                    plainsIndex++;
                }
            }

            // Tile (37-40): Decor Grass (4 tiles)
            BufferedImage decorGrassSheet = ImageIO.read(getClass().getResourceAsStream("/tiles/decor_8x8.png"));

            int decorGrassIndex = 37;
            for (int i = 0; i < 4; i++) {
                tiles[decorGrassIndex] = new Tile();
                tiles[decorGrassIndex].image = scaler.scale(decorGrassSheet.getSubimage(i * 8, 8, 8, 8), panel.TILE_SIZE / 3, panel.TILE_SIZE / 3);
                tiles[decorGrassIndex].collision = false;
                decorGrassIndex++;
            }

            // Tile 41: Rock
            BufferedImage objectsSheet = ImageIO.read(getClass().getResourceAsStream("/tiles/objects.png"));

            tiles[41] = new Tile();
            tiles[41].image = scaler.scale(objectsSheet.getSubimage(0, 16, 16, 16), panel.TILE_SIZE / 2, panel.TILE_SIZE / 2);
            tiles[41].collision = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMap() {
        String path = "/map/world.txt";

        try (InputStream is = getClass().getResourceAsStream(path);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            for (int row = 0; row < panel.MAX_WORLD_ROW; row++) {
                String[] nums = br.readLine().split(" ");
                for (int col = 0; col < panel.MAX_WORLD_COL; col++) {
                    tileMap[col][row] = Integer.parseInt(nums[col]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calculateTileStats() {
        totalTiles = panel.MAX_WORLD_COL * panel.MAX_WORLD_ROW;

        int visibleCols = (panel.SCREEN_WIDTH / panel.TILE_SIZE) + 2;
        int visibleRows = (panel.SCREEN_HEIGHT / panel.TILE_SIZE) + 2;
        visibleTileArea = visibleCols * visibleRows;
    }

    public void draw(Graphics2D g2) {
        int tilesDrawn = 0;

        int startCol = Math.max(0, panel.cameraX / panel.TILE_SIZE);
        int endCol = Math.min(panel.MAX_WORLD_COL - 1, (panel.cameraX + panel.SCREEN_WIDTH) / panel.TILE_SIZE);
        int startRow = Math.max(0, panel.cameraY / panel.TILE_SIZE);
        int endRow = Math.min(panel.MAX_WORLD_ROW - 1, (panel.cameraY + panel.SCREEN_HEIGHT) / panel.TILE_SIZE);

        for (int row = startRow; row <= endRow; row++) {
            for (int col = startCol; col <= endCol; col++) {
                int index = tileMap[col][row];

                if (index < tiles.length && tiles[index] != null) {
                    int screenX = col * panel.TILE_SIZE - panel.cameraX;
                    int screenY = row * panel.TILE_SIZE - panel.cameraY;

                    g2.drawImage(tiles[index].image, screenX, screenY, null);
                    tilesDrawn++;

                    if (panel.debug && tiles[index].collision) {
                        g2.setColor(new Color(255, 0, 0, 100));

                        Rectangle collArea = tiles[index].collisionArea;

                        g2.fillRect(screenX + collArea.x, screenY + collArea.y, collArea.width, collArea.height);
                        g2.setColor(Color.RED);
                        g2.drawRect(screenX + collArea.x, screenY + collArea.y, collArea.width, collArea.height);
                    }
                }
            }
        }

        tilesDrawnLastFrame = tilesDrawn;
    }
}

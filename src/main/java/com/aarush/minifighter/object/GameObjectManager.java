package com.aarush.minifighter.object;

import com.aarush.minifighter.main.GamePanel;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;
import java.util.ArrayList;

public class GameObjectManager {

    public int objectsDrawnLastFrame = 0;
    List<GameObject> gameObjects = new ArrayList<>();
    GamePanel panel;

    public GameObjectManager(GamePanel panel) {
        this.panel = panel;
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public int getTotalObjectCount() {
        return gameObjects.size();
    }

    public void draw(Graphics2D g2) {
        int objectsDrawn = 0;
        int buffer = panel.TILE_SIZE * 2;

        int xScreen = -buffer / 2;
        int yScreen = -buffer / 2;
        int widthScreen = panel.SCREEN_WIDTH + buffer;
        int heightScreen = panel.SCREEN_HEIGHT + buffer;

        Rectangle screenBounds = new Rectangle(xScreen, yScreen, widthScreen, heightScreen);

        for (GameObject gameObject : gameObjects) {
            if (gameObject != null) {
                int screenX = gameObject.worldX - panel.cameraX;
                int screenY = gameObject.worldY - panel.cameraY;

                int xObject = screenX + gameObject.collisionArea.x;
                int yObject = screenY + gameObject.collisionArea.y;
                int widthObject = gameObject.collisionArea.width;
                int heightObject = gameObject.collisionArea.height;

                Rectangle objectBounds = new Rectangle(xObject, yObject, widthObject, heightObject);

                if (screenBounds.intersects(objectBounds)) {
                    gameObject.draw(g2, panel);
                    objectsDrawn++;
                }
            }
        }

        objectsDrawnLastFrame = objectsDrawn;
    }
}

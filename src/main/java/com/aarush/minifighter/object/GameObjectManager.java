package com.aarush.minifighter.object;

import com.aarush.minifighter.main.GamePanel;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

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

        for (GameObject gameObject : gameObjects) {
            if (gameObject != null && gameObject.image != null) {
                int screenX = gameObject.worldX - panel.cameraX;
                int screenY = gameObject.worldY - panel.cameraY;

                int imageWidth = gameObject.image.getWidth();
                int imageHeight = gameObject.image.getHeight();

                boolean isVisible = (screenX + imageWidth) >= 0 && screenX <= panel.SCREEN_WIDTH && (screenY + imageHeight) >= 0 && screenY <= panel.SCREEN_HEIGHT;
                if (isVisible) {
                    gameObject.draw(g2, panel);
                    objectsDrawn++;
                }
            }
        }

        objectsDrawnLastFrame = objectsDrawn;
    }
}

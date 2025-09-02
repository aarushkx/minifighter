package com.aarush.minifighter.object;

import com.aarush.minifighter.main.GamePanel;

import java.awt.Graphics2D;
import java.util.List;
import java.util.ArrayList;

public class GameObjectManager {

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

    public void draw(Graphics2D graphics2D) {
        for (GameObject gameObject : gameObjects) {
            if (gameObject != null) {
                int screenX = gameObject.worldX - panel.cameraX;
                int screenY = gameObject.worldY - panel.cameraY;

                if (screenX < panel.SCREEN_WIDTH && screenY < panel.SCREEN_HEIGHT) {
                    gameObject.draw(graphics2D, panel);
                }
            }
        }
    }
}

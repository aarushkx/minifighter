package com.aarush.minifighter.entity;

import com.aarush.minifighter.main.GamePanel;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;
import java.util.ArrayList;

public class EntityManager {

    public int entitiesDrawnLastFrame = 0;
    public int entitiesUpdatedLastFrame = 0;

    GamePanel panel;
    List<Entity> entities = new ArrayList<>();

    public EntityManager(GamePanel panel) {
        this.panel = panel;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public List<Entity> getEntities() {
        return new ArrayList<>(entities);
    }

    public int getTotalEntityCount() {
        return entities.size();
    }

    public void update() {
        int entitiesUpdated = 0;

        for (Entity entity : entities) {
            if (entity != null) {
                entity.update();
                entitiesUpdated++;
            }
        }

        entitiesUpdatedLastFrame = entitiesUpdated;
    }

    public void draw(Graphics2D g2) {
        int entitiesDrawn = 0;

        Rectangle screenBounds = new Rectangle(0, 0, panel.SCREEN_WIDTH, panel.SCREEN_HEIGHT);

        for (Entity entity : entities) {
            if (entity != null) {
                int screenX = entity.x - panel.cameraX;
                int screenY = entity.y - panel.cameraY;

                int xEntity = screenX + entity.collisionArea.x;
                int yEntity = screenY + entity.collisionArea.y;
                int widthEntity = entity.collisionArea.width;
                int heightEntity = entity.collisionArea.height;

                Rectangle entityBounds = new Rectangle(xEntity, yEntity, widthEntity, heightEntity);

                if (screenBounds.intersects(entityBounds)) {
                    entity.draw(g2);
                    entitiesDrawn++;
                }
            }
        }

        entitiesDrawnLastFrame = entitiesDrawn;
    }
}

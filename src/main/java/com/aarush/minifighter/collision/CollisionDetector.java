package com.aarush.minifighter.collision;

import com.aarush.minifighter.entity.Entity;
import com.aarush.minifighter.main.GamePanel;
import com.aarush.minifighter.object.GameObject;

import java.awt.Rectangle;

public class CollisionDetector {

    GamePanel panel;

    public CollisionDetector(GamePanel panel) {
        this.panel = panel;
    }

    public void checkTile(Entity entity) {
        int entityLeftX = entity.x + entity.collisionArea.x;
        int entityRightX = entity.x + entity.collisionArea.x + entity.collisionArea.width;
        int entityTopY = entity.y + entity.collisionArea.y;
        int entityBottomY = entity.y + entity.collisionArea.y + entity.collisionArea.height;

        int entityLeftCol = entityLeftX / panel.TILE_SIZE;
        int entityRightCol = entityRightX / panel.TILE_SIZE;
        int entityTopRow = entityTopY / panel.TILE_SIZE;
        int entityBottomRow = entityBottomY / panel.TILE_SIZE;

        int x = entity.x + entity.collisionArea.x;
        int y = entity.y + entity.collisionArea.y;
        int width = entity.collisionArea.width;
        int height = entity.collisionArea.height;

        Rectangle entityNextPos = new Rectangle(x, y, width, height);

        switch (entity.direction) {
            case "UP" -> {
                entityNextPos.y -= entity.speed;
                entityTopRow = (entityTopY - entity.speed) / panel.TILE_SIZE;
                checkTileCollision(entity, entityNextPos, entityLeftCol, entityRightCol, entityTopRow, entityTopRow);
            }
            case "DOWN" -> {
                entityNextPos.y += entity.speed;
                entityBottomRow = (entityBottomY + entity.speed) / panel.TILE_SIZE;
                checkTileCollision(entity, entityNextPos, entityLeftCol, entityRightCol, entityBottomRow, entityBottomRow);
            }
            case "LEFT" -> {
                entityNextPos.x -= entity.speed;
                entityLeftCol = (entityLeftX - entity.speed) / panel.TILE_SIZE;
                checkTileCollision(entity, entityNextPos, entityLeftCol, entityLeftCol, entityTopRow, entityBottomRow);
            }
            case "RIGHT" -> {
                entityNextPos.x += entity.speed;
                entityRightCol = (entityRightX + entity.speed) / panel.TILE_SIZE;
                checkTileCollision(entity, entityNextPos, entityRightCol, entityRightCol, entityTopRow, entityBottomRow);
            }
        }
    }

    public void checkTileCollision(Entity entity, Rectangle entityRect, int col1, int col2, int row1, int row2) {
        for (int col = col1; col <= col2; col++) {
            for (int row = row1; row <= row2; row++) {
                if (col >= 0 && col < panel.MAX_WORLD_COL && row >= 0 && row < panel.MAX_WORLD_ROW) {
                    int tileNum = panel.tileManager.tileMap[col][row];

                    if (tileNum < panel.tileManager.tiles.length && panel.tileManager.tiles[tileNum] != null && panel.tileManager.tiles[tileNum].collision) {
                        int x = col * panel.TILE_SIZE + panel.tileManager.tiles[tileNum].collisionArea.x;
                        int y = row * panel.TILE_SIZE + panel.tileManager.tiles[tileNum].collisionArea.y;
                        int width = panel.tileManager.tiles[tileNum].collisionArea.width;
                        int height = panel.tileManager.tiles[tileNum].collisionArea.height;

                        Rectangle tileCollisionRect = new Rectangle(x, y, width, height);
                        if (entityRect.intersects(tileCollisionRect)) {
                            entity.isCollisionDetected = true;
                            return;
                        }
                    }
                }
            }
        }
    }

    public boolean checkPlayer(Entity entity) {
        boolean contactPlayer = false;

        entity.collisionArea.x = entity.x + entity.collisionArea.x;
        entity.collisionArea.y = entity.y + entity.collisionArea.y;

        panel.player.collisionArea.x = panel.player.x + panel.player.collisionArea.x;
        panel.player.collisionArea.y = panel.player.y + panel.player.collisionArea.y;

        switch (entity.direction) {
            case "UP" -> entity.collisionArea.y -= entity.speed;
            case "DOWN" -> entity.collisionArea.y += entity.speed;
            case "LEFT" -> entity.collisionArea.x -= entity.speed;
            case "RIGHT" -> entity.collisionArea.x += entity.speed;
        }

        if (entity.collisionArea.intersects(panel.player.collisionArea)) {
            if (panel.player != entity) {
                entity.isCollisionDetected = true;
                contactPlayer = true;
            }
        }
        entity.collisionArea.x = entity.collisionAreaDefaultX;
        entity.collisionArea.y = entity.collisionAreaDefaultY;
        panel.player.collisionArea.x = panel.player.collisionAreaDefaultX;
        panel.player.collisionArea.y = panel.player.collisionAreaDefaultY;

        return contactPlayer;
    }

    public void checkObject(Entity entity) {
        int entityOriginalX = entity.collisionArea.x;
        int entityOriginalY = entity.collisionArea.y;

        entity.collisionArea.x = entity.x + entity.collisionArea.x;
        entity.collisionArea.y = entity.y + entity.collisionArea.y;

        switch (entity.direction) {
            case "UP" -> entity.collisionArea.y -= entity.speed;
            case "DOWN" -> entity.collisionArea.y += entity.speed;
            case "LEFT" -> entity.collisionArea.x -= entity.speed;
            case "RIGHT" -> entity.collisionArea.x += entity.speed;
        }

        for (int i = 0; i < panel.gameObjectManager.getGameObjects().size(); i++) {
            GameObject gameObject = panel.gameObjectManager.getGameObjects().get(i);

            if (gameObject != null && gameObject.hasCollision()) {
                int x = gameObject.getWorldX() + gameObject.getCollisionArea().x;
                int y = gameObject.getWorldY() + gameObject.getCollisionArea().y;
                int width = gameObject.getCollisionArea().width;
                int height = gameObject.getCollisionArea().height;

                Rectangle objectCollisionRect = new Rectangle(x, y, width, height);

                if (entity.collisionArea.intersects(objectCollisionRect)) {
                    entity.isCollisionDetected = true;
                    break;
                }
            }
        }

        entity.collisionArea.x = entityOriginalX;
        entity.collisionArea.y = entityOriginalY;
    }
}

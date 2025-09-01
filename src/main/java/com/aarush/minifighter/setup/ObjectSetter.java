package com.aarush.minifighter.setup;

import com.aarush.minifighter.main.GamePanel;
import com.aarush.minifighter.object.Board;

public class ObjectSetter {

    GamePanel panel;

    public ObjectSetter(GamePanel panel) {
        this.panel = panel;
    }

    public void setupAllGameObjects() {
        setupBoard();
    }

    private void setupBoard() {
        int tileX = 6, tileY = 7;
        Board board = new Board(panel, tileX * panel.TILE_SIZE, tileY * panel.TILE_SIZE);
        panel.gameObjectManager.addGameObject(board);
    }
}

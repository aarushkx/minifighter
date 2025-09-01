package com.aarush.minifighter.setup;

import com.aarush.minifighter.main.GamePanel;
import com.aarush.minifighter.object.entities.Board;
import com.aarush.minifighter.object.entities.TreeLarge;
import com.aarush.minifighter.object.entities.TreeSmall;
import com.aarush.minifighter.object.entities.TreeTall;
import com.aarush.minifighter.object.entities.TreeStump;

public class ObjectSetter {

    GamePanel panel;

    public ObjectSetter(GamePanel panel) {
        this.panel = panel;
    }

    public void setupAllGameObjects() {
        setupBoard();
        setupTreeLarge();
        setupTreeSmall();
        setupTreeTall();
        setupTreeStump();
    }

    private void setupBoard() {
        int tileX = 6, tileY = 7;
        Board board = new Board(panel, tileX * panel.TILE_SIZE, tileY * panel.TILE_SIZE);
        panel.gameObjectManager.addGameObject(board);
    }

    private void setupTreeLarge() {
        int tileX = 8, tileY = 5;
        TreeLarge treeLarge = new TreeLarge(panel, tileX * panel.TILE_SIZE, tileY * panel.TILE_SIZE);
        panel.gameObjectManager.addGameObject(treeLarge);
    }

    private void setupTreeSmall() {
        int tileX = 14, tileY = 5;
        TreeSmall treeSmall = new TreeSmall(panel, tileX * panel.TILE_SIZE, tileY * panel.TILE_SIZE);
        panel.gameObjectManager.addGameObject(treeSmall);
    }

    private void setupTreeTall() {
        int tileX = 6, tileY = 3;
        TreeTall treeTall = new TreeTall(panel, tileX * panel.TILE_SIZE, tileY * panel.TILE_SIZE);
        panel.gameObjectManager.addGameObject(treeTall);
    }

    private void setupTreeStump() {
        int tileX = 14, tileY = 2;
        TreeStump treeStump = new TreeStump(panel, tileX * panel.TILE_SIZE, tileY * panel.TILE_SIZE);
        panel.gameObjectManager.addGameObject(treeStump);
    }
}

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
        addBoard(16, 17);
    }

    private void setupTreeLarge() {
        addTreeLarge(17, 15);
        addTreeLarge(1, 29);
        addTreeLarge(30, 6);
        addTreeLarge(5, 3);
        addTreeLarge(35, 6);
        addTreeLarge(6, 15);
        addTreeLarge(36, 12);
        addTreeLarge(28, 29);
        addTreeLarge(8, 25);
    }

    private void setupTreeSmall() {
        addTreeSmall(23, 16);
        addTreeSmall(8, 2);
        addTreeSmall(2, 8);
        addTreeSmall(3, 18);
        addTreeSmall(38, 15);
        addTreeSmall(12, 28);
        addTreeSmall(32, 27);
        addTreeSmall(1, 1);
        addTreeSmall(37, 30);
        addTreeSmall(7, 27);
    }

    private void setupTreeTall() {
        addTreeTall(15, 12);
        addTreeTall(32, 4);
        addTreeTall(4, 12);
        addTreeTall(34, 9);
        addTreeTall(33, 18);
        addTreeTall(20, 26);
        addTreeTall(38, 1);
        addTreeTall(10, 4);
        addTreeTall(31, 28);
    }

    private void setupTreeStump() {
        addTreeStump(24, 12);
        addTreeStump(1, 5);
        addTreeStump(37, 8);
        addTreeStump(2, 22);
        addTreeStump(36, 24);
        addTreeStump(15, 6);
        addTreeStump(25, 8);
        addTreeStump(12, 23);
        addTreeStump(28, 25);
    }

    private void addBoard(int tileX, int tileY) {
        Board board = new Board(panel, tileX * panel.TILE_SIZE, tileY * panel.TILE_SIZE);
        panel.gameObjectManager.addGameObject(board);
    }

    private void addTreeLarge(int tileX, int tileY) {
        TreeLarge treeLarge = new TreeLarge(panel, tileX * panel.TILE_SIZE, tileY * panel.TILE_SIZE);
        panel.gameObjectManager.addGameObject(treeLarge);
    }

    private void addTreeSmall(int tileX, int tileY) {
        TreeSmall treeSmall = new TreeSmall(panel, tileX * panel.TILE_SIZE, tileY * panel.TILE_SIZE);
        panel.gameObjectManager.addGameObject(treeSmall);
    }

    private void addTreeTall(int tileX, int tileY) {
        TreeTall treeTall = new TreeTall(panel, tileX * panel.TILE_SIZE, tileY * panel.TILE_SIZE);
        panel.gameObjectManager.addGameObject(treeTall);
    }

    private void addTreeStump(int tileX, int tileY) {
        TreeStump treeStump = new TreeStump(panel, tileX * panel.TILE_SIZE, tileY * panel.TILE_SIZE);
        panel.gameObjectManager.addGameObject(treeStump);
    }
}

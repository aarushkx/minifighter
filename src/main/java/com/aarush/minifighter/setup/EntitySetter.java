package com.aarush.minifighter.setup;

import com.aarush.minifighter.entity.monster.Skeleton;
import com.aarush.minifighter.entity.monster.Slime;
import com.aarush.minifighter.main.GamePanel;

public class EntitySetter {

    GamePanel panel;

    public EntitySetter(GamePanel panel) {
        this.panel = panel;
    }

    public void setupAllEntities() {
        setupSlimes();
        setupSkeletons();
    }

    private void setupSlimes() {
        addSlime(15, 15);
        addSlime(26, 14);
        addSlime(8, 25);
    }

    private void setupSkeletons() {
        addSkeleton(13, 13);
        addSkeleton(16, 14);
    }

    private void addSlime(int tileX, int tileY) {
        Slime slime = new Slime(panel);
        slime.x = tileX * panel.TILE_SIZE;
        slime.y = tileY * panel.TILE_SIZE;
        panel.entityManager.addEntity(slime);
    }

    private void addSkeleton(int tileX, int tileY) {
        Skeleton skeleton = new Skeleton(panel);
        skeleton.x = tileX * panel.TILE_SIZE;
        skeleton.y = tileY * panel.TILE_SIZE;
        panel.entityManager.addEntity(skeleton);
    }
}

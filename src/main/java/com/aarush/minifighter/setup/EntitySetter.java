package com.aarush.minifighter.setup;

import com.aarush.minifighter.entity.monster.Slime;
import com.aarush.minifighter.main.GamePanel;

public class EntitySetter {

    GamePanel panel;

    public EntitySetter(GamePanel panel) {
        this.panel = panel;
    }

    public void setupAllEntities() {
        setupSlimes();
    }

    private void setupSlimes() {
        addSlime(15, 15);
        addSlime(26, 14);
        addSlime(8, 25);
    }

    private void addSlime(int tileX, int tileY) {
        Slime slime = new Slime(panel);
        slime.x = tileX * panel.TILE_SIZE;
        slime.y = tileY * panel.TILE_SIZE;
        panel.entityManager.addEntity(slime);
    }
}

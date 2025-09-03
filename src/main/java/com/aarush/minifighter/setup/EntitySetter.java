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
        Slime slime1 = new Slime(panel);
        slime1.x = panel.TILE_SIZE * 15;
        slime1.y = panel.TILE_SIZE * 15;
        panel.entityManager.addEntity(slime1);

        Slime slime2 = new Slime(panel);
        slime2.x = panel.TILE_SIZE * 26;
        slime2.y = panel.TILE_SIZE * 14;
        panel.entityManager.addEntity(slime2);

        Slime slime3 = new Slime(panel);
        slime3.x = panel.TILE_SIZE * 8;
        slime3.y = panel.TILE_SIZE * 25;
        panel.entityManager.addEntity(slime3);
    }
}

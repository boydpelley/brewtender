package Tile_Interactive;

import Entity.Entity;
import Main.GamePanel;

import java.awt.*;

public class InteractiveTile extends Entity {
    GamePanel gp;
    public boolean destructible = false;

    public InteractiveTile(GamePanel gp) {
        super(gp);
        this.gp = gp;
    }

    public boolean isCorrectTool(Entity entity) {
        return false;
    }

    public InteractiveTile getDestroyedForm() {
        return null;
    }

    public void update() {
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 20) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }


    public Color getParticleColor() {
        return new Color(65, 45, 30);
    }

    public int getParticleSize() {
        return 6;
    }

    public int getParticleSpeed() {
        return 1;
    }

    public int getParticleMaxLife() {
        return 20;
    }

}

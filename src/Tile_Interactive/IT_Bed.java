package Tile_Interactive;

import Entity.Entity;
import Main.GamePanel;

public class IT_Bed extends InteractiveTile {

    GamePanel gp;

    public IT_Bed(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;

        this.worldX = col * gp.tileSize;
        this.worldY = row * gp.tileSize;

        down1 = setup("tiles_interactive/bed_full", gp.tileSize, gp.tileSize * 2);
        destructible = false;

        solidArea.height = gp.tileSize * 2;
    }

    public void interact() {
        if (gp.envManager.lighting.dayState == gp.envManager.lighting.dusk ||
                gp.envManager.lighting.dayState == gp.envManager.lighting.night) {
            gp.gameState = gp.sleepState;
        } else {
            gp.ui.addMessage("Cannot sleep now.");
        }
    }
}

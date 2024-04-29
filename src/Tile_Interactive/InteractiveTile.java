package Tile_Interactive;

import Entity.Entity;
import Main.GamePanel;

public class InteractiveTile extends Entity {
    GamePanel gp;
    public boolean destructible = false;

    public InteractiveTile(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;
    }

    public void update() {

    }
}

package Tile_Interactive;

import Entity.Entity;
import Main.GamePanel;

import java.awt.*;

public class IT_SmallTree extends InteractiveTile {

    GamePanel gp;

    public IT_SmallTree(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = col * gp.tileSize;
        this.worldY = row * gp.tileSize;

        down1 = setup("tiles_interactive/small_tree", gp.tileSize, gp.tileSize);
        destructible = true;
        life = 6;
    }

    public boolean isCorrectTool(Entity entity) {
        return entity.currentTool.type == type_std_axe || entity.currentTool.type == type_upgraded_axe;
    }

    public InteractiveTile getDestroyedForm() {
        return new IT_SmallTreeTrunk(gp, worldX / gp.tileSize, worldY / gp.tileSize);
    }
}

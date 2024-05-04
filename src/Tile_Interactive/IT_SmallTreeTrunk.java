package Tile_Interactive;

import Main.GamePanel;

import java.awt.*;

public class IT_SmallTreeTrunk extends InteractiveTile{

    GamePanel gp;

    public IT_SmallTreeTrunk(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;

        this.worldX = col * gp.tileSize;
        this.worldY = row * gp.tileSize;

        down1 = setup("tiles_interactive/small_tree_trunk", gp.tileSize, gp.tileSize);

        // Since we are dealing with entities, we set the solid area to nothing so that the player
        // can walk through the trunk
        solidArea = new Rectangle(0, 0, 0, 0);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}

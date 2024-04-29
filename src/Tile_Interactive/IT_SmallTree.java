package Tile_Interactive;

import Main.GamePanel;

public class IT_SmallTree extends InteractiveTile {

    GamePanel gp;

    public IT_SmallTree(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = col * gp.tileSize;
        this.worldY = row * gp.tileSize;

        down1 = setup("tiles_interactive/small_tree", gp.tileSize, gp.tileSize);
        destructible = true;
    }
}

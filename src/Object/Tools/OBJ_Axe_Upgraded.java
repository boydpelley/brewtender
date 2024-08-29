package Object.Tools;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Axe_Upgraded extends Entity {

    public OBJ_Axe_Upgraded(GamePanel gp) {
        super(gp);

        type = type_upgraded_axe;
        name = "Steel Axe";
        down1 = setup("objects/player_items/axes/upgraded_axe", gp.tileSize, gp.tileSize);
        collision = true;
        destroyValue = 2;
        toolArea.width = 40;
        toolArea.height = 40;
        description = "An axe that brings an extra \npunch.";
    }
}

package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Axe_Upgraded extends Entity {

    public OBJ_Axe_Upgraded(GamePanel gp) {
        super(gp);

        name = "Steel Axe";
        down1 = setup("objects/upgraded_axe", gp.tileSize, gp.tileSize);
        collision = true;
        destroyValue = 2;
        description = "An axe that brings an extra \n punch.";
    }
}

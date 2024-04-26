package Object;

import Main.GamePanel;
import Entity.Entity;

public class OBJ_CrabApples extends Entity {

    public OBJ_CrabApples(GamePanel gp) {
        super(gp);

        name = "Crab Apples";
        down1 = setup("objects/crab_apples", gp.tileSize, gp.tileSize);
        collision = true;
        exp = 1;
        foraging = 1;
        description = "A sour tasting apple that adds tart flavours to cider.\nDon't eat too many of them!" ;
    }
}

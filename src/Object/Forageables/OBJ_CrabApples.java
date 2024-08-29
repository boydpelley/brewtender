package Object.Forageables;

import Main.GamePanel;
import Entity.Entity;

public class OBJ_CrabApples extends Entity {

    public OBJ_CrabApples(GamePanel gp) {
        super(gp);

        name = "Crab Apples";
        down1 = setup("objects/forageables/crab_apples", gp.tileSize, gp.tileSize);
        collision = true;
        exp = 1;
        foraging = 1;
        description = "A sour tasting apple that \nadds tart flavours to cider.\nDon't eat too many of them!";

        price = 1;
        stackable = true;
    }
}

package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_JuniperBunch extends Entity {

    public OBJ_JuniperBunch(GamePanel gp) {

        super(gp);

        name = "Juniper Bunch";
        down1 = setup("objects/juniper_bunch", gp.tileSize, gp.tileSize);
        collision = true;
        exp = 2;
        foraging = 1;
        description = "A berry that typically used \nin spicing foods and making gin.";

        price = 2;
        stackable = true;
    }
}

package Object.Tools;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Lantern extends Entity {

    public OBJ_Lantern(GamePanel gp) {
        super(gp);

        type = type_light;
        name = "Lantern";
        down1 = setup("objects/player_items/other/lantern", gp.tileSize, gp.tileSize);
        description = "Illuminates your surroundings.";
        price = 20;
        lightRadius = 250;
    }
}

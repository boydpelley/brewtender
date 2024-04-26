package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Axe_Std extends Entity {

    public OBJ_Axe_Std(GamePanel gp) {
        super(gp);

        name = "Standard Axe";
        down1 = setup("objects/axe_std", gp.tileSize, gp.tileSize);
        destroyValue = 1;
        description = "A basic axe used for chopping trees, when needed.";
    }
}

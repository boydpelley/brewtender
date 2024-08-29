package Object.Tools;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Axe_Std extends Entity {

    public OBJ_Axe_Std(GamePanel gp) {
        super(gp);

        type = type_std_axe;
        name = "Standard Axe";
        down1 = setup("objects/player_items/axes/axe_std", gp.tileSize, gp.tileSize);
        collision = true;
        destroyValue = 1;
        toolArea.width = 36;
        toolArea.height = 36;
        description = "A basic axe used for chopping \ntrees, when needed.";
    }
}

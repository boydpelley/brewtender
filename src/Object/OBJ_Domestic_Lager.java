package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Domestic_Lager extends Entity {

    GamePanel gp;
    int speedIncrease = 1;

    public OBJ_Domestic_Lager(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_consumable;
        name = "Domestic Beer";
        down1 = setup("objects/domestic_lager", gp.tileSize, gp.tileSize);
        description = "A light beer that tastes \nlike water and grain.";
    }

    public void use (Entity entity) {
        gp.gameState = gp.dialogueState;
        gp.ui.addMessage("Nothing like a domestic for a marathon!");

        if (gp.player.speed > 4) {
            entity.speed -= speedIncrease;
            gp.ui.addMessage("You start to feel sluggish...");
        }

        entity.speed += speedIncrease;
    }
}

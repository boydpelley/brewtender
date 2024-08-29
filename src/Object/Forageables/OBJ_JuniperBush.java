package Object.Forageables;

import Entity.Forage;
import Main.GamePanel;

public class OBJ_JuniperBush extends Forage {
    GamePanel gp;

    public OBJ_JuniperBush(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Juniper Bush";
        down1 = setup("objects/forageables/juniper_bush", gp.tileSize, gp.tileSize);
        collision = true;
    }
    public void setNewImage() {
        down1 = setup("objects/forageables/juniper_bush_no_berries", gp.tileSize, gp.tileSize);
    }
}

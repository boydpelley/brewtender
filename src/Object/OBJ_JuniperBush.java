package Object;

import Entity.Entity;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_JuniperBush extends Entity {

    public OBJ_JuniperBush(GamePanel gp) {

        super(gp);

        name = "Juniper Bush";
        down1 = setup("objects/juniper_bush.png");
        collision = true;
    }
}

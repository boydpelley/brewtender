package Object;

import Entity.Entity;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_JuniperBunch extends Entity {

    public OBJ_JuniperBunch(GamePanel gp) {

        super(gp);

        name = "Juniper Bunch";
        down1 = setup("objects/juniper_bunch.png");
        collision = true;
    }
}

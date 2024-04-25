package Object;

import Main.GamePanel;
import Entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_CrabApples extends Entity {

    public OBJ_CrabApples(GamePanel gp) {
        super(gp);

        name = "Crab Apples";
        down1 = setup("objects/crab_apples.png");
        collision = true;
    }
}

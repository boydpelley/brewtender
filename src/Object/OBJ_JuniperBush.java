package Object;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_JuniperBush extends SuperObject {

    GamePanel gp;
    public OBJ_JuniperBush(GamePanel gp) {

        this.gp = gp;

        name = "Juniper Bush";
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/juniper_bush.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}

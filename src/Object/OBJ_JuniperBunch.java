package Object;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_JuniperBunch extends SuperObject {

    GamePanel gp;
    public OBJ_JuniperBunch(GamePanel gp) {

        this.gp = gp;

        name = "Juniper Bunch";
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/juniper_bunch.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}

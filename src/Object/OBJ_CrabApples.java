package Object;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_CrabApples extends SuperObject {

    GamePanel gp;
    public OBJ_CrabApples(GamePanel gp) {

        name = "Crab Apples";
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/crab_apples.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}

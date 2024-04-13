package Object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_CrabApples extends SuperObject {

    public OBJ_CrabApples() {

        name = "Crab Apples";
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/crab_apples.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

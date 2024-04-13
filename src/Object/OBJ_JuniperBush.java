package Object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_JuniperBush extends SuperObject {

    public OBJ_JuniperBush() {

        name = "Juniper Bush";
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/juniper_bush.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

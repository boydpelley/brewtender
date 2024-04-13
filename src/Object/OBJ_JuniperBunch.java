package Object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_JuniperBunch extends SuperObject {

    public OBJ_JuniperBunch() {
        name = "Juniper Bunch";
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/juniper_bunch.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}

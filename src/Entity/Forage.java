package Entity;

import Main.GamePanel;

public class Forage extends Entity {

    public boolean foraged = false;

    public Forage(GamePanel gp) {
        super(gp);

    }

    public void setNewImage() {
        down1 = setup(null, gp.tileSize, gp.tileSize);
    }
}

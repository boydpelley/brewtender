package Entity;

import Main.GamePanel;

public class NPC_Cop extends Entity {

    public NPC_Cop(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 5;
    }
}

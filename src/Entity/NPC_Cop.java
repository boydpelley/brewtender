package Entity;

import Main.GamePanel;

import java.util.Random;

public class NPC_Cop extends Entity {

    public NPC_Cop(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 3;

        getImage();
    }

    public void getImage() {

        down1 = setup("npc/cop1");
        down2 = setup("npc/cop2");
        down3 = setup("npc/cop3");
        right1 = setup("npc/cop4");
        right2 = setup("npc/cop5");
        right3 = setup("npc/cop6");
        left1 = setup("npc/cop7");
        left2 = setup("npc/cop8");
        left3 = setup("npc/cop9");
        up1 = setup("npc/cop10");
        up2 = setup("npc/cop11");
        up3 = setup("npc/cop12");
    }

    public void setAction() {

        actionLockCounter++;

        if (actionLockCounter == 120) {
            Random random = new Random();

            // Pick a number from 1-100
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }

            actionLockCounter = 0;
        }
    }
}

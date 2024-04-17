package Entity;

import Main.GamePanel;

import java.util.Random;

public class NPC_Cop extends Entity {

    public NPC_Cop(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 3;

        getImage();
        setDialogue();
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

    public void setDialogue() {

        dialogues[0] = "Boy what in the sweet hell are you doing out here?!";
        dialogues[1] = "You didn't hear of anyone selling alcohol now did you?";
        dialogues[2] = "Don't mind me... Keep your eye out for anything suspicious...";
        dialogues[3] = "Damned moonshiners.";

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

    public void speak() {

        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
    }
}

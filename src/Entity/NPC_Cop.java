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

        down1 = setup("npc/cop1", gp.tileSize, gp.tileSize);
        down2 = setup("npc/cop2", gp.tileSize, gp.tileSize);
        down3 = setup("npc/cop3", gp.tileSize, gp.tileSize);
        right1 = setup("npc/cop4", gp.tileSize, gp.tileSize);
        right2 = setup("npc/cop5", gp.tileSize, gp.tileSize);
        right3 = setup("npc/cop6", gp.tileSize, gp.tileSize);
        left1 = setup("npc/cop7", gp.tileSize, gp.tileSize);
        left2 = setup("npc/cop8", gp.tileSize, gp.tileSize);
        left3 = setup("npc/cop9", gp.tileSize, gp.tileSize);
        up1 = setup("npc/cop10", gp.tileSize, gp.tileSize);
        up2 = setup("npc/cop11", gp.tileSize, gp.tileSize);
        up3 = setup("npc/cop12", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {

        dialogues[0] = "Boy what in the sweet hell are you doing out \nhere?!";
        dialogues[1] = "You didn't hear of anyone selling alcohol now \ndid you?";
        dialogues[2] = "Don't mind me... Keep your eye out for anything \nsuspicious...";
        dialogues[3] = "Damned moonshiners.";

    }

    public void update() {
        super.update();

        if (gp.player.suspicion > 25 && !onPath) {

            int xDist = Math.abs(worldX - gp.player.worldX);
            int yDist = Math.abs(worldY - gp.player.worldY);
            int tileDist = (xDist + yDist) / gp.tileSize;

            int tilePursue = 3 + (gp.player.suspicion / 50);

            int rand = new Random().nextInt(3) + 1;
            int pursue = rand * gp.player.suspicion;

            if (pursue > 100 && tileDist < tilePursue) {
                onPath = true;
            }
        }
    }

    public void setAction() {

        if (onPath) {
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

            searchPath(goalCol, goalRow);
        }
        else {
            actionLockCounter++;

            if (actionLockCounter == 120) {
                Random random = new Random();

                // Pick a number from 1-100
                int i = random.nextInt(100) + 1;

                if (i <= 25) {
                    direction = "up";
                }
                else if (i <= 50) {
                    direction = "down";
                }
                else if (i <= 75) {
                    direction = "left";
                }
                else {
                    direction = "right";
                }

                actionLockCounter = 0;
            }
        }
    }

    public void speak() {
        super.speak();
    }
}

package Entity;

import Main.GamePanel;
import Object.Alcohol.OBJ_Domestic_Lager;

public class NPC_StoreClerk extends Entity {

    public NPC_StoreClerk(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 0;

        getImage();
        setDialogue();
        setItems();
    }

    public void getImage() {

        down1 = setup("npc/store_clerk", gp.tileSize, gp.tileSize);
        down2 = setup("npc/store_clerk", gp.tileSize, gp.tileSize);
        down3 = setup("npc/store_clerk", gp.tileSize, gp.tileSize);
        right1 = setup("npc/store_clerk", gp.tileSize, gp.tileSize);
        right2 = setup("npc/store_clerk", gp.tileSize, gp.tileSize);
        right3 = setup("npc/store_clerk", gp.tileSize, gp.tileSize);
        left1 = setup("npc/store_clerk", gp.tileSize, gp.tileSize);
        left2 = setup("npc/store_clerk", gp.tileSize, gp.tileSize);
        left3 = setup("npc/store_clerk", gp.tileSize, gp.tileSize);
        up1 = setup("npc/store_clerk", gp.tileSize, gp.tileSize);
        up2 = setup("npc/store_clerk", gp.tileSize, gp.tileSize);
        up3 = setup("npc/store_clerk", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {

        dialogues[0] = "Hi... Welcome to Mick's";

    }

    public void setItems() {
        inventory.add(new OBJ_Domestic_Lager(gp));
    }

    public void speak() {
        super.speak();
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;
    }

}

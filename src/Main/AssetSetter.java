package Main;

import Entity.NPC_Cop;
import Object.*;
import Tile_Interactive.IT_SmallTree;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setDroppable() {

        gp.droppable[0] = new OBJ_JuniperBush(gp);
        gp.droppable[0].worldX = 35 * gp.tileSize;
        gp.droppable[0].worldY = 17 * gp.tileSize;

        gp.droppable[1] = new OBJ_JuniperBush(gp);
        gp.droppable[1].worldX = 10 * gp.tileSize;
        gp.droppable[1].worldY = 23 * gp.tileSize;
    }

    public void setObject() {

        gp.obj[0] = new OBJ_CrabApples(gp);
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 30 * gp.tileSize;

        gp.obj[1] = new OBJ_CrabApples(gp);
        gp.obj[1].worldX = 15 * gp.tileSize;
        gp.obj[1].worldY = 5 * gp.tileSize;

        gp.obj[2] = new OBJ_Axe_Std(gp);
        gp.obj[2].worldX = 20 * gp.tileSize;
        gp.obj[2].worldY = 20 * gp.tileSize;

        gp.obj[3] = new OBJ_Axe_Upgraded(gp);
        gp.obj[3].worldX = 23 * gp.tileSize;
        gp.obj[3].worldY = 18 * gp.tileSize;

        gp.obj[4] = new OBJ_Domestic_Lager(gp);
        gp.obj[4].worldX = 24 * gp.tileSize;
        gp.obj[4].worldY = 19 * gp.tileSize;

        gp.obj[5] = new OBJ_Domestic_Lager(gp);
        gp.obj[5].worldX = 24 * gp.tileSize;
        gp.obj[5].worldY = 22 * gp.tileSize;

        gp.obj[6] = new OBJ_Domestic_Lager(gp);
        gp.obj[6].worldX = 25 * gp.tileSize;
        gp.obj[6].worldY = 24 * gp.tileSize;

    }

    public void setNPC() {
        gp.npc[0] = new NPC_Cop(gp);
        gp.npc[0].worldX = 20 * gp.tileSize;
        gp.npc[0].worldY = 25 * gp.tileSize;
    }

    public void setInteractiveTile() {

        int i = 0;

        gp.iTile[i] = new IT_SmallTree(gp, 9, 15);
        i++;

        gp.iTile[i] = new IT_SmallTree(gp, 10, 15);
        i++;

        gp.iTile[i] = new IT_SmallTree(gp, 11, 15);
        i++;

        gp.iTile[i] = new IT_SmallTree(gp, 12, 15);
        i++;

        gp.iTile[i] = new IT_SmallTree(gp, 13, 15);
        i++;

        gp.iTile[i] = new IT_SmallTree(gp, 14, 15);

    }
}

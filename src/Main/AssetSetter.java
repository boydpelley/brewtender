package Main;

import Entity.NPC_Cop;
import Entity.NPC_StoreClerk;
import Object.Alcohol.OBJ_Domestic_Lager;
import Object.Forageables.OBJ_CrabApples;
import Object.Forageables.OBJ_JuniperBush;
import Object.Tools.OBJ_Axe_Std;
import Object.Tools.OBJ_Axe_Upgraded;
import Object.Tools.OBJ_Lantern;
import Tile_Interactive.IT_Bed;
import Tile_Interactive.IT_SmallTree;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setDroppable() {
        int mapNum = 0;

        gp.droppable[mapNum][0] = new OBJ_JuniperBush(gp);
        gp.droppable[mapNum][0].worldX = 35 * gp.tileSize;
        gp.droppable[mapNum][0].worldY = 15 * gp.tileSize;

        gp.droppable[mapNum][1] = new OBJ_JuniperBush(gp);
        gp.droppable[mapNum][1].worldX = 10 * gp.tileSize;
        gp.droppable[mapNum][1].worldY = 23 * gp.tileSize;
    }

    public void setObject() {
        int mapNum = 0;

        gp.obj[mapNum][0] = new OBJ_CrabApples(gp);
        gp.obj[mapNum][0].worldX = 23 * gp.tileSize;
        gp.obj[mapNum][0].worldY = 30 * gp.tileSize;

        gp.obj[mapNum][1] = new OBJ_CrabApples(gp);
        gp.obj[mapNum][1].worldX = 15 * gp.tileSize;
        gp.obj[mapNum][1].worldY = 5 * gp.tileSize;

        gp.obj[mapNum][2] = new OBJ_Axe_Std(gp);
        gp.obj[mapNum][2].worldX = 20 * gp.tileSize;
        gp.obj[mapNum][2].worldY = 20 * gp.tileSize;

        gp.obj[mapNum][3] = new OBJ_Axe_Upgraded(gp);
        gp.obj[mapNum][3].worldX = 23 * gp.tileSize;
        gp.obj[mapNum][3].worldY = 18 * gp.tileSize;

        gp.obj[mapNum][4] = new OBJ_Domestic_Lager(gp);
        gp.obj[mapNum][4].worldX = 24 * gp.tileSize;
        gp.obj[mapNum][4].worldY = 19 * gp.tileSize;

        gp.obj[mapNum][5] = new OBJ_Domestic_Lager(gp);
        gp.obj[mapNum][5].worldX = 24 * gp.tileSize;
        gp.obj[mapNum][5].worldY = 22 * gp.tileSize;

        gp.obj[mapNum][6] = new OBJ_Domestic_Lager(gp);
        gp.obj[mapNum][6].worldX = 25 * gp.tileSize;
        gp.obj[mapNum][6].worldY = 24 * gp.tileSize;

        gp.obj[mapNum][7] = new OBJ_Lantern(gp);
        gp.obj[mapNum][7].worldX = 15 * gp.tileSize;
        gp.obj[mapNum][7].worldY = 18 * gp.tileSize;
    }

    public void setNPC() {
        int mapNum = 0;

        gp.npc[mapNum][0] = new NPC_Cop(gp);
        gp.npc[mapNum][0].worldX = 20 * gp.tileSize;
        gp.npc[mapNum][0].worldY = 30 * gp.tileSize;

        gp.npc[mapNum][1] = new NPC_StoreClerk(gp);
        gp.npc[mapNum][1].worldX = 25 * gp.tileSize;
        gp.npc[mapNum][1].worldY = 25 * gp.tileSize;
    }

    public void setInteractiveTile() {
        int mapNum = 0;
        int i = 0;

        gp.iTile[mapNum][i] = new IT_SmallTree(gp, 9, 15);
        i++;

        gp.iTile[mapNum][i] = new IT_SmallTree(gp, 10, 15);
        i++;

        gp.iTile[mapNum][i] = new IT_SmallTree(gp, 11, 15);
        i++;

        gp.iTile[mapNum][i] = new IT_SmallTree(gp, 12, 15);
        i++;

        gp.iTile[mapNum][i] = new IT_SmallTree(gp, 13, 15);
        i++;

        gp.iTile[mapNum][i] = new IT_SmallTree(gp, 14, 15);

        mapNum++;
        i = 0;

        gp.iTile[mapNum][i] = new IT_Bed(gp, 30, 10);

    }
}

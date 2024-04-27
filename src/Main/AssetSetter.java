package Main;

import Entity.NPC_Cop;
import Object.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        gp.obj[0] = new OBJ_CrabApples(gp);
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 30 * gp.tileSize;

        gp.obj[1] = new OBJ_CrabApples(gp);
        gp.obj[1].worldX = 15 * gp.tileSize;
        gp.obj[1].worldY = 5 * gp.tileSize;

        gp.obj[2] = new OBJ_JuniperBush(gp);
        gp.obj[2].worldX = 35 * gp.tileSize;
        gp.obj[2].worldY  = 17 * gp.tileSize;

        gp.obj[3] = new OBJ_JuniperBush(gp);
        gp.obj[3].worldX = 10 * gp.tileSize;
        gp.obj[3].worldY = 23 * gp.tileSize;

        gp.obj[4] = new OBJ_JuniperBunch(gp);
        gp.obj[4].worldX = 36 * gp.tileSize;
        gp.obj[4].worldY = 17 * gp.tileSize;

        gp.obj[5] = new OBJ_JuniperBunch(gp);
        gp.obj[5].worldX = 9 * gp.tileSize;
        gp.obj[5].worldY = 24 * gp.tileSize;

        gp.obj[6] = new OBJ_Axe_Std(gp);
        gp.obj[6].worldX = 20 * gp.tileSize;
        gp.obj[6].worldY = 20 * gp.tileSize;

        gp.obj[7] = new OBJ_Axe_Upgraded(gp);
        gp.obj[7].worldX = 23 * gp.tileSize;
        gp.obj[7].worldY = 18 * gp.tileSize;

        gp.obj[8] = new OBJ_Domestic_Lager(gp);
        gp.obj[8].worldX = 24 * gp.tileSize;
        gp.obj[8].worldY = 19 * gp.tileSize;

        gp.obj[9] = new OBJ_Domestic_Lager(gp);
        gp.obj[9].worldX = 24 * gp.tileSize;
        gp.obj[9].worldY = 22 * gp.tileSize;

    }

    public void setNPC() {
        gp.npc[0] = new NPC_Cop(gp);
        gp.npc[0].worldX = gp.tileSize * 20;
        gp.npc[0].worldY = gp.tileSize * 25;
    }
}

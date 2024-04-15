package Main;

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

    }
}

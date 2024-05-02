package Tile;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;


    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[50];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("maps/temp_world_map.txt");
    }

    public void getTileImage() {
        setup(0, "grass", false);
        setup(1, "stone", false);
        setup(2, "water", true);
        setup(30, "stone_wall1", true);
        setup(31, "stone_wall2", true);
        setup(5, "trees2", true);
        setup(6, "trees3", true);

        setup(10, "grass/grass", false);
        setup(11, "grass/grass_var1", false);
        setup(12, "grass/grass_var2", false);
        setup(13, "grass/grass_var3", false);
        setup(14, "grass/grass_var4", false);

        setup(15, "grass/grass_bl", false);
        setup(16, "grass/grass_bottom", false);
        setup(17, "grass/grass_br", false);
        setup(18, "grass/grass_left", false);

        setup(19, "grass/grass_o_bl", false);
        setup(20, "grass/grass_o_blb", false);
        setup(21, "grass/grass_o_bld", false);
        setup(22, "grass/grass_o_bll", false);

        setup(23, "grass/grass_o_br", false);
        setup(24, "grass/grass_o_brb", false);
        setup(25, "grass/grass_o_brd", false);
        setup(26, "grass/grass_o_brr", false);

        setup(27, "grass/grass_o_tl", false);
        setup(28, "grass/grass_o_tld", false);
        setup(29, "grass/grass_o_tll", false);
        setup(30, "grass/grass_o_tlt", false);

        setup(31, "grass/grass_o_tr", false);
        setup(32, "grass/grass_o_trd", false);
        setup(33, "grass/grass_o_trr", false);
        setup(34, "grass/grass_o_trt", false);

        setup(35, "grass/grass_right", false);
        setup(36, "grass/grass_tl", false);
        setup(37, "grass/grass_top", false);
        setup(38, "grass/grass_tr", false);

        setup(39, "dirt/dirt", false);
        setup(40, "dirt/dirt1", false);
        setup(41, "dirt/dirt2", false);
        setup(42, "dirt/dirt3", false);
        setup(43, "dirt/dirt4", false);
    }

    public void setup(int index, String imageName, boolean collision) {

        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/" + imageName + ".png")));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try{
            InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine();

                while (col < gp.maxWorldCol) {

                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;


        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize >  gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenX) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }


            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}

package Main;

import java.awt.*;
import java.awt.image.BufferedImage;

import Object.OBJ_CrabApples;

public class UI {

    GamePanel gp;
    Font arial_40;
    BufferedImage crabApplesImage;

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        OBJ_CrabApples crabApples = new OBJ_CrabApples();
        crabApplesImage = crabApples.image;
    }

    public void draw(Graphics g2) {

        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);
        g2.drawImage(crabApplesImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
        g2.drawString(": " + gp.player.hasCrabApples, 74, 65);
    }
}

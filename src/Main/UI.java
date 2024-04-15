package Main;

import java.awt.*;
import java.awt.image.BufferedImage;

import Object.OBJ_CrabApples;

public class UI {

    GamePanel gp;
    Font arial_40;
    BufferedImage crabApplesImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        OBJ_CrabApples crabApples = new OBJ_CrabApples(gp);
        crabApplesImage = crabApples.image;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics g2) {

        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);
        g2.drawImage(crabApplesImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
        g2.drawString(": " + gp.player.hasCrabApples, 74, 65);

        if (messageOn) {
            g2.drawString(message, gp.tileSize * 9, 65);

            messageCounter++;

            if (messageCounter > 120) {
                messageCounter = 0;
                messageOn = false;
            }
        }
    }
}

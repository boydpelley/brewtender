package Main;


import java.awt.*;
import java.awt.image.BufferedImage;

import Object.OBJ_CrabApples;

public class UI {

    GamePanel gp;
    Graphics2D g2;
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

    public void draw(Graphics2D g2) {
        this.g2 = g2;

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

        if (gp.gameState == gp.playState) {

        }
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen(g2);
        }
    }

    public void drawPauseScreen(Graphics2D g2) {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public int getXCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
}

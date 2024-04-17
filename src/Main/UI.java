package Main;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import Object.OBJ_CrabApples;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font retroGaming;
    BufferedImage crabApplesImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public String currentDialogue = "";

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("font/Retro_Gaming.ttf");
            retroGaming = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        OBJ_CrabApples crabApples = new OBJ_CrabApples(gp);
        crabApplesImage = crabApples.image;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(retroGaming);
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

        // Play state
        if (gp.gameState == gp.playState) {

        }

        // Pause state
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen(g2);
        }

        // Dialogue state
        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }
    }

    public void drawPauseScreen(Graphics2D g2) {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {

        // Window for dialogue
        int x = gp.tileSize * 2;
        int y = gp.tileSize;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 5;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 22F));
        x += gp.tileSize;
        y += gp.tileSize;

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }

    }

    public void drawSubWindow(int x, int y, int width, int height) {

        Color dialogueColor = new Color(12, 12, 12, 220);
        g2.setColor(dialogueColor);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        dialogueColor = new Color(250, 250, 250, 220);
        g2.setStroke(new BasicStroke(5));
        g2.setColor(dialogueColor);
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

    }

    public int getXCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
}

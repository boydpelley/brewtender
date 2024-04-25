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
    public int commandNum = 0;

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


        // Title state
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // Play state
        if (gp.gameState == gp.playState) {
            drawHotBar();
        }

        // Pause state
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen(g2);
        }

        // Dialogue state
        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }

        // Character state
        if (gp.gameState == gp.characterState) {
            drawCharacterStatScreen();
        }
    }

    public void drawTitleScreen() {

        g2.setColor(Color.PINK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Title name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String gameTitle = "Brewtender";
        int x = getXCenteredText(gameTitle);
        int y = gp.tileSize * 3;

        // Text "shadow"
        g2.setColor(new Color(55, 55, 55));
        g2.drawString(gameTitle, x + 5, y + 5);

        // Main colour of text
        g2.setColor(Color.WHITE);
        g2.drawString(gameTitle, x, y);

        // TODO: Add beer sprite when added
        x = (gp.screenWidth / 2) - (gp.tileSize * 2) / 2;
        y += gp.tileSize * 2;
        // Use player character for now
        g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

        // Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

        String text = "NEW GAME";
        x = getXCenteredText(text);
        y += gp.tileSize * 5;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "LOAD GAME";
        x = getXCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "QUIT";
        x = getXCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - gp.tileSize, y);
        }
    }

    public void drawHotBar() {

        // The box for the hot-bar
        int x = gp.tileSize * 4;
        int y = gp.screenHeight - (gp.tileSize * 2);
        int width = gp.screenWidth - (gp.tileSize * 8);
        int height = gp.tileSize;

        Color hotBarColor = new Color(155, 155, 155, 250);

        g2.setColor(hotBarColor);
        g2.fillRect(x, y, width, height);

        hotBarHelper(x, y, width, height);
    }

    public void hotBarHelper(int startX, int startY, int width, int height) {

        Color borderColor = new Color(125, 125, 125, 175);

        int endX = startX + width;

        g2.setStroke(new BasicStroke(4));
        g2.setColor(borderColor);
        while (startX < endX) {
            g2.drawRect(startX, startY, gp.tileSize, height);
            startX += gp.tileSize;
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

    public void drawCharacterStatScreen() {

        // Create a frame
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 7;
        final int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Text
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(24F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 36;

        // Attribute Names
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Money", textX, textY);
        textY += lineHeight;
        g2.drawString("Reputation", textX, textY);
        textY += lineHeight;
        g2.drawString("Foraging", textX, textY);
        textY += lineHeight;
        g2.drawString("Beer Brewing", textX, textY);
        textY += lineHeight;
        g2.drawString("Cider Brewing", textX, textY);
        textY += lineHeight;
        g2.drawString("Distilling", textX, textY);
        textY += lineHeight;
        g2.drawString("Marketability", textX, textY);
        textY += lineHeight;
        g2.drawString("Suspicion", textX, textY);
        textY += lineHeight;

        // Atrribute Values
        int tailX = (frameX + frameWidth) - 30;
        // Reset textY
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXForRightAlign(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXForRightAlign(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevel);
        textX = getXForRightAlign(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXForRightAlign(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.reputation);
        textX = getXForRightAlign(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.foraging);
        textX = getXForRightAlign(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.beerBrewing);
        textX = getXForRightAlign(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.ciderBrewing);
        textX = getXForRightAlign(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.distilling);
        textX = getXForRightAlign(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.marketability);
        textX = getXForRightAlign(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.suspicion);
        textX = getXForRightAlign(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

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

    public int getXForRightAlign(String text, int tailX) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
}

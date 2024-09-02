package Main;

import Entity.Entity;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    public Font retroGaming;
    public ArrayList<String> message = new ArrayList<>();
    public ArrayList<Integer> messageCounter = new ArrayList<>();
    public String currentDialogue = "";
    public int commandNum = 0;
    public int playerSlotRow = 0;
    public int playerSlotCol = 0;
    public int npcSlotRow = 0;
    public int npcSlotCol = 0;
    int subState = 0;
    int counter = 0;
    public Entity npc;

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("font/Retro_Gaming.ttf");
            assert is != null;
            retroGaming = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(retroGaming);
        g2.setColor(Color.WHITE);

        // Title state
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // Play state
        if (gp.gameState == gp.playState) {
            //drawHotBar();
            drawMessage();
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
            drawInventory(gp.player, true);
        }

        // Options state
        if (gp.gameState == gp.optionsState) {
            drawOptionsScreen();
        }

        // Transition state
        if (gp.gameState == gp.transitionState) {
            drawTransition();
        }

        // Trade state
        if (gp.gameState == gp.tradeState) {
            drawTradeScreen();
        }

        // Sleep state
        if (gp.gameState == gp.sleepState) {
            drawSleepScreen();
        }
    }

    public void drawMessage() {

        int messageX = gp.tileSize * 10;
        int messageY = gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(20F));

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {

                // Text shadow
                g2.setColor(new Color(55, 55, 55));
                g2.drawString(message.get(i), messageX + 2, messageY + 2);

                g2.setColor(Color.WHITE);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
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
        y += gp.tileSize * 4;
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

    public void drawPauseScreen(Graphics2D g2) {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {

        // Window for dialogue
        int x = gp.tileSize * 3;
        int y = gp.tileSize;
        int width = gp.screenWidth - (gp.tileSize * 6);
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

    }

    public void drawOptionsScreen() {
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(25F));

        //Sub window
        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0 -> optionsTop(frameX, frameY);
            case 1 -> optionsFullScreenNotification(frameX, frameY);
            case 2 -> optionsControls(frameX, frameY);
            case 3 -> optionsEndGameConfirmation(frameX, frameY);
        }

        gp.keyH.enterPressed = false;
    }

    public void optionsTop(int frameX, int frameY) {
        // Title
        String text = "Options";
        int textX = getXCenteredText(text);
        int textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        // Full screen on / off
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Full Screen", textX, textY);
        if (commandNum == 0) {
            g2.drawString( ">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                gp.fullScreenOn = !gp.fullScreenOn;
                subState = 1;
            }
        }

        // Music
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if (commandNum == 1) {
            g2.drawString( ">", textX - 25, textY);
        }

        // Sound Effects
        textY += gp.tileSize;
        g2.drawString("SE", textX, textY);
        if (commandNum == 2) {
            g2.drawString( ">", textX - 25, textY);
        }

        // Controls
        textY += gp.tileSize;
        g2.drawString("Controls", textX, textY);
        if (commandNum == 3) {
            g2.drawString( ">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 2;
                commandNum = 0;
            }
        }

        // End Game
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if (commandNum == 4) {
            g2.drawString( ">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 3;
                commandNum = 0;
            }
        }

        // Back
        textY += gp.tileSize * 2;
        g2.drawString("Back", textX, textY);
        if (commandNum == 5) {
            g2.drawString( ">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
                gp.gameState = gp.playState;
            }
        }

        // Full screen check box
        textX = frameX + gp.tileSize * 6;
        textY = frameY + gp.tileSize * 2 + (gp.tileSize / 2);
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        if (gp.fullScreenOn) {
            g2.fillRect(textX, textY, 24, 24);
        }

        // Music Volume
        textX = frameX + gp.tileSize * 5;
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24); // 120/5 = 24
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        // Sound Effect volume
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.sound.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        gp.config.saveConfig();
    }

    public void optionsFullScreenNotification(int frameX, int frameY) {

        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "The change will \ntake effect after \nrelaunching the \ngame.";

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // Back
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) subState = 0;
        }
    }

    public void optionsControls(int frameX, int frameY) {
        String text = "Controls";
        int textX = getXCenteredText(text);
        int textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        g2.setFont(g2.getFont().deriveFont(19F));
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY); textY += 35;
        g2.drawString("Confirm", textX, textY); textY += 35;
        g2.drawString("Use tool", textX, textY); textY += 35;
        g2.drawString("Interact Forage", textX, textY); textY += 35;
        g2.drawString("Talk", textX, textY); textY += 35;
        g2.drawString("Character Screen", textX, textY); textY += 35;
        g2.drawString("Pause", textX, textY); textY += 35;
        g2.drawString("Options", textX, textY);

        textX = frameX + gp.tileSize * 6;
        textY = frameY + gp.tileSize * 2;
        g2.drawString("WASD", textX, textY); textY += 35;
        g2.drawString("Enter", textX, textY); textY += 35;
        g2.drawString("E", textX, textY); textY += 35;
        g2.drawString("Q", textX, textY); textY += 35;
        g2.drawString("T", textX, textY); textY += 35;
        g2.drawString("C", textX, textY); textY += 35;
        g2.drawString("P", textX, textY); textY += 35;
        g2.drawString("Esc", textX, textY);

        g2.setFont(g2.getFont().deriveFont(25F));
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
                commandNum = 3;
            }
        }
    }

    public void optionsEndGameConfirmation(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "Quit game and \nreturn to title?";

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 30;
        }

        // Yes
        String text = "Yes";
        textX = getXCenteredText(text);
        textY += gp.tileSize * 3;
        g2.drawString(text, textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
                gp.gameState = gp.titleState;
            }
        }

        // No
        text = "No";
        textX = getXCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
                commandNum = 4;
            }
        }
    }

    public void drawTransition() {
        counter++;
        g2.setColor(new Color(0, 0, 0, counter * 5));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        if (counter == 35) {
            counter = 0;
            gp.gameState = gp.playState;
            gp.currentMap = gp.eventHandler.tempMap;
            gp.player.worldX = gp.tileSize * gp.eventHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eventHandler.tempRow;
            gp.eventHandler.previousEventX = gp.player.worldX;
            gp.eventHandler.previousEventY = gp.player.worldY;
        }
    }

    public void drawTradeScreen() {

        switch (subState) {
            case 0 -> tradeSelect();
            case 1 -> tradeBuy();
            case 2 -> tradeSell();
        }
        gp.keyH.enterPressed = false;
    }

    public void tradeSelect() {
        drawDialogueScreen();

        // Draw window
        int x = gp.tileSize * 15;
        int y = gp.tileSize * 6;
        int width = gp.tileSize * 3;
        int height = (int) (gp.tileSize * 3.5);
        drawSubWindow(x, y, width, height);

        // Draw text
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Buy", x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 24, y);
            if (gp.keyH.enterPressed) {
                subState = 1;
            }
        }
        y += gp.tileSize;
        g2.drawString("Sell", x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 24, y);
            if (gp.keyH.enterPressed) {
                subState = 2;
            }
        }
        y += gp.tileSize;
        g2.drawString("Exit", x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - 24, y);
            if (gp.keyH.enterPressed) {
                commandNum = 0;
                gp.gameState = gp.dialogueState;
                currentDialogue = "Thanks come again.";
            }
        }
    }

    public void tradeBuy() {

        // Draw player inventory
        drawInventory(gp.player, false);

        // Draw npc inventory
        drawInventory(npc, true);

        g2.setFont(g2.getFont().deriveFont(20F));

        // Draw hint window
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 10;
        int width = gp.tileSize * 6;
        int height = (int)(gp.tileSize * 1.5);
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x + 24, y + 45);

        // Draw player coin window
        x = gp.tileSize * 12;
        drawSubWindow(x, y, width, height);
        g2.drawString("Balance: " + gp.player.coin, x + 24, y + 45);

        // Draw price window
        int itemIndex = getItemIndexFromSlots(npcSlotCol, npcSlotRow);
        if (itemIndex < npc.inventory.size()) {
            x = (int)(gp.tileSize * 5.5);
            y = (int)(gp.tileSize * 6.5);
            width = (int)(gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawString("$: ", x + 10, y + 30);

            int price = npc.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXForRightAlign(text, gp.tileSize * 8 - 20);
            g2.drawString(text, x, y + 30);

            // Buying an item
            if (gp.keyH.enterPressed) {
                if (npc.inventory.get(itemIndex).price > gp.player.coin) {
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "You need more money to buy that.";
                    drawDialogueScreen();
                }
                else {
                    if (gp.player.canObtainItem(npc.inventory.get(itemIndex))) {
                        gp.player.coin -= npc.inventory.get(itemIndex).price;
                    }
                    else {
                        subState = 0;
                        gp.gameState = gp.dialogueState;
                        currentDialogue = "You cannot carry any more items.\nTry tossing a few things away.";
                    }
                }
//                else if (gp.player.inventory.size() == gp.player.maxInventorySize) {
//                    subState = 0;
//                    gp.gameState = gp.dialogueState;
//                    currentDialogue = "You cannot carry any more items.\nTry tossing a few things away.";
//                }
//                else {
//                    gp.player.coin -= npc.inventory.get(itemIndex).price;
//                    gp.player.inventory.add(npc.inventory.get(itemIndex));
//                }
            }
        }

    }

    public void tradeSell() {

        // Draw player inventory
        drawInventory(gp.player, true);

        g2.setFont(g2.getFont().deriveFont(20F));

        // Draw hint window
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 10;
        int width = gp.tileSize * 6;
        int height = (int)(gp.tileSize * 1.5);
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x + 24, y + 45);

        // Draw player coin window
        x = gp.tileSize * 12;
        drawSubWindow(x, y, width, height);
        g2.drawString("Balance: " + gp.player.coin, x + 24, y + 45);

        // Draw price window
        int itemIndex = getItemIndexFromSlots(playerSlotCol, playerSlotRow);
        if (itemIndex < gp.player.inventory.size()) {
            x = (int)(gp.tileSize * 15.5);
            y = (int)(gp.tileSize * 6.5);
            width = (int)(gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawString("$: ", x + 10, y + 30);

            int price = (int)(gp.player.inventory.get(itemIndex).price * 0.7);
            String text = "" + price;
            x = getXForRightAlign(text, gp.tileSize * 18 - 20);
            g2.drawString(text, x, y + 30);

            // Sell an item
            if (gp.keyH.enterPressed) {
                if (gp.player.inventory.get(itemIndex) == gp.player.currentTool) {
                    commandNum = 0;
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "You cannot sell an equipped item.";
                }
                else {
                    if (gp.player.inventory.get(itemIndex).amount > 1) {
                        gp.player.inventory.get(itemIndex).amount--;
                    }
                    else {
                        gp.player.inventory.remove(itemIndex);
                    }
                    gp.player.coin += price;
                }
            }
        }

    }

    public void drawInventory(Entity entity, boolean cursor) {

        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if (entity == gp.player) {
            frameX = gp.tileSize * 12;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 6;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        }
        else {
            frameX = gp.tileSize * 2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 6;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Inventory slots
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gp.tileSize + 2; // This is used to balance out the inventory slots

        // Draw items
        for (int i = 0; i < entity.inventory.size(); i++) {

            // For showing the cursor for equipables
            if (entity.inventory.get(i) == entity.currentTool || entity.inventory.get(i) == entity.currentLight) {
                g2.setColor(Color.PINK);
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }

            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

            // Display item amounts
            if (entity.inventory.get(i).amount > 1) {
                g2.setFont(g2.getFont().deriveFont((32f)));
                int amountX;
                int amountY;

                String s = "" + entity.inventory.get(i).amount;
                amountX = getXForRightAlign(s, slotX + 44);
                amountY = slotY + gp.tileSize;

                // Number shadow
                g2.setColor(new Color(60, 60, 60));
                g2.drawString(s, amountX, amountY);

                // Number
                g2.setColor(Color.WHITE);
                g2.drawString(s, amountX - 3, amountY - 3);
            }

            slotX += slotSize;
            if (i == 4 || i == 9 || i == 14 || i == 19) {
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        // Draw Cursor
        if (cursor) {
            int cursorX = slotXStart + (slotSize * slotCol);
            int cursorY = slotYStart + (slotSize * slotRow);
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;

            // Draw cursor
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            // Description frame
            int dFrameY = frameY + frameHeight;
            int dFrameHeight = gp.tileSize * 3;

            // Draw description
            int textX = frameX + 20;
            int textY = dFrameY + gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(12F));

            int itemIndex = getItemIndexFromSlots(slotCol, slotRow);

            if (itemIndex < entity.inventory.size()) {
                drawSubWindow(frameX, dFrameY, frameWidth, dFrameHeight);

                for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 30;
                }
            }
        }
    }

    private void drawSleepScreen() {
        counter++;

        if (counter < 120) {
            gp.envManager.lighting.filterAlpha += 0.01f;
            if (gp.envManager.lighting.filterAlpha > 1f) {
                gp.envManager.lighting.filterAlpha = 1f;
            }
        }
        if (counter >= 120) {
            gp.envManager.lighting.filterAlpha -= 0.01f;
            if (gp.envManager.lighting.filterAlpha <= 0f) {
                gp.envManager.lighting.filterAlpha = 0f;
                counter = 0;
                gp.envManager.lighting.dayState = gp.envManager.lighting.day;
                gp.gameState = gp.playState;
            }
        }

    }

    public int getItemIndexFromSlots(int slotCol, int slotRow) {
        return slotCol + (slotRow * 5);
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
        return gp.screenWidth / 2 - length / 2;
    }

    public int getXForRightAlign(String text, int tailX) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length;
    }
}

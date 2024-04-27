package Entity;

import Main.GamePanel;
import Main.KeyHandler;
import Object.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 25;

    // Requirements to level up skills
    public int nextForagingLevel, nextBeerLevel, nextCiderLevel, nextDistillingLevel, nextRepLevel, nextMarketingLevel;

    // Exp points associated
    public int foragingExp, beerExp, ciderExp, distillingExp, marketingExp, reputationExp;


    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // Solid Area of the player
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        // Destroy area for the player's axe
        toolArea.width = 36;
        toolArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerAxeImage();
        setItems();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 22;
        worldY = gp.tileSize * 22;
        speed = 4;
        direction = "down";

        // Player Status
        level = 1;
        foraging = 1;
        marketability = 1;
        reputation = 1;
        beerBrewing = 1;
        ciderBrewing = 1;
        distilling = 1;
        coin = 0;
        suspicion = 1;

        // Default the experience points
        exp = 0;
        foragingExp = 0;
        beerExp = 0;
        ciderExp = 0;
        distillingExp = 0;
        marketingExp = 0;
        reputationExp = 0;

        // Set the next levels
        nextLevel = 10;
        nextForagingLevel = 50;
        nextBeerLevel = 25;
        nextCiderLevel = 25;
        nextDistillingLevel = 20;
        nextRepLevel = 25;
        nextMarketingLevel = 50;

        // Default tool
        currentTool = new OBJ_Axe_Std(gp);
    }

    public void setItems() {

    }

    public void getPlayerImage() {

        down1 = setup("player/player1", gp.tileSize, gp.tileSize);
        down2 = setup("player/player2", gp.tileSize, gp.tileSize);
        down3 = setup("player/player4", gp.tileSize, gp.tileSize);
        right1 = setup("player/player5", gp.tileSize, gp.tileSize);
        right2 = setup("player/player6", gp.tileSize, gp.tileSize);
        right3 = setup("player/player8", gp.tileSize, gp.tileSize);
        left1 = setup("player/player9", gp.tileSize, gp.tileSize);
        left2 = setup("player/player10", gp.tileSize, gp.tileSize);
        left3 = setup("player/player12", gp.tileSize, gp.tileSize);
        up1 = setup("player/player13", gp.tileSize, gp.tileSize);
        up2 = setup("player/player14", gp.tileSize, gp.tileSize);
        up3 = setup("player/player16", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAxeImage() {
        axeDown1 = setup("player/axe_swing_y1", gp.tileSize, gp.tileSize * 2);
        axeDown2 = setup("player/axe_swing_y2", gp.tileSize, gp.tileSize * 2);
        axeUp1 = setup("player/axe_swing_y3", gp.tileSize, gp.tileSize * 2);
        axeUp2 = setup("player/axe_swing_y4", gp.tileSize, gp.tileSize * 2);
        axeLeft1 = setup("player/axe_swing_x3", gp.tileSize * 2, gp.tileSize);
        axeLeft2 = setup("player/axe_swing_x4", gp.tileSize * 2, gp.tileSize);
        axeRight1 = setup("player/axe_swing_x1", gp.tileSize * 2, gp.tileSize);
        axeRight2 = setup("player/axe_swing_x2", gp.tileSize * 2, gp.tileSize);

    }

    public void update() {
        if (usingTool) {
            useTool();
        }
        else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.tPressed) {
            gp.playSoundEffect(1);
            spriteCounter++;
            if (spriteCounter < 8) {
                spriteNum = 0;
            }
            if (spriteCounter == 8) {
                spriteNum = 1;
            }
            if (spriteCounter == 16) {
                spriteNum = 0;
            }
            if (spriteCounter == 24) {
                spriteNum = 2;
            }
            if (spriteCounter >= 32) {
                spriteCounter = 0;
            }
        }

        if (keyH.upPressed) {
            direction = "up";
        }
        if (keyH.downPressed) {
            direction = "down";
        }

        if (keyH.leftPressed) {
            direction = "left";
        }
        if (keyH.rightPressed) {
            direction = "right";
        }

        // Check tile collision
        collisionOn = false;
        gp.cChecker.checkTile(this);

        // Check object collision
        int objectIndex = gp.cChecker.checkObject(this, true);
        pickupObject(objectIndex);

        // Check NPC Collision
        int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
        interactNPC(npcIndex);

        // Check event
        gp.eventHandler.checkEvent();

        // If collision is false, player CAN move
        if (!collisionOn && !keyH.tPressed) {
            if (keyH.upPressed) {
                worldY -= speed;
            }
            else if (keyH.downPressed) {
                worldY += speed;
            }

            if (keyH.leftPressed) {
                worldX -= speed;
            }
            else if (keyH.rightPressed) {
                worldX += speed;
            }
        }

        // We want to reset the t key being pressed afterwards
        gp.keyH.tPressed = false;

        if (!keyH.upPressed && !keyH.downPressed && !keyH.leftPressed && !keyH.rightPressed && !usingTool) {
            spriteNum = 0;
        }

    }

    public void useTool() {
        spriteCounter++;

        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            //Save the current coords
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Adjust player's worldX/Y for the attackArea
            switch (direction) {
                case "up" -> worldY -= toolArea.height;
                case "down" -> worldY += toolArea.height;
                case "left" -> worldX -= toolArea.width;
                case "right" -> worldX += toolArea.width;
            }

            solidArea.width = toolArea.width;
            solidArea.height = toolArea.height;

            // TODO: implement checking tree tiles
            // int treeIndex = gp.cChecker.checkTile();

            // Restore to original position
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            usingTool = false;
        }

    }

    public void pickupObject(int i) {

        if (i != 999) {
            String text;
            if (inventory.size() != maxInventorySize) {
                inventory.add(gp.obj[i]);
                text = "Collected " + gp.obj[i].name + "!";

                exp += gp.obj[i].exp;
                foraging += gp.obj[i].exp;

                checkExpLevelUp();
                checkForagingLevelUp();
            } else {
                text = "Inventory full.";
            }
            gp.ui.addMessage(text);
            gp.obj[i] = null;
        }
    }

    public void checkExpLevelUp() {
        if (exp >= nextLevel) {
            level++;
            nextLevel *= 4;
        }
    }

    public void checkForagingLevelUp() {
        if (foragingExp >= nextForagingLevel) {
            foraging++;
            nextForagingLevel *= 4;
        }
    }

    public void interactNPC(int i) {
        if (i != 999) {
            if (gp.keyH.tPressed) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
        else if (gp.keyH.ePressed) {
            usingTool = true;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        int tempScreenX = screenX;
        int tempScreenY = screenY;

        if (usingTool) {
            switch (direction) {
                case "up" -> {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) image = axeUp1;
                    if (spriteNum == 2) image = axeUp2;
                }
                case "down" -> {
                    if (spriteNum == 1) image = axeDown1;
                    if (spriteNum == 2) image = axeDown2;
                }
                case "left" -> {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) image = axeLeft1;
                    if (spriteNum == 2) image = axeLeft2;
                }
                case "right" -> {
                    if (spriteNum == 1) image = axeRight1;
                    if (spriteNum == 2) image = axeRight2;
                }
            }
        }
        else {
            image = getImageForDirectionAndSpriteNum(direction, spriteNum);
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);
    }
}

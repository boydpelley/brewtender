package Entity;

import Main.GamePanel;
import Main.KeyHandler;
import Object.Tools.OBJ_Axe_Std;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    // Requirements to level up skills
    public int nextForagingLevel, nextBeerLevel, nextCiderLevel, nextDistillingLevel, nextRepLevel, nextMarketingLevel;

    // Exp points associated
    public int foragingExp, beerExp, ciderExp, distillingExp, marketingExp, reputationExp;
    public boolean lightUpdated = false;


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
        coin = 50;
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
        if (currentTool.type == type_std_axe) {
            axeDown1 = setup("player/axe_swing_y1", gp.tileSize, gp.tileSize * 2);
            axeDown2 = setup("player/axe_2_swing_y1", gp.tileSize, gp.tileSize * 2);
            axeUp1 = setup("player/axe_swing_y3", gp.tileSize, gp.tileSize * 2);
            axeUp2 = setup("player/axe_2_swing_y2", gp.tileSize, gp.tileSize * 2);
            axeLeft1 = setup("player/axe_swing_x3", gp.tileSize * 2, gp.tileSize);
            axeLeft2 = setup("player/axe_2_swing_x2", gp.tileSize * 2, gp.tileSize);
            axeRight1 = setup("player/axe_swing_x1", gp.tileSize * 2, gp.tileSize);
            axeRight2 = setup("player/axe_2_swing_x1", gp.tileSize * 2, gp.tileSize);
        }
        if (currentTool.type == type_upgraded_axe) {
            axeDown1 = setup("player/axe_swing_y1", gp.tileSize, gp.tileSize * 2);
            axeDown2 = setup("player/axe_swing_y2", gp.tileSize, gp.tileSize * 2);
            axeUp1 = setup("player/axe_swing_y3", gp.tileSize, gp.tileSize * 2);
            axeUp2 = setup("player/axe_swing_y4", gp.tileSize, gp.tileSize * 2);
            axeLeft1 = setup("player/axe_swing_x3", gp.tileSize * 2, gp.tileSize);
            axeLeft2 = setup("player/axe_swing_x4", gp.tileSize * 2, gp.tileSize);
            axeRight1 = setup("player/axe_swing_x1", gp.tileSize * 2, gp.tileSize);
            axeRight2 = setup("player/axe_swing_x2", gp.tileSize * 2, gp.tileSize);
        }
    }

    public void update() {
        if (usingTool) {
            useTool();
        }
        else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.tPressed) {
            if (!keyH.tPressed) {
                gp.playSoundEffect(1);
            }
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

        // Check for collision of objects that drop items
        gp.cChecker.checkObject(this, false, gp.droppable);

        // Check object collision for pickup
        int objectIndex = gp.cChecker.checkObject(this, true, gp.obj);
        pickupObject(objectIndex, gp.obj);

        // Check the droppable object collision
        objectIndex = gp.cChecker.checkObject(this, true, gp.objDropped);
        pickupObject(objectIndex, gp.objDropped);

        // Check NPC Collision
        int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
        interactNPC(npcIndex);

        gp.cChecker.checkEntity(this, gp.iTile);

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

            if (keyH.upPressed && keyH.rightPressed || keyH.upPressed && keyH.leftPressed ||
                    keyH.downPressed && keyH.rightPressed || keyH.downPressed && keyH.leftPressed) {
                speed = 3;
            } else speed = 4;
        }

        // Reset the tPressed back to false. Must do it here rather than KeyHandler because the player stops moving if
        // they try to talk to NPCs
        keyH.tPressed = false;

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

            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
            damageInteractiveTile(iTileIndex);

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

    private void damageInteractiveTile(int i) {

        if (i != 999 && gp.iTile[gp.currentMap][i].destructible && gp.iTile[gp.currentMap][i].isCorrectTool(this)
        && !gp.iTile[gp.currentMap][i].invincible) {
            gp.iTile[gp.currentMap][i].life -= currentTool.destroyValue;
            gp.iTile[gp.currentMap][i].invincible = true;

            // Generate particle
            generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);

            if (gp.iTile[gp.currentMap][i].life == 0) {
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
            }
        }
    }

    public void updateToolArea() {
        toolArea = currentTool.toolArea;
    }

    public void pickupObject(int i, Entity[][] list) {

        if (i != 999) {
            if (canObtainItem(list[gp.currentMap][i])) {
                String text;
                if (inventory.size() != maxInventorySize) {
                    //inventory.add(list[gp.currentMap][i]);
                    text = "Collected " + list[gp.currentMap][i].name + "!";

                    exp += list[gp.currentMap][i].exp;
                    foragingExp += list[gp.currentMap][i].exp;

                    checkExpLevelUp();
                    checkForagingLevelUp();
                } else {
                    text = "Inventory full.";
                }
                gp.ui.addMessage(text);
                list[gp.currentMap][i] = null;
            }
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

    public void selectItem() {
        int itemIndex = gp.ui.getItemIndexFromSlots(gp.ui.playerSlotCol, gp.ui.playerSlotRow);

        if (itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == type_std_axe || selectedItem.type == type_upgraded_axe) {

                currentTool = selectedItem;
                updateToolArea();
                getPlayerAxeImage();
            }
            if (selectedItem.type == type_consumable) {
                // This will be implemented when we add consumables
                selectedItem.use(this);
                if (selectedItem.amount > 1) {
                    selectedItem.amount--;
                } else {
                    inventory.remove(itemIndex);
                }
                gp.gameState = gp.playState;
            }
            if (selectedItem.type == type_light) {
                if (currentLight == selectedItem) {
                    currentLight = null;
                } else {
                    currentLight = selectedItem;
                }
                lightUpdated = true;
            }

        }
    }

    public void interactNPC(int i) {
        if (i != 999) {
            if (gp.keyH.tPressed) {
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][i].speak();
            }
        }
        else if (gp.keyH.ePressed) {
            usingTool = true;
        }
    }

    public int searchItemInInventory(String itemName) {

        int itemIndex = 999;

        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.equals(itemName)) {
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }

    public boolean canObtainItem(Entity item) {

        boolean canObtain = false;
        // Check if stackable
        if (item.stackable) {
            int index = searchItemInInventory(item.name);

            if (index != 999) {
                inventory.get(index).amount++;
                canObtain = true;
            }
            // Else we have a new item
            else {
                if (inventory.size() != maxInventorySize) {
                    inventory.add(item);
                    canObtain = true;
                }
            }
        }
        // Not a stackable item, check if there is space
        else {
            if (inventory.size() != maxInventorySize) {
                inventory.add(item);
                canObtain = true;
            }
        }
        return canObtain;
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

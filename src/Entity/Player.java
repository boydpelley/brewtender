package Entity;

import Main.GamePanel;
import Main.KeyHandler;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;

public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    int hasJuniperBunch = 0;
    public int hasCrabApples = 0;
    public int dialogueCoolDown = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 22;
        worldY = gp.tileSize * 22;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {

        down1 = setup("player/player1");
        down2 = setup("player/player2");
        down3 = setup("player/player4");
        right1 = setup("player/player5");
        right2 = setup("player/player6");
        right3 = setup("player/player8");
        left1 = setup("player/player9");
        left2 = setup("player/player10");
        left3 = setup("player/player12");
        up1 = setup("player/player13");
        up2 = setup("player/player14");
        up3 = setup("player/player16");
    }

    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
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
        else if (keyH.downPressed) {
            direction = "down";
        }

        if (keyH.leftPressed) {
            direction = "left";
        }
        else if (keyH.rightPressed) {
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

        // If collision is false, player CAN move
        if (collisionOn == false) {
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

        if (!keyH.upPressed && !keyH.downPressed && !keyH.leftPressed && !keyH.rightPressed) {
            spriteNum = 0;
        }

    }

    public void pickupObject(int i) {

        if (i != 999) {
            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "Juniper Bunch":
                    hasJuniperBunch++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("Collected juniper bunch!");
                    break;
                case "Crab Apples":
                    hasCrabApples++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("Collected crab apples!");
                    break;
            }
        }
    }

    public void interactNPC(int i) {
        dialogueCoolDown++;
        if (i != 999 && dialogueCoolDown == 1) {
            gp.gameState = gp.dialogueState;
            gp.npc[i].speak();
        }

        if (dialogueCoolDown == 10) dialogueCoolDown = 0;
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = down1;

        switch(direction) {
            case "up":
                if (spriteNum == 0) {
                    image = up1;
                }
                if (spriteNum == 1) {
                    image = up2;
                }
                if (spriteNum == 2) {
                    image = up3;
                }
                break;
            case "down":
                if (spriteNum == 0) {
                    image = down1;
                }
                if (spriteNum == 1) {
                    image = down2;
                }
                if (spriteNum == 2) {
                    image = down3;
                }
                break;
            case "left":
                if (spriteNum == 0) {
                    image = left1;
                }
                if (spriteNum == 1) {
                    image = left2;
                }
                if (spriteNum == 2) {
                    image = left3;
                }
                break;
            case "right":
                if (spriteNum == 0) {
                    image = right1;
                }
                if (spriteNum == 1) {
                    image = right2;
                }
                if (spriteNum == 2) {
                    image = right3;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, null);
    }
}

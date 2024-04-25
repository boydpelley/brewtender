package Entity;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Entity {
    GamePanel gp;
    public int worldX, worldY;
    public BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3;
    public String direction = "down";

    public int spriteCounter = 0;
    public int spriteNum = 0;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    String dialogues[] = new String[20];
    int dialogueIndex = 0;
    public boolean collision = false;

    // Character Attributes
    public String name;
    public int speed;
    public int foraging;
    public int suspicion;
    public int marketability;
    public int reputation;
    public int beerBrewing;
    public int ciderBrewing;
    public int distilling;
    public int level;
    public int nextLevel;
    public int coin;
    public int exp;
    public Entity currentTool;

    // Item Attributes
    public int destroyValue;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {

    }

    public void speak() {

        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }

        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.direction) {
            case "up": direction = "down"; break;
            case "down": direction = "up"; break;
            case "left": direction = "right"; break;
            case "right": direction = "left"; break;
        }
    }

    public void update() {

        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkPlayer(this);

        // If collision is false, player CAN move
        if (collisionOn == false) {
            switch (direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }

        spriteCounter++;
        if (spriteCounter < 10) {
            spriteNum = 0;
        }
        if (spriteCounter == 10) {
            spriteNum = 1;
        }
        if (spriteCounter == 20) {
            spriteNum = 0;
        }
        if (spriteCounter == 30) {
            spriteNum = 2;
        }
        if (spriteCounter >= 40) {
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize >  gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenX) {
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

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    public BufferedImage setup(String path, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(path + ".png"));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}

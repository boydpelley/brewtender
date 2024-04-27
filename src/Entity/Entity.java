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
    public BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3;
    public BufferedImage axeUp1, axeUp2, axeDown1, axeDown2, axeLeft1, axeLeft2, axeRight1, axeRight2;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle toolArea = new Rectangle(0, 0, 36, 36);
    public int solidAreaDefaultX, solidAreaDefaultY;
    String[] dialogues = new String[20];
    public boolean collision = false;

    // State
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 0;
    int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean usingTool = false;

    // Counter variables
    public int spriteCounter = 0;
    public int actionLockCounter = 0;

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
    public String description = "";

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

        switch (gp.player.direction) {
            case "up" -> direction = "down";
            case "down" -> direction = "up";
            case "left" -> direction = "right";
            case "right" -> direction = "left";
        }
    }

    public void update() {

        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkPlayer(this);

        // If collision is false, player CAN move
        if (!collisionOn) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
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
            image = getImageForDirectionAndSpriteNum(direction, spriteNum);

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    public BufferedImage getImageForDirectionAndSpriteNum(String direction, int spriteNum) {
        return switch (direction) {
            case "up" -> getImageForDirection(up1, up2, up3, spriteNum);
            case "down" -> getImageForDirection(down1, down2, down3, spriteNum);
            case "left" -> getImageForDirection(left1, left2, left3, spriteNum);
            case "right" -> getImageForDirection(right1, right2, right3, spriteNum);
            default -> null;
        };
    }

    private BufferedImage getImageForDirection(BufferedImage img1, BufferedImage img2, BufferedImage img3, int spriteNum) {
        return switch (spriteNum) {
            case 0 -> img1;
            case 1 -> img2;
            case 2 -> img3;
            default -> null;
        };
    }

    public BufferedImage setup(String path, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path + ".png")));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}

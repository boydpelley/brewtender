package Entity;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
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
    public boolean active = true;
    public boolean onPath = false;

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
    public Entity currentLight;

    // Item Attributes
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 25;
    public int destroyValue;
    public String description = "";
    public int maxLife;
    public int life;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    public int price;
    public boolean stackable = false;
    public int amount = 1;
    public int lightRadius;

    // Type
    public int type;
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_std_axe = 2;
    public final int type_upgraded_axe = 3;
    public final int type_consumable = 4;
    public final int type_light = 5;

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

    public void use(Entity entity) {}

    public void dropItem(Entity droppedItem) {
        droppedItem.worldX = this.worldX + gp.tileSize;
        droppedItem.worldY = this.worldY + gp.tileSize;
        for (int i = 0; i < gp.objDropped[1].length; i++) {
            if (gp.objDropped[gp.currentMap][i] == null) {
                gp.objDropped[gp.currentMap][i] = droppedItem;
                break;
            }
        }
    }

    public Color getParticleColor() {
        return new Color(0, 0, 0);
    }
    public int getParticleSize() {
        return 0;
    }

    public int getParticleSpeed() {
        return 0;
    }

    public int getParticleMaxLife() {
        return 0;
    }

    public void generateParticle(Entity generator, Entity target) {

        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -1, -1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 1, -1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -1, 1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 1, 1);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }

    public void checkCollision() {
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false, gp.obj);
        gp.cChecker.checkObject(this, false, gp.droppable);
        gp.cChecker.checkPlayer(this);
    }

    public void update() {

        setAction();
        checkCollision();

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

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
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

            g2.drawImage(image, screenX, screenY, null);
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

    public void searchPath(int goalCol, int goalRow) {

        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;

        gp.pFinder.setNode(startCol, startRow, goalCol, goalRow);

        if (gp.pFinder.search()) {
            // Next worldX and worldY
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            // Entity's solidArea position
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            // Check relative positions and adjust direction accordingly
            if (worldX < nextX) {
                direction = "right";
            } else if (worldX > nextX) {
                direction = "left";
            } else if (worldY < nextY) {
                direction = "down";
            } else if (worldY > nextY) {
                direction = "up";
            }

            // Adjust direction based on collision
            if (collisionOn) {
                // If collision is detected, try alternative directions
                if (enTopY > nextY && direction.equals("up")) {
                    if (enLeftX > nextX) {
                        direction = "left";
                    } else if (enRightX < nextX) {
                        direction = "right";
                    }
                } else if (enBottomY < nextY && direction.equals("down")) {
                    if (enLeftX > nextX) {
                        direction = "left";
                    } else if (enRightX < nextX) {
                        direction = "right";
                    }
                } else if (enLeftX > nextX && direction.equals("left")) {
                    if (enTopY > nextY) {
                        direction = "up";
                    } else if (enBottomY < nextY) {
                        direction = "down";
                    }
                } else if (enRightX < nextX && direction.equals("right")) {
                    if (enTopY > nextY) {
                        direction = "up";
                    } else if (enBottomY < nextY) {
                        direction = "down";
                    }
                }
            }
        }
    }
}

package Entity;

import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try{

            down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player1.png"));
            down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player2.png"));
            down3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player4.png"));
            right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player5.png"));
            right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player6.png"));
            right3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player8.png"));
            left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player9.png"));
            left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player10.png"));
            left3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player12.png"));
            up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player13.png"));
            up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player14.png"));
            up3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player16.png"));

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
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
            y -= speed;
        }
        else if (keyH.downPressed) {
            direction = "down";
            y += speed;
        }

        if (keyH.leftPressed) {
            direction = "left";
            x -= speed;
        }
        else if (keyH.rightPressed) {
            direction = "right";
            x += speed;
        }

        if (!keyH.upPressed && !keyH.downPressed && !keyH.leftPressed && !keyH.rightPressed) {
            spriteNum = 0;
        }


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
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}

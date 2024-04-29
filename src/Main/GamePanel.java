package Main;

import Entity.Entity;
import Entity.Player;
import Tile.TileManager;
import Tile_Interactive.InteractiveTile;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16 x 16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48 x 48 tile
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 15;
    // Screen ratio would be 4:3
    public final int screenWidth = tileSize * maxScreenCol; // 960 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 720 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS
    int FPS = 60;

    // System
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound sound = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eventHandler = new EventHandler(this);
    Thread gameThread;

    // Entities and Objects
    public Player player = new Player(this, keyH);
    public Entity[] obj = new Entity[15];
    public Entity[] npc = new Entity[10];
    public Entity[] droppable = new Entity[10];
    public Entity[] objDropped = new Entity[50];
    public InteractiveTile[] iTile = new InteractiveTile[30];
    ArrayList<Entity> entityList = new ArrayList<>();

    // Game State
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;


    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.pink);
        this.setDoubleBuffered(true);

        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {

        aSetter.setDroppable();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setInteractiveTile();
        //music.playMusic(0);
        gameState = titleState;
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = (double)1000000000 / FPS; // 0.016666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        long timer = System.nanoTime();

        while (gameThread != null) {

            update();

            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;


                if (System.nanoTime() - timer > 1000000000) { // If a second has passed
                    timer = System.nanoTime();
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update() {

        if (gameState == playState) {
            // Player
            player.update();

            // NPC
            for (Entity entity : npc) {
                if (entity != null) {
                    entity.update();
                }
            }

            for (InteractiveTile t : iTile) {
                if (t != null) {
                    t.update();
                }
            }
        }
        /*
        if (gameState == pauseState) {
            // Nothing for now
        }
         */

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        // Debugging
        long drawStart = 0;
        if (keyH.showDebug) {
            drawStart = System.nanoTime();
        }

        // Title screen
        if (gameState == titleState) {
            ui.draw(g2);
        }
        else {
            // Tiles
            tileM.draw(g2);

            // Interactive tiles
            for (InteractiveTile tile : iTile) {
                if (tile != null) {
                    tile.draw(g2);
                }
            }

            // Add entities to the list
            entityList.add(player);

            for (Entity n : npc) {
                if (n != null) {
                    entityList.add(n);
                }
            }

            for (Entity drop : droppable) {
                if (drop != null) {
                    entityList.add(drop);
                }
            }

            for (Entity ob : obj) {
                if (ob != null) {
                    entityList.add(ob);
                }
            }

            // Add everything that exists in the objects dropped array
            for (Entity ob : objDropped) {
                if (ob != null) {
                    entityList.add(ob);
                }
            }

            // Sort the list by the worldY position
            entityList.sort(Comparator.comparingInt(e -> e.worldY));

            // Draw entities
            for (Entity entity : entityList) {
                entity.draw(g2);
            }

            // Empty entity list because their positions may change
            entityList.clear();

            // UI
            ui.draw(g2);
        }

        // For debugging
        if (keyH.showDebug) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.WHITE);
            int x = 10;
            int y = 400;
            int lineHeight = 20;

            g2.drawString("WorldX: " + player.worldX, x, y);
            y += lineHeight;
            g2.drawString("WorldY: " + player.worldY, x, y);
            y += lineHeight;

            g2.drawString("Col: " + (player.worldX + player.solidArea.x) / tileSize, x, y);
            y += lineHeight;
            g2.drawString("Row: " + (player.worldY + player.solidArea.y) / tileSize, x, y);
            y += lineHeight;

            g2.drawString("Draw Time: " + passed, x, y);
        }


        g2.dispose();
    }


    public void playSoundEffect(int i) {
        if (!sound.isPlaying) {
            sound.setFile(i);
            sound.play();
            sound.isPlaying = true;
        }
        sound.clip.addLineListener(event -> {
            if (event.getType() == LineEvent.Type.STOP) {
                sound.isPlaying = false;
            }
        });
    }


}

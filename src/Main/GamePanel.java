package Main;

import Entity.Entity;
import Entity.Player;
import Tile.TileManager;
import Tile_Interactive.InteractiveTile;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16 x 16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48 x 48 tile
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    // Screen ratio would be 16:9
    public final int screenWidth = tileSize * maxScreenCol; // 960 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxMap = 10;
    public int currentMap = 0;

    // FULL SCREEN SETTINGS
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;

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
    Config config = new Config(this);
    Thread gameThread;

    // Entities and Objects
    public Player player = new Player(this, keyH);
    public Entity[][] obj = new Entity[maxMap][15];
    public Entity[][] npc = new Entity[maxMap][10];
    public Entity[][] droppable = new Entity[maxMap][10];
    public Entity[][] objDropped = new Entity[maxMap][50];
    public InteractiveTile[][] iTile = new InteractiveTile[maxMap][30];
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    // Game State
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int transitionState = 6;
    public final int tradeState = 7;


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

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();

        if (fullScreenOn) setFullScreen();
    }

    public void setFullScreen() {

        // Get local screen device
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        // Get full screen width and height
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
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

            drawToTempScreen(); // This draws everything to buffered image
            drawToScreen(); // Drw the buffered image to the screen

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
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }

            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }

            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    if (particleList.get(i).active) {
                        particleList.get(i).update();
                    }
                    if (!particleList.get(i).active) {
                        particleList.remove(i);
                    }
                }
            }
        }
    }

    public void drawToTempScreen() {

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
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2);
                }
            }

            // Add entities to the list
            entityList.add(player);

            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }

            for (int i = 0; i < droppable[1].length; i++) {
                if (droppable[currentMap][i] != null) {
                    entityList.add(droppable[currentMap][i]);
                }
            }

            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);
                }
            }

            // Add everything that exists in the objects dropped array
            for (int i = 0; i < objDropped[1].length; i++) {
                if (objDropped[currentMap][i] != null) {
                    entityList.add(objDropped[currentMap][i]);
                }
            }

            for (Entity p : particleList) {
                if (p != null) {
                    entityList.add(p);
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

    }

    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
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

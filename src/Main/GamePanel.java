package Main;

import Entity.Player;
import Tile.TileManager;
import Object.SuperObject;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.*;
import java.awt.*;

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
    KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound sound = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    // Entities and Objects
    public Player player = new Player(this, keyH);
    public SuperObject[] obj = new SuperObject[10];

    // Game State
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;


    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.pink);
        this.setDoubleBuffered(true);

        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {

        aSetter.setObject();
        playMusic(0);
        stopMusic();
        gameState = playState;
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS; // 0.016666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        long timer = System.nanoTime();
        int drawCount = 0;

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

                drawCount++;

                if (System.nanoTime() - timer > 1000000000) { // If a second has passed
                    //System.out.println("FPS: " + drawCount);
                    drawCount = 0;
                    timer = System.nanoTime();
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update() {

        if (gameState == playState) {
            player.update();
        }
        if (gameState == pauseState) {
            // Nothing for now
        }

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        // Debugging
        long drawStart = 0;
        if (keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }


        tileM.draw(g2);
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }
        player.draw(g2);

        // UI
        ui.draw(g2);

        if (keyH.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setColor(Color.WHITE);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time: " + passed);
        }


        g2.dispose();
    }

    // TO BE USED WHEN MUSIC IS ADDED
    public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {

        music.stop();
    }

    public void playSoundEffect(int i) {
        if (!sound.isPlaying) {
            sound.setFile(i);
            sound.play();
            sound.isPlaying = true;
        }
        sound.clip.addLineListener(new LineListener() {
            @Override
            public void update(LineEvent event) {
                if (event.getType() == LineEvent.Type.STOP) {
                    sound.isPlaying = false;
                }
            }
        });
    }


}

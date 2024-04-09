package Main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16 x 16 tile
    final int scale = 3;

    final int tileSize = originalTileSize * scale; // 48 x 48 tile
    final int maxScreenCol = 20;
    final int maxScreenRow = 15;
    // Screen ratio would be 4:3
    final int screenWidth = tileSize * maxScreenCol; // 960 pixels
    final int screenHeight = tileSize * maxScreenRow; // 720 pixels

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.pink);
        this.setDoubleBuffered(true);
    }
}

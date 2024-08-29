package Environment;

import Main.GamePanel;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Lighting {
    GamePanel gp;
    BufferedImage darknessFilter;

    public Lighting(GamePanel gp) {
        this.gp = gp;
        setLightSource();
    }

    public void setLightSource() {
        // Create a buffered image to have 'darkness' surround the character
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        if (gp.player.currentLight == null) {
            g2.setColor(new Color(0, 0, 0, 0.90f));
        } else {
            // Center values for x and y to draw the circle from these points
            int centerX = gp.player.screenX + (gp.tileSize / 2);
            int centerY = gp.player.screenY + (gp.tileSize / 2);

            // Create a gradient effect
            Color[] color = new Color[5];
            float[] fraction = new float[5];

            for (int i = 0; i < 5; i++) {
                float val = (float) (i * 0.25);
                if (i == 4) {
                    val = 0.98f;
                }
                color[i] = new Color(0, 0, 0, val);
            }

            for (int i = 0; i < 5; i++) {
                float val = (float) (i * 0.25);
                fraction[i] = val;
            }

            // Create gradient paint settings for light circle
            RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, gp.player.currentLight.lightRadius, fraction, color);

            // Set gradient data
            g2.setPaint(gPaint);
        }



        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.dispose();
    }

    public void update() {
        if(gp.player.lightUpdated) {
            setLightSource();
            gp.player.lightUpdated = false;
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(darknessFilter, 0, 0, null);
    }
}

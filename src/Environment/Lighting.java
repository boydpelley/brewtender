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

    public Lighting(GamePanel gp, int circleDiameter) {
        // Create a buffered image to have 'darkness' surround the character
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        // Screen sized rectangle area to draw the darkness covering the whole screen
        Area screenArea = new Area(new Rectangle2D.Double(0, 0, gp.screenWidth, gp.screenHeight));

        // Center values for x and y to draw the circle from these points
        int centerX = gp.player.screenX + (gp.tileSize / 2);
        int centerY = gp.player.screenY + (gp.tileSize / 2);

        // Top left x and y coordinates to draw the outermost point of the circle
        double x = centerX - ((double)circleDiameter / 2);
        double y = centerY - ((double)circleDiameter / 2);

        // Create the circle where it is less dark
        Shape circleShape = new Ellipse2D.Double(x, y, circleDiameter, circleDiameter);

        // Create circle area
        Area lightArea = new Area(circleShape);

        // Subtract the light area so that there isn't overlap when we create darkness
        screenArea.subtract(lightArea);

        // Create a gradient effect
        Color color[] = new Color[5];
        float fraction[] = new float[5];

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
        RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, (circleDiameter / 2), fraction, color);

        // Set gradient data
        g2.setPaint(gPaint);

        // Draw light area
        g2.fill(lightArea);

        g2.fill(screenArea);
        g2.dispose();
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(darknessFilter, 0, 0, null);
    }
}

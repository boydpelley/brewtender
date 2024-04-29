package Main;

import Entity.Entity;
import Object.*;

public class EventHandler {

    GamePanel gp;
    EventRect[][] eventRect;

    // These are used to set margins for an event tile to occur
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[col][row] = new EventRect();
            // EXAMPLE TILE FOR FUTURE USE
            // This allows is to set future event tiles on the map.
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public void checkEvent() {

        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }

        if (canTouchEvent) {
            if (checkSurroundingHit( (gp.droppable[0].worldX / gp.tileSize), (gp.droppable[0].worldY / gp.tileSize) )) {
                System.out.println("HIT");
                interactForage(gp.droppable[0]);
            }
            if (checkSurroundingHit( (gp.droppable[1].worldX / gp.tileSize), (gp.droppable[1].worldY / gp.tileSize) )) {
                interactForage(gp.droppable[1]);
            }
        }
    }

    public boolean checkSurroundingHit(int col, int row) {
        return (hit(col - 1, row, "any") || hit(col, row + 1, "any")
                || hit(col, row - 1, "any") || hit(col + 1, row, "any"));
    }

    public void interactForage(Entity entity) {
        if (gp.keyH.qPressed) {
            entity.dropItem(new OBJ_JuniperBunch(gp));
        }
        gp.keyH.qPressed = false;
    }

    public boolean hit(int col, int row, String reqDirection) {

        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;

        if (gp.player.solidArea.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone) {
            if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;

                hit = true;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }
}

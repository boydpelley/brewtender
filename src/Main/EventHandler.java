package Main;

import Entity.Forage;
import Object.Forageables.OBJ_JuniperBunch;

public class EventHandler {

    GamePanel gp;
    EventRect[][][] eventRect;

    // These are used to set margins for an event tile to occur
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;
        while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            // EXAMPLE TILE FOR FUTURE USE
            // This allows is to set future event tiles on the map.
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;

                if (row == gp.maxWorldRow) {
                    row = 0;
                    map++;
                }
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
            if (checkSurroundingHit(0, 35, 15)) {
                interactForage(gp.droppable[0][0]);
            }
            else if (checkSurroundingHit(0, 10, 23)) {
                interactForage(gp.droppable[0][1]);
            }
            else if (hit(0, 41, 11, "any") && gp.keyH.ePressed) {
                changeMap(1, 25, 16);
            }
            else if (hit(1, 25, 17, "any")) {
                changeMap(0, 41, 11);
            }
        }
    }

    public boolean checkSurroundingHit(int map, int col, int row) {
        return (hit(map, col - 1, row, "any") || hit(map, col, row + 1, "any")
                || hit(map, col, row - 1, "any") || hit(map, col + 1, row, "any"));
    }

    public void interactForage(Forage forage) {
        if (gp.keyH.qPressed && !forage.foraged && checkSurroundingHit(0,forage.worldX / gp.tileSize, forage.worldY / gp.tileSize)) {
            forage.dropItem(new OBJ_JuniperBunch(gp));
            forage.foraged = true;
            forage.setNewImage();
        }
        gp.keyH.qPressed = false;
    }

    public boolean hit(int map, int col, int row, String reqDirection) {

        boolean hit = false;

        if (map == gp.currentMap){

            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

            if (gp.player.solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone) {
                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;

                    hit = true;
                }
            }

            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;

        }

        return hit;
    }

    public void changeMap(int map, int col, int row) {

        gp.gameState = gp.transitionState;

        tempMap = map;
        tempCol = col;
        tempRow = row;
    }
}

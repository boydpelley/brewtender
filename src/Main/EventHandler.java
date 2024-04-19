package Main;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    Rectangle eventRect;
    int eventRectDefaultX, eventRectDefaultY;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

    }

    public void checkEvent() {

    }

    public boolean hit(int eventCol, int eventRow) {
        return false;
    }
}

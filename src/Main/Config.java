package Main;

import java.io.*;

public class Config {
    GamePanel gp;

    public Config(GamePanel gp) {
        this.gp = gp;
    }

    public void saveConfig() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config"));

            // Full screen
            if (gp.fullScreenOn) {
                bw.write("On");
            }
            if (!gp.fullScreenOn) {
                bw.write("Off");
            }
            bw.newLine();

            // Music volume
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            // Sound Effect volume
            bw.write(String.valueOf(gp.sound.volumeScale));
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("config"));

            String s = br.readLine();

            // Full Screen
            if (s.equals("On")) {
                gp.fullScreenOn = true;
            }
            if (s.equals("Off")) {
                gp.fullScreenOn = false;
            }

            // Music volume
            s = br.readLine();
            gp.music.volumeScale = Integer.parseInt(s);

            // Sound Effect Volume
            s = br.readLine();
            gp.sound.volumeScale = Integer.parseInt(s);

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

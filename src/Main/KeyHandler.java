package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed,
            tPressed, ePressed, qPressed, enterPressed;

    // Debug
    boolean showDebug = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        // Title state
        if (gp.gameState == gp.titleState) {
            titleState(code);
        }

        // While in Play state
        if (gp.gameState == gp.playState) {
            playState(code);
        }

        // While in Pause state
        else if (gp.gameState == gp.pauseState) {
            pauseState(code);
        }

        // While in Dialogue state
        else if (gp.gameState == gp.dialogueState) {
            dialogueState(code);
        }

        // While in Character Stats State
        else if (gp.gameState == gp.characterState) {
            characterState(code);
        }

        // While in options state
        else if (gp.gameState == gp.optionsState) {
            optionsState(code);
        }

        // While in trade state
        else if (gp.gameState == gp.tradeState) {
            tradeState(code);
        }

        // While in Map state
        else if (gp.gameState == gp.mapState) {
            mapState(code);
        }

    }

    public void titleState(int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 2;
            }
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 2) {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            switch (gp.ui.commandNum) {
                case 0:
                    gp.gameState = gp.playState;
                    break;
                case 1:
                    // TODO: implement save files
                    break;
                case 2:
                    System.exit(0);
                    break;
            }
        }
    }

    public void playState(int code) {
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.pauseState;
        }
        if (code == KeyEvent.VK_T) {
            tPressed = true;
        }
        if (code == KeyEvent.VK_E) {
            ePressed = true;
        }
        if (code == KeyEvent.VK_Q) {
            qPressed = true;
        }
        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.characterState;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.optionsState;
        }
        if (code == KeyEvent.VK_M) {
            gp.gameState = gp.mapState;
        }
        if (code == KeyEvent.VK_X) {
            gp.map.miniMapOn = !gp.map.miniMapOn;
        }

        // Debugging
        if (code == KeyEvent.VK_R) {
            showDebug = !showDebug;
        }
    }

    public void pauseState(int code) {
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.playState;
        }
    }

    public void dialogueState(int code) {
        if (code == KeyEvent.VK_T) {
            gp.gameState = gp.playState;
        }
    }

    public void characterState(int code) {
        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }
        playerInventory(code);
    }

    public void optionsState(int code) {

        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) gp.ui.commandNum = 5;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 5) gp.ui.commandNum = 0;
        }

        if (code == KeyEvent.VK_A) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                }
                if (gp.ui.commandNum == 2 && gp.sound.volumeScale > 0) {
                    gp.sound.volumeScale--;
                    gp.sound.checkVolume();
                }
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                }
                if (gp.ui.commandNum == 2 && gp.sound.volumeScale < 5) {
                    gp.sound.volumeScale++;
                    gp.sound.checkVolume();
                }
            }
        }
    }

    public void tradeState(int code) {
        if (code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }

        if (gp.ui.subState == 0) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) gp.ui.commandNum = 2;
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) gp.ui.commandNum = 0;
            }
        }

        if (gp.ui.subState == 1) {
            npcInventory(code);
            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }
        if (gp.ui.subState == 2) {
            playerInventory(code);
            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }
    }

    public void playerInventory(int code) {
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            if (gp.ui.playerSlotRow != 0) {
                gp.ui.playerSlotRow--;
            }
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            if (gp.ui.playerSlotCol != 0) {
                gp.ui.playerSlotCol--;
            }
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            if (gp.ui.playerSlotRow != 4) {
                gp.ui.playerSlotRow++;
            }
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            if (gp.ui.playerSlotCol != 4) {
                gp.ui.playerSlotCol++;
            }
        }
    }

    public void npcInventory(int code) {
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            if (gp.ui.npcSlotRow != 0) {
                gp.ui.npcSlotRow--;
            }
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            if (gp.ui.npcSlotCol != 0) {
                gp.ui.npcSlotCol--;
            }
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            if (gp.ui.npcSlotRow != 4) {
                gp.ui.npcSlotRow++;
            }
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            if (gp.ui.npcSlotCol != 4) {
                gp.ui.npcSlotCol++;
            }
        }
    }

    public void mapState(int code) {
        if (code == KeyEvent.VK_M) {
            gp.gameState = gp.playState;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {


        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_E) {
            ePressed = false;
        }

        if (code == KeyEvent.VK_Q) {
            qPressed = false;
        }
    }
}

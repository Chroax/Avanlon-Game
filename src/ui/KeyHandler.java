package ui;

import entity.JobClass;
import state.BattleState;
import state.ChooseState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener
{
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    GamePanel gp;

    public KeyHandler(GamePanel gp)
    {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(gp.gameState == gp.startState)
        {
            if(gp.ui.startScreenState == gp.ui.homeState)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    gp.ui.startScreenState = gp.ui.titleState;
            }
            else if(gp.ui.startScreenState == gp.ui.titleState)
            {
                switch (e.getKeyCode())
                {
                    case KeyEvent.VK_W -> {
                        gp.ui.states[gp.ui.titleState].commandNum--;
                        if (gp.ui.states[gp.ui.titleState].commandNum < 0)
                            gp.ui.states[gp.ui.titleState].commandNum = 2;
                    }
                    case KeyEvent.VK_S -> {
                        gp.ui.states[gp.ui.titleState].commandNum++;
                        if (gp.ui.states[gp.ui.titleState].commandNum > 2)
                            gp.ui.states[gp.ui.titleState].commandNum = 0;
                    }
                    case KeyEvent.VK_ENTER -> {
                        switch (gp.ui.states[gp.ui.titleState].commandNum)
                        {
                            case 0 -> gp.ui.startScreenState = gp.ui.chooseState;
                            case 1 -> {}
                            case 2 -> System.exit(0);
                        }
                    }
                }
            }
            else if(gp.ui.startScreenState == gp.ui.chooseState)
            {
                switch (e.getKeyCode())
                {
                    case KeyEvent.VK_W -> {
                        gp.ui.states[gp.ui.chooseState].commandNum--;
                        if (gp.ui.states[gp.ui.chooseState].commandNum < 0)
                        {
                            if(((ChooseState)gp.ui.states[gp.ui.chooseState]).chooseGender)
                                gp.ui.states[gp.ui.chooseState].commandNum = 2;
                            else
                                gp.ui.states[gp.ui.chooseState].commandNum = 3;
                        }
                    }
                    case KeyEvent.VK_S -> {
                        gp.ui.states[gp.ui.chooseState].commandNum++;
                        if(((ChooseState)gp.ui.states[gp.ui.chooseState]).chooseGender)
                        {
                            if (gp.ui.states[gp.ui.chooseState].commandNum > 2)
                                gp.ui.states[gp.ui.chooseState].commandNum = 0;
                        }
                        else
                        {
                            if (gp.ui.states[gp.ui.chooseState].commandNum > 3)
                                gp.ui.states[gp.ui.chooseState].commandNum = 0;
                        }
                    }
                    case KeyEvent.VK_ENTER -> {
                        if (((ChooseState)gp.ui.states[gp.ui.chooseState]).chooseGender)
                        {
                            switch (gp.ui.states[gp.ui.chooseState].commandNum)
                            {
                                case 0 -> {
                                    ((ChooseState)gp.ui.states[gp.ui.chooseState]).gender = false;
                                    ((ChooseState)gp.ui.states[gp.ui.chooseState]).chooseGender = false;
                                }
                                case 1 -> {
                                    gp.ui.states[gp.ui.chooseState].commandNum = 0;
                                    ((ChooseState)gp.ui.states[gp.ui.chooseState]).gender = true;
                                    ((ChooseState)gp.ui.states[gp.ui.chooseState]).chooseGender = false;
                                }
                                case 2 -> {
                                    gp.ui.startScreenState = gp.ui.titleState;
                                    gp.ui.states[gp.ui.chooseState].commandNum = 0;
                                    gp.ui.states[gp.ui.titleState].commandNum = 0;
                                }
                            }
                        }
                        else
                        {
                            switch (gp.ui.states[gp.ui.chooseState].commandNum)
                            {
                                case 0 -> gp.generatePlayer(JobClass.PALADIN, ((ChooseState)gp.ui.states[gp.ui.chooseState]).gender);
                                case 1 -> gp.generatePlayer(JobClass.WIZARD, ((ChooseState)gp.ui.states[gp.ui.chooseState]).gender);
                                case 2 -> gp.generatePlayer(JobClass.ARCHER, ((ChooseState)gp.ui.states[gp.ui.chooseState]).gender);
                                case 3 -> {
                                    gp.ui.states[gp.ui.chooseState].commandNum = 0;
                                    ((ChooseState)gp.ui.states[gp.ui.chooseState]).chooseGender = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        // Key Handling at play state
        else if(gp.gameState == gp.playState)
        {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_W -> upPressed = true;
                case KeyEvent.VK_S -> downPressed = true;
                case KeyEvent.VK_A -> leftPressed = true;
                case KeyEvent.VK_D -> rightPressed = true;
                case KeyEvent.VK_ENTER -> enterPressed = true;
                case KeyEvent.VK_P -> {
                    gp.returnState = gp.gameState;
                    gp.gameState = gp.pauseState;
                }
                case KeyEvent.VK_I -> gp.gameState = gp.inventoryState;
            }
        }
        else if(gp.gameState == gp.battleState)
        {
            if(((BattleState)gp.ui.states[gp.ui.battleState]).messageOn)
            {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    ((BattleState)gp.ui.states[gp.ui.battleState]).messageOn = false;
            }
            else
            {
                switch (e.getKeyCode())
                {
                    case KeyEvent.VK_W, KeyEvent.VK_S -> {
                        if (gp.ui.states[gp.ui.battleState].commandNum == 0)
                            gp.ui.states[gp.ui.battleState].commandNum = 2;
                        else if (gp.ui.states[gp.ui.battleState].commandNum == 1)
                            gp.ui.states[gp.ui.battleState].commandNum = 3;
                        else if (gp.ui.states[gp.ui.battleState].commandNum == 2)
                            gp.ui.states[gp.ui.battleState].commandNum = 0;
                        else if (gp.ui.states[gp.ui.battleState].commandNum == 3)
                            gp.ui.states[gp.ui.battleState].commandNum = 1;
                    }
                    case KeyEvent.VK_A, KeyEvent.VK_D ->{
                        if (gp.ui.states[gp.ui.battleState].commandNum == 0)
                            gp.ui.states[gp.ui.battleState].commandNum = 1;
                        else if (gp.ui.states[gp.ui.battleState].commandNum == 1)
                            gp.ui.states[gp.ui.battleState].commandNum = 0;
                        else if (gp.ui.states[gp.ui.battleState].commandNum == 2)
                            gp.ui.states[gp.ui.battleState].commandNum = 3;
                        else if (gp.ui.states[gp.ui.battleState].commandNum == 3)
                            gp.ui.states[gp.ui.battleState].commandNum = 2;
                    }
                    case KeyEvent.VK_ENTER -> {
                        switch (gp.ui.states[gp.ui.battleState].commandNum)
                        {
                            case 0 -> {
                                {
                                    gp.player.attack(gp.map[gp.getMapPick()].monsters[gp.monsterIndex], 1);
                                    gp.map[gp.getMapPick()].monsters[gp.monsterIndex].attack(gp.player, 2);
                                    if(gp.map[gp.getMapPick()].monsters[gp.monsterIndex].getHP() <= 0)
                                    {
                                        gp.player.defeatMonster(gp.map[gp.getMapPick()].monsters[gp.monsterIndex]);
                                        gp.gameState = gp.playState;
                                        gp.map[gp.getMapPick()].monsters[gp.monsterIndex].generateMonster();
                                    }
                                    else if(gp.player.getHP() <= 0)
                                    {
                                        System.out.println("YOU DIED");
                                        gp.gameState = gp.playState;
                                        gp.player.respawn();
                                        gp.player.resetStat();
                                        gp.map[gp.getMapPick()].monsters[gp.monsterIndex].generateMonster();
                                    }
                                }
                            }
                            case 1 -> System.out.println("DO OPEN INVENTORY");
                            case 2 -> {
                                gp.gameState = gp.playState;
                                gp.map[gp.getMapPick()].monsters[gp.monsterIndex].generateMonster();
                            }
                            case 3 -> System.out.println("DO SHOW STATUS");
                        }
                    }
                    case KeyEvent.VK_P -> {
                        gp.returnState = gp.gameState;
                        gp.gameState = gp.pauseState;
                    }
                }
            }
        }
        else if(gp.gameState == gp.pauseState)
        {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_P -> gp.gameState = gp.returnState;
            }
        }
        else if(gp.gameState == gp.chooseMapState) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W -> {
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum < 0)
                        gp.ui.commandNum = 4;
                }
                case KeyEvent.VK_S -> {
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum > 4)
                        gp.ui.commandNum = 0;
                }
                case KeyEvent.VK_ENTER -> {
                    switch (gp.ui.commandNum) {
                        case 0 -> {
                            gp.adjustWorldWidth(gp.plain);
                            gp.adjustWorldHeight(gp.plain);
                            gp.setMapPick(gp.plain);
                            gp.gameState = gp.playState;
                            gp.player.setXAndY(gp.plain);
                        }
                        case 1 -> {
                            gp.adjustWorldWidth(gp.dungeon);
                            gp.adjustWorldHeight(gp.dungeon);
                            gp.setMapPick(gp.dungeon);
                            gp.gameState = gp.playState;
                            gp.player.setXAndY(gp.dungeon);
                        }
                        case 2 -> {
                            gp.adjustWorldWidth(gp.castle);
                            gp.adjustWorldHeight(gp.castle);
                            gp.setMapPick(gp.castle);
                            gp.gameState = gp.playState;
                            gp.player.setXAndY(gp.castle);
                        }
                        case 3 -> {
                            gp.adjustWorldWidth(gp.snow);
                            gp.adjustWorldHeight(gp.snow);
                            gp.setMapPick(gp.snow);
                            gp.gameState = gp.playState;
                            gp.player.setXAndY(gp.snow);
                        }
                        case 4 -> {
                            gp.ui.commandNum = 0;
                            gp.gameState = gp.playState;
                        }
                    }
                }
            }
        }
        else if(gp.gameState == gp.inventoryState)
        {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_W -> {
                    gp.ui.states[gp.ui.inventoryState].commandNum -= 8;
                    if(gp.ui.states[gp.ui.inventoryState].commandNum < 0)
                        gp.ui.states[gp.ui.inventoryState].commandNum += 32;
                }
                case KeyEvent.VK_S -> {
                    gp.ui.states[gp.ui.inventoryState].commandNum += 8;
                    if(gp.ui.states[gp.ui.inventoryState].commandNum > 31)
                        gp.ui.states[gp.ui.inventoryState].commandNum -= 32;
                }
                case KeyEvent.VK_A -> {
                    gp.ui.states[gp.ui.inventoryState].commandNum -= 1;
                    if((gp.ui.states[gp.ui.inventoryState].commandNum + 1) % 8 == 0 || gp.ui.states[gp.ui.inventoryState].commandNum < 0)
                        gp.ui.states[gp.ui.inventoryState].commandNum += 8;
                }
                case KeyEvent.VK_D -> {
                    gp.ui.states[gp.ui.inventoryState].commandNum += 1;
                    if(gp.ui.states[gp.ui.inventoryState].commandNum % 8 == 0)
                        gp.ui.states[gp.ui.inventoryState].commandNum -= 8;
                }
                case KeyEvent.VK_ENTER -> enterPressed = true;
                case KeyEvent.VK_I -> gp.gameState = gp.playState;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_W -> upPressed = false;
            case KeyEvent.VK_S -> downPressed = false;
            case KeyEvent.VK_A -> leftPressed = false;
            case KeyEvent.VK_D -> rightPressed = false;
        }
    }
}

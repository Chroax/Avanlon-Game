package ui;

import entity.JobClass;
import state.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class UI
{
    GamePanel gp;
    Graphics2D g2;

    BufferedImage imageEquip;
    BufferedImage imagePlayer;

    public static Font maruMonica;
    public static Font pokemon;

    public int homeState = 0;
    public int titleState = 1;
    public int chooseState = 2;
    public int battleState = 3;
    public int inventoryState = 4;
    public int startScreenState = homeState;
    public State[] states = new State[5];

    public int commandNum = 0;

    public UI(GamePanel gp)
    {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/MaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            InputStream is = getClass().getResourceAsStream("/font/Pokemon.ttf");
            pokemon = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        getUIImage();
        setState();
    }

    public void setState()
    {
        states[homeState] = new HomeState(gp);
        states[titleState] = new TitleState(gp);
        states[chooseState] = new ChooseState(gp);
        states[battleState] = new BattleState(gp);
        states[inventoryState] = new InventoryState(gp);
    }

    public void getUIImage()
    {
        try
        {
            imageEquip = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/equip_ui.png")));
            imagePlayer = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/player_ui.png")));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2)
    {
        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.white);

        if(gp.gameState == gp.startState)
            states[startScreenState].draw(g2);
        else if(gp.gameState == gp.battleState)
            states[battleState].draw(g2);
        else if(gp.gameState == gp.playState)
            drawPlayUI();
        else if(gp.gameState == gp.pauseState)
            drawPauseScreen();
        else if(gp.gameState == gp.chooseMapState)
            drawChooseMapScreen();
        else if(gp.gameState == gp.inventoryState)
        {
            drawPlayUI();
            states[inventoryState].draw(g2);
        }
    }

    public void drawChooseMapScreen()
    {
        String mapOption = "PLAIN\nDUNGEON\nCASTLE\nSNOW\nBACK";
        int i = 0;
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = (int) (gp.tileSize * 5);
        drawSubWindow(x, y, width, height);

        x += gp.tileSize;
        y += gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        for (String line: mapOption.split("\n"))
        {
            g2.drawString(line, x, y);
            if(commandNum == i)
                g2.drawString(">", x - 15, y);
            i++;
            if(i == 4)
                y += 80;
            else
                y += 40;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height)
    {
        g2.setColor(new Color(0, 0, 0, 215));
        g2.fillRoundRect(x, y, width, height, 35, 35);
        g2.setColor(new Color(255, 255, 255));
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height -10, 25, 25);
    }

    public void drawPlayUI()
    {
        g2.drawImage(imagePlayer, 10,20, 200, 62, null);
        g2.drawImage(imageEquip, 10, 85, 130, 48, null);

        if(gp.player.getWeapon() != null)
            g2.drawImage(gp.player.getWeapon().image, 33, 92, 32, 32, null);

        g2.setFont(pokemon);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F));
        g2.drawString("Level    " + gp.player.getLvl(), 60, 18);

        double health = (double) gp.player.getHP() / gp.player.getMaxHP();
        int healthBar = (int) (102 * health);
        double mp = (double) gp.player.getMP() / gp.player.getMaxMP();
        int mpBar = (int) (102 * mp);
        double exp = (double) gp.player.getEXP() / gp.player.getMaxEXP();
        int expBar = (int) (102 * exp);

        if(healthBar != 0)
        {
            g2.setColor(new Color(208, 70, 72));
            g2.fillRect(82 , 28, healthBar, 8);
            g2.setColor(new Color(210, 170, 153));
            g2.fillRect(84 , 28, healthBar - 2, 4);
        }
        else
        {
            g2.setColor(new Color(133, 149, 161));
            g2.fillRect(84 , 28, healthBar - 2, 4);
        }

        if(mpBar != 0)
        {
            g2.setColor(new Color(109, 170, 44));
            g2.fillRect(82 , 47, mpBar, 8);
            g2.setColor(new Color(218, 212, 94));
            g2.fillRect(84 , 47, mpBar - 2, 4);
        }
        else
        {
            g2.setColor(new Color(133, 149, 161));
            g2.fillRect(84 , 47, healthBar - 2, 4);
        }

        if(expBar != 0)
        {
            g2.setColor(new Color(89, 125, 206));
            g2.fillRect(82 , 67, expBar, 8);
            g2.setColor(new Color(109, 194, 202));
            g2.fillRect(84 , 67, expBar - 2, 4);
        }
        else
        {
            g2.setColor(new Color(133, 149, 161));
            g2.fillRect(84 , 67, healthBar - 2, 4);
        }
        if(gp.player.jobClass == JobClass.PALADIN)
            g2.drawImage(gp.player.down2, 18, 23, 48, 48, null);
        else
            g2.drawImage(gp.player.down2, 22, 23, 48, 48, null);
    }

    public void drawPauseScreen()
    {
        g2.setFont(maruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
        g2.setFont(pokemon);
    }

    public int getXCenteredText(String text)
    {
        return ((gp.screenWidth / 2) - ((int) g2.getFontMetrics().getStringBounds(text, g2).getWidth() / 2));
    }
}

package semestralka;

import javax.swing.JPanel;

import battle.FightMenu;
import entity.Player;
import tile.TileManager;
import battle.Battle;
import battle.PlayerHeart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {

    // Screen dimensions
    private final int originalTileSize = 20;
    private final int scale = 3;

    private final int tileSize = originalTileSize * scale;
    // private final int maxScreenCol = 80;
    // private final int maxScreenRow = 20;
    private final int screenWidth = 1280; // 1280px width
    private final int screenHeight = 720; // 720px height

    // WORLD SETTINGS
    private final int maxWorldColumn = 32; // 32 tiles
    private final int maxWorldRow = 18; // 18 tiles

    private double delta;

    private KeyHandler keyH = new KeyHandler();
    private Thread gameThread;
    private HitBoxCheck hitBoxCheck;
    private Battle battle = new Battle(this);
    private FightMenu fightMenu;
    private Player player;
    private TileManager tileManager;
    private PlayerHeart playerHeart;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        player = new Player(this, keyH, battle);
        hitBoxCheck = new HitBoxCheck(this, player);
        tileManager = new TileManager(this);
        player.setTileManager(tileManager);
        playerHeart = new PlayerHeart(this, keyH);
        fightMenu = new FightMenu(this, keyH, playerHeart);
        playerHeart.setFightMenu(fightMenu);
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getMaxWorldColumn() {
        return maxWorldColumn;
    }

    public int getMaxWorldRow() {
        return maxWorldRow;
    }

    public HitBoxCheck getHitBoxCheck() {
        return hitBoxCheck;
    }

    public Player getPlayer() {
        return player;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Snimky za sekundu
    int targetUpdateInterval = 60;

    @Override
    public void run() {
        final double UPDATE_INTERVAL = 1_000_000_000.0 / targetUpdateInterval;
        long previousTime = System.nanoTime();
        delta = 0;

        while (gameThread != null) {
            long currentTime = System.nanoTime();
            long elapsedTime = currentTime - previousTime;
            previousTime = currentTime;

            delta += elapsedTime / UPDATE_INTERVAL;

            while (delta >= 1) {
                updateGame();
                delta--;
            }

            repaint();
        }
    }

    long lastLogTime = 0;

    public void changeFightMenu(boolean state) {
        battle.setFightMenu(state);
    }

    public void changeTurn(String turn) {
        if (turn == "+") {
            fightMenu.setTurn(1);
        } else {
            fightMenu.setTurn(0);
        }
    }

    public void resetEncounterTile(int tile) {
        tileManager.resetEncounterTile(tile);
    }

    public void drawEncounter(boolean fightMenu) {
        changeFightMenu(fightMenu);
        paintComponent(this.getGraphics());
    }

    public void updateGame() {
        long currentTime = System.currentTimeMillis();
        if (!battle.getFightMenu()) {
            player.update();
            if (currentTime - lastLogTime >= 1000) {
                lastLogTime = currentTime;
            }
        } else {
            fightMenu.update();
            if (fightMenu.getNumberOfTurn() == 1) {
                playerHeart.update();
            }
            if (currentTime - lastLogTime >= 1000) {
                lastLogTime = currentTime;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        if (!battle.getFightMenu()) {
            tileManager.draw(g2);
            player.draw(g2);
        } else if (battle.getFightMenu()) {
            fightMenu.drawFightMenu(g2);
            if (fightMenu.getNumberOfTurn() == 1) {
                playerHeart.draw(g2);
            }
        } else {
            System.out.println("Error in paintComponent");
        }
        g2.dispose();
    }

}
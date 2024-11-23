package semestralka;

import javax.swing.JPanel;

import battle.FightMenu;
import entity.Player;
import tile.Tile;
import tile.TileManager;
import battle.Battle;
import battle.PlayerHeart;
import battle.Projectile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {

    // Screen dimensions
    private final int originalTileSize = 20;
    public final int scale = 2;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 80;
    public final int maxScreenRow = 20;
    public final int screenWidth = tileSize * 16;
    public final int screenHeight = tileSize * 12;

    // WORLD SETTINGS
    public final int maxWorldColumn = 26;
    public final int maxWorldRow = 12;
    public final int worldWidth = tileSize * 16;
    public final int worldHeight = tileSize * 12;
    public double delta;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public HitBoxCheck hitBoxCheck = new HitBoxCheck(this);
    public Tile tile = new Tile();
    public Battle battle = new Battle();
    public FightMenu fightMenu;
    public Player player;
    public TileManager tileManager;
    public PlayerHeart playerHeart;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        player = new Player(this, keyH, battle);
        tileManager = new TileManager(this);
        player.setTileManager(tileManager);
        playerHeart = new PlayerHeart(this, keyH);
        fightMenu = new FightMenu(this, keyH, playerHeart);
        playerHeart.setFightMenu(fightMenu);
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

    public void changeFightMenu() {
        battle.fightMenu = !battle.fightMenu;
    }

    public void changeTurn(String turn) {
        if (turn == "+") {
            battle.numberOfTurn++;
        } else {
            battle.numberOfTurn--;
        }
    }

    public void updateGame() {
        long currentTime = System.currentTimeMillis();
        if (!battle.fightMenu) {
            player.update();
            if (currentTime - lastLogTime >= 1000) {
                System.out.println("Player is moving");
                lastLogTime = currentTime;
            }
        } else {
            fightMenu.update();
            if (battle.numberOfTurn == 1) {
                playerHeart.update();
            }
            if (currentTime - lastLogTime >= 1000) {
                System.out.println("Fight menu is open");
                lastLogTime = currentTime;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        if (!battle.fightMenu) {
            tileManager.draw(g2);
            player.draw(g2);
        } else {
            fightMenu.drawFightMenu(g2);
            if (battle.numberOfTurn == 1) {
                playerHeart.draw(g2);
            }
        }
        g2.dispose();
    }

}
package semestralka;

import javax.swing.JPanel;

import battle.FightMenu;
import entity.Player;
import tile.TileManager;
import battle.Battle;
import battle.PlayerHeart;
import battle.PlayerHeartBlue;
import tile.Object;

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
    private final int maxWorldRow = 12; // 18 tiles

    private double delta;

    // Ako casto sa ma hra updatovat
    private int targetUpdateInterval = 60;

    // Ako casto sa ma hra renderovat
    final double RENDER_INTERVAL = 1_000_000_000.0 / 60;

    private long lastLogTime = 0;

    private int room = 1;

    private KeyHandler keyH = new KeyHandler();
    private Thread gameThread;
    private HitBoxCheck hitBoxCheck;
    private Battle battle = new Battle(this);
    private FightMenu fightMenu;
    private Player player;
    private TileManager tileManager;
    private PlayerHeart playerHeart;
    private PlayerHeartBlue playerHeartBlue;
    private Object dummyObj = new Object("dummy", tileSize * 23, tileSize * 6);

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
        playerHeartBlue = new PlayerHeartBlue(this, keyH);
        fightMenu = new FightMenu(this, keyH, playerHeart, playerHeartBlue);
        playerHeart.setFightMenu(fightMenu);
        playerHeartBlue.setFightMenu(fightMenu);
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

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public FightMenu getFightMenu() {
        return fightMenu;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        final double UPDATE_INTERVAL = 1_000_000_000.0 / targetUpdateInterval;
        long previousTime = System.nanoTime();
        long lastRenderTime = System.nanoTime();
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

            if (currentTime - lastRenderTime >= RENDER_INTERVAL) {
                repaint();
                lastRenderTime = currentTime;
            }

            // Sleep to reduce CPU usage
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

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
        tileManager.resetEncounterTile(room, tile);
    }

    public void setCollisionTile(int tile) {
        tileManager.setCollisionTile(room, tile);
    }

    public void removeObject(String obj) {
        if (obj.equals("dummy")) {
            dummyObj = null;
        }
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
                if (fightMenu.getEncounteredEnemy().equals("sans")) {
                    playerHeartBlue.update();
                } else {
                    playerHeart.update();
                }
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
            tileManager.draw(g2, room);
            if (room == 2) {
                if (dummyObj != null) {
                    dummyObj.draw(g2, this);
                }
            }
            player.draw(g2);
        } else if (battle.getFightMenu()) {
            fightMenu.drawFightMenu(g2);
            if (fightMenu.getNumberOfTurn() == 1) {
                if (fightMenu.getEncounteredEnemy().equals("sans")) {
                    playerHeartBlue.draw(g2);
                } else {
                    playerHeart.draw(g2);
                }
            }
        } else {
            System.out.println("Error in paintComponent");
        }
        g2.dispose();
    }

}
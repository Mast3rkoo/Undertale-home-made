package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import semestralka.GamePanel;
import semestralka.KeyHandler;
import tile.TileManager;
import battle.Battle;

public class Player extends Entity {
    private GamePanel gp;
    private KeyHandler keyH;
    private TileManager tileManager;
    private Battle battle;

    private final int screenX;
    private final int screenY;
    private BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    private int spriteCounter = 0;
    private int imageNumber = 1;
    private int level;
    private int widthOfPlayerHitbox, heightOfPlayerHitbox;

    public Player(GamePanel gp, KeyHandler keyH, Battle battle) {
        this.gp = gp;
        this.keyH = keyH;
        this.battle = battle;

        this.level = 2;

        screenX = gp.getScreenWidth() / 2;
        screenY = gp.getScreenHeight() / 2;

        widthOfPlayerHitbox = 30;
        heightOfPlayerHitbox = 30;

        setHitBox(new Rectangle(10, 20, widthOfPlayerHitbox, heightOfPlayerHitbox));

        setDefaultValues(gp.getTileSize() * 3, gp.getTileSize() * 3);
        getPlayerImage();
    }

    public void setDefaultValues(int x, int y) {
        setWorldX(x); // Set spawn X from TileManager or default
        setWorldY(y); // Set spawn Y from TileManager or default
        setSpeed(4);
        setDirection("down");
    }

    public void setTileManager(TileManager tileManager) {
        this.tileManager = tileManager;
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/Tesa_back_idle.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/Tesa-back-right-step.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/Tesa-front-idle.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/Tesa-front-right-step.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/Tesa-right-idle.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/Tesa-right-right-step.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/Tesa-left-idle.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/Tesa-left-right-step.png"));
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void update() {
        int speed = getSpeed();
        if (keyH.isUpPressed() == true) {
            setDirection("up");

        } else if (keyH.isDownPressed() == true) {
            setDirection("down");

        } else if (keyH.isLeftPressed() == true) {
            setDirection("left");

        } else if (keyH.isRightPressed() == true) {
            setDirection("right");

        } else {
            imageNumber = 1;
            return;
        }

        setCollisionDetected(false);
        gp.getHitBoxCheck().checkTileCollision();

        if (!isCollisionDetected()) {
            if (keyH.isUpPressed() && !keyH.isRightPressed() && !keyH.isLeftPressed()
                    && !keyH.isDownPressed()) {
                setWorldY(getWorldY() - speed);
            } else if (keyH.isDownPressed() && !keyH.isRightPressed() && !keyH.isLeftPressed()
                    && !keyH.isUpPressed()) {
                setWorldY(getWorldY() + speed);
            } else if (keyH.isLeftPressed() && !keyH.isUpPressed() && !keyH.isDownPressed()
                    && !keyH.isRightPressed()) {
                setWorldX(getWorldX() - speed);
            } else if (keyH.isRightPressed() && !keyH.isUpPressed() && !keyH.isDownPressed()
                    && !keyH.isLeftPressed()) {
                setWorldX(getWorldX() + speed);
            } else if (keyH.isUpPressed() && keyH.isRightPressed()) {
                setWorldY(getWorldY() - speed);
                setWorldX(getWorldX() + speed);
            } else if (keyH.isUpPressed() && keyH.isLeftPressed()) {
                setWorldY(getWorldY() - speed);
                setWorldX(getWorldX() - speed);
            } else if (keyH.isDownPressed() && keyH.isRightPressed()) {
                setWorldY(getWorldY() + speed);
                setWorldX(getWorldX() + speed);
            } else if (keyH.isDownPressed() && keyH.isLeftPressed()) {
                setWorldY(getWorldY() + speed);
                setWorldX(getWorldX() - speed);
            }
        }

        if (isWalkThroughDoor()) {
            this.tileManager.loadMap(String.format("/res/maps/map00%s.txt", level));
            setDefaultValues(gp.getTileSize() * 5, gp.getTileSize() * 7);
            setWalkThroughDoor(false);
            this.level++;
        }

        spriteCounter++;
        if (spriteCounter > 20) {
            if (imageNumber == 1) {
                imageNumber = 2;
            } else if (imageNumber == 2) {
                imageNumber = 1;
            }
            spriteCounter = 0;
        }

    }

    public void draw(Graphics2D g2) {
        if (battle.getFightMenu() == false) {
            BufferedImage image = null;

            switch (getDirection()) {
                case "up":
                    if (imageNumber == 1) {
                        image = up1;
                    }
                    if (imageNumber == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if (imageNumber == 1) {
                        image = down1;
                    }
                    if (imageNumber == 2) {
                        image = down2;
                    }
                    break;
                case "right":
                    if (imageNumber == 1) {
                        image = right1;
                    }
                    if (imageNumber == 2) {
                        image = right2;
                    }
                    break;
                case "left":
                    if (imageNumber == 1) {
                        image = left1;
                    }
                    if (imageNumber == 2) {
                        image = left2;
                    }
                    break;
            }
            Rectangle hitBox = getHitBox();
            g2.drawImage(image, screenX, screenY, gp.getTileSize() + 10, gp.getTileSize() + 10, null);
            g2.setColor(java.awt.Color.RED);
            g2.drawRect(screenX + hitBox.x, screenY + hitBox.y, widthOfPlayerHitbox, heightOfPlayerHitbox);

        }

    }
}

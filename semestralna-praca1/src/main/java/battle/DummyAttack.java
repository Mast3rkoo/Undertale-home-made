package battle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import semestralka.GamePanel;

public class DummyAttack extends Battle {
    private GamePanel gp;
    private FightMenu fightMenu;
    private PlayerHeart playerHeart;
    private BufferedImage bullet;
    private int xOfBullet, yOfBullet;
    private long lastSavedTime;
    private Rectangle bulletHitBox;
    private boolean bulletActive;
    private boolean alreadyHit = false;
    private int bulletSpeed;
    private int bulletDamage;
    private String whichSide;
    private int sizeOfBullet;
    private long lastSavedTimeForExplosion;
    private boolean bulletExploded = false;

    public DummyAttack(GamePanel gp, PlayerHeart playerHeart) {
        super(gp);
        this.gp = gp;
        this.playerHeart = playerHeart;

        getProjectile();
        bulletActive = true;
        bulletSpeed = 0;
        bulletDamage = 0;
        sizeOfBullet = getHeightOfBattleRect() / 40;
    }

    public void getProjectile() {
        try {
            bullet = ImageIO.read(getClass().getResourceAsStream("/res/projectile/basic-bullet.png"));
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void timerBeforeExplosion() {
        lastSavedTimeForExplosion = System.currentTimeMillis();
    }

    public void setFightMenu(FightMenu fightMenu, String whichSide) {
        this.fightMenu = fightMenu;
        this.whichSide = whichSide;

        int tempWidthOfBattleRect = gp.getScreenWidth() / 3;
        int tempxOfBattleRect = (gp.getScreenWidth() - tempWidthOfBattleRect) / 2;
        int tempyOfBattleRect = gp.getScreenHeight() / 5 * 2;
        int tempHeightOfBattleRect = 270;

        // Choosing random starting position of bullet based on width of rectangle
        if (whichSide.equals("top")) {
            int minX = tempxOfBattleRect + sizeOfBullet;
            int maxX = tempxOfBattleRect + tempWidthOfBattleRect - sizeOfBullet;
            xOfBullet = (int) (Math.random() * (maxX - minX)) + minX;
            yOfBullet = tempyOfBattleRect + bullet.getHeight();
        }

        else if (whichSide.equals("bottom")) {
            int minX = tempxOfBattleRect + bullet.getWidth();
            int maxX = tempxOfBattleRect + tempWidthOfBattleRect - sizeOfBullet;
            xOfBullet = (int) (Math.random() * (maxX - minX)) + minX;
            yOfBullet = tempyOfBattleRect + tempHeightOfBattleRect - sizeOfBullet;
        }

        else if (whichSide.equals("left")) {
            int minY = tempyOfBattleRect + sizeOfBullet;
            int maxY = tempyOfBattleRect + tempHeightOfBattleRect - sizeOfBullet;
            xOfBullet = tempxOfBattleRect + sizeOfBullet;
            yOfBullet = (int) (Math.random() * (maxY - minY)) + minY;
        }

        else if (whichSide.equals("right")) {
            int minY = tempyOfBattleRect + sizeOfBullet;
            int maxY = tempyOfBattleRect + tempHeightOfBattleRect + sizeOfBullet;
            xOfBullet = tempxOfBattleRect + tempWidthOfBattleRect - sizeOfBullet * 2;
            yOfBullet = (int) (Math.random() * (maxY - minY)) + minY;
        }

        else {
            System.out.println("Error in setting bullet position");
        }

        bulletHitBox = new Rectangle(xOfBullet + bullet.getWidth(), yOfBullet, sizeOfBullet, sizeOfBullet);

        timerBeforeExplosion();
    }

    public void checkBulletCollision() {
        Rectangle battleRectHitbox = fightMenu.getBattleRectHitbox();

        // Bottom side
        int rectBottomY = battleRectHitbox.y + battleRectHitbox.height;
        int bulletBottomY = bulletHitBox.y + bulletHitBox.height;

        // Top side
        int bulletTopY = bulletHitBox.y;
        int rectTopY = battleRectHitbox.y;

        // Left side
        int bulletLeftX = bulletHitBox.x;
        int rectLeftX = battleRectHitbox.x;

        // Right side
        int bulletRightX = bulletHitBox.x + bulletHitBox.width;
        int rectRightX = battleRectHitbox.x + battleRectHitbox.width;

        // Check if the bullet overlaps with the heart on the X-axis
        boolean isXOverlap = (bulletRightX > playerHeart.getWorldHeart("leftX")
                && bulletLeftX < playerHeart.getWorldHeart("rightX"));

        // Check if the bullet overlaps with the heart on the Y-axis
        boolean isYOverlap = (bulletBottomY > playerHeart.getWorldHeart("topY")
                && bulletTopY < playerHeart.getWorldHeart("bottomY"));

        if (isXOverlap && isYOverlap) {
            bulletActive = false;
            if (!alreadyHit) {
                fightMenu.changeHealth(bulletDamage);
                alreadyHit = true;
            }
        }

        if (bulletBottomY >= rectBottomY || bulletLeftX <= rectLeftX || bulletRightX >= rectRightX
                || bulletTopY <= rectTopY) {
            bulletActive = false;
        }
    }

    public void bulletLogic(int speed, int damage) {
        bulletSpeed = speed;
        bulletDamage = damage;
    }

    public boolean getBulletActive() {
        return bulletActive;
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastSavedTime >= 20) {
            if (currentTime - lastSavedTimeForExplosion < 2500) {
                switch (whichSide) {
                    case "top":
                        yOfBullet += bulletSpeed;
                        break;
                    case "bottom":
                        yOfBullet -= bulletSpeed;
                        break;
                    case "left":
                        xOfBullet += bulletSpeed;
                        break;
                    case "right":
                        xOfBullet -= bulletSpeed;
                        break;
                    default:
                        break;
                }
                bulletHitBox.x = xOfBullet + bullet.getWidth();
                bulletHitBox.y = yOfBullet;
                lastSavedTime = currentTime;
            } else {
                bulletExploded = true;
            }
        }
        checkBulletCollision();
    }

    public void draw(Graphics2D g2) {
        if (bulletActive) {
            g2.setColor(Color.BLUE); // For missile hitbox
            g2.drawRect(bulletHitBox.x, bulletHitBox.y, bulletHitBox.width, bulletHitBox.height);
            g2.drawImage(bullet, xOfBullet + bullet.getWidth(), yOfBullet, null);
            if (bulletExploded) {
                g2.setColor(Color.RED);
                g2.drawImage(bullet, xOfBullet + bullet.getWidth() + 5, yOfBullet, null);
                g2.drawImage(bullet, xOfBullet + bullet.getWidth() - 5, yOfBullet, null);
                g2.drawImage(bullet, xOfBullet + bullet.getWidth(), yOfBullet + 5, null);
                g2.drawImage(bullet, xOfBullet + bullet.getWidth(), yOfBullet - 5, null);
            }
        }
    }
}

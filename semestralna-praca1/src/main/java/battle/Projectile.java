package battle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import semestralka.GamePanel;

public class Projectile extends Battle {
    GamePanel gp;
    FightMenu fightMenu;
    PlayerHeart playerHeart;
    private int xOfBullet, yOfBullet;
    private long lastSavedTime;
    public Rectangle bulletHitBox;
    public boolean bulletActive;
    private boolean alreadyHit = false;
    private int bulletSpeed;
    private int bulletDamage;
    private Random random;
    private String whichSide;

    public Projectile(GamePanel gp, PlayerHeart playerHeart) {
        this.gp = gp;
        this.playerHeart = playerHeart;

        getProjectile();
        bulletActive = true;
        bulletSpeed = 0;
        bulletDamage = 0;
        random = new Random();
    }

    public void getProjectile() {
        try {
            bullet = ImageIO.read(getClass().getResourceAsStream("/res/projectile/basic-bullet.png"));
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void setFightMenu(FightMenu fightMenu, String whichSide) {
        this.fightMenu = fightMenu;
        this.whichSide = whichSide;

        int tempWidthOfBattleRect = (gp.screenWidth - 80) / 2;
        int tempxOfBattleRect = (gp.screenWidth - tempWidthOfBattleRect) / 2;
        int tempyOfBattleRect = gp.tileSize * 5;
        int tempHeightOfBattleRect = 170;

        // Choosing random starting position of bullet based on width of rectangle
        if (whichSide.equals("top")) {
            int minX = tempxOfBattleRect + bullet.getWidth();
            int maxX = tempxOfBattleRect + tempWidthOfBattleRect - bullet.getWidth();
            xOfBullet = (int) (Math.random() * (maxX - minX)) + minX;
            yOfBullet = tempyOfBattleRect + bullet.getHeight();
        }

        else if (whichSide.equals("bottom")) {
            int minX = tempxOfBattleRect + bullet.getWidth();
            int maxX = tempxOfBattleRect + tempWidthOfBattleRect - bullet.getWidth();
            xOfBullet = (int) (Math.random() * (maxX - minX)) + minX;
            yOfBullet = tempyOfBattleRect + tempHeightOfBattleRect - bullet.getHeight();
        }

        else if (whichSide.equals("left")) {
            int minY = tempyOfBattleRect + bullet.getHeight();
            int maxY = tempyOfBattleRect + tempHeightOfBattleRect - bullet.getHeight();
            xOfBullet = tempxOfBattleRect + bullet.getWidth();
            yOfBullet = (int) (Math.random() * (maxY - minY)) + minY;
        }

        else if (whichSide.equals("right")) {
            int minY = tempyOfBattleRect + bullet.getHeight();
            int maxY = tempyOfBattleRect + tempHeightOfBattleRect + bullet.getHeight();
            xOfBullet = tempxOfBattleRect + tempWidthOfBattleRect - bullet.getWidth() * 2;
            yOfBullet = (int) (Math.random() * (maxY - minY)) + minY;
        }

        else {
            System.out.println("Error in setting bullet position");
        }

        bulletHitBox = new Rectangle(xOfBullet + bullet.getWidth(), yOfBullet, 5, 5);
    }

    public void checkBulletCollision() {
        battleRectHitbox = fightMenu.setBattleRectHitbox();

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
        boolean isXOverlap = (bulletRightX > playerHeart.heartLeftX && bulletLeftX < playerHeart.heartRightX);

        // Check if the bullet overlaps with the heart on the Y-axis
        boolean isYOverlap = (bulletBottomY > playerHeart.heartTopY && bulletTopY < playerHeart.heartBottomY);

        if (isXOverlap && isYOverlap) {
            bulletActive = false;
            if (!alreadyHit) {
                playerHealth -= bulletDamage;
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

    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastSavedTime >= 20) {
            switch (whichSide) {
                case "top":
                    yOfBullet += bulletSpeed;
                    xOfBullet += random.nextInt(5) - 2;
                    break;
                case "bottom":
                    yOfBullet -= bulletSpeed;
                    xOfBullet += random.nextInt(5) - 2;
                    break;
                case "left":
                    xOfBullet += bulletSpeed;
                    yOfBullet += random.nextInt(5) - 2;
                    break;
                case "right":
                    xOfBullet -= bulletSpeed;
                    yOfBullet += random.nextInt(5) - 2;
                    break;
                default:
                    break;
            }
            bulletHitBox.x = xOfBullet + bullet.getWidth();
            bulletHitBox.y = yOfBullet;
            lastSavedTime = currentTime;
        }
        checkBulletCollision();
    }

    public void draw(Graphics2D g2) {
        if (bulletActive) {
            g2.setColor(Color.BLUE); // For missile hitbox
            g2.drawRect(bulletHitBox.x, bulletHitBox.y, bulletHitBox.width,
                    bulletHitBox.height);
            g2.drawImage(bullet, xOfBullet + bullet.getWidth(), yOfBullet, null);
        }
    }
}

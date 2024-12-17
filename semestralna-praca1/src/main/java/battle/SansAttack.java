package battle;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Flow;

import javax.imageio.ImageIO;

import semestralka.GamePanel;

public class SansAttack extends Battle {
    private GamePanel gp;
    private FightMenu fightMenu;
    private PlayerHeartBlue playerHeart;
    private BufferedImage bullet;
    private int xOfBullet, yOfBullet;
    private long lastSavedTime;
    private Rectangle bulletHitBox;
    private boolean bulletActive;
    private boolean alreadyHit = false;
    private int bulletSpeed;
    private int bulletDamage;
    private Random random;
    private String whichSide;
    private int sizeOfBullet;
    private int boneHeight;

    public SansAttack(GamePanel gp, PlayerHeartBlue playerHeart) {
        super(gp);
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
            bullet = ImageIO.read(getClass().getResourceAsStream("/res/projectile/sans-bone.png"));
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void setFightMenu(FightMenu fightMenu, String whichSide) {
        this.fightMenu = fightMenu;
        this.whichSide = whichSide;

        int tempWidthOfBattleRect = gp.getScreenWidth() / 3;
        int tempxOfBattleRect = (gp.getScreenWidth() - tempWidthOfBattleRect) / 2;
        int tempyOfBattleRect = gp.getScreenHeight() / 5 * 2;
        int tempHeightOfBattleRect = 270;

        boneHeight = random.nextInt(100) + 20;

        sizeOfBullet = bullet.getWidth() * 2;

        yOfBullet = tempyOfBattleRect + tempHeightOfBattleRect - boneHeight - 5;

        // Choosing random starting position of bullet based on width of rectangle
        if (whichSide.equals("left")) {
            xOfBullet = tempxOfBattleRect + sizeOfBullet;
        }

        else if (whichSide.equals("right")) {
            xOfBullet = tempxOfBattleRect + tempWidthOfBattleRect - sizeOfBullet * 2;
        }

        else {
            System.out.println("Error in setting bullet position");
        }

        bulletHitBox = new Rectangle(xOfBullet + bullet.getWidth() * 2, yOfBullet, bullet.getWidth() * 2,
                boneHeight);
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

    public void bulletLogic(int damage) {
        bulletSpeed = 300 / boneHeight + 1;
        bulletDamage = damage;
    }

    public boolean getBulletActive() {
        return bulletActive;
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastSavedTime >= 20) {
            switch (whichSide) {
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
        }
        checkBulletCollision();
    }

    public void draw(Graphics2D g2) {
        if (bulletActive) {
            // g2.setColor(Color.BLUE); // For missile hitbox
            // g2.drawRect(bulletHitBox.x, bulletHitBox.y, bulletHitBox.width,
            // bulletHitBox.height);

            g2.drawImage(bullet, xOfBullet + bullet.getWidth(), yOfBullet, bullet.getWidth() * 2, boneHeight, null);
        }
    }
}
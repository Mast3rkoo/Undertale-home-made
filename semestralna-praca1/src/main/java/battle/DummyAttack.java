package battle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
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
    private Rectangle bigBulletHitBox;
    private boolean bulletActive;
    private boolean alreadyHit = false;
    private int bulletSpeed;
    private int bulletDamage;
    private String whichSide;
    private int sizeOfBullet;
    private int sizeOfBigBullet;
    private long lastSavedTimeForExplosion;
    private boolean bulletExploded = false;
    private ArrayList<Rectangle> bulletsHitbox = new ArrayList<>();

    public DummyAttack(GamePanel gp, PlayerHeart playerHeart) {
        super(gp);
        this.gp = gp;
        this.playerHeart = playerHeart;

        getProjectile();
        bulletActive = true;
        bulletSpeed = 0;
        bulletDamage = 0;
        sizeOfBigBullet = getHeightOfBattleRect() / 20;
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

    public Rectangle makeBullet(int x, int y, int size) {
        return new Rectangle(x, y, size, size);
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
            int minX = tempxOfBattleRect + sizeOfBigBullet;
            int maxX = tempxOfBattleRect + tempWidthOfBattleRect - sizeOfBigBullet;
            xOfBullet = (int) (Math.random() * (maxX - minX)) + minX;
            yOfBullet = tempyOfBattleRect + bullet.getHeight();
        }

        else if (whichSide.equals("bottom")) {
            int minX = tempxOfBattleRect + bullet.getWidth();
            int maxX = tempxOfBattleRect + tempWidthOfBattleRect - sizeOfBigBullet;
            xOfBullet = (int) (Math.random() * (maxX - minX)) + minX;
            yOfBullet = tempyOfBattleRect + tempHeightOfBattleRect - sizeOfBigBullet;
        }

        else if (whichSide.equals("left")) {
            int minY = tempyOfBattleRect + sizeOfBigBullet;
            int maxY = tempyOfBattleRect + tempHeightOfBattleRect - sizeOfBigBullet;
            xOfBullet = tempxOfBattleRect + sizeOfBigBullet;
            yOfBullet = (int) (Math.random() * (maxY - minY)) + minY;
        }

        else if (whichSide.equals("right")) {
            int minY = tempyOfBattleRect + sizeOfBigBullet;
            int maxY = tempyOfBattleRect + tempHeightOfBattleRect + sizeOfBigBullet;
            xOfBullet = tempxOfBattleRect + tempWidthOfBattleRect - sizeOfBigBullet * 2;
            yOfBullet = (int) (Math.random() * (maxY - minY)) + minY;
        }

        else {
            System.out.println("Error in setting bullet position");
        }

        bigBulletHitBox = makeBullet(xOfBullet + bullet.getWidth(), yOfBullet, sizeOfBigBullet);

        timerBeforeExplosion();
    }

    public boolean checkBulletCollision(Rectangle bulletHitBox) {
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
            if (!bulletExploded) {
                bulletActive = false;
                if (!alreadyHit) {
                    fightMenu.changeHealth(bulletDamage);
                    alreadyHit = true;
                }
                return false;
            } else {
                if (!alreadyHit) {
                    fightMenu.changeHealth(bulletDamage);
                    alreadyHit = true;
                }
                return true;
            }
        }

        if (bulletBottomY >= rectBottomY || bulletLeftX <= rectLeftX || bulletRightX >= rectRightX
                || bulletTopY <= rectTopY) {
            if (!bulletExploded) {
                bulletActive = false;
                return false;
            } else {
                return true;
            }
        }

        return false;
    }

    public void bulletLogic(int speed, int damage) {
        bulletSpeed = speed;
        bulletDamage = damage;
    }

    public boolean getBulletActive() {
        return bulletActive;
    }

    public void makeBulletExplosion() {
        bulletsHitbox.add(makeBullet(xOfBullet + bullet.getWidth() + 5, yOfBullet, sizeOfBullet));
        bulletsHitbox.add(makeBullet(xOfBullet + bullet.getWidth() - 5, yOfBullet, sizeOfBullet));
        bulletsHitbox.add(makeBullet(xOfBullet + bullet.getWidth(), yOfBullet + 5, sizeOfBullet));
        bulletsHitbox.add(makeBullet(xOfBullet + bullet.getWidth(), yOfBullet - 5, sizeOfBullet));
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
                bigBulletHitBox.x = xOfBullet + bullet.getWidth();
                bigBulletHitBox.y = yOfBullet;
                checkBulletCollision(bigBulletHitBox);
                lastSavedTime = currentTime;
            } else if (!bulletExploded) {
                makeBulletExplosion();
                bulletExploded = true;
            }

            if (bulletExploded) {

                for (int i = 0; i < bulletsHitbox.size(); i++) {
                    Rectangle bullet = bulletsHitbox.get(i);

                    if (bullet == null) {
                        if (bulletsHitbox.get(0) == null && bulletsHitbox.get(1) == null && bulletsHitbox.get(2) == null
                                && bulletsHitbox.get(3) == null) {
                            bulletActive = false;
                            break;
                        }
                        continue;
                    }

                    switch (i % 4) {
                        case 0:
                            bullet.x += bulletSpeed;
                            break;
                        case 1:
                            bullet.x -= bulletSpeed;
                            break;
                        case 2:
                            bullet.y += bulletSpeed;
                            break;
                        case 3:
                            bullet.y -= bulletSpeed;
                            break;
                    }

                    if (checkBulletCollision(bullet)) {
                        bulletsHitbox.set(i, null);
                    }
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        if (bulletActive) {
            if (bulletExploded) {
                g2.setColor(Color.RED);
                for (Rectangle bulletHitbox : bulletsHitbox) {
                    if (bulletHitbox == null) {
                        continue;
                    }
                    g2.drawRect(bulletHitbox.x, bulletHitbox.y, bulletHitbox.width, bulletHitbox.height);
                    g2.drawImage(bullet, bulletHitbox.x, bulletHitbox.y, bulletHitbox.width, bulletHitbox.height, null);
                }
            } else {
                if (bigBulletHitBox != null) {
                    g2.setColor(Color.BLUE); // For missile hitbox
                    g2.drawRect(bigBulletHitBox.x, bigBulletHitBox.y, bigBulletHitBox.width, bigBulletHitBox.height);
                    g2.drawImage(bullet, xOfBullet + bullet.getWidth(), yOfBullet, bigBulletHitBox.width,
                            bigBulletHitBox.height, null);
                }
            }
        }
    }
}

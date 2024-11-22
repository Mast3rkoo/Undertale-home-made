package battle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;

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

    public Projectile(GamePanel gp, PlayerHeart playerHeart) {
        this.gp = gp;
        this.playerHeart = playerHeart;

        getProjectile();
        bulletActive = true;
    }

    public void getProjectile() {
        try {
            bullet = ImageIO.read(getClass().getResourceAsStream("/res/projectile/basic-bullet.png"));
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void setFightMenu(FightMenu fightMenu) {
        this.fightMenu = fightMenu;

        int temporalWidthOfBattleRect = (gp.screenWidth - 80) / 2;
        int temporalXofBattleRect = (gp.screenWidth - temporalWidthOfBattleRect) / 2;
        int temporalYofBattleRect = gp.tileSize * 5;

        // Choosing random starting position of bullet based on width of rectangle
        xOfBullet = (int) (Math.random()
                * ((temporalXofBattleRect + temporalWidthOfBattleRect - bullet.getWidth()) - (temporalXofBattleRect
                        + bullet.getWidth())))
                + temporalXofBattleRect;
        yOfBullet = temporalYofBattleRect + bullet.getHeight();
        bulletHitBox = new Rectangle(xOfBullet + bullet.getWidth(), yOfBullet, 5, 5);
    }

    public void checkBulletCollision() {
        battleRectHitbox = fightMenu.setBattleRectHitbox();

        // Bottom side
        int rectBottomY = battleRectHitbox.y + battleRectHitbox.height;
        int bulletBottomY = bulletHitBox.y + bulletHitBox.height;

        // Top side
        int bulletTopY = bulletHitBox.y;

        // Left side
        int bulletLeftX = bulletHitBox.x;

        // Right side
        int bulletRightX = bulletHitBox.x + bulletHitBox.width;

        // Check if the bullet overlaps with the heart on the X-axis
        boolean isXOverlap = (bulletRightX > playerHeart.heartLeftX && bulletLeftX < playerHeart.heartRightX);

        // Check if the bullet overlaps with the heart on the Y-axis
        boolean isYOverlap = (bulletBottomY > playerHeart.heartTopY && bulletTopY < playerHeart.heartBottomY);

        if (isXOverlap && isYOverlap) {
            bulletActive = false;
            if (!alreadyHit) {
                playerHealth -= 10;
                alreadyHit = true;
            }
        }

        if (bulletBottomY >= rectBottomY) {
            bulletActive = false;
        }
        // if (bulletLeftX <= rectLeftX) {
        // return false;
        // }
        // if (bulletRightX >= rectRightX) {
        // return false;
        // }
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastSavedTime >= 10) {
            yOfBullet++;
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

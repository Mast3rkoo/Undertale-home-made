package battle;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import semestralka.GamePanel;
import semestralka.KeyHandler;

public class PlayerHeart extends Battle {
    GamePanel gp;
    KeyHandler keyH;
    FightMenu fightMenu;
    private Rectangle heartHitBox;

    public PlayerHeart(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        heartSpeed = 3;
        directionHeart = "";

        worldXHeart = (gp.screenWidth - 16) / 2; // Calculating the middle of rectangle, 16 is the width of heart
        worldYHeart = (gp.screenHeight + 170 / 2) / 2; // 170 is the height of rectangle

        heartHitBox = new Rectangle(worldXHeart, worldYHeart, 16, 16);

        getPlayerHeartImage();
    }

    public void getPlayerHeartImage() {
        try {
            heart = ImageIO.read(getClass().getResourceAsStream("/res/fightMenu/heart.png"));
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void setFightMenu(FightMenu fightMenu) {
        this.fightMenu = fightMenu;
    }

    public void checkCollision() {
        battleRectHitbox = fightMenu.setBattleRectHitbox();

        // Top edge of the heart
        int heartTopY = heartHitBox.y;
        int rectTopY = battleRectHitbox.y;

        // Left side
        int rectLeftX = battleRectHitbox.x;
        int heartLeftX = heartHitBox.x;

        // Right side
        int rectRightX = battleRectHitbox.x + battleRectHitbox.width;
        int heartRightX = heartHitBox.x + heartHitBox.width;

        // Bottom side
        int rectBottomY = battleRectHitbox.y + battleRectHitbox.height;
        int heartBottomY = heartHitBox.y + heartHitBox.height;

        // Check each side of the battle rectangle
        if (heartTopY <= rectTopY) {
            worldYHeart = rectTopY + 2;
            heartHitBox.y = worldYHeart;
        }
        if (heartBottomY >= rectBottomY) {
            worldYHeart = rectBottomY - heartHitBox.height - 2;
            heartHitBox.y = worldYHeart;
        }
        if (heartLeftX <= rectLeftX) {
            worldXHeart = rectLeftX + heartSpeed;
            heartHitBox.x = worldXHeart;
        }
        if (heartRightX >= rectRightX) {
            worldXHeart = rectRightX - heartSpeed - heartHitBox.width;
            heartHitBox.x = worldXHeart;
        }

    }

    public void update() {
        if (keyH.upPressed && !keyH.rightPressed && !keyH.leftPressed
                && !keyH.downPressed) {
            worldYHeart -= heartSpeed;
            heartHitBox.y -= heartSpeed;
        }
        if (keyH.downPressed && !keyH.rightPressed && !keyH.leftPressed
                && !keyH.upPressed) {
            worldYHeart += heartSpeed;
            heartHitBox.y += heartSpeed;
        }
        if (keyH.leftPressed && !keyH.upPressed && !keyH.downPressed
                && !keyH.rightPressed) {
            worldXHeart -= heartSpeed;
            heartHitBox.x -= heartSpeed;
        }
        if (keyH.rightPressed && !keyH.upPressed && !keyH.downPressed
                && !keyH.leftPressed) {
            worldXHeart += heartSpeed;
            heartHitBox.x += heartSpeed;
        }
        if (keyH.upPressed && keyH.rightPressed) {
            worldYHeart -= heartSpeed;
            worldXHeart += heartSpeed;
            heartHitBox.y -= heartSpeed;
            heartHitBox.x += heartSpeed;
        }
        if (keyH.upPressed && keyH.leftPressed) {
            worldYHeart -= heartSpeed;
            worldXHeart -= heartSpeed;
            heartHitBox.y -= heartSpeed;
            heartHitBox.x -= heartSpeed;

        }
        if (keyH.downPressed && keyH.rightPressed) {
            worldYHeart += heartSpeed;
            worldXHeart += heartSpeed;
            heartHitBox.y += heartSpeed;
            heartHitBox.x += heartSpeed;
        }
        if (keyH.downPressed && keyH.leftPressed) {
            worldYHeart += heartSpeed;
            worldXHeart -= heartSpeed;
            heartHitBox.y += heartSpeed;
            heartHitBox.x -= heartSpeed;
        }
        checkCollision();

    }

    public void draw(Graphics2D g2) {

        g2.drawImage(heart, worldXHeart, worldYHeart, 16, 16, null);
    }
}

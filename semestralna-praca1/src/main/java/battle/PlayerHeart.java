package battle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import semestralka.GamePanel;
import semestralka.KeyHandler;

public class PlayerHeart extends Battle {
    private KeyHandler keyH;
    private FightMenu fightMenu;
    private Rectangle heartHitBox;
    private BufferedImage heart;
    private int heartSpeed;

    public PlayerHeart(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        heartSpeed = 3;

        setWorldXHeart((gp.getScreenWidth() - 16) / 2); // Calculating the middle of rectangle, 16 is the width of heart
        setWorldYHeart((gp.getScreenHeight() + 170 / 2) / 2); // 170 is the height of rectangle

        heartHitBox = new Rectangle(getWorldXHeart(), getWorldYHeart(), 16, 16);

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

    public void setPlayerHeartHitbox(Rectangle newHitbox) {
        heartHitBox = newHitbox;
    }

    public void checkCollision() {
        Rectangle battleRectHitbox = fightMenu.getBattleRectHitbox();

        // Top edge of the heart
        setHeartTopY(heartHitBox.y);
        int rectTopY = battleRectHitbox.y;

        // Left side
        int rectLeftX = battleRectHitbox.x;
        setHeartLeftX(heartHitBox.x);

        // Right side
        int rectRightX = battleRectHitbox.x + battleRectHitbox.width;
        setHeartRightX(heartHitBox.x + heartHitBox.width);

        // Bottom side
        int rectBottomY = battleRectHitbox.y + battleRectHitbox.height;
        setHeartBottomY(heartHitBox.y + heartHitBox.height);

        // Check each side of the battle rectangle
        if (getHeartTopY() <= rectTopY) {
            setWorldYHeart(rectTopY + 2);
            heartHitBox.y = getWorldYHeart();
        }
        if (getHeartBottomY() >= rectBottomY) {
            setWorldYHeart(rectBottomY - heartHitBox.height - 2);
            heartHitBox.y = getWorldYHeart();
        }
        if (getHeartLeftX() <= rectLeftX) {
            setWorldXHeart(rectLeftX + heartSpeed);
            heartHitBox.x = getWorldXHeart();
        }
        if (getHeartRightX() >= rectRightX) {
            setWorldXHeart(rectRightX - heartSpeed - heartHitBox.width);
            heartHitBox.x = getWorldXHeart();
        }
    }

    public int getWorldHeart(String coordinate) {
        switch (coordinate) {
            case "leftX":
                return getHeartLeftX();
            case "rightX":
                return getHeartRightX();
            case "topY":
                return getHeartTopY();
            case "bottomY":
                return getHeartBottomY();
            default:
                return 0;
        }
    }

    public void update() {
        if (keyH.isUpPressed() && !keyH.isRightPressed() && !keyH.isLeftPressed()
                && !keyH.isDownPressed()) {
            setWorldYHeart(getWorldYHeart() - heartSpeed);
            heartHitBox.y -= heartSpeed;
        }
        if (keyH.isDownPressed() && !keyH.isRightPressed() && !keyH.isLeftPressed()
                && !keyH.isUpPressed()) {
            setWorldYHeart(getWorldYHeart() + heartSpeed);
            heartHitBox.y += heartSpeed;
        }
        if (keyH.isLeftPressed() && !keyH.isUpPressed() && !keyH.isDownPressed()
                && !keyH.isRightPressed()) {
            setWorldXHeart(getWorldXHeart() - heartSpeed);
            heartHitBox.x -= heartSpeed;
        }
        if (keyH.isRightPressed() && !keyH.isUpPressed() && !keyH.isDownPressed()
                && !keyH.isLeftPressed()) {
            setWorldXHeart(getWorldXHeart() + heartSpeed);
            heartHitBox.x += heartSpeed;
        }
        if (keyH.isUpPressed() && keyH.isRightPressed()) {
            setWorldYHeart(getWorldYHeart() - heartSpeed);
            setWorldXHeart(getWorldXHeart() + heartSpeed);
            heartHitBox.y -= heartSpeed;
            heartHitBox.x += heartSpeed;
        }
        if (keyH.isUpPressed() && keyH.isLeftPressed()) {
            setWorldYHeart(getWorldYHeart() - heartSpeed);
            setWorldXHeart(getWorldXHeart() - heartSpeed);
            heartHitBox.y -= heartSpeed;
            heartHitBox.x -= heartSpeed;

        }
        if (keyH.isDownPressed() && keyH.isRightPressed()) {
            setWorldYHeart(getWorldYHeart() + heartSpeed);
            setWorldXHeart(getWorldXHeart() + heartSpeed);
            heartHitBox.y += heartSpeed;
            heartHitBox.x += heartSpeed;
        }
        if (keyH.isDownPressed() && keyH.isLeftPressed()) {
            setWorldYHeart(getWorldYHeart() + heartSpeed);
            setWorldXHeart(getWorldXHeart() - heartSpeed);
            heartHitBox.y += heartSpeed;
            heartHitBox.x -= heartSpeed;
        }
        checkCollision();
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.RED); // For heartHitBox
        g2.drawRect(heartHitBox.x, heartHitBox.y, heartHitBox.width,
                heartHitBox.height);

        g2.drawImage(heart, heartHitBox.x, heartHitBox.y, 16, 16, null);
    }
}

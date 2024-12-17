package battle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import semestralka.GamePanel;
import semestralka.KeyHandler;

public class PlayerHeartBlue extends Battle {
    private KeyHandler keyH;
    private FightMenu fightMenu;
    private GamePanel gp;
    private Rectangle heartHitBox;
    private BufferedImage heartBlue;
    private int heartSpeed;
    private boolean playerJumped = false;
    private boolean onGround = false;
    private double velocityY = 0;
    private final double gravity = 0.4;
    private final double maxJumpStrength = 5.5;
    private final double maxFallSpeed = 4;

    public PlayerHeartBlue(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        this.gp = gp;

        heartSpeed = 4;

        heartHitBox = new Rectangle(getWorldXHeart(), getWorldYHeart(), 16, 16);
        resetPlayerPosition();

        getPlayerHeartImage();
    }

    public void getPlayerHeartImage() {
        try {
            heartBlue = ImageIO.read(getClass().getResourceAsStream("/res/fightMenu/heart-blue.png"));
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void resetPlayerPosition() {
        int rectBottomY = getBattleRectHitbox().y + getBattleRectHitbox().height;
        setWorldXHeart((this.gp.getScreenWidth() - 16) / 2);
        setWorldYHeart(rectBottomY - heartHitBox.height - 2);
        heartHitBox.x = getWorldXHeart();
        heartHitBox.y = getWorldYHeart();
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
        int rectBottomY = getBattleRectHitbox().y + getBattleRectHitbox().height;

        // Ak hrac len klikne W tak naraz velky skok
        if (keyH.isUpPressed() && onGround) {
            velocityY = -maxJumpStrength;
            playerJumped = true;
            onGround = false;
        }

        // Ak hrac drzi W tak skace vyssie pomalsie a pomalsie
        if (playerJumped) {
            if (keyH.isUpPressed() && velocityY < 0) {
                // Apply weaker gravity if still holding jump and still going up
                velocityY += (gravity * 0.2); // half gravity, for example
            } else {
                // Normal gravity otherwise
                velocityY += gravity;
            }

            // Cap fall speed
            if (velocityY > maxFallSpeed) {
                velocityY = maxFallSpeed;
            }

            setWorldYHeart(getWorldYHeart() + (int) velocityY);
            heartHitBox.y = getWorldYHeart();

            // Check for collision with the ground
            if (getWorldYHeart() + heartHitBox.height >= rectBottomY - 2) {
                // Snap to ground
                setWorldYHeart(rectBottomY - heartHitBox.height - 2);
                heartHitBox.y = getWorldYHeart();
                velocityY = 0;
                playerJumped = false;
                onGround = true;
            }
        } else {
            // Apply gravity
            velocityY += gravity;

            // Cap fall speed
            if (velocityY > maxFallSpeed) {
                velocityY = maxFallSpeed;
            }

            setWorldYHeart(getWorldYHeart() + (int) velocityY);
            heartHitBox.y = getWorldYHeart();

            // Check for collision with the ground
            if (getWorldYHeart() + heartHitBox.height >= rectBottomY - 2) {
                // Snap to ground
                setWorldYHeart(rectBottomY - heartHitBox.height - 2);
                heartHitBox.y = getWorldYHeart();
                velocityY = 0;
                onGround = true;
            }
        }

        // Horizontal movement stays the same
        if (keyH.isLeftPressed()) {
            setWorldXHeart(getWorldXHeart() - heartSpeed);
            heartHitBox.x = getWorldXHeart();
        }
        if (keyH.isRightPressed()) {
            setWorldXHeart(getWorldXHeart() + heartSpeed);
            heartHitBox.x = getWorldXHeart();
        }

        checkCollision();

    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.RED); // For heartHitBox
        g2.drawRect(heartHitBox.x, heartHitBox.y, heartHitBox.width,
                heartHitBox.height);

        g2.drawImage(heartBlue, heartHitBox.x, heartHitBox.y, 16, 16, null);
    }
}

package battle;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import semestralka.GamePanel;

public class Battle {
    private boolean fightMenu = false;

    // Players heart attributes
    private int worldXHeart, worldYHeart;
    private int playerHealth = 100;

    private int heartTopY, heartBottomY, heartLeftX, heartRightX;
    private int numberOfTurn = 0;

    private int widthOfBattleRect;
    private int heightOfBattleRect;
    private int xOfBattleRect;
    private int yOfBattleRect;

    private Rectangle battleRectHitbox;

    private BufferedImage[] enemyImages = new BufferedImage[4];

    public Battle(GamePanel gp) {
        xOfBattleRect = gp.getScreenWidth() / 10;
        yOfBattleRect = gp.getScreenHeight() / 3;

        widthOfBattleRect = gp.getScreenWidth() - 80;
        heightOfBattleRect = 270;

        battleRectHitbox = new Rectangle(xOfBattleRect, yOfBattleRect, widthOfBattleRect, heightOfBattleRect);
    }

    public BufferedImage[] splitEnemyImages(BufferedImage image, int imageCount) {
        int subImageWidth = image.getWidth() / 2;
        int subImageHeight = image.getHeight() / (imageCount / 2);

        int index = 0;
        for (int row = 0; row < imageCount / 2; row++) {
            for (int col = 0; col < 2; col++) {
                enemyImages[index] = image.getSubimage(
                        col * subImageWidth,
                        row * subImageHeight,
                        subImageWidth,
                        subImageHeight);
                index++;
            }
        }
        return enemyImages;
    }

    public BufferedImage[] getFloweyImages() {
        BufferedImage flowey = null;
        try {
            flowey = ImageIO.read(getClass().getResourceAsStream("/res/enemy/flowey.png"));
            return splitEnemyImages(flowey, 4);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getWidthOfBattleRect() {
        return this.widthOfBattleRect;
    }

    public int getHeightOfBattleRect() {
        return this.heightOfBattleRect;
    }

    public int getXOfBattleRect() {
        return this.xOfBattleRect;
    }

    public int getYOfBattleRect() {
        return this.yOfBattleRect;
    }

    public void setWidthOfBattleRect(int widthOfBattleRect) {
        this.widthOfBattleRect = widthOfBattleRect;
    }

    public void setXOfBattleRect(int xOfBattleRect) {
        this.xOfBattleRect = xOfBattleRect;
    }

    public void setYOfBattleRect(int yOfBattleRect) {
        this.yOfBattleRect = yOfBattleRect;
    }

    public boolean getFightMenu() {
        return this.fightMenu;
    }

    public void setFightMenu(boolean fightMenu) {
        this.fightMenu = fightMenu;
    }

    public int getWorldXHeart() {
        return this.worldXHeart;
    }

    public int getWorldYHeart() {
        return this.worldYHeart;
    }

    public void setWorldXHeart(int worldXHeart) {
        this.worldXHeart = worldXHeart;
    }

    public void setWorldYHeart(int worldYHeart) {
        this.worldYHeart = worldYHeart;
    }

    public int getPlayerHealth() {
        return this.playerHealth;
    }

    public void setPlayerHealth(int health) {
        this.playerHealth = health;
    }

    public int getHeartTopY() {
        return this.heartTopY;
    }

    public int getHeartBottomY() {
        return this.heartBottomY;
    }

    public int getHeartLeftX() {
        return this.heartLeftX;
    }

    public int getHeartRightX() {
        return this.heartRightX;
    }

    public void setHeartTopY(int heartTopY) {
        this.heartTopY = heartTopY;
    }

    public void setHeartBottomY(int heartBottomY) {
        this.heartBottomY = heartBottomY;
    }

    public void setHeartLeftX(int heartLeftX) {
        this.heartLeftX = heartLeftX;
    }

    public void setHeartRightX(int heartRightX) {
        this.heartRightX = heartRightX;
    }

    public int getNumberOfTurn() {
        return this.numberOfTurn;
    }

    public void setNumberOfTurn(int numberOfTurn) {
        this.numberOfTurn = numberOfTurn;
    }

    public Rectangle getBattleRectHitbox() {
        this.battleRectHitbox = new Rectangle(xOfBattleRect, yOfBattleRect, widthOfBattleRect, heightOfBattleRect);
        return this.battleRectHitbox;
    }
}

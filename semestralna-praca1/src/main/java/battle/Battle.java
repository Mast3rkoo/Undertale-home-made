package battle;

import java.awt.Rectangle;

import semestralka.GamePanel;

public class Battle {
    private boolean fightMenu = true;

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

    public Battle(GamePanel gp) {
        xOfBattleRect = gp.getScreenWidth() / 10;
        yOfBattleRect = gp.getTileSize() * 5;

        widthOfBattleRect = gp.getScreenWidth() - 80;
        heightOfBattleRect = 170;

        battleRectHitbox = new Rectangle(xOfBattleRect, yOfBattleRect, widthOfBattleRect, heightOfBattleRect);
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

    public void setHeightOfBattleRect(int heightOfBattleRect) {
        this.heightOfBattleRect = heightOfBattleRect;
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
        this.battleRectHitbox = new Rectangle(xOfBattleRect, yOfBattleRect, widthOfBattleRect, 170);
        return this.battleRectHitbox;
    }
}

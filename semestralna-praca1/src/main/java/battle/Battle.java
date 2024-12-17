package battle;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import semestralka.GamePanel;

public class Battle {
    private boolean fightMenu = false;

    // Players heart attributes
    private int worldXHeart, worldYHeart;
    private int playerHealth;

    private int heartTopY, heartBottomY, heartLeftX, heartRightX;
    private int numberOfTurn = 0;

    private int widthOfBattleRect;
    private int heightOfBattleRect;
    private int xOfBattleRect;
    private int yOfBattleRect;

    private Rectangle battleRectHitbox;

    private BufferedImage[] enemyImages = new BufferedImage[4];
    private int enemyHp;
    private int enemyDamage;
    private int enemySpeed;
    private int enemyWidth;
    private int enemyHeight;
    private String[] enemyDialogue;
    private String[] actOptions;

    public Battle(GamePanel gp) {
        this.playerHealth = 100;
        xOfBattleRect = gp.getScreenWidth() / 10;
        yOfBattleRect = gp.getScreenHeight() / 5 * 2;

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

    private BufferedImage[] getEnemyImage(String enemy, int imageCount) {
        BufferedImage enemyImage = null;
        try {
            enemyImage = ImageIO.read(getClass().getResourceAsStream("/res/enemy/" + enemy + ".png"));
            return splitEnemyImages(enemyImage, imageCount);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BufferedImage[] getFloweyAttributes() {
        this.enemyHp = 100;
        this.enemyDamage = 10;
        this.enemySpeed = 4;
        this.enemyDialogue = new String[] {
                "You've encountered Flowey!", // Encounter
                "You attacked Flowey!", // Attack action
                "Trying to attack? How pathetic!", // Attack action
                "You beg Flowey to stop.",
                "Stop? We're just getting started!", // Begging
                "You check Flowey's stats. HP: 100, Damage: 10", // Checking stats
                "You used an item.", // Using an item
                "Healing? How cute!", // Using an item
                "You tried to run, but Flowey blocked your path.", // Trying to run
                "You're not going anywhere!", // Trying to run
                "You won!", // Winning
                "Yes I am that strong!", // Checking stats
                "" // Clear dialogue - 12
        };
        this.actOptions = new String[] { "Check", "Beg" };
        this.enemyWidth = 100;
        this.enemyHeight = 100;
        return getEnemyImage("flowey", 2);
    }

    public BufferedImage[] getDummyAttributes() {
        this.enemyHp = 200;
        this.enemyDamage = 5;
        this.enemySpeed = 2;
        this.enemyDialogue = new String[] { "You've encountered Dummy!", // Encounter
                "You attacked Dummy!", // Attack action
                "Dummy is not impressed.", // Attack action
                "You beg Dummy to stop.",
                "Dummy is not impressed.", // Pushing
                "You check Dummy's stats. HP: 200, Damage: 5", // Checking stats
                "You used an item.", // Using an item
                "Dummy is not impressed.", // Using an item
                "You tried to run, but Dummy blocked your path.", // Trying to run
                "Dummy is not impressed.", // Trying to run
                "You won!", // Winning
                "I'm not that weak!", // Checking stats
                "" // Clear dialogue - 12
        };
        this.actOptions = new String[] { "Check", "Push" };
        this.enemyWidth = 120;
        this.enemyHeight = 180;
        return getEnemyImage("dummy", 2);
    }

    public BufferedImage[] getSansAttributes() {
        this.enemyHp = 300;
        this.enemyDamage = 15;
        this.enemySpeed = 6;
        this.enemyDialogue = new String[] { "You've encountered Sans!", // Encounter
                "You attacked Sans!", // Attack action
                "You're gonna have a bad time.", // Attack action
                "You beg Sans to stop.",
                "You're gonna pay.", // Begging
                "You check Sans's stats. HP: 300, Damage: 15", // Checking stats
                "You used an item.", // Using an item
                "You're gonna have a bad time.", // Using an item
                "You tried to run, but Sans blocked your path.", // Trying to run
                "You're gonna have a bad time.", // Trying to run
                "You won!", // Winning
                "I'm not that weak!", // Checking stats
                "" // Clear dialogue - 12
        };
        this.actOptions = new String[] { "Check", "Beg" };
        this.enemyWidth = 100;
        this.enemyHeight = 100;
        return getEnemyImage("sans", 4);
    }

    public String[] getActOptions() {
        return this.actOptions;
    }

    public int getEnemyHp() {
        return this.enemyHp;
    }

    public int getEnemyDamage() {
        return this.enemyDamage;
    }

    public int getEnemySpeed() {
        return this.enemySpeed;
    }

    public int getEnemyWidth() {
        return this.enemyWidth;
    }

    public int getEnemyHeight() {
        return this.enemyHeight;
    }

    public String[] getEnemyDialogue() {
        return this.enemyDialogue;
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

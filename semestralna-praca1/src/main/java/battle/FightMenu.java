package battle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.Timer;

import javax.imageio.ImageIO;

import semestralka.GamePanel;
import semestralka.KeyHandler;

public class FightMenu extends Battle {
    private GamePanel gp;
    private KeyHandler keyH;
    private DummyAttack projectile;
    private PlayerHeart playerHeart;
    private BufferedImage heart, dialogBox, actionButtons;
    private BufferedImage[] enemyImages = new BufferedImage[4];
    private BufferedImage[] actionButtonImages = new BufferedImage[8];
    private int enemyWidth;
    private int enemyHeight;
    private int subImageWidthActions;
    private int subImageHeightActions;
    private int positionOfHeart = 0;
    private int hpOfEnemy;
    private Random random;
    private int numberOfSprite;
    private String battleMessage;
    private ArrayList<DummyAttack> projectiles;
    private List<DummyAttack> tempProjectiles;
    private int delayBetweenBullets;
    private int xOfBattleRect, widthOfBattleRect;
    private int xOfButton, yOfButton;
    private int buttonImageWidth, buttonImageHeight;
    private int gapBetweenButtons;
    private int heartLocationX, heartLocationY;
    private String[] enemyDialogue;
    private int numberOfDialogueBox;
    private int numberOfDialogueEnemy;
    private boolean actOptionsOpen;
    private String[] actOptions;
    private int actHeartPosition;
    private int yOfHeartAct;
    private boolean isChoicePicked;
    private String chosenEnemy;

    public FightMenu(GamePanel gp, KeyHandler keyH, PlayerHeart playerHeart) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;
        this.playerHeart = playerHeart;

        random = new Random();
        numberOfSprite = 0;
        numberOfDialogueBox = 0;
        numberOfDialogueEnemy = 0;
        isChoicePicked = false;
        actHeartPosition = 0;
        yOfHeartAct = gp.getScreenHeight() / 3 + 30;

        xOfButton = gp.getScreenWidth() / 5;
        yOfButton = gp.getScreenHeight() - gp.getScreenHeight() / 7;

        gapBetweenButtons = gp.getScreenWidth() / 32;

        heartLocationX = xOfButton + 13;
        heartLocationY = yOfButton + 18;

        projectiles = new ArrayList<>();
        tempProjectiles = new ArrayList<>();

        getFightMenuImages();
        splitImagesActions();
    }

    public void getFightMenuImages() {
        try {
            actionButtons = ImageIO.read(getClass().getResourceAsStream("/res/fightMenu/action-choices.png"));
            heart = ImageIO.read(getClass().getResourceAsStream("/res/fightMenu/heart.png"));
            dialogBox = ImageIO.read(getClass().getResourceAsStream("/res/fightMenu/dialogBox.png"));
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void splitImagesActions() {
        subImageWidthActions = actionButtons.getWidth() / 2;
        subImageHeightActions = actionButtons.getHeight() / 4;

        buttonImageWidth = subImageWidthActions * 3 / 2;
        buttonImageHeight = subImageHeightActions * 3 / 2;

        int index = 0;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 2; col++) {
                actionButtonImages[index] = actionButtons.getSubimage(
                        col * subImageWidthActions,
                        row * subImageHeightActions,
                        subImageWidthActions,
                        subImageHeightActions);
                index++;
            }
        }
    }

    public void setEnemy(String enemy) {
        if (enemy.equals("flowey")) {
            enemyImages = getFloweyAttributes();
            chosenEnemy = "flowey";
        } else if (enemy.equals("dummy")) {
            enemyImages = getDummyAttributes();
            chosenEnemy = "dummy";
        }
        hpOfEnemy = getEnemyHp();
        enemyDialogue = getEnemyDialogue();
        actOptions = getActOptions();
        enemyWidth = getEnemyWidth();
        enemyHeight = getEnemyHeight();
        delayBetweenBullets = getDelayBetweenBullets();
    }

    public void enemyAttack() {
        gp.changeTurn("+");
        setNumberOfTurn(1);
        setBattleTurn();
        int numberOfProjectiles = random.nextInt(10) + 10;
        int randomSide = random.nextInt(3);
        String side = "";
        switch (randomSide) {
            case 0:
                side = "top";
                break;
            case 1:
                side = "bottom";
                break;
            case 2:
                side = "left";
                break;
            case 3:
                side = "right";
                break;
            default:
                break;
        }
        while (numberOfProjectiles > 0) {
            makeProjectile(2, random.nextInt(10) + getEnemyDamage(), side, delayBetweenBullets);
            numberOfProjectiles--;
        }
        tempProjectiles = new ArrayList<>(projectiles);
    }

    public void changeHealth(int health) {
        setPlayerHealth(getPlayerHealth() - health);
    }

    public void setTurn(int numberOfTurn) {
        setNumberOfTurn(numberOfTurn);
    }

    public void setEncounter(boolean fightMenu) {
        gp.getPlayer().setEnemyEncounterAlert(true);
        System.out.println("Setting fight menu to " + fightMenu);
        // Remove pressed enter
        numberOfDialogueBox = 0;
        numberOfDialogueEnemy = 0;
        isChoicePicked = false;
        actHeartPosition = 0;
        actOptionsOpen = false;
        positionOfHeart = 0;
        heartLocationX = xOfButton + 13;
        setNumberOfTurn(0);
        keyH.setEnterPressed(false);
        gp.drawEncounter(fightMenu);
    }

    private void removeEncounterTile() {
        if (chosenEnemy.equals("flowey")) {
            gp.resetEncounterTile(2);
        } else if (chosenEnemy.equals("dummy")) {
            gp.resetEncounterTile(79);
            gp.resetEncounterTile(80);
            gp.resetEncounterTile(81);
            gp.resetEncounterTile(82);

            gp.setCollisionTile(79);
            gp.setCollisionTile(80);
            gp.setCollisionTile(81);
            gp.setCollisionTile(82);
        }
    }

    public void update() {
        int numberOfTurn = getNumberOfTurn();
        // Action choices movement
        if (hpOfEnemy <= 0 || getPlayerHealth() <= 0) {
            removeEncounterTile();
            gp.changeFightMenu(false);
            return;
        }
        if (numberOfTurn == 0) {
            int spaceBetweenButtons = buttonImageWidth + gapBetweenButtons;
            if (keyH.isRightPressed() && positionOfHeart <= 2 && positionOfHeart >= 0) {
                heartLocationX += spaceBetweenButtons;
                positionOfHeart++;
                keyH.setRightPressed(false);
            } else if (keyH.isLeftPressed() && positionOfHeart >= 1 && positionOfHeart <= 3) {
                heartLocationX -= spaceBetweenButtons;
                positionOfHeart--;
                keyH.setLeftPressed(false);
            }
            if (keyH.isEnterPressed() && positionOfHeart == 0 && hpOfEnemy > 0 && numberOfTurn == 0
                    && !isChoicePicked) {
                keyH.setEnterPressed(false);
                // Attack logic
                numberOfSprite = 1;
                hpOfEnemy -= random.nextInt(21) + 10;
                numberOfDialogueEnemy = 2;
                // Changes sprite to make it look like it hurt the enemy and then changes it
                // back to 0 meaning the normal state after 1 second
                Timer timer = new Timer(2500, _ -> {
                    numberOfSprite = 0; // Reset sprite to 0
                    gp.repaint();
                });
                numberOfDialogueBox = 1;
                isChoicePicked = true;
                enemyAttack();
                timer.setRepeats(false);
                timer.start();
            } else if (keyH.isEnterPressed() && positionOfHeart == 1 && hpOfEnemy > 0 && numberOfTurn == 0
                    && !isChoicePicked) {
                keyH.setEnterPressed(false);
                isChoicePicked = true;
                actOptionsOpen = true;
            } else if (keyH.isEnterPressed() && positionOfHeart == 2 && hpOfEnemy > 0 && numberOfTurn == 0
                    && !isChoicePicked) {
                keyH.setEnterPressed(false);
                if (getPlayerHealth() < 100) {
                    int healedAmount = random.nextInt(21) + 10;
                    if (getPlayerHealth() + healedAmount > 100) {
                        setPlayerHealth(100);
                    } else {
                        setPlayerHealth(getPlayerHealth() + healedAmount);
                    }
                }
                numberOfDialogueBox = 6;
                numberOfDialogueEnemy = 7;
                isChoicePicked = true;
                enemyAttack();
            } else if (keyH.isEnterPressed() && positionOfHeart == 3 && hpOfEnemy > 0 && numberOfTurn == 0
                    && !isChoicePicked) {
                keyH.setEnterPressed(false);
                double chanceOfSpare = random.nextDouble() - hpOfEnemy / 1000 * 5;
                isChoicePicked = true;
                if (chanceOfSpare > 0.0) {
                    System.out.println("You spared the enemy with an " + chanceOfSpare + " chance");
                    removeEncounterTile();
                    gp.changeFightMenu(false);
                    return;
                } else {
                    numberOfDialogueBox = 8;
                    numberOfDialogueEnemy = 9;
                    enemyAttack();
                }

            }
            if (actOptionsOpen && isChoicePicked) {
                if (keyH.isDownPressed() && actHeartPosition == 0) {
                    yOfHeartAct = gp.getScreenHeight() / 3 + 30 + 60;
                    actHeartPosition++;
                    keyH.setDownPressed(false);
                } else if (keyH.isUpPressed() && actHeartPosition == 1) {
                    yOfHeartAct = gp.getScreenHeight() / 3 + 30;
                    actHeartPosition--;
                    keyH.setUpPressed(false);
                }
                if (keyH.isEnterPressed() && actHeartPosition == 0) {
                    keyH.setEnterPressed(false);
                    numberOfDialogueBox = 5;
                    numberOfDialogueEnemy = 11;
                    actOptionsOpen = false;
                    isChoicePicked = false;
                    enemyAttack();
                } else if (keyH.isEnterPressed() && actHeartPosition == 1) {
                    keyH.setEnterPressed(false);
                    numberOfDialogueEnemy = 4;
                    numberOfDialogueBox = 3;
                    actOptionsOpen = false;
                    isChoicePicked = false;
                    enemyAttack();
                }
            }
        }

        if (projectiles.size() > 0) {
            for (DummyAttack projectile : tempProjectiles) {
                projectile.update();
                if (!projectile.getBulletActive()) {
                    projectiles.remove(projectile);
                }
            }
        }

        if (projectiles.size() == 0 && numberOfTurn == 1) {
            gp.changeTurn("-");
            numberOfTurn--;
            setBattleTurn();
            keyH.setEnterPressed(false);
        }
    }

    public void setBattleTurn() {
        xOfBattleRect = getXOfBattleRect();
        widthOfBattleRect = getWidthOfBattleRect();
        if (getNumberOfTurn() == 1) {
            setWidthOfBattleRect(gp.getScreenWidth() / 3);
            setXOfBattleRect((gp.getScreenWidth() - widthOfBattleRect) / 2);
            isChoicePicked = false;
            battleMessage = "";
        } else {
            setWorldXHeart((gp.getScreenWidth() - 16) / 2);
            setWorldYHeart((gp.getScreenHeight() + 170 / 2) / 2);
            playerHeart.setPlayerHeartHitbox(new Rectangle(getWorldXHeart(), getWorldYHeart(), 16, 16));
            setWidthOfBattleRect(buttonImageWidth * 4 + gapBetweenButtons * 3);
            setXOfBattleRect(xOfButton);
            battleMessage = "Flowey wants to fight!";
        }
    }

    public void makeProjectile(int speed, int damage, String whichSide, int delayBetweenBullets) {
        setBattleTurn();
        projectile = new DummyAttack(gp, playerHeart);
        projectile.setFightMenu(this, whichSide);
        projectiles.add(projectile);
        projectile.bulletLogic(speed, damage);
    }

    public void drawFightMenu(Graphics2D g2) {
        setBattleTurn();
        // Rectangle battleRect = getBattleRectHitbox();
        // g2.setColor(Color.green);
        // g2.drawRect(battleRect.x, battleRect.y, battleRect.width, battleRect.height);

        g2.setFont(new Font("TimesRoman", Font.BOLD, 20));
        if (getNumberOfTurn() == 1) {
            g2.drawImage(dialogBox, (gp.getScreenWidth()) / 2 + enemyImages[0].getWidth() * 2, gp.getTileSize(),
                    dialogBox.getWidth() * 2 - 50,
                    dialogBox.getHeight() * 2 - 50, null);
            g2.setColor(Color.BLACK);
            g2.drawString(enemyDialogue[numberOfDialogueEnemy],
                    (gp.getScreenWidth()) / 2 + enemyImages[0].getWidth() * 2 + 60,
                    gp.getTileSize() + 40);

        } else {
            g2.setColor(Color.WHITE);
            g2.drawString(enemyDialogue[numberOfDialogueBox], xOfBattleRect + battleMessage.length(),
                    gp.getScreenHeight() / 5 * 2 + battleMessage.length() * 2);
        }

        if (actOptionsOpen) {
            numberOfDialogueBox = 12;

            int xOfChoice = xOfBattleRect + 20;
            int yOfChoice = gp.getScreenHeight() / 5 * 2 + 50;

            // Choice picker
            g2.drawImage(heart, xOfChoice - 5, yOfHeartAct, 20, 20, null);

            // Choices
            g2.setColor(Color.WHITE);
            g2.drawString(actOptions[0], xOfChoice + 20,
                    yOfChoice);
            g2.drawString(actOptions[1], xOfChoice + 20,
                    yOfChoice + gp.getScreenHeight() / 12);
        }

        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.WHITE);
        g2.drawRect(xOfBattleRect, gp.getScreenHeight() / 5 * 2, widthOfBattleRect,
                getHeightOfBattleRect());

        // Enemy
        g2.drawImage(enemyImages[numberOfSprite],
                (gp.getScreenWidth() - (enemyWidth)) / 2,
                gp.getTileSize(),
                enemyWidth,
                enemyHeight, null);
        // Enemy health bar
        g2.setColor(Color.RED);
        g2.fillRect(gp.getScreenWidth() / 2 - 100,
                gp.getTileSize() + getEnemyHeight(), getEnemyHp(),
                15);
        g2.setColor(Color.GREEN);
        g2.fillRect(gp.getScreenWidth() / 2 - 100,
                gp.getTileSize() + getEnemyHeight(),
                hpOfEnemy,
                15);

        // Action choices
        switch (positionOfHeart) {

            case 0:
                g2.drawImage(actionButtonImages[1], xOfButton, yOfButton, buttonImageWidth,
                        buttonImageHeight,
                        null);
                g2.drawImage(actionButtonImages[2], xOfButton + buttonImageWidth + gapBetweenButtons,
                        yOfButton,
                        buttonImageWidth,
                        buttonImageHeight, null);
                g2.drawImage(actionButtonImages[4], xOfButton + buttonImageWidth * 2 +
                        gapBetweenButtons * 2,
                        yOfButton,
                        buttonImageWidth,
                        buttonImageHeight, null);
                g2.drawImage(actionButtonImages[6], xOfButton + buttonImageWidth * 3 +
                        gapBetweenButtons * 3,
                        yOfButton,
                        buttonImageWidth,
                        buttonImageHeight, null);
                break;
            case 1:
                g2.drawImage(actionButtonImages[0], xOfButton, yOfButton, buttonImageWidth,
                        buttonImageHeight,
                        null);
                g2.drawImage(actionButtonImages[3], xOfButton + buttonImageWidth + gapBetweenButtons,
                        yOfButton,
                        buttonImageWidth,
                        buttonImageHeight, null);
                g2.drawImage(actionButtonImages[4], xOfButton + buttonImageWidth * 2 +
                        gapBetweenButtons * 2,
                        yOfButton,
                        buttonImageWidth,
                        buttonImageHeight, null);
                g2.drawImage(actionButtonImages[6], xOfButton + buttonImageWidth * 3 +
                        gapBetweenButtons * 3,
                        yOfButton,
                        buttonImageWidth,
                        buttonImageHeight, null);
                break;
            case 2:
                g2.drawImage(actionButtonImages[0], xOfButton, yOfButton, buttonImageWidth,
                        buttonImageHeight,
                        null);
                g2.drawImage(actionButtonImages[2], xOfButton + buttonImageWidth + gapBetweenButtons,
                        yOfButton,
                        buttonImageWidth,
                        buttonImageHeight, null);
                g2.drawImage(actionButtonImages[5], xOfButton + buttonImageWidth * 2 +
                        gapBetweenButtons * 2,
                        yOfButton,
                        buttonImageWidth,
                        buttonImageHeight, null);
                g2.drawImage(actionButtonImages[6], xOfButton + buttonImageWidth * 3 +
                        gapBetweenButtons * 3,
                        yOfButton,
                        buttonImageWidth,
                        buttonImageHeight, null);
                break;
            case 3:
                g2.drawImage(actionButtonImages[0], xOfButton, yOfButton, buttonImageWidth,
                        buttonImageHeight,
                        null);
                g2.drawImage(actionButtonImages[2], xOfButton + buttonImageWidth + gapBetweenButtons,
                        yOfButton,
                        buttonImageWidth,
                        buttonImageHeight, null);
                g2.drawImage(actionButtonImages[4], xOfButton + buttonImageWidth * 2 +
                        gapBetweenButtons * 2,
                        yOfButton,
                        buttonImageWidth,
                        buttonImageHeight, null);
                g2.drawImage(actionButtonImages[7], xOfButton + buttonImageWidth * 3 +
                        gapBetweenButtons * 3,
                        yOfButton,
                        buttonImageWidth,
                        buttonImageHeight, null);
                break;
            default:
                break;
        }

        if (!actOptionsOpen) {
            // Heart
            g2.drawImage(heart, heartLocationX, heartLocationY, 26, 26, null);
        }

        // Heart health bar
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g2.drawString("HP: " + getPlayerHealth(), gp.getScreenWidth() / 2 - 7 * 19,
                gp.getScreenHeight() - gp.getScreenHeight() / 5 + 15);
        g2.setColor(Color.RED);
        g2.fillRect(gp.getScreenWidth() / 2 - 50, gp.getScreenHeight() - gp.getScreenHeight() / 5, 100, 15);
        g2.setColor(Color.GREEN);
        g2.fillRect(gp.getScreenWidth() / 2 - 50, gp.getScreenHeight() - gp.getScreenHeight() / 5,
                getPlayerHealth(), 15);

        for (DummyAttack projectile : tempProjectiles) {
            projectile.draw(g2);
        }
    }
}

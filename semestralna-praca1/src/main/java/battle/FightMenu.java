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
    private Projectile projectile;
    private PlayerHeart playerHeart;
    private BufferedImage heart;
    private BufferedImage actionButtons;
    private BufferedImage flowey;
    private BufferedImage[] floweyImages = new BufferedImage[4];
    private BufferedImage[] actionButtonImages = new BufferedImage[8];
    private int subImageWidthActions;
    private int subImageHeightActions;
    private int heartLocationX;
    private int positionOfHeart = 0;
    private int hpOfEnemy;
    private Random random;
    private int numberOfSprite;
    private String battleMessage;
    private ArrayList<Projectile> projectiles;
    private List<Projectile> tempProjectiles;
    private int xOfBattleRect, widthOfBattleRect;

    public FightMenu(GamePanel gp, KeyHandler keyH, PlayerHeart playerHeart) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;
        this.playerHeart = playerHeart;

        random = new Random();
        heartLocationX = gp.getScreenWidth() / 13;
        hpOfEnemy = 100;
        numberOfSprite = 0;
        battleMessage = "Flowey wants to fight!";

        projectiles = new ArrayList<>();
        tempProjectiles = new ArrayList<>();

        getFightMenuImages();
        splitImagesActions();
    }

    public void getFightMenuImages() {
        try {
            actionButtons = ImageIO.read(getClass().getResourceAsStream("/res/fightMenu/action-choices.png"));
            heart = ImageIO.read(getClass().getResourceAsStream("/res/fightMenu/heart.png"));
            flowey = ImageIO.read(getClass().getResourceAsStream("/res/enemy/flowey.png"));
        } catch (IOException i) {
            i.printStackTrace();
        }
        splitImagesEnemy(flowey, 4, floweyImages);
    }

    public void splitImagesActions() {
        subImageWidthActions = actionButtons.getWidth() / 2;
        subImageHeightActions = actionButtons.getHeight() / 4;

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

    public void splitImagesEnemy(BufferedImage image, int imageCount, BufferedImage[] imagesArray) {
        int subImageWidth = image.getWidth() / 2;
        int subImageHeight = image.getHeight() / (imageCount / 2);

        int index = 0;
        for (int row = 0; row < imageCount / 2; row++) {
            for (int col = 0; col < 2; col++) {
                imagesArray[index] = image.getSubimage(
                        col * subImageWidth,
                        row * subImageHeight,
                        subImageWidth,
                        subImageHeight);
                index++;
            }
        }
    }

    public void enemyAttack() {
        gp.changeTurn("+");
        setNumberOfTurn(1);
        setBattleTurn();
        int numberOfProjectiles = random.nextInt(10) + 30;
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
            makeProjectile(2, random.nextInt(10) + 10, side);
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

    public void update() {

        int numberOfTurn = getNumberOfTurn();
        // Action choices movement
        if (hpOfEnemy <= 0 || getPlayerHealth() <= 0) {
            gp.changeFightMenu(false);
        }

        if (numberOfTurn == 0) {
            int spaceBetweenButtons = gp.getScreenWidth() / 16 + subImageWidthActions;
            if (keyH.isRightPressed() && positionOfHeart <= 2 && positionOfHeart >= 0) {
                heartLocationX += spaceBetweenButtons;
                positionOfHeart++;
                keyH.setRightPressed(false);
            } else if (keyH.isLeftPressed() && positionOfHeart >= 1 && positionOfHeart <= 3) {
                heartLocationX -= spaceBetweenButtons;
                positionOfHeart--;
                keyH.setLeftPressed(false);
            }
            if (keyH.isEnterPressed() && positionOfHeart == 0 && hpOfEnemy > 0 && numberOfTurn == 0) {
                // Attack logic
                numberOfSprite = 3;
                hpOfEnemy -= random.nextInt(21) + 10;
                keyH.setEnterPressed(false);
                // Changes sprite to make it look like it hurt the enemy and then changes it
                // back to 0 meaning the normal state after 1 second
                Timer timer = new Timer(1500, e -> {
                    numberOfSprite = 0; // Reset sprite to 0
                    gp.repaint();
                });
                timer.setRepeats(false);
                timer.start();
                enemyAttack();
            } else if (keyH.isEnterPressed() && positionOfHeart == 1 && hpOfEnemy > 0 && numberOfTurn == 0) {
                System.out.println("You chose to act on the enemy");
            } else if (keyH.isEnterPressed() && positionOfHeart == 2 && hpOfEnemy > 0 && numberOfTurn == 0) {
                if (getPlayerHealth() < 100) {
                    setPlayerHealth(getPlayerHealth() + random.nextInt(21) + 10);
                }
                keyH.setEnterPressed(false);
                enemyAttack();
            } else if (keyH.isEnterPressed() && positionOfHeart == 3 && hpOfEnemy > 0 && numberOfTurn == 0) {
                double chanceOfSpare = random.nextDouble() - hpOfEnemy / 1000 * 5;
                if (chanceOfSpare > 0.7) {
                    System.out.println("You spared the enemy with an " + chanceOfSpare + " chance");
                    gp.changeFightMenu(false);
                } else {
                    enemyAttack();
                }
                keyH.setEnterPressed(false);
            }
        }

        if (projectiles.size() > 0) {
            for (Projectile projectile : tempProjectiles) {
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
        }
    }

    public void setBattleTurn() {
        xOfBattleRect = getXOfBattleRect();
        widthOfBattleRect = getWidthOfBattleRect();
        if (getNumberOfTurn() == 1) {
            setWidthOfBattleRect((gp.getScreenWidth() - 80) / 2);
            setXOfBattleRect((gp.getScreenWidth() - widthOfBattleRect) / 2);
            battleMessage = "";
        } else {
            setWorldXHeart((gp.getScreenWidth() - 16) / 2);
            setWorldYHeart((gp.getScreenHeight() + 170 / 2) / 2);
            playerHeart.setPlayerHeartHitbox(new Rectangle(getWorldXHeart(), getWorldYHeart(), 16, 16));
            setWidthOfBattleRect(gp.getScreenWidth() - 80);
            setXOfBattleRect(gp.getTileSize());
            battleMessage = "Flowey wants to fight!";
        }
    }

    public void makeProjectile(int speed, int damage, String whichSide) {
        setBattleTurn();
        projectile = new Projectile(gp, playerHeart);
        projectile.setFightMenu(this, whichSide);
        projectiles.add(projectile);
        projectile.bulletLogic(speed, damage);
    }

    public void drawFightMenu(Graphics2D g2) {
        int xOfButton = gp.getScreenWidth() / 16;
        int yOfButton = gp.getScreenHeight() - gp.getScreenHeight() / 7;

        if (getFightMenu() == true) {
            setBattleTurn();
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(xOfBattleRect, gp.getTileSize() * 5, widthOfBattleRect, 170);

            g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g2.drawString(battleMessage, gp.getScreenWidth() / 10, gp.getScreenHeight() / 2);

            // Enemy
            g2.drawImage(floweyImages[numberOfSprite],
                    (gp.getScreenWidth() - (floweyImages[0].getWidth() * 2 + 20)) / 2,
                    gp.getTileSize(),
                    floweyImages[0].getWidth() * 2 + 20,
                    floweyImages[0].getHeight() * 2 + 20, null);
            // Enemy health bar
            g2.setColor(Color.RED);
            g2.fillRect((gp.getScreenWidth() - (floweyImages[0].getWidth() * 2 + 20)) / 2, gp.getTileSize() * 4, 100,
                    15);
            g2.setColor(Color.GREEN);
            g2.fillRect((gp.getScreenWidth() - (floweyImages[0].getWidth() * 2 + 20)) / 2, gp.getTileSize() * 4,
                    hpOfEnemy,
                    15);

            switch (positionOfHeart) {
                case 0:
                    g2.drawImage(actionButtonImages[1], xOfButton, yOfButton, subImageWidthActions,
                            subImageHeightActions,
                            null);
                    g2.drawImage(actionButtonImages[2], xOfButton * 2 + subImageWidthActions, yOfButton,
                            subImageWidthActions,
                            subImageHeightActions, null);
                    g2.drawImage(actionButtonImages[4], xOfButton * 3 + subImageWidthActions * 2, yOfButton,
                            subImageWidthActions,
                            subImageHeightActions, null);
                    g2.drawImage(actionButtonImages[6], xOfButton * 4 + subImageWidthActions * 3, yOfButton,
                            subImageWidthActions,
                            subImageHeightActions, null);
                    break;
                case 1:
                    g2.drawImage(actionButtonImages[0], xOfButton, yOfButton, subImageWidthActions,
                            subImageHeightActions,
                            null);
                    g2.drawImage(actionButtonImages[3], xOfButton * 2 + subImageWidthActions, yOfButton,
                            subImageWidthActions,
                            subImageHeightActions, null);
                    g2.drawImage(actionButtonImages[4], xOfButton * 3 + subImageWidthActions * 2, yOfButton,
                            subImageWidthActions,
                            subImageHeightActions, null);
                    g2.drawImage(actionButtonImages[6], xOfButton * 4 + subImageWidthActions * 3, yOfButton,
                            subImageWidthActions,
                            subImageHeightActions, null);
                    break;
                case 2:
                    g2.drawImage(actionButtonImages[0], xOfButton, yOfButton, subImageWidthActions,
                            subImageHeightActions,
                            null);
                    g2.drawImage(actionButtonImages[2], xOfButton * 2 + subImageWidthActions, yOfButton,
                            subImageWidthActions,
                            subImageHeightActions, null);
                    g2.drawImage(actionButtonImages[5], xOfButton * 3 + subImageWidthActions * 2, yOfButton,
                            subImageWidthActions,
                            subImageHeightActions, null);
                    g2.drawImage(actionButtonImages[6], xOfButton * 4 + subImageWidthActions * 3, yOfButton,
                            subImageWidthActions,
                            subImageHeightActions, null);
                    break;
                case 3:
                    g2.drawImage(actionButtonImages[0], xOfButton, yOfButton, subImageWidthActions,
                            subImageHeightActions,
                            null);
                    g2.drawImage(actionButtonImages[2], xOfButton * 2 + subImageWidthActions, yOfButton,
                            subImageWidthActions,
                            subImageHeightActions, null);
                    g2.drawImage(actionButtonImages[4], xOfButton * 3 + subImageWidthActions * 2, yOfButton,
                            subImageWidthActions,
                            subImageHeightActions, null);
                    g2.drawImage(actionButtonImages[7], xOfButton * 4 + subImageWidthActions * 3, yOfButton,
                            subImageWidthActions,
                            subImageHeightActions, null);
                    break;
                default:
                    break;
            }
            // Heart
            g2.drawImage(heart, heartLocationX, yOfButton + 13, 16, 16, null);

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

            for (Projectile projectile : tempProjectiles) {
                projectile.draw(g2);
            }
        }
    }
}

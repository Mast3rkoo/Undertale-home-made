package battle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.swing.Timer;

import javax.imageio.ImageIO;

import semestralka.GamePanel;
import semestralka.KeyHandler;

public class FightMenu extends Battle {
    GamePanel gp;
    KeyHandler keyH;
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

    public FightMenu(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        random = new Random();
        heartLocationX = gp.screenWidth / 13;
        hpOfEnemy = 100;
        numberOfSprite = 0;
        xOfBattleRect = gp.tileSize;
        battleMessage = "Flowey wants to fight!";
        widthOfBattleRect = gp.screenWidth - 80;

        battleRectHitbox = new Rectangle(xOfBattleRect, gp.tileSize * 5, widthOfBattleRect, 170);

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

    public Rectangle setBattleRectHitbox() {
        return new Rectangle(xOfBattleRect, gp.tileSize * 5, widthOfBattleRect, 170);
    }

    public void update() {
        // Action choices movement
        if (numberOfTurn == 0) {
            int spaceBetweenButtons = gp.screenWidth / 16 + subImageWidthActions;
            if (keyH.rightPressed && positionOfHeart <= 2 && positionOfHeart >= 0) {
                heartLocationX += spaceBetweenButtons;
                positionOfHeart++;
                keyH.rightPressed = false;
            } else if (keyH.leftPressed && positionOfHeart >= 1 && positionOfHeart <= 3) {
                heartLocationX -= spaceBetweenButtons;
                positionOfHeart--;
                keyH.leftPressed = false;

            } else if (keyH.enterPressed && positionOfHeart == 0 && hpOfEnemy > 0 && numberOfTurn == 0) {
                // Attack logic
                numberOfSprite = 3;
                hpOfEnemy -= random.nextInt(21) + 10;
                keyH.enterPressed = false;
                // Changes sprite to make it look like it hurt the enemy and then changes it
                // back to 0 meaning the normal state after 1 second
                Timer timer = new Timer(1500, e -> {
                    numberOfSprite = 0; // Reset sprite to 0
                    gp.repaint();
                });
                timer.setRepeats(false);
                timer.start();
                gp.changeTurn("+");
                numberOfTurn++;
            } else if (keyH.enterPressed && positionOfHeart == 1 && hpOfEnemy > 0 && numberOfTurn == 1) {
                gp.changeTurn("-");
                numberOfTurn--;
            }
            if (hpOfEnemy <= 0) {
                gp.changeFightMenu();
            }
        }
    }

    public void setBattleTurn() {
        if (numberOfTurn == 1) {
            widthOfBattleRect = (gp.screenWidth - 80) / 2;
            xOfBattleRect = (gp.screenWidth - widthOfBattleRect) / 2;
            battleMessage = "";
        } else {
            widthOfBattleRect = gp.screenWidth - 80;
            xOfBattleRect = gp.tileSize;
            battleMessage = "Flowey wants to fight!";
        }
        battleRectHitbox.setBounds(xOfBattleRect, gp.tileSize * 5, widthOfBattleRect, 170);
    }

    public void drawFightMenu(Graphics2D g2) {
        int xOfButton = gp.screenWidth / 16;
        int yOfButton = gp.tileSize * 10;
        if (fightMenu) {
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3));
            setBattleTurn();
            g2.drawRect(xOfBattleRect, gp.tileSize * 5, widthOfBattleRect, 170);
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g2.drawString(battleMessage, gp.screenWidth / 10, gp.screenHeight / 2);

            g2.drawImage(floweyImages[numberOfSprite], (gp.screenWidth - (floweyImages[0].getWidth() * 2 + 20)) / 2,
                    gp.tileSize,
                    floweyImages[0].getWidth() * 2 + 20,
                    floweyImages[0].getHeight() * 2 + 20, null);
            g2.setColor(Color.RED);
            g2.fillRect((gp.screenWidth - (floweyImages[0].getWidth() * 2 + 20)) / 2, gp.tileSize * 4, 100,
                    15);
            g2.setColor(Color.GREEN);
            g2.fillRect((gp.screenWidth - (floweyImages[0].getWidth() * 2 + 20)) / 2, gp.tileSize * 4, hpOfEnemy,
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
            g2.drawImage(heart, heartLocationX, gp.screenHeight - gp.screenHeight / 7, 16, 16, null);
        }
    }
}

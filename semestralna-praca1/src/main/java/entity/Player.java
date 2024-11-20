package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import semestralka.GamePanel;
import semestralka.KeyHandler;
import tile.TileManager;
import battle.Battle;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    private TileManager tileManager;
    private Battle battle;

    public final int screenX;
    public final int screenY;
    private int level;

    public Player(GamePanel gp, KeyHandler keyH, Battle battle) {
        this.gp = gp;
        this.keyH = keyH;
        this.battle = battle;

        this.level = 2;

        screenX = gp.screenWidth / 2;
        screenY = gp.screenHeight / 2;

        hitBox = new Rectangle(8, 16, 32, 32);

        setDefaultValues(gp.tileSize * 3, gp.tileSize * 3);
        getPlayerImage();
    }

    public void setDefaultValues(int x, int y) {
        worldX = x; // Set spawn X from TileManager or default
        worldY = y; // Set spawn Y from TileManager or default
        speed = 3;
        direction = "down";
    }

    public void setTileManager(TileManager tileManager) {
        this.tileManager = tileManager;
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/Tesa_back_idle.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/Tesa-back-right-step.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/Tesa-front-idle.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/Tesa-front-right-step.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/Tesa-right-idle.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/Tesa-right-right-step.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/Tesa-left-idle.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/Tesa-left-right-step.png"));
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void update() {
        if (keyH.upPressed == true) {
            direction = "up";

        } else if (keyH.downPressed == true) {
            direction = "down";

        } else if (keyH.leftPressed == true) {
            direction = "left";

        } else if (keyH.rightPressed == true) {
            direction = "right";
        } else {
            imageNumber = 1;
            return;
        }

        collisionDetected = false;
        gp.hitBoxCheck.checkTileCollision(this);

        if (!collisionDetected) {
            if (keyH.upPressed && !keyH.rightPressed && !keyH.leftPressed
                    && !keyH.downPressed) {
                worldY -= speed;
            } else if (keyH.downPressed && !keyH.rightPressed && !keyH.leftPressed
                    && !keyH.upPressed) {
                worldY += speed;
            } else if (keyH.leftPressed && !keyH.upPressed && !keyH.downPressed
                    && !keyH.rightPressed) {
                worldX -= speed;
            } else if (keyH.rightPressed && !keyH.upPressed && !keyH.downPressed
                    && !keyH.leftPressed) {
                worldX += speed;
            } else if (keyH.upPressed && keyH.rightPressed) {
                worldY -= speed;
                worldX += speed;
            } else if (keyH.upPressed && keyH.leftPressed) {
                worldY -= speed;
                worldX -= speed;
            } else if (keyH.downPressed && keyH.rightPressed) {
                worldY += speed;
                worldX += speed;
            } else if (keyH.downPressed && keyH.leftPressed) {
                worldY += speed;
                worldX -= speed;
            }
        }

        if (canWalkThroughDoor) {
            this.tileManager.loadMap(String.format("/res/maps/map00%s.txt", level));
            canWalkThroughDoor = false;
        }

        spriteCounter++;
        if (spriteCounter > 20) {
            if (imageNumber == 1) {
                imageNumber = 2;
            } else if (imageNumber == 2) {
                imageNumber = 1;
            }
            spriteCounter = 0;
        }

    }

    public void draw(Graphics2D g2) {
        if (!battle.fightMenu) {
            BufferedImage image = null;

            switch (direction) {
                case "up":
                    if (imageNumber == 1) {
                        image = up1;
                    }
                    if (imageNumber == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if (imageNumber == 1) {
                        image = down1;
                    }
                    if (imageNumber == 2) {
                        image = down2;
                    }
                    break;
                case "right":
                    if (imageNumber == 1) {
                        image = right1;
                    }
                    if (imageNumber == 2) {
                        image = right2;
                    }
                    break;
                case "left":
                    if (imageNumber == 1) {
                        image = left1;
                    }
                    if (imageNumber == 2) {
                        image = left2;
                    }
                    break;
            }
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }

    }
}

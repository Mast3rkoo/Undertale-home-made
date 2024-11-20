package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {

    // Player attributes
    public int worldX, worldY;
    public int spawnX, spawnY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, heart;
    public String direction;

    public int spriteCounter = 0;
    public int imageNumber = 1;

    public Rectangle hitBox;
    public boolean collisionDetected = false;
    public boolean canWalkThroughDoor = false;
}

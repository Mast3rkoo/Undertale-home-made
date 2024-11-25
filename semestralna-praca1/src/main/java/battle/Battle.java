package battle;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Battle {
    public BufferedImage heart, bullet;
    public boolean fightMenu = false;

    // Players heart attributes
    public int worldXHeart, worldYHeart;
    public int heartSpeed;
    public static int playerHealth;

    public int heartTopY, heartBottomY, heartLeftX, heartRightX;
    public boolean collisionDetected;
    public int numberOfTurn = 0;

    public int widthOfBattleRect, heightOfBattleRect;
    public int xOfBattleRect, yOfBattleRect;
    public Rectangle battleRectHitbox;

    public ArrayList<Projectile> projectiles;
}

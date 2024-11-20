package battle;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Battle {

    public BufferedImage heart;
    public boolean fightMenu = true;

    // Players heart attributes
    public int worldXHeart, worldYHeart;
    public int heartSpeed;
    public String directionHeart;
    public boolean collisionDetected;
    public int numberOfTurn = 0;

    public int widthOfBattleRect;
    public int xOfBattleRect;
    public Rectangle battleRectHitbox;
}

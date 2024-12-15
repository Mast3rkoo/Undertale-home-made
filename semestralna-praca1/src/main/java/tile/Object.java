package tile;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import semestralka.GamePanel;

public class Object {
    private BufferedImage object;
    private int worldX;
    private int worldY;
    private String name;
    private boolean enemyAlreadyCalled;

    public Object(String name, int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.name = name;
        try {
            this.object = ImageIO.read(getClass().getResourceAsStream("/res/objects/" + this.name + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkHitBox(GamePanel gp) {
        Rectangle objectHitBox = new Rectangle(worldX, worldY, 60, 90);

        Rectangle hitBox = gp.getPlayer().getHitBox();
        int entityLeftEdge = gp.getPlayer().getWorldX() + hitBox.x;
        int entityTopEdge = gp.getPlayer().getWorldY() + hitBox.y;

        hitBox = new Rectangle(entityLeftEdge, entityTopEdge, hitBox.width, hitBox.height);

        if (objectHitBox.intersects(hitBox)) {
            if (!enemyAlreadyCalled) {
                gp.getFightMenu().setEnemy(this.name);
                enemyAlreadyCalled = true;
            }
        }
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
        int screenY = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();

        // g2.setColor(Color.RED);
        // g2.drawRect(screenX, screenY, 60, 90);
        checkHitBox(gp);
        if (worldX + gp.getTileSize() > gp.getPlayer().getWorldX() - gp.getPlayer().getScreenX() &&
                worldX - gp.getTileSize() < gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX() &&
                worldY + gp.getTileSize() > gp.getPlayer().getWorldY() - gp.getPlayer().getScreenY() &&
                worldY - gp.getTileSize() < gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY()) {
            g2.drawImage(object, screenX, screenY, 60, 90, null);
        }
    }
}

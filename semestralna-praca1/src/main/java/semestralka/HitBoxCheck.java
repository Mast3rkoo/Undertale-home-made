package semestralka;

import java.awt.Rectangle;

import entity.Player;

public class HitBoxCheck {
    private GamePanel gp;
    private Player player;

    public HitBoxCheck(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
    }

    public void checkTileCollision() {
        Rectangle hitBox = player.getHitBox();
        int entityLeftEdge = player.getWorldX() + hitBox.x;
        int entityRightEdge = player.getWorldX() + hitBox.x + hitBox.width;
        int entityTopEdge = player.getWorldY() + hitBox.y;
        int entityBottomEdge = player.getWorldY() + hitBox.y + hitBox.height;

        int leftTileColumn = entityLeftEdge / gp.getTileSize();
        int rightTileColumn = entityRightEdge / gp.getTileSize();
        int topTileRow = entityTopEdge / gp.getTileSize();
        int bottomTileRow = entityBottomEdge / gp.getTileSize();

        int tileId1, tileId2;

        switch (player.getDirection()) {
            case "up":
                topTileRow = (entityTopEdge - player.getSpeed()) / gp.getTileSize();
                tileId1 = gp.getTileManager().getMapTileNumber(leftTileColumn, topTileRow);
                tileId2 = gp.getTileManager().getMapTileNumber(rightTileColumn, topTileRow);
                if (gp.getTileManager().getTile(tileId1).isCollision()
                        || gp.getTileManager().getTile(tileId2).isCollision()) {
                    if (tileId1 == 17 || tileId1 == 18 || tileId1 == 19 || tileId2 == 17 || tileId2 == 18
                            || tileId2 == 19) {
                        player.setWalkThroughDoor(true);
                    } else {
                        player.setCollisionDetected(true);
                    }
                } else if (gp.getTileManager().getTile(tileId1).isEncounter()
                        || gp.getTileManager().getTile(tileId2).isEncounter()) {

                    gp.getFightMenu().setEnemy("flowey");
                    player.setEnemyEncounterAlert(true);
                }
                break;
            case "down":
                bottomTileRow = (entityBottomEdge + player.getSpeed()) / gp.getTileSize();
                tileId1 = gp.getTileManager().getMapTileNumber(leftTileColumn, bottomTileRow);
                tileId2 = gp.getTileManager().getMapTileNumber(rightTileColumn, bottomTileRow);
                if (gp.getTileManager().getTile(tileId1).isCollision()
                        || gp.getTileManager().getTile(tileId2).isCollision()) {
                    player.setCollisionDetected(true);
                } else if (gp.getTileManager().getTile(tileId1).isEncounter()
                        || gp.getTileManager().getTile(tileId2).isEncounter()) {

                    gp.getFightMenu().setEnemy("dummy");
                    player.setEnemyEncounterAlert(true);
                }
                break;
            case "left":
                leftTileColumn = (entityLeftEdge - player.getSpeed()) / gp.getTileSize();
                tileId1 = gp.getTileManager().getMapTileNumber(leftTileColumn, topTileRow);
                tileId2 = gp.getTileManager().getMapTileNumber(leftTileColumn, bottomTileRow);
                if (gp.getTileManager().getTile(tileId1).isCollision()
                        || gp.getTileManager().getTile(tileId2).isCollision()) {
                    player.setCollisionDetected(true);
                } else if (gp.getTileManager().getTile(tileId1).isEncounter()
                        || gp.getTileManager().getTile(tileId2).isEncounter()) {

                    gp.getFightMenu().setEnemy("flowey");
                    player.setEnemyEncounterAlert(true);
                }
                break;
            case "right":
                rightTileColumn = (entityRightEdge + player.getSpeed()) / gp.getTileSize();
                tileId1 = gp.getTileManager().getMapTileNumber(rightTileColumn, topTileRow);
                tileId2 = gp.getTileManager().getMapTileNumber(rightTileColumn, bottomTileRow);
                if (gp.getTileManager().getTile(tileId1).isCollision()
                        || gp.getTileManager().getTile(tileId2).isCollision()) {
                    player.setCollisionDetected(true);
                } else if (gp.getTileManager().getTile(tileId1).isEncounter()
                        || gp.getTileManager().getTile(tileId2).isEncounter()) {

                    gp.getFightMenu().setEnemy("flowey");
                    player.setEnemyEncounterAlert(true);
                }
                break;
            default:
                break;
        }
    }

}

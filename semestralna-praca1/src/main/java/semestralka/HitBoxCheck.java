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

    public void pickEnemyEncounter(int tileId1, int tileId2) {
        if (tileId1 == 2 || tileId2 == 2) {
            gp.getFightMenu().setEnemy("flowey");
        } else if (tileId1 == 33 || tileId2 == 33 || tileId1 == 34 || tileId2 == 34 || tileId1 == 35 || tileId2 == 35
                || tileId1 == 36 || tileId2 == 36) {
            gp.getFightMenu().setEnemy("dummy");
        }
        player.setEnemyEncounterAlert(true);
    }

    public void checkTileCollision(int room) {
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
                tileId1 = gp.getTileManager().getMapTileNumber(room, leftTileColumn, topTileRow);
                tileId2 = gp.getTileManager().getMapTileNumber(room, rightTileColumn, topTileRow);
                if (gp.getTileManager().getTile(room, tileId1).isCollision()
                        || gp.getTileManager().getTile(room, tileId2).isCollision()) {
                    switch (room) {
                        case 1:
                            if (tileId1 == 12 || tileId1 == 13 || tileId1 == 14 || tileId2 == 12 ||
                                    tileId2 == 13
                                    || tileId2 == 14) {
                                player.setWalkThroughDoor(true);
                            } else {
                                player.setCollisionDetected(true);
                            }
                            break;
                        case 2:
                            if (tileId1 == 0 || tileId2 == 0) {
                                player.setWalkThroughDoor(true);
                            } else {
                                player.setCollisionDetected(true);
                            }
                        default:
                            player.setCollisionDetected(true);
                            break;
                    }
                } else if (gp.getTileManager().getTile(room, tileId1).isEncounter()
                        || gp.getTileManager().getTile(room, tileId2).isEncounter()) {
                    pickEnemyEncounter(tileId1, tileId2);
                }
                break;
            case "down":
                bottomTileRow = (entityBottomEdge + player.getSpeed()) / gp.getTileSize();
                tileId1 = gp.getTileManager().getMapTileNumber(room, leftTileColumn,
                        bottomTileRow);
                tileId2 = gp.getTileManager().getMapTileNumber(room, rightTileColumn,
                        bottomTileRow);
                if (gp.getTileManager().getTile(room, tileId1).isCollision()
                        || gp.getTileManager().getTile(room, tileId2).isCollision()) {
                    player.setCollisionDetected(true);
                } else if (gp.getTileManager().getTile(room, tileId1).isEncounter()
                        || gp.getTileManager().getTile(room, tileId2).isEncounter()) {
                    pickEnemyEncounter(tileId1, tileId2);
                }
                break;
            case "left":
                leftTileColumn = (entityLeftEdge - player.getSpeed()) / gp.getTileSize();
                tileId1 = gp.getTileManager().getMapTileNumber(room, leftTileColumn, topTileRow);
                tileId2 = gp.getTileManager().getMapTileNumber(room, leftTileColumn,
                        bottomTileRow);
                if (gp.getTileManager().getTile(room, tileId1).isCollision()
                        || gp.getTileManager().getTile(room, tileId2).isCollision()) {
                    player.setCollisionDetected(true);
                } else if (gp.getTileManager().getTile(room, tileId1).isEncounter()
                        || gp.getTileManager().getTile(room, tileId2).isEncounter()) {
                    pickEnemyEncounter(tileId1, tileId2);
                }
                break;
            case "right":
                rightTileColumn = (entityRightEdge + player.getSpeed()) / gp.getTileSize();
                tileId1 = gp.getTileManager().getMapTileNumber(room, rightTileColumn, topTileRow);
                tileId2 = gp.getTileManager().getMapTileNumber(room, rightTileColumn,
                        bottomTileRow);
                if (gp.getTileManager().getTile(room, tileId1).isCollision()
                        || gp.getTileManager().getTile(room, tileId2).isCollision()) {
                    player.setCollisionDetected(true);
                } else if (gp.getTileManager().getTile(room, tileId1).isEncounter()
                        || gp.getTileManager().getTile(room, tileId2).isEncounter()) {
                    pickEnemyEncounter(tileId1, tileId2);
                }
                break;
            default:
                break;
        }
    }

}

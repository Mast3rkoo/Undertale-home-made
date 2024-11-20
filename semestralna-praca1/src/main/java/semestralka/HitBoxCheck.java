package semestralka;

import entity.Entity;

public class HitBoxCheck {
    GamePanel gp;

    public HitBoxCheck(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTileCollision(Entity entity) {
        int entityLeftEdge = entity.worldX + entity.hitBox.x;
        int entityRightEdge = entity.worldX + entity.hitBox.x + entity.hitBox.width;
        int entityTopEdge = entity.worldY + entity.hitBox.y;
        int entityBottomEdge = entity.worldY + entity.hitBox.y + entity.hitBox.height;

        int leftTileColumn = entityLeftEdge / gp.tileSize;
        int rightTileColumn = entityRightEdge / gp.tileSize;
        int topTileRow = entityTopEdge / gp.tileSize;
        int bottomTileRow = entityBottomEdge / gp.tileSize;

        int tileId1, tileId2;

        switch (entity.direction) {
            case "up":
                topTileRow = (entityTopEdge - entity.speed) / gp.tileSize;
                tileId1 = gp.tileManager.mapTileNumber[leftTileColumn][topTileRow];
                tileId2 = gp.tileManager.mapTileNumber[rightTileColumn][topTileRow];
                if (gp.tileManager.tile[tileId1].collision || gp.tileManager.tile[tileId2].collision) {
                    if (tileId1 == 17 || tileId1 == 18 || tileId1 == 19 || tileId2 == 17 || tileId2 == 18
                            || tileId2 == 19) {
                        entity.canWalkThroughDoor = true;
                    } else {
                        entity.collisionDetected = true;
                    }
                }
                break;
            case "down":
                bottomTileRow = (entityBottomEdge + entity.speed) / gp.tileSize;
                tileId1 = gp.tileManager.mapTileNumber[leftTileColumn][bottomTileRow];
                tileId2 = gp.tileManager.mapTileNumber[rightTileColumn][bottomTileRow];
                if (gp.tileManager.tile[tileId1].collision || gp.tileManager.tile[tileId2].collision) {
                    entity.collisionDetected = true;
                }
                break;
            case "left":
                leftTileColumn = (entityLeftEdge - entity.speed) / gp.tileSize;
                tileId1 = gp.tileManager.mapTileNumber[leftTileColumn][topTileRow];
                tileId2 = gp.tileManager.mapTileNumber[leftTileColumn][bottomTileRow];
                if (gp.tileManager.tile[tileId1].collision || gp.tileManager.tile[tileId2].collision) {
                    entity.collisionDetected = true;
                }
                break;
            case "right":
                rightTileColumn = (entityRightEdge + entity.speed) / gp.tileSize;
                tileId1 = gp.tileManager.mapTileNumber[rightTileColumn][topTileRow];
                tileId2 = gp.tileManager.mapTileNumber[rightTileColumn][bottomTileRow];
                if (gp.tileManager.tile[tileId1].collision || gp.tileManager.tile[tileId2].collision) {
                    entity.collisionDetected = true;
                }
                break;
            default:
                break;
        }
    }

}

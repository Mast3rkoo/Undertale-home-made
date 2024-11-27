package entity;

import java.awt.Rectangle;

public class Entity {
    // Player attributes
    private int worldX, worldY;
    private int speed;

    private String direction;

    private Rectangle hitBox;
    private boolean collisionDetected = false;
    private boolean canWalkThroughDoor = false;

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    public boolean isCollisionDetected() {
        return collisionDetected;
    }

    public void setCollisionDetected(boolean collisionDetected) {
        this.collisionDetected = collisionDetected;
    }

    public boolean isWalkThroughDoor() {
        return canWalkThroughDoor;
    }

    public void setWalkThroughDoor(boolean canWalkThroughDoor) {
        this.canWalkThroughDoor = canWalkThroughDoor;
    }
}

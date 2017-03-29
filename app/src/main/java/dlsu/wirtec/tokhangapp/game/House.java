package dlsu.wirtec.tokhangapp.game;

import android.graphics.Bitmap;

/**
 * Created by Denmark on 3/11/2017.
 */

public class House extends  Sprite{
    public static final String BUNGALOW = "bungalow";
    public static final String SKWATER = "skwater";

    private String name; // name of House
    private float x; // leftmost part of the house
    private float y; // uppermost part of the house
    private float destinationX; // the destination of the house
    private int[] xSpawns; // x of where characters can spawn
    private int[] ySpawns; // y of where characters can spawn
    private int houseSpawnTime;
    private float originalX;
    private float originalY;
    private boolean moving; // returns true is house is currently moving
    private int remainingCharacterSpawns;

    public House() {}

    public House(String name){
        this.name = name;
    }

    public House(Bitmap bitmap, float x, float y, float destinationX, int houseSpawnTime, int remainingCharacterSpawns) {
        super(bitmap);
        this.x = x;
        this.y = y;
        originalX = x;
        originalY = y;
        this.destinationX = destinationX;
        this.houseSpawnTime = houseSpawnTime;
        this.remainingCharacterSpawns = remainingCharacterSpawns;
        if(x != destinationX)
            moving = true;
    }

    public House(Bitmap bitmap, float x, float y, float destinationX, int houseSpawnTime, int remainingCharacterSpawns, int[] xSpawns, int[] ySpawns) {
        super(bitmap);
        this.x = x;
        this.y = y;
        originalX = x;
        originalY = y;
        this.destinationX = destinationX;
        this.houseSpawnTime = houseSpawnTime;
        this.remainingCharacterSpawns = remainingCharacterSpawns;
        setXandYSpawns(xSpawns, ySpawns);
        if(x != destinationX)
            moving = true;
    }

    public boolean isMoving() {
        if(x != destinationX)
            return true;
        return false;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void moveHouseToDestination(int currentMillisecond, int charTranslationDuration) {
        if(destinationX <= x) {
            float changeInX = destinationX - originalX;
            float changeInY = originalY - originalY;
            float progress = (currentMillisecond - houseSpawnTime) / (float)charTranslationDuration;
            setX(originalX + changeInX * progress);
            setY(originalY + changeInY * progress);
            if (progress >= 1 && moving) {
                moving = false;
                originalX = x;
                originalY = y;
                destinationX = x;
            } else if (progress < 1){
                moving = true;
            }
        }
    }

    public void setXandYSpawns (int[] xSpawns, int[] ySpawns) {
        this.xSpawns = xSpawns;
        this.ySpawns = ySpawns;
    }

    public void deductCharacterSpawn() {
        remainingCharacterSpawns --;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getRemainingCharacterSpawns() {
        return remainingCharacterSpawns;
    }

    public void setRemainingCharacterSpawns(int remainingCharacterSpawns) {
        this.remainingCharacterSpawns = remainingCharacterSpawns;
    }

    public int getHouseSpawnTime() {
        return houseSpawnTime;
    }

    public void setHouseSpawnTime(int houseSpawnTime) {
        this.houseSpawnTime = houseSpawnTime;
    }

    public float getOriginalY() {
        return originalY;
    }

    public void setOriginalY(float originalY) {
        this.originalY = originalY;
    }

    public float getOriginalX() {
        return originalX;
    }

    public void setOriginalX(float originalX) {
        this.originalX = originalX;
    }

    public int[] getxSpawns() {
        return xSpawns;
    }

    public void setxSpawns(int[] xSpawns) {
        this.xSpawns = xSpawns;
    }

    public int[] getySpawns() {
        return ySpawns;
    }

    public void setySpawns(int[] ySpawns) {
        this.ySpawns = ySpawns;
    }

    public float getDestinationX() {
        return destinationX;
    }

    public void setDestinationX(float destinationX) {
        this.destinationX = destinationX;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}

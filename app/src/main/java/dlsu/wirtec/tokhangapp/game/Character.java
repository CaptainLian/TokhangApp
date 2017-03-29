package dlsu.wirtec.tokhangapp.game;

import android.graphics.Bitmap;

/**
 * Created by Denmark on 3/6/2017.
 */

public class Character extends SpriteAnimation {
    private String name; //name of the character
    private float x; //leftmost touched part of the image
    private float y; // uppermost touched part of the image
    private float originalX;
    private float originalY;
    private int timeOfDue;
    private boolean touched; // if the cbaracter is touched
    private int duration;
    private int spawnTime;
    private int score; // the score of the character
    private int health; // health of the character
    private boolean due;
    private boolean shakenToRight;

    public Character () {}

    public Character(String name, SpriteAnimation animation, float x, float y, int spawnTime, int duration, int score, int sourceSpriteIndex, int destinationSpriteIndex, int health) {
        super(animation.getSprites(), 0);
        this.name = name;
        this.x = x;
        this.y = y;
        originalX = x;
        originalY = y;
        this.score = score;
        this.spawnTime = spawnTime;
        this.duration = duration;
        setSpriteAnimation(sourceSpriteIndex, destinationSpriteIndex);
        this.health = health;
    }

    public void animate() {
        incrementSpriteIndex();
    }

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(float touchX, float touchY) {
        if (touchX <= getCurrentSprite().getFrameWidth() + this.x && touchX >= this.x
                    && touchY <= getCurrentSprite().getFrameHeight() + this.y && touchY >= this.y)
                touched = true;
    }

    public Bitmap returnBitmapToDraw() {
        return getCurrentSprite().getBitmap();
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public boolean isDue() {
        return due;
    }

    public void setDue(boolean due) {
        this.due = due;
    }

    public boolean isShakenToRight() {
        return shakenToRight;
    }

    public void setShakenToRight(boolean shakenToRight) {
        this.shakenToRight = shakenToRight;
    }

    public float getOriginalY() {
        return originalY;
    }

    public void setOriginalY(float originalY) {
        this.originalY = originalY;
    }

    public int getTimeOfDue() {
        return timeOfDue;
    }

    public void setTimeOfDue(int timeOfDue) {
        this.timeOfDue = timeOfDue;
    }

    public float getOriginalX() {
        return originalX;
    }

    public void setOriginalX(float originalX) {
        this.originalX = originalX;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getSpawnTime() {
        return spawnTime;
    }

    public void setSpawnTime(int spawnTime) {
        this.spawnTime = spawnTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
package dlsu.wirtec.tokhangapp.game;

/**
 * Created by Denmark on 4/7/2017.
 */

public class DamageAnimation {
    private int damageValue;
    private float damageX;
    private float damageY;
    private float originalX;
    private float originalY;
    private int damageSpawnTime;
    private float damageDestinationY;

    public DamageAnimation(int damageValue, float damageX, float damageY, float damageDestinationY, int damageSpawnTime) {
        this.damageValue = damageValue;
        this.damageX = damageX;
        this.damageY = damageY;
        this.damageSpawnTime = damageSpawnTime;
        originalX = damageX;
        originalY = damageY;
        this.damageDestinationY = damageDestinationY;
    }

    public float moveDamage(int currentMillisecond, int translationDuration) {
        // returns the progress
        float changeInX = originalX - originalX;
        float changeInY = damageDestinationY - originalY;
        float progress = (currentMillisecond - damageSpawnTime) / (float) translationDuration;
        setDamageX(originalX + changeInX * progress);
        setDamageY(originalY + changeInY * progress);
        return progress;
    }

    public int getDamageValue() {
        return damageValue;
    }

    public void setDamageValue(int damageValue) {
        this.damageValue = damageValue;
    }

    public float getDamageX() {
        return damageX;
    }

    public void setDamageX(float damageX) {
        this.damageX = damageX;
    }

    public float getDamageY() {
        return damageY;
    }

    public void setDamageY(float damageY) {
        this.damageY = damageY;
    }

    public float getOriginalX() {
        return originalX;
    }

    public void setOriginalX(float originalX) {
        this.originalX = originalX;
    }

    public float getOriginalY() {
        return originalY;
    }

    public void setOriginalY(float originalY) {
        this.originalY = originalY;
    }

    public int getDamageSpawnTime() {
        return damageSpawnTime;
    }

    public void setDamageSpawnTime(int damageSpawnTime) {
        this.damageSpawnTime = damageSpawnTime;
    }

    public float getDamageDestinationY() {
        return damageDestinationY;
    }

    public void setDamageDestinationY(float damageDestinationY) {
        this.damageDestinationY = damageDestinationY;
    }
}
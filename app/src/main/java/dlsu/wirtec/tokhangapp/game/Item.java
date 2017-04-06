package dlsu.wirtec.tokhangapp.game;

/**
 * Created by Denmark on 3/6/2017.
 */

public class Item {
    private String name;
    private Sprite sprite;
    private int effect; // 0-No effect, 1-Increase health
    private float x;
    private float y;

    public Item () {

    }

    public Item(String name, Sprite sprite, int effect) {
        this.name = name;
        this.sprite = sprite;
        this.effect = effect;
    }

    public Item(String name, Sprite sprite, int effect, float x, float y) {
        this.name = name;
        this.sprite = sprite;
        this.effect = effect;
        this.x = x;
        this.y = y;
    }

    public boolean checkIfItemTouched(float touchX, float touchY) {
        if (touchX <= sprite.getFrameWidth() + this.x && touchX >= this.x
                && touchY <= sprite.getFrameHeight() + this.y && touchY >= this.y)
            return true;
        return false;
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

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }
}

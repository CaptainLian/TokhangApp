package dlsu.wirtec.tokhangapp.game;

/**
 * Created by Denmark on 3/6/2017.
 */

public class Item {
    private String name;
    private Sprite sprite;
    private int effect; // 1 - time stop // 2 - health pack // 3 - x-ray vision // 4 - machine gun

    public Item () {

    }

    public Item(String name, Sprite sprite, int effect) {
        this.name = name;
        this.sprite = sprite;
        this.effect = effect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

package dlsu.wirtec.tokhangapp.game;

import android.graphics.Bitmap;

/**
 * Created by Denmark on 3/14/2017.
 */

public class MainCharacter extends SpriteAnimation {
    private float x;
    private float y;
    private int addiction = 0;
    private SpriteAnimation addictionSprites;

    public MainCharacter() {}

    public MainCharacter(SpriteAnimation animation, float x, float y, int sourceSpriteIndex, int destinationSpriteIndex, SpriteAnimation addictionSprites) {
        super(animation.getSprites(), 0);
        this.x = x;
        this.y = y;
        setSpriteAnimation(sourceSpriteIndex, destinationSpriteIndex);
        this.addictionSprites = addictionSprites;
    }

    public void animate() {
        incrementSpriteIndex();
    }

    public Bitmap returnBitmapToDraw() {
        return getCurrentSprite().getBitmap();
    }

    //public void decreaseHealth() { if(addiction > 0) addiction--; }

    public void increaseAddiction() {
        if(addiction < 3) {
            addiction++;
        }
    }

    public boolean isAlive() {
        if(addiction < 3)
            return true;
        else
            return false;
    }

    public Sprite getAddictionSprite() {
        return addictionSprites.getSprites().get(addiction);
    }

    public SpriteAnimation getAddictionSprites() {
        return addictionSprites;
    }

    public void setAddictionSprites(SpriteAnimation addictionSprites) {
        this.addictionSprites = addictionSprites;
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

    public int getAddiction() {
        return addiction;
    }

    public void setAddiction(int addiction) {
        this.addiction = addiction;
    }
}

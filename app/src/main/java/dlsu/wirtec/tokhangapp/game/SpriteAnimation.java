package dlsu.wirtec.tokhangapp.game;

import java.util.ArrayList;

/**
 * Created by Denmark on 3/2/2017.
 */

public class SpriteAnimation {
    private ArrayList<Sprite> sprites; // list of sprites to animate
    private int currentSpriteIndex; // current frame of the animation
    private int destinationSpriteIndex; // last frame of the animation
    private int sourceSpriteIndex; // source frame of the animation

    public SpriteAnimation() {
        sprites = new ArrayList<Sprite>();
    }

    public SpriteAnimation(ArrayList<Sprite> sprites, int currentSprite) {
        this.sprites = sprites;
        this.currentSpriteIndex = currentSprite;
    }

    public Sprite getCurrentSprite() { // returns the current sprite based on the currentSpriteIndex
        return sprites.get(currentSpriteIndex);
    }

    public void incrementSpriteIndex() { // increments the value of the currentSpriteIndex
        currentSpriteIndex ++;
        if(currentSpriteIndex > destinationSpriteIndex)
            currentSpriteIndex = sourceSpriteIndex;
    }

    public void setSpriteAnimation(int sourceSpriteIndex, int destinationSpriteIndex) { // set the range of frame to animate
        if(sourceSpriteIndex != this.sourceSpriteIndex || destinationSpriteIndex != this.destinationSpriteIndex) {
            currentSpriteIndex = sourceSpriteIndex;
            this.sourceSpriteIndex = sourceSpriteIndex;
            this.destinationSpriteIndex = destinationSpriteIndex;
        }
    }

    public void addSpriteToAnimation (Sprite sprite) { // add sprite to the arrayList of sprites
        sprites.add(sprite);
    }

    public void addSpritesToAnimation (SpriteAnimation spriteAnimation) { // add the SpriteAnimation to the arraylist of sprites
        for(int i = 0 ; i < spriteAnimation.getSprites().size(); i++) {
            this.sprites.add(spriteAnimation.getSprites().get(i));
        }
    }

    public void addSprites(Sprite sprite) {
        sprites.add(sprite);
    }

    public int getDestinationSpriteIndex() {
        return destinationSpriteIndex;
    }

    public void setDestinationSpriteIndex(int destinationSpriteIndex) {
        this.destinationSpriteIndex = destinationSpriteIndex;
    }

    public int getSourceSpriteIndex() {
        return sourceSpriteIndex;
    }

    public void setSourceSpriteIndex(int sourceSpriteIndex) {
        this.sourceSpriteIndex = sourceSpriteIndex;
    }

    public ArrayList<Sprite> getSprites() {
        return sprites;
    }

    public void setSprites(ArrayList<Sprite> sprites) {
        this.sprites = sprites;
    }

    public int getCurrentSpriteIndex() {
        return currentSpriteIndex;
    }

    public void setCurrentSpriteIndex(int currentSprite) {
        this.currentSpriteIndex = currentSprite;
    }

    public void destroyBitmaps() {
        for(int i = 0; i < sprites.size(); i++) {
            sprites.get(i).destroyBitmap();
        }
    }
}

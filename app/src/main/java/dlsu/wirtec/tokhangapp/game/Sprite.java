package dlsu.wirtec.tokhangapp.game;

import android.graphics.Bitmap;

/**
 * Created by Denmark on 3/2/2017.
 */

public class Sprite {
    private Bitmap bitmap;
    private double frameWidth;
    private double frameHeight;

    public Sprite(){
    }

    public Sprite(Bitmap bitmap) {
        this.bitmap = bitmap;
        frameWidth = bitmap.getWidth();
        frameHeight = bitmap.getHeight();
    }

    public Sprite(Bitmap bitmap, double frameWidth, double frameHeight) {
        this.bitmap = bitmap;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public double getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(double frameWidth) {
        this.frameWidth = frameWidth;
    }

    public double getFrameHeight() {
        return frameHeight;
    }

    public void setFrameHeight(double frameHeight) {
        this.frameHeight = frameHeight;
    }

    public void destroyBitmap () {
        bitmap.recycle();
    }

}

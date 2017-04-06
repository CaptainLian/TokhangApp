package dlsu.wirtec.tokhangapp.logic;

import android.os.Parcelable;

/**
 * Created by lyssa on 26/03/2017.
 */

public interface GunSound{
    public static final int RESOURCE_ID_NONE = -1;
    /**
     * sound on fire.
     * @return
     */
    public int getFireSoundID();

    /**
     * sound during reload.
     * @return
     */
    public int getReloadSoundID();

    /**
     * sound before each shot.
     * @return
     */
    public int getCockSoundID();

    public int getOnShopViewSoundID();
}

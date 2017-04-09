package dlsu.wirtec.tokhangapp.logic;

import java.util.Random;

/**
 * Created by lyssa on 08/04/2017.
 */

public class GunComplexFireSound implements GunSound {

    public final Random random;
    public final int[] fireSoundIDs;
    public final int reloadSoundID;
    public final int cockSoundID;
    public final int shopViewSoundID;

    public GunComplexFireSound(int[] fireSoundIDs, int reloadSoundID, int cockSoundID, int shopViewSoundID){
        this.fireSoundIDs = fireSoundIDs;
        this.reloadSoundID = reloadSoundID;
        this.cockSoundID = cockSoundID;
        this.shopViewSoundID = shopViewSoundID;

        this.random = new Random(new Random().nextLong());
    }


    @Override
    public int getFireSoundID() {
        int r = random.nextInt(fireSoundIDs.length);
        return fireSoundIDs[r];
    }

    @Override
    public int getReloadSoundID() {
        return reloadSoundID;
    }

    @Override
    public int getCockSoundID() {
        return cockSoundID;
    }

    @Override
    public int getOnShopViewSoundID() {
        return shopViewSoundID;
    }
}

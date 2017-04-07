package dlsu.wirtec.tokhangapp.logic;

public class SimpleGunSound implements GunSound{
    public static final int ID_NONE = -1;

    private int fireSoundID;
    private int reloadSoundID;
    private int cockSoundID;
    private int shopViewSoundID;


    public SimpleGunSound(int fireSoundID, int reloadSoundID, int cockSoundID, int shopViewSoundID) {
        this.fireSoundID = fireSoundID;
        this.reloadSoundID = reloadSoundID;
        this.cockSoundID = cockSoundID;
        this.shopViewSoundID = shopViewSoundID;
    }

    public int getFireSoundID() {
        return fireSoundID;
    }

    public int getReloadSoundID() {
        return reloadSoundID;
    }

    public int getCockSoundID() {
        return cockSoundID;
    }

    public int getOnShopViewSoundID() {
        return shopViewSoundID;
    }
}
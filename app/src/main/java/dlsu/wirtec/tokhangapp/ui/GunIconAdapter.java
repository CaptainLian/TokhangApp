package dlsu.wirtec.tokhangapp.ui;

import android.support.annotation.NonNull;

import dlsu.wirtec.tokhangapp.logic.Gun;

/**
 * Created by lyssa on 01/04/2017.
 */

public class GunIconAdapter {
    public final Gun gun;
    public final int gunIcon;

    public GunIconAdapter(@NonNull Gun gun, int gunIcon) {
        this.gun = gun;
        this.gunIcon = gunIcon;
    }
}

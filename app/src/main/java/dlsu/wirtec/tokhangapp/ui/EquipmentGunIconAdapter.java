package dlsu.wirtec.tokhangapp.ui;

import android.support.annotation.NonNull;

import dlsu.wirtec.tokhangapp.logic.Gun;

/**
 * Created by lyssa on 01/04/2017.
 */

public class EquipmentGunIconAdapter {
    public final Gun GUN;
    public final int DRAWABLE_ID_GUN_UNLOCKED;

    public EquipmentGunIconAdapter(@NonNull Gun GUN, int DRAWABLE_ID_GUN_UNLOCKED) {
        this.GUN = GUN;
        this.DRAWABLE_ID_GUN_UNLOCKED = DRAWABLE_ID_GUN_UNLOCKED;
    }
}

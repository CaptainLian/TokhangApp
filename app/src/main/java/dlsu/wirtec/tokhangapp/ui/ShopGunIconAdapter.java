package dlsu.wirtec.tokhangapp.ui;

import android.support.annotation.NonNull;

import dlsu.wirtec.tokhangapp.logic.Gun;

/**
 * Created by lyssa on 04/04/2017.
 */

public class ShopGunIconAdapter {
    public final Gun GUN;
    public final int DRAWABLE_ID_SOLD;
    public final int DRAWABLE_ID_PURCHASED;

    public ShopGunIconAdapter(@NonNull Gun GUN, @NonNull int DRAWABLE_ID_PURCHASED, @NonNull int DRAWABLE_ID_SOLD) {
        this.GUN = GUN;
        this.DRAWABLE_ID_PURCHASED = DRAWABLE_ID_PURCHASED;
        this.DRAWABLE_ID_SOLD = DRAWABLE_ID_SOLD;
    }

}

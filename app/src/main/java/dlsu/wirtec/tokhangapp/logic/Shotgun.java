package dlsu.wirtec.tokhangapp.logic;

import android.support.annotation.Nullable;

/**
 * Created by lyssa on 21/03/2017.
 */

public class Shotgun extends Gun {

    /**
     * @param name
     * @param damage
     * @param fireDelayTime Delay between each time a gun can be fired in miliseconds.
     * @param reloadTime    Amount of time required to reload the gun in miliseconds.
     * @param cost          Cost in any abritrary currency.
     */
    public Shotgun(@Nullable String name, int damage, long fireDelayTime, long reloadTime, int cost) {
        super(name, damage, fireDelayTime, reloadTime, cost);
    }
}

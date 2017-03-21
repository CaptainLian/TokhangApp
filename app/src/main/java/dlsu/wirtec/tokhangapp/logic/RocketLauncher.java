package dlsu.wirtec.tokhangapp.logic;

import android.support.annotation.Nullable;

/**
 * Created by lyssa on 21/03/2017.
 */

public class RocketLauncher extends Gun {

    private int explosiveRadius;

     /**
     * @param name
     * @param damage
     * @param fireDelayTime Delay between each time a gun can be fired in miliseconds.
     * @param reloadTime    Amount of time required to reload the gun in miliseconds.
     * @param cost          Cost in any abritrary currency.
     * @param explosiveRadius
     */
    public RocketLauncher(@Nullable String name, int damage, long fireDelayTime, long reloadTime, int cost, int explosiveRadius) {
        super(name, damage, fireDelayTime, reloadTime, cost);
        setExplosiveRadius(explosiveRadius);
    }

    public void setExplosiveRadius(int explosiveRadius){
        if(explosiveRadius < 0){
            throw new IllegalArgumentException("explosive radius cannot be negative");
        }
        this.explosiveRadius = explosiveRadius;
    }

    @Override
    public void fire(int x, int y) {
        super.fire(x, y);
    }
}

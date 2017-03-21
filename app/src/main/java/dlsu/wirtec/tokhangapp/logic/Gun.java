package dlsu.wirtec.tokhangapp.logic;

import android.support.annotation.Nullable;

/**
 * Created by lyssa on 21/03/2017.
 */

public class Gun {

    private String name;

    private int damage;

    /**
     * Amount of time to fire between shots in miliseconds
     */
    private long fireDelayTime;

    /**
     * Amount of time required to reload in miliseconds
     */
    private long reloadTime;

    private int cost;

    /**
     *
     * @param name
     * @param damage
     * @param fireDelayTime Delay between each time a gun can be fired in miliseconds.
     * @param reloadTime Amount of time required to reload the gun in miliseconds.
     * @param cost Cost in any abritrary currency.
     */
    public Gun(@Nullable String name, int damage, long fireDelayTime, long reloadTime, int cost){
        setName(name);
        setDamage(damage);
        setFireDelayTime(fireDelayTime);
        setReloadTime(reloadTime);
        setCost(cost);
    }

    public void setDamage(int damage){
        if(damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative.");
        }
        this.damage = damage;
    }

    /**
     * Set the amount of time between each shots.
     * @param fireDelayTime
     */
    public void setFireDelayTime(long fireDelayTime){
        if(fireDelayTime < 0){
            throw  new IllegalArgumentException("fire delay time cannot be negative.");
        }
        this.fireDelayTime = fireDelayTime;
    }

    /**
     * Set the amount of time to reload a gun.
     * @param reloadTime time in miliseconds
     */
    public void setReloadTime(long reloadTime){
        if(reloadTime < 0){
            throw new IllegalArgumentException("reload time cannot be negative.");
        }
        this.reloadTime = reloadTime;
    }

    public void setName(@Nullable String name){
        this.name = name != null ? name : "";
    }

    public void setCost(int cost){
        if(cost < 0){
            throw new IllegalArgumentException("Cost cannot be negative.");
        }
        this.cost = cost;
    }

    public String getName() {
        return this.name;
    }

    public int getDamage() {
        return this.damage;
    }

    public long getFireDelayTime() {
        return this.fireDelayTime;
    }

    public long getReloadTime(){
        return this.reloadTime;
    }

    public int getCost(){
        return this.cost;
    }

    /**
     * Fires the gun at the specified point (x, y)
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public void fire(int x, int y){

    }
}

package dlsu.wirtec.tokhangapp.logic;

import android.support.annotation.Nullable;

import dlsu.wirtec.tokhangapp.game.Character;
import dlsu.wirtec.tokhangapp.game.Sprite;
import dlsu.wirtec.tokhangapp.managers.GameManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lyssa on 21/03/2017.
 */

public class Gun {

    private String name;
    private String description;

    private int damage;

    /**
     * Amount of time to fire between shots in miliseconds
     */
    private long fireDelayTime;

    /**
     * Amount of time required to reload in miliseconds
     */
    private long reloadTime;

    private int maxAmmoCapacity = -1;


    private int cost;

    private GunSound gunSound;

    private int id;

    /**
     *
     * @param name
     * @param damage
     * @param fireDelayTime Delay between each time a GUN can be fired in miliseconds.
     * @param reloadTime Amount of time required to reload the GUN in miliseconds.
     * @param cost Cost in any abritrary currency.
     */
    public Gun(@Nullable String name, int damage, int maxAmmoCapacity, long fireDelayTime, long reloadTime, int cost){
        this(name, damage, maxAmmoCapacity, fireDelayTime, reloadTime, cost, null, null);
    }

    /**
     *
     * @param name
     * @param damage
     * @param fireDelayTime Delay between each time a GUN can be fired in miliseconds.
     * @param reloadTime Amount of time required to reload the GUN in miliseconds.
     * @param cost Cost in any abritrary currency.
     * @param gunSound
     */
    public Gun(@Nullable String name, int damage, int maxAmmoCapacity, long fireDelayTime, long reloadTime, int cost, @Nullable GunSound gunSound, @Nullable String description){
        setName(name);
        setDamage(damage);
        setMaxAmmoCapacity(maxAmmoCapacity);
        setFireDelayTime(fireDelayTime);
        setReloadTime(reloadTime);
        setCost(cost);
        setGunSound(gunSound);
        setDescription(description);
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
     * Set the amount of time to reload a GUN.
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

    public void setDescription(@Nullable String description){
        this.description = description;
    }

    /**
     * Sets the maximum amount of shots a GUN has
     * @param maxAmmoCapacity the maximum amount of shots before reloading, a value of zero or negative means infinite number of shots.
     */
    public void setMaxAmmoCapacity(int maxAmmoCapacity){
       this.maxAmmoCapacity = maxAmmoCapacity;
    }

    public void setGunSound(@Nullable GunSound gunSound){
        this.gunSound = gunSound;
    }

    public String getName() {
        return this.name;
    }

    public @Nullable String getDescription (){
        return this.description;
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

    public @Nullable GunSound getGunSound(){
        return gunSound;
    }


    /**
     * Fires the gun in the specified x and y coordinate.
     * ALL GUNS OVERRIDING THIS METHOD SHOULD RETURN A LIST OF AFFECTED CHARACTERS, WHICH WOULD BE ITERATED OVER BY THE GAME
     * LINKED LIST CAN BE USED FOR EFFICIENCY
     * @param touchX
     * @param touchY
     * @param characters The touchable characters in the game area
     * @return The affected characters in the area
     */
    public List<Character> fire(float touchX, float touchY, ArrayList<Character> characters){
        LinkedList<Character> affectedChars = new LinkedList<>();
        for(Character c: characters){
            if(c.isTouched(touchX, touchY)){
                affectedChars.add(c);
            }
        }
        return affectedChars;
    }

    public int getGunID(){
        return id;
    }

    public void setGunID(int id){
        this.id = id;
    }


}

package dlsu.wirtec.tokhangapp.logic;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dlsu.wirtec.tokhangapp.game.Character;
import dlsu.wirtec.tokhangapp.game.Sprite;

/**
 * Created by lyssa on 21/03/2017.
 */

public class RocketLauncher extends Gun {

    private double explosiveRadius;
    /**
     * @param name
     * @param damage
     * @param maxAmmoCapacity
     * @param fireDelayTime   Delay between each time a GUN can be fired in miliseconds.
     * @param reloadTime      Amount of time required to reload the GUN in miliseconds.
     * @param cost            Cost in any abritrary currency.
     * @param explosiveRadius
     * @throws IllegalArgumentException
     */
    public RocketLauncher(@Nullable String name, int damage, int maxAmmoCapacity, long fireDelayTime, long reloadTime, int cost, double explosiveRadius) throws IllegalArgumentException {
        this(name, damage, maxAmmoCapacity, fireDelayTime, reloadTime, cost, explosiveRadius, null, null);
    }

    /**
     * @param name
     * @param damage
     * @param maxAmmoCapacity
     * @param fireDelayTime   Delay between each time a GUN can be fired in miliseconds.
     * @param reloadTime      Amount of time required to reload the GUN in miliseconds.
     * @param cost            Cost in any abritrary currency.
     * @param explosiveRadius
     * @param gunSound
     */
    public RocketLauncher(@Nullable String name, int damage, int maxAmmoCapacity, long fireDelayTime, long reloadTime, int cost, double explosiveRadius, @Nullable GunSound gunSound, @Nullable String description) {
        super(name, damage, maxAmmoCapacity, fireDelayTime, reloadTime, cost, gunSound, description);
        setExplosiveRadius(explosiveRadius);
    }


    public void setExplosiveRadius(double explosiveRadius){
        if(explosiveRadius < 0){
            throw new IllegalArgumentException("explosive radius cannot be negative");
        }
        this.explosiveRadius = explosiveRadius*explosiveRadius;
    }

    @Override
    public List<Character> fire(float touchX, float touchY, ArrayList<Character> characters) {
        LinkedList<Character> affectedCharacters = new LinkedList<>();
        for(int i = 0, size = characters.size(); i < size; i++){
            Character c = characters.get(i);

            if(Character.DRUGS.equals(c.getName())){
                final double deltaX = c.getMidX() - touchX;
                final double deltaY = c.getMidY() - touchY;
                if(deltaX*deltaX + deltaY*deltaY <= explosiveRadius){
                    affectedCharacters.add(c);
                }
            }
        }
        return affectedCharacters;
    }
}

package dlsu.wirtec.tokhangapp.logic;

import android.support.annotation.Nullable;

/**
 * Created by lyssa on 21/03/2017.
 */

public class Shotgun extends Gun {

    /**
     * The radius the pellet will scatter
     */
    private int spreadRadius;
    /**
     * The damage radius for each pellet
     */
    private int pelletRadius;
    /**
     * The amount of pellets that will be scattered within spread radius
     */
    private int pelletAmount;

    /**
     * @param name
     * @param damage The damage of each pellet.
     * @param maxAmmoCapacity
     * @param fireDelayTime   Delay between each time a gun can be fired in miliseconds.
     * @param reloadTime      Amount of time required to reload the gun in miliseconds.
     * @param cost            Cost in any abritrary currency.
     * @param cost Cost in any abritrary currency.
     * @param spreadRadius The range the pellet will scatter.
     * @param pelletRadius The damage radius of each pellet.
     * @param pelletAmount The amount of pellets the gun fires.
     * @throws IllegalArgumentException
     */
    public Shotgun(@Nullable String name, int damage, int maxAmmoCapacity, long fireDelayTime, long reloadTime, int cost, int spreadRadius, int pelletRadius, int pelletAmount) throws IllegalArgumentException{
        this(name, damage, maxAmmoCapacity, fireDelayTime, reloadTime, cost, spreadRadius, pelletRadius, pelletAmount, null, null);
    }

    /**
     * @param name
     * @param damage The damage of each pellet.
     * @param maxAmmoCapacity
     * @param fireDelayTime   Delay between each time a gun can be fired in miliseconds.
     * @param reloadTime      Amount of time required to reload the gun in miliseconds.
     * @param cost            Cost in any abritrary currency.
     * @param cost Cost in any abritrary currency.
     * @param spreadRadius The range the pellet will scatter.
     * @param pelletRadius The damage radius of each pellet.
     * @param pelletAmount The amount of pellets the gun fires.
     * @param gunSound
     * @throws IllegalArgumentException
     */
    public Shotgun(@Nullable String name, int damage, int maxAmmoCapacity, long fireDelayTime, long reloadTime, int cost, int spreadRadius, int pelletRadius, int pelletAmount, @Nullable GunSound gunSound, @Nullable String description) {
        super(name, damage, maxAmmoCapacity, fireDelayTime, reloadTime, cost, gunSound, description);
        setSpreadRadius(spreadRadius);
        setPelletAmount(pelletAmount);
        setPelletRadius(pelletRadius);
    }


    /**
     * Sets the range of the spread of the pellets
     * @param spreadRadius
     * @throws IllegalArgumentException If the radius is less than or equal to zero (0).
     */
    public void setSpreadRadius(int spreadRadius) throws IllegalArgumentException {
        if(spreadRadius <= 0)
            throw new IllegalArgumentException("Spread radius cannot be less than or equal to zero (0).");
        this.spreadRadius = spreadRadius;
    }

    /**
     * Sets the damage radius of each pellet
     * @param pelletRadius
     * @throws IllegalArgumentException If the radius is less than or equal to zero (0).
     */
    public void setPelletRadius(int pelletRadius) throws IllegalArgumentException  {
        if(pelletRadius <= 0 )
            throw new IllegalArgumentException("Pellet radius cannot be less than or equal to zero (0).");
        this.pelletRadius = pelletRadius;
    }

    /**
     * Sets the amount of pellets to fire
     * @param pelletAmount
     * @throws IllegalArgumentException  If the amount is less than or equal to zero (0).
     */
    public void setPelletAmount(int pelletAmount) throws IllegalArgumentException {
        if(pelletAmount <= 0)
            throw new IllegalArgumentException("Pellet amount cannot be less than or equal to zero (0).");
        this.pelletAmount = pelletAmount;
    }

    public int getSpreadRadius() {
        return spreadRadius;
    }

    public int getPelletRadius() {
        return pelletRadius;
    }

    public int getPelletAmount() {
        return pelletAmount;
    }

    @Override
    public void fire(int x, int y) {
        super.fire(x, y);
    }
}

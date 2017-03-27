package dlsu.wirtec.tokhangapp.logic;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import dlsu.wirtec.tokhangapp.database.Score;

/**
 * Created by lyssa on 27/03/2017.
 */

public class Player {

    private CharSequence name;
    private int money = 0;
    private int score = 0;
    private int maxLife;

    public @NonNull Player setName(@Nullable CharSequence name){
        this.name = name;
        return this;
    }

    public @NonNull Player incrementMoney(int amount){
        this.money += amount;
        return this;
    }

    public @NonNull Player decrementMoney(int amount){
        this.money = amount > this.money ? 0 : this.money - amount;
        return this;
    }

    public @NonNull Player setMoney(int money){
        this.money = money < 0 ? 0 : money;
        return this;
    }

    public @NonNull Player incrementScore(int amount){
        this.score += amount;
        return this;
    }

    public @NonNull Player decrementScore(int amount){
        this.score -= amount;
        return this;
    }

    public @NonNull Player setScore(int score){
        this.score = score;
        return this;
    }

    public @NonNull Player setMaxLife(int maxLife) throws IllegalArgumentException {
        if(maxLife <= 0 ){
            throw new IllegalArgumentException("Max life cannot be  less than or equal to zero (0).");
        }
        this.maxLife = maxLife;
        return this;
    }

    public Score getScore(){
        return new Score(this.name.toString(), this.score);
    }

}

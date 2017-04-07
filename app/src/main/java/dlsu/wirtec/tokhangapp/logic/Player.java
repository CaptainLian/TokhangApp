package dlsu.wirtec.tokhangapp.logic;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashSet;
import java.util.Set;

import dlsu.wirtec.tokhangapp.database.Score;
import dlsu.wirtec.tokhangapp.managers.GunManager;

/**
 * Created by lyssa on 27/03/2017.
 */

public class Player {

    public static final String PREFERENCES_NAME = "PLAYER_NAME";
    public static final String PREFERENCES_MONEY = "PLAYER_MONEY";
    public static final String PREFERENCES_SCORE = "PLAYER_SCORE";
    public static final String PREFERENCES_GUN_EQUIPPED = "PLAYER_GUN_EQUIPPED";
    public static final String PREFERENCES_GUNS_OWNED = "PLAYER_GUNS_OWNED";
    public static final String PREFERENCES_CURRENT_LEVEL = "PLAYER_CURRENT_LEVEL";

    private String name;
    private int money;
    private int score;

    private int currentLevel = 0;

    private Gun equippedGun;
    private HashSet<Gun> ownedGuns = new HashSet<>();

    private Player(){

    }

    public Player(String name, Gun defaultGun, int money){
        this(name, defaultGun);
        this.setMoney(money);
    }

    public Player(String name,  Gun defaultGun){
        this.setName(name);

        this.ownedGuns.add(defaultGun);
        this.equippedGun = defaultGun;
    }

    public @NonNull Player setName(@Nullable String name){
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

    public int getMoney(){
        return this.money;
    }

    public int getScore(){
        return this.score;
    }

    public Score getScoreObject(){
        return new Score(this.name.toString(), this.score);
    }

    public boolean canPurchaseGun(Gun g){
        return this.money >= g.getCost();
    }

    public boolean addGun(Gun g){
        return this.ownedGuns.add(g);
    }

    public boolean purchaseGun(Gun g){
        if(canPurchaseGun(g)){
            this.money -= g.getCost();
            return addGun(g);
        }
        return false;
    }

    public boolean isGunOwned(Gun g){
        return this.ownedGuns.contains(g);
    }

    public boolean equipGun(Gun g){
        if(isGunOwned(g)){
            this.equippedGun = g;
            return true;
        }
        return false;
    }

    public Gun getEquippedGun(){
        return this.equippedGun;
    }

    public Gun[] getOwnedGuns(){
        Gun[] g = new Gun[this.ownedGuns.size()];
        return this.ownedGuns.toArray(g);
    }

    public void save(SharedPreferences.Editor editor){
        Set<String> set = new android.support.v4.util.ArraySet<>(ownedGuns.size());
        for (Gun g: ownedGuns){
            set.add(Integer.toString(g.getGunID()));
        }

        editor.putString(PREFERENCES_NAME, name.toString());
        editor.putInt(PREFERENCES_CURRENT_LEVEL, currentLevel);
        editor.putInt(PREFERENCES_MONEY, money);
        editor.putInt(PREFERENCES_SCORE, score);
        editor.putInt(PREFERENCES_GUN_EQUIPPED, equippedGun.getGunID());
        editor.putStringSet(PREFERENCES_GUNS_OWNED, set);
    }

    public static final Player getPlayer(SharedPreferences sp, GunManager gunManager){
        int equippedID = sp.getInt(PREFERENCES_GUN_EQUIPPED, 0);

        Set<String> defaultSet = new android.support.v4.util.ArraySet<>(1);
        defaultSet.add(Integer.toString(gunManager.GUN_PISTOL_ID));
        Set<String> ownedGuns = sp.getStringSet(PREFERENCES_GUNS_OWNED, defaultSet);

        Player p = new Player();
        p.name = sp.getString(PREFERENCES_NAME, "");
        p.money = sp.getInt(PREFERENCES_MONEY, 0);
        p.score = sp.getInt(PREFERENCES_SCORE, 0);;
        p.currentLevel = sp.getInt(PREFERENCES_CURRENT_LEVEL, 1);
        for (String gunID : ownedGuns){
            int id = Integer.parseInt(gunID);
            Gun g = gunManager.getGun(id);
            p.ownedGuns.add(g);
        }
        p.equippedGun = gunManager.getGun(equippedID);

        return p;
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void incrementLevel(){
        this.currentLevel++;
    }
}

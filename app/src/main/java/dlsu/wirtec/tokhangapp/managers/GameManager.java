package dlsu.wirtec.tokhangapp.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import dlsu.wirtec.tokhangapp.logic.Player;

/**
 * Created by lyssa on 26/03/2017.
 */

public class GameManager {

    private static SoundManager soundManager;
    private static GunManager gunManager;
    private static GameManager gameManager;

    private Context context;
    private Player player;
    private GameManager(Context context){
        this.context = context;
    }

    /**
     * Initializes the game manager
     * @param context
     * @throws IllegalStateException If the manager is already initalized
     */
    public static void initialize(@NonNull Context context) throws IllegalStateException {
        if(gunManager == null || soundManager == null || gameManager == null) {
            soundManager = new SoundManager(context);
            gunManager = new GunManager(soundManager);
            gameManager = new GameManager(context);

            if(gameManager.isSavedPlayerExist()){
                gameManager.player = Player.getPlayer(PreferenceManager.getDefaultSharedPreferences(context), gunManager);
            }
        }
    }

    /**
     *
     * @return A sound manager
     * @throws IllegalStateException If the manager is not initialized
     */
    public static SoundManager getSoundManager() throws IllegalStateException {
        return soundManager;
    }

    public static GunManager getGunManager(){
        return gunManager;
    }

    public static GameManager getGameManager(){
        return gameManager;
    }

    public boolean isSavedPlayerExist(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.context);
        return preferences.contains(Player.PREFERENCES_NAME) &&
                preferences.contains(Player.PREFERENCES_SCORE) &&
                preferences.contains(Player.PREFERENCES_MONEY) &&
                preferences.contains(Player.PREFERENCES_GUNS_OWNED) &&
                preferences.contains(Player.PREFERENCES_GUN_EQUIPPED);
    }

    public Player createNewPlayerOverwrite(String name){
        this.player = new Player(name, gunManager.PISTOL);
        this.player.setMoney(500);
        SharedPreferences.Editor preferences = PreferenceManager.getDefaultSharedPreferences(this.context).edit();
        preferences.clear();
        this.player.save(preferences);
        preferences.apply();
        return this.player;
    }

    public void savePlayer() {
        if (player != null) {
            SharedPreferences.Editor preferences = PreferenceManager.getDefaultSharedPreferences(this.context).edit();
            player.save(preferences);
            preferences.apply();
        }
    }

    public Player getPlayer(){
        return this.player;
    }
}

package dlsu.wirtec.tokhangapp.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import dlsu.wirtec.tokhangapp.game.House;
import dlsu.wirtec.tokhangapp.game.Stage;
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
    private Stage[] stages;


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

            gameManager.stages =  new Stage[]{
                    new Stage(
                            "Sector 1",
                            3,
                            new String[]{
                                    House.BUNGALOW,
                                    House.SKWATER
                            },
                            700
                    ),
                    new Stage(
                            "Sector 2",
                            5,
                            new String[]{
                                House.BUNGALOW
                            },
                            600
                    ),
                    new Stage(
                            "Sector 3",
                            5,
                            new String[]{
                               House.SKWATER
                            },
                            600
                    ),
                    new Stage(
                            "Sector 4",
                            7,
                            new String[]{
                                    House.SKWATER,
                                    House.BUNGALOW,
                                    House.BUNGALOW,
                                    House.SKWATER
                            },
                            550
                    )
            };//Stage array
        }//if gameManager or SoundManager or gunManager is null.
    }//function initialize()

    /**
     *
     * @return A sound manager
     * @throws IllegalStateException If the manager is not initialized
     */
    public static SoundManager getSoundManager() throws IllegalStateException {
        if(soundManager == null){
            throw new IllegalStateException("Game Manager not initialized.");
        }
        return soundManager;
    }


    public static GunManager getGunManager() throws IllegalStateException {
        if(gunManager == null){
            throw new IllegalStateException("Game Manager not initialized.");
        }
        return gunManager;
    }

    public static GameManager getGameManager() throws IllegalStateException{
        if(gameManager == null){
            throw new IllegalStateException("Game Manager not initialized.");
        }

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

    public Player createNewPlayer(String name){
        this.player = new Player(name, gunManager.PISTOL, 500);
        return this.player;
    }

    public void savePlayer() {
        if (player != null) {
            SharedPreferences.Editor preferences = PreferenceManager.getDefaultSharedPreferences(this.context).edit();
            player.save(preferences);
            preferences.apply();
        }
    }

    /**
     *
     * @return
     */
    public Player getPlayer(){
        return this.player;
    }

    public Stage getStage(int level){
        if(level < 0 || level >= stages.length){
            throw new IllegalArgumentException("Invalid level " + level + ". Min: 0, Max: " + stages.length);
        }

        return stages[level];
    }
}

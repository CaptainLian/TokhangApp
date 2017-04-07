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
                            0,
                            "Sector 1",
                            3,
                            new String[]{
                                    House.BUILDING1,
                                    House.BUILDING2
                            },
                            800
                    ),
                    new Stage(
                            1,
                            "Sector 2",
                            5,
                            new String[]{
                                House.BUILDING3
                            },
                            720
                    ),
                    new Stage(
                            2,
                            "Sector 3",
                            5,
                            new String[]{
                               House.BUILDING1
                            },
                            720
                    ),
                    new Stage(
                            3,
                            "Sector 4",
                            7,
                            new String[]{
                                    House.BUILDING1,
                                    House.BUILDING2,
                                    House.BUILDING2,
                                    House.BUILDING3
                            },
                            640
                    ),
                    new Stage(
                            4,
                            "Sector 5",
                            7,
                            new String[]{
                                    House.BUILDING2,
                                    House.BUILDING1,
                                    House.BUILDING1,
                                    House.BUILDING3
                            },
                            500
                    ),
                    new Stage(
                            5,
                            "Sector 6",
                            7,
                            new String[]{
                                    House.BUILDING1,
                                    House.BUILDING3,
                                    House.BUILDING3,
                                    House.BUILDING2
                            },
                            420
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
                preferences.contains(Player.PREFERENCES_GUN_EQUIPPED) &&
                preferences.contains(Player.PREFERENCES_CURRENT_LEVEL);
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

    public Player getPlayer(){
        return this.player;
    }

    public Stage getStage(int level){
        return stages[level];
    }
}

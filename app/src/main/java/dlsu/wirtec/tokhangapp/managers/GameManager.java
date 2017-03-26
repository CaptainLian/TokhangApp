package dlsu.wirtec.tokhangapp.managers;

import android.content.Context;

/**
 * Created by lyssa on 26/03/2017.
 */

public class GameManager {

    private static Context context = null;

    private static SoundManager soundManager;

    /**
     * Initializes the game manager
     * @param context
     * @throws IllegalStateException If the manager is already initalized
     */
    public static void initialize(Context context) throws IllegalStateException {
        if(context != null){
            throw new IllegalStateException("Manager already initalized");
        }
        GameManager.context = context;
    }

    public static SoundManager getSoundManager() throws IllegalStateException {
        if(context == null){
            throw new IllegalStateException("Manager not initialized!");
        }
        if(soundManager == null){
            soundManager = new SoundManager(context);
        }
        return soundManager;
    }
}

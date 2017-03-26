package dlsu.wirtec.tokhangapp.managers;

import android.content.Context;
import android.support.annotation.NonNull;

import dlsu.wirtec.tokhangapp.logic.Gun;

/**
 * Created by lyssa on 26/03/2017.
 */

public class GameManager {

    private static SoundManager soundManager;
    private static GunManager gunManager;

    /**
     * Initializes the game manager
     * @param context
     * @throws IllegalStateException If the manager is already initalized
     */
    public static void initialize(@NonNull Context context) throws IllegalStateException {
        if(gunManager == null || soundManager == null) {
            soundManager = new SoundManager(context);
            gunManager = new GunManager(soundManager);
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
}

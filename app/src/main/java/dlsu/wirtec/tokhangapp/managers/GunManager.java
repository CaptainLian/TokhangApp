package dlsu.wirtec.tokhangapp.managers;

import android.content.Context;
import android.content.res.Resources;

import dlsu.wirtec.tokhangapp.R;
import dlsu.wirtec.tokhangapp.logic.Gun;
import dlsu.wirtec.tokhangapp.logic.RocketLauncher;
import dlsu.wirtec.tokhangapp.logic.Shotgun;
import dlsu.wirtec.tokhangapp.logic.SimpleGunSound;

/**
 * Created by lyssa on 26/03/2017.
 */

public class GunManager {

    public final Gun PISTOL;
    public final Gun RIFLE;
    public final Shotgun SHOTGUN;
    public final Gun SNIPER;
    public final RocketLauncher ROCKET;

    public final Gun[] ALL_GUNS;

    GunManager(SoundManager soundManager){
        Context context = soundManager.getContext();
        Resources r = context.getResources();

       PISTOL = new Gun(
               r.getString(R.string.gun_pistol_name),
               1, // damage
               16, // ammo
               500, //fireDelay
               400, // reloadDelay
               0 // cost
       );

        RIFLE = new Gun(
          r.getString(R.string.gun_rifle_name),
                1, // damage
                30, // ammo
                350, // fireDelay
                550, // reload Delay
                500, // cost
                new SimpleGunSound(
                        -1,
                        -1,
                        -1,
                        soundManager.SOUND_SHOP_UI_BRRT
                )
        );

        SHOTGUN = new Shotgun(
                r.getString(R.string.gun_shotgun_name),
                2, //damage
                9, // ammont
                600,// fireDelay
                700, // reloadDelay
                1200, //cost
                300, //spread
                32, // pelletRadius
                8, // pellet count
                new SimpleGunSound(
                        soundManager.SOUND_GUN_SHOTGUN_FIRE1,
                        soundManager.SOUND_GUN_SHOTGUN_RELOAD1,
                        soundManager.SOUND_GUN_SHOTGUN_COCK1,
                        -1
                )
        );

        SNIPER = new Gun(
                r.getString(R.string.gun_sniper_name),
                5, // damage
                6, //ammo
                550, //fire delay
                600, // reload delay
                2000, // cost
                new SimpleGunSound(
                        -1,
                        -1,
                        -1,
                        soundManager.SOUND_SHOP_UI_PEW
                )
        );

        ROCKET = new RocketLauncher(
                r.getString(R.string.gun_rocket_name),
                10,
                1,
                0,
                1200,
                3200,
                700
        );

        ALL_GUNS = new Gun[]{PISTOL, RIFLE, SHOTGUN, SNIPER, ROCKET};
    }//constructor


}

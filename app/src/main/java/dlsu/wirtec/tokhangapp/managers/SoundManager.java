package dlsu.wirtec.tokhangapp.managers;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import dlsu.wirtec.tokhangapp.R;

/**
 * Created by lyssa on 21/03/2017.
 */

public class SoundManager{
    public static final int DEFAULT_PLAY_PRIORITY = 1;
    public static final int DEFAULT_LOAD_PRIORITY = 1;
    public static final int DEFAULT_MAX_SOUND_STREAMS = 5;

    private final Context context;

    private final SoundPool soundPlayer;
    private MediaPlayer currentMusicPlayer;

    private float volume;


    public final int SOUND_PLAYER_DEATH2;
    public final int SOUND_PLAYER_PAIN1;
    public final int SOUND_PLAYER_PAIN2;
    public final int SOUND_PLAYER_PAIN3;

    /* UI sounds */
    public final int SOUND_MENU_ERROR1;
    public final int SOUND_SHOP_PURCHASE1;

    /* Gun sounds */
    public final int SOUND_GUN_SHOTGUN_FIRE1;
    public final int SOUND_GUN_SHOTGUN_RELOAD1;
    public final int SOUND_GUN_SHOTGUN_COCK1;

    SoundManager(Context context){
        this(context, 1.0f);
    }

    SoundManager(Context context, float volume){
        this.context = context;
        this.volume = volume;

        soundPlayer = new SoundPool(DEFAULT_MAX_SOUND_STREAMS, AudioManager.STREAM_MUSIC, 0);

        /* Load player sounds*/
        SOUND_PLAYER_DEATH2 = soundPlayer.load(context, R.raw.sound_player_death2, DEFAULT_LOAD_PRIORITY);
        SOUND_PLAYER_PAIN1 = soundPlayer.load(context, R.raw.sound_player_pain1, DEFAULT_LOAD_PRIORITY);
        SOUND_PLAYER_PAIN2 = soundPlayer.load(context, R.raw.sound_player_pain2, DEFAULT_LOAD_PRIORITY);
        SOUND_PLAYER_PAIN3 = soundPlayer.load(context, R.raw.sound_player_pain3, DEFAULT_LOAD_PRIORITY);

        /* load ui sounds*/
        SOUND_MENU_ERROR1 = soundPlayer.load(context, R.raw.sound_menu_error1, DEFAULT_LOAD_PRIORITY);
        SOUND_SHOP_PURCHASE1 = soundPlayer.load(context, R.raw.sound_shop_purchase1, DEFAULT_LOAD_PRIORITY);

        /* Load gun sounds */
        //shotgun
        SOUND_GUN_SHOTGUN_FIRE1 = soundPlayer.load(context, R.raw.sound_gun_shotgun_fire1, DEFAULT_LOAD_PRIORITY);
        SOUND_GUN_SHOTGUN_RELOAD1 = soundPlayer.load(context, R.raw.sound_gun_shotgun_reload1, DEFAULT_LOAD_PRIORITY);
        SOUND_GUN_SHOTGUN_COCK1 = soundPlayer.load(context, R.raw.sound_gun_shotgun_cock1, DEFAULT_LOAD_PRIORITY);
    }

    public void playSound(int soundID){
        soundPlayer.play(soundID, volume, volume, DEFAULT_PLAY_PRIORITY, 0, 1f);
    }

    public void playMusic(int rawID) {
        if(currentMusicPlayer != null){
            currentMusicPlayer.stop();
            currentMusicPlayer.release();
            currentMusicPlayer = null;
        }

        currentMusicPlayer = MediaPlayer.create(context, rawID);
        currentMusicPlayer.start();
    }


}

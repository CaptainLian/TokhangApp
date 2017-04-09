package dlsu.wirtec.tokhangapp.managers;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.annotation.Nullable;

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
    public final int SOUND_SHOP_BRRT;
    public final int SOUND_SHOP_PEW;
    public final int SOUND_SHOP_SPRAK;
    public final int SOUND_SHOP_BOOM;

    public final int SOUND_EQUIPMENT_LOAD1;
    public final int SOUND_EQUIPMENT_LOAD2;
    public final int SOUND_EQUIPMENT_LOAD3;

    /* Gun sounds */
    public final int SOUND_GUN_SHOTGUN_FIRE1;
    public final int SOUND_GUN_SHOTGUN_RELOAD1;
    public final int SOUND_GUN_SHOTGUN_COCK1;

    public final int SOUND_GUN_PISTOL_FIRE1;

    public final int SOUND_GUN_RIFLE_FIRE1;

    public final int SOUND_GUN_ROCKET_FIRE1;

    public final int SOUND_POWERUP_PICKUP1;

    public final int SOUND_GAME_CLEAR1;
    public final int SOUND_GAME_CLEAR2;
    public final int SOUND_GAME_CLEAR3;

    public final int SOUND_GAME_GO1;
    public final int SOUND_GAME_GO2;
    public final int SOUND_GAME_GO3;
    public final int SOUND_GAME_GO4;

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
        SOUND_SHOP_BOOM = soundPlayer.load(context, R.raw.sound_shop_boom, DEFAULT_LOAD_PRIORITY);
        SOUND_SHOP_BRRT = soundPlayer.load(context, R.raw.sound_shop_brrt, DEFAULT_LOAD_PRIORITY);
        SOUND_SHOP_PEW = soundPlayer.load(context, R.raw.sound_shop_pew, DEFAULT_LOAD_PRIORITY);
        SOUND_SHOP_SPRAK = soundPlayer.load(context, R.raw.sound_shop_sprak, DEFAULT_LOAD_PRIORITY);

        /* Load GUN sounds */
        //shotgun
        SOUND_GUN_SHOTGUN_FIRE1 = soundPlayer.load(context, R.raw.sound_gun_shotgun_fire1, DEFAULT_LOAD_PRIORITY);
        SOUND_GUN_SHOTGUN_RELOAD1 = soundPlayer.load(context, R.raw.sound_gun_shotgun_reload1, DEFAULT_LOAD_PRIORITY);
        SOUND_GUN_SHOTGUN_COCK1 = soundPlayer.load(context, R.raw.sound_gun_shotgun_cock1, DEFAULT_LOAD_PRIORITY);

        //pistol
        SOUND_GUN_PISTOL_FIRE1 = soundPlayer.load(context, R.raw.sound_gun_pistol_fire1, DEFAULT_LOAD_PRIORITY);

        SOUND_GUN_RIFLE_FIRE1 = soundPlayer.load(context, R.raw.sound_gun_rifle_fire1, DEFAULT_LOAD_PRIORITY);

        SOUND_GUN_ROCKET_FIRE1 = soundPlayer.load(context, R.raw.sound_gun_rocket_fire1, DEFAULT_LOAD_PRIORITY);

        SOUND_POWERUP_PICKUP1 = soundPlayer.load(context, R.raw.sound_powerup_pickup1, DEFAULT_LOAD_PRIORITY);

        SOUND_EQUIPMENT_LOAD1 = soundPlayer.load(context, R.raw.sound_equipment_load1, DEFAULT_LOAD_PRIORITY);
        SOUND_EQUIPMENT_LOAD2 = soundPlayer.load(context, R.raw.sound_equipment_load2, DEFAULT_LOAD_PRIORITY);
        SOUND_EQUIPMENT_LOAD3 = soundPlayer.load(context, R.raw.sound_equipment_load3, DEFAULT_LOAD_PRIORITY);

        SOUND_GAME_CLEAR1 = soundPlayer.load(context, R.raw.sound_game_clear_idf, DEFAULT_LOAD_PRIORITY);
        SOUND_GAME_CLEAR2 = soundPlayer.load(context, R.raw.sound_game_clear_gign, DEFAULT_LOAD_PRIORITY);
        SOUND_GAME_CLEAR3 = soundPlayer.load(context, R.raw.sound_game_clear_gsg, DEFAULT_LOAD_PRIORITY);

        SOUND_GAME_GO1 = soundPlayer.load(context, R.raw.sound_game_go_phoenix, DEFAULT_LOAD_PRIORITY);
        SOUND_GAME_GO2 = soundPlayer.load(context, R.raw.sound_game_go_phoenix1, DEFAULT_LOAD_PRIORITY);
        SOUND_GAME_GO3 = soundPlayer.load(context, R.raw.sound_game_go_sas1, DEFAULT_LOAD_PRIORITY);
        SOUND_GAME_GO4 = soundPlayer.load(context, R.raw.sound_game_go_sas2, DEFAULT_LOAD_PRIORITY);
    }

    public void playSound(int soundID){
        soundPlayer.play(soundID, volume, volume, DEFAULT_PLAY_PRIORITY, 0, 1f);
    }

    public void playMusic(int rawID, @Nullable final MediaPlayer.OnCompletionListener completionListener, @Nullable final  MediaPlayer.OnPreparedListener preparedListener) {
        stopMusic();
        currentMusicPlayer = MediaPlayer.create(context, rawID);
        if(completionListener != null){
            currentMusicPlayer.setOnCompletionListener(completionListener);
        }

        currentMusicPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                if(completionListener != null) {
                    completionListener.onCompletion(mp);
                }
            }
        });

    }

    public MediaPlayer getCurrentMusicPlayer(){
       return currentMusicPlayer;
   }

    public void startMusic(){
        if(currentMusicPlayer != null){
            currentMusicPlayer.start();
        }
    }

    public boolean isMusicExists(){
        return currentMusicPlayer != null;
    }

    public boolean isMusicPlaying(){
        return currentMusicPlayer.isPlaying();
    }

    public void stopMusic(){
        if(currentMusicPlayer != null){
            currentMusicPlayer.stop();
            currentMusicPlayer.release();
            currentMusicPlayer = null;
        }
    }

    public void pauseMusic(){
        if(currentMusicPlayer != null){
            currentMusicPlayer.pause();
        }
    }

    public float getVolume(){
        return this.volume;
    }

    public void setVolume(float volume){
        if(volume < 0)
            throw new IllegalArgumentException("Volume cannot be negative.");
        if(volume > 1.0f){
            throw new IllegalArgumentException("Volume cannot be greater than one (1).");
        }
        this.volume = volume;
    }

    public void setVolumeFast(float volume){
        this.volume = volume;
    }

    public Context getContext(){
        return context;
    }
}

package dlsu.wirtec.tokhangapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

/**
 * Created by lyssa on 21/03/2017.
 */

public class SoundManager{
    public static final int DEFAULT_PRIORITY = 1;
    public static final int DEFAULT_MAX_SOUND_STREAMS = 5;

    private final Context context;

    private final SoundPool soundPlayer;
    private MediaPlayer currentMusicPlayer;

    private float volume;

    public SoundManager(Context context){
        this(context, 1.0f);
    }

    public SoundManager(Context context, float volume){
        this.context = context;
        this.volume = volume;

        soundPlayer = new SoundPool(DEFAULT_MAX_SOUND_STREAMS, AudioManager.STREAM_MUSIC, 0);
    }

    public void playSound(int soundID){
        soundPlayer.play(soundID, volume, volume, DEFAULT_PRIORITY, 0, 1f);
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

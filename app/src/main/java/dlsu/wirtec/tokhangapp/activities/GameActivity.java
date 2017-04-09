package dlsu.wirtec.tokhangapp.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Random;

import dlsu.wirtec.tokhangapp.R;
import dlsu.wirtec.tokhangapp.game.GameStateListener;
import dlsu.wirtec.tokhangapp.game.GameView;
import dlsu.wirtec.tokhangapp.game.Stage;
import dlsu.wirtec.tokhangapp.managers.GameManager;
import dlsu.wirtec.tokhangapp.managers.SoundManager;


public class GameActivity extends AppCompatActivity {

    private GameView gameView;
    private SoundManager soundManager;
    private Stage stage;

    private int[] musicIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        //SETS THE ACTIVITY TO FULLSCREENS
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        Stage stage = getIntent().getParcelableExtra("stage");
        gameView = new GameView(this, stage, gameStateListener);

        setContentView(gameView);

        soundManager = GameManager.getSoundManager();

        musicIDs = new int[]{
                R.raw.music_letsbecops,
                R.raw.music_soundofdapolice
        };

        this.stage = stage;


    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
        if(!soundManager.isMusicExists()){
            Random r = new Random(new Random().nextLong());
            soundManager.playMusic(musicIDs[r.nextInt(musicIDs.length)], null, null);
        }else if(soundManager.isMusicPlaying()){
            soundManager.startMusic();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();

        if(soundManager.isMusicExists()){
            soundManager.pauseMusic();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameView.destroyBitmaps();

        if(soundManager.isMusicExists()){
            soundManager.stopMusic();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameView.destroyBitmaps();
    }

    @Override
    public void onBackPressed() {
        return;
    }


    private GameStateListener gameStateListener = new GameStateListener() {
        @Override
        public void onPlayerDeath(int score) {
            setResult(NodeActivity.ACTIVITY_RESULT_DEATH);
            finish();
        }

        @Override
        public void onPlayerSuccess(int score) {
            Intent data = new Intent();
            data.putExtra(NodeActivity.RESULT_INTENT_SCORE, score);
            data.putExtra(NodeActivity.RESULT_INTENT_STAGEID, stage.ID);
            setResult(NodeActivity.ACTIVITY_RESULT_OKAY, data);

            finish();
        }
    };
}

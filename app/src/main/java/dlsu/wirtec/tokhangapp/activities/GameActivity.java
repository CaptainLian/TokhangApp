package dlsu.wirtec.tokhangapp.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import dlsu.wirtec.tokhangapp.R;
import dlsu.wirtec.tokhangapp.game.GameStateListener;
import dlsu.wirtec.tokhangapp.game.GameView;
import dlsu.wirtec.tokhangapp.game.Stage;
import dlsu.wirtec.tokhangapp.managers.GameManager;
import dlsu.wirtec.tokhangapp.managers.SoundManager;


public class GameActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        Stage stage = getIntent().getParcelableExtra("stage");
        gameView = new GameView(this, stage, gameStateListener);
        setContentView(gameView);

        //SETS THE ACTIVITY TO FULLSCREENS
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameView.destroyBitmaps();
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameView.destroyBitmaps();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
            setResult(NodeActivity.ACTIVITY_RESULT_OKAY, data);

            finish();
        }
    };
}

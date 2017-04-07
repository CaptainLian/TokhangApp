package dlsu.wirtec.tokhangapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import dlsu.wirtec.tokhangapp.game.GameView;
import dlsu.wirtec.tokhangapp.game.Stage;


public class GameActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        Stage stage = getIntent().getParcelableExtra("stage");
        gameView = new GameView(this, stage);
        setContentView(gameView);

        //SETS THE ACTIVITY TO FULLSCREEN
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
}

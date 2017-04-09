package dlsu.wirtec.tokhangapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;

import dlsu.wirtec.tokhangapp.R;
import dlsu.wirtec.tokhangapp.managers.GameManager;
import dlsu.wirtec.tokhangapp.managers.SoundManager;
import dlsu.wirtec.tokhangapp.ui.NewGameDialogFragment;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnStartNewGame, btnContinue, btnLeaderboard;

    private SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        GameManager.initialize(getBaseContext());

        /* Parse UI Components */
        btnStartNewGame = (ImageButton) findViewById(R.id.btn_startGame);
        btnContinue = (ImageButton) findViewById(R.id.btn_continue);
        btnLeaderboard = (ImageButton) findViewById(R.id.btn_leaderboard);

        /* Attach listeners to buttons */
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMap();
            }
        });
        btnStartNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GameManager.getGameManager().isSavedPlayerExist()){
                    NewGameDialogFragment dialog = new NewGameDialogFragment();
                    dialog.setDialogListener(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch(which){
                                // clicked yes
                                case DialogInterface.BUTTON_POSITIVE:
                                    goToCreatePlayer();
                                    break;
                            }//switch(which)
                        }//function onClick
                    });// setDialogListener
                    dialog.show(getFragmentManager(), NewGameDialogFragment.DIALOG_TAG_OVERWRITE_SAVE );
                }else{
                    goToCreatePlayer();
                }
            }
        });//setOnClickListener


        btnLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLeaderboard();
            }
        });

        soundManager = GameManager.getSoundManager();
    }

    private final void goToMap() {
        Intent i = new Intent(getBaseContext(), NodeActivity.class);
        startActivity(i);
        finish();
    }

    private final  void goToLeaderboard(){
        Intent i = new Intent(getBaseContext(), LeaderboardActivity.class);
        startActivity(i);
    }

    private final void goToCreatePlayer(){
        Intent i = new Intent(getBaseContext(), CreatePlayerActivity.class);
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(GameManager.getGameManager().isSavedPlayerExist()){
            btnContinue.setClickable(true);
            btnContinue.setImageResource(R.mipmap.menu_button_continue);
        }else{
            btnContinue.setClickable(false);
            btnContinue.setImageResource(0);
        }

        if(soundManager.isMusicExists() ){
            if(!soundManager.isMusicPlaying()){
                soundManager.startMusic();
            }
        }else{
            soundManager.playMusic(R.raw.music_heytdrags_trap_short, null, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        soundManager.pauseMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundManager.stopMusic();
    }
}//class

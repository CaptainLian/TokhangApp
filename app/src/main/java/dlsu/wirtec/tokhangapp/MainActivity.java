package dlsu.wirtec.tokhangapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    /* UI Components */

    private ViewGroup layoutSuperParent;
    //private TextView tvTitle;
    private Button btnStartNewGame, btnContinue, btnLeaderboard;

    private static final int[] BACKGROUND_IDS = {
        R.drawable.background_tokhang1,
            R.drawable.background_tokhang2
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        /* Parse UI Components */
        layoutSuperParent = (ViewGroup) findViewById(R.id.container_super_parent);
        //tvTitle = (TextView) findViewById(R.id.tv_title);
        btnStartNewGame = (Button) findViewById(R.id.btn_start_new_game);
        btnContinue = (Button) findViewById(R.id.btn_continue);
        btnLeaderboard = (Button) findViewById(R.id.btn_leaderboard);

        /* Process UI Components */
        Random random = new Random(new Random().nextLong());
        final int backgroundID = BACKGROUND_IDS[random.nextInt(BACKGROUND_IDS.length)];
        layoutSuperParent.setBackgroundResource(backgroundID);

        /* Attach listeners to buttons */
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Go to Continue Game Activity
            }
        });
        btnStartNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Go to New Game Activity

            }
        });
        btnLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), LeaderboardActivity.class);
                startActivity(i);
            }
        });
    }
}

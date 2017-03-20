package dlsu.wirtec.tokhangapp.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import dlsu.wirtec.tokhangapp.R;

public class MainActivity extends AppCompatActivity {

    /* UI Components */
    private ViewGroup layoutSuperParent;
    private TextView tvTitle;
    private ImageButton btnStartNewGame, btnContinue, btnLeaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        /* Parse UI Components */
        layoutSuperParent = (ViewGroup) findViewById(R.id.container_superParent);
        btnStartNewGame = (ImageButton) findViewById(R.id.btn_startGame);
        btnContinue = (ImageButton) findViewById(R.id.btn_continue);
        btnLeaderboard = (ImageButton) findViewById(R.id.btn_leaderboard);

        /* Attach listeners to buttons */
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Go to Continue Game Activity
                goToMap();

            }
        });
        btnStartNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Go to New Game Activity
                goToMap();
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

    private final void goToMap() {
        return;
    }
}

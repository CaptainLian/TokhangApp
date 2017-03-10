package dlsu.wirtec.tokhangapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /* UI Components */
    private ViewGroup layoutSuperParent;
    private TextView tvTitle;
    private Button btnStartNewGame, btnContinue, btnLeaderboard;

    private Typeface outrunFuture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        /* Parse UI Components */
        layoutSuperParent = (ViewGroup) findViewById(R.id.container_super_parent);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        btnStartNewGame = (Button) findViewById(R.id.btn_start_new_game);
        btnContinue = (Button) findViewById(R.id.btn_continue);
        btnLeaderboard = (Button) findViewById(R.id.btn_leaderboard);

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

        outrunFuture = Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/outrun_future.otf");
        tvTitle.setTypeface(outrunFuture);
    }

    private final void goToMap(){
        Intent i = new Intent(getBaseContext(), GameMapActivity.class);
        startActivity(i);
        finish();
    }
}

package dlsu.wirtec.tokhangapp;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import dlsu.wirtec.tokhangapp.database.ScoreDatabaseHelper;
import dlsu.wirtec.tokhangapp.ui.ScoreAdapter;

public class LeaderboardActivity extends AppCompatActivity {

    private FloatingActionButton btnBack;
    private RecyclerView rvPlayer;
    private ScoreDatabaseHelper scoreDatabase;
    private ScoreAdapter scoreAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_leaderboard);

        btnBack = (FloatingActionButton) findViewById(R.id.btn_back);
        rvPlayer = (RecyclerView) findViewById(R.id.rv_player);

        scoreDatabase = new ScoreDatabaseHelper(getBaseContext());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });// call setOnclickListener
    }//function onCreate

    @Override
    protected void onResume() {
        super.onResume();

        if(rvPlayer.getAdapter() == null){
            Cursor c = scoreDatabase.getAllScores();
            scoreAdapter = new ScoreAdapter(getBaseContext(), c);
            rvPlayer.setAdapter(scoreAdapter);
        }else{
            scoreAdapter.changeCursor(scoreDatabase.getAllScores());
        }//else
    }//function onResume
}// class LeaderboardActivity

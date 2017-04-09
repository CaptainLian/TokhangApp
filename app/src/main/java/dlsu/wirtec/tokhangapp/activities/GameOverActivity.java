package dlsu.wirtec.tokhangapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import dlsu.wirtec.tokhangapp.R;
import dlsu.wirtec.tokhangapp.database.ScoreDatabaseHelper;
import dlsu.wirtec.tokhangapp.logic.Player;
import dlsu.wirtec.tokhangapp.managers.GameManager;
import dlsu.wirtec.tokhangapp.ui.GameOverProceedDialogFragment;

public class GameOverActivity extends AppCompatActivity {

    private Button btnProceed;
    private ScoreDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_game_over);

        btnProceed = (Button) findViewById(R.id.btn_proceed);
        dbHelper = new ScoreDatabaseHelper(getBaseContext());

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameOverProceedDialogFragment proceedDialog = new GameOverProceedDialogFragment();
                proceedDialog.setClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Player p = GameManager.getGameManager().getPlayer();
                                dbHelper.addScore(p.getName(), p.getScore());
                                break;
                        }

                        Intent i = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                });

                proceedDialog.show(getSupportFragmentManager(), "tamad na ako");
            }
        });

    }
}

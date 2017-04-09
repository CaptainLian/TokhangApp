package dlsu.wirtec.tokhangapp.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import dlsu.wirtec.tokhangapp.R;
import dlsu.wirtec.tokhangapp.database.ScoreDatabaseHelper;
import dlsu.wirtec.tokhangapp.logic.Player;
import dlsu.wirtec.tokhangapp.managers.GameManager;
import dlsu.wirtec.tokhangapp.ui.GameOverProceedDialogFragment;

public class GameOverActivity extends AppCompatActivity {

    private Button btnProceed;
    private TextView tvScore;
    private ScoreDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_game_over);

        btnProceed = (Button) findViewById(R.id.btn_proceed);
        tvScore = (TextView) findViewById(R.id.tv_score);

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

                                DialogFragment dialogFragment = new ProceedDialogFragment();
                                dialogFragment.show(getSupportFragmentManager(), "wew");

                                break;
                            default:
                                Intent i = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(i);
                                finish();
                                break;
                        }//switch
                    }
                });

                proceedDialog.show(getSupportFragmentManager(), "tamad na ako");
            }
        });

        //katamad eeehhhhh
        tvScore.setText(getResources().getString(R.string.final_score) + ": " + GameManager.getGameManager().getPlayer().getScore());
    }

    public static class ProceedDialogFragment extends DialogFragment{
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getContext())
                    .setMessage("Would you like to view the leadboards?")
                    .setNegativeButton(R.string.no, null)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(getContext(), LeaderboardActivity.class);
                            Activity a = getActivity();
                            a.startActivity(i);
                            a.finish();
                        }
                    }).create();
        }
    }
}

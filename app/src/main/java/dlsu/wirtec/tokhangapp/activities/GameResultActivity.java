package dlsu.wirtec.tokhangapp.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import dlsu.wirtec.tokhangapp.R;
import dlsu.wirtec.tokhangapp.logic.Player;
import dlsu.wirtec.tokhangapp.managers.GameManager;

public class GameResultActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA_MONEY_RECEIVED = "GAME_RESULT_MONEY";
    public static final String INTENT_EXTRA_SCORE_RECEIVED = "GAME_RESULT_SCORE";

    private TextView tvName, tvMoneyReceived, tvScoreReceived, tvTotalMoney, tvTotalScore, tvTotalMoneyAdditional, tvTotalScoreAdditional;

    private Button btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_game_result);

        tvName = (TextView) findViewById(R.id.tv_name);
        tvMoneyReceived = (TextView) findViewById(R.id.tv_cash);
        tvScoreReceived = (TextView) findViewById(R.id.tv_score);
        tvTotalMoney = (TextView) findViewById(R.id.tv_totalAdditionMoney);
        tvTotalScore = (TextView) findViewById(R.id.tv_totalScore);
        tvTotalMoneyAdditional = (TextView) findViewById(R.id.tv_totalAdditionMoney);
        tvTotalScoreAdditional = (TextView) findViewById(R.id.tv_totalScoreAdditional);
        btnReturn = (Button) findViewById(R.id.btn_return);

        Resources r = getResources();

        Player p = GameManager.getGameManager().getPlayer();

        Intent i = getIntent();
        int money = i.getIntExtra(INTENT_EXTRA_MONEY_RECEIVED, 0);
        int score = i.getIntExtra(INTENT_EXTRA_SCORE_RECEIVED, 0);

        tvName.setText(p.getName());
        tvMoneyReceived.setText(r.getString(R.string.money) + ": " + money);
        tvScoreReceived.setText(r.getString(R.string.score) + ": " + score);

        tvTotalMoneyAdditional.setText(" +" + money);
        tvTotalScoreAdditional.setText(" +" + score);

        tvTotalMoney.setText(r.getString(R.string.total_money) + ": " + Integer.toString(p.getMoney() - money));
        tvTotalScore.setText(r.getString(R.string.total_score) + ": " + Integer.toString(p.getScore() - score));

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

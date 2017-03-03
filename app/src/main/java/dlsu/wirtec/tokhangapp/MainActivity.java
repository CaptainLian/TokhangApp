package dlsu.wirtec.tokhangapp;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Random;

import dlsu.wirtec.tokhangapp.R;

public class MainActivity extends AppCompatActivity {

    /* UI Components */

    private ViewGroup layoutSuperParent;
    //private TextView tvTitle;
    private Button btnStartNewGame, btnContinue, btnOptions;

    private static final int[] BACKGROUND_IDS = {
        R.drawable.background_tokhang1,
            R.drawable.background_tokhang2
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().hide();

        /* Parse UI Components */
        layoutSuperParent = (ViewGroup) findViewById(R.id.container_super_parent);
        //tvTitle = (TextView) findViewById(R.id.tv_title);
        btnStartNewGame = (Button) findViewById(R.id.btn_start_new_game);
        btnContinue = (Button) findViewById(R.id.btn_continue);
        btnOptions = (Button) findViewById(R.id.btn_options);

        /* Process UI Components */
        Random random = new Random(new Random().nextLong());
        final int backgroundID = BACKGROUND_IDS[random.nextInt(BACKGROUND_IDS.length)];
        layoutSuperParent.setBackgroundResource(backgroundID);

        /* Attach listeners to buttons */
        btnStartNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Go to New Game Activity

            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Go to Continue Game Activity
            }
        });
        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Go to Options Activity
            }
        });
    }
}

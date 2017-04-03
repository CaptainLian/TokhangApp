package dlsu.wirtec.tokhangapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import dlsu.wirtec.tokhangapp.R;
import dlsu.wirtec.tokhangapp.managers.GameManager;

public class CreatePlayerActivity extends AppCompatActivity {

    private Button btnProceed;
    private EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_create_player);

        btnProceed = (Button) findViewById(R.id.btn_proceed);
        etName = (EditText) findViewById(R.id.et_name);


        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameManager gameManager = GameManager.getGameManager();

                gameManager.createNewPlayerOverwrite(etName.getText().toString());

                Intent i = new Intent(getBaseContext(), NodeActivity.class);
                startActivity(i);
            }
        });


    }
}

package dlsu.wirtec.tokhangapp.activities;

import android.content.Intent;
import android.media.MediaCodec;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

import dlsu.wirtec.tokhangapp.R;
import dlsu.wirtec.tokhangapp.managers.GameManager;

public class CreatePlayerActivity extends AppCompatActivity {

    public static final Pattern SPACES = Pattern.compile("\\s+");

    private EditText etName;
    private Button btnProceed, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_create_player);

        etName = (EditText) findViewById(R.id.et_name);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnProceed = (Button) findViewById(R.id.btn_proceed);

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();

                if(SPACES.matcher(name).replaceAll("").length() == 0){
                    etName.setError("Name cannot be empty.");
                }else{
                    GameManager gameManager = GameManager.getGameManager();
                    gameManager.createNewPlayerOverwrite(etName.getText().toString());

                    Intent i = new Intent(getBaseContext(), NodeActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

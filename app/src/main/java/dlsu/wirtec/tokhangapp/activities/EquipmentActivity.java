package dlsu.wirtec.tokhangapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import dlsu.wirtec.tokhangapp.R;
import dlsu.wirtec.tokhangapp.logic.Gun;
import dlsu.wirtec.tokhangapp.logic.Player;
import dlsu.wirtec.tokhangapp.managers.GameManager;
import dlsu.wirtec.tokhangapp.managers.GunManager;
import dlsu.wirtec.tokhangapp.ui.GunIconAdapter;

public class EquipmentActivity extends AppCompatActivity {

    private Button btnReturn;
    private ImageButton btnDefault, btnRifle, btnShotgun, btnSniper, btnRocket;
    private ImageButton[] buttons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_equipment);

        GunManager gunManager = GameManager.getGunManager();

        View.OnClickListener gunListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };

        btnDefault = (ImageButton) findViewById(R.id.btn_default);
        btnRifle = (ImageButton) findViewById(R.id.btn_rifle);
        btnShotgun = (ImageButton) findViewById(R.id.btn_shotgun);
        btnSniper = (ImageButton) findViewById(R.id.btn_sniper);
        btnRocket = (ImageButton) findViewById(R.id.btn_rocket);
        btnReturn = (Button) findViewById(R.id.btn_return);

        btnDefault.setTag(new GunIconAdapter(gunManager.PISTOL, R.drawable.defaultgun));
        btnRifle.setTag(new GunIconAdapter(gunManager.RIFLE, R.drawable.brrrrt));
        btnShotgun.setTag(new GunIconAdapter(gunManager.SHOTGUN, R.drawable.sprak));
        btnSniper.setTag(new GunIconAdapter(gunManager.SNIPER, R.drawable.pew));
        btnRocket.setTag(new GunIconAdapter(gunManager.ROCKET, R.drawable.booom));

        btnDefault.setOnClickListener(gunListener);
        btnRifle.setOnClickListener(gunListener);
        btnShotgun.setOnClickListener(gunListener);
        btnSniper.setOnClickListener(gunListener);
        btnRocket.setOnClickListener(gunListener);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttons = new ImageButton[]{btnRifle, btnShotgun, btnSniper, btnRocket};
    }

    @Override
    protected void onResume() {
        super.onResume();
        Player p = GameManager.getGameManager().getPlayer();

        for(ImageButton b: buttons){
            GunIconAdapter gia = (GunIconAdapter) b.getTag();
            if(p.isGunOwned(gia.gun)){
                b.setBackgroundResource(gia.gunIcon);
            }else{
                b.setBackgroundResource(R.drawable.icon_equipment_locked);
            }
        }
    }
}

package dlsu.wirtec.tokhangapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Random;

import dlsu.wirtec.tokhangapp.R;
import dlsu.wirtec.tokhangapp.logic.Gun;
import dlsu.wirtec.tokhangapp.logic.Player;
import dlsu.wirtec.tokhangapp.managers.GameManager;
import dlsu.wirtec.tokhangapp.managers.GunManager;
import dlsu.wirtec.tokhangapp.managers.SoundManager;
import dlsu.wirtec.tokhangapp.ui.EquipmentGunIconAdapter;

public class EquipmentActivity extends AppCompatActivity {

    private Button btnReturn;
    private ImageButton btnDefault, btnRifle, btnShotgun, btnSniper, btnRocket;
    private ImageView ivLoadout;
    private ImageButton[] buttons;

    View.OnClickListener gunClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EquipmentGunIconAdapter egia = (EquipmentGunIconAdapter) v.getTag();

            GameManager.getGameManager().getPlayer().equipGun(egia.GUN);
            ivLoadout.setImageResource(egia.DRAWABLE_ID_GUN_UNLOCKED);

            SoundManager soundManager = GameManager.getSoundManager();
            Random r = new Random(new Random().nextLong());

            int rand = r.nextInt(3);
            switch(rand){
                default:
                    soundManager.playSound(soundManager.SOUND_EQUIPMENT_LOAD1);
                    break;
                case 1:
                    soundManager.playSound(soundManager.SOUND_EQUIPMENT_LOAD2);
                    break;
                case 2:
                    soundManager.playSound(soundManager.SOUND_EQUIPMENT_LOAD3);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_equipment);

        GunManager gunManager = GameManager.getGunManager();

        btnDefault = (ImageButton) findViewById(R.id.btn_default);
        btnRifle = (ImageButton) findViewById(R.id.btn_rifle);
        btnShotgun = (ImageButton) findViewById(R.id.btn_shotgun);
        btnSniper = (ImageButton) findViewById(R.id.btn_sniper);
        btnRocket = (ImageButton) findViewById(R.id.btn_rocket);
        btnReturn = (Button) findViewById(R.id.btn_return);

        ivLoadout = (ImageView) findViewById(R.id.iv_icon_loadout);

        btnDefault.setTag(new EquipmentGunIconAdapter(gunManager.PISTOL, R.drawable.icon_weapon_pistol));
        btnRifle.setTag(new EquipmentGunIconAdapter(gunManager.RIFLE, R.drawable.icon_weapon_rifle));
        btnShotgun.setTag(new EquipmentGunIconAdapter(gunManager.SHOTGUN, R.drawable.icon_weapon_shotgun));
        btnSniper.setTag(new EquipmentGunIconAdapter(gunManager.SNIPER, R.drawable.icon_weapon_sniper));
        btnRocket.setTag(new EquipmentGunIconAdapter(gunManager.ROCKET, R.drawable.icon_weapon_rocket));

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttons = new ImageButton[]{btnDefault, btnRifle, btnShotgun, btnSniper, btnRocket};
    }

    @Override
    protected void onResume() {
        super.onResume();
        Player p = GameManager.getGameManager().getPlayer();

        Gun equppedGun = p.getEquippedGun();
        for(ImageButton b: buttons){
            final EquipmentGunIconAdapter gia = (EquipmentGunIconAdapter) b.getTag();
            if(p.isGunOwned(gia.GUN)){
                b.setImageResource(gia.DRAWABLE_ID_GUN_UNLOCKED);
                b.setOnClickListener(gunClickListener);
            }else{
                b.setImageResource(R.drawable.icon_equipment_locked);
            }

            if(gia.GUN == equppedGun){
                ivLoadout.setImageResource(gia.DRAWABLE_ID_GUN_UNLOCKED);
            }
        }
    }
}

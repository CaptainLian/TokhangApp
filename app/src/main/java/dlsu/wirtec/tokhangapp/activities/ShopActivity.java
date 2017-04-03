package dlsu.wirtec.tokhangapp.activities;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import dlsu.wirtec.tokhangapp.R;
import dlsu.wirtec.tokhangapp.logic.Gun;
import dlsu.wirtec.tokhangapp.logic.GunSound;
import dlsu.wirtec.tokhangapp.logic.Player;
import dlsu.wirtec.tokhangapp.logic.SimpleGunSound;
import dlsu.wirtec.tokhangapp.managers.GameManager;
import dlsu.wirtec.tokhangapp.managers.GunManager;
import dlsu.wirtec.tokhangapp.managers.SoundManager;
import dlsu.wirtec.tokhangapp.ui.ShopDialogFragment;

public class ShopActivity extends AppCompatActivity {

    private ImageButton btnGunAuto, btnGunShotgun, btnGunSniper, btnGunRocket;
    private Button btnReturn;
    private TextView tvCoinAmount;

    private SoundManager soundManager;
    private GunManager gunManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_shop);

        /* shop view initialization */
        btnGunAuto = (ImageButton) findViewById(R.id.btn_rifle);
        btnGunShotgun = (ImageButton) findViewById(R.id.btn_shotgun);
        btnGunSniper = (ImageButton) findViewById(R.id.btn_sniper);
        btnGunRocket = (ImageButton) findViewById(R.id.btn_rocket);
        btnReturn = (Button) findViewById(R.id.btn_return);

        tvCoinAmount = (TextView) findViewById(R.id.tv_money_amount);

        /* shop view initialization end */

        soundManager = GameManager.getSoundManager();
        gunManager = GameManager.getGunManager();

        btnGunAuto.setTag(gunManager.RIFLE);
        btnGunShotgun.setTag(gunManager.SHOTGUN);
        btnGunSniper.setTag(gunManager.SNIPER);
        btnGunRocket.setTag(gunManager.ROCKET);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gun g = (Gun) v.getTag();

                GunSound sound = g.getGunSound();
                if(sound != null){
                    int onViewID = sound.getOnShopViewSoundID();
                    if(onViewID != SimpleGunSound.ID_NONE){
                        soundManager.playSound(onViewID);
                    }
                }
                Toast.makeText(getBaseContext(), "Gun clicked: " + g.getName(), Toast.LENGTH_SHORT).show();
            }
        };
        btnGunAuto.setOnClickListener(listener);
        btnGunShotgun.setOnClickListener(listener);
        btnGunSniper.setOnClickListener(listener);
        btnGunRocket.setOnClickListener(listener);


        View.OnLongClickListener longListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Gun g = (Gun) v.getTag();
                //Toast.makeText(getBaseContext(), "Gun long clicked: " + g.getName(), Toast.LENGTH_SHORT).show();

                Bundle args = new Bundle();
                args.putString(ShopDialogFragment.ARGUMENT_GUN_NAME, g.getName());
                args.putString(ShopDialogFragment.ARGUMENT_GUN_DESCRIPTION, g.getDescription());
                args.putInt(ShopDialogFragment.ARGUMENT_GUN_COST, g.getCost());

                ShopDialogFragment dialog = new ShopDialogFragment();
                dialog.setArguments(args);
                dialog.setDialogListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Player p = GameManager.getGameManager().getPlayer();
                                SoundManager soundManager = GameManager.getSoundManager();
                                if(p.purchaseGun(g)){
                                    soundManager.playSound(soundManager.SOUND_SHOP_PURCHASE1);
                                }else{
                                    soundManager.playSound(soundManager.SOUND_MENU_ERROR1);
                                }// can player purchase gun
                                break;
                        }//switch
                    }//function onClick
                });//setDialogListener
                dialog.show(getSupportFragmentManager(), ShopDialogFragment.DIALOG_TAG_SHOP);
                return true;
            }
        };
        btnGunAuto.setOnLongClickListener(longListener);
        btnGunShotgun.setOnLongClickListener(longListener);
        btnGunSniper.setOnLongClickListener(longListener);
        btnGunRocket.setOnLongClickListener(longListener);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvCoinAmount.setText(Integer.toString(GameManager.getGameManager().getPlayer().getMoney()));
    }
}

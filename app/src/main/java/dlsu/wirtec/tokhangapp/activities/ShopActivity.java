package dlsu.wirtec.tokhangapp.activities;

import android.content.DialogInterface;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import dlsu.wirtec.tokhangapp.R;
import dlsu.wirtec.tokhangapp.logic.Gun;
import dlsu.wirtec.tokhangapp.logic.GunSound;
import dlsu.wirtec.tokhangapp.logic.Player;
import dlsu.wirtec.tokhangapp.logic.SimpleGunSound;
import dlsu.wirtec.tokhangapp.managers.GameManager;
import dlsu.wirtec.tokhangapp.managers.GunManager;
import dlsu.wirtec.tokhangapp.managers.SoundManager;
import dlsu.wirtec.tokhangapp.ui.ShopDialogFragment;
import dlsu.wirtec.tokhangapp.ui.ShopGunIconAdapter;

public class ShopActivity extends AppCompatActivity {

    private ImageButton btnGunAuto, btnGunShotgun, btnGunSniper, btnGunRocket;
    private ImageButton[] buttons;

    private Button btnReturn;
    private TextView tvCoinAmount;
    private View.OnLongClickListener longListener;

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

        GunManager gunManager = GameManager.getGunManager();

        btnGunAuto.setTag(new ShopGunIconAdapter(
                gunManager.RIFLE,
                R.drawable.icon_weapon_rifle,
                R.drawable.icon_weapon_sold_rifle
        ));
        btnGunShotgun.setTag(new ShopGunIconAdapter(
                gunManager.SHOTGUN,
                R.drawable.icon_weapon_shotgun,
                R.drawable.icon_weapon_sold_shotgun

        ));
        btnGunSniper.setTag(new ShopGunIconAdapter(
                gunManager.SNIPER,
                R.drawable.icon_weapon_sniper,
                R.drawable.icon_weapon_sold_sniper
        ));
        btnGunRocket.setTag(new ShopGunIconAdapter(
                gunManager.ROCKET,
                R.drawable.icon_weapon_rocket,
                R.drawable.icon_weapon_sold_rocket

        ));

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Gun g =((ShopGunIconAdapter) v.getTag()).GUN;

                SoundManager soundManager = GameManager.getSoundManager();
                GunSound sound = g.getGunSound();

                if(sound != null){
                    int onViewID = sound.getOnShopViewSoundID();
                    if(onViewID != SimpleGunSound.ID_NONE){
                        soundManager.playSound(onViewID);
                    }
                }
            }
        };
        btnGunAuto.setOnClickListener(listener);
        btnGunShotgun.setOnClickListener(listener);
        btnGunSniper.setOnClickListener(listener);
        btnGunRocket.setOnClickListener(listener);

        longListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final ShopGunIconAdapter sgia = (ShopGunIconAdapter) v.getTag();
                //Toast.makeText(getBaseContext(), "Gun long clicked: " + g.getName(), Toast.LENGTH_SHORT).show();

                Bundle args = new Bundle();
                args.putString(ShopDialogFragment.ARGUMENT_GUN_NAME, sgia.GUN.getName());
                args.putString(ShopDialogFragment.ARGUMENT_GUN_DESCRIPTION, sgia.GUN.getDescription());
                args.putInt(ShopDialogFragment.ARGUMENT_GUN_COST, sgia.GUN.getCost());

                ShopDialogFragment dialog = new ShopDialogFragment();
                dialog.setArguments(args);
                dialog.setDialogListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Player p = GameManager.getGameManager().getPlayer();
                                SoundManager soundManager = GameManager.getSoundManager();
                                if(p.purchaseGun(sgia.GUN)){
                                    soundManager.playSound(soundManager.SOUND_SHOP_PURCHASE1);

                                    tvCoinAmount.setText(Integer.toString(GameManager.getGameManager().getPlayer().getMoney()));
                                }else{
                                    soundManager.playSound(soundManager.SOUND_MENU_ERROR1);
                                }// can player purchase GUN
                                break;
                        }//switch
                    }//function onClick
                });//setDialogListener
                dialog.show(getSupportFragmentManager(), ShopDialogFragment.DIALOG_TAG_SHOP);
                return true;
            }
        };

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvCoinAmount.setText(Integer.toString(GameManager.getGameManager().getPlayer().getMoney()));

        buttons = new ImageButton[]{
                btnGunAuto,
                btnGunShotgun,
                btnGunSniper,
                btnGunRocket
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        View.OnLongClickListener longListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                final ShopGunIconAdapter sgia = (ShopGunIconAdapter) v.getTag();
                //Toast.makeText(getBaseContext(), "Gun long clicked: " + g.getName(), Toast.LENGTH_SHORT).show();

                Bundle args = new Bundle();
                args.putString(ShopDialogFragment.ARGUMENT_GUN_NAME, sgia.GUN.getName());
                args.putString(ShopDialogFragment.ARGUMENT_GUN_DESCRIPTION, sgia.GUN.getDescription());
                args.putInt(ShopDialogFragment.ARGUMENT_GUN_COST, sgia.GUN.getCost());

                ShopDialogFragment dialog = new ShopDialogFragment();
                dialog.setArguments(args);
                dialog.setDialogListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Player p = GameManager.getGameManager().getPlayer();
                                SoundManager soundManager = GameManager.getSoundManager();
                                if(p.purchaseGun(sgia.GUN)){
                                    soundManager.playSound(soundManager.SOUND_SHOP_PURCHASE1);
                                    ImageButton b = (ImageButton) v;
                                    b.setOnLongClickListener(null);
                                    b.setImageResource(sgia.DRAWABLE_ID_SOLD);
                                    tvCoinAmount.setText(Integer.toString(GameManager.getGameManager().getPlayer().getMoney()));
                                }else{
                                    soundManager.playSound(soundManager.SOUND_MENU_ERROR1);
                                }// can player purchase GUN
                                break;
                        }//switch
                    }//function onClick
                });//setDialogListener
                dialog.show(getSupportFragmentManager(), ShopDialogFragment.DIALOG_TAG_SHOP);
                return true;
            }
        };

        Player p = GameManager.getGameManager().getPlayer();

        for(ImageButton b: buttons){
            ShopGunIconAdapter sgia = (ShopGunIconAdapter) b.getTag();
            if(p.isGunOwned(sgia.GUN)){
                b.setImageResource(sgia.DRAWABLE_ID_SOLD);
                b.setOnLongClickListener(null);
            }else{
                b.setImageResource(sgia.DRAWABLE_ID_PURCHASED);
                b.setOnLongClickListener(longListener);
            }
        }
    }
}

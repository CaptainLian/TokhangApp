package dlsu.wirtec.tokhangapp.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import dlsu.wirtec.tokhangapp.R;
import dlsu.wirtec.tokhangapp.game.House;
import dlsu.wirtec.tokhangapp.game.Stage;
import dlsu.wirtec.tokhangapp.logic.Gun;
import dlsu.wirtec.tokhangapp.logic.GunSound;
import dlsu.wirtec.tokhangapp.logic.SimpleGunSound;
import dlsu.wirtec.tokhangapp.managers.GameManager;
import dlsu.wirtec.tokhangapp.managers.GunManager;
import dlsu.wirtec.tokhangapp.managers.SoundManager;
import dlsu.wirtec.tokhangapp.ui.ShopDialogFragment;

public class MapActivity extends AppCompatActivity{

    public static final int ACTIVITY_REQUEST_CODE_GAME = 0;

    private ViewFlipper viewFlipper;
    private boolean isMapView = true;

    /* nodes */
    private ImageView drawArea;
    private TextView tvSector1, tvSector2, tvSector3;
    private Button btnShop;

    /* shop */
    private ImageButton btnGunPistol, btnGunAuto, btnGunShotgun, btnGunSniper, btnGunRocket;
    private Button btnReturn;

    private SoundManager soundManager;
    private GunManager gunManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_map);

        viewFlipper = (ViewFlipper) findViewById(R.id.container_viewFlipper);

        View.OnClickListener toggleClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipView();
            }
        };

        /* node view initalization */
        drawArea = (ImageView) findViewById(R.id.iv_drawArea);

        tvSector1 = (TextView) findViewById(R.id.tv_sector1);
        tvSector2 = (TextView) findViewById(R.id.tv_sector2);
        tvSector3 = (TextView) findViewById(R.id.tv_sector3);

        btnShop = (Button) findViewById(R.id.btn_shop);

        tvSector1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Sector 1", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getBaseContext(), GameActivity.class);

                String[] houseNames = {
                        House.BUNGALOW,
                        House.SKWATER,
                        House.SKWATER
                };
                Stage stage = new Stage("Sector1", 4, houseNames, 900);
                i.putExtra(Stage.EXTRA_STAGE, stage);
                //startActivityForResult(i, ACTIVITY_REQUEST_CODE_GAME);
                startActivity(i);
                finish();
            }
        });
        tvSector2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Sector 2", Toast.LENGTH_SHORT).show();
            }
        });
        tvSector3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Sector 3", Toast.LENGTH_SHORT).show();
            }
        });

        btnShop.setOnClickListener(toggleClickListener);

        drawArea.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                drawArea.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                final Bitmap bitmap = Bitmap.createBitmap(drawArea.getWidth(), drawArea.getHeight(), Bitmap.Config.ARGB_8888);
                final Canvas c = new Canvas(bitmap);
                final Paint paint = new Paint();

                paint.setStrokeWidth(12f);
                paint.setColor(Color.BLACK);
                paint.setAntiAlias(true);


                final float sector1MidX = tvSector1.getX() + tvSector1.getWidth()/2,
                        sector1MidY = tvSector1.getY() + tvSector1.getHeight()/2;
                final float sector2MidX = tvSector2.getX() + tvSector2.getWidth()/2,
                        sector2MidY = tvSector2.getY() + tvSector2.getHeight()/2;
                final float sector3MidX = tvSector3.getX() + tvSector3.getWidth()/2,
                        sector3MidY = tvSector3.getY() + tvSector3.getHeight()/2;

                c.drawLine(sector2MidX, sector2MidY, sector1MidX, sector1MidY, paint);
                c.drawLine(sector2MidX, sector2MidY, sector3MidX, sector3MidY, paint);

                drawArea.setImageBitmap(bitmap);
            }
        });
        /* node view initialization end */


        /* shop view initialization */


        btnGunPistol = (ImageButton) findViewById(R.id.btn_default);
        btnGunAuto = (ImageButton) findViewById(R.id.btn_auto);
        btnGunShotgun = (ImageButton) findViewById(R.id.btn_shotgun);
        btnGunSniper = (ImageButton) findViewById(R.id.btn_sniper);
        btnGunRocket = (ImageButton) findViewById(R.id.btn_rocket);
        btnReturn = (Button) findViewById(R.id.btn_back);
        /* shop view initialization end */

        soundManager = GameManager.getSoundManager();
        gunManager = GameManager.getGunManager();

        btnGunPistol.setTag(gunManager.PISTOL);
        btnGunAuto.setTag(gunManager.RIFLE);
        btnGunShotgun.setTag(gunManager.SHOTGUN);
        btnGunSniper.setTag(gunManager.SNIPER);
        btnGunRocket.setTag(gunManager.ROCKET);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gun g = (Gun) v.getTag();
                if(gunClickedListener != null){
                    gunClickedListener.onGunClicked(g);
                }
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
        btnGunPistol.setOnClickListener(listener);
        btnGunAuto.setOnClickListener(listener);
        btnGunShotgun.setOnClickListener(listener);
        btnGunSniper.setOnClickListener(listener);
        btnGunRocket.setOnClickListener(listener);

        btnReturn.setOnClickListener(toggleClickListener);

        View.OnLongClickListener longListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Gun g = (Gun) v.getTag();
                if(gunClickedListener != null){
                    gunLongClickedListener.onGunLongClicked(g);
                }
                //Toast.makeText(getBaseContext(), "Gun long clicked: " + g.getName(), Toast.LENGTH_SHORT).show();

                Bundle args = new Bundle();
                args.putString(ShopDialogFragment.ARGUMENT_GUN_NAME, g.getName());
                args.putString(ShopDialogFragment.ARGUMENT_GUN_DESCRIPTION, g.getDescription());
                args.putInt(ShopDialogFragment.ARGUMENT_GUN_COST, g.getCost());

                ShopDialogFragment dialog = new ShopDialogFragment();
                dialog.setArguments(args);
                dialog.show(getSupportFragmentManager(), ShopDialogFragment.DIALOG_TAG_SHOP);
                return true;
            }
        };

        btnGunPistol.setOnLongClickListener(longListener);
        btnGunAuto.setOnLongClickListener(longListener);
        btnGunShotgun.setOnLongClickListener(longListener);
        btnGunSniper.setOnLongClickListener(longListener);
        btnGunRocket.setOnLongClickListener(longListener);
    }

    private final void flipView(){
        if(isMapView){
            viewFlipper.showNext();
        }else{
            viewFlipper.showPrevious();
        }
        isMapView = !isMapView;
    }

    private GunClickedListener gunClickedListener;
    private GunLongClickedListener gunLongClickedListener;

    public void setOnGunClickedListener(GunClickedListener listener){
        this.gunClickedListener = listener;
    }

    public void setOnGunLongClickedListener(GunLongClickedListener listener){
        this.gunLongClickedListener = listener;
    }

    public interface GunClickedListener {
        public void onGunClicked(Gun g);
    }
    public interface GunLongClickedListener {
        public void onGunLongClicked(Gun g);
    }

}//Class MapActivity

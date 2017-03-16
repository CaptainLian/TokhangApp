package dlsu.wirtec.tokhangapp;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ViewFlipper;

import dlsu.wirtec.tokhangapp.ui.MapPerspective;
import dlsu.wirtec.tokhangapp.ui.QuitDialogFragment;
import dlsu.wirtec.tokhangapp.ui.ShopPerspective;

public class GameMapActivity extends AppCompatActivity implements Animation.AnimationListener, NavigationView.OnNavigationItemSelectedListener {

    private ViewFlipper viewFlipper;

    private FloatingActionButton fab;
    private Button btnBack;

    /* Flipper Perspectives */
    MapPerspective map;
    ShopPerspective shop;

    /* animation stuffs */
    private boolean isMapShown = true;
    private boolean isFlipping = false;
    private static final long ANIMATION_DURATION = 300;

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_map);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawer = (DrawerLayout) findViewById(R.id.container_map);

        viewFlipper = (ViewFlipper) findViewById(R.id.container_viewFlipper);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        btnBack = (Button) findViewById(R.id.btn_back);

        map = new MapPerspective(this);
        shop = new ShopPerspective(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipPerspective();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipPerspective();
            }
        });

    }//onCreate

    private final void flipPerspective(){
        if(!isFlipping){
            float firstFromX, firstToX, firstFromY, firstToY,
                    secondFromX, secondToX, secondFromY, secondToY;

            TranslateAnimation first = null;
            TranslateAnimation second = null;

            if(isMapShown){//goUp
                firstFromX = 0f;
                firstFromY = 0f;
                firstToX = 0f;
                firstToY = -1f;

                secondFromX = 0f;
                secondFromY = 1f;
                secondToX = 0f;
                secondToY = 0f;

                first = new TranslateAnimation(
                        TranslateAnimation.RELATIVE_TO_PARENT, firstFromX,
                        TranslateAnimation.RELATIVE_TO_PARENT, firstToX,
                        TranslateAnimation.RELATIVE_TO_PARENT, firstFromY,
                        TranslateAnimation.RELATIVE_TO_PARENT, firstToY
                );
                first.setDuration(ANIMATION_DURATION);

                second = new TranslateAnimation(
                        TranslateAnimation.RELATIVE_TO_PARENT, secondFromX,
                        TranslateAnimation.RELATIVE_TO_PARENT, secondToX,
                        TranslateAnimation.RELATIVE_TO_PARENT, secondFromY,
                        TranslateAnimation.RELATIVE_TO_PARENT, secondToY
                );
                second.setDuration(ANIMATION_DURATION);

                viewFlipper.setInAnimation(second);
                viewFlipper.setOutAnimation(first);

                viewFlipper.showNext();
            }else{//goDown
                firstFromX = 0f;
                firstFromY = -1f;
                firstToX = 0f;
                firstToY = 0f;

                secondFromX = 0f;
                secondFromY = 0f;
                secondToX = 0f;
                secondToY = 1f;

                first = new TranslateAnimation(
                        TranslateAnimation.RELATIVE_TO_PARENT, firstFromX,
                        TranslateAnimation.RELATIVE_TO_PARENT, firstToX,
                        TranslateAnimation.RELATIVE_TO_PARENT, firstFromY,
                        TranslateAnimation.RELATIVE_TO_PARENT, firstToY
                );
                first.setDuration(ANIMATION_DURATION);

                second = new TranslateAnimation(
                        TranslateAnimation.RELATIVE_TO_PARENT, secondFromX,
                        TranslateAnimation.RELATIVE_TO_PARENT, secondToX,
                        TranslateAnimation.RELATIVE_TO_PARENT, secondFromY,
                        TranslateAnimation.RELATIVE_TO_PARENT, secondToY
                );
                second.setDuration(ANIMATION_DURATION);

                viewFlipper.setInAnimation(first);
                viewFlipper.setOutAnimation(second);

                viewFlipper.showPrevious();
            }//isMapShown

            first.setAnimationListener(this);
            second.setAnimationListener(this);

            isMapShown = !isMapShown;
        }//isFlipping
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        map.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        isFlipping = true;
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        isFlipping = false;
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        return;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        final int id = item.getItemId();

        switch (id){
            case R.id.nav_statistics:{

            }break;

            case R.id.nav_equipment: {

            }break;

            case R.id.nav_quit: {
                QuitDialogFragment dialog = new QuitDialogFragment();
                dialog.show(getSupportFragmentManager(), "MapActivity");
            }break;

            case R.id.nav_save: {

            }break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

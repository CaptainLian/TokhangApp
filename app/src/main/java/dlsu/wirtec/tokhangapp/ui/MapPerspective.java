package dlsu.wirtec.tokhangapp.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import dlsu.wirtec.tokhangapp.GameMapActivity;
import dlsu.wirtec.tokhangapp.R;

/**
 *
 * Created by lyssa on 10/03/2017.
 */

public class MapPerspective implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    private ImageButton btnMakati, btnPaco;


    public MapPerspective(GameMapActivity activity){
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);

        drawer = (DrawerLayout) activity.findViewById(R.id.container_map);
        navigationView = (NavigationView) activity.findViewById(R.id.nav_view);

        btnMakati = (ImageButton) activity.findViewById(R.id.btn_makati);
        btnPaco = (ImageButton) activity.findViewById(R.id.btn_paco);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);

        new LineView(activity.getBaseContext(), 0, 0, 50, 50);
        new LineView(activity.getBaseContext(), btnMakati.getX(), btnMakati.getY(), btnPaco.getX(), btnPaco.getY());

        btnMakati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnPaco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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

            }break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();

    }

    public boolean isDrawerOpen(){
        return drawer.isDrawerOpen(GravityCompat.START);
    }

    public void closeDrawer(){
        drawer.closeDrawer(GravityCompat.START);
    }
}

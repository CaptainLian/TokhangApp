package dlsu.wirtec.tokhangapp.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

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


    public MapPerspective(final GameMapActivity activity){
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

        Bitmap bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(bitmap);

        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setAntiAlias(true);
        p.setDither(true);

        c.drawLine(btnMakati.getX(), btnMakati.getY(), btnPaco.getX(), btnPaco.getY(), p);

        btnMakati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity.getBaseContext(), "Makati Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        btnPaco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity.getBaseContext(), "Paco Clicked", Toast.LENGTH_SHORT).show();
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

            case R.id.nav_save: {

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

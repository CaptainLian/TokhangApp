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
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import dlsu.wirtec.tokhangapp.GameMapActivity;
import dlsu.wirtec.tokhangapp.R;

/**
 *
 * Created by lyssa on 10/03/2017.
 */

public class MapPerspective implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ImageView drawArea;

    private Paint paint;
    private ImageButton btnMakati, btnPaco;


    public MapPerspective(final GameMapActivity activity){
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);

        drawer = (DrawerLayout) activity.findViewById(R.id.container_map);
        navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
        drawArea = (ImageView) activity.findViewById(R.id.id_map_drawArea);

        btnMakati = (ImageButton) activity.findViewById(R.id.btn_makati);
        btnPaco = (ImageButton) activity.findViewById(R.id.btn_paco);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(12f);

        btnMakati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity.getBaseContext(), "salsalan na ser!!!!", Toast.LENGTH_SHORT).show();
            }
        });
        btnPaco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity.getBaseContext(), "fingeran na ser!!!!", Toast.LENGTH_SHORT).show();
            }
        });

        drawArea.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                drawArea.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int[] pacoPoint = new int[2];
                int[] makatiPoint = new int[2];

                btnPaco.getLocationInWindow(pacoPoint);
                btnMakati.getLocationInWindow(makatiPoint);

                Bitmap bitmap = Bitmap.createBitmap(drawArea.getWidth(), drawArea.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(bitmap);

                c.drawLine(pacoPoint[0], pacoPoint[1], makatiPoint[0], makatiPoint[1], paint);

                drawArea.setImageBitmap(bitmap);
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

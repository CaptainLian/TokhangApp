package dlsu.wirtec.tokhangapp.activities;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import dlsu.wirtec.tokhangapp.R;

public class MapActivity extends AppCompatActivity{

    private ViewFlipper viewFlipper;
    private boolean isMapView = true;


    /* nodes */
    private ImageView drawArea;
    private TextView tvSector1, tvSector2, tvSector3;
    private Button btnShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_map);

        viewFlipper = (ViewFlipper) findViewById(R.id.container_viewFlipper);
    }

    private final void flipView(){
        if(isMapView){
            viewFlipper.showNext();
        }else{
            viewFlipper.showPrevious();
        }
        isMapView = !isMapView;
    }


    private final void initializeNodeView(){
        drawArea = (ImageView) findViewById(R.id.iv_drawArea);

        tvSector1 = (TextView) findViewById(R.id.tv_sector1);
        tvSector2 = (TextView) findViewById(R.id.tv_sector2);
        tvSector3 = (TextView) findViewById(R.id.tv_sector3);

        btnShop = (Button) findViewById(R.id.btn_shop);

        tvSector1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Sector 1", Toast.LENGTH_SHORT).show();
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

        btnShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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

    }//function initialise node

}

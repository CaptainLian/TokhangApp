package dlsu.wirtec.tokhangapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import dlsu.wirtec.tokhangapp.R;
import dlsu.wirtec.tokhangapp.game.House;
import dlsu.wirtec.tokhangapp.game.Stage;

public class NodeActivity extends AppCompatActivity {
    public static final int ACTIVITY_RESULT_OKAY = 0;
    public static final int ACTIVITY_RESULT_DEATH = 1;

    public static final int ACTIVITY_REQUEST_CODE_GAME = 0;

    /* nodes */
    private ImageView drawArea;
    private TextView tvSector1, tvSector2, tvSector3;
    private Button btnShop, btnEquipment;

    Bitmap lines;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_node);

        /* node view initalization */
        drawArea = (ImageView) findViewById(R.id.iv_drawArea);

        tvSector1 = (TextView) findViewById(R.id.tv_sector1);
        tvSector2 = (TextView) findViewById(R.id.tv_sector2);
        tvSector3 = (TextView) findViewById(R.id.tv_sector3);

        btnShop = (Button) findViewById(R.id.btn_shop);
        btnEquipment = (Button) findViewById(R.id.btn_equipment);

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

                Stage s = new Stage("Sector 1", 6, houseNames, 700);
                i.putExtra("stage", s);
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

        btnShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ShopActivity.class);
                startActivity(i);
            }
        });

        btnEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), EquipmentActivity.class);
                startActivity(i);
            }
        });

        drawArea.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                drawArea.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                drawLines();
            }
        });
        /* node view initialization end */
    }

    @Override
    protected void onRestart() {
        super.onResume();
        if(lines == null){
            drawLines();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(lines != null){
            drawArea.setImageBitmap(null);
            lines.recycle();
            lines = null;
        }
    }

    private void drawLines(){
        lines = Bitmap.createBitmap(drawArea.getWidth(), drawArea.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas c = new Canvas(lines);
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

        drawArea.setImageBitmap(lines);
    }
}

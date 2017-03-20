package dlsu.wirtec.tokhangapp.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
public class MapPerspective{

    private ImageView drawArea;

    private Paint paint;
    private ImageButton btnMakati, btnPaco;

    public MapPerspective(final GameMapActivity activity){

        drawArea = (ImageView) activity.findViewById(R.id.id_map_drawArea);

        btnMakati = (ImageButton) activity.findViewById(R.id.btn_makati);
        btnPaco = (ImageButton) activity.findViewById(R.id.btn_paco);

        paint = new Paint();
        paint.setAntiAlias(true);

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(12f);

        btnMakati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity.getBaseContext(), "Tondo", Toast.LENGTH_SHORT).show();
            }
        });
        btnPaco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity.getBaseContext(), "Makati", Toast.LENGTH_SHORT).show();
            }
        });

        drawArea.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                drawArea.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                Bitmap bitmap = Bitmap.createBitmap(drawArea.getWidth(), drawArea.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(bitmap);

                float pacoMidX =  btnPaco.getX() + btnPaco.getWidth()/2.0f;
                float pacoMidY = btnPaco.getY() + btnPaco.getHeight()/2.0f;
                float makatiMidX = btnMakati.getX() + btnMakati.getWidth()/2.0f;
                float makatiMidY = btnMakati.getY() + btnMakati.getHeight()/2.0f;

                c.drawLine(pacoMidX, pacoMidY, makatiMidX, makatiMidY, paint);

                drawArea.setImageBitmap(bitmap);
            }
        });
    }

    public void onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();
    }
}

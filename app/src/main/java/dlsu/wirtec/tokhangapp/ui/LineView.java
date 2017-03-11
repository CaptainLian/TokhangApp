package dlsu.wirtec.tokhangapp.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by lyssa on 10/03/2017.
 */

public class LineView extends View {
    public static final float PAINT_STROKE_WIDTH_DEFAULT = 4.5f;
    private Paint paint = new Paint();

    private float startX;
    private float startY;
    private float endX;
    private float endY;

    public LineView(Context context, float startX, float startY, float endX, float endY, float strokeWidth){
        super(context);
        setFocusable(false);
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;

        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(strokeWidth);
        paint.setDither(true);

    }
    public LineView(Context context, float startX, float startY, float endX, float endY) {
        this(context, startX, startY, endX, endY, PAINT_STROKE_WIDTH_DEFAULT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawLine(startX, startY, endX, endY, paint);
        super.onDraw(canvas);
    }
}

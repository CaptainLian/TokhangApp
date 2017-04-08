package dlsu.wirtec.tokhangapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import dlsu.wirtec.tokhangapp.R;
import dlsu.wirtec.tokhangapp.game.Stage;
import dlsu.wirtec.tokhangapp.logic.Player;
import dlsu.wirtec.tokhangapp.managers.GameManager;

public class NodeActivity extends AppCompatActivity {

    public static final int ACTIVITY_RESULT_OKAY = 0;
    public static final int ACTIVITY_RESULT_DEATH = 1;

    public static final int ACTIVITY_REQUEST_CODE_GAME = 0;

    public static final String RESULT_INTENT_SCORE = "GAME_SCORE";

    private ImageView drawArea;
    private Bitmap lines;

    private Button btnShop, btnEquipment;
    private Button btnSave, btnQuit;

    private ImageButton btnStage1, btnStage2, btnStage3, btnStage4, btnStage5, btnStage6;
    private ImageButton[] stages;

    private boolean beingDrawn = true;

    private View.OnClickListener sectorClickedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Stage s = (Stage) v.getTag();

            Intent i = new Intent(getBaseContext(), GameActivity.class);
            i.putExtra(Stage.INTENT_EXTRA_STAGE, s);

            startActivityForResult(i, ACTIVITY_REQUEST_CODE_GAME);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_node);

        /* node view initalization */
        drawArea = (ImageView) findViewById(R.id.iv_drawArea);

        btnShop = (Button) findViewById(R.id.btn_shop);
        btnEquipment = (Button) findViewById(R.id.btn_equipment);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnQuit = (Button) findViewById(R.id.btn_quit);

        btnStage1 = (ImageButton) findViewById(R.id.btn_stage_1);
        btnStage2 = (ImageButton) findViewById(R.id.btn_stage_2);
        btnStage3 = (ImageButton) findViewById(R.id.btn_stage_3);
        btnStage4 = (ImageButton) findViewById(R.id.btn_stage_4);
        btnStage5 = (ImageButton) findViewById(R.id.btn_stage_5);
        btnStage6 = (ImageButton) findViewById(R.id.btn_stage_6);

        stages = new ImageButton[]{btnStage1, btnStage2, btnStage3, btnStage4, btnStage5, btnStage6 };

        GameManager gameManager = GameManager.getGameManager();


        btnShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ShopActivity.class);
                overridePendingTransition(R.anim.in_left_right, R.anim.out_center_right);
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
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameManager.getGameManager().savePlayer();
                Toast.makeText(getBaseContext(), "Saved!", Toast.LENGTH_SHORT).show();
            }
        });
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        drawArea.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                drawArea.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                drawNodes();
                beingDrawn = false;
            }
        });
        /* node view initialization end */

        for(int i = 0; i < stages.length; i++){
            stages[i].setTag(GameManager.getGameManager().getStage(i));
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

    @Override
    protected void onResume() {
        super.onResume();
        if(lines == null && !beingDrawn){
            drawNodes();
        }
    }

    private void drawNodes(){

        lines = Bitmap.createBitmap(drawArea.getWidth(), drawArea.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas c = new Canvas(lines);
        final Paint paint = new Paint();

        paint.setStrokeWidth(14f);
        int silanBlue = getResources().getColor(R.color.silan_blue);
        paint.setColor(silanBlue);
        paint.setAntiAlias(true);

        for (int current = 1; current < stages.length; current++){
            int previous = current - 1;
            final float firstNodeMidX = stages[current].getX() + stages[current].getWidth()/2.0f,
                        firstNodeMidY = stages[current].getY() + stages[current].getHeight()/2.0f;
            final float secondNodeMidX = stages[previous].getX() + stages[previous].getWidth()/2.0f,
                        secondNodeMidY = stages[previous].getY() + stages[previous].getHeight()/2.0f;

            c.drawLine(firstNodeMidX, firstNodeMidY, secondNodeMidX, secondNodeMidY, paint);
        }


        int playerLevel = GameManager.getGameManager().getPlayer().getCurrentLevel();
        for(ImageButton stage: stages){
            Stage s = (Stage) stage.getTag();
            if(s.ID == playerLevel){
                stage.setImageResource(R.drawable.icon_node_current);
                stage.setOnClickListener(sectorClickedListener);
            }else if(s.ID > playerLevel){
                stage.setImageResource(R.drawable.icon_node_locked);
            }else{
                stage.setImageResource(R.drawable.icon_node_completed);
                stage.setOnClickListener(sectorClickedListener);
            }
            stage.bringToFront();
        }

        drawArea.setImageBitmap(lines);
    }


    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case ACTIVITY_REQUEST_CODE_GAME:
                switch (resultCode){
                    case ACTIVITY_RESULT_OKAY:

                        int score = data.getIntExtra(RESULT_INTENT_SCORE, 0);
                        Player p = GameManager.getGameManager().getPlayer();

                        p.incrementScore(score);
                        p.incrementLevel();
                        p.incrementMoney(500);

                        break;
                    case ACTIVITY_RESULT_DEATH:

                        break;
                }//sresultcode
                break;
        }//requestCode
    }
}

package dlsu.wirtec.tokhangapp.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import dlsu.wirtec.tokhangapp.R;
import dlsu.wirtec.tokhangapp.activities.NodeActivity;
import dlsu.wirtec.tokhangapp.logic.Gun;
import dlsu.wirtec.tokhangapp.logic.GunSound;
import dlsu.wirtec.tokhangapp.logic.Player;
import dlsu.wirtec.tokhangapp.managers.GameManager;
import dlsu.wirtec.tokhangapp.managers.SoundManager;

/**
 * Created by Denmark on 3/2/2017.
 */

public class GameView extends SurfaceView implements Runnable {

    public final int[] PLAYER_PAIN_SOUND_IDS;
    public final int[] PLAYER_DEATH_SOUND_IDS;
    public final int[] GAME_CLEAR_SOUND_IDS;
    public final int[] GAME_GO_SOUND_IDS;

    // Important stuff
    private Thread gameThread = null;
    private SurfaceHolder ourHolder;
    private volatile boolean playing;
    private Canvas canvas;
    private Paint paint;
    private boolean debug = false;

    // States
    private final int END_STATE = 2; // lastDraw state is where the stage is already finished
    private final int GAME_STATE = 1; // game state is where the user would be able to shoot enemies
    private final int MOVE_STATE = 0; // move state is where the house is moving in place
    private int currentState = 0;

    // Stage
    private Stage stage;

    // Background
    private int frameWidth;
    private Sprite bg;
    private int bgX1;
    private int bgX2;
    private int bgMoveRate = 5;
    private long lastBgMoveChangeTime;
    private Sprite bgBridge;
    private int bgBridgeX1;
    private int bgBridgeX2;
    private int bgBridgeMoveRate = 1;
    private long lastBgBridgeMoveChangeTime;

    // FPS data
    private long fps;
    private long timeThisFrame;

    // Touch Data
    private float touchX;
    private float touchY;
    private boolean isCurrentlyTouched; //true if the screen is currently touched

    // House Data
    private House currentHouse;
    private int houseWidth = 1200;
    private int houseHeight = 1700;
    private int houseSpawnX;
    private int houseSpawnY = -150;
    private int houseDestinationX1 = 700;
    private int houseDestinationX2 = -2000;
    private int houseTranslationDuration = 3000;

    // Main character data
    private MainCharacter mainChar;
    private int mainCharAddictionWidth = 1000;
    private int mainCharAddictionHeight = 400;
    private int mainCharAddictionX = 30;
    private int mainCharAddictionY = 1130;
    private int mainCharWidth = 250;
    private int mainCharHeight = 250;
    private int mainCharX = 75;
    private int mainCharY = 975;
    private int defaultSourceFrame = 17;
    private int defaultDestinationFrame = 17;
    private long mainCharLastFrameChangeTime = 0;
    private int mainCharAnimationRate = 100;
    private int gunshotDuration = 700;
    private int gunshotRemainingSeconds;
    private int hurtDuration = 400;
    private int hurtRemainingSeconds;
    private int runDuration = houseTranslationDuration;
    private int runRemainingSeconds;

    // Char data
    private ArrayList<Character> chars;
    private int charWidth = 150;
    private int charHeight = 150;
    private long charLastFrameChangeTime = 0;
    private boolean characterSpawned;
    private int charAnimationRate = 200;
    private int touchedStartAnimationIndex = 1;
    private int touchedEndAnimationIndex = 5;
    private float charTranslationDuration = 300;
    private int shakeStart = 1000; // when 1 second remains of the character's duration
    private int shakeIntensity = 7;
    private int characterInterval;

    // Item data
    private Sprite itemTitle;
    private int itemTitleWidth = 100;
    private int itemTitleLength = 50;
    private int itemTitleX = 1825;
    private int itemTitleY = 1200;
    private ArrayList<Item> items;
    private Sprite defaultItemSprite;
    private int itemWidth = 150;
    private int itemLength = 150;
    private int slotX = 1600;
    private int slotY = 1250;

    // DamageAnimation data
    private ArrayList<DamageAnimation> damageAnimations;

    // extras
    private int points;
    private Timer timer;
    private int currentMillisecond;
    private int characterCount = 0;
    private int countTouch;

    private SoundManager soundManager;
    private Player player;

    private boolean lastUpdate = false;
    private boolean lastDraw = false;

    private Activity context;

    private GameStateListener gameStateListener;

    private Random random = new Random(new Random().nextLong());
    public GameView(final Activity context, Stage stage, @Nullable GameStateListener gameStateListener) {
        super(context);
        // Damage Initialization
        damageAnimations = new ArrayList<DamageAnimation>();

        // Stage Initialization
        this.stage = stage;
        characterInterval = stage.getSpriteSpawningInterval();

        frameWidth = context.getResources().getDisplayMetrics().widthPixels;

        ourHolder = getHolder();
        paint = new Paint();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                currentMillisecond +=40;
                hurtRemainingSeconds -= 40;
                gunshotRemainingSeconds -= 40;
                runRemainingSeconds -= 40;
            }
        },0,40);


        // BG instantiation
        bgX1 = 0;
        bgX2 = frameWidth;
        bgBridgeX1 = 0;
        bgBridgeX2 = frameWidth;
        Bitmap bgBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.citybg);
        bgBitmap = Bitmap.createScaledBitmap(bgBitmap, context.getResources().getDisplayMetrics().widthPixels,context.getResources().getDisplayMetrics().heightPixels, false);
        bg = new Sprite (bgBitmap);
        Bitmap bgBridgeBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.citybgbridge);
        bgBridgeBitmap = Bitmap.createScaledBitmap(bgBridgeBitmap, context.getResources().getDisplayMetrics().widthPixels,context.getResources().getDisplayMetrics().heightPixels, false);
        bgBridge = new Sprite (bgBridgeBitmap);
        //Main character instantiation
        Bitmap mainCharBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.soldier4);
        mainCharBitmap = Bitmap.createScaledBitmap(mainCharBitmap, 600, 300,false);
        SpriteAnimation mainCharacterAnimation = createAnimation(mainCharBitmap, 3, 6, 100, 100, mainCharWidth, mainCharHeight);
        Bitmap healthBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ui_game_addictionlevel);
        healthBitmap = Bitmap.createScaledBitmap(healthBitmap, 400, 400,false);
        SpriteAnimation healthAnimation = createAnimation(healthBitmap, 4, 1, 400, 100, mainCharAddictionWidth, mainCharAddictionHeight);
        mainChar = new MainCharacter(mainCharacterAnimation, mainCharX, mainCharY, 17, 17, healthAnimation);
        mainCharBitmap.recycle();
        healthBitmap.recycle();
        //Item instantiation
        Bitmap itemTitleBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.itemtitle);
        itemTitleBitmap = Bitmap.createScaledBitmap(itemTitleBitmap, itemTitleWidth, itemTitleLength, false);
        itemTitle = new Sprite (itemTitleBitmap);
        items = new ArrayList<Item>();
        Bitmap slotBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.itemslot1);
        slotBitmap = Bitmap.createScaledBitmap(slotBitmap, itemWidth, itemLength, false);
        defaultItemSprite = new Sprite(slotBitmap);
        items.add(new Item("empty", defaultItemSprite, 0, slotX, slotY));
        items.add(new Item("empty", defaultItemSprite, 0, slotX+200, slotY));
        items.add(new Item("empty", defaultItemSprite, 0, slotX+400, slotY));
        initialize(context);

        this.player = GameManager.getGameManager().getPlayer();
        this.soundManager = GameManager.getSoundManager();

        this.context = context;
        this.gameStateListener = gameStateListener;

        GAME_GO_SOUND_IDS = new int[]{
            soundManager.SOUND_GAME_GO1,
            soundManager.SOUND_GAME_GO2,
            soundManager.SOUND_GAME_GO3,
            soundManager.SOUND_GAME_GO4
        };

        GAME_CLEAR_SOUND_IDS = new int[]{
            soundManager.SOUND_GAME_CLEAR1,
            soundManager.SOUND_GAME_CLEAR2,
            soundManager.SOUND_GAME_CLEAR3
        };

        PLAYER_DEATH_SOUND_IDS = new int[]{
            soundManager.SOUND_PLAYER_DEATH1,
            soundManager.SOUND_PLAYER_DEATH2,
            soundManager.SOUND_PLAYER_DEATH3
        };

        PLAYER_PAIN_SOUND_IDS = new int[]{
          soundManager.SOUND_PLAYER_PAIN1,
          soundManager.SOUND_PLAYER_PAIN2,
          soundManager.SOUND_PLAYER_PAIN3
        };

        soundManager.playSound(GAME_GO_SOUND_IDS[random.nextInt(GAME_GO_SOUND_IDS.length)]);
    }

    @Override
    public void run() {
        while(playing) {
            long startFrameTime = System.currentTimeMillis();

            update();
            draw();

            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if(timeThisFrame >= 1) {
                fps = 1000/timeThisFrame;
            }
        }
    }

    public void update() {
        if((currentMillisecond -1000)% characterInterval <= 10) {
            characterSpawned = false;
        }
        if(currentMillisecond % characterInterval <= 10  && currentMillisecond != 0) {
            // will create a new character
            if(currentState == GAME_STATE && currentHouse.getRemainingCharacterSpawns() > 0) {
                spawnCharacter();
            }
        }

        animateCharacter(charAnimationRate);
        animateMainCharacter(mainCharAnimationRate);
        animateMainCharacterGunShot();
        animateMainCharacterHurt();
        animateMainCharacterRun();
        checkCharacterIfLastFrame();
        checkCharacterIfDue();
        checkIfMainCharacterDead();
        checkTouchedItem();
        checkIfHouseDone();
        moveHouse(houseTranslationDuration);
        moveCharacter();
        moveDamage();
        moveBg(bgMoveRate);
        moveBgBridge(bgBridgeMoveRate);
        shakeCharacter();
    }

    public void draw() {
        if(ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();

            canvas.drawColor(Color.WHITE);
            // Background Drawing
            canvas.drawBitmap(bg.getBitmap(),bgX1,0,paint);
            canvas.drawBitmap(bg.getBitmap(),bgX2,0,paint);
            // Building/ House Drawing
            canvas.drawBitmap(currentHouse.getBitmap(), currentHouse.getX(), currentHouse.getY(), paint);
            // Background Bridge Drawing
            canvas.drawBitmap(bgBridge.getBitmap(),bgBridgeX1,0,paint);
            canvas.drawBitmap(bgBridge.getBitmap(),bgBridgeX2,0,paint);
            // Character Drawing
            for(int i = 0; i < chars.size(); i++) {
                canvas.drawBitmap(chars.get(i).returnBitmapToDraw(), chars.get(i).getX(), chars.get(i).getY(), paint);
            }
            //Score Drawing
            paint.setColor(Color.WHITE);
            paint.setTextSize(50);
            canvas.drawText("SCORE: " + points , 2000, 50, paint);
            // MainCharacter Drawing
            canvas.drawBitmap(mainChar.getAddictionSprite().getBitmap(), mainCharAddictionX, mainCharAddictionY,paint);
            canvas.drawBitmap(mainChar.getCurrentSprite().getBitmap(), mainChar.getX(), mainChar.getY(), paint);
            // Item Drawing
            for(int i = 0; i < items.size();i++) {
                canvas.drawBitmap(items.get(i).getSprite().getBitmap(), items.get(i).getX(), items.get(i).getY(), paint);
            }
            canvas.drawBitmap(itemTitle.getBitmap(), itemTitleX, itemTitleY, paint);
            // Damage Drawing
            for(int i = 0 ; i < damageAnimations.size(); i ++) {
                canvas.drawText(damageAnimations.get(i).getDamageValue()+"", damageAnimations.get(i).getDamageX(), damageAnimations.get(i).getDamageY(), paint);
            }

            // Debug Drawing
            if(debug) {
                paint.setColor(Color.WHITE);
                paint.setTextSize(45);
                canvas.drawText("FPS: " + fps + " Cur Ms: " + currentMillisecond + " Character Count: " + chars.size(), 20, 40, paint);
                canvas.drawText("X: " + touchX + " Y: " + touchY , 20, 80, paint);
                canvas.drawText("Points: " + points + " " + mainChar.getX(), 20, 120, paint);
                canvas.drawText(mainChar.getSourceSpriteIndex() + "." + mainChar.getCurrentSpriteIndex() + "." + mainChar.getDestinationSpriteIndex(), 20, 160, paint);
                canvas.drawText("HP: " + mainChar.getAddiction(), 20, 200, paint);
                canvas.drawText("House X: " + currentHouse.getX(), 20, 240, paint);
                canvas.drawText("Remaining houses: " + stage.getNumberOfHouses() + " " + characterCount + " " + countTouch, 20, 280, paint);
                for(int i = 0; i < damageAnimations.size(); i ++) {
                    canvas.drawText("DamageAnimation: " + damageAnimations.get(i).getDamageDestinationY() + " " + damageAnimations.get(i).getDamageY() + " " + damageAnimations.get(i).getOriginalY(), 20, 320, paint);
                }
            }

            if(currentState == END_STATE) {
                paint.setColor(Color.WHITE);
                paint.setTextSize(100);
                canvas.drawText("TAP ANYWHERE ON THE SCREEN TO EXIT", 250, 800, paint);

                if(!lastDraw){
                    lastDraw = true;

                    if(mainChar.isAlive()){
                        soundManager.playSound(GAME_CLEAR_SOUND_IDS[random.nextInt(GAME_CLEAR_SOUND_IDS.length)]);
                    }else{
                        soundManager.playSound(PLAYER_DEATH_SOUND_IDS[random.nextInt(PLAYER_DEATH_SOUND_IDS.length)]);
                    }//if(mainChar.isAlive())
                }//if (!lastDraw)
            }//if(currentState == END_STATE)
            ourHolder.unlockCanvasAndPost(canvas);
        }//if surface Valid
    }//function draw

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void destroyBitmaps() {
        bg.destroyBitmap();
        bgBridge.destroyBitmap();
        mainChar.destroyBitmaps();
        defaultItemSprite.destroyBitmap();
        for(int i = 0; i < chars.size(); i++) {
            chars.get(i).destroyBitmaps();
        }
        for(int i = 0; i < items.size(); i++) {
            items.get(i).getSprite().destroyBitmap();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                if(!isCurrentlyTouched) {
                    isCurrentlyTouched = true;
                    countTouch ++;
                    touchX = motionEvent.getX();
                    touchY = motionEvent.getY();

                    if (mainChar.isAlive() && currentState == GAME_STATE) {
                        mainCharacterShoot();

                        Gun g = player.getEquippedGun();

                        GunSound sound = g.getGunSound();

                        if(sound != null){
                            int id = sound.getFireSoundID();
                            if(id != GunSound.RESOURCE_ID_NONE){
                                soundManager.playSound(id);
                            }
                        }

                        List<Character> affectedChars = g.fire(touchX, touchY, chars);

                        for (Character c: affectedChars){
                            c.setTouched(true);
                            c.setSpriteAnimation(touchedStartAnimationIndex, touchedEndAnimationIndex);

                            final double midX = c.getMidX();
                            final double midY = c.getMidY();
                            damageAnimations.add(new DamageAnimation(g.getDamage(), (float) midX, (float) midY, (float) (midY - 200.0f), currentMillisecond));
                        }

                        affectedChars.clear();

                    } else if (currentState == END_STATE) {
                        if(mainChar.isAlive()){
                            if(gameStateListener != null){
                                gameStateListener.onPlayerSuccess(points);
                            }
                        }else{
                            if(gameStateListener != null){
                                gameStateListener.onPlayerDeath(points);
                            }
                        }//if(mainChar.isAlive())
                    }//currentState == END_STATE
                }
                break;

            case MotionEvent.ACTION_UP:
                isCurrentlyTouched = false;
                touchX = -1;
                touchY = -1;
                if(mainChar.isAlive() && currentState == GAME_STATE) {
                    mainCharacterShoot();
                }
                break;
        }
        return true;
    }

    // Own methods //
    public void initialize (Context context) {
        currentState = MOVE_STATE;
        chars = new ArrayList<Character>();

        //House instantiation
        int r = new Random().nextInt(stage.getTypeOfHouse().length);
        houseSpawnX=frameWidth;
        if(stage.getTypeOfHouse()[r].equals(House.BUILDING1)) {
            int[] xSpawns = {840, 1060, 1280, 840, 1060, 1280, 840, 1060, 1280, 840, 1060, 1280, 840, 1060, 1280};
            int[] ySpawns = {320, 320, 320, 485, 485, 485, 650, 650, 650, 815, 815, 815, 980, 980, 980};
            Bitmap houseBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.building1);
            currentHouse = new House(Bitmap.createScaledBitmap(houseBitmap, houseWidth, houseHeight,false), houseSpawnX, houseSpawnY, houseDestinationX1, currentMillisecond, 5,xSpawns, ySpawns);
            houseBitmap.recycle();
        }
        else if(stage.getTypeOfHouse()[r].equals(House.BUILDING2)) {
            int[] xSpawns = {915, 1135, 1335, 1565, 915, 1335, 915, 1145, 1350};
            int[] ySpawns = {675, 675, 675, 675, 848, 848, 1021, 1021, 1021};
            Bitmap houseBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.building2);
            currentHouse = new House(Bitmap.createScaledBitmap(houseBitmap, houseWidth, houseHeight,false), houseSpawnX, houseSpawnY, houseDestinationX1, currentMillisecond, 5,xSpawns, ySpawns);
            houseBitmap.recycle();
        }
        else if(stage.getTypeOfHouse()[r].equals(House.BUILDING3)) {
            int[] xSpawns = {855, 1040, 1225, 1410, 1595, 880, 1175, 1470, 880, 1175, 1470};
            int[] ySpawns = {360, 360, 360, 360, 360, 620, 620, 620, 1000, 1000, 1000};
            Bitmap houseBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.building3);
            currentHouse = new House(Bitmap.createScaledBitmap(houseBitmap, houseWidth, houseHeight,false), houseSpawnX, houseSpawnY, houseDestinationX1, currentMillisecond, 5,xSpawns, ySpawns);
            houseBitmap.recycle();
        }
        runRemainingSeconds = runDuration;
    }

    public void spawnCharacter() {
        // spawns a random character based on the given spawn locations of the current house
        int spawnIndex = new Random().nextInt(currentHouse.getxSpawns().length);
        if(!isXandYSpawned(currentHouse.getxSpawns()[spawnIndex], currentHouse.getySpawns()[spawnIndex])) {
            characterCount++;
            Bitmap smoke = BitmapFactory.decodeResource(this.getResources(), R.drawable.smoke);
            smoke = Bitmap.createScaledBitmap(smoke, 500, 100, false);
            SpriteAnimation animation2 = createAnimation(smoke, 1, 5, 100, 100, charWidth, charHeight);
            smoke.recycle();

            int r = new Random().nextInt(100); // spawns a drug if 0 - 69, spawns an innocent if 70 - 89 , spawns an item if  90 - 99
            if(r <= 69) {
                int charType = new Random().nextInt(3);
                if(charType == 0) {
                    Bitmap character = BitmapFactory.decodeResource(this.getResources(), R.drawable.drug1);
                    character = Bitmap.createScaledBitmap(character, 100, 100, false);
                    SpriteAnimation animation1 = createAnimation(character, 1, 1, 100, 100, charWidth, charHeight);
                    character.recycle();
                    chars.add(new Character("drugs", animation1, currentHouse.getxSpawns()[spawnIndex], currentHouse.getySpawns()[spawnIndex], currentMillisecond, 2000, 5, 0, 0, 3));
                }
                else if(charType == 1) {
                    Bitmap character = BitmapFactory.decodeResource(this.getResources(), R.drawable.drug2);
                    character = Bitmap.createScaledBitmap(character, 100, 100, false);
                    SpriteAnimation animation1 = createAnimation(character, 1, 1, 100, 100, charWidth, charHeight);
                    character.recycle();
                    chars.add(new Character("drugs", animation1, currentHouse.getxSpawns()[spawnIndex], currentHouse.getySpawns()[spawnIndex], currentMillisecond, 2000, 10, 0, 0, 7));
                }
                else if(charType == 2) {
                    Bitmap character = BitmapFactory.decodeResource(this.getResources(), R.drawable.drug3);
                    character = Bitmap.createScaledBitmap(character, 100, 100, false);
                    SpriteAnimation animation1 = createAnimation(character, 1, 1, 100, 100, charWidth, charHeight);
                    character.recycle();
                    chars.add(new Character("drugs", animation1, currentHouse.getxSpawns()[spawnIndex], currentHouse.getySpawns()[spawnIndex], currentMillisecond, 2000, 2, 0, 0, 1));
                }
            }
            else if (r <= 89) {
                int charType = new Random().nextInt(4);
                if (charType == 0) {
                    Bitmap character = BitmapFactory.decodeResource(this.getResources(), R.drawable.innocent1);
                    character = Bitmap.createScaledBitmap(character, 100, 100, false);
                    SpriteAnimation animation1 = createAnimation(character, 1, 1, 100, 100, charWidth, charHeight);
                    character.recycle();
                    chars.add(new Character("innocent", animation1, currentHouse.getxSpawns()[spawnIndex], currentHouse.getySpawns()[spawnIndex], currentMillisecond, 1000, -2, 0, 0, 1));
                }
                else if (charType == 1) {
                    Bitmap character = BitmapFactory.decodeResource(this.getResources(), R.drawable.innocent2);
                    character = Bitmap.createScaledBitmap(character, 100, 100, false);
                    SpriteAnimation animation1 = createAnimation(character, 1, 1, 100, 100, charWidth, charHeight);
                    character.recycle();
                    chars.add(new Character("innocent", animation1, currentHouse.getxSpawns()[spawnIndex], currentHouse.getySpawns()[spawnIndex], currentMillisecond, 1500, -5, 0, 0, 1));
                }
                else if (charType == 2) {
                    Bitmap character = BitmapFactory.decodeResource(this.getResources(), R.drawable.innocent3);
                    character = Bitmap.createScaledBitmap(character, 100, 100, false);
                    SpriteAnimation animation1 = createAnimation(character, 1, 1, 100, 100, charWidth, charHeight);
                    character.recycle();
                    chars.add(new Character("innocent", animation1, currentHouse.getxSpawns()[spawnIndex], currentHouse.getySpawns()[spawnIndex], currentMillisecond, 2000, -10, 0, 0, 1));
                }
                else if (charType == 3) {
                    Bitmap character = BitmapFactory.decodeResource(this.getResources(), R.drawable.innocent4);
                    character = Bitmap.createScaledBitmap(character, 100, 100, false);
                    SpriteAnimation animation1 = createAnimation(character, 1, 1, 100, 100, charWidth, charHeight);
                    character.recycle();
                    chars.add(new Character("innocent", animation1, currentHouse.getxSpawns()[spawnIndex], currentHouse.getySpawns()[spawnIndex], currentMillisecond, 2500, -20, 0, 0, 1));
                }
            }
            else if (r <= 99) {
                int charType = new Random().nextInt(1);
                if (charType == 0) {
                    Bitmap character = BitmapFactory.decodeResource(this.getResources(), R.drawable.health);
                    character = Bitmap.createScaledBitmap(character, 100, 100, false);
                    SpriteAnimation animation1 = createAnimation(character, 1, 1, 100, 100, charWidth, charHeight);
                    character.recycle();
                    chars.add(new Character("item_health", animation1, currentHouse.getxSpawns()[spawnIndex], currentHouse.getySpawns()[spawnIndex], currentMillisecond, 300, 0, 0, 0, 5));
                }
            }


            chars.get(chars.size() - 1).addSpritesToAnimation(animation2);
            currentHouse.deductCharacterSpawn();
            characterSpawned = true;
        }
    }

    public void mainCharacterShoot() {
        // sets the shooting animation of the main character
        if(mainChar.isAlive()) {
            mainChar.setSpriteAnimation(13, 16);
            gunshotRemainingSeconds = gunshotDuration;
        }
    }

    public void damageMainCharacter() {
        // decreases the hp of the main character and sets the hurt animation
        if(mainChar.isAlive()) {
            mainChar.increaseAddiction();
            hurtRemainingSeconds = hurtDuration;
            mainChar.setSpriteAnimation(6, 6);
            soundManager.playSound(PLAYER_PAIN_SOUND_IDS[random.nextInt(PLAYER_PAIN_SOUND_IDS.length)]);
        }
    }

    public void animateCharacter(int charAnimationRate){
        // animates the character from its source to destination frames
        // according to the rate of animation based on the parameter
        long time  = System.currentTimeMillis();
        if ( time > charLastFrameChangeTime + charAnimationRate) {
            charLastFrameChangeTime = time;
            for(int i = 0; i < chars.size(); i++) {
                chars.get(i).animate();
            }
        }
    }

    public void animateMainCharacter (int mainCharAnimationRate) {
        // animates the main character from its source to destination frames
        // according to the rate of animation based on the parameter
        long time = System.currentTimeMillis();
        if (time > mainCharLastFrameChangeTime + mainCharAnimationRate) {
            mainCharLastFrameChangeTime = time;
            mainChar.animate();
        }
    }

    public void animateMainCharacterGunShot() {
        // animates gun shot animation while main char have remaining gunshot secs
        if(gunshotRemainingSeconds >= -50 && gunshotRemainingSeconds <= 50 && mainChar.isAlive()) {
            mainChar.setSpriteAnimation(defaultSourceFrame, defaultDestinationFrame);
        }
    }

    public void animateMainCharacterHurt() {
        // animates the hurt animation while main char has remaining hurt secs
        if(hurtRemainingSeconds >= -50 && hurtRemainingSeconds <= 50 && mainChar.isAlive()) {
            mainChar.setSpriteAnimation(defaultSourceFrame, defaultDestinationFrame);
        }
    }

    public void animateMainCharacterRun() {
        // animates the running animation while main char has remaining run secs
        if(runRemainingSeconds >= -50 && runRemainingSeconds <= 50 && !currentHouse.isMoving()) {
            mainChar.setSpriteAnimation(defaultSourceFrame, defaultDestinationFrame);
        }
    }

    public void checkCharacterIfLastFrame() {
        // we are assuming that if an animation reachces the last frame, it is already finished
        // if the animation reaches its last frame, it is removed from the arraylist,
        // adds score to the player and deduct the character spawns  of the house
        for(int i = 0; i < chars.size(); i ++) {
            Character c = chars.get(i);
            if (c.getCurrentSpriteIndex() == c.getSprites().size()-1) {
                points+= c.getScore();
                if(c.getName().split("_")[0].equals("item")) {
                    if(c.getName().split("_")[1].equals("health")) {
                        setItem(c.getName().split("_")[1], 1, c.getSprites().get(0));
                    }
                }
                chars.remove(i);
            }
        }
    }

    public void checkCharacterIfDue() {
        // this method checks the char if it is already due its duration
        // if the Character is due its duration, it will be removed
        for (int i= 0; i< chars.size(); i++) {
            if(currentMillisecond - chars.get(i).getSpawnTime() >= chars.get(i).getDuration()+1000 && !chars.get(i).isTouched() && !chars.get(i).isDue()) {
                if(!chars.get(i).getName().equals("drugs")) {
                    chars.remove(i--);
                }
                else {
                    chars.get(i).setDue(true);
                    chars.get(i).setTimeOfDue(currentMillisecond);
                }
            }
        }

    }


    public void checkTouchedItem() {
        for(int i = 0; i < items.size(); i ++) {
            if(items.get(i).getEffect() != 0 && items.get(i).checkIfItemTouched(touchX, touchY) && currentState != END_STATE) {
                if(items.get(i).getEffect() == 1) {
                    mainChar.decreaseAddiction();
                    items.get(i).setSprite(defaultItemSprite);
                    items.get(i).setName("empty");
                    items.get(i).setEffect(0);
                }
            }
        }
    }

    public void checkIfMainCharacterDead() {
        // checks if the main character is dead and not yet on the last death frame
        if(!mainChar.isAlive() && mainChar.getCurrentSpriteIndex() != 12) {
            // sets the death animation to the main character if the character is dead
            mainChar.setSpriteAnimation(6, 12);
        }
        if(mainChar.getCurrentSpriteIndex() == 12 && mainChar.getSourceSpriteIndex() != 12) {
            mainChar.setSpriteAnimation(12,12);
        }
    }

    public void setItem (String name, int effect, Sprite sprite) {
        for(int i = 0; i < items.size(); i ++) {
            if(items.get(i).getEffect() == 0) {
                items.get(i).setName("health");
                items.get(i).setEffect(1);
                items.get(i).setSprite(sprite);
                i=items.size();
            }
        }
    }

    public void moveCharacter () {
        // moves the character to the location of the main character
        // damages the main character as soon as they collide
        for(int i = 0; i < chars.size(); i ++ ) {
            if(chars.get(i).isDue()&& chars.get(i).getName().toString().equals("drugs")){
                float changeInX = mainChar.getX() - chars.get(i).getOriginalX();
                float changeInY = mainChar.getY() + 150 - chars.get(i).getOriginalY();
                float progress = (currentMillisecond - chars.get(i).getTimeOfDue())/charTranslationDuration;
                chars.get(i).setX(chars.get(i).getOriginalX()+changeInX*progress);
                chars.get(i).setY(chars.get(i).getOriginalY()+changeInY*progress);
                if(progress>=1) {
                    chars.remove(i--);
                    damageMainCharacter();
                    if(!mainChar.isAlive() && currentState != END_STATE){
                        currentState = END_STATE;
                    }
                }
            }
        }
    }

    public void checkIfHouseDone() {
        if(currentHouse.getRemainingCharacterSpawns() == 0 && currentState != MOVE_STATE && chars.size() == 0 && currentState != END_STATE) {
            currentState = MOVE_STATE;
            currentHouse.setDestinationX(houseDestinationX2);
            currentHouse.setHouseSpawnTime(currentMillisecond);
        }
    }

    public void moveDamage() {
        for(int i = 0; i < damageAnimations.size(); i++) {
            if(damageAnimations.get(i).moveDamage(currentMillisecond, 200) >=1) {
                damageAnimations.remove(i--);
            }
        }
    }

    public void moveHouse (int houseTranslationDuration) {
        // moves the house from source x to destination x based on the house movement rate
        currentHouse.moveHouseToDestination(currentMillisecond,houseTranslationDuration);
        if(!currentHouse.isMoving() && currentState == MOVE_STATE && currentState != GAME_STATE) {
            if (currentHouse.getX() <= houseDestinationX2 + 40) {
                if (stage.getNumberOfHouses() <= 1) {
                    mainChar.setSpriteAnimation(defaultSourceFrame, defaultDestinationFrame);
                    currentState = END_STATE;
                }
                else if(stage.getNumberOfHouses() > 0) {
                    stage.deductHouse();
                    initialize(context);
                }
            }
            else if(currentHouse.getX() <= houseDestinationX1 + 40) {
                currentState = GAME_STATE;
            }
        }
        //Set main character animation
        if(currentHouse.isMoving() && mainChar.getSourceSpriteIndex() != 0 && mainChar.getDestinationSpriteIndex() != 5) {
            mainChar.setSpriteAnimation(0,5);
        }
    }

    public boolean isXandYSpawned (int xSpawn, int ySpawn) {
        // checks if the x and y to spawn already exists
        // returns true if x and y already has a char characterSpawned returns false otherwise
        for(int i = 0; i < chars.size(); i++) {
            if(chars.get(i).getOriginalX() == xSpawn && chars.get(i).getOriginalY() == ySpawn) {
                return true;
            }
        }
        return false;
    }

    public void moveMainCharacter() {
        //changeInX = 0 - mainCharX;
        //changeInY = 0 - mainCharY;
        //progress = currentMillisecond / 3000;
        //mainChar.setX(mainCharX+changeInX * progress);
        //mainChar.setY(mainCharY+changeInY * progress);
    }

    public void moveBg(int bgMoveRate) {
        if(currentHouse.isMoving()) {
            long time = System.currentTimeMillis();
            if (time > lastBgMoveChangeTime + bgMoveRate) {
                lastBgMoveChangeTime = time;
                bgX1-=2;
                bgX2-=2;
                if (bgX1 <= -frameWidth) {
                    bgX1 = frameWidth;
                } else if (bgX2 <= -frameWidth) {
                    bgX2 = frameWidth;
                }
            }
        }
    }

    public void moveBgBridge(int bgBridgeMoveRate) {
        if(currentHouse.isMoving()) {
            long time = System.currentTimeMillis();
            if (time > lastBgBridgeMoveChangeTime + bgBridgeMoveRate) {
                lastBgBridgeMoveChangeTime = time;
                bgBridgeX1-=7;
                bgBridgeX2-=7;
                if (bgBridgeX1 <= -frameWidth) {
                    bgBridgeX1 = frameWidth;
                } else if (bgBridgeX2 <= -frameWidth) {
                    bgBridgeX2 = frameWidth;
                }
            }
        }
    }

    public void shakeCharacter () {
        for(int i = 0; i < chars.size(); i++) {
            if(!chars.get(i).isTouched() && !chars.get(i).isDue() && currentMillisecond - chars.get(i).getSpawnTime() >= shakeStart && chars.get(i).getName().equals("drugs")) {
                if (chars.get(i).isShakenToRight()) {
                    chars.get(i).setShakenToRight(false);
                    chars.get(i).setX(chars.get(i).getX()-shakeIntensity);
                }
                else {
                    chars.get(i).setShakenToRight(true);
                    chars.get(i).setX(chars.get(i).getX()+shakeIntensity);
                }
            }
        }
    }


    public SpriteAnimation createAnimation (Bitmap bitmap, int row, int column, int spriteWidth, int spriteHeight, int frameWidth, int frameHeight) {
        SpriteAnimation animation = new SpriteAnimation();
        for(int i = 0; i < row; i ++) {
            for (int j = 0; j < column; j++) {
                animation.addSprites(new Sprite(
                        Bitmap.createScaledBitmap(Bitmap.createBitmap(bitmap, 0 + (spriteWidth * j), 0 + (spriteHeight * i), spriteWidth, spriteHeight, null, false), frameWidth, frameHeight, false)));
            }
        }
        animation.setCurrentSpriteIndex(0);
        return animation;
    }
}

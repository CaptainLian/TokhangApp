package dlsu.wirtec.tokhangapp.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import dlsu.wirtec.tokhangapp.R;

/**
 * Created by Denmark on 3/2/2017.
 */

public class GameView extends SurfaceView implements Runnable {

    // Important stuff
    private Thread gameThread = null;
    private SurfaceHolder ourHolder;
    private volatile boolean playing;
    private Canvas canvas;
    private Paint paint;
    private boolean debug = true;

    // States
    private final int END_STATE = 2; // end state is where the stage is already finished
    private final int GAME_STATE = 1; // game state is where the user would be able to shoot enemies
    private final int MOVE_STATE = 0; // move state is where the house is moving in place
    private int currentState = 0;

    // Stage
    private Stage stage;

    // Background
    private Sprite bg;
    private int bgX1;
    private int bgX2;
    private int bgMoveRate = 5;
    private int frameWidth;
    private long laseBgMoveChangeTime;
    Bitmap bgBitmap = null;

    // FPS data
    private long fps;
    private long timeThisFrame;

    // Touch Data
    private float touchX;
    private float touchY;

    // House Data
    private House currentHouse;
    private int houseWidth = 2000;
    private int houseHeight = 1000;
    private int houseSpawnX;
    private int houseSpawnY = 220;
    private int houseDestinationX1 = 300;
    private int houseDestinationX2 = -2000;
    private int houseTranslationDuration = 4000;

    // Main character data
    private MainCharacter mainChar;
    private int mainCharAddictionWidth = 900;
    private int mainCharAddictionHeight = 300;
    private int mainCharWidth = 500;
    private int mainCharHeight = 500;
    private int mainCharX = 75;
    private int mainCharY = 850;
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

    // extras
    private int points;
    private Timer timer;
    private boolean counterStarted;
    private int currentMillisecond;
    private int count = 0;

    public GameView(final Context context, Stage stage) {
        super(context);
        // Stage Initialization
        this.stage = stage;
        characterInterval = stage.getSpriteSpawningInterval();

        frameWidth = context.getResources().getDisplayMetrics().widthPixels;
        bgX1 = 0;
        bgX2 = frameWidth;
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
        bgBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.citybg2);
        bgBitmap = Bitmap.createScaledBitmap(bgBitmap, context.getResources().getDisplayMetrics().widthPixels,context.getResources().getDisplayMetrics().heightPixels, false);
        bg = new Sprite (bgBitmap);
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
        initialize(context);
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
        if((currentMillisecond -1000)% characterInterval <= 20) {
            characterSpawned = false;
        }
        if(currentMillisecond % characterInterval <= 20  && currentMillisecond != 0) {
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
        checkTouchedCharacters();
        checkIfHouseDone();
        moveHouse(houseTranslationDuration);
        moveCharacter();
        moveBg(bgMoveRate);
        shakeCharacter();
    }

    public void draw() {
        if(ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();

            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(bg.getBitmap(),bgX1,0,paint);
            canvas.drawBitmap(bg.getBitmap(),bgX2,0,paint);
            canvas.drawBitmap(currentHouse.getBitmap(), currentHouse.getX(), currentHouse.getY(), paint);
            for(int i = 0; i < chars.size(); i++) {
                canvas.drawBitmap(chars.get(i).returnBitmapToDraw(), chars.get(i).getX(), chars.get(i).getY(), paint);
            }
            paint.setColor(Color.WHITE);
            paint.setTextSize(50);
            canvas.drawText("SCORE: " + points , 1500, 50, paint);
            canvas.drawBitmap(mainChar.getAddictionSprite().getBitmap(),0,0,paint);
            canvas.drawBitmap(mainChar.getCurrentSprite().getBitmap(), mainChar.getX(), mainChar.getY(), paint);

            if(debug) {
                paint.setColor(Color.WHITE);
                paint.setTextSize(45);
                canvas.drawText("FPS: " + fps + " Cur Ms: " + currentMillisecond + " Character Count: " + chars.size(), 20, 40, paint);
                canvas.drawText("X: " + touchX + " Y: " + touchY, 20, 80, paint);
                canvas.drawText("Points: " + points + " " + mainChar.getX(), 20, 120, paint);
                canvas.drawText(mainChar.getSourceSpriteIndex() + "." + mainChar.getCurrentSpriteIndex() + "." + mainChar.getDestinationSpriteIndex(), 20, 160, paint);
                canvas.drawText("HP: " + mainChar.getAddiction(), 20, 200, paint);
                canvas.drawText("House X: " + currentHouse.getX(), 20, 240, paint);
                canvas.drawText("Remaining houses: " + stage.getNumberOfHouses() + " " + count, 20, 280, paint);
            }
            if(currentState == END_STATE) {
                paint.setColor(Color.WHITE);
                paint.setTextSize(100);
                canvas.drawText("TAP ANYWHERE ON THE SCREEN TO EXIT", 250, 800, paint);
            }
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

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

    public void destroy() {
        bgBitmap.recycle();
        mainChar.destroyBitmaps();
        for(int i = 0; i < chars.size(); i++) {
            chars.get(i).destroyBitmaps();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                touchX = motionEvent.getX();
                touchY = motionEvent.getY();
                if(mainChar.isAlive() && currentState == GAME_STATE) {
                    mainCharacterShoot();
                }
                else if (currentState == END_STATE) {
                    /*
                    Intent i = new Intent(getContext(), MainActivity.class);
                    getContext().startActivity(i);
                    ((Activity)getContext()).finish();
                    */
                }
                break;

            case MotionEvent.ACTION_UP:
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
        int[] xSpawns = {620, 1570, 2060, 740, 1000, 700};
        //int[] xSpawns = {620};
        int[] ySpawns = {570, 540, 415, 890, 500, 650};
        //int[] ySpawns = {570};
        if(stage.getTypeOfHouse()[r].equals("bungalow")) {
            Bitmap houseBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.house1);
            currentHouse = new House(Bitmap.createScaledBitmap(houseBitmap, houseWidth, houseHeight,false), houseSpawnX, houseSpawnY, houseDestinationX1, currentMillisecond, 3,xSpawns, ySpawns);
            houseBitmap.recycle();
        }
        else if(stage.getTypeOfHouse()[r].equals("skwater")) {
            Bitmap houseBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.house3);
            currentHouse = new House(Bitmap.createScaledBitmap(houseBitmap, houseWidth, houseHeight,false), houseSpawnX, houseSpawnY, houseDestinationX1, currentMillisecond, 5,xSpawns, ySpawns);
            houseBitmap.recycle();
        }
        runRemainingSeconds = runDuration;
    }

    public void spawnCharacter() {
        // spawns a random character based on the given spawn locations of the current house
        int spawnIndex = new Random().nextInt(currentHouse.getxSpawns().length);
        if(!isXandYSpawned(currentHouse.getxSpawns()[spawnIndex], currentHouse.getySpawns()[spawnIndex])) {
            count++;
            Bitmap smoke = BitmapFactory.decodeResource(this.getResources(), R.drawable.smoke);
            smoke = Bitmap.createScaledBitmap(smoke, 500, 100, false);
            SpriteAnimation animation2 = createAnimation(smoke, 1, 5, 100, 100, charWidth, charHeight);
            smoke.recycle();

            int r = new Random().nextInt(2); // spawns a drug if 0, spawns an innocent if 1
            if(r ==  0) {
                Bitmap charactter = BitmapFactory.decodeResource(this.getResources(), R.drawable.drug2);
                charactter = Bitmap.createScaledBitmap(charactter, 100, 100, false);
                SpriteAnimation animation1 = createAnimation(charactter, 1, 1, 100, 100, charWidth, charHeight);
                charactter.recycle();
                chars.add(new Character("drugs", animation1, currentHouse.getxSpawns()[spawnIndex], currentHouse.getySpawns()[spawnIndex], currentMillisecond, 2000, 5, 0, 0, 5));
            }
            else if (r == 1) {
                Bitmap charactter = BitmapFactory.decodeResource(this.getResources(), R.drawable.innocent);
                charactter = Bitmap.createScaledBitmap(charactter, 100, 100, false);
                SpriteAnimation animation1 = createAnimation(charactter, 1, 1, 100, 100, charWidth, charHeight);
                charactter.recycle();
                chars.add(new Character("innocent", animation1, currentHouse.getxSpawns()[spawnIndex], currentHouse.getySpawns()[spawnIndex], currentMillisecond, 2000, -5, 0, 0, 5));
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
            if (chars.get(i).getCurrentSpriteIndex() == chars.get(i).getSprites().size()-1) {
                points+= chars.get(i).getScore();
                chars.remove(i);

            }
        }
    }

    public void checkCharacterIfDue() {
        // this method checks the char if it is already due its duration
        // if the Character is due its duration, it will be removed
        for (int i= 0; i< chars.size(); i++) {
            if(currentMillisecond - chars.get(i).getSpawnTime() >= chars.get(i).getDuration()+1000 && !chars.get(i).isTouched() && !chars.get(i).isDue()) {
                if(chars.get(i).getName().equals("innocent")) {
                    chars.remove(i--);
                }
                else {
                    chars.get(i).setDue(true);
                    chars.get(i).setTimeOfDue(currentMillisecond);
                }
            }
        }

    }

    public void checkTouchedCharacters() {
        // checks if a character is touched
        for(int i = chars.size()-1; i>=0; i--) {
            if(!chars.get(i).isTouched()) {
                chars.get(i).setTouched(touchX, touchY);
                if (chars.get(i).isTouched() && !chars.get(i).isDue()) {
                    // sets the touched animation for the character
                    chars.get(i).setSpriteAnimation(touchedStartAnimationIndex, touchedEndAnimationIndex);
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

    public void moveCharacter () {
        // moves the character to the location of the main character
        // damages the main character as soon as they collide
        for(int i = 0; i < chars.size(); i ++ ) {
            if(chars.get(i).isDue()&& chars.get(i).getName().toString().equals("drugs")){
                float changeInX = mainChar.getX()+ 200 - chars.get(i).getOriginalX();
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

    public void moveHouse (int houseTranslationDuration) {
        // moves the house from source x to destination x based on the house movement rate
        currentHouse.moveHouseToDestination(currentMillisecond,houseTranslationDuration);
        if(!currentHouse.isMoving() && currentState == MOVE_STATE && currentState != GAME_STATE) {
            if (currentHouse.getX() <= houseDestinationX2 + 40) {
                if (stage.getNumberOfHouses() <= 1) {
                    currentState = END_STATE;
                }
                else if(stage.getNumberOfHouses() > 0) {
                    stage.deductHouse();
                    initialize(getContext());
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
            if (time > laseBgMoveChangeTime + bgMoveRate) {
                laseBgMoveChangeTime = time;
                bgX1--;
                bgX2--;
                if (bgX1 == -frameWidth) {
                    bgX1 = frameWidth;
                } else if (bgX2 == -frameWidth) {
                    bgX2 = frameWidth;
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

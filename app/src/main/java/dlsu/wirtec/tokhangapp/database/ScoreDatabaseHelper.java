package dlsu.wirtec.tokhangapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lyssa on 02/03/2017.
 */

public class ScoreDatabaseHelper extends SQLiteOpenHelper {

    public static final String SCHEMA = "TokhangDB";
    public static final int VERSION = 1;

    static final String QUERY_ON_CREATE
            = "CREATE TABLE IF NOT EXISTS " + Score.TABLE + " ( "
            + Score.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Score.COLUMN_NAME + " TEXT NOT NULL, "
            + Score.COLUMN_SCORE + " INTEGER, "
            + Score.COLUMN_DATECREATED + "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ); ";

    static final String QUERY_DROP_TABLE
            = "DROP TABLE IF EXISTS " + Score.TABLE + ";";


    public ScoreDatabaseHelper(Context context) {
        super(context, SCHEMA, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_ON_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(QUERY_DROP_TABLE);

        onCreate(db);
    }

    public long addScore(final String name, final int score){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues(2);
        cv.put(Score.COLUMN_NAME, name);
        cv.put(Score.COLUMN_SCORE, score);

        long affectedRowCount = db.insert(
                Score.TABLE,
                null,
                cv
        );

        return affectedRowCount;
    }

    public static final String[] SELECT = {
            Score.COLUMN_ID,
            Score.COLUMN_NAME,
            Score.COLUMN_SCORE
    };

    public Score getScore(final int id){
        Score s = null;
        SQLiteDatabase db = getReadableDatabase();

        final String[] args = {String.valueOf(id)};


        Cursor c = db.query(Score.TABLE,
                SELECT,
                Score.COLUMN_ID + " = ?", // where clause
                args, // where aguments
                null, // group by
                null, // having
                Score.COLUMN_SCORE); // order by

        if(c.moveToFirst()){
            final String name = c.getString(c.getColumnIndex(Score.COLUMN_NAME));
            final int score = c.getInt(c.getColumnIndex(Score.COLUMN_SCORE));
            final long dateCreated = c.getLong(c.getColumnIndex(Score.COLUMN_DATECREATED));

            s = new Score(id, name, score, dateCreated);
        }

        return s;
    };

    public Cursor getAllScores(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(Score.TABLE,
                null, // all
                null, // where clause
                null, // where aguments
                null, // group by
                null, // having
                Score.COLUMN_SCORE  +  " DESC"); // order by

        return c;
    }

    public int deleteScore(int id){
        SQLiteDatabase db = getWritableDatabase();

        final String[] whereArg = {String.valueOf(id)};

        int affectedRowsCount = db.delete(
                Score.TABLE,
                Score.COLUMN_ID + " = ?",
                whereArg
        );

        db.close();
        return affectedRowsCount;
    }

    public int deleteScore(Score s){
        int id = s.getID();
        s.setID(-1);
        return this.deleteScore(id);
    }
}

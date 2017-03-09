package dlsu.wirtec.tokhangapp.database;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lyssa on 02/03/2017.
 */

public class Score implements Parcelable {

    public static final String TABLE = "score";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SCORE = "score";
    public static final String COLUMN_DATECREATED = "dateCreated";

    private int id;
    private String name;
    private int score;
    private long dateCreated;

    public Score(String name, int score){
        this(-1, name, score, -1L);
    }

    Score(int id, String name, int score, long dateCreated){
        this.id = id;
        this.name = name;
        this.score = score;
        this.dateCreated = dateCreated;
    }

    void setID(int id){
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    protected Score(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.score = in.readInt();
        this.dateCreated = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.score);
        dest.writeLong(this.dateCreated);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Score> CREATOR = new Parcelable.Creator<Score>() {
        @Override
        public Score createFromParcel(Parcel in) {
            return new Score(in);
        }

        @Override
        public Score[] newArray(int size) {
            return new Score[size];
        }
    };
}
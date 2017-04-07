package dlsu.wirtec.tokhangapp.game;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Denmark on 3/23/2017.
 */

public class Stage implements Parcelable {

    public static final String INTENT_EXTRA_STAGE = "stage";

    public final int ID;
    private String stageName;
    private int numberOfHouses;
    private String[] typeOfHouse;
    private int spriteSpawningInterval;

    public Stage(int id, String stageName, int numberOfHouses, String[] typeOfHouse, int spriteSpawningInterval) {
        this.ID = id;
        this.stageName = stageName;
        this.numberOfHouses = numberOfHouses;
        this.typeOfHouse = typeOfHouse;
        this.spriteSpawningInterval = spriteSpawningInterval;
    }

    public void deductHouse() {
        numberOfHouses --;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(java.lang.String stageName) {
        this.stageName = stageName;
    }

    public int getNumberOfHouses() {
        return numberOfHouses;
    }

    public void setNumberOfHouses(int numberOfHouses) {
        this.numberOfHouses = numberOfHouses;
    }

    public String[] getTypeOfHouse() {
        return typeOfHouse;
    }

    public void setTypeOfHouse(String[] typeOfHouse) {
        this.typeOfHouse = typeOfHouse;
    }

    public int getSpriteSpawningInterval() {
        return spriteSpawningInterval;
    }

    public void setSpriteSpawningInterval(int spriteSpawningInterval) {
        this.spriteSpawningInterval = spriteSpawningInterval;
    }

    protected Stage(Parcel in) {
        ID = in.readInt();
        stageName = in.readString();
        numberOfHouses = in.readInt();
        typeOfHouse = in.createStringArray();
        spriteSpawningInterval = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(stageName);
        dest.writeInt(numberOfHouses);
        dest.writeStringArray(typeOfHouse);
        dest.writeInt(spriteSpawningInterval);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Stage> CREATOR = new Parcelable.Creator<Stage>() {
        @Override
        public Stage createFromParcel(Parcel in) {
            return new Stage(in);
        }

        @Override
        public Stage[] newArray(int size) {
            return new Stage[size];
        }
    };
}
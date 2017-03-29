package dlsu.wirtec.tokhangapp.game;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Denmark on 3/23/2017.
 */

public class Stage implements Parcelable {
    public static final String EXTRA_STAGE = "stage";

    private String stageName;
    private int numberOfHouses;
    private String[] typeOfHouse;
    private int spriteSpawningInterval;

    public Stage () {}

    public Stage(java.lang.String stageName, int numberOfHouses, String[] typeOfHouse, int spriteSpawningInterval) {
        this.stageName = stageName;
        this.numberOfHouses = numberOfHouses;
        this.typeOfHouse = typeOfHouse;
        this.spriteSpawningInterval = spriteSpawningInterval;
    }

    public void deductHouse() {
        numberOfHouses --;
    }

    public java.lang.String getStageName() {
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
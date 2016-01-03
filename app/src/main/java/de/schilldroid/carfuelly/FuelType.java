package de.schilldroid.carfuelly;

/**
 * Created by Simon on 02.01.2016.
 */
public class FuelType {

    private int mID;
    private String mName;

    public FuelType(int id, String name) {
        mID = id;
        mName = name;
    }

    public int getID() {
        return mID;
    }

    public String getName() {
        return mName;
    }
}

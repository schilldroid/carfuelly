package de.schilldroid.carfuelly;

import de.schilldroid.carfuelly.Utils.Consts;
import de.schilldroid.carfuelly.Utils.Logger;

/**
 * Created by Simon on 02.01.2016.
 */
public class Tank {

    private int mCarID;
    private String mFuelType;
    private int mCapacity;
    private String mName;


    public Tank(int id, String name, int capacity, String fuelType) {
        mCarID = id;
        mName = name;
        mCapacity = capacity;
        mFuelType = fuelType;
    }

    public String getName() {
        return mName;
    }

    public int getCapacity() {
        return mCapacity;
    }

    public int getCarID() {
        return mCarID;
    }

    public String getFuelType() {
        return mFuelType;
    }

    public void setCapacity(int capacity) {
        mCapacity = capacity;
    }

    public void setCapacity(String strCapacity) {
        try {
            mCapacity = Integer.parseInt(strCapacity);
        } catch (NumberFormatException ex) {
            Logger.log(Consts.Logger.LOG_ERROR, "[Tank]", "error parsing String '"+ strCapacity +"' to int -> defaulting to 0");
            mCapacity = 0;
        }
    }

    public void setFuelType(String fuelType) {
        mFuelType = fuelType;
    }

    public void setName(String name) {
        mName = name;
    }
}

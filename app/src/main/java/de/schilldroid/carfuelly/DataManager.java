package de.schilldroid.carfuelly;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Simon on 28.12.2015.
 */
public class DataManager {

    private HashMap<Integer, Car> mCars;




    private static DataManager mInstance = null;

    private DataManager() {

    }

    public DataManager getInstance() {
        if(mInstance == null)
            mInstance = new DataManager();
        return mInstance;
    }








    public void addCar(Car c) {
        mCars.put(getCarCount() + 1, c);
    }

    public Car getCarByID(int id) {
        return mCars.get(id);
    }

    public int getCarCount() {
        return mCars.size();
    }

}

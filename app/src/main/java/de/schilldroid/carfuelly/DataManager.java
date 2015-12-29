package de.schilldroid.carfuelly;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Simon on 28.12.2015.
 */
public class DataManager {

    private HashMap<Integer, Car> mCars;

    private static DataManager mInstance = null;

    private DataManager() {
        mCars = new HashMap<>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2010);
        cal.set(Calendar.MONTH, Calendar.JULY);
        Date date = cal.getTime();
        cal.set(Calendar.YEAR, 2011);
        cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
        Date purchase = cal.getTime();
        for(int i = 0; i < 10; i++) {
            mCars.put(i, new Car(i, "Car " + i, "blabla", "Porsche", "Panamera", date, 234, 2.5, "ultra super comfort line", purchase, 11123456, "POR S 1234"));
        }
    }

    public static DataManager getInstance() {
        if(mInstance == null)
            mInstance = new DataManager();
        return mInstance;
    }

    public void addCar(Car c) {
        mCars.put(-1, c);
    }

    public Car getCarByID(int id) {
        return mCars.get(id);
    }

    public int getCarCount() {
        return mCars.size();
    }

    public ArrayList<Car> getCars() {
        return new ArrayList<Car>(mCars.values());
    }
}

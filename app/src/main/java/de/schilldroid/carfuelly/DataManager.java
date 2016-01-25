package de.schilldroid.carfuelly;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Simon on 28.12.2015.
 */
public class DataManager {

    // just for testing purposes
    private int ID_COUNT;

    private HashMap<Integer, Car> mCars;
    private ArrayList<FuelType> mFuelTypes;

    private static DataManager mInstance = null;

    private DataManager() {
        initCars();
    }


    public void initCars() {

        ID_COUNT = 0;
        mCars = new HashMap<>();
        Calendar cal = Calendar.getInstance();
        Date date, purchase;

        cal.set(Calendar.YEAR, 2013);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        date = cal.getTime();
        cal.set(Calendar.YEAR, 2015);
        cal.set(Calendar.MONTH, Calendar.OCTOBER);
        cal.set(Calendar.DAY_OF_MONTH, 5);
        purchase = cal.getTime();
        Car c = new Car(ID_COUNT++, "S 2007", "mein Privatwagen", "Opel", "Corsa D", date, 90, 1.4, "standard line", purchase, 8500, "GEL S 2007", 6.5, 5.5, 6.0);
        mCars.put(c.getID(), c);

        cal.set(Calendar.YEAR, 2007);
        cal.set(Calendar.MONTH, Calendar.JULY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        date = cal.getTime();
        cal.set(Calendar.YEAR, 2013);
        cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 23);
        purchase = cal.getTime();
        c = new Car(ID_COUNT++, "F 2889", "Franzis Auto", "Seat", "Leon", date, 106, 1.8, "Sportaustattung", purchase, 7000, "KLE F 2889", 8.0, 7.0, 7.5);
        mCars.put(c.getID(), c);

        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        date = cal.getTime();
        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 5);
        purchase = cal.getTime();
        c = new Car(ID_COUNT++, "Traum", "mein Traumwagen", "Porsche", "Panamera", date, 310, 2.4, "super ultra comfort sports line", purchase, 100000, "POR SCHE 123", 12.0, 10.0, 11.0);
        mCars.put(c.getID(), c);

        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        date = cal.getTime();
        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 5);
        purchase = cal.getTime();
        c = new Car(ID_COUNT++, "blaa", "mein Traumwagen", "Porsche", "Panamera", date, 310, 2.4, "super ultra comfort sports line", purchase, 100000, "POR SCHE 123", 12.0, 10.0, 11.0);
        mCars.put(c.getID(), c);

        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        date = cal.getTime();
        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 5);
        purchase = cal.getTime();
        c = new Car(ID_COUNT++, "foo", "mein Traumwagen", "Porsche", "Panamera", date, 310, 2.4, "super ultra comfort sports line", purchase, 100000, "POR SCHE 123", 12.0, 10.0, 11.0);
        mCars.put(c.getID(), c);

    }


    public static DataManager getInstance() {
        if(mInstance == null)
            mInstance = new DataManager();
        return mInstance;
    }

    public Car createNewCar() {
        Car c = new Car();
        c.setID(ID_COUNT++);
        mCars.put(c.getID(), c);
        return c;
    }



    public void deleteCar(int id) {
        mCars.remove(id);
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

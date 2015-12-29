package de.schilldroid.carfuelly;

import java.util.Date;

/**
 * Created by Simon on 28.12.2015.
 */
public class Car {

    private int mID;
    private String mName;
    private String mDescription;
    private String mManufacturer;
    private String mModel;
    private Date mYear;
    private int mPerformance;
    private double mEngine;
    private String mConfiguration;
    private Date mPurchaseDate;
    private int mPrice;
    private String mRegistration;


    public Car() {

    }

    public Car(String name, String desc, String manufacturer, String model, Date year, int performance, double engine, String config, Date purchase, int price, String reg) {
        this(-1, name, desc, manufacturer, model, year, performance, engine, config, purchase, price, reg);
    }

    public Car(int id, String name, String desc, String manufacturer, String model, Date year, int performance, double engine, String config, Date purchase, int price, String reg) {
        mID = id;
        mName = name;
        mDescription = desc;
        mManufacturer = manufacturer;
        mModel = model;
        mYear = year;
        mPerformance = performance;
        mEngine = engine;
        mConfiguration = config;
        mPurchaseDate = purchase;
        mPrice = price;
        mRegistration = reg;
    }


    public int getID() {
        return mID;
    }

    public String getName() {
        return mName;
    }


}

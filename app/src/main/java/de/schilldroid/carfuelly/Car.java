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
    private Date mFirstRegistration;
    private int mPower;
    private double mEngine;
    private String mConfiguration;
    private Date mPurchaseDate;
    private int mPrice;
    private String mLicensePlate;
    private double mConsumptionUrban;
    private double mConsumptionExtraUrban;
    private double mConsumptionCombined;


    public Car() {
        mID = -1;
    }

    public Car(String name, String desc, String manufacturer, String model, Date firstReg,
               int performance, double engine, String config, Date purchase, int price, String plate, double conUrban, double conExtra, double conCom) {
        this(-1, name, desc, manufacturer, model, firstReg, performance, engine, config, purchase, price, plate, conUrban, conExtra, conCom);
    }

    public Car(int id, String name, String desc, String manufacturer, String model, Date firstReg,
               int performance, double engine, String config, Date purchase, int price, String plate, double conUrban, double conExtra, double conCom) {
        mID = id;
        mName = name;
        mDescription = desc;
        mManufacturer = manufacturer;
        mModel = model;
        mFirstRegistration = firstReg;
        mPower = performance;
        mEngine = engine;
        mConfiguration = config;
        mPurchaseDate = purchase;
        mPrice = price;
        mLicensePlate = plate;
        mConsumptionUrban = conUrban;
        mConsumptionExtraUrban = conExtra;
        mConsumptionCombined = conCom;
    }

    public void setParams(String name, String desc, String manufacturer, String model, Date firstReg, int performance, double engine, String config,
                          Date purchase, int price, String plate, double conUrban, double conExtra, double conCom) {
        mName = name;
        mDescription = desc;
        mManufacturer = manufacturer;
        mModel = model;
        mFirstRegistration = firstReg;
        mPower = performance;
        mEngine = engine;
        mConfiguration = config;
        mPurchaseDate = purchase;
        mPrice = price;
        mLicensePlate = plate;
        mConsumptionUrban = conUrban;
        mConsumptionExtraUrban = conExtra;
        mConsumptionCombined = conCom;
    }


    public void setID(int id) {
        mID = id;
    }

    public int getID() {
        return mID;
    }

    public String getName() {
        return mName;
    }

    public String getManufacturer() {
        return mManufacturer;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getModel() {
        return mModel;
    }

    public Date getFirstRegistration() {
        return mFirstRegistration;
    }

    public int getPower() {
        return mPower;
    }

    public double getEngine() {
        return mEngine;
    }

    public String getConfiguration() {
        return mConfiguration;
    }

    public Date getPurchaseDate() {
        return mPurchaseDate;
    }

    public int getPrice() {
        return mPrice;
    }

    public String getLicensePlate() {
        return mLicensePlate;
    }

    public double getConsumptionUrban() {
        return mConsumptionUrban;
    }

    public double getConsumptionExtraUrban() {
        return mConsumptionExtraUrban;
    }

    public double getConsumptionCombined() {
        return mConsumptionCombined;
    }
}

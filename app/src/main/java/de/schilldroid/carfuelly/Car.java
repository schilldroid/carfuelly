package de.schilldroid.carfuelly;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Simon on 28.12.2015.
 */
public class Car {

    private static SimpleDateFormat mDateFormatFirstRegistration = new SimpleDateFormat("MM.yyyy");
    private static SimpleDateFormat mDateFormatPurchase = new SimpleDateFormat("dd.MM.yyyy");

    public static SimpleDateFormat getDateFormatFirstRegistration() {
        return mDateFormatFirstRegistration;
    }

    public static SimpleDateFormat getDateFormatPurchase() {
        return mDateFormatPurchase;
    }


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
    private String mImageFilename;


    public Car() {
        this(-1, "", "", "", "", null, -1, -1, "", null, -1, "", -1, -1, -1);
    }

    public Car(String name, String desc, String manufacturer, String model, Date firstReg,
               int power, double engine, String config, Date purchase, int price, String plate, double conUrban, double conExtra, double conCom) {
        this(-1, name, desc, manufacturer, model, firstReg, power, engine, config, purchase, price, plate, conUrban, conExtra, conCom);
    }

    public Car(int id, String name, String desc, String manufacturer, String model, Date firstReg,
               int power, double engine, String config, Date purchase, int price, String plate, double conUrban, double conExtra, double conCom) {
        setID(id);
        setName(name);
        setDescription(desc);
        setManufacturer(manufacturer);
        setModel(model);
        setFirstRegistration(firstReg);
        setPower(power);
        setEngine(engine);
        setConfiguration(config);
        setPurchaseDate(purchase);
        setPrice(price);
        setLicensePlate(plate);
        setConsumptionUrban(conUrban);
        setConsumptionExtraUrban(conExtra);
        setConsumptionCombined(conCom);
    }

    public void setParams(String name, String desc, String manufacturer, String model, String firstReg, String power, String engine, String config,
                          String purchase, String price, String plate, String conUrban, String conExtra, String conCom) {
        setName(name);
        setDescription(desc);
        setManufacturer(manufacturer);
        setModel(model);
        setFirstRegistration(firstReg);
        setPower(power);
        setEngine(engine);
        setConfiguration(config);
        setPurchaseDate(purchase);
        setPrice(price);
        setLicensePlate(plate);
        setConsumptionUrban(conUrban);
        setConsumptionExtraUrban(conExtra);
        setConsumptionCombined(conCom);
    }

    public void setStrParams(String name, String desc, String manufacturer, String model, String firstReg, String power, String engine, String config,
                          String purchase, String price, String plate, String conUrban, String conExtra, String conCom) {

        setName(name);
        setDescription(desc);
        setManufacturer(manufacturer);
        setModel(model);
        setFirstRegistration(firstReg);
        setPower(power);
        setEngine(engine);
        setConfiguration(config);
        setPurchaseDate(purchase);
        setPrice(price);
        setLicensePlate(plate);
        setConsumptionUrban(conUrban);
        setConsumptionExtraUrban(conExtra);
        setConsumptionCombined(conCom);
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
    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }
    public void setDescription(String description) {
        mDescription = description;
    }

    public String getManufacturer() {
        return mManufacturer;
    }
    public void setManufacturer(String manufacturer) {
        mManufacturer = manufacturer;
    }

    public String getModel() {
        return mModel;
    }
    public void setModel(String model) {
        mModel = model;
    }

    public Date getFirstRegistration() {
        return mFirstRegistration;
    }
    public String getStrFirstRegistration() {
        if(mFirstRegistration == null) {
            return "";
        }
        return mDateFormatFirstRegistration.format(mFirstRegistration.getTime());
    }
    public void setFirstRegistration(Date firstRegistration) {
        mFirstRegistration = firstRegistration;
    }
    public void setFirstRegistration(String firstRegistration) {
        try {
            mFirstRegistration = mDateFormatFirstRegistration.parse(firstRegistration);
        } catch (ParseException e) {
            mFirstRegistration = null;
        }
    }

    public int getPower() {
        return mPower;
    }
    public String getStrPower() {
        if(mPower < 0) {
            return "";
        }
        return String.valueOf(mPower);
    }
    public void setPower(int power) {
        mPower = power;
    }
    public void setPower(String power) {
        try {
            mPower = Integer.parseInt(power);
        } catch (NumberFormatException e) {
            mPower = -1;
        }

    }

    public double getEngine() {
        return mEngine;
    }
    public String getStrEngine() {
        if(mEngine < 0) {
            return "";
        }
        return String.valueOf(mEngine);
    }
    public void setEngine(double engine) {
        mEngine = engine;
    }
    public void setEngine(String engine) {
        try {
            mEngine = Double.parseDouble(engine);
        } catch (NumberFormatException e) {
            mEngine = -1;
        }
    }

    public String getConfiguration() {
        return mConfiguration;
    }
    public void setConfiguration(String configuration) {
        mConfiguration = configuration;
    }

    public Date getPurchaseDate() {
        return mPurchaseDate;
    }
    public String getStrPurchaseDate() {
        if(mPurchaseDate == null) {
            return "";
        }
        return mDateFormatPurchase.format(mPurchaseDate.getTime());
    }
    public void setPurchaseDate(Date purchaseDate) {
        mPurchaseDate = purchaseDate;
    }
    public void setPurchaseDate(String purchaseDate) {
        try {
            mPurchaseDate = mDateFormatPurchase.parse(purchaseDate);
        } catch (ParseException e) {
            mPurchaseDate = null;
        }
    }


    public int getPrice() {
        return mPrice;
    }
    public String getStrPrice() {
        if(mPrice < 0) {
            return "";
        }
        return String.valueOf(mPrice);
    }
    public void setPrice(int price) {
        mPrice = price;
    }
    public void setPrice(String price) {
        // TODO handle comma or point as divider for euro and cent
        try {
            mPrice = Integer.parseInt(price);
        } catch (NumberFormatException e) {
            mPrice = -1;
        }
    }

    public String getLicensePlate() {
        return mLicensePlate;
    }
    public void setLicensePlate(String licensePlate) {
        mLicensePlate = licensePlate;
    }


    public double getConsumptionUrban() {
        return mConsumptionUrban;
    }
    public String getStrConsumptionUrban() {
        if(mConsumptionUrban < 0) {
            return "";
        }
        return String.valueOf(mConsumptionUrban);
    }
    public void setConsumptionUrban(double consumptionUrban) {
        mConsumptionUrban = consumptionUrban;
    }
    public void setConsumptionUrban(String consumptionUrban) {
        try {
            mConsumptionUrban = Double.parseDouble(consumptionUrban);
        } catch (NumberFormatException e) {
            mConsumptionUrban = -1;
        }
    }


    public double getConsumptionExtraUrban() {
        return mConsumptionExtraUrban;
    }
    public String getStrConsumptionExtraUrban() {
        if(mConsumptionExtraUrban < 0) {
            return "";
        }
        return String.valueOf(mConsumptionExtraUrban);
    }
    public void setConsumptionExtraUrban(double consumptionExtraUrban) {
        mConsumptionExtraUrban = consumptionExtraUrban;
    }
    public void setConsumptionExtraUrban(String consumptionExtraUrban) {
        try {
            mConsumptionExtraUrban = Double.parseDouble(consumptionExtraUrban);
        } catch (NumberFormatException e) {
            mConsumptionExtraUrban = -1;
        }
    }

    public double getConsumptionCombined() {
        return mConsumptionCombined;
    }
    public String getStrConsumptionCombined() {
        if(mConsumptionCombined < 0) {
            return "";
        }
        return String.valueOf(mConsumptionCombined);
    }
    public void setConsumptionCombined(double consumptionCombined) {
        mConsumptionCombined = consumptionCombined;
    }
    public void setConsumptionCombined(String consumptionCombined) {
        try {
            mConsumptionCombined = Double.parseDouble(consumptionCombined);
        } catch (NumberFormatException e) {
            mConsumptionCombined = -1;
        }
    }

    public String getImageFilename() {
        return mImageFilename;
    }

    public void setImageFilename(String imageFilename) {
        mImageFilename = imageFilename;
    }
}

package de.schilldroid.carfuelly.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.schilldroid.carfuelly.Car;
import de.schilldroid.carfuelly.CarDetailTankListAdapter;
import de.schilldroid.carfuelly.CarDetailsDatePickerFragment;
import de.schilldroid.carfuelly.DataManager;
import de.schilldroid.carfuelly.R;
import de.schilldroid.carfuelly.Utils.Consts;
import de.schilldroid.carfuelly.Utils.Logger;

/**
 * Created by Simon on 30.12.2015.
 */
public class CarDetailsActivity extends AppCompatActivity implements CarDetailsDatePickerFragment.OnDateAppliedListener {


    private SimpleDateFormat mDateFormatFirstRegistration;
    private SimpleDateFormat mDateFormatPurchase;

    private EditText mViewName;
    private EditText mViewDescription;
    private EditText mViewManufacturer;
    private EditText mViewModel;
    private EditText mViewFirstRegistration;
    private EditText mViewPower;
    private EditText mViewEngine;
    private EditText mViewConfiguration;
    private EditText mViewPurchaseDate;
    private EditText mViewPrice;
    private EditText mViewLicensePlate;
    private EditText mViewConsumptionUrban;
    private EditText mViewConsumptionExtraUrban;
    private EditText mViewConsumptionCombined;
    private ListView mViewTankList;
    private CarDetailTankListAdapter mTankListAdapter;
    private ImageView mViewAddTank;

    private int mContext;

    private Car mCar;
    private Toolbar mToolbar;
    private int mRequestedCarID;

    private String mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        initComponents();

        mDateFormatFirstRegistration = new SimpleDateFormat("MM.yyyy");
        mDateFormatPurchase = new SimpleDateFormat("dd.MM.yyyy");


        Intent intent = getIntent();
        mContext = intent.getIntExtra(Consts.CarDetails.CONTEXT_KEY, Consts.CarDetails.CONTEXT_CREATE);
        switch(mContext) {
            case Consts.CarDetails.CONTEXT_CREATE:
                mTitle = "Add Car";
                mCar = new Car();
                break;
            case Consts.CarDetails.CONTEXT_MODIFY:
                mTitle = "Modify Car";
                mRequestedCarID = intent.getIntExtra(Consts.CarDetails.PARAM_CAR_ID, -1);
                if(mRequestedCarID >= 0) {
                    mCar = DataManager.getInstance().getCarByID(mRequestedCarID);
                    setFieldValues();
                }
                else {
                    Logger.log(Consts.Logger.LOG_ERROR, "[CarDetailsActivity]", "unable to load car details for ID = -1");
                    mCar = new Car();
                }
                break;
            default:
                mTitle = "Car Details";
                break;
        }


        // add date picker
        mViewFirstRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new CarDetailsDatePickerFragment();
                Bundle args = new Bundle();
                args.putInt(Consts.CarDetails.DATE_PICKER_CONTEXT, Consts.CarDetails.DATE_PICKER_CONTEXT_FIRST_REGISTRATION);
                datePicker.setArguments(args);
                datePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });
        mViewPurchaseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new CarDetailsDatePickerFragment();
                Bundle args = new Bundle();
                args.putInt(Consts.CarDetails.DATE_PICKER_CONTEXT, Consts.CarDetails.DATE_PICKER_CONTEXT_PURCHASE_DATE);
                datePicker.setArguments(args);
                datePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });

        // init listview
        mViewTankList = (ListView) findViewById(R.id.car_details_tank_list);
        mTankListAdapter = new CarDetailTankListAdapter(this);
        mViewTankList.setAdapter(mTankListAdapter);

        // implement add tank button
        mViewAddTank = (ImageView) findViewById(R.id.car_details_tank_add);
        mViewAddTank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTankListAdapter.addTank();
            }
        });

        // setup toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(mTitle);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    public void initComponents() {
        // initialize components
        mViewName = (EditText) findViewById(R.id.car_details_name);
        mViewDescription = (EditText) findViewById(R.id.car_details_description);
        mViewManufacturer = (EditText) findViewById(R.id.car_details_manufacturer);
        mViewModel = (EditText) findViewById(R.id.car_details_model);
        mViewFirstRegistration = (EditText) findViewById(R.id.car_details_first_registration);
        mViewPower = (EditText) findViewById(R.id.car_details_power);
        mViewEngine = (EditText) findViewById(R.id.car_details_engine);
        mViewConfiguration = (EditText) findViewById(R.id.car_details_configuration);
        mViewPurchaseDate = (EditText) findViewById(R.id.car_details_purchase_date);
        mViewPrice = (EditText) findViewById(R.id.car_details_price);
        mViewLicensePlate = (EditText) findViewById(R.id.car_details_license_plate);
        mViewConsumptionUrban = (EditText) findViewById(R.id.car_details_consumption_urban);
        mViewConsumptionExtraUrban = (EditText) findViewById(R.id.car_details_consumption_extraurban);
        mViewConsumptionCombined = (EditText) findViewById(R.id.car_details_consumption_combined);
    }



    private void setFieldValues() {
        // pre define field values when opened as modification activity
        mViewName.setText(mCar.getName());
        mViewDescription.setText(mCar.getDescription());
        mViewManufacturer.setText(mCar.getManufacturer());
        mViewModel.setText(mCar.getModel());
        mViewFirstRegistration.setText(mDateFormatFirstRegistration.format(mCar.getFirstRegistration()));
        mViewPower.setText(""+mCar.getPower());
        mViewEngine.setText(""+mCar.getEngine());
        mViewConfiguration.setText(mCar.getConfiguration());
        mViewPurchaseDate.setText(mDateFormatPurchase.format(mCar.getPurchaseDate()));
        mViewPrice.setText(""+ mCar.getPrice());
        mViewLicensePlate.setText(mCar.getLicensePlate());
        mViewConsumptionUrban.setText(""+mCar.getConsumptionUrban());
        mViewConsumptionExtraUrban.setText(""+ mCar.getConsumptionExtraUrban());
        mViewConsumptionCombined.setText(""+ mCar.getConsumptionCombined());
    }




    // This method computes the nedded height of the listview and applies this height to it.
    // So the listview will grow with its items, instead of just showing the first item. This behaviour
    // is a known issue of the listview in combination with a scrollview.
    public void updateTankListViewHeightBasedOnChildren() {

        if (mTankListAdapter == null) {
            // pre-condition
            return;
        }

        // compute total height of children
        int totalHeight = 0;
        for (int i = 0; i < mTankListAdapter.getCount(); i++) {
            View listItem = mTankListAdapter.getView(i, null, mViewTankList);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        // apply computed height to listview
        ViewGroup.LayoutParams params = mViewTankList.getLayoutParams();
        params.height = totalHeight + (mViewTankList.getDividerHeight() * (mViewTankList.getCount() - 1));
        mViewTankList.setLayoutParams(params);
        mViewTankList.requestLayout();
    }



    @Override
    public void onDateApplied(int year, int month, int day, int context) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);

        if(context == Consts.CarDetails.DATE_PICKER_CONTEXT_FIRST_REGISTRATION) {
            mViewFirstRegistration.setText(mDateFormatFirstRegistration.format(c.getTime()));
        }
        else if(context == Consts.CarDetails.DATE_PICKER_CONTEXT_PURCHASE_DATE) {
            mViewPurchaseDate.setText(mDateFormatPurchase.format(c.getTime()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.car_details_toolbar_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle interaction with toolbar actions
        int itemId = item.getItemId();
        CoordinatorLayout clayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout_car_details);
        switch(itemId) {
            case R.id.car_detail_action_apply:
                boolean dataVerified = checkData();
                if(dataVerified == true) {
                    applyData();
                    if(mContext == Consts.CarDetails.CONTEXT_CREATE) {
                        DataManager.getInstance().addCar(mCar);
                    }
                    Snackbar.make(clayout, "Car succesfully added!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    setResult(Consts.CarDetails.CAR_DETAILS_OK);
                    finish();
                }
                else {
                    Snackbar.make(clayout, "Error: Check your input!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
                return dataVerified;
            case android.R.id.home:
                setResult(Consts.CarDetails.CAR_DETAILS_CANCELED);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {
        setResult(Consts.CarDetails.CAR_DETAILS_CANCELED);
        super.onBackPressed();
    }



    private boolean checkData() {
        // TODO check each field for correct input
        return true;
    }

    private boolean applyData() {

        try {
            // parse field values
            String name = mViewName.getText().toString();
            String desc = mViewDescription.getText().toString();
            String manu = mViewManufacturer.getText().toString();
            String model = mViewModel.getText().toString();
            Date firstReg = (new SimpleDateFormat("MM.yyyy")).parse(mViewFirstRegistration.getText().toString());
            int power = Integer.parseInt(mViewPower.getText().toString());
            double engine = Double.parseDouble(mViewEngine.getText().toString());
            String config = mViewConfiguration.getText().toString();
            Date purchaseDate = (new SimpleDateFormat("dd.MM.yyyy")).parse(mViewPurchaseDate.getText().toString());
            int price = Integer.parseInt(mViewPrice.getText().toString());
            String plate = mViewLicensePlate.getText().toString();
            double conUrban = Double.parseDouble(mViewConsumptionUrban.getText().toString());
            double conExtraUrban = Double.parseDouble(mViewConsumptionExtraUrban.getText().toString());
            double conCombined = Double.parseDouble(mViewConsumptionCombined.getText().toString());

            // set parameter of car
            mCar.setParams(name, desc, manu, model, firstReg, power, engine, config, purchaseDate, price, plate, conUrban, conExtraUrban, conCombined);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return true;
    }
}

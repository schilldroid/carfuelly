package de.schilldroid.carfuelly.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
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

import java.io.File;
import java.util.Calendar;

import de.schilldroid.carfuelly.BitmapReceiver;
import de.schilldroid.carfuelly.LoadBitmapWorkerTask;
import de.schilldroid.carfuelly.LoadDownsampledBitmapWorkerTask;
import de.schilldroid.carfuelly.Car;
import de.schilldroid.carfuelly.CarDetailTankListAdapter;
import de.schilldroid.carfuelly.CarDetailsDatePickerFragment;
import de.schilldroid.carfuelly.DataManager;
import de.schilldroid.carfuelly.R;
import de.schilldroid.carfuelly.SaveBitmapWorkerTask;
import de.schilldroid.carfuelly.Utils.Consts;
import de.schilldroid.carfuelly.Utils.Logger;
import de.schilldroid.carfuelly.Utils.Tools;

/**
 * Created by Simon on 30.12.2015.
 */
public class CarDetailsActivity extends AppCompatActivity implements CarDetailsDatePickerFragment.OnDateAppliedListener, BitmapReceiver {

    private String mClassName = "[CarDetailsActivity]";

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
    private ImageView mToolbarImage;
    private FloatingActionButton mFAB;


    private int mContext;

    private Car mCar;
    private Toolbar mToolbar;
    private int mRequestedCarID;

    private String mTitle;
    private Uri mChosenCarImageUri;
    private Bitmap mDownSampledBitmap;

    private File mCarImageFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_details_activity_layout);

        mChosenCarImageUri = null;
        mDownSampledBitmap = null;
        initComponents();

        Intent intent = getIntent();
        mContext = intent.getIntExtra(Consts.CarDetails.CONTEXT_KEY, Consts.CarDetails.CONTEXT_CREATE);
        switch(mContext) {
            case Consts.CarDetails.CONTEXT_CREATE:
                mTitle = "Add Car";
                mCar = DataManager.getInstance().createNewCar();
                break;
            case Consts.CarDetails.CONTEXT_MODIFY:
                mTitle = "Modify Car";
                mRequestedCarID = intent.getIntExtra(Consts.CarDetails.PARAM_CAR_ID, -1);
                if(mRequestedCarID >= 0) {
                    mCar = DataManager.getInstance().getCarByID(mRequestedCarID);
                }
                else {
                    Logger.log(Consts.Logger.LOG_ERROR, mClassName, "unable to load car details for ID = -1");
                    mCar = DataManager.getInstance().createNewCar();
                }
                break;
            default:
                mTitle = "Car Details";
                break;
        }

        mCarImageFile = new File(Tools.getExternalStorageImagesDirectory().getAbsolutePath(), Consts.General.CAR_IMAGES_NAME_PREFIX + mCar.getID() + Consts.General.IMAGES_SUFFIX);
        setFieldValues();
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
        mToolbar = (Toolbar) findViewById(R.id.car_details_toolbar);
        mToolbar.setTitle(mTitle);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        mFAB = (FloatingActionButton) findViewById(R.id.car_details_fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Choose image"), Consts.CarDetails.REQUEST_PICK_IMAGE);
            }
        });

        mToolbarImage = (ImageView) findViewById(R.id.car_details_toolbar_image);

    }





    private void setFieldValues() {
        // pre define field values when opened as modification activity
        mViewName.setText(mCar.getName());
        mViewDescription.setText(mCar.getDescription());
        mViewManufacturer.setText(mCar.getManufacturer());
        mViewModel.setText(mCar.getModel());
        mViewFirstRegistration.setText(mCar.getStrFirstRegistration());
        mViewPower.setText(mCar.getStrPower());
        mViewEngine.setText(mCar.getStrEngine());
        mViewConfiguration.setText(mCar.getConfiguration());
        mViewPurchaseDate.setText(mCar.getStrPurchaseDate());
        mViewPrice.setText(mCar.getStrPrice());
        mViewLicensePlate.setText(mCar.getLicensePlate());
        mViewConsumptionUrban.setText(mCar.getStrConsumptionUrban());
        mViewConsumptionExtraUrban.setText(mCar.getStrConsumptionExtraUrban());
        mViewConsumptionCombined.setText(mCar.getStrConsumptionCombined());
        showToolbarImage();
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
            mViewFirstRegistration.setText(Car.getDateFormatFirstRegistration().format(c.getTime()));
        }
        else if(context == Consts.CarDetails.DATE_PICKER_CONTEXT_PURCHASE_DATE) {
            mViewPurchaseDate.setText(Car.getDateFormatPurchase().format(c.getTime()));
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
                    Snackbar.make(clayout, "Car succesfully modified/created!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    setResult(Consts.CarDetails.CAR_DETAILS_OK);
                    finish();
                }
                else {
                    Snackbar.make(clayout, "Error: Check your input!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
                return dataVerified;
            case android.R.id.home:
                if(mContext == Consts.CarDetails.CONTEXT_CREATE) {
                    DataManager.getInstance().deleteCar(mCar.getID());
                }
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

        // get field values
        String name = mViewName.getText().toString();
        String desc = mViewDescription.getText().toString();
        String manu = mViewManufacturer.getText().toString();
        String model = mViewModel.getText().toString();
        String firstReg = mViewFirstRegistration.getText().toString();
        String power = mViewPower.getText().toString();
        String engine = mViewEngine.getText().toString();
        String config = mViewConfiguration.getText().toString();
        String purchaseDate = mViewPurchaseDate.getText().toString();
        String price = mViewPrice.getText().toString();
        String plate = mViewLicensePlate.getText().toString();
        String conUrban = mViewConsumptionUrban.getText().toString();
        String conExtraUrban = mViewConsumptionExtraUrban.getText().toString();
        String conCombined = mViewConsumptionCombined.getText().toString();

        // hey car, i have some data for you. The thing is that the data are given as Strings, but im sure you can handle that ;)
        mCar.setParams(name, desc, manu, model, firstReg, power, engine, config, purchaseDate, price, plate, conUrban, conExtraUrban, conCombined);

        if(mChosenCarImageUri != null) {
            applyCarImage();
        }

        return true;
    }

    private void applyCarImage() {
        Logger.log(Consts.Logger.LOG_DEBUG, mClassName, "applying car image for car: " + mCar.toString());

        Logger.log(Consts.Logger.LOG_DEBUG, mClassName, "target filepath: " + mCarImageFile.getAbsolutePath());

        // create new bitmapWorker to save the image, that the user has chosen, in an appfolder
        SaveBitmapWorkerTask task = new SaveBitmapWorkerTask(this, mDownSampledBitmap, mCarImageFile.getAbsolutePath());
        task.execute();

        mCar.setImageFilename(mCarImageFile.getName());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // if the user has picked an image
        if (requestCode == Consts.CarDetails.REQUEST_PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            // query the URI of that image
            mChosenCarImageUri = data.getData();
            Logger.log(Consts.Logger.LOG_DEBUG, "[CarDetailsActivity]", "data received from gallery picker: " + mChosenCarImageUri);

            // and show that image
            showToolbarImage();
        }
    }

    private void showToolbarImage() {


        // if an URI is set ...
        if(mChosenCarImageUri != null) {
            // start new bitmapWorker to load a downsampled version of that image into the imageview
            LoadDownsampledBitmapWorkerTask task = new LoadDownsampledBitmapWorkerTask(this, mToolbarImage, mChosenCarImageUri, this);
            task.execute();
        }
        // else if there is already an image assigned to this car ...
        else if(mCar.getImageFilename() != null) {
            // start new bitmapWorker to load the assigned image from app folder
            //LoadBitmapWorkerTask task = new LoadBitmapWorkerTask(mToolbarImage, mCarImageFile.getAbsolutePath());
            //task.execute();
            Tools.loadCarImageFromFile(mToolbarImage, mCar, null);
        }
        else {
            Logger.log(Consts.Logger.LOG_DEBUG, mClassName, "neither uri nor filename set, displaying default image!");
            mToolbarImage.setImageResource(R.drawable.cockpit_def);
        }


    }


    @Override
    public void onReceiveBitmap(Bitmap bitmap) {
        mDownSampledBitmap = bitmap;
    }
}

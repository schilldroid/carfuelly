package de.schilldroid.carfuelly.Activities;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.schilldroid.carfuelly.Car;
import de.schilldroid.carfuelly.CarDetailTankListAdapter;
import de.schilldroid.carfuelly.CarDetailsDatePickerFragment;
import de.schilldroid.carfuelly.Carfuelly;
import de.schilldroid.carfuelly.R;
import de.schilldroid.carfuelly.Utils.Consts;

/**
 * Created by Simon on 30.12.2015.
 */
public class CarDetailsActivity extends AppCompatActivity implements CarDetailsDatePickerFragment.OnDateAppliedListener {


    private EditText mViewFirstRegistration;
    private EditText mViewPurchaseDate;
    private ListView mViewTankList;
    private CarDetailTankListAdapter mTankListAdapter;
    private ImageView mViewAddTank;


    private Car mCar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_details);

        // add date picker
        mViewFirstRegistration = (EditText) findViewById(R.id.car_details_first_registration);
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
        mViewPurchaseDate = (EditText) findViewById(R.id.car_details_purchase_date);
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM.yyyy");
            mViewFirstRegistration.setText(dateFormat.format(c.getTime()));
        }
        else if(context == Consts.CarDetails.DATE_PICKER_CONTEXT_PURCHASE_DATE) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            mViewPurchaseDate.setText(dateFormat.format(c.getTime()));
        }
    }
}

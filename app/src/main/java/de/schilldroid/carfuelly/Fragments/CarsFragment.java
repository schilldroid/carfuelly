package de.schilldroid.carfuelly.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.BundleCompat;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import de.schilldroid.carfuelly.Activities.CarDetailsActivity;
import de.schilldroid.carfuelly.CarCardListAdapter;
import de.schilldroid.carfuelly.R;
import de.schilldroid.carfuelly.Utils.Consts;
import de.schilldroid.carfuelly.Utils.Logger;

/**
 * Created by Simon on 22.12.2015.
 */
public class CarsFragment extends BaseFragment {


    private ListView mCarListView;
    private CarCardListAdapter mCarListAdapter;


    public CarsFragment() {
        // Empty constructor required for fragment subclasses
        mTitle = "Cars";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // apply layout to fragment
        View rootView = inflater.inflate(R.layout.fragment_cars, container, false);

        // get listview instance of that fragment layout
        mCarListView = (ListView) rootView.findViewById(R.id.car_card_list);

        // create list adapter
        mCarListAdapter = new CarCardListAdapter(this);

        // apply list adapter
        mCarListView.setAdapter(mCarListAdapter);
        /*mCarListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int carID = position;
                        Intent intent = new Intent(mAppContext, CarDetailsActivity.class);
                        intent.putExtra(Consts.CarDetails.CONTEXT_KEY, Consts.CarDetails.CONTEXT_MODIFY);
                        intent.putExtra(Consts.CarDetails.PARAM_CAR_ID, carID);
                        // to get result, onActivityResult ist called on finishing CarDetailsActivity
                        startActivityForResult(intent, Consts.CarDetails.REQUEST_CAR_DETAILS);
            }
        });*/

        FloatingActionButton fab = mAppContext.getFab();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View clayout = mAppContext.findViewById(R.id.coordinator_layout_main);
                Snackbar.make(clayout, "Cars", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent = new Intent(mAppContext, CarDetailsActivity.class);
                intent.putExtra(Consts.CarDetails.CONTEXT_KEY, Consts.CarDetails.CONTEXT_CREATE);
                // to get result, onActivityResult ist called on finishing CarDetailsActivity
                startActivityForResult(intent, Consts.CarDetails.REQUEST_CAR_DETAILS);
            }
        });

        return rootView;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Logger.log(Consts.Logger.LOG_DEBUG, "[CarsFragment]", "onActivityResult is called ("+ requestCode +", "+ resultCode +")");
        if (requestCode == Consts.CarDetails.REQUEST_CAR_DETAILS) {
            if (resultCode == Consts.CarDetails.CAR_DETAILS_OK) {
                mCarListAdapter.notifyDataSetChanged();
                Logger.log(Consts.Logger.LOG_DEBUG, "[CarsFragment]", "notifyDataSetChanged is called!");
            }
        }
    }


    public String getName() {
        return mTitle;
    }
}

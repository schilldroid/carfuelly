package de.schilldroid.carfuelly.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import de.schilldroid.carfuelly.Activities.CarDetailsActivity;
import de.schilldroid.carfuelly.CarCardListAdapter;
import de.schilldroid.carfuelly.R;

/**
 * Created by Simon on 22.12.2015.
 */
public class CarsFragment extends BaseFragment {


    public CarsFragment() {
        // Empty constructor required for fragment subclasses
        mTitle = "Cars";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // apply layout to fragment
        View rootView = inflater.inflate(R.layout.fragment_cars, container, false);

        // get listview instance of that fragment layout
        ListView carListView = (ListView) rootView.findViewById(R.id.car_card_list);

        // create list adapter
        CarCardListAdapter carListAdapter = new CarCardListAdapter(mAppContext);

        // apply list adapter
        carListView.setAdapter(carListAdapter);

        FloatingActionButton fab = mAppContext.getFab();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View clayout = mAppContext.findViewById(R.id.coordinator_layout_main);
                Snackbar.make(clayout, "Cars", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent = new Intent(mAppContext, CarDetailsActivity.class);
                startActivity(intent);
            }
        });


        return rootView;
    }



    public String getName() {
        return mTitle;
    }
}

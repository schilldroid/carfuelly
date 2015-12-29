package de.schilldroid.carfuelly.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import de.schilldroid.carfuelly.Car;
import de.schilldroid.carfuelly.CarCardListAdapter;
import de.schilldroid.carfuelly.Carfuelly;
import de.schilldroid.carfuelly.DataManager;
import de.schilldroid.carfuelly.R;

/**
 * Created by Simon on 22.12.2015.
 */
public class CarsFragment extends BaseFragment {


    private Carfuelly mAppContext;


    public CarsFragment() {
        // Empty constructor required for fragment subclasses
        mTitle = "Cars";
        mAppContext = Carfuelly.getMainActivity();
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


        return rootView;
    }



    public String getName() {
        return mTitle;
    }
}

package de.schilldroid.carfuelly.Fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.schilldroid.carfuelly.Carfuelly;
import de.schilldroid.carfuelly.R;

/**
 * Created by Simon on 19.12.2015.
 */
public class HomeFragment extends BaseFragment {


    public HomeFragment() {
        // Empty constructor required for fragment subclasses
        mTitle = "Carfuelly";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        FloatingActionButton fab = mAppContext.getFab();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View clayout = mAppContext.findViewById(R.id.coordinator_layout_main);
                Snackbar.make(clayout, "Home", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        return rootView;
    }

    public String getName() {
        return mTitle;
    }
}

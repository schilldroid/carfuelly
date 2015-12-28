package de.schilldroid.carfuelly.Fragments;

import android.app.Fragment;
import android.os.Bundle;
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
        mTitle = "Home";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dummy, container, false);

        return rootView;
    }

    public String getName() {
        return mTitle;
    }
}

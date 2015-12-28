package de.schilldroid.carfuelly.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.schilldroid.carfuelly.Carfuelly;
import de.schilldroid.carfuelly.R;

/**
 * Created by Simon on 22.12.2015.
 */
public class DummyFragment extends BaseFragment {


    public DummyFragment() {
        // Empty constructor required for fragment subclasses
        mTitle = "Dummy";
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

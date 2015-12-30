package de.schilldroid.carfuelly.Fragments;

import android.app.Fragment;

import de.schilldroid.carfuelly.Carfuelly;

/**
 * Created by Simon on 28.12.2015.
 */
public class BaseFragment extends Fragment {

    public String mTitle;
    public Carfuelly mAppContext;

    public BaseFragment() {
        mAppContext = Carfuelly.getMainActivity();
    }

    public String getTitle() {
        return mTitle;
    }

}

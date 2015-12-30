package de.schilldroid.carfuelly;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.schilldroid.carfuelly.Utils.Consts;
import de.schilldroid.carfuelly.Utils.Logger;

/**
 * Created by Simon on 29.12.2015.
 */
public class CarCardListAdapter extends BaseAdapter {


    private Carfuelly mAppContext;
    private LayoutInflater mInflater;
    private ArrayList<Car> mCars;


    public CarCardListAdapter(AppCompatActivity a) {
        mAppContext = (Carfuelly) a;
        initCarList();

        // obtain layout inflater instance
        mInflater = (LayoutInflater) mAppContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    private void initCarList() {
        mCars = DataManager.getInstance().getCars();
    }



    @Override
    public int getCount() {
        return mCars.size();
    }

    @Override
    public Object getItem(int position) {

        if(position >= 0 && position < mCars.size()) {
            Logger.log(Consts.Logger.LOG_INFO, "[CarCardListAdapter]", "requested car at position " + position);
            return mCars.get(position);
        }
        else {
            Logger.log(Consts.Logger.LOG_ERROR, "[CarCardListAdapter]", "requested car at position "+ position +" not found (size="+ mCars.size() +")");
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        if(position >= 0 && position < mCars.size()) {
            int id = mCars.get(position).getID();
            Logger.log(Consts.Logger.LOG_INFO, "[CarCardListAdapter]", "requested car id at position "+ position +" is "+ id);
            return id;
        }
        else {
            Logger.log(Consts.Logger.LOG_ERROR, "[CarCardListAdapter]", "requested car id at position "+ position +" not found (size="+ mCars.size() +")");
            return -1;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        view = initializeEntry(view, position);

        return view;
    }

    public View initializeEntry(View v, int position) {

        // if no view is present, create a new
        if(v == null) {
            v = mInflater.inflate(R.layout.car_card_list_item, null);
        }

        // initialize components of view
        TextView dest = (TextView) v.findViewById(R.id.car_card_name);
        Car c = mCars.get(position);
        dest.setText(c.getName());
        return v;
    }
}


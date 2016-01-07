package de.schilldroid.carfuelly;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.schilldroid.carfuelly.Activities.CarDetailsActivity;
import de.schilldroid.carfuelly.Fragments.CarsFragment;
import de.schilldroid.carfuelly.Utils.Consts;
import de.schilldroid.carfuelly.Utils.Logger;

/**
 * Created by Simon on 29.12.2015.
 */
public class CarCardListAdapter extends BaseAdapter {


    private CarsFragment mContainingFragment;
    private Carfuelly mAppContext;
    private LayoutInflater mInflater;
    private ArrayList<Car> mCars;


    public CarCardListAdapter(CarsFragment fragment) {
        mAppContext = Carfuelly.getMainActivity();
        mContainingFragment = fragment;
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
        View v = convertView;

        // if no view is present, create a new
        if(v == null) {
            v = mInflater.inflate(R.layout.car_card_list_item, null);
        }

        CardView cardView = (CardView) v.findViewById(R.id.car_card);
        // saving the car ID in the cardview
        cardView.setTag(mCars.get(position).getID());
        // applying on click listener
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int carID = (int) v.getTag();
                Intent intent = new Intent(mAppContext, CarDetailsActivity.class);
                intent.putExtra(Consts.CarDetails.CONTEXT_KEY, Consts.CarDetails.CONTEXT_MODIFY);
                intent.putExtra(Consts.CarDetails.PARAM_CAR_ID, carID);
                // to get result, onActivityResult ist called on finishing CarDetailsActivity
                mContainingFragment.startActivityForResult(intent, Consts.CarDetails.REQUEST_CAR_DETAILS);
            }
        });

        Car c = mCars.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("MM.yyyy");

        // initialize components of view
        TextView name = (TextView) v.findViewById(R.id.car_card_name);
        name.setText(c.getName());

        TextView manufacturer = (TextView) v.findViewById(R.id.car_card_manufacturer);
        manufacturer.setText(c.getManufacturer());

        TextView model = (TextView) v.findViewById(R.id.car_card_model);
        model.setText(c.getModel());

        TextView dateOfFirstReg = (TextView) v.findViewById(R.id.car_card_date_of_first_registration);
        dateOfFirstReg.setText(sdf.format(c.getFirstRegistration()));

        TextView performance = (TextView) v.findViewById(R.id.car_card_performance);
        performance.setText(""+ c.getPower());

        TextView engine = (TextView) v.findViewById(R.id.car_card_engine);
        engine.setText(""+ c.getEngine());

        TextView registration = (TextView) v.findViewById(R.id.car_card_registration);
        registration.setText(c.getLicensePlate());

        return v;
    }

    @Override
    public void notifyDataSetChanged() {
        initCarList();
        super.notifyDataSetChanged();
    }

}


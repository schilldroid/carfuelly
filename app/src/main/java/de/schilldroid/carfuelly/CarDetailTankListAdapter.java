package de.schilldroid.carfuelly;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import junit.framework.Test;

import java.util.ArrayList;

import de.schilldroid.carfuelly.Activities.CarDetailsActivity;
import de.schilldroid.carfuelly.Utils.Consts;
import de.schilldroid.carfuelly.Utils.Logger;

/**
 * Created by Simon on 31.12.2015.
 */
public class CarDetailTankListAdapter extends BaseAdapter {

    private Carfuelly mAppContext;
    private CarDetailsActivity mActivityContext;
    private LayoutInflater mInflater;
    private ArrayList<CarDetailsTankListEntry> mListEntries;

    public CarDetailTankListAdapter(AppCompatActivity a) {
        mActivityContext = (CarDetailsActivity) a;
        mAppContext = Carfuelly.getMainActivity();
        mListEntries = new ArrayList<>();
        mInflater = (LayoutInflater) mAppContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        addTank();
    }

    @Override
    public int getCount() {
        if(mListEntries != null) {
            return mListEntries.size();
        }
        else {
            Logger.log(Consts.Logger.LOG_ERROR, "[CarDetailsTankListAdapter]", "try to call method 'size' of null object 'mListEntries'");
            return -1;
        }

    }

    @Override
    public Object getItem(int position) {
        if(position >= 0 && position < getCount()) {
            return mListEntries.get(position);
        }
        else {
            Logger.log(Consts.Logger.LOG_ERROR, "[CarDetailsTankListAdapter]", "position " + position + " out of bounds (range: 0 - " + (getCount() - 1) + ")");
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(convertView == null) {
            view = mInflater.inflate(R.layout.car_details_tank_list_item, null);
        }

        //Logger.log(Consts.Logger.LOG_DEBUG, "[CarDetailsTankListAdapter]", "getView(): pos = "+ position +", tanks='"+ tanksToString() +"'");

        // position out of bounds?
        if(position >= mListEntries.size() || position < 0) {
            Logger.log(Consts.Logger.LOG_ERROR, "[CarDetailsTankListAdapter]", "position " + position + " out of bounds (range: 0 - " + (getCount()-1) + ")");
            return view;
        }

        // request views
        EditText viewName = (EditText) view.findViewById(R.id.car_details_tank_name);
        EditText viewCapacity = (EditText) view.findViewById(R.id.car_details_tank_capacity);
        EditText viewFuelType = (EditText) view.findViewById(R.id.car_details_tank_fuel);
        ImageView viewRemove = (ImageView) view.findViewById(R.id.car_details_tank_remove);

        // SET the onclickListener (there is just one)
        viewRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeTank(position);
            }
        });

        // update appropriate CarDetailsTankListEntry
        CarDetailsTankListEntry entry = (CarDetailsTankListEntry) getItem(position);
        entry.setViews(viewName, viewCapacity, viewFuelType, viewRemove);

        // initialize values of views with appropriate data from tank list
        Tank currentTank = entry.getTank();
        viewName.setText(currentTank.getName());
        viewFuelType.setText(currentTank.getFuelType());
        if(currentTank.getCapacity() > 0)
            viewCapacity.setText(""+currentTank.getCapacity());
        else
            viewCapacity.setText("");

        return view;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if(mActivityContext == null) {
            Logger.log(Consts.Logger.LOG_ERROR, "[CarDetailsTankListAdapter]", "trying to call method 'updateTankListViewHeightBasedOnChildren' on null pointer object 'mActivityContext' (in notifyDataSetChanged)");
            return;
        }
        // workaround to be able to use listview inside scrollview
        mActivityContext.updateTankListViewHeightBasedOnChildren();
    }


    public void addTank() {
        Logger.log(Consts.Logger.LOG_DEBUG, "[CarDetailsTankListAdapter]", "tanks before applying: " + tanksToString());
        // to apply data of input fields to appropriate 'Tank' objects
        updateData();
        Logger.log(Consts.Logger.LOG_DEBUG, "[CarDetailsTankListAdapter]", "tanks before adding: " + tanksToString());
        mListEntries.add(new CarDetailsTankListEntry(new Tank(-1, "", 0, "")));
        Logger.log(Consts.Logger.LOG_DEBUG, "[CarDetailsTankListAdapter]", "tanks after adding: " + tanksToString());
        notifyDataSetChanged();
    }

    private void updateData() {
        // loop over entries and call applyValues for each
        for(int i = 0; i < getCount(); i++) {
            CarDetailsTankListEntry entry = (CarDetailsTankListEntry) getItem(i);
            entry.applyValues();
        }
    }

    private void removeTank(int position) {
        Logger.log(Consts.Logger.LOG_DEBUG, "[CarDetailsTankListAdapter]", "tanks before applying: " + tanksToString());
        // to apply data of input fields to appropriate 'Tank' objects
        updateData();
        Logger.log(Consts.Logger.LOG_DEBUG, "[CarDetailsTankListAdapter]", "tanks before removing: " + tanksToString());
        Logger.log(Consts.Logger.LOG_DEBUG, "[CarDetailsTankListAdapter]", "removing ListEntry at position: " + position);
        mListEntries.remove(position);
        Logger.log(Consts.Logger.LOG_DEBUG, "[CarDetailsTankListAdapter]", "tanks after removing: " + tanksToString());
        notifyDataSetChanged();

    }


    public String tanksToString() {
        String str = "{";
        for (int i = 0; i < getCount(); i++) {
            Tank t = ((CarDetailsTankListEntry) getItem(i)).getTank();
            str += "["+i+" ; "+t.getName()+" ; "+ t.getCapacity() +" ; "+ t.getFuelType() +"] , ";
        }
        return str+="}";
    }




    // ###############################################################################################################
    // INNER CLASSES
    // ###############################################################################################################


    public class CarDetailsTankListEntry {

        private EditText mViewName;
        private EditText mViewCapacity;
        private EditText mViewFuelType;
        private ImageView mViewRemove;
        private Tank mTank;

        public CarDetailsTankListEntry(Tank t) {
            mTank = t;
        }

        public CarDetailsTankListEntry(EditText name, EditText capacity, EditText fuelType, ImageView remove, Tank t) {
            mViewName = name;
            mViewCapacity = capacity;
            mViewFuelType = fuelType;
            mViewRemove = remove;
            mTank = t;
        }

        public void setViews(EditText name, EditText capacity, EditText fuelType, ImageView remove) {
            mViewName = name;
            mViewCapacity = capacity;
            mViewFuelType = fuelType;
            mViewRemove = remove;
        }

        public Tank getTank() {
            return mTank;
        }

        public void applyValues() {
            mTank.setName(mViewName.getText().toString());
            mTank.setCapacity(mViewCapacity.getText().toString());
            mTank.setFuelType(mViewFuelType.getText().toString());
        }
    }
}

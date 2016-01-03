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
    private ArrayList<Tank> mTanks;

    public CarDetailTankListAdapter(AppCompatActivity a) {
        mActivityContext = (CarDetailsActivity) a;
        mAppContext = Carfuelly.getMainActivity();
        mTanks = new ArrayList<>();
        mInflater = (LayoutInflater) mAppContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        addTank();
    }

    @Override
    public int getCount() {
        return mTanks.size();
    }

    @Override
    public Object getItem(int position) {
        return mTanks.get(position);
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
        if(position >= mTanks.size() || position < 0) {
            Logger.log(Consts.Logger.LOG_ERROR, "[CarDetailsTankListAdapter]", "position " + position + " out of bounds (range: 0 - " + mTanks.size() + 1 + ")");
            return view;
        }

        // request views
        EditText viewName = (EditText) view.findViewById(R.id.car_details_tank_name);
        EditText viewCapacity = (EditText) view.findViewById(R.id.car_details_tank_capacity);
        EditText viewFuelType = (EditText) view.findViewById(R.id.car_details_tank_fuel);
        ImageView viewRemove = (ImageView) view.findViewById(R.id.car_details_tank_remove);

        // initialize values of views with appropriate data from tank list
        Tank currentTank = mTanks.get(position);
        viewName.setText(currentTank.getName());
        viewFuelType.setText(currentTank.getFuelType());
        if(currentTank.getCapacity() > 0)
            viewCapacity.setText(currentTank.getCapacity());
        else
            viewCapacity.setText("");



        if(convertView == null) {
            // ADD initial textWatcher
            // (here the textwatcher is ADDED, so there can be more than one. So we remember the textwatcher object in the appropriate view, to be able
            // to adjust the position by the next call of getView method)
            CarDetailsTankGroupTextWatcher tw = new CarDetailsTankGroupTextWatcher(viewName, viewCapacity, viewFuelType, position);
            viewName.addTextChangedListener(tw);
            viewCapacity.addTextChangedListener(tw);
            viewFuelType.addTextChangedListener(tw);
            view.setTag(tw);
        }
        else {
            // if view already exists, update the position of the CarDetailsTankGroupTextWatcher object, so that it will edit the correct Tank object
            CarDetailsTankGroupTextWatcher tw = (CarDetailsTankGroupTextWatcher) view.getTag();
            if(tw == null) {
                Logger.log(Consts.Logger.LOG_ERROR, "[CarDetailsTankListAdapter]", "cannot obtain 'CarDetailsTankGroupTextWatcher' from view!");
            }
            else {
                tw.setPos(position);
            }
        }

        // SET the onclickListener
        // (here the onclicklistener is SET, so there will be just one)
        viewRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeTank(position);
            }
        });

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

        Logger.log(Consts.Logger.LOG_DEBUG, "[CarDetailsTankListAdapter]", "tanks before adding: "+ tanksToString());
        if(mTanks == null) {
            Logger.log(Consts.Logger.LOG_ERROR, "[CarDetailsTankListAdapter]", "trying to call method 'add' of null pointer object 'mActivityContext' (in notifyDataSetChanged)");
        }
        mTanks.add(new Tank(-1, "", 0, ""));
        Logger.log(Consts.Logger.LOG_DEBUG, "[CarDetailsTankListAdapter]", "tanks after adding: " + tanksToString());
        notifyDataSetChanged();
    }

    private void removeTank(int position) {
        Logger.log(Consts.Logger.LOG_DEBUG, "[CarDetailsTankListAdapter]", "tanks before remove: "+ tanksToString());
        Logger.log(Consts.Logger.LOG_DEBUG, "[CarDetailsTankListAdapter]", "remove pos "+ position);
        mTanks.remove(position);
        Logger.log(Consts.Logger.LOG_DEBUG, "[CarDetailsTankListAdapter]", "tanks after remove: " + tanksToString());
        notifyDataSetChanged();
    }


    public String tanksToString() {
        String str = "{";
        for (int i = 0; i < mTanks.size(); i++) {
            str += "["+i+" ; "+mTanks.get(i).getName()+"] , ";
        }
        return str+="}";
    }




    // ###############################################################################################################
    // INNER CLASSES
    // ###############################################################################################################

    public class CarDetailsTankGroupTextWatcher implements TextWatcher {
        private int mPositionInList;
        private EditText mViewName;
        private EditText mViewCapacity;
        private EditText mViewFuelType;
        public CarDetailsTankGroupTextWatcher(EditText name, EditText capacity, EditText fuelType, int pos) {
            mViewName = name;
            mViewCapacity = capacity;
            mViewFuelType = fuelType;
            mPositionInList = pos;
        }
        public void setPos(int pos) {
            mPositionInList = pos;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable s) {
            Logger.log(Consts.Logger.LOG_DEBUG, "[CarDetailsTankListAdapter]", "tanks before updating: " + tanksToString());
            Logger.log(Consts.Logger.LOG_DEBUG, "[CarDetailsTankListAdapter]", "updating position: " + mPositionInList);
            mTanks.get(mPositionInList).setName(mViewName.getText().toString());
            mTanks.get(mPositionInList).setFuelType(mViewFuelType.getText().toString());
            mTanks.get(mPositionInList).setCapacity(mViewCapacity.getText().toString());
            Logger.log(Consts.Logger.LOG_DEBUG, "[CarDetailsTankListAdapter]", "tanks after updating: " + tanksToString());
        }
    }
}

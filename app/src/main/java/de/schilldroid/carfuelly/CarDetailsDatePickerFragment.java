package de.schilldroid.carfuelly;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

import de.schilldroid.carfuelly.Activities.CarDetailsActivity;
import de.schilldroid.carfuelly.Utils.Consts;
import de.schilldroid.carfuelly.Utils.Logger;

/**
 * Created by Simon on 31.12.2015.
 */
public class CarDetailsDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{


    private OnDateAppliedListener mListener;
    private int mContext;


    public interface OnDateAppliedListener {
        void onDateApplied(int year, int month, int day, int context);
    }

    @Override
    public void onAttach(Activity a) {
        // do default things, when attaching fragment to an activity
        super.onAttach(a);

        try {
            // check if the activity has implemented the callback method
            mListener = (OnDateAppliedListener) a;
        } catch (ClassCastException e) {
            throw new ClassCastException(a.toString() + " must implement OnDateAppliedListener");
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // receive the context (to which element this picker belongs)
        Bundle args = getArguments();
        mContext = args.getInt(Consts.CarDetails.DATE_PICKER_CONTEXT, -1);
        Logger.log(Consts.Logger.LOG_DEBUG, "[CarDetailsDatePickerFragment]", "got date picker context "+ mContext);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // call callback method of the activity
        mListener.onDateApplied(year, month, day, mContext);
    }
}

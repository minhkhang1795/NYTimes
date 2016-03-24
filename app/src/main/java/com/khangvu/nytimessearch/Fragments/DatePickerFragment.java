package com.khangvu.nytimessearch.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import com.khangvu.nytimessearch.Activities.FilterSettingActivity;
import com.khangvu.nytimessearch.Models.Query;
import com.khangvu.nytimessearch.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by duyvu on 3/22/16.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    Query mCurrentQuery;
    int year, month, day;

    public DatePickerFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        mCurrentQuery = args.getParcelable("query");
        // Use the current date as the default date in the picker

        if (mCurrentQuery.beginDateCalendar != null) {
            year = mCurrentQuery.beginDateCalendar.get(GregorianCalendar.YEAR);
            month = mCurrentQuery.beginDateCalendar.get(GregorianCalendar.MONTH);
            day = mCurrentQuery.beginDateCalendar.get(GregorianCalendar.DAY_OF_MONTH);
        } else {
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        ((FilterSettingActivity) getActivity()).mCurrentQuery.onDateSet(year, month, day);
        TextView mDateEditText = (TextView) getActivity().findViewById(R.id.date_edit_text);
        mDateEditText.setText(month + "/" + day + "/" + year);
    }
}

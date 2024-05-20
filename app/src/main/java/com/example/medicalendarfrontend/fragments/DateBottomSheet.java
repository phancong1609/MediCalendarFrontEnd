package com.example.medicalendarfrontend.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.medicalendarfrontend.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class DateBottomSheet extends BottomSheetDialogFragment implements DatePickerDialog.OnDateSetListener {

    private OnDateSelectedListener mListener;
    private Set<Long> unavailableDates;

    public interface OnDateSelectedListener {
        void onDateSelected(long date);
    }

    public void setOnDateSelectedListener(OnDateSelectedListener listener) {
        mListener = listener;
    }

    public void setUnavailableDates(Set<Long> dates) {
        unavailableDates = dates;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_date_picker, container, false);

        // Set up the date picker dialog
        Calendar now = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );


        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();
        maxDate.add(Calendar.DAY_OF_YEAR, 30);

        datePickerDialog.setMinDate(minDate);
        datePickerDialog.setMaxDate(maxDate);


        if (unavailableDates != null) {
            Calendar[] disabledDays = new Calendar[unavailableDates.size()];
            int i = 0;
            for (Long millis : unavailableDates) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(millis);
                disabledDays[i++] = cal;
            }
            datePickerDialog.setDisabledDays(disabledDays);
        }

        datePickerDialog.show(getParentFragmentManager(), "Datepickerdialog");

        return view;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(year, monthOfYear, dayOfMonth);

        long selectedDateInMillis = selectedDate.getTimeInMillis();
        if (unavailableDates.contains(selectedDateInMillis)) {

        } else {
            if (mListener != null) {
                mListener.onDateSelected(selectedDateInMillis);
            }
            dismiss();
        }
    }
}

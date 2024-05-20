package com.example.medicalendarfrontend.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.medicalendarfrontend.R;
import com.example.medicalendarfrontend.activities.AppointmentDetailActivity;
import com.example.medicalendarfrontend.activities.LoginActivity;
import com.example.medicalendarfrontend.activities.RegisterActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AppointmentOptionsBottomSheet extends BottomSheetDialogFragment {
    private String facilityName;

    public AppointmentOptionsBottomSheet(String facilityName) {
        this.facilityName = facilityName;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_layout, container, false);

        TextView facilityNameTextView = view.findViewById(R.id.facility_name);
        facilityNameTextView.setText(facilityName);

        Button specialistAppointmentButton = view.findViewById(R.id.btn_specialist_appointment);
        Button healthPackageButton = view.findViewById(R.id.btn_health_package);

        specialistAppointmentButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AppointmentDetailActivity.class);
            startActivity(intent);
        });

        healthPackageButton.setOnClickListener(v -> {
            // Handle health package button click
        });

        return view;
    }
}

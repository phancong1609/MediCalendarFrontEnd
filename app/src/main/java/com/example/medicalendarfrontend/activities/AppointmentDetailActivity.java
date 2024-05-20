package com.example.medicalendarfrontend.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.medicalendarfrontend.R;
import com.example.medicalendarfrontend.fragments.DateBottomSheet;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class AppointmentDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);

        // Toolbar back button functionality
        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());

        FragmentManager fragmentManager = getSupportFragmentManager();

        Button selectDate = findViewById(R.id.select_date);
        selectDate.setOnClickListener(v -> {
            DateBottomSheet bottomSheet = new DateBottomSheet();
            bottomSheet.setOnDateSelectedListener(date -> {
                Toast.makeText(this, "Selected Date: " + date, Toast.LENGTH_SHORT).show();
            });

            Set<Long> unavailableDates = new HashSet<>();
            Calendar cal = Calendar.getInstance();

            cal.set(2024, Calendar.MAY, 21);
            unavailableDates.add(cal.getTimeInMillis());
            cal.set(2024, Calendar.MAY, 25);
            unavailableDates.add(cal.getTimeInMillis());

            bottomSheet.setUnavailableDates(unavailableDates);

            bottomSheet.show(fragmentManager, bottomSheet.getTag());
        });

        Button continueButton = findViewById(R.id.continue_button);
        continueButton.setOnClickListener(v -> {
        });
    }
}

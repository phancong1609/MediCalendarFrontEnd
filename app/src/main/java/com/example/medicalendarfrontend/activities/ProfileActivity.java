package com.example.medicalendarfrontend.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.medicalendarfrontend.R;
import com.example.medicalendarfrontend.fragments.ProfileFragment;

public class ProfileActivity extends AppCompatActivity {

    private static final String EXTRA_MODE = "com.example.medicalendarfrontend.MODE";
    private static final String EXTRA_PROFILE_ID = "com.example.medicalendarfrontend.PROFILE_ID";

    public static void start(Context context, String mode, int profileId) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(EXTRA_MODE, mode);
        intent.putExtra(EXTRA_PROFILE_ID, profileId);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        int profileId = getIntent().getIntExtra(EXTRA_PROFILE_ID, -1);
        String mode = getIntent().getStringExtra(EXTRA_MODE);
        if (mode == null) {
            throw new IllegalArgumentException("Mode must be specified to start ProfileActivity");
        }

        Fragment profileFragment = ProfileFragment.newInstance(mode, profileId);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, profileFragment)
                .commit();
    }
}

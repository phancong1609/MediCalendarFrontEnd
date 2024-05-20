package com.example.medicalendarfrontend.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.medicalendarfrontend.R;
import com.example.medicalendarfrontend.activities.BookAtOfficeActivity;
import com.example.medicalendarfrontend.activities.LoginActivity;
import com.example.medicalendarfrontend.activities.MainActivity;
import com.google.android.material.button.MaterialButton;


public class HomeFragment extends Fragment {
    MaterialButton button1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        reflect(view);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Button 1 selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), BookAtOfficeActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void reflect(View view){
        button1 = view.findViewById(R.id.bookOffice);
    }
}
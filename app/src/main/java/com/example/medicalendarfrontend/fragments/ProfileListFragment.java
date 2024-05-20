package com.example.medicalendarfrontend.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.medicalendarfrontend.R;
import com.example.medicalendarfrontend.activities.ProfileActivity;
import com.example.medicalendarfrontend.adapters.ProfileAdapter;
import com.example.medicalendarfrontend.models.Profile;
import com.example.medicalendarfrontend.response.ProfileResponse;
import com.example.medicalendarfrontend.utils.APIService;
import com.example.medicalendarfrontend.utils.RetrofitClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileListFragment extends Fragment {
    private static final String SHARED_PREFS_NAME = "MedicalendarPrefs";
    private static final String USERNAME_KEY = "username";
    APIService apiService;
    private RecyclerView recyclerView;
    private ProfileAdapter profileAdapter;
    private List<Profile> profileList;
    private Button createNewProfileButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_list, container, false);

        ImageView backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> getActivity().onBackPressed());

        ImageView addProfileButton = view.findViewById(R.id.add_profile);
        addProfileButton.setOnClickListener(v -> {
            ProfileActivity.start(getContext(), ProfileFragment.MODE_CREATE, -1);
        });

        createNewProfileButton = view.findViewById(R.id.create_new_profile_button);
        createNewProfileButton.setOnClickListener(v -> {
            ProfileActivity.start(getContext(), ProfileFragment.MODE_CREATE, -1);
        });

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        profileList = new ArrayList<>();
        profileAdapter = new ProfileAdapter(profileList, getContext());
        recyclerView.setAdapter(profileAdapter);

        apiService = RetrofitClient.getClient().create(APIService.class);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString(USERNAME_KEY, null);
        loadData(savedUsername);
        return view;
    }

    private void loadData(String email) {
        Call<ProfileResponse> getPatientProfileCall = apiService.getPatientProfile(email);
        getPatientProfileCall.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful()) {
                    ProfileResponse res = response.body();
                    if (res != null && res.getProfileList() != null) {
                        profileList.clear();
                        profileList.addAll(res.getProfileList());
                        profileAdapter.notifyDataSetChanged();

                        if (profileList.isEmpty()) {
                            createNewProfileButton.setVisibility(View.VISIBLE);
                        } else {
                            createNewProfileButton.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(getActivity(), "Response body or data is null", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        ProfileResponse errorResponse = new Gson().fromJson(response.errorBody().charStream(), ProfileResponse.class);
                        String errorMessage = (errorResponse != null && errorResponse.getMessgage() != null) ? errorResponse.getMessgage() : "Get profile failed";
                        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "An error occurred", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable throwable) {
                Toast.makeText(getActivity(), "Get profile failed", Toast.LENGTH_SHORT).show();
                System.out.println(throwable.getMessage());
            }
        });
    }
}

package com.example.medicalendarfrontend.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.medicalendarfrontend.R;
import com.example.medicalendarfrontend.requests.CreateProfileRequest;
import com.example.medicalendarfrontend.requests.EditProfileRequest;
import com.example.medicalendarfrontend.response.MessageResponse;
import com.example.medicalendarfrontend.utils.APIService;
import com.example.medicalendarfrontend.utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private static final String SHARED_PREFS_NAME = "MedicalendarPrefs";
    private static final String USERNAME_KEY = "username";
    private static final String ARG_MODE = "mode";
    private static final String ARG_PROFILE_ID = "profileId";
    public static final String MODE_CREATE = "create";
    public static final String MODE_EDIT = "edit";

    private String mode;
    private String id;
    String savedUsername;
    private ImageView backButton;
    private TextView toolbarTitle;
    private Button submitButton;
    private EditText fullName, dob, gender, cmndPassport, insuranceCode, occupation, phoneNumber, email, country, ethnicity, province, district, ward, address;

    private APIService apiService;

    public static ProfileFragment newInstance(String mode, int profileId) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MODE, mode);
        args.putString(ARG_PROFILE_ID, String.valueOf(profileId));
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        apiService = RetrofitClient.getClient().create(APIService.class);

        if (getArguments() != null) {
            mode = getArguments().getString(ARG_MODE);
            id = getArguments().getString(ARG_PROFILE_ID);
        }

        backButton = view.findViewById(R.id.back_button);
        toolbarTitle = view.findViewById(R.id.toolbar_title);
        submitButton = view.findViewById(R.id.btn_submit);

        fullName = view.findViewById(R.id.editText_fullName);
        dob = view.findViewById(R.id.editText_dob);
        gender = view.findViewById(R.id.editText_gender);
        cmndPassport = view.findViewById(R.id.editText_cmndPassport);
        insuranceCode = view.findViewById(R.id.editText_insuranceCode);
        occupation = view.findViewById(R.id.editText_occupation);
        phoneNumber = view.findViewById(R.id.editText_phoneNumber);
        email = view.findViewById(R.id.editText_email);
        country = view.findViewById(R.id.editText_country);
        ethnicity = view.findViewById(R.id.editText_ethnicity);
        province = view.findViewById(R.id.editText_province);
        district = view.findViewById(R.id.editText_district);
        ward = view.findViewById(R.id.editText_ward);
        address = view.findViewById(R.id.editText_address);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        savedUsername = sharedPreferences.getString(USERNAME_KEY, null);
        // Set up toolbar title and button text based on mode
        toolbarTitle.setText(mode.equals(MODE_CREATE) ? R.string.add_profile : R.string.edit_profile);
        submitButton.setText(mode.equals(MODE_CREATE) ? R.string.create_profile : R.string.edit_profile);


        backButton.setOnClickListener(v -> getActivity().onBackPressed());


        submitButton.setOnClickListener(v -> {
            if (mode.equals(MODE_CREATE)) {
                createProfile();
            } else if (mode.equals(MODE_EDIT)) {
                editProfile();
            }
        });

        return view;
    }

    private void createProfile() {
        CreateProfileRequest request = createRequestFromInput();
        Call<MessageResponse> call = apiService.createProfile(request);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Profile created successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to create profile", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editProfile() {
        EditProfileRequest request = editRequestFromInput();
        Call<MessageResponse> call = apiService.editProfile(request);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Profile edited successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to edit profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private CreateProfileRequest createRequestFromInput() {
        return new CreateProfileRequest(
                savedUsername,
                fullName.getText().toString(),
                dob.getText().toString(),
                gender.getText().toString(),
                cmndPassport.getText().toString(),
                insuranceCode.getText().toString(),
                occupation.getText().toString(),
                phoneNumber.getText().toString(),
                country.getText().toString(),
                ethnicity.getText().toString(),
                province.getText().toString(),
                district.getText().toString(),
                ward.getText().toString(),
                address.getText().toString()
        );
    }

    private EditProfileRequest editRequestFromInput() {
        return new EditProfileRequest(
                Long.parseLong(id),
                fullName.getText().toString(),
                dob.getText().toString(),
                gender.getText().toString(),
                cmndPassport.getText().toString(),
                insuranceCode.getText().toString(),
                occupation.getText().toString(),
                phoneNumber.getText().toString(),
                country.getText().toString(),
                ethnicity.getText().toString(),
                province.getText().toString(),
                district.getText().toString(),
                ward.getText().toString(),
                address.getText().toString()
        );
    }
}

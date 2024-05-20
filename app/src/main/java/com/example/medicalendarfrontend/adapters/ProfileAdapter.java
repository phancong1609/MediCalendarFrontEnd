package com.example.medicalendarfrontend.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalendarfrontend.R;
import com.example.medicalendarfrontend.activities.ProfileActivity;

import com.example.medicalendarfrontend.fragments.ProfileFragment;
import com.example.medicalendarfrontend.models.Profile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    private List<Profile> profiles;
    private Context context;

    public ProfileAdapter(List<Profile> profiles, Context context) {
        this.profiles = profiles;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Profile profile = profiles.get(position);
        holder.profileName.setText(profile.getName());
        holder.profilePhone.setText(profile.getPhone());
        String birthdate = profile.getBirthdate();
        holder.profileBirthdate.setText(birthdate);
        holder.profileAddress.setText(profile.getAddressNumber());
        holder.selectButton.setOnClickListener(v -> {
            ProfileActivity.start(context, ProfileFragment.MODE_EDIT, Integer.parseInt(profile.getId().toString()));
        });
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView profileName;
        public TextView profilePhone;
        public TextView profileBirthdate;
        public TextView profileAddress;
        public Button selectButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileName = itemView.findViewById(R.id.profile_name);
            profilePhone = itemView.findViewById(R.id.profile_phone);
            profileBirthdate = itemView.findViewById(R.id.profile_birthdate);
            profileAddress = itemView.findViewById(R.id.profile_address);
            selectButton = itemView.findViewById(R.id.select_button);
        }
    }
}

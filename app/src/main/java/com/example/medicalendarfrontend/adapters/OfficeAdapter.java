package com.example.medicalendarfrontend.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalendarfrontend.R;
import com.example.medicalendarfrontend.fragments.AppointmentOptionsBottomSheet;
import com.example.medicalendarfrontend.models.Office;

import java.util.List;

public class OfficeAdapter extends RecyclerView.Adapter<OfficeAdapter.ViewHolder> {
    private List<Office> facilities;
    private Context context;
    private FragmentManager fragmentManager;

    public OfficeAdapter(List<Office> facilities, Context context, FragmentManager fragmentManager) {
        this.facilities = facilities;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.office_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Office facility = facilities.get(position);
        holder.facilityName.setText(facility.getName());
        holder.facilityAddress.setText(facility.getAddress());

        holder.viewDetailsButton.setOnClickListener(v -> {
            // Handle view details click
        });

        holder.bookAppointmentButton.setOnClickListener(v -> {
            AppointmentOptionsBottomSheet bottomSheet = new AppointmentOptionsBottomSheet(facility.getName());
            bottomSheet.show(fragmentManager, bottomSheet.getTag());
        });
    }

    @Override
    public int getItemCount() {
        return facilities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView facilityName;
        public TextView facilityAddress;
        public Button viewDetailsButton;
        public Button bookAppointmentButton;

        public ViewHolder(View itemView) {
            super(itemView);
            facilityName = itemView.findViewById(R.id.facility_name);
            facilityAddress = itemView.findViewById(R.id.facility_address);
            viewDetailsButton = itemView.findViewById(R.id.btn_view_details);
            bookAppointmentButton = itemView.findViewById(R.id.btn_book_appointment);
        }
    }
}

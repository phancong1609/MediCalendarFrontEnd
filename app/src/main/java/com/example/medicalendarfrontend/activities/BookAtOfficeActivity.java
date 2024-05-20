package com.example.medicalendarfrontend.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.medicalendarfrontend.R;
import com.example.medicalendarfrontend.adapters.OfficeAdapter;
import com.example.medicalendarfrontend.models.Office;
import com.example.medicalendarfrontend.response.OfficeResponse;
import com.example.medicalendarfrontend.utils.APIService;
import com.example.medicalendarfrontend.utils.RetrofitClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAtOfficeActivity extends AppCompatActivity {

    APIService apiService;
    private RecyclerView recyclerView;
    private OfficeAdapter adapter;
    private List<Office> facilityList = new ArrayList<>(); // Initialize facilityList

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_at_office);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new OfficeAdapter(facilityList, this, getSupportFragmentManager());
        recyclerView.setAdapter(adapter);

        apiService = RetrofitClient.getClient().create(APIService.class);
        loadData();
    }

    private void loadData() {
        Call<OfficeResponse> getOfficeCall = apiService.getallOffice();

        getOfficeCall.enqueue(new Callback<OfficeResponse>() {
            @Override
            public void onResponse(Call<OfficeResponse> call, Response<OfficeResponse> response) {
                if (response.isSuccessful()) {
                    OfficeResponse res = response.body();
                    if (res != null && res.getOfficeList() != null) {
                        facilityList.clear();
                        facilityList.addAll(res.getOfficeList());
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(BookAtOfficeActivity.this, "Response body or office list is null", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    OfficeResponse errorResponse = new Gson().fromJson(response.errorBody().charStream(), OfficeResponse.class);
                    String errorMessage = (errorResponse != null && errorResponse.getMessage() != null) ? errorResponse.getMessage() : "Get office failed";
                    Toast.makeText(BookAtOfficeActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OfficeResponse> call, Throwable throwable) {
                Toast.makeText(BookAtOfficeActivity.this, "Get office failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

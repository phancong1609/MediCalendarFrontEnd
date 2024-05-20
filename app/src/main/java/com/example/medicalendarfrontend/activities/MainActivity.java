package com.example.medicalendarfrontend.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.medicalendarfrontend.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String SHARED_PREFS_NAME = "MedicalendarPrefs";
    private static final String USERNAME_KEY = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(bottomNavigationView, navController);

            bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
                    int id = item.getItemId();
                    if (!handled) {
                        if (id == R.id.navigation_home) {
                            navController.navigate(R.id.homeFragment);
                            Toast.makeText(MainActivity.this, "Home tab selected", Toast.LENGTH_SHORT).show();
                        } else if (id == R.id.navigation_profile) {
                            navController.navigate(R.id.profileFragment);
                            Toast.makeText(MainActivity.this, "Profile tab selected", Toast.LENGTH_SHORT).show();
                        } else if (id == R.id.navigation_logout) {
                            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.remove(USERNAME_KEY);
                            editor.apply();

                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                    return handled;
                }
            });
        } else {
            throw new NullPointerException("NavHostFragment is null");
        }
    }
}

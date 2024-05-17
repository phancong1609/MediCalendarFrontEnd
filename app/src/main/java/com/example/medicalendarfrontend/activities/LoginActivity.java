package com.example.medicalendarfrontend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medicalendarfrontend.R;
import com.example.medicalendarfrontend.requests.LoginRequest;
import com.example.medicalendarfrontend.response.MessageResponse;
import com.example.medicalendarfrontend.utils.APIService;
import com.example.medicalendarfrontend.utils.RetrofitClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    APIService apiService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        apiService = RetrofitClient.getClient().create(APIService.class);
        TextInputEditText emailEditText = findViewById(R.id.emailEditText);
        TextInputEditText passwordEditText = findViewById(R.id.passwordEditText);
        TextInputLayout emailTextInputLayout = findViewById(R.id.emailTextInputLayout);
        MaterialButton loginButton = findViewById(R.id.loginButton);

        emailEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String email = emailEditText.getText().toString().trim();
                if (!isValidEmail(email)) {
                    emailTextInputLayout.setError("Invalid email format");
                } else {
                    emailTextInputLayout.setError(null);
                }
            }
        });

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            } else if (!isValidEmail(email)) {
                emailTextInputLayout.setError("Invalid email format");
            } else {
                emailTextInputLayout.setError(null);

                LoginRequest loginRequest = new LoginRequest(email, password);

                Call<MessageResponse> loginCall = apiService.login(loginRequest);
                loginCall.enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        if (response.isSuccessful()) {
                            MessageResponse res = response.body();
                            Toast.makeText(LoginActivity.this, res.getMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            MessageResponse errorResponse = new Gson().fromJson(response.errorBody().charStream(), MessageResponse.class);
                            String errorMessage = (errorResponse != null && errorResponse.getMessage() != null) ? errorResponse.getMessage() : "Login failed";
                            Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        findViewById(R.id.signUpTextView).setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "Navigate to sign up", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private boolean isValidEmail(CharSequence email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}

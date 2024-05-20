// RegisterActivity.java
package com.example.medicalendarfrontend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medicalendarfrontend.R;
import com.example.medicalendarfrontend.requests.RegisterRequest;
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

public class RegisterActivity extends AppCompatActivity {
    APIService apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextInputEditText emailEditText = findViewById(R.id.emailEditText);
        TextInputEditText passwordEditText = findViewById(R.id.passwordEditText);
        TextInputEditText confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        TextInputLayout emailTextInputLayout = findViewById(R.id.emailTextInputLayout);
        TextInputLayout passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);
        TextInputLayout confirmPasswordTextInputLayout = findViewById(R.id.confirmPasswordTextInputLayout);
        MaterialButton registerButton = findViewById(R.id.registerButton);
        apiService = RetrofitClient.getClient().create(APIService.class);

        emailEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String email = emailEditText.getText().toString().trim();
                if (!isValidEmail(email)) {
                    emailTextInputLayout.setError("Invalid email format");
                } else {
                    emailTextInputLayout.setError(null); // Clear the error
                }
            }
        });

        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            if ( email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else if (!isValidEmail(email)) {
                emailTextInputLayout.setError("Invalid email format");
            } else if (!password.equals(confirmPassword)) {
                confirmPasswordTextInputLayout.setError("Passwords do not match");
            } else {
                emailTextInputLayout.setError(null);
                confirmPasswordTextInputLayout.setError(null);
                register(email, password, confirmPassword);
            }
        });

        findViewById(R.id.alreadyHaveAccountTextView).setOnClickListener(v -> {
            Toast.makeText(RegisterActivity.this, "Navigate to login", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private boolean isValidEmail(CharSequence email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void register(String email, String password, String confirmPassword)
    {
        RegisterRequest registerRequest = new RegisterRequest(email, password, confirmPassword);

        // Make the API call
        Call<MessageResponse> registerCall = apiService.register(registerRequest);
        registerCall.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                MessageResponse res = response.body();
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, res.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    MessageResponse errorResponse = new Gson().fromJson(response.errorBody().charStream(), MessageResponse.class);
                    String errorMessage = (errorResponse != null && errorResponse.getMessage() != null) ? errorResponse.getMessage() : "Register failed";
                    Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

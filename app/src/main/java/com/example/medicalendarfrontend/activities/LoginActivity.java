// LoginActivity.java
package com.example.medicalendarfrontend.activities;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medicalendarfrontend.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.signUpTextView).setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "Navigate to sign up", Toast.LENGTH_SHORT).show();
        });
    }

    private boolean isValidEmail(CharSequence email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}

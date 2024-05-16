// RegisterActivity.java
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

public class RegisterActivity extends AppCompatActivity {

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
                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.alreadyHaveAccountTextView).setOnClickListener(v -> {
            Toast.makeText(RegisterActivity.this, "Navigate to login", Toast.LENGTH_SHORT).show();
        });
    }

    private boolean isValidEmail(CharSequence email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}

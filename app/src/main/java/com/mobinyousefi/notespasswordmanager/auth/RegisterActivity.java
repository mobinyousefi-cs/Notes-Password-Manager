package com.mobinyousefi.notespasswordmanager.auth;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mobinyousefi.notespasswordmanager.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        binding.btnRegister.setOnClickListener(v -> attemptRegister());
        binding.btnBackToLogin.setOnClickListener(v -> finish());
    }

    private void attemptRegister() {
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString();
        String confirm = binding.etConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            binding.etEmail.setError("Email is required");
            return;
        }
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            binding.etPassword.setError("Password must be at least 6 characters");
            return;
        }
        if (!password.equals(confirm)) {
            binding.etConfirmPassword.setError("Passwords do not match");
            return;
        }

        binding.progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this::handleRegisterResult);
    }

    private void handleRegisterResult(@NonNull Task<AuthResult> task) {
        binding.progressBar.setVisibility(View.GONE);
        if (task.isSuccessful()) {
            Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
            finish(); // back to login
        } else {
            Toast.makeText(this, "Registration failed: " +
                    (task.getException() != null ? task.getException().getMessage() : ""),
                    Toast.LENGTH_LONG).show();
        }
    }
}

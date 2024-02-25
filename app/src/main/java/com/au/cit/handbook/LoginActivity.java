package com.au.cit.handbook;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.au.cit.handbook.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupEventListeners();
    }

    private void setupEventListeners() {
        binding.buttonLogin.setOnClickListener(this::buttonLoginAction);
        binding.signUp.setOnClickListener(this::signUpAction);
    }

    /*---------------------------------------------------------------------------
     * HANDLERS
     *---------------------------------------------------------------------------
     *
     *
     */
    private void buttonLoginAction(View v) {
        String email = binding.editTextEmail.getText().toString().trim();
        String password = binding.editTextPassword.getText().toString().trim();

/*        boolean isValid = true;

        isValid &= validateEmail(email);
        isValid &= validatePassword(password);
        if (isValid) {
            Toast.makeText(this, "Login success!", Toast.LENGTH_SHORT).show();
        }*/
        if (email.matches("admin") && password.matches("admin")) {
            finish();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
    }

    private void signUpAction(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    /*---------------------------------------------------------------------------
     * HELPERS
     *---------------------------------------------------------------------------
     *
     *
     */
    private boolean validateEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            binding.editTextEmail.setError(getString(R.string.error_email_missing));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextEmail.setError(getString(R.string.error_email_invalid));
            return false;
        }
        binding.editTextEmail.setError(null);
        return true;
    }

    private boolean validatePassword(String password) {
        if (TextUtils.isEmpty(password)) {
            binding.editTextPassword.setError(getString(R.string.error_password_missing));
            return false;
        }
        binding.editTextPassword.setError(null);
        return true;
    }
}

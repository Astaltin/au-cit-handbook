package com.au.cit.handbook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.au.cit.handbook.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private final int FIRST_NAME_LENGTH_MIN;
    private final int LAST_NAME_LENGTH_MIN;
    private final int PASSWORD_LENGTH_MIN;
    private ActivityRegisterBinding binding;

    {
        FIRST_NAME_LENGTH_MIN = 2;
        LAST_NAME_LENGTH_MIN = 2;
        PASSWORD_LENGTH_MIN = 8;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        actionBarInit();
        eventListenersInit();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    /*
     * For some reason, the IDE is detecting this method of AppCompatActivity
     * as deprecated. As of 2022, stated by ChatGPT, that is not the case.
     * In fact, it is still the standard method for programming the on back
     * pressed.
     *
     * SO BASICALLY, JUST IGNORE THE DEPRECATION WARNING. ;-;
     *
     * - Edward Gulmayo
     */
    public void onBackPressed() {
        finish();
        Intent i = new Intent(
                getApplicationContext(), AuthenticationActivity.class);
        startActivity(i);
    }

    /*---------------------------------------------------------------------------
     * INITIALIZATION
     *---------------------------------------------------------------------------
     *
     *
     */
    private void actionBarInit() {
        setSupportActionBar(binding.actionBar.toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        binding.actionBar.toolbar.setNavigationOnClickListener(v -> {
            finish();
            Intent i = new Intent(
                    getApplicationContext(), AuthenticationActivity.class);
            startActivity(i);
        });
    }

    private void eventListenersInit() {
        binding.buttonSignUp.setOnClickListener(this::buttonSignupAction);
        binding.login.setOnClickListener(this::loginAction);
    }

    /*---------------------------------------------------------------------------
     * HANDLERS
     *---------------------------------------------------------------------------
     *
     *
     */
    private void buttonSignupAction(View v) {
        String firstName =
                binding.editTextFirstName.getText().toString().trim();
        String lastName =
                binding.editTextLastName.getText().toString().trim();
        String email =
                binding.editTextEmail.getText().toString().trim();
        String password =
                binding.editTextPassword.getText().toString().trim();
        String passwordConfirm =
                binding.editTextPasswordConfirm.getText().toString().trim();

        boolean isValid = true;
        isValid &= validateFirstName(firstName);
        isValid &= validateLastName(lastName);
        isValid &= validateEmail(email);
        isValid &= validatePassword(password);
        isValid &= validatePasswordConfirm(passwordConfirm, password);
        if (!isValid) {
            return;
        } else {
            finish();
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            i.putExtra("email", email);
            startActivity(i);
        }
    }

    /**
     * @deprecated
     */
    private void loginAction(View v) {
        finish();
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }

    /*---------------------------------------------------------------------------
     * HELPERS
     *---------------------------------------------------------------------------
     *
     *
     */
    private boolean validateFirstName(String firstName) {
        if (TextUtils.isEmpty(firstName)) {
            binding.editTextFirstName.setError(getString(R.string.error_first_name_missing));
            return false;
        } else if (firstName.length() < FIRST_NAME_LENGTH_MIN) {
            binding.editTextFirstName.setError(getString(R.string.error_first_name_invalid));
            return false;
        }
        binding.editTextFirstName.setError(null);
        return true;
    }

    private boolean validateLastName(String lastName) {
        if (TextUtils.isEmpty(lastName)) {
            binding.editTextLastName.setError(getString(R.string.error_last_name_missing));
            return false;
        } else if (lastName.length() < LAST_NAME_LENGTH_MIN) {
            binding.editTextLastName.setError(getString(R.string.error_last_name_invalid));
            return false;
        }
        binding.editTextLastName.setError(null);
        return true;
    }

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
        } else if (password.length() < PASSWORD_LENGTH_MIN) {
            binding.editTextPassword.setError(
                    getString(R.string.error_password_min_length));
            return false;
        }
        binding.editTextPassword.setError(null);
        return true;
    }

    private boolean validatePasswordConfirm(String passwordConfirm, String password) {
        if (TextUtils.isEmpty(passwordConfirm) || !passwordConfirm.matches(password)) {
            binding.editTextPasswordConfirm.setError(
                    getString(R.string.error_password_does_not_match));
            return false;
        }
        binding.editTextPasswordConfirm.setError(null);
        return true;
    }
}
package com.au.cit.handbook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.au.cit.handbook.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        actionBarInit();
        eventListenersInit();

        checkForIncomingRegisteredEmail();
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

    private void checkForIncomingRegisteredEmail() {
        Intent externalIntent = getIntent();
        if (externalIntent != null && externalIntent.hasExtra("email")) {
            binding.editTextEmail.setText(externalIntent.getStringExtra("email"));
        }
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
        binding.editTextEmail.setText("admin");
        binding.editTextPassword.setText("admin");

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

    /**
     * @deprecated
     */
    private void signUpAction(View view) {
        finish();
        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i);
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

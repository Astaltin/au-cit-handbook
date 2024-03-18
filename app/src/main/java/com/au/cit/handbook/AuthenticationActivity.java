package com.au.cit.handbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.au.cit.handbook.databinding.ActivityAuthenticationBinding;
import com.au.cit.handbook.singleton.SharedPrefsManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class AuthenticationActivity extends AppCompatActivity {

    private ActivityAuthenticationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAuthenticationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initEventListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = SharedPrefsManager.getInstance(this)
                .sharedPreferences();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        String authenticator = null;

        if (sharedPreferences.contains("ip_address")
                && sharedPreferences.contains("session_token")
                && sharedPreferences.contains("user_token")) {

            authenticator = "local";
        }
        if (account != null) {
            authenticator = "google";
        }

        if (authenticator != null) {
            finish();
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("authenticator", authenticator);
            startActivity(i);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        binding = null;
    }

    private void initEventListeners() {
        binding.buttonLogin.setOnClickListener(v -> {
            finish();
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        });
        binding.buttonRegister.setOnClickListener(v -> {
            finish();
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        });
    }
}
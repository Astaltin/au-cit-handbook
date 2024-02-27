package com.au.cit.handbook;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.au.cit.handbook.databinding.ActivityAuthenticationBinding;

public class AuthenticationActivity extends AppCompatActivity {

    private ActivityAuthenticationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAuthenticationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        eventListenersInit();
    }

    /*---------------------------------------------------------------------------
     * INITIALIZATION
     *---------------------------------------------------------------------------
     *
     *
     */
    private void eventListenersInit() {
        /*---------------------------------------------------------------------------
         * HANDLERS
         *---------------------------------------------------------------------------
         *
         *
         */
        binding.buttonLogin.setOnClickListener(v -> {
            finish();
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        });
        binding.buttonSignUp.setOnClickListener(v -> {
            finish();
            Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(i);
        });
    }
}
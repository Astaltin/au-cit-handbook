package com.au.cit.handbook;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.au.cit.handbook.databinding.ActivityReservationsBinding;

public class ReservationsActivity extends AppCompatActivity {

    private ActivityReservationsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityReservationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initActionBar();
    }

    private void initActionBar() {
        setSupportActionBar(binding.actionBar.toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        binding.actionBar.toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
    }
}
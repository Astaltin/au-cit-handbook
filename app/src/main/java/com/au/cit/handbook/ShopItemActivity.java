package com.au.cit.handbook;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.au.cit.handbook.databinding.ActivityShopItemBinding;

public class ShopItemActivity extends AppCompatActivity {

    private ActivityShopItemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShopItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.grayshade)); // Change 'your_color' to the desired color
        }

        initActionBar();
    }

    private void initActionBar() throws NullPointerException {
        setSupportActionBar(binding.actionBar.toolbar);

        ActionBar actionBar = getSupportActionBar();


        if (actionBar == null) {
            throw new NullPointerException("getSupportActionBar() is null.");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            actionBar.setBackgroundDrawable(getDrawable(R.drawable.bg_grayshade));
        }
        actionBar.setDisplayHomeAsUpEnabled(true);

        binding.actionBar.toolbar.setNavigationOnClickListener(v -> finish());
    }
}
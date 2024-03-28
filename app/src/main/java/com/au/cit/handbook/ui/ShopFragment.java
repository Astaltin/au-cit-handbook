package com.au.cit.handbook.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.au.cit.handbook.ShopItemActivity;
import com.au.cit.handbook.databinding.FragmentShopBinding;

public class ShopFragment extends Fragment implements View.OnClickListener {

    private FragmentShopBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentShopBinding.inflate(inflater, container, false);

        initEventListners();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initEventListners() {
        for (int i = 0; i < binding.constraintLayout.getChildCount(); i++) {
            binding.constraintLayout.getChildAt(i).setOnClickListener(this);
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View v) {
        Intent i = new Intent(getActivity(), ShopItemActivity.class);

        if (v.getId() == binding.cardItAcadUniformM.getId()) {
            i.putExtra("title", binding.descAcadUniformM.getText().toString());
            i.putExtra("itemId", binding.cardItAcadUniformM.getId());

        } else if (v.getId() == binding.cardItAcadUniformF.getId()) {
            i.putExtra("title", binding.descAcadUniformF.getText().toString());
            i.putExtra("itemId", binding.cardItAcadUniformF.getId());

        } else if (v.getId() == binding.cardItCorpoUniformM.getId()) {
            i.putExtra("title", binding.descItCorpoUniformM.getText().toString());
            i.putExtra("itemId", binding.cardItCorpoUniformM.getId());

        } else if (v.getId() == binding.cardItCorpoUniformF.getId()) {
            i.putExtra("title", binding.descItCorpoUniformF.getText().toString());
            i.putExtra("itemId", binding.cardItCorpoUniformF.getId());
        }

        startActivity(i);
    }
}
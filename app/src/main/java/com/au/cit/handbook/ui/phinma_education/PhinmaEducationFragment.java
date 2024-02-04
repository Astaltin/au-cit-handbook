package com.au.cit.handbook.ui.phinma_education;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.au.cit.handbook.R;
import com.au.cit.handbook.databinding.FragmentPhinmaEducationBinding;

public class PhinmaEducationFragment extends Fragment {

    private FragmentPhinmaEducationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPhinmaEducationBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.knowMoreButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hyperlink("https://www.phinma.edu.ph/");
            }
        });
        view.findViewById(R.id.findOutMoreButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hyperlink("https://www.phinma.com.ph/");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void hyperlink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
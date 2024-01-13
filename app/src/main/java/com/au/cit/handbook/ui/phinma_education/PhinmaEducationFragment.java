package com.au.cit.handbook.ui.phinma_education;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.au.cit.handbook.databinding.FragmentPhinmaEducationBinding;

public class PhinmaEducationFragment extends Fragment {

    private FragmentPhinmaEducationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PhinmaEducationModel phinmaEducationModel =
                new ViewModelProvider(this).get(PhinmaEducationModel.class);

        binding = FragmentPhinmaEducationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textPhinmaEducation;
        phinmaEducationModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
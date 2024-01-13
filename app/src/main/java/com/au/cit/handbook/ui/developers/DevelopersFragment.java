package com.au.cit.handbook.ui.developers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.au.cit.handbook.databinding.FragmentDevelopersBinding;

public class DevelopersFragment extends Fragment {

    private FragmentDevelopersBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DevelopersViewModel developersViewModel =
                new ViewModelProvider(this).get(DevelopersViewModel.class);

        binding = FragmentDevelopersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDevelopers;
        developersViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.au.studenthandbook.ui.student_manual;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.au.studenthandbook.databinding.FragmentStudentManualBinding;

public class StudentManualFragment extends Fragment {

    private FragmentStudentManualBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StudentManualViewModel studentManualViewModel =
                new ViewModelProvider(this).get(StudentManualViewModel.class);

        binding = FragmentStudentManualBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textStudentManual;
        studentManualViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
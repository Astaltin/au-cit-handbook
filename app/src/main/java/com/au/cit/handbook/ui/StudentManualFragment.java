package com.au.cit.handbook.ui.student_manual;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.au.cit.handbook.databinding.FragmentStudentManualBinding;

public class StudentManualFragment extends Fragment {

    private FragmentStudentManualBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentStudentManualBinding.inflate(inflater, container, false);
        WebView studentManualWebView = binding.studentManualWebView;

        WebSettings webSettings = studentManualWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        studentManualWebView.setWebViewClient(new WebViewClient());
        studentManualWebView.loadUrl("https://drive.google.com/file/d/1zIfG7y-t_SiBlk679yFOetmrgZ14WI85/view?usp=sharing");
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
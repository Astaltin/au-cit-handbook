package com.au.cit.handbook.ui.map;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.au.cit.handbook.databinding.FragmentMapBinding;

public class MapFragment extends Fragment {

    private FragmentMapBinding binding;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMapBinding.inflate(inflater, container, false);
        WebView mapWebView = binding.mapWebView;

        // Enable Javascript
        WebSettings webSettings = mapWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        mapWebView.setWebViewClient(new WebViewClient());
        //Location of Cabanatuan: @15.4943366,120.9761594
        mapWebView.loadUrl("https://www.google.com/maps/place/PHINMA+Araullo+University+South/@15.461597,120.9510386,20.75z/data=!4m6!3m5!1s0x3397288e7773ce57:0xdd78f815acb955f0!8m2!3d15.461596!4d120.951127!16s%2Fg%2F11cp7j4r6k?entry=ttu");

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
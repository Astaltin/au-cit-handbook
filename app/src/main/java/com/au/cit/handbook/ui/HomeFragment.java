package com.au.cit.handbook.ui.home;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.au.cit.handbook.databinding.FragmentHomeBinding;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        binding.sharing.setOnClickListener(this::shareToFacebook);

        return binding.getRoot();
    }

    private void shareToFacebook(View view) {
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://www.youtube.com/"))
                .build();
        ShareDialog.show(this, content);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
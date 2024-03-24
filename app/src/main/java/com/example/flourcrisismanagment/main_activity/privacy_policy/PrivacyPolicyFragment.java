package com.example.flourcrisismanagment.main_activity.privacy_policy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;

import com.example.flourcrisismanagment.databinding.FragmentPrivacyPolicyBinding;

public class PrivacyPolicyFragment extends Fragment {
    FragmentPrivacyPolicyBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentPrivacyPolicyBinding.inflate(getLayoutInflater());
        mBinding.webView.getSettings().setJavaScriptEnabled(true);

        // Load HTML content from the assets folder
       mBinding.webView.loadUrl("file:///android_asset/Privacy.html");

        // Optional: Handle navigation within the WebView itself
        mBinding.webView.setWebViewClient(new WebViewClient());

        return mBinding.getRoot();
    }
}  
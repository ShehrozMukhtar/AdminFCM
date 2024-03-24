package com.example.flourcrisismanagment.main_activity.term_condition;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;

import com.example.flourcrisismanagment.databinding.FragmentTermConditionBinding;

public class TermConditionFragment extends Fragment {
    FragmentTermConditionBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentTermConditionBinding.inflate(getLayoutInflater());
        mBinding.webView.getSettings().setJavaScriptEnabled(true);

        // Load HTML content from the assets folder
        mBinding.webView.loadUrl("file:///android_asset/Terms.html");

        // Optional: Handle navigation within the WebView itself
        mBinding.webView.setWebViewClient(new WebViewClient());
        return mBinding.getRoot();
    }
}
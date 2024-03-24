package com.example.flourcrisismanagment.main_activity.dashboard_fragment;

import static androidx.core.app.ActivityCompat.finishAffinity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.flourcrisismanagment.R;
import com.example.flourcrisismanagment.databinding.FragmentDashBoardBinding;
import com.example.flourcrisismanagment.main_activity.MainActivity;
import com.google.android.material.navigation.NavigationView;

public class DashBoardFragment extends Fragment {
    FragmentDashBoardBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mBinding = FragmentDashBoardBinding.inflate(getLayoutInflater());
        mBinding.profile2.setOnClickListener(view -> {
          Navigation.findNavController(view).navigate(R.id.action_dashBoardFragment_to_profileFragment);

        });
         mBinding.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(requireActivity(), "ThankYou:)", Toast.LENGTH_SHORT).show();
                requireActivity().finishAffinity();
            }
        });

        mBinding.eligibility.setOnClickListener(view -> {
            ((MainActivity) requireActivity()).launchEligibleActivity();
        });
        mBinding.contactUs.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_dashBoardFragment_to_contactUsFragment));
        mBinding.dailyTask.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_dashBoardFragment_to_dailyHistoryFragment));
        mBinding.stock.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_dashBoardFragment_to_stockFragment));
        mBinding.feedBack.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_dashBoardFragment_to_feedBackFragment));
        mBinding.privacy.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_dashBoardFragment_to_privacyPolicyFragment));
        mBinding.term.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_dashBoardFragment_to_termConditionFragment));
        mBinding.setting.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_dashBoardFragment_to_settingFragment));
        return mBinding.getRoot();
    }
}
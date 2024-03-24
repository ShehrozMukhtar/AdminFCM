package com.example.flourcrisismanagment.main_activity.contact_us;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.flourcrisismanagment.databinding.FragmentContactUsBinding;


public class ContactUsFragment extends Fragment {
    FragmentContactUsBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mBinding = FragmentContactUsBinding.inflate(getLayoutInflater());
        mBinding.pAhmad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "+923127861329";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });
        mBinding.pHaris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "+923036000517";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });
        mBinding.pShehroz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "+923434139070";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });
        mBinding.backward.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });
        mBinding.aMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipientEmail = "ahmadchistie6458@gamil.com";
                String subject = "Subject";
                String body = "Body";

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", recipientEmail, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, body);

                if (emailIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
                    startActivity(emailIntent);
                } else {
                    // Handle the case where no email client is installed
                    // You may want to display a message to the user
                }
            }
        });
        mBinding.hMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipientEmail = "haaris5421@gamil.com";
                String subject = "Subject";
                String body = "Body";

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", recipientEmail, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, body);

                if (emailIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
                    startActivity(emailIntent);
                } else {
                    // Handle the case where no email client is installed
                    // You may want to display a message to the user
                }
            }
        });
        mBinding.mMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipientEmail = "shehroz416rgc@gamil.com";
                String subject = "Subject";
                String body = "Body";

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", recipientEmail, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, body);

                if (emailIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
                    startActivity(emailIntent);
                } else {
                    // Handle the case where no email client is installed
                    // You may want to display a message to the user
                }
            }
        });

        return mBinding.getRoot();
    }
}
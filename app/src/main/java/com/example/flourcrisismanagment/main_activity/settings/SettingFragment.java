package com.example.flourcrisismanagment.main_activity.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.flourcrisismanagment.R;
import com.example.flourcrisismanagment.databinding.FragmentSettingBinding;
import com.example.flourcrisismanagment.main_activity.MainActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class SettingFragment extends Fragment {
    FragmentSettingBinding mBinding;
    SharedPreferences mSharedPreferences;
    FirebaseFirestore firestore;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mBinding = FragmentSettingBinding.inflate(getLayoutInflater());
        mSharedPreferences = getActivity().getSharedPreferences("loginUser", Context.MODE_PRIVATE);
        firestore = FirebaseFirestore.getInstance();
        String mEmail =    mSharedPreferences.getString("email",null);
        String id     =    mSharedPreferences.getString("id",null);
        mBinding.deleteAcc.setOnClickListener(view -> {
            // Build the alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Confirm Deletion")
                    .setMessage("Are you sure you want to delete your account?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // User clicked Yes, proceed with deletion
                        firestore.collection("suppliers")
                                .whereEqualTo("email", mEmail)
                                .get()
                                .addOnSuccessListener(queryDocumentSnapshots -> {
                                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                        firestore.collection("suppliers")
                                                .document(id)
                                                .delete()
                                                .addOnSuccessListener(aVoid -> {
                                                    // Document deleted successfully
                                                    Toast.makeText(requireActivity(), "User Deleted Successfully", Toast.LENGTH_SHORT).show();
                                                    mSharedPreferences
                                                            .edit()
                                                            .remove("name")
                                                            .remove("email")
                                                            .remove("password")
                                                            .apply();
                                                    ((MainActivity) requireActivity()).launchAuthActivity();
                                                })
                                                .addOnFailureListener(e -> {
                                                    // Failed to delete document
                                                    Toast.makeText(requireActivity(), "Failed to delete user", Toast.LENGTH_SHORT).show();
                                                });
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    // Failed to fetch documents
                                    Toast.makeText(requireActivity(), "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                                });
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        // User clicked No, do nothing
                        dialog.dismiss();
                    });

            // Show the alert dialog
            builder.create().show();
        });

        mBinding.cMail.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_settingFragment_to_changeEmailFragment);
        });
        mBinding.backwardSetting.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        return mBinding.getRoot();
    }
}
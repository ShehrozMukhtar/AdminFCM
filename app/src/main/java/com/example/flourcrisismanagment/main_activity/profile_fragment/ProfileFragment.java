package com.example.flourcrisismanagment.main_activity.profile_fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.flourcrisismanagment.R;
import com.example.flourcrisismanagment.databinding.FragmentProfileBinding;
import com.example.flourcrisismanagment.main_activity.MainActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {
    FragmentProfileBinding mBinding;
    SharedPreferences mSharedPreferences;
    FirebaseFirestore fireStore;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mBinding = FragmentProfileBinding.inflate(getLayoutInflater());
        fireStore = FirebaseFirestore.getInstance();
        mSharedPreferences = requireContext().getSharedPreferences("loginUser", Context.MODE_PRIVATE);
        mBinding.backward.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });
        mBinding.logoutBtn.setOnClickListener(view -> {
            mSharedPreferences
                    .edit()
                    .remove("name")
                    .remove("email")
                    .remove("password")
                    .apply();
            ((MainActivity) requireActivity()).launchAuthActivity();
        });


        String userId = mSharedPreferences.getString("id",null);
        fireStore
                .collection("suppliers")
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String email = documentSnapshot.getString("email");
                        String name = documentSnapshot.getString("name");
                        mBinding.email.setText(email);
                        mBinding.name.setText(name);
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle error
                    Log.e("FireStoreError", "Error fetching user data: " + e.getMessage());
                });
        return mBinding.getRoot();
    }
}
package com.example.flourcrisismanagment.auth_activity.fragments.login_fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.flourcrisismanagment.R;
import com.example.flourcrisismanagment.auth_activity.AuthActivity;
import com.example.flourcrisismanagment.databinding.FragmentLoginBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class LoginFragment extends Fragment {
    SharedPreferences mSharedPreferences;
    boolean isPasswordVisible = false;
    FirebaseFirestore fireStore;
    FragmentLoginBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentLoginBinding.inflate(getLayoutInflater());
        fireStore = FirebaseFirestore.getInstance();
        mBinding.eye.setOnClickListener(v -> {
            mBinding.eye.setImageResource(isPasswordVisible ? R.drawable.hide_eye : R.drawable.ic_eye);
            mBinding.password.setInputType(isPasswordVisible ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_TEXT);
            isPasswordVisible = !isPasswordVisible;
        });
        mSharedPreferences = requireContext().getSharedPreferences("loginUser", Context.MODE_PRIVATE);
        mBinding.loginBtn.setOnClickListener(v -> {
            String loginEmail = mBinding.email.getText().toString().trim();
            String loginPassword = mBinding.password.getText().toString().trim();
            if (loginEmail.isEmpty()) {
                mBinding.email.setError("Entre Email");
                mBinding.email.requestFocus();
            } else if (loginPassword.isEmpty()) {
                mBinding.password.setError("Enter Password");
                mBinding.password.requestFocus();
            } else {
                // Query Firestore to fetch the user with the provided email
                fireStore
                        .collection("suppliers")
                        .whereEqualTo("email", loginEmail)
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    // Get the user data
                                    String storedPassword = documentSnapshot.getString("password");
                                    // Check if the provided password matches the stored password
                                    if (storedPassword != null && storedPassword.equals(loginPassword)) {
                                        // Passwords match, login successful
                                        Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                        String userId = documentSnapshot.getId();
                                        String name    =documentSnapshot.getString("name");
                                            mSharedPreferences
                                                    .edit()
                                                    .putString("id", userId)
                                                    .putString("email", loginEmail)
                                                    .putString("password", storedPassword)
                                                    .putString("name", name)
                                                    .apply();


                                        ((AuthActivity) requireActivity()).launchMainActivity();

                                    } else {
                                        // Passwords don't match
                                        Toast.makeText(requireContext(), "Password Does not match", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                // No user with the provided email
                                Toast.makeText(requireContext(), "User not found with this Email", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(requireContext(), "Failed to retrieve user data", Toast.LENGTH_SHORT).show();
                        });

            }


        });
        return mBinding.getRoot();
    }

}
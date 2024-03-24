package com.example.flourcrisismanagment.main_activity.email_change;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.flourcrisismanagment.databinding.FragmentChangeEmailBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class ChangeEmailFragment extends Fragment {
    FragmentChangeEmailBinding mBinding;
    FirebaseFirestore firestore;
    SharedPreferences mSharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mBinding = FragmentChangeEmailBinding.inflate(getLayoutInflater());
        mSharedPreferences = getActivity().getSharedPreferences("loginUser", Context.MODE_PRIVATE);
        String id     =    mSharedPreferences.getString("id",null);
        firestore = FirebaseFirestore.getInstance();

        mBinding.update.setOnClickListener(view -> {
            String mEmail = mBinding.email.getText().toString().trim();
            String mPassword = mBinding.password.getText().toString();

            if (mEmail.isEmpty()) {
                mBinding.email.setError("Enter new Email");
                mBinding.email.requestFocus();
            }else if (!isValidEmail(mEmail)) {
                mBinding.email.setError("Enter a valid Gmail address");
                mBinding.email.requestFocus();
            }else if (mPassword.isEmpty()) {
                mBinding.password.setError("Enter Password");
                mBinding.password.requestFocus();
            }else if (!isValidPassword(mPassword)) {
                mBinding.password.setError("Password must be at least 8 characters long, " +
                        "contain at least one uppercase letter, one lowercase letter, and one digit.");
                mBinding.password.requestFocus();
            }else {
                // Get the existing user data
                firestore.collection("suppliers")
                        .document(id)
                        .get()
                        .addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()) {
                                // Keep the existing name
                                String existingName = documentSnapshot.getString("name");

                                // Create a new user map with updated email and password
                                Map<String, String> updatedUser = new HashMap<>();
                                updatedUser.put("name", existingName);
                                updatedUser.put("email", mEmail);
                                updatedUser.put("password", mPassword);

                                // Update the user data in Firestore
                                firestore.collection("suppliers").document(id)
                                        .set(updatedUser)
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(requireActivity(), "Data is Updated Successfully", Toast.LENGTH_SHORT).show();
                                            Navigation.findNavController(view).popBackStack();
                                        })
                                        .addOnFailureListener(e -> {
                                            // Failed to update data, show a failure toast
                                            Toast.makeText(requireActivity(), "Failed to update data", Toast.LENGTH_SHORT).show();
                                        });
                            } else {
                                // Handle the case where the document doesn't exist
                                Toast.makeText(requireActivity(), "User not found", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(e -> {
                            // Handle errors while fetching the document
                            Toast.makeText(requireActivity(), "Error fetching user data", Toast.LENGTH_SHORT).show();
                        });
            }
        });
        return mBinding.getRoot();
    }
    // Function to validate the password
    private boolean isValidPassword(String password) {
        // Password must be at least 8 characters long and contain at least one uppercase letter,
        // one lowercase letter, and one digit
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        return password.matches(passwordPattern);
    }
    // Function to validate the email
    private boolean isValidEmail(String email) {
        // Email must end with "@gmail.com"
        return email.endsWith("@gmail.com");
    }

}

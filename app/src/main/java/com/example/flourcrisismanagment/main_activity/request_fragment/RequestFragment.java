package com.example.flourcrisismanagment.main_activity.request_fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.flourcrisismanagment.databinding.FragmentRequestBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RequestFragment extends Fragment {
    FragmentRequestBinding mBinding;
    FirebaseFirestore fireStore;
    SharedPreferences mSharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mBinding = FragmentRequestBinding.inflate(getLayoutInflater());
        fireStore = FirebaseFirestore.getInstance();
        mSharedPreferences = requireContext().getSharedPreferences("loginUser", Context.MODE_PRIVATE);
        mBinding.backward.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        mBinding.requestStock.setOnClickListener(view -> {

            String stock = mBinding.needStock.getText().toString().trim();
            String email = mSharedPreferences.getString("email",null);
            String id  = mSharedPreferences.getString("id",null);
            if (stock.isEmpty()) {
                mBinding.needStock.setError("Enter Requested Amount");
                mBinding.needStock.requestFocus();
            }else {
                fireStore
                        .collection("requestStock")
                        .whereEqualTo("id",id)
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            if (queryDocumentSnapshots.isEmpty()) {
                                Map<String,Object> requestStock = new HashMap<>();
                                requestStock.put("sold_items", "0");
                                requestStock.put("request flour", stock);
                                requestStock.put("id",id);
                                requestStock.put("email",email);
                                requestStock.put("approved",false);
                                fireStore
                                        .collection("requestStock")
                                        .add(requestStock)
                                        .addOnSuccessListener(documentReference -> {
                                            Toast.makeText(requireContext(), "Request of Stock is Successfully Delivered", Toast.LENGTH_SHORT).show();
                                            Navigation.findNavController(view).popBackStack();
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(requireContext(), "Failed to add Data", Toast.LENGTH_SHORT).show();
                                        });
                            } else {
                                Toast.makeText(requireContext(), "Stock is already Delivered", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(requireContext(), "Failure to request", Toast.LENGTH_SHORT).show();
                        });
            }

        });
        return mBinding.getRoot();
    }
}
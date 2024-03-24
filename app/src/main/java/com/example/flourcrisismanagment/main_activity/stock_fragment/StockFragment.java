package com.example.flourcrisismanagment.main_activity.stock_fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.flourcrisismanagment.R;
import com.example.flourcrisismanagment.databinding.FragmentStockBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class StockFragment extends Fragment {
    FragmentStockBinding mBinding;
    FirebaseFirestore fireStore;
    SharedPreferences mSharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mBinding = FragmentStockBinding.inflate(getLayoutInflater());
        fireStore = FirebaseFirestore.getInstance();
        mSharedPreferences = requireContext().getSharedPreferences("loginUser", Context.MODE_PRIVATE);
        mBinding.requestStock.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_stockFragment_to_requestFragment);
        });
        mBinding.backward.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });
        String id  = mSharedPreferences.getString("id",null);
        fireStore
                .collection("requestStock")
                .whereEqualTo("id",id)
                .whereEqualTo("approved",true)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                        int totalStock  = Integer.parseInt(snapshot.getString("request flour")); // Use correct field name
                        int totalSold = Integer.parseInt(snapshot.getString("sold_items"));
                        mBinding.currentStock.setText("" + (totalStock - totalSold));
                        mBinding.totalStock.setText("" +totalSold);
                    }
                });


        return mBinding.getRoot();
    }

}
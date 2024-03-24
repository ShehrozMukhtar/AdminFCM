package com.example.flourcrisismanagment.main_activity.feed_back;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.flourcrisismanagment.databinding.FragmentFeedBackBinding;
import com.example.flourcrisismanagment.main_activity.eligible_fragment.EligibleActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FeedBackFragment extends Fragment {
    FragmentFeedBackBinding mBinding;
    FirebaseFirestore fireStore;
    SharedPreferences mSharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mBinding = FragmentFeedBackBinding.inflate(getLayoutInflater());
        fireStore = FirebaseFirestore.getInstance();
        mSharedPreferences = requireContext().getSharedPreferences("loginUser", Context.MODE_PRIVATE);
        mBinding.sendFeedback.setOnClickListener(view -> {
            String  title     = mBinding.titleFeedback.getText().toString().trim();
            String  description   = mBinding.detailFeedack.getText().toString().trim();
            String email = mSharedPreferences.getString("email",null);
            String id   = mSharedPreferences.getString("id",null);
            if(title.isEmpty()){
                mBinding.titleFeedback.setError("Enter Title");
                mBinding.titleFeedback.requestFocus();
            } else if (description.isEmpty()) {
                mBinding.detailFeedack.setError("Enter Title");
                mBinding.detailFeedack.requestFocus();
            }else {
                Toast.makeText(requireContext(), "Adding....Please Wait", Toast.LENGTH_SHORT).show();
                mBinding.titleFeedback.setText(" ");
                mBinding.detailFeedack.setText(" ");
                Map<String,Object> feedBack = new HashMap<>();
                feedBack.put("title",title);
                feedBack.put("description",description);
                feedBack.put("id",id);
                feedBack.put("email",email);

                fireStore
                        .collection("feedback")
                        .add(feedBack)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(requireContext(), "FeedBack Successfully", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(view).popBackStack();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(requireContext(), "Failed to add Data", Toast.LENGTH_SHORT).show();
                        });
            }

        });
        mBinding.backward.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });
        return mBinding.getRoot();
    }
}
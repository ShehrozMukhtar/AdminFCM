package com.example.flourcrisismanagment.main_activity.daily_history;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.flourcrisismanagment.R;
import com.example.flourcrisismanagment.databinding.FragmentDailyHistoryBinding;
import com.example.flourcrisismanagment.main_activity.daily_history.recycle_view.ItemDailyAdpater;
import com.example.flourcrisismanagment.main_activity.daily_history.recycle_view.ItemDailyModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class DailyHistoryFragment extends Fragment {
    List<ItemDailyModel> itemDailyModelList;
    ItemDailyAdpater adapter;
    FirebaseFirestore fireStore;
    RecyclerView recyclerView;
    SharedPreferences mSharedPreferences;
    ImageView mBackward;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_history,container,false);
        mBackward = view.findViewById(R.id.backward_daily);
        fireStore = FirebaseFirestore.getInstance();
        mSharedPreferences = requireContext().getSharedPreferences("loginUser", Context.MODE_PRIVATE);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mBackward.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).popBackStack();
        });
        String id  = mSharedPreferences.getString("id",null);
       //show firebase
        fireStore
                .collection("userDetails")
                .whereEqualTo("id",id)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    itemDailyModelList = new ArrayList<>();
                    for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                        String aName       = snapshot.get("name").toString();
                        String aPhoneNo    = snapshot.get("phoneNo").toString().trim();
                        String aGender     = snapshot.get("gender").toString().trim();
                        String aCardNo     = snapshot.get("cardNo").toString();
                        String aAddress    = snapshot.get("address").toString();
                        String aDate       = snapshot.get("date").toString();
                        String aCity       = snapshot.get("city").toString();
                        String aPrice      = snapshot.get("price").toString();
                        String time        = snapshot.get("time").toString();
                        Log.d(TAG, snapshot.getId() + " => " + snapshot.getData());
                         itemDailyModelList.add(new ItemDailyModel(aName, aPhoneNo, aGender, aCardNo, aAddress, aDate, aCity, aPrice));
                    }
                    adapter = new ItemDailyAdpater(itemDailyModelList);
                    recyclerView.setAdapter(adapter);

                })

                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error getting documents.", e);
                });


        return view;
    }
}
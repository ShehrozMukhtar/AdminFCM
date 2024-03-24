package com.example.flourcrisismanagment.main_activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import com.example.flourcrisismanagment.main_activity.eligible_fragment.EligibleActivity;
import com.example.flourcrisismanagment.R;
import com.example.flourcrisismanagment.auth_activity.AuthActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    FirebaseFirestore fireStore;
    SharedPreferences mSharedPreferences;
    NavGraph navGraph;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences = getSharedPreferences("loginUser", Context.MODE_PRIVATE);
        fireStore = FirebaseFirestore.getInstance();
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.app_nav_host_fragment);
        navController = navHostFragment.getNavController();
        navGraph = navController.getNavInflater().inflate(R.navigation.app_nav_graph);
        navGraph.setStartDestination(R.id.dashBoardFragment);
        navController.setGraph(navGraph);
        String id     =   mSharedPreferences.getString("id",null);
        fireStore
                .collection("userDetails")
                .whereEqualTo("id",id)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                        Long   time   =  Long.parseLong(snapshot.get("time").toString());
                        Log.d(TAG, snapshot.getId() + " => " + snapshot.getData());
                        Log.d(TAG, "User ID from SharedPreferences: " + id);
                        Long currentTime = System.currentTimeMillis();
                        Long twoDaysInMillis = (long) (2 * 24 * 60 * 60 * 1000);
                        if (currentTime - time > twoDaysInMillis ){
                        fireStore
                                .collection("userDetails")
                                .document(id)
                                .delete()
                                .addOnSuccessListener(aVoid -> {
                                    Log.d(TAG, "User Details Deleted  Successfully!");
                                })
                                .addOnFailureListener(e -> {
                                    Log.w(TAG, "Error deleting document", e);
                                });
                    }

                    }
                });

    }
    public void launchAuthActivity() {
        startActivity(new Intent(this, AuthActivity.class));
        finish();
    }
    public void launchEligibleActivity(){
        startActivity(new Intent(this, EligibleActivity.class));
    }
}
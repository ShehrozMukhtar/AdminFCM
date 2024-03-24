package com.example.flourcrisismanagment.auth_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;

import com.example.flourcrisismanagment.R;
import com.example.flourcrisismanagment.main_activity.MainActivity;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.auth_nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        NavGraph navGraph  = navController.getNavInflater().inflate(R.navigation.auth_nav_graph);
        navGraph.setStartDestination(R.id.loginFragment);
        navController.setGraph(navGraph);
    }
    public void launchMainActivity() {
        startActivity(new Intent(AuthActivity.this, MainActivity.class));
        finish();
    }
}
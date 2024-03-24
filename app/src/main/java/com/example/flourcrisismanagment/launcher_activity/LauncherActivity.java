package com.example.flourcrisismanagment.launcher_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.flourcrisismanagment.R;
import com.example.flourcrisismanagment.auth_activity.AuthActivity;
import com.example.flourcrisismanagment.main_activity.MainActivity;

import co.infinum.goldfinger.Goldfinger;

public class LauncherActivity extends AppCompatActivity {
     SharedPreferences mSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        mSharedPreferences = getSharedPreferences("loginUser", MODE_PRIVATE);
        Goldfinger goldfinger =  new Goldfinger.Builder(this).build();
        if (goldfinger.hasFingerprintHardware() && goldfinger.hasEnrolledFingerprint()) {
        Goldfinger.PromptParams params = new Goldfinger.PromptParams.Builder(this)
                .title("Put Your Finger On the Sensor")
                .negativeButtonText("Cancel")
            //    .description("For Finger Print Authentication")
             //   .subtitle("This is For Register Purpose")
                .build();
        if (mSharedPreferences.getString("email", null) == null) {
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                startActivity(new Intent(LauncherActivity.this, AuthActivity.class));
                finish();
            }, 3000);
        } else {
            goldfinger.authenticate(params, new Goldfinger.Callback() {
                @Override
                public void onResult(@NonNull Goldfinger.Result result) {
                    if (result.type() == Goldfinger.Type.SUCCESS) {
                        Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onError(@NonNull Exception e) {
                    e.printStackTrace();
                    Toast.makeText(LauncherActivity.this, "Finger not Recogninze", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }else {
         startAppropriateActivity();
        }
    }
    private void startAppropriateActivity() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (mSharedPreferences.getString("email", null) == null) {
                // User not logged in, start AuthActivity
                startActivity(new Intent(LauncherActivity.this, AuthActivity.class));
            } else {
                // User logged in, start MainActivity
                startActivity(new Intent(LauncherActivity.this, MainActivity.class));
            }
            finish();
        }, 3000); // 3-second delay
    }

}

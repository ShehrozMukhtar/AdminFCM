package com.example.flourcrisismanagment.main_activity.eligible_fragment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.flourcrisismanagment.databinding.ActivityEligibleBinding;
import com.example.flourcrisismanagment.main_activity.MainActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class EligibleActivity extends AppCompatActivity {
    ActivityEligibleBinding mBinding;
    FirebaseFirestore fireStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityEligibleBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        fireStore = FirebaseFirestore.getInstance();
        mBinding.backward.setOnClickListener(v -> {
            startActivity(new Intent(this,MainActivity.class));
        });
       mBinding.submit.setOnClickListener(v -> {
            // Get the card number from your input field (assuming you have an EditText for card number)
            String cnic = mBinding.enterCnic.getText().toString();
           if (cnic.length() != 13) {
               if (cnic.length() > 13) {
                   // Show an alert for increased CNIC length
                   AlertDialog.Builder builder = new AlertDialog.Builder(this);
                   builder.setTitle("Alert")
                           .setMessage("Invalid CNIC number length. CNIC length should not exceed 13 digits.")
                           .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                           .show();
               } else {
                   // Show an alert for decreased CNIC length
                   AlertDialog.Builder builder = new AlertDialog.Builder(this);
                   builder.setTitle("Alert")
                           .setMessage("Invalid CNIC number length. CNIC length should not be less than 13 digits.")
                           .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                           .show();
               }
               return; // Exit the onClickListener, preventing further execution
           }
            // Query Firestore to check if the card number already exists
            fireStore
                    .collection("userDetails")
                    .whereEqualTo("cardNo", cnic) // Assuming "cardNo" is the field for card numbers
                    .get()
                    .addOnSuccessListener(querySnapshot -> {
                      if (!querySnapshot.isEmpty()) {
                            // A user with the same card number already exists, show an alert
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle("Alert")
                                    .setMessage("A CNIC already exists.Flour is taken already")
                                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                    .show();
                        } else {
                          Intent intent = new Intent(this, User_Detail_Activity.class);
                          intent.putExtra("cnic", cnic);
                          startActivity(intent);
                          finish();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to check card number", Toast.LENGTH_SHORT).show();
                    });
        });

        mBinding.qrCode.setOnClickListener(view -> {
            startQRScanner();
        });
    }
    private void startQRScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CustomCaptureActivity.class); // Custom capture activity for landscape orientation
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Scan a QR Code");
        integrator.setCameraId(0);  // Use the back camera
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }
    public  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null && result.getContents() != null) {
            String scannedData = result.getContents();
            if (isValidScannedData(scannedData)) {
                String editTextValue = scannedData.substring(12, 25); // Skip first 16 and last character
                mBinding.enterCnic.setText(editTextValue);
            } else {
                // Handle invalid scanned data
                mBinding.enterCnic.setText("Invalid Scanned Data");
            }
        }

    }
    private boolean isValidScannedData(String scannedData) {
        return scannedData.length() == 26;
    }
}
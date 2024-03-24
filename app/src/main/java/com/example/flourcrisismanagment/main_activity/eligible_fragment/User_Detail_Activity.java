package com.example.flourcrisismanagment.main_activity.eligible_fragment;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;
import com.example.flourcrisismanagment.databinding.ActivityUserDetailBinding;
import com.example.flourcrisismanagment.main_activity.MainActivity;
import com.example.flourcrisismanagment.main_activity.stock_fragment.StockFragment;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
public class User_Detail_Activity extends AppCompatActivity {
    int year;
    int month;
    int day;
    FirebaseFirestore fireStore;
    ActivityUserDetailBinding mBinding;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityUserDetailBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        fireStore = FirebaseFirestore.getInstance();
        mSharedPreferences = getSharedPreferences("loginUser", Context.MODE_PRIVATE);
        //Date
        Calendar calendar = Calendar.getInstance();
        mBinding.enterDate.setOnClickListener(view -> {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                    year = selectedYear;
                    month = selectedMonth;
                    day = selectedDay;
                    String selectedDate = day + "/" + (month + 1) + "/" + year;
                    mBinding.enterDate.setText(selectedDate);
                }
            }, year, month, day);
            datePickerDialog.show();
        });
        String cardNo     = getIntent().getStringExtra("cnic");
        mBinding.enterCardNumber.setText(cardNo);
        mBinding.submit.setOnClickListener(view -> {
            String name       = mBinding.enterName.getText().toString().trim();
            String phoneNo    = mBinding.enterPhoneNumber.getText().toString().trim();
            String gender     =  " ";
            if (mBinding.male.isChecked()) {
                gender = "male";
            } else if (mBinding.female.isChecked()) {
                gender = "female";
            }
            String address    = mBinding.enterAddress.getText().toString().trim();
            String date       = mBinding.enterDate.getText().toString().trim();
            String city       = mBinding.enterCity.getText().toString().trim();
            String price      = mBinding.enterPrice.getText().toString().trim();
            String id  = mSharedPreferences.getString("id",null);

            if(name.isEmpty()){
                mBinding.enterName.setError("Enter Name");
                mBinding.enterName.requestFocus();
            }  else if (!isValidName(name)) {
                mBinding.enterName.setError("Invalid Name. Name should only contain letters.");
                mBinding.enterName.requestFocus();
            } else if (phoneNo.isEmpty()) {
                mBinding.enterPhoneNumber.setError("Enter Phone Number");
                mBinding.enterPhoneNumber.requestFocus();
            }else if (!isValidPhoneNumber(phoneNo)) {
                mBinding.enterPhoneNumber.setError("Invalid Phone Number. Please enter numeric values only.");
                mBinding.enterPhoneNumber.requestFocus();
            } else if (phoneNo.length() != 11) {
                mBinding.enterPhoneNumber.setError("Phone Number should have exactly 11 digits.");
                mBinding.enterPhoneNumber.requestFocus();
            }else if (cardNo.isEmpty()) {
                mBinding.enterCardNumber.setError("Enter Card Number");
                mBinding.enterCardNumber.requestFocus();
            }else if (address.isEmpty()) {
                mBinding.enterAddress.setError("Enter Address");
                mBinding.enterAddress.requestFocus();
            } else if (date.isEmpty()) {
                mBinding.enterDate.setError("Enter Date");
                mBinding.enterDate.requestFocus();
            } else if (city.isEmpty()) {
                mBinding.enterCity.setError("Enter City");
                mBinding.enterCity.requestFocus();
            }else if (price.isEmpty()) {
                mBinding.enterPrice.setError("Enter Price");
                mBinding.enterPrice.requestFocus();
            } else {
                Toast.makeText(this, "Adding....Please Wait", Toast.LENGTH_SHORT).show();
                mBinding.enterName.setText(" ");
                mBinding.enterPhoneNumber.setText(" ");
                mBinding.enterCardNumber.setText(" ");
                mBinding.enterAddress.setText(" ");
                mBinding.enterDate.setText(" ");
                mBinding.enterCity.setText(" ");
                mBinding.enterPrice.setText(" ");

                Map<String,Object> userDetail = new HashMap<>();
                userDetail.put("name",name);
                userDetail.put("phoneNo",phoneNo);
                userDetail.put("gender",gender);
                userDetail.put("cardNo",cardNo);
                userDetail.put("address",address);
                userDetail.put("date",date);
                userDetail.put("city",city);
                userDetail.put("price",price);
                userDetail.put("time",System.currentTimeMillis());
                userDetail.put("id",id);

                fireStore
                        .collection("userDetails")
                        .add(userDetail)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(this, "Data Added Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this,EligibleActivity.class));
                            finish();
                            fireStore
                                    .collection("requestStock")
                                    .get()
                                    .addOnSuccessListener(queryDocumentSnapshots -> {
                                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                                            String amount = snapshot.getString("sold_items"); // Use correct field name
                                            if (amount != null) {
                                                int currentAmount = Integer.parseInt(amount) + 1;
                                                String b =   String.valueOf(currentAmount);
                                                fireStore
                                                        .collection("requestStock")
                                                        .document(snapshot.getId())
                                                        .update("sold_items", b)
                                                        .addOnSuccessListener(aVoid -> {
                                                  //          Toast.makeText(this, "stock is updated", Toast.LENGTH_SHORT).show();
                                                            // Update successful

                                                        })
                                                        .addOnFailureListener(e -> {
                                                            // Handle update failure
                                                        });
                                            }
                                        }
                                        })
                                    .addOnFailureListener(e -> {

                                    });
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Failed to add Data", Toast.LENGTH_SHORT).show();
                        });

            }
        });

        mBinding.backward.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
    // Function to validate name (should contain only letters)
    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z]+");
    }
    // Function to validate phone number (should contain only numbers)
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d+");
    }
}
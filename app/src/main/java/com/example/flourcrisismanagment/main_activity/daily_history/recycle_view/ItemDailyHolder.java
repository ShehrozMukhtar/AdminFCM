package com.example.flourcrisismanagment.main_activity.daily_history.recycle_view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flourcrisismanagment.R;

public class ItemDailyHolder extends RecyclerView.ViewHolder {
    TextView mName,mPhoneNo,mGender,mCardNo,mAddress,mDate,mCity,mPrice;
    public ItemDailyHolder(@NonNull View itemView) {
        super(itemView);
        mName       = itemView.findViewById(R.id.name_text);
        mPhoneNo = itemView.findViewById(R.id.phone_no);
        mGender     = itemView.findViewById(R.id.gender_txt);
        mCardNo     = itemView.findViewById(R.id.card_no_txt);
        mAddress    = itemView.findViewById(R.id.address_txt);
        mDate       = itemView.findViewById(R.id.date_txt);
        mCity       = itemView.findViewById(R.id.city_txt);
        mPrice      = itemView.findViewById(R.id.price_txt);

    }
}

package com.example.flourcrisismanagment.main_activity.daily_history.recycle_view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flourcrisismanagment.R;

import java.util.List;

public class ItemDailyAdpater extends RecyclerView.Adapter<ItemDailyHolder> {
    List<ItemDailyModel> model;

    public ItemDailyAdpater(List<ItemDailyModel> model) {
        this.model = model;
    }

    @NonNull
    @Override
    public ItemDailyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemDailyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_history,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemDailyHolder holder, int position) {
        holder.mName.setText(model.get(position).getName());
        holder.mPhoneNo.setText(model.get(position).getPhoneNo());
        holder.mGender.setText(model.get(position).getGender());
        holder.mCardNo.setText(model.get(position).getCardNo());
        holder.mAddress.setText(model.get(position).getAddress());
        holder.mDate.setText(model.get(position).getDate());
        holder.mCity.setText(model.get(position).getCity());
        holder.mPrice.setText(model.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return model.size();
    }
}

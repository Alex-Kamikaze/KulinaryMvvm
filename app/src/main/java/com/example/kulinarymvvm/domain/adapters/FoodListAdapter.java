package com.example.kulinarymvvm.domain.adapters;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kulinarymvvm.data.db.FoodEntity;
import com.example.kulinarymvvm.databinding.FoodListItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder> {

    private ArrayList<FoodEntity> foodList = new ArrayList<>();

    public FoodListAdapter(ArrayList<FoodEntity> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FoodListItemBinding binding = FoodListItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListAdapter.ViewHolder holder, int position) {
        holder.binding.foodName.setText(foodList.get(position).getFoodName());
        Picasso.get().load(foodList.get(position).getFoodImageUri()).into(holder.binding.foodImage);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        FoodListItemBinding binding;
        public ViewHolder(@NonNull FoodListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

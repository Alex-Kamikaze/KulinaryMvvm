package com.example.kulinarymvvm.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.kulinarymvvm.R;
import com.example.kulinarymvvm.data.db.FoodEntity;
import com.example.kulinarymvvm.databinding.FragmentMainBinding;
import com.example.kulinarymvvm.domain.adapters.FoodListAdapter;
import com.example.kulinarymvvm.domain.viewmodels.FoodListViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    FragmentMainBinding binding;
    FoodListViewModel viewModel;


    public MainFragment() {}

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(FoodListViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater);
        ArrayList<FoodEntity> foodList = (ArrayList<FoodEntity>) viewModel.foodList.getValue();
        if(foodList == null) {
            foodList = new ArrayList<>();
        }
        FoodListAdapter adapter = new FoodListAdapter(foodList);
        viewModel.foodList.observe(getViewLifecycleOwner(), foodEntities -> {
            adapter.updateData((ArrayList<FoodEntity>) foodEntities);
        });
        binding.foodListView.setAdapter(adapter);
        binding.addFoodButton.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_mainFragment_to_addFoodFragment);
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
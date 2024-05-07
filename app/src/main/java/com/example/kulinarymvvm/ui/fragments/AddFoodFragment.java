package com.example.kulinarymvvm.ui.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.kulinarymvvm.data.db.FoodCategoryEntity;
import com.example.kulinarymvvm.data.db.FoodEntity;
import com.example.kulinarymvvm.databinding.FragmentAddFoodBinding;
import com.example.kulinarymvvm.domain.viewmodels.AddFoodViewModel;
import com.example.kulinarymvvm.data.workers.SaveImageWorker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.UUID;

public class AddFoodFragment extends Fragment {
    FragmentAddFoodBinding binding;
    FoodEntity newFoodItem = new FoodEntity();
    AddFoodViewModel viewModel;
    public AddFoodFragment() {}

    public static AddFoodFragment newInstance() {
        return new AddFoodFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddFoodViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddFoodBinding.inflate(inflater);
        ActivityResultLauncher<String> getFoodImagePicker = registerForActivityResult(new ActivityResultContracts.GetContent(), foodImage -> {
            if(foodImage != null) {
                Picasso.get().load(foodImage).into(binding.foodImage);
                String newFileName = UUID.randomUUID() + ".jpg";
                Data workerInput = new Data.Builder()
                        .putString("image_uri", foodImage.toString())
                        .putString("image_filename", newFileName)
                        .build();
                Constraints workerConstraints = new Constraints.Builder()
                        .setRequiresStorageNotLow(true)
                        .build();
                OneTimeWorkRequest saveImageWork = new OneTimeWorkRequest.Builder(SaveImageWorker.class)
                        .setInputData(workerInput)
                        .setConstraints(workerConstraints)
                        .build();
                WorkManager.getInstance(requireContext()).enqueue(saveImageWork);
                newFoodItem.setFoodImageUri(newFileName);
            }
            else {
                binding.imageHint.setText("Ошибка: вы должны выбрать фотографию блюда!");
                binding.imageHint.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }
        });

        ArrayList<FoodCategoryEntity> categoryEntities = (ArrayList<FoodCategoryEntity>) viewModel.categories.getValue();
        if(categoryEntities == null) {
            categoryEntities = new ArrayList<>();
        }
        ArrayList<String> categories = new ArrayList<>();
        categoryEntities.forEach(foodCategoryEntity -> {
            categories.add(foodCategoryEntity.getCategoryName());
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categories);

        AdapterView.OnItemSelectedListener adapterListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedCategory = (String)adapterView.getItemAtPosition(i);
                newFoodItem.setFoodCategoryId(viewModel.getCategoryByName(selectedCategory).getCategoryId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        };

        binding.categoryChooser.setAdapter(adapter);
        binding.categoryChooser.setOnItemSelectedListener(adapterListener);

        binding.foodImage.setOnClickListener(v -> getFoodImagePicker.launch("image/*"));
        binding.saveButton.setOnClickListener(v -> {
            if(binding.foodNameInput.getText().toString().isEmpty() ||
                    binding.foodPriceInput.getText().toString().isEmpty() ||
                    newFoodItem.getFoodCategoryId() == 0 ||
                    newFoodItem.getFoodImageUri().isEmpty()
            ) {
                Toast.makeText(requireContext(), "Ошибка: вы не заполнили все поля", Toast.LENGTH_SHORT).show();
            }
            else {
                newFoodItem.setFoodName(binding.foodNameInput.getText().toString());
                newFoodItem.setFoodPrice(Integer.parseInt(binding.foodPriceInput.getText().toString()));
                viewModel.addNewFood(newFoodItem);
                Navigation.findNavController(binding.getRoot()).popBackStack();
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
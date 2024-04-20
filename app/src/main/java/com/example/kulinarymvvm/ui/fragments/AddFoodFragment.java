package com.example.kulinarymvvm.ui.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kulinarymvvm.R;
import com.example.kulinarymvvm.data.db.FoodEntity;
import com.example.kulinarymvvm.databinding.FragmentAddFoodBinding;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class AddFoodFragment extends Fragment {
    FragmentAddFoodBinding binding;
    FoodEntity newFoodItem = new FoodEntity();
    public AddFoodFragment() {}

    public static AddFoodFragment newInstance() {
        return new AddFoodFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddFoodBinding.inflate(inflater);
        ActivityResultLauncher<String> getFoodImagePicker = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri foodImage) {
                if(foodImage != null) {
                    Picasso.get().load(foodImage).into(binding.foodImage);
                    try {
                        byte[] image = new byte[] {};
                        requireContext().getContentResolver().openInputStream(foodImage).read(image);
                        String newFileName = UUID.randomUUID() + ".jpg";
                        File newImage = new File(requireContext().getFilesDir(), newFileName);
                        new FileOutputStream(newImage).write(image);
                        newFoodItem.setFoodImageUri(newImage.getPath());
                    } catch (IOException e) {
                        Toast.makeText(requireContext(), "Произошла ошибка при сохранении картинки: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    binding.imageHint.setText("Ошибка: вы должны выбрать фотографию блюда!");
                    binding.imageHint.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                }
            }
        });

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
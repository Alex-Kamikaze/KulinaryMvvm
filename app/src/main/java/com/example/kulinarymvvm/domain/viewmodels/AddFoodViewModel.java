package com.example.kulinarymvvm.domain.viewmodels;

import android.view.ContextMenu;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.kulinarymvvm.FoodApplication;
import com.example.kulinarymvvm.data.db.FoodCategoryDao;
import com.example.kulinarymvvm.data.db.FoodCategoryEntity;
import com.example.kulinarymvvm.data.db.FoodDao;
import com.example.kulinarymvvm.data.db.FoodEntity;

import java.util.List;
import java.util.concurrent.Executor;

public class AddFoodViewModel extends ViewModel {
    private final FoodDao foodDao = FoodApplication
            .getInstance()
            .getDb()
            .getFoodDao();

    private final FoodCategoryDao categoryDao = FoodApplication
            .getInstance()
            .getDb()
            .getCategoryDao();

    public LiveData<List<FoodCategoryEntity>> categories = categoryDao.getAllCategories();

    public void addNewFood(FoodEntity food) {
        foodDao.insertNewFood(food);
    }

    public FoodCategoryEntity getCategoryByName(String categoryName) {
        return categoryDao.getCategoryByName(categoryName);
    }

    public void saveFoodImage() {
        Executor executor = runnable -> {

        };
    }
}

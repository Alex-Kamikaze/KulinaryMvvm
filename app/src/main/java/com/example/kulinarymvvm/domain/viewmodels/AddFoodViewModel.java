package com.example.kulinarymvvm.domain.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.kulinarymvvm.FoodApplication;
import com.example.kulinarymvvm.data.db.FoodCategoryDao;
import com.example.kulinarymvvm.data.db.FoodCategoryEntity;
import com.example.kulinarymvvm.data.db.FoodDao;

import java.util.List;

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

}

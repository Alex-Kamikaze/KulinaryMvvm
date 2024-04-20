package com.example.kulinarymvvm.domain.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.kulinarymvvm.FoodApplication;
import com.example.kulinarymvvm.data.db.AppDatabase;
import com.example.kulinarymvvm.data.db.FoodDao;
import com.example.kulinarymvvm.data.db.FoodEntity;

import java.util.List;

public class FoodListViewModel extends ViewModel {
    private final FoodDao foodDao = FoodApplication.getInstance().getDb().getFoodDao();

    public LiveData<List<FoodEntity>> foodList = foodDao.getAllFood();
}

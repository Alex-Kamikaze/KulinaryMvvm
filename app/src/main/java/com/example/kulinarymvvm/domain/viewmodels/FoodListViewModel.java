package com.example.kulinarymvvm.domain.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.kulinarymvvm.data.db.AppDatabase;
import com.example.kulinarymvvm.data.db.FoodDao;
import com.example.kulinarymvvm.data.db.FoodEntity;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class FoodListViewModel extends ViewModel {
    AppDatabase db;
    @Inject FoodListViewModel(AppDatabase appDb) {
        this.db = appDb;
    }

    LiveData<List<FoodEntity>> foodList = foodDao.getAllFood();
}

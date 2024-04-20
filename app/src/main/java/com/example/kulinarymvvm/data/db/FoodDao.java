package com.example.kulinarymvvm.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FoodDao {

    @Insert
    void insertNewFood(FoodEntity... foodEntities);

    @Update
    void updateFood(FoodEntity... foodEntities);

    @Delete
    void deleteFood(FoodEntity... foodEntities);

    @Query("SELECT * FROM foodentity")
    LiveData<List<FoodEntity>> getAllFood();

    @Query("SELECT * FROM foodentity WHERE FoodID = :id")
    FoodEntity getFoodById(int id);

    @Query("SELECT * FROM foodentity WHERE foodName LIKE :foodName")
    LiveData<List<FoodEntity>> searchFoodByName(String foodName);

}

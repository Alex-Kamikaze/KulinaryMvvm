package com.example.kulinarymvvm.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FoodCategoryDao {

    @Insert
    void insertNewCategories(FoodCategoryEntity... entities);

    @Update
    void updateFood(FoodCategoryEntity... entities);

    @Delete
    void deleteFood(FoodCategoryEntity... entities);

    @Query("SELECT * FROM foodcategoryentity")
    LiveData<List<FoodCategoryEntity>> getAllCategories();

    @Query("SELECT * FROM foodcategoryentity WHERE CategoryId = :id")
    FoodCategoryEntity getCategoryById(int id);

    @Query("SELECT * FROM foodcategoryentity WHERE categoryName LIKE :categoryName")
    LiveData<List<FoodCategoryEntity>> searchCategoryByName(String categoryName);
}

package com.example.kulinarymvvm.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FoodCategoryEntity.class, FoodEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FoodDao getFoodDao();
    public abstract FoodCategoryDao getCategoryDao();

}

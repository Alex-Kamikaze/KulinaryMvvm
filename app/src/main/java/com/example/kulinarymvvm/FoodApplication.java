package com.example.kulinarymvvm;

import android.app.Application;

import androidx.room.Room;

import com.example.kulinarymvvm.data.db.AppDatabase;

public class FoodApplication extends Application {
    private static FoodApplication Instance;
    private AppDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        Instance = this;
        db = Room.databaseBuilder(this, AppDatabase.class, "app.db")
                .allowMainThreadQueries()
                .build();
    }

    public static FoodApplication getInstance() {
        return Instance;
    }

    public AppDatabase getDb() {
        return db;
    }
}

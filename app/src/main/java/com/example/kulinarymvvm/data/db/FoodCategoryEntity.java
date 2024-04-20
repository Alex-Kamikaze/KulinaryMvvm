package com.example.kulinarymvvm.data.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class FoodCategoryEntity {

    @PrimaryKey(autoGenerate = true)
    private int CategoryId;

    @ColumnInfo(name = "categoryName")
    private String CategoryName;

    public FoodCategoryEntity(int categoryId, String categoryName) {
        CategoryId = categoryId;
        CategoryName = categoryName;
    }

    public FoodCategoryEntity() {}

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }
}

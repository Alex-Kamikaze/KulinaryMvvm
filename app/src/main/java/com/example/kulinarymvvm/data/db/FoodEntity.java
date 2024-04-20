package com.example.kulinarymvvm.data.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.android.material.circularreveal.CircularRevealHelper;

@Entity(foreignKeys = {@ForeignKey(
        entity = FoodCategoryEntity.class,
        parentColumns = {"CategoryId"},
        childColumns = {"foodCategoryId"},
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
)})
public class FoodEntity {

    @PrimaryKey(autoGenerate = true)
    private int FoodID;

    @ColumnInfo(name = "foodName")
    private String FoodName;

    @ColumnInfo(name = "foodCategoryId")
    private int FoodCategoryId;

    @ColumnInfo(name = "foodImageUri")
    private String FoodImageUri;

    @ColumnInfo(name = "foodPrice")
    private int FoodPrice;

    public FoodEntity(int foodID, String foodName, int foodCategoryId, String foodImageUri, int foodPrice) {
        FoodID = foodID;
        FoodName = foodName;
        FoodCategoryId = foodCategoryId;
        FoodImageUri = foodImageUri;
        FoodPrice = foodPrice;
    }

    public FoodEntity() {}

    public int getFoodID() {
        return FoodID;
    }

    public void setFoodID(int foodID) {
        FoodID = foodID;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public int getFoodCategoryId() {
        return FoodCategoryId;
    }

    public void setFoodCategoryId(int foodCategoryId) {
        FoodCategoryId = foodCategoryId;
    }

    public String getFoodImageUri() {
        return FoodImageUri;
    }

    public void setFoodImageUri(String foodImageUri) {
        FoodImageUri = foodImageUri;
    }

    public int getFoodPrice() {
        return FoodPrice;
    }

    public void setFoodPrice(int foodPrice) {
        FoodPrice = foodPrice;
    }
}

package com.test.recipeapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.test.recipeapp.entities.converter.MealListConverter

@Entity(tableName = "Meal")
class Meal(
    @ColumnInfo(name = "meals")
    @Expose
    @SerializedName("meals")
    @TypeConverters(MealListConverter::class)
    var mealsItem: List<MealsItems>? = null,

    @ColumnInfo(name = "name")
    @Expose
    @SerializedName("name")
    var name: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}
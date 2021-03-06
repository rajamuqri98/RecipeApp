package com.test.recipeapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "MealDetails")
class MealDetails(

        @ColumnInfo(name = "name")
        @Expose
        @SerializedName("name")
        var name: String,

        @ColumnInfo(name = "category")
        @Expose
        @SerializedName("category")
        var category: String,

        @ColumnInfo(name = "time")
        @Expose
        @SerializedName("time")
        var time: String,

        @ColumnInfo(name = "calorie")
        @Expose
        @SerializedName("calorie")
        var calorie: String,

        @ColumnInfo(name = "serving")
        @Expose
        @SerializedName("serving")
        var serving: String,

        @ColumnInfo(name = "ingredients")
        @Expose
        @SerializedName("ingredients")
        var ingredients: String,

        @ColumnInfo(name = "measurements")
        @Expose
        @SerializedName("measurements")
        var measurements: String,

        @ColumnInfo(name = "instructions")
        @Expose
        @SerializedName("instructions")
        var instructions: String,

        @ColumnInfo(name = "images")
        @Expose
        @SerializedName("images")
        var images: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}
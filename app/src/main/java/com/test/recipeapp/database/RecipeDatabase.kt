package com.test.recipeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import com.test.recipeapp.MainResources
import com.test.recipeapp.dao.RecipeDao
import com.test.recipeapp.database.RecipeMigration.Companion.MIGRATION_1_2
import com.test.recipeapp.entities.*
import com.test.recipeapp.entities.converter.CategoryListConverter
import com.test.recipeapp.entities.converter.MealListConverter

@Database(entities = [
    Recipes::class,
    CategoryItems::class,
    Category::class,
    Meal::class,
    MealsItems::class,
    MealDetails::class]
        ,version = MainResources.DB_VERSION
        ,exportSchema = false)
@TypeConverters(CategoryListConverter::class, MealListConverter::class)
abstract class RecipeDatabase: RoomDatabase() {

    companion object{

        var recipesDatabase:RecipeDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): RecipeDatabase{
            if (recipesDatabase == null){
                recipesDatabase = Room.databaseBuilder(
                    context,
                    RecipeDatabase::class.java,
                    MainResources.DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return recipesDatabase!!
        }
    }

    abstract fun recipeDao(): RecipeDao
}
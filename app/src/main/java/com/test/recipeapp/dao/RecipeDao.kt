package com.test.recipeapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.recipeapp.entities.CategoryItems
import com.test.recipeapp.entities.Meal
import com.test.recipeapp.entities.MealDetails
import com.test.recipeapp.entities.MealsItems

@Dao
interface RecipeDao {

    @Query("SELECT * FROM categoryitems ORDER BY id DESC")
    suspend fun getAllCategory() : List<CategoryItems>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categoryItems: CategoryItems?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealItems(mealsItems: MealsItems?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealDetails(mealDetails: MealDetails)

    @Query("DELETE FROM MealDetails WHERE id = :details_id")
    suspend fun deleteMealDetailbyId(details_id: String)

    @Query("DELETE FROM MealItems WHERE idMeal = :meals_id")
    suspend fun deleteMealItembyMealId(meals_id: String)

    @Query("DELETE FROM categoryitems")
    suspend fun clearDb()

    @Query("SELECT id FROM MealDetails ORDER BY id ASC")
    suspend fun getMealDetailsId() : List<Int>

    @Query("SELECT id FROM MealDetails LIMIT 1")
    suspend fun getMealDetailsLastId() : Int

    @Query("SELECT * FROM MealItems WHERE categoryName = :categoryName ORDER BY id DESC")
    suspend fun getSpecificMealList(categoryName:String) : List<MealsItems>

    @Query("SELECT * FROM MealItems WHERE strmeal LIKE :mealName ORDER BY id DESC")
    suspend fun getSpecificMealQuery(mealName: String) : List<MealsItems>

    @Query("SELECT * FROM MealDetails WHERE id = :details_id LIMIT 1")
    suspend fun getSpecificMealDetails(details_id: String) : MealDetails
}
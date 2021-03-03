package com.test.recipeapp.ui.splash

import android.view.View
import android.widget.Toast
import com.test.recipeapp.database.RecipeDatabase
import com.test.recipeapp.entities.Category
import com.test.recipeapp.entities.Meal
import com.test.recipeapp.entities.MealsItems
import com.test.recipeapp.interfaces.GetDataService
import com.test.recipeapp.retrofitclient.RetrofitClientInstance
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashController(parent: SplashActivity) {

    private val activity: SplashActivity = parent

    fun readStorage() {
        clearDataBase()
        getCategories()
    }

    private fun clearDataBase() {
        activity.launch {
            this.let {
                RecipeDatabase.getDatabase(activity).recipeDao().clearDb()
            }
        }
    }

    private fun getCategories() {
        val service = RetrofitClientInstance.retrofitInstance!!.create(GetDataService::class.java)
        val call = service.getCategoryList()
        call.enqueue(object : Callback<Category> {
            override fun onFailure(call: Call<Category>, t: Throwable) {
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }
            override fun onResponse(
                call: Call<Category>,
                response: Response<Category>
            ) {
                for (arr in response.body()!!.categorieitems!!) {
                    getMeal(arr.strcategory)
                }
                insertDataIntoRoomDb(response.body())
            }
        })
    }

    fun getMeal(categoryName: String) {
        val service = RetrofitClientInstance.retrofitInstance!!.create(GetDataService::class.java)
        val call = service.getMealList(categoryName)
        call.enqueue(object : Callback<Meal> {
            override fun onFailure(call: Call<Meal>, t: Throwable) {

                activity.loader.visibility = View.INVISIBLE
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(
                call: Call<Meal>,
                response: Response<Meal>
            ) {
                insertMealDataIntoRoomDb(categoryName, response.body())
            }
        })
    }

    fun insertDataIntoRoomDb(category: Category?) {
        activity.launch {
            this.let {

                for (arr in category!!.categorieitems!!) {
                    RecipeDatabase.getDatabase(activity)
                        .recipeDao().insertCategory(arr)
                }
            }
        }
    }

    fun insertMealDataIntoRoomDb(categoryName: String, meal: Meal?) {
        activity.launch {
            this.let {
                for (arr in meal!!.mealsItem!!) {
                    val mealItemModel = MealsItems(
                        arr.idMeal,
                        categoryName,
                        arr.strMeal,
                        arr.strMealThumb
                    )
                    RecipeDatabase.getDatabase(activity)
                        .recipeDao().insertMealItems(mealItemModel)
                    // Log.d("mealData", arr.toString())
                }
                activity.btnGetStarted.visibility = View.VISIBLE
            }
        }
    }
}
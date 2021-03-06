package com.test.recipeapp.ui.detail

import android.widget.Toast
import com.bumptech.glide.Glide
import com.test.recipeapp.database.RecipeDatabase
import com.test.recipeapp.entities.MealResponse
import com.test.recipeapp.interfaces.GetDataService
import com.test.recipeapp.retrofitclient.RetrofitClientInstance
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailController(parent: DetailActivity) {

    val activity = parent
    lateinit var category: String

    fun getSpecificItem(id: String, preload: Boolean) {
        if (preload) {
            getFromServer(id)
        } else {
            getFromLocal(id)
        }
    }

    private fun getFromServer(id: String) {
        val service = RetrofitClientInstance.retrofitInstance!!.create(GetDataService::class.java)
        val call = service.getSpecificItem(id)
        call.enqueue(object : Callback<MealResponse> {
            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(
                call: Call<MealResponse>,
                response: Response<MealResponse>
            ) {

                Glide.with(activity).load(response.body()!!.mealsEntity[0].strmealthumb)
                    .into(activity.imgItem)

                activity.tvCategory.text = response.body()!!.mealsEntity[0].strmeal

                val ingredient =
                    "${response.body()!!.mealsEntity[0].stringredient1}      ${response.body()!!.mealsEntity[0].strmeasure1}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient2}      ${response.body()!!.mealsEntity[0].strmeasure2}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient3}      ${response.body()!!.mealsEntity[0].strmeasure3}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient4}      ${response.body()!!.mealsEntity[0].strmeasure4}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient5}      ${response.body()!!.mealsEntity[0].strmeasure5}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient6}      ${response.body()!!.mealsEntity[0].strmeasure6}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient7}      ${response.body()!!.mealsEntity[0].strmeasure7}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient8}      ${response.body()!!.mealsEntity[0].strmeasure8}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient9}      ${response.body()!!.mealsEntity[0].strmeasure9}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient10}      ${response.body()!!.mealsEntity[0].strmeasure10}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient11}      ${response.body()!!.mealsEntity[0].strmeasure11}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient12}      ${response.body()!!.mealsEntity[0].strmeasure12}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient13}      ${response.body()!!.mealsEntity[0].strmeasure13}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient14}      ${response.body()!!.mealsEntity[0].strmeasure14}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient15}      ${response.body()!!.mealsEntity[0].strmeasure15}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient16}      ${response.body()!!.mealsEntity[0].strmeasure16}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient17}      ${response.body()!!.mealsEntity[0].strmeasure17}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient18}      ${response.body()!!.mealsEntity[0].strmeasure18}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient19}      ${response.body()!!.mealsEntity[0].strmeasure19}\n" +
                            "${response.body()!!.mealsEntity[0].stringredient20}      ${response.body()!!.mealsEntity[0].strmeasure20}\n"

                activity.tvIngredients.text = ingredient
                activity.tvInstructions.text = response.body()!!.mealsEntity[0].strinstructions
            }
        })
    }

    private fun getFromLocal(id: String) {
        activity.launch {
            let {
                val meal = RecipeDatabase.getDatabase(activity).recipeDao().getSpecificMealDetails(id)

                Glide.with(activity).load(meal.images).into(activity.imgItem)

                activity.tvCategory.text = meal.name
                activity.tvTime.text = meal.time
                activity.tvCal.text = meal.calorie
                activity.tvServing.text = meal.serving

                category = meal.category

                val ingredients = meal.ingredients.split(",")
                val measurements = meal.measurements.split(",")

                var text = ""
                var position = 0
                ingredients.forEach {
                    text += it + "    " + measurements[position] + "\n"
                    position += 1
                }

                activity.tvIngredients.text = text
                activity.tvInstructions.text = meal.instructions
            }
        }
    }
}
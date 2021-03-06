package com.test.recipeapp.ui.addrecipe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.recipeapp.MainResources
import com.test.recipeapp.R
import com.test.recipeapp.database.RecipeDatabase
import com.test.recipeapp.entities.MealDetails
import com.test.recipeapp.entities.MealsItems
import com.test.recipeapp.ui.BaseActivity
import com.test.recipeapp.ui.home.HomeActivity
import com.test.recipeapp.uiadapter.IngredientAdapter
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_newrecipe.*
import kotlinx.coroutines.launch

class AddActivity: BaseActivity() {

    private val TAG = "AddActivity"
    private val list = ArrayList<Ingredients>()

    private lateinit var id: String
    private lateinit var adapter: IngredientAdapter
    private lateinit var cat: String
    private var img: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newrecipe)

        cat = intent.getStringExtra("cat") + " Category"
        id = intent.getStringExtra("id").toString()
        new_category.text = cat

        val recyclerView: RecyclerView = findViewById(R.id.rv_add_ingredients)
        adapter = IngredientAdapter(list)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter

        if (id != "null") {
            editPage(id)
        }

        imgToolbarBtnDelete.setOnClickListener{
            if (id != "") {
                deleteInDB()
            }
            nextActivity()
        }

        adBtn.setOnClickListener {
            list.add(Ingredients("Name", "Amount"))
            adapter.notifyDataSetChanged()
        }

        btnSubmit.setOnClickListener {
            if (validate()) {
                saveIntoDB()
            } else {
                Toast.makeText(this@AddActivity, "Please fill all forms.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        etItem.setOnClickListener {
            openGallery()
        }
    }

    private fun saveIntoDB() {
        launch {
            let {
                val details = MealDetails(
                    etName.text.toString(),
                    cat.replace(" Category", ""),
                    etTime.text.toString(),
                    etCal.text.toString(),
                    etServing.text.toString(),
                    adapter.getIngredientName(),
                    adapter.getIngredientMeasurement(),
                    etInstructions.text.toString(),
                    img
                )
                if (id != "null") {
                    details.id = id.toInt()
                }

                RecipeDatabase.getDatabase(this@AddActivity)
                    .recipeDao().insertMealDetails(details)

                if (id == "null") {
                    val mealId = RecipeDatabase.getDatabase(this@AddActivity)
                        .recipeDao().getMealDetailsLastId()

                    val mealItemModel = MealsItems(
                        mealId.toString(),
                        cat.replace(" Category", ""),
                        etName.text.toString(),
                        img
                    )

                    RecipeDatabase.getDatabase(this@AddActivity)
                        .recipeDao().insertMealItems(mealItemModel)
                }

                Toast.makeText(this@AddActivity, "Successfully added.", Toast.LENGTH_SHORT)
                    .show()
                nextActivity()
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type ="image/*"
        startActivityForResult(intent, MainResources.READ_STORAGE_PERMISSION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == MainResources.READ_STORAGE_PERMISSION) {
            Glide.with(this).load(data?.data).into(etItem)
            img = data?.data.toString()
        }
    }

    private fun deleteInDB() {
        launch {
            let {
                RecipeDatabase.getDatabase(this@AddActivity).recipeDao().deleteMealDetailbyId(id)
                RecipeDatabase.getDatabase(this@AddActivity).recipeDao().deleteMealItembyMealId(id)

                Toast.makeText(this@AddActivity, "Successfully deleted.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun nextActivity() {
        val intent = Intent(this@AddActivity, HomeActivity::class.java)
        adapter.arrIngredients.clear()
        startActivity(intent)
    }

    private fun validate() : Boolean {
        if (!hasText(etName)) return false
        if (!hasText(etTime)) return false
        if (!hasText(etCal)) return false
        if (!hasText(etServing)) return false
        if (adapter.arrIngredients.size == 0) return false
        if (!hasText(etInstructions)) return false
        return true
    }

    private fun hasText(editText: EditText) : Boolean {
        return editText.text.isNotEmpty()
    }

    private fun editPage(id: String) {
        launch {
            let {
                val meal = RecipeDatabase.getDatabase(this@AddActivity).recipeDao().getSpecificMealDetails(id)

                Glide.with(this@AddActivity).load(meal.images).into(etItem)

                img = meal.images

                etName.setText(meal.name)
                etTime.setText(meal.time)
                etCal.setText(meal.calorie)
                etServing.setText(meal.serving)

                val ingredients = meal.ingredients.split(",")
                val measurement = meal.measurements.split(",")

                ingredients.dropLast(1)
                measurement.dropLast(1)

                var position = 0
                ingredients.forEach {
                    list.add(Ingredients(it, measurement[position]))
                    position += 1
                }
                adapter.notifyDataSetChanged()

                etInstructions.setText(meal.instructions)
            }
        }
    }
}
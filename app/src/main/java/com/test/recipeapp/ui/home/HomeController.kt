package com.test.recipeapp.ui.home

import androidx.recyclerview.widget.LinearLayoutManager
import com.test.recipeapp.database.RecipeDatabase
import com.test.recipeapp.entities.CategoryItems
import com.test.recipeapp.entities.MealsItems
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeController(parent: HomeActivity) {

    private val activity = parent

    var arrMainCategory = ArrayList<CategoryItems>()
    var arrSubCategory = ArrayList<MealsItems>()

    fun getDataFromDb(){
        activity.launch {
            this.let {
                val cat = RecipeDatabase.getDatabase(activity).recipeDao().getAllCategory()
                arrMainCategory = cat as ArrayList<CategoryItems>
                arrMainCategory.reverse()

                getMealDataFromDb(arrMainCategory[0].strcategory)
                activity.mainCategoryAdapter.setData(arrMainCategory)
                activity.rv_main_category.layoutManager = LinearLayoutManager(activity,
                    LinearLayoutManager.HORIZONTAL,false)
                activity.rv_main_category.adapter = activity.mainCategoryAdapter
            }


        }
    }

    fun getMealDataFromDb(categoryName:String){
        val text = "$categoryName Category"
        activity.tvCategory.text = text
        activity.launch {
            this.let {
                val cat = RecipeDatabase.getDatabase(activity).recipeDao().getSpecificMealList(categoryName)
                arrSubCategory = cat as ArrayList<MealsItems>
                activity.subCategoryAdapter.setData(arrSubCategory)
                activity.rv_sub_category.layoutManager = LinearLayoutManager(activity,
                    LinearLayoutManager.HORIZONTAL,false)
                activity.rv_sub_category.adapter = activity.subCategoryAdapter
            }
        }
    }

    fun getMealDataFromDbByQuery(query: String?) {
        val newQuery = "%$query%"
        val text = "Search '$query'"
        activity.tvCategory.text = text
        activity.launch {
            this.let {
                val cat = RecipeDatabase.getDatabase(activity).recipeDao().getSpecificMealQuery(newQuery)
                arrSubCategory = cat as ArrayList<MealsItems>
                activity.subCategoryAdapter.setData(arrSubCategory)
                activity.rv_sub_category.layoutManager = LinearLayoutManager(activity,
                    LinearLayoutManager.HORIZONTAL,false)
                activity.rv_sub_category.adapter = activity.subCategoryAdapter
            }
        }
    }

    fun getCountMealDataFromDb(query: String?): Int? {
        val newQuery = "%$query%"
        val cat : List<MealsItems> = runBlocking {
            RecipeDatabase.getDatabase(activity).recipeDao().getSpecificMealQuery(newQuery)
        }
        return cat.size
    }
}
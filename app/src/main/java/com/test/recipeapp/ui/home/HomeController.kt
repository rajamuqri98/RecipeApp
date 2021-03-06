package com.test.recipeapp.ui.home

import android.content.Intent
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.recipeapp.database.RecipeDatabase
import com.test.recipeapp.entities.CategoryItems
import com.test.recipeapp.entities.MealsItems
import com.test.recipeapp.ui.detail.DetailActivity
import com.test.recipeapp.ui.addrecipe.AddActivity
import com.test.recipeapp.uiadapter.MainCategoryAdapter
import com.test.recipeapp.uiadapter.SubCategoryAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeController(parent: HomeActivity) {

    private val activity = parent
    private var cur_category = ""

    var arrMainCategory = ArrayList<CategoryItems>()
    var arrSubCategory = ArrayList<MealsItems>()
    var listId = ArrayList<Int>()

    fun getDataFromDb(){
        activity.launch {
            this.let {
                val cat = RecipeDatabase.getDatabase(activity).recipeDao().getAllCategory()
                arrMainCategory = cat as ArrayList<CategoryItems>
                arrMainCategory.reverse()

                listId = RecipeDatabase.getDatabase(activity).recipeDao().getMealDetailsId().toMutableList() as ArrayList<Int>

                cur_category = arrMainCategory[0].strcategory
                getMealDataFromDb(cur_category)
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

    fun getCountMealDataFromDb(query: String?): Int {
        val newQuery = "%$query%"
        val cat : List<MealsItems> = runBlocking {
            RecipeDatabase.getDatabase(activity).recipeDao().getSpecificMealQuery(newQuery)
        }
        return cat.size
    }

    val onClicked  = object : MainCategoryAdapter.OnItemClickListener{
        override fun onClicked(categoryName: String) {
            cur_category = categoryName
            getMealDataFromDb(categoryName)
        }
    }

    val onClickedSubItem  = object : SubCategoryAdapter.OnItemClickListener{
        override fun onClicked(id: String) {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("id",id)
            if (id.toInt() in listId) {
                // If Local
                intent.putExtra("preload",false)
            }
            activity.startActivity(intent)
        }
    }

    val searchByQuery = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            if (getCountMealDataFromDb(query) > 0) {
                getMealDataFromDbByQuery(query)
            } else {
                Toast.makeText(activity, "No Match found", Toast.LENGTH_LONG).show()
            }
            return false
        }

        override fun onQueryTextChange(query: String?): Boolean {
            getMealDataFromDbByQuery(query)
            return false
        }
    }

    val onFABClicked = View.OnClickListener {
        val intent = Intent(activity, AddActivity::class.java)
        intent.putExtra("cat",cur_category)
        activity.startActivity(intent)
    }
}
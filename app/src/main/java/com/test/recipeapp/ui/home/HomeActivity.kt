package com.test.recipeapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import com.test.recipeapp.R
import com.test.recipeapp.ui.BaseActivity
import com.test.recipeapp.ui.detail.DetailActivity
import com.test.recipeapp.uiadapter.MainCategoryAdapter
import com.test.recipeapp.uiadapter.SubCategoryAdapter
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity: BaseActivity() {

    // Adapter
    var mainCategoryAdapter = MainCategoryAdapter()
    var subCategoryAdapter = SubCategoryAdapter()

    // Controller Class
    var controller = HomeController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        controller.getDataFromDb()

        mainCategoryAdapter.setClickListener(onClicked)
        subCategoryAdapter.setClickListener(onClickedSubItem)

        search_view.setOnQueryTextListener(searchByQuery)
    }

    private val onClicked  = object : MainCategoryAdapter.OnItemClickListener{
        override fun onClicked(categoryName: String) {
            controller.getMealDataFromDb(categoryName)
        }
    }

    private val onClickedSubItem  = object : SubCategoryAdapter.OnItemClickListener{
        override fun onClicked(id: String) {
            val intent = Intent(this@HomeActivity, DetailActivity::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }
    }

    private val searchByQuery = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            if (controller.getCountMealDataFromDb(query)!! > 0) {
                controller.getMealDataFromDbByQuery(query)
            } else {
                Toast.makeText(this@HomeActivity, "No Match found", Toast.LENGTH_LONG).show()
            }
            return false
        }

        override fun onQueryTextChange(query: String?): Boolean {
            controller.getMealDataFromDbByQuery(query)
            return false
        }
    }
}
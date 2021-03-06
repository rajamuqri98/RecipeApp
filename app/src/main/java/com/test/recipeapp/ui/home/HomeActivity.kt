package com.test.recipeapp.ui.home

import android.os.Bundle
import com.test.recipeapp.R
import com.test.recipeapp.ui.BaseActivity
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

        mainCategoryAdapter.setClickListener(controller.onClicked)
        subCategoryAdapter.setClickListener(controller.onClickedSubItem)

        search_view.setOnQueryTextListener(controller.searchByQuery)
        home_fab.setOnClickListener(controller.onFABClicked)
    }
}
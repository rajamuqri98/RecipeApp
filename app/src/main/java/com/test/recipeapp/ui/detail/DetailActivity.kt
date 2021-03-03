package com.test.recipeapp.ui.detail

import android.os.Bundle
import com.test.recipeapp.R
import com.test.recipeapp.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : BaseActivity() {

    val controller = DetailController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id = intent.getStringExtra("id")

        if (!id?.equals("0")!!) {
            controller.getSpecificItem(id)
        }

        imgToolbarBtnBack.setOnClickListener {
            finish()
        }
    }
}
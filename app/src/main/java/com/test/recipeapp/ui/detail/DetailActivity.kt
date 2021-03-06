package com.test.recipeapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.test.recipeapp.R
import com.test.recipeapp.database.RecipeDatabase
import com.test.recipeapp.ui.BaseActivity
import com.test.recipeapp.ui.addrecipe.AddActivity
import com.test.recipeapp.ui.home.HomeActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.*
import kotlin.properties.Delegates

class DetailActivity : BaseActivity() {

    private val controller = DetailController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id = intent.getStringExtra("id")
        val preload = intent.getBooleanExtra("preload", true)

        id?.let { controller.getSpecificItem(it, preload) }

        imgToolbarBtnEdit.setOnClickListener {
            if (preload) {
                Toast.makeText(this@DetailActivity, "Preloaded Recipe cannot be edited.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val intent = Intent(this@DetailActivity, AddActivity::class.java)
                intent.putExtra("id",id)
                intent.putExtra("cat",controller.category)
                startActivity(intent)
            }
        }

        imgToolbarBtnBack.setOnClickListener {
            finish()
        }
    }
}
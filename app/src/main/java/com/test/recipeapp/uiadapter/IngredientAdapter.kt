package com.test.recipeapp.uiadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.test.recipeapp.R
import com.test.recipeapp.ui.addrecipe.Ingredients
import kotlinx.android.synthetic.main.item_rv_ingredients.view.*

class IngredientAdapter(dataSet: ArrayList<Ingredients>) : RecyclerView.Adapter<IngredientAdapter.ViewHolder>(){

    var arrIngredients = dataSet

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_ingredients, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.etIngredients.setText(arrIngredients[position].name)
        holder.itemView.etAmount.setText(arrIngredients[position].measurement)

        holder.itemView.etIngredients.addTextChangedListener {
            arrIngredients[position].name = it.toString()
        }

        holder.itemView.etAmount.addTextChangedListener {
            arrIngredients[position].measurement = it.toString()
        }

        holder.itemView.removeBtn.setOnClickListener {
            arrIngredients.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = arrIngredients.size

    fun getIngredientName(): String {
        var name = ""
        for (arrIngredient in arrIngredients) {
            name += arrIngredient.name + ","
        }
        return name
    }

    fun getIngredientMeasurement(): String {
        var measurements = ""
        for (arrIngredient in arrIngredients) {
            measurements += arrIngredient.measurement + ","
        }
        return measurements
    }

}
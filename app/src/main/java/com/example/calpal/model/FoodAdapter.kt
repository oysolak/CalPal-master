package com.example.calpal.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calpal.R

/*
class FoodAdapter (
    private val foodList : MutableList<Food>,
    private val onItemClick: (Food) -> Unit
) : ListAdapter<Food, FoodAdapter.FoodViewHolder>(FoodDiffCallback()) {

    inner class FoodViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val foodNameText: TextView= itemView.findViewById(R.id.enterFoodName)

        fun bind(food: Food) {
            foodNameText.text = food.foodName
            itemView.setOnClickListener { onItemClick(food) }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_recycler_view, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foodList[position])
    }

    override fun getItemCount(): Int = foodList.size

    class FoodDiffCallback : DiffUtil.ItemCallback<Food>() {
        override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.foodName == newItem.foodName
        }

        override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem == newItem
        }
    }
}*/

class FoodAdapter : ListAdapter<Food, FoodAdapter.FoodViewHolder>(DiffCallback()) {

    inner class FoodViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val foodNameText: TextView = view.findViewById(R.id.foodItemName)
        private val foodCalorieText: TextView = view.findViewById(R.id.foodItemCalorie)

        fun bind(food: Food) {
            foodNameText.text = food.foodName
            foodCalorieText.text = "${food.foodCalorie} kcal"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_item, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Food>() {
        override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean = oldItem.foodName == newItem.foodName
        override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean = oldItem == newItem
    }
}


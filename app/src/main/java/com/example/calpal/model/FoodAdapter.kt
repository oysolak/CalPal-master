package com.example.calpal.model

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calpal.R
import com.google.android.material.card.MaterialCardView

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

class FoodAdapter(private val listener: OnItemClickListener) : ListAdapter<Food, FoodAdapter.FoodViewHolder>(DiffCallback()) {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    inner class FoodViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val foodNameText: TextView = view.findViewById(R.id.foodItemName)
        private val foodCalorieText: TextView = view.findViewById(R.id.foodItemCalorie)
        private val cardView: MaterialCardView = view.findViewById(R.id.foodCardView)

        @RequiresApi(Build.VERSION_CODES.M)
        fun bind(food: Food, isSelected: Boolean) {
            foodNameText.text = food.foodName
            foodCalorieText.text = "${food.foodCalorie} kcal"

            cardView.setCardBackgroundColor(
                if (isSelected) {
                    view.context.getColor(R.color.purple_200)
                } else {
                    view.context.getColor(R.color.white)
                }
            )

            cardView.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)
                listener.onItemClick(food)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_item, parent, false)
        return FoodViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(getItem(position), position == selectedPosition)
    }

    class DiffCallback : DiffUtil.ItemCallback<Food>() {
        override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean = oldItem.foodName == newItem.foodName
        override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean = oldItem == newItem
    }

    interface OnItemClickListener {
        fun onItemClick(food: Food)
    }
}


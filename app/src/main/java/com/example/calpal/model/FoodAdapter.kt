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
import com.example.calpal.databinding.FoodItemBinding
import com.google.android.material.card.MaterialCardView

class FoodAdapter(private val listener: OnItemClickListener) : ListAdapter<Food, FoodAdapter.FoodViewHolder>(DiffCallback()) {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    inner class FoodViewHolder(private val binding: FoodItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.M)
        fun bind(food: Food, isSelected: Boolean) {
            binding.foodItemName.text = food.foodName
            binding.foodItemCalorie.text = "${food.foodCalorie} kcal"

            binding.root.setBackgroundColor(
                if (isSelected) {
                    binding.root.context.getColor(R.color.purple_200)
                } else {
                    binding.root.context.getColor(R.color.white)
                }
            )

            binding.root.setOnClickListener {
                listener.onItemClick(food)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = FoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
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


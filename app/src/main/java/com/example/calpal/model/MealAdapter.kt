package com.example.calpal.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calpal.R

class MealAdapter(private val mListener: onItemClickListener) :
    ListAdapter<Meal, MealAdapter.MealViewHolder>(MealDiffCallback()){

    interface onItemClickListener{
            fun onItemClick(position : Int)
    }

    class MealViewHolder(itemView: View, private val listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.meal_image)
        val textView: TextView = itemView.findViewById(R.id.meal_name)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.meal, parent, false)
        return MealViewHolder(itemView, mListener);
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = getItem(position)
        holder.imageView.setImageResource(meal.mealImage)
        holder.textView.text = meal.mealName
    }

    class MealDiffCallback : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.mealName == newItem.mealName // Assuming name is unique
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }
}
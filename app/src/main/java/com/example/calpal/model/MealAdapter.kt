package com.example.calpal.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calpal.R
import com.google.android.material.imageview.ShapeableImageView

class MealAdapter(
        private val mealList: List<String>,
        private val mListener: onItemClickListener
    ) : ListAdapter<Meal, MealAdapter.MealViewHolder>(MealDiffCallback()) {


    interface onItemClickListener{
            fun onItemClick(position : Int)
    }

    inner class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mealImage: ShapeableImageView = itemView.findViewById(R.id.meal_image)
        val mealName: TextView = itemView.findViewById(R.id.meal_name)

        fun bind(meal: String) {
            mealName.text = meal
            when (meal) {
                "Breakfast" -> mealImage.setImageResource(R.drawable.breakfast)
                "Lunch" -> mealImage.setImageResource(R.drawable.lunch)
                "Dinner" -> mealImage.setImageResource(R.drawable.dinner)
                "Snack" -> mealImage.setImageResource(R.drawable.snack)
            }
            itemView.setOnClickListener {
                mListener.onItemClick(adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.meal, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(mealList[position])
    }

    override fun getItemCount(): Int {
        return mealList.size
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
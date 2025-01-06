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

/*class MealAdapter(private val mListener: onItemClickListener) :
    ListAdapter<Meal, MealAdapter.MealViewHolder>(MealDiffCallback()){*/

class MealAdapter(
        private val mealList: List<String>,
        private val mListener: onItemClickListener
    ) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {


    interface onItemClickListener{
            fun onItemClick(position : Int)
    }

 /*   class MealViewHolder(itemView: View, private val listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.meal_image)
        val textView: TextView = itemView.findViewById(R.id.meal_name)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }*/
    inner class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mealImage: ShapeableImageView = itemView.findViewById(R.id.meal_image)
        val mealName: TextView = itemView.findViewById(R.id.meal_name)

        fun bind(meal: String) {
            // Set data for meal item
            mealName.text = meal
            // Set meal image based on name
            when (meal) {
                "Breakfast" -> mealImage.setImageResource(R.drawable.breakfast)  // Example image resource
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

    /*override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.meal, parent, false)
        return MealViewHolder(itemView, mListener);
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = getItem(position)
        holder.mealImage.setImageResource(meal.mealImage)
        holder.mealName.text = meal.mealName
    }*/

    class MealDiffCallback : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.mealName == newItem.mealName // Assuming name is unique
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }
}
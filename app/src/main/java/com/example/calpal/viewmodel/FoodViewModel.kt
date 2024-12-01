package com.example.calpal.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calpal.model.Food
import com.example.calpal.model.Meal

class FoodViewModel : ViewModel() {
    val foodsList: MutableLiveData<MutableList<Food>> = MutableLiveData(mutableListOf())

    fun addFood(food : Food){
        val currentList = foodsList.value ?: mutableListOf()
        currentList.add(food)
        foodsList.value = currentList
    }

    fun getFoods() : MutableLiveData<MutableList<Food>> {
        return foodsList
    }
}
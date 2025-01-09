package com.example.calpal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FoodViewModel : ViewModel() {

    private val _breakfastCalories = MutableLiveData(0)
    val breakfastCalories: LiveData<Int> = _breakfastCalories

    private val _lunchCalories = MutableLiveData(0)
    val lunchCalories: LiveData<Int> = _lunchCalories

    private val _dinnerCalories = MutableLiveData(0)
    val dinnerCalories: LiveData<Int> = _dinnerCalories

    private val _snackCalories = MutableLiveData(0)
    val snackCalories: LiveData<Int> = _snackCalories

    private val _currentCalories = MutableLiveData(0)
    val currentCalories: LiveData<Int> = _snackCalories

    private val _targetCalories = MutableLiveData<Int>(0) // New live data for target calories
    val targetCalories: LiveData<Int> = _targetCalories

    fun addCaloriesToMeal(mealType: String, calories: Int) {
        when (mealType) {
            "Breakfast" -> _breakfastCalories.value = (_breakfastCalories.value ?: 0) + calories
            "Lunch" -> _lunchCalories.value = (_lunchCalories.value ?: 0) + calories
            "Dinner" -> _dinnerCalories.value = (_dinnerCalories.value ?: 0) + calories
            "Snack" -> _snackCalories.value = (_snackCalories.value ?: 0) + calories
        }
    }

    fun setTargetCalories(calories: Int) {
        _targetCalories.value = calories
    }
}

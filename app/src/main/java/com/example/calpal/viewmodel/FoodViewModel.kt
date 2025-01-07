package com.example.calpal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FoodViewModel : ViewModel() {
    private var _foodName = MutableLiveData<String>();
    private var _foodCalorie = MutableLiveData<Int>();

    fun setFoodName(newFoodName: String){
        _foodName.value = newFoodName;
    }

    fun setFoodCalorie(newFoodCalorie: Int){
        _foodCalorie.value = newFoodCalorie;
    }

    val foodName : LiveData<String>
        get() = _foodName;

    val foodCalorie : LiveData<Int>
        get() = _foodCalorie;







}
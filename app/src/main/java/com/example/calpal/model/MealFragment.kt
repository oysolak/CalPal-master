package com.example.calpal.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.calpal.R
import com.example.calpal.viewmodel.FoodViewModel
import com.example.calpal.databinding.FragmentMealBinding

class MealFragment : Fragment(R.layout.fragment_meal) {

    private lateinit var binding : FragmentMealBinding

    private val viewModel: FoodViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_meal, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        val bundle = arguments
        if(bundle != null && bundle.containsKey("clickedMeal")){
            val clickedMeal : Meal? = bundle.getParcelable("clickedMeal")
            clickedMeal?.let{
                binding.mealName.text = it.mealName
                binding.mealImage.setImageResource(it.mealImage)

            }
        }

    }

}
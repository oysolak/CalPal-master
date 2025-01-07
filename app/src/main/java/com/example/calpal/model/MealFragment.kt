package com.example.calpal.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.calpal.R
import com.example.calpal.databinding.FragmentMealBinding
import com.example.calpal.viewmodel.FoodViewModel

class MealFragment : Fragment(R.layout.fragment_meal) {

    private lateinit var binding: FragmentMealBinding
    private val viewModel: FoodViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val bundle = arguments
        if (bundle != null) {
            val mealName = bundle.getString("mealName")
            val mealImageResId = bundle.getInt("mealImage")

            binding.mealName.text = mealName
            binding.mealImage.setImageResource(mealImageResId)
        }
    }
}

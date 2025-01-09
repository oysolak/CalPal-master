package com.example.calpal.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.calpal.databinding.FragmentStatisticspopupBinding
import com.example.calpal.viewmodel.FoodViewModel
import androidx.fragment.app.DialogFragment

class StatisticsPopupFragment : DialogFragment() {

    private var _binding: FragmentStatisticspopupBinding? = null
    private val binding get() = _binding!!
    private val foodViewModel: FoodViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticspopupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        foodViewModel.breakfastCalories.observe(viewLifecycleOwner) { calories ->
            binding.BreakfastCalorieDisplay.text = "$calories kcal"
        }

        foodViewModel.lunchCalories.observe(viewLifecycleOwner) { calories ->
            binding.LunchCalorieDisplay.text = "$calories kcal"
        }

        foodViewModel.dinnerCalories.observe(viewLifecycleOwner) { calories ->
            binding.DinnerCalorieDisplay.text = "$calories kcal"
        }

        foodViewModel.snackCalories.observe(viewLifecycleOwner) { calories ->
            binding.SnackCalorieDisplay.text = "$calories kcal"
        }

        foodViewModel.targetCalories.observe(viewLifecycleOwner) { targetCalories ->
            binding.targetCalorieDisplay.text = "$targetCalories kcal"
        }

        foodViewModel.breakfastCalories.observe(viewLifecycleOwner) { updateTotalCalories() }
        foodViewModel.lunchCalories.observe(viewLifecycleOwner) { updateTotalCalories() }
        foodViewModel.dinnerCalories.observe(viewLifecycleOwner) { updateTotalCalories() }
        foodViewModel.snackCalories.observe(viewLifecycleOwner) { updateTotalCalories() }

        binding.closeButton.setOnClickListener {
            dismiss()
        }

        binding.setTargetCalorieButton.setOnClickListener {
            val targetCalories = binding.enterTargetCalorie.text.toString()
            if (targetCalories.isNotEmpty()) {
                foodViewModel.setTargetCalories(targetCalories.toInt()) // Set target calories in ViewModel
                binding.enterTargetCalorie.text.clear() // Clear the EditText
            }
        }
    }

    private fun updateTotalCalories() {
        val totalCalories = (foodViewModel.breakfastCalories.value ?: 0) +
                (foodViewModel.lunchCalories.value ?: 0) +
                (foodViewModel.dinnerCalories.value ?: 0) +
                (foodViewModel.snackCalories.value ?: 0)

        binding.currentCalorieDisplay.text = "$totalCalories kcal"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

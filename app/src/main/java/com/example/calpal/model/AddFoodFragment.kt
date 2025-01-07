package com.example.calpal.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.calpal.R
import com.example.calpal.databinding.FragmentAddFoodBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

data class Food(val foodName : String, val foodCalorie : Int)

class AddFoodFragment : Fragment(R.layout.fragment_add_food) {

    private lateinit var binding: FragmentAddFoodBinding
    private lateinit var dbRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_food, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddFoodBinding.bind(view)
        dbRef = FirebaseDatabase.getInstance().getReference("Foods")

        binding.addFoodToDbButton.setOnClickListener {
            if (validate()) {
                saveFoodData()
            } else {
                Toast.makeText(requireContext(), "Please fill all fields!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validate(): Boolean {
        return binding.enterFoodName.text.isNotEmpty() && binding.enterFoodCalorie.text.isNotEmpty()
    }

    private fun saveFoodData() {
        val foodName = binding.enterFoodName.text.toString().trim()
        val foodCalorie = binding.enterFoodCalorie.text.toString().trim()

        if (foodName.isEmpty() || foodCalorie.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields!", Toast.LENGTH_SHORT).show()
            return
        }

        val foodCalorieValue = foodCalorie.toInt()

        dbRef.child(foodName).get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                Toast.makeText(requireContext(), "Food name already exists. Please use a different name.", Toast.LENGTH_SHORT).show()
            } else {
                val food = Food(foodName, foodCalorieValue)

                dbRef.child(foodName).setValue(food).addOnCompleteListener {
                    Toast.makeText(requireContext(), "Food added successfully!", Toast.LENGTH_SHORT).show()
                    binding.enterFoodName.text.clear()
                    binding.enterFoodCalorie.text.clear()
                }.addOnFailureListener {
                    Toast.makeText(requireContext(), "Error adding food. Try again.", Toast.LENGTH_SHORT).show()
                }
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Error checking food name. Try again.", Toast.LENGTH_SHORT).show()
        }
    }
}


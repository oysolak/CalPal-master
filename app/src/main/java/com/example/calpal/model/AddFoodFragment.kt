package com.example.calpal.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calpal.R
import com.example.calpal.databinding.FragmentAddFoodBinding
import com.example.calpal.model.FoodAdapter.OnItemClickListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class AddFoodFragment : Fragment(R.layout.fragment_add_food), OnItemClickListener {

    private lateinit var binding: FragmentAddFoodBinding
    private lateinit var dbRef: DatabaseReference
    private var selectedFood: Food? = null

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

        val foodAdapter = FoodAdapter(this)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = foodAdapter
        }

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val foodList = mutableListOf<Food>()
                for (data in snapshot.children) {
                    val food = data.getValue(Food::class.java)
                    food?.let { foodList.add(it) }
                }
                foodAdapter.submitList(foodList)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to load data", Toast.LENGTH_SHORT).show()
            }
        })

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

    override fun onItemClick(food: Food) {
        selectedFood = food
    }
}


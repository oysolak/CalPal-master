package com.example.calpal.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calpal.R
import com.example.calpal.databinding.FragmentMealBinding
import com.example.calpal.model.FoodAdapter.OnItemClickListener
import com.example.calpal.viewmodel.FoodViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MealFragment : Fragment(R.layout.fragment_meal), OnItemClickListener {

    private val foodViewModel: FoodViewModel by activityViewModels()
    private lateinit var binding: FragmentMealBinding
    private val viewModel: FoodViewModel by viewModels()
    private lateinit var foodList: List<Food>
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var dbRef: DatabaseReference
    private var selectedFood: Food? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMealBinding.inflate(inflater, container, false)
        return binding.root
    }

/*    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
    }*/
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    dbRef = FirebaseDatabase.getInstance().getReference("Foods")

    binding.recyclerView.layoutManager = LinearLayoutManager(context)
    foodList = mutableListOf()
    foodAdapter = FoodAdapter(this)
    binding.recyclerView.adapter = foodAdapter

    fetchDataFromDatabase()

    val bundle = arguments
    if (bundle != null) {
        val mealName = bundle.getString("mealName")
        val mealImageResId = bundle.getInt("mealImage")

        binding.mealName.text = mealName
        binding.mealImage.setImageResource(mealImageResId)
    }
}

    private fun fetchDataFromDatabase() {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newFoodList = mutableListOf<Food>()
                for (dataSnapshot in snapshot.children) {
                    val foodName = dataSnapshot.child("foodName").getValue(String::class.java) ?: ""
                    val foodCalorie =
                        dataSnapshot.child("foodCalorie").getValue(Int::class.java) ?: 0
                    val food = Food(foodName, foodCalorie)

                    newFoodList.add(food)
                }
                foodAdapter.submitList(newFoodList)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Error fetching data", Toast.LENGTH_SHORT).show()
            }
        })

        binding.selectFood.setOnClickListener {
            selectedFood?.let {
                foodViewModel.selectedCalories.value = it.foodCalorie
                Toast.makeText(requireContext(), "Calories set to ${it.foodCalorie}", Toast.LENGTH_SHORT).show()
            } ?: Toast.makeText(requireContext(), "No food selected!", Toast.LENGTH_SHORT).show()
        }
    }

        override fun onItemClick(food: Food) {
        selectedFood = food
        Toast.makeText(requireContext(), "Selected: ${food.foodName}", Toast.LENGTH_SHORT).show()
    }
}

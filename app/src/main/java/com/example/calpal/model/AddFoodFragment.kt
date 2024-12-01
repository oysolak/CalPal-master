package com.example.calpal.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.calpal.R
import com.example.calpal.databinding.FragmentAddFoodBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddFoodFragment : Fragment(R.layout.fragment_add_food){

    private lateinit var binding: FragmentAddFoodBinding
    private lateinit var dbRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_food, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddFoodBinding.bind(view)
        dbRef = FirebaseDatabase.getInstance().getReference("Food")

        binding.addFoodButton.setOnClickListener{
            if(validate()){
                saveFoodData()
                view.findNavController().navigate(R.id.action_addFoodFragment_to_recyclerViewFragment)
            }else{
                Toast.makeText(requireContext(), "Please fill all fields!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validate() : Boolean{
        if(binding.foodName.text.isEmpty()){
            return false
        }
        if(binding.foodCalorie.text.isEmpty()){
            return false
        }
        return true
    }

    private fun saveFoodData(){
        val FoodName = binding.foodName.text.toString()
        val FoodCalorie = binding.foodCalorie.text.toString().toInt()
        val FoodId = dbRef.push().key!!

        val food = Food(
            FoodName,
            FoodCalorie,
        )

        dbRef.child(FoodId).setValue(food)
            .addOnCompleteListener{
                Toast.makeText(requireContext(), "Food added successfully", Toast.LENGTH_LONG).show()
        }   .addOnFailureListener{
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
        }
    }
}
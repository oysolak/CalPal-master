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

class AddFoodFragment : Fragment(R.layout.fragment_add_food) {

    private lateinit var dbRef: DatabaseReference
    private lateinit var binding: FragmentAddFoodBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddFoodBinding.bind(view)
        dbRef = FirebaseDatabase.getInstance().getReference("Foods")

        binding.addFoodButton.setOnClickListener {
            if (validate()) {
                saveFoodData()
                val recyclerViewFragment = RecyclerViewFragment()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, recyclerViewFragment)
                    .addToBackStack(null)
                    .commit()
            } else {
                Toast.makeText(requireContext(), "Please fill all fields!", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun validate(): Boolean {
        return binding.foodName.text.isNotEmpty() && binding.foodCalorie.text.isNotEmpty()
    }

    private fun saveFoodData() {
        val foodName = binding.enterFoodName.text.toString()
        val foodCalorie = binding.enterFoodCalorie.text.toString().toInt()

        val foodId = dbRef.push().key!!
        val food = Food(foodName, foodCalorie)

        dbRef.child(foodId).setValue(food).addOnCompleteListener {
            Toast.makeText(requireContext(), "Food added successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Error adding food", Toast.LENGTH_SHORT).show()
        }
    }
}

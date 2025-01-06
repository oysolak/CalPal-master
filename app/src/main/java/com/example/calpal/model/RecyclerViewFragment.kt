package com.example.calpal.model

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calpal.R
import com.example.calpal.databinding.FragmentRecyclerViewBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/*
class RecyclerViewFragment : Fragment(R.layout.fragment_recycler_view) {

    private lateinit var binding: FragmentRecyclerViewBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var mealList: ArrayList<Meal>
    private lateinit var mealAdapter: MealAdapter

    private lateinit var dbRef: DatabaseReference


override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {
    // Inflate the layout for this fragment
    val view = inflater.inflate(R.layout.fragment_recycler_view, container, false)

    // Initialize the RecyclerView and MealAdapter
    mealList = arrayListOf()
    val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
    recyclerView.layoutManager = LinearLayoutManager(requireContext())
    mealAdapter = MealAdapter(object : MealAdapter.onItemClickListener {
        override fun onItemClick(position: Int) {
            // Handle item clicks
        }
    })
    recyclerView.adapter = mealAdapter

    // Fetch meals from Firebase
    getMeals()

    return view
}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecyclerViewBinding.bind(view)


        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mealList = ArrayList()

        mealAdapter = MealAdapter(object : MealAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val clickedMeal: Meal = mealList[position]

                val bundle = Bundle()
                bundle.putParcelable("clickedFriend", clickedMeal)
                bundle.putInt("clickedPosition", position)

                val friendFragment = MealFragment()
                friendFragment.arguments = bundle

                view.findNavController()
                    .navigate(R.id.action_recyclerViewFragment_to_meal, bundle)
            }
        })

        recyclerView.adapter = mealAdapter

        getMeals()

        mealAdapter.submitList(mealList)

        binding.addFoodButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_recyclerViewFragment_to_addFoodFragment)
        }
    }

    private fun getMeals() {

        dbRef = FirebaseDatabase.getInstance().getReference("Meals")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mealList.clear()
                if (snapshot.exists()) {
                    for (friendSnap in snapshot.children) {
                        val image = friendSnap.child("image").getValue(Int::class.java) ?: 0
                        val name = friendSnap.child("name").getValue(String::class.java) ?: ""

                        val friendData = Meal(image, name)
                        mealList.add(friendData)
                    }
                    mealAdapter.submitList(mealList) // Explicitly convert to ArrayList<Meal>

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }



}*/

class RecyclerViewFragment : Fragment(R.layout.fragment_recycler_view) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mealAdapter: MealAdapter
    private lateinit var mealList: List<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Define the list of meals
        mealList = listOf("Breakfast", "Lunch", "Dinner", "Snack")

        mealAdapter = MealAdapter(mealList, object : MealAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val selectedMeal = mealList[position]
                val bundle = Bundle()
                bundle.putString("selectedMeal", selectedMeal)
                view.findNavController().navigate(R.id.action_recyclerViewFragment_to_addFoodFragment, bundle)
            }
        })

        recyclerView.adapter = mealAdapter
    }
}


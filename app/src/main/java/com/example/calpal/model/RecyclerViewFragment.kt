package com.example.calpal.model

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calpal.R
import com.example.calpal.databinding.FragmentRecyclerViewBinding
import com.example.calpal.viewmodel.FoodViewModel

class RecyclerViewFragment : Fragment(R.layout.fragment_recycler_view) {

    private val foodViewModel: FoodViewModel by activityViewModels()
    private lateinit var binding: FragmentRecyclerViewBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var mealAdapter: MealAdapter
    private lateinit var mealList: List<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mealList = listOf("Breakfast", "Lunch", "Dinner", "Snack")

        mealAdapter = MealAdapter(mealList, object : MealAdapter.onItemClickListener {

            override fun onItemClick(position: Int) {
                val selectedMeal = mealList[position]
                val mealImageResId = when (selectedMeal) {
                    "Breakfast" -> R.drawable.breakfast
                    "Lunch" -> R.drawable.lunch
                    "Dinner" -> R.drawable.dinner
                    "Snack" -> R.drawable.snack
                    else -> R.drawable.breakfast
                }

                val bundle = Bundle()
                bundle.putString("mealName", selectedMeal)
                bundle.putInt("mealImage", mealImageResId)

                val mealFragment = MealFragment()
                mealFragment.arguments = bundle

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, mealFragment)
                    .addToBackStack(null)
                    .commit()

            }
        })

        recyclerView.adapter = mealAdapter

        binding.statisticsButton.setOnClickListener {
            StatisticsPopupFragment().show(parentFragmentManager, "PopupFragment")
        }

        val addFoodFragment = AddFoodFragment()
        view.findViewById<Button>(R.id.addFoodButton).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, addFoodFragment)
                .addToBackStack(null)
                .commit()
        }

    }
}



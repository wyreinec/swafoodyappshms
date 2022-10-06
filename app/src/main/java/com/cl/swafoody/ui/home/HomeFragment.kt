package com.cl.swafoody.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cl.swafoody.databinding.FragmentHomeBinding
import com.cl.swafoody.ui.ingredients.IngredientsActivity
import com.cl.swafoody.ui.recipe.RecipeAdapter
import com.cl.swafoody.ui.recipe.RecipeViewModel
import com.cl.swafoody.viewmodel.ViewModelFactory
import com.cl.swafoody.vo.Status

class HomeFragment : Fragment() {
    private var fragmentHomeBinding: FragmentHomeBinding? = null
    private val binding get() = fragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[RecipeViewModel::class.java]

            val recipeAdapter = RecipeAdapter()

            viewModel.getRecipeByIngredients("apples").observe(viewLifecycleOwner) { recipes ->
                if (recipes != null) {
                    when (recipes.status) {
                        Status.LOADING -> binding?.progressBar?.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            binding?.progressBar?.visibility = View.GONE
                            recipes.message?.let { Log.d("RESEP", it) }
                            recipeAdapter.setRecipes(recipes.data)
                        }
                        Status.ERROR -> {
                            binding?.progressBar?.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            binding?.btnAddIngredients?.setOnClickListener {
                val intent = Intent(context, IngredientsActivity::class.java)
                startActivity(intent)
            }

            binding?.tvViewAll?.setOnClickListener {
            }

            with(binding?.rvRecipe) {
                this?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                this?.setHasFixedSize(true)
                this?.adapter = recipeAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentHomeBinding = null
    }
}
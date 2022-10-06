package com.cl.swafoody.ui.recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cl.swafoody.databinding.FragmentRecipeBinding
import com.cl.swafoody.viewmodel.ViewModelFactory
import com.cl.swafoody.vo.Status

class RecipeFragment : Fragment() {
    private var fragmentRecipeBinding: FragmentRecipeBinding? = null
    private val binding get() = fragmentRecipeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentRecipeBinding = FragmentRecipeBinding.inflate(layoutInflater, container, false)
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
                            recipeAdapter.setRecipes(recipes.data)
                        }
                        Status.ERROR -> {
                            binding?.progressBar?.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            with(binding?.rvRecipe) {
                this?.layoutManager = LinearLayoutManager(context)
                this?.setHasFixedSize(true)
                this?.adapter = recipeAdapter
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentRecipeBinding = null
    }
}
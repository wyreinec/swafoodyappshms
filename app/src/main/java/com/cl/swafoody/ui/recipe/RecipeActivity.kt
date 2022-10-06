package com.cl.swafoody.ui.recipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cl.swafoody.R
import com.cl.swafoody.databinding.ActivityMainBinding
import com.cl.swafoody.databinding.ActivityRecipeBinding
import com.cl.swafoody.viewmodel.ViewModelFactory
import com.cl.swafoody.vo.Status

class RecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[RecipeViewModel::class.java]

        val recipeAdapter = RecipeAdapter()

        viewModel.getRecipeByIngredients("apple").observe(this) { recipe ->
            if (recipe != null) {
                when (recipe.status) {
                    Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        recipeAdapter.setRecipes(recipe.data)
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        with(binding.rvRecipe) {
            this.layoutManager = GridLayoutManager(this.context, 2)
            this.setHasFixedSize(true)
            this.adapter = recipeAdapter
        }
    }
}
package com.cl.swafoody.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.cl.swafoody.R
import com.cl.swafoody.data.source.local.entity.RecipeEntity
import com.cl.swafoody.databinding.ActivityDetailRecipeBinding
import com.cl.swafoody.databinding.ActivityRecipeBinding
import com.cl.swafoody.ui.recipe.RecipeViewModel
import com.cl.swafoody.viewmodel.ViewModelFactory
import com.cl.swafoody.vo.Status

class DetailRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[DetailRecipeViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            viewModel.getRecipeInformation(extras.getInt(EXTRA_RECIPE)).observe(this) { recipe ->
                if (recipe != null) {
                    when (recipe.status) {
                        Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            for (response in recipe.data!!) {
                                binding.tvTitle.text = response.name
                            }
                            recipe.data
                        }
                        Status.ERROR -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val EXTRA_RECIPE = "extra_recipe"
    }
}

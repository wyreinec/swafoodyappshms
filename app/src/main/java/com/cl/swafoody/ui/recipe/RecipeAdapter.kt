package com.cl.swafoody.ui.recipe

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.cl.swafoody.R
import com.cl.swafoody.data.source.local.entity.RecipeEntity
import com.cl.swafoody.databinding.ItemBinding
import com.cl.swafoody.ui.detail.DetailRecipeActivity

class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
    private var listRecipes = ArrayList<RecipeEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun setRecipes(recipes: List<RecipeEntity>?) {
        if (recipes == null) return
        this.listRecipes.clear()
        this.listRecipes.addAll(recipes)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemsRecipeBinding =
            ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(itemsRecipeBinding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val items = listRecipes[position]
        holder.bind(items)
    }

    override fun getItemCount(): Int {
        return listRecipes.size
    }

    class RecipeViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: RecipeEntity) {
            with(binding) {
                tvTitle.text = recipe.name
                Glide.with(itemView.context)
                    .load("https://spoonacular.com/recipeImages/" + recipe.recipeId + "-312x231.jpg")
                    .transform(RoundedCorners(24))
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgPoster)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailRecipeActivity::class.java)
                    intent.putExtra(DetailRecipeActivity.EXTRA_RECIPE, recipe.recipeId)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}
package com.cl.swafoody.ui.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.cl.swafoody.data.source.Repository
import com.cl.swafoody.data.source.local.entity.RecipeEntity
import com.cl.swafoody.vo.Resource

class RecipeViewModel(private val repository: Repository) : ViewModel() {
    fun getRecipeByIngredients(ingredients: String): LiveData<Resource<List<RecipeEntity>>> =
        repository.getRecipeByIngredients(ingredients)
}
package com.cl.swafoody.data.source

import androidx.lifecycle.LiveData
import com.cl.swafoody.data.source.local.entity.RecipeEntity
import com.cl.swafoody.vo.Resource

interface DataSource {
    fun getRecipeByIngredients(ingredients: String): LiveData<Resource<List<RecipeEntity>>>
    fun getRecipeInformation(id: Int): LiveData<Resource<List<RecipeEntity>>>
}
package com.cl.swafoody.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.cl.swafoody.data.source.Repository
import com.cl.swafoody.data.source.local.entity.RecipeEntity
import com.cl.swafoody.vo.Resource

class DetailRecipeViewModel(private val repository: Repository) : ViewModel() {
    fun getRecipeInformation(id: Int): LiveData<Resource<List<RecipeEntity>>> =
        repository.getRecipeInformation(id)
}
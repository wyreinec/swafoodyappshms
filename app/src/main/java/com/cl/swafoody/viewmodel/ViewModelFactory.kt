package com.cl.swafoody.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cl.swafoody.data.source.Repository
import com.cl.swafoody.di.Injection
import com.cl.swafoody.ui.detail.DetailRecipeViewModel
import com.cl.swafoody.ui.recipe.RecipeViewModel

class ViewModelFactory private constructor(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RecipeViewModel::class.java) -> {
                RecipeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailRecipeViewModel::class.java) -> {
                DetailRecipeViewModel(repository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }
}
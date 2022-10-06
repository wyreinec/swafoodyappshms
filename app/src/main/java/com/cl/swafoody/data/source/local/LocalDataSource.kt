package com.cl.swafoody.data.source.local

import com.cl.swafoody.data.source.local.entity.RecipeEntity
import com.cl.swafoody.data.source.local.room.SwafoodyDao

class LocalDataSource private constructor(private val dao: SwafoodyDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(swafoodyDao: SwafoodyDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(swafoodyDao)
    }

    fun insertRecipe(recipe: List<RecipeEntity>) = dao.insertRecipe(recipe)
    fun getAllRecipe() = dao.getAllRecipe()
}
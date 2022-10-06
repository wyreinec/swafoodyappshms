package com.cl.swafoody.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.*
import androidx.room.OnConflictStrategy
import com.cl.swafoody.data.source.local.entity.RecipeEntity

@Dao
interface SwafoodyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRecipe(users: List<RecipeEntity>)

    @Query("SELECT * FROM recipe_entities")
    fun getAllRecipe(): LiveData<List<RecipeEntity>>
}
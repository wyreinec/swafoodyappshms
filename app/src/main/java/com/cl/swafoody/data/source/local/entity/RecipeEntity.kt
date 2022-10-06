package com.cl.swafoody.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_entities")
data class RecipeEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "recipeId")
    var recipeId: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "calories")
    var calories: String,
)

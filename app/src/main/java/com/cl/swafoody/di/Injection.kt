package com.cl.swafoody.di

import android.content.Context
import com.cl.swafoody.data.source.Repository
import com.cl.swafoody.data.source.local.LocalDataSource
import com.cl.swafoody.data.source.local.room.RecipeDatabase
import com.cl.swafoody.data.source.remote.RemoteDataSource
import com.cl.swafoody.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): Repository {

        val database = RecipeDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.recipeDao())
        val appExecutors = AppExecutors()

        return Repository.getInstance(localDataSource, remoteDataSource, appExecutors)
    }
}
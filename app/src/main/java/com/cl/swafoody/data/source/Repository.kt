package com.cl.swafoody.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import com.cl.swafoody.data.NetworkBoundResource
import com.cl.swafoody.data.source.local.LocalDataSource
import com.cl.swafoody.data.source.local.entity.RecipeEntity
import com.cl.swafoody.data.source.remote.ApiResponse
import com.cl.swafoody.data.source.remote.RemoteDataSource
import com.cl.swafoody.data.source.remote.response.RecipeInformationItem
import com.cl.swafoody.data.source.remote.response.RecipeItem
import com.cl.swafoody.utils.AppExecutors
import com.cl.swafoody.vo.Resource

class Repository private constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
) : DataSource {

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            localData: LocalDataSource,
            remotData: RemoteDataSource,
            appExecutors: AppExecutors
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(localData, remotData, appExecutors).apply {
                    instance = this
                }
            }
    }

    override fun getRecipeByIngredients(ingredients: String): LiveData<Resource<List<RecipeEntity>>> {
        return object : NetworkBoundResource<List<RecipeEntity>, List<RecipeItem>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<RecipeEntity>> =
                localDataSource.getAllRecipe()

            override fun shouldFetch(data: List<RecipeEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<RecipeItem>>> =
                remoteDataSource.getRecipeByIngredients(ingredients)

            override fun saveCallResult(data: List<RecipeItem>) {
                val recipeList = ArrayList<RecipeEntity>()
                for (response in data) {
                    val recipe = RecipeEntity(
                        response.id,
                        response.title,
                        "666"
                    )
                    recipeList.add(recipe)
                }
                Log.d("RESEP", data.toString())

                localDataSource.insertRecipe(recipeList)
            }
        }.asLiveData()
    }

    override fun getRecipeInformation(id: Int): LiveData<Resource<List<RecipeEntity>>> {
        return object : NetworkBoundResource<List<RecipeEntity>, List<RecipeInformationItem>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<RecipeEntity>> =
                localDataSource.getAllRecipe()

            override fun shouldFetch(data: List<RecipeEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<RecipeInformationItem>>> =
                remoteDataSource.getRecipeInformation(id)

            override fun saveCallResult(data: List<RecipeInformationItem>) {
                val recipeList = ArrayList<RecipeEntity>()
                for (response in data) {
                    val recipe = RecipeEntity(
                        response.id,
                        response.sourceName,
                        ""
                    )
                    recipeList.add(recipe)
                }
                Log.d("RESEP", data.toString())

                localDataSource.insertRecipe(recipeList)
            }
        }.asLiveData()
    }
}
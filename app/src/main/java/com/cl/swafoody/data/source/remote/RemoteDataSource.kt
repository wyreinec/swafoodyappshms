package com.cl.swafoody.data.source.remote

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cl.swafoody.data.source.remote.response.RecipeInformationItem
import com.cl.swafoody.data.source.remote.response.RecipeInformationResponse
import com.cl.swafoody.data.source.remote.response.RecipeResponse
import com.cl.swafoody.data.source.remote.response.RecipeItem
import com.cl.swafoody.network.ApiConfig
import retrofit2.Call
import retrofit2.Response

class RemoteDataSource {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource().apply { instance = this }
            }
    }

    fun getRecipeByIngredients(ingredients: String): LiveData<ApiResponse<List<RecipeItem>>> {
        val resultMovie = MutableLiveData<ApiResponse<List<RecipeItem>>>()
        val client = ApiConfig.getApiService().getRecipeByIngredients(ingredients)
        client.enqueue(object : retrofit2.Callback<RecipeResponse> {
            override fun onResponse(call: Call<RecipeResponse>, response: Response<RecipeResponse>) {
                if (response.isSuccessful) {
                    Log.d(this@RemoteDataSource.toString(), "Get Recipe Success")
                    resultMovie.value = response.body()?.let {
                        ApiResponse.success(it.results)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
        return resultMovie
    }

    fun getRecipeInformation(id: Int): LiveData<ApiResponse<List<RecipeInformationItem>>> {
        val result = MutableLiveData<ApiResponse<List<RecipeInformationItem>>>()
        val client = ApiConfig.getApiService().getRecipeInformation(id)
        client.enqueue(object : retrofit2.Callback<RecipeInformationResponse> {
            override fun onResponse(call: Call<RecipeInformationResponse>, response: Response<RecipeInformationResponse>) {
                if (response.isSuccessful) {
                    result.value =response.body()?.let {
                        Log.d(this@RemoteDataSource.toString(), "Get Recipe Information Success")
                        ApiResponse.success(it.results)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RecipeInformationResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
        return result
    }
}
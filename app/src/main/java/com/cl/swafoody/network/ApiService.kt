package com.cl.swafoody.network

import com.cl.swafoody.data.source.remote.response.RecipeInformationResponse
import com.cl.swafoody.data.source.remote.response.RecipeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("recipes/complexSearch?apiKey=d875e34740f5490691c153e31d93f1da")
    fun getRecipeByIngredients(
        @Query("includeIngredients") ingredients: String
    ): Call<RecipeResponse>

    @GET("recipes/{id}/information?apiKey=d875e34740f5490691c153e31d93f1da")
    fun getRecipeInformation(
        @Path("id") id: Int
    ): Call<RecipeInformationResponse>
}
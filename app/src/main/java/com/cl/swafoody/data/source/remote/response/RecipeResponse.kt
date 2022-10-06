package com.cl.swafoody.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RecipeResponse(
	@field:SerializedName("number")
	val number: Int? = null,

	@field:SerializedName("totalResults")
	val totalResults: Int? = null,

	@field:SerializedName("offset")
	val offset: Int? = null,

	@field:SerializedName("results")
	val results: List<RecipeItem>
)

data class RecipeItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("imageType")
	val imageType: String? = null
)

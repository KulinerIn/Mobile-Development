package com.example.kulinerin.food.model.pojo

import com.google.gson.annotations.SerializedName

data class IngredientItem(

	@field:SerializedName("amount")
	val amount: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)

data class Food(

    @field:SerializedName("image")
	val image: String,

    @field:SerializedName("alt_name")
	val altName: String,

    @field:SerializedName("ingredient")
	val ingredient: List<IngredientItem>,

    @field:SerializedName("origin")
	val origin: String,

    @field:SerializedName("name")
	val name: String,

    @field:SerializedName("recipe")
	val recipe: String,

    @field:SerializedName("description")
	val description: String,

    @field:SerializedName("id")
	val id: Int
)

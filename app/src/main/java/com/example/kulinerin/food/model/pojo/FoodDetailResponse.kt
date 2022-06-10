package com.example.kulinerin.food.model.pojo

import com.google.gson.annotations.SerializedName

data class FoodDetailResponse(

    @field:SerializedName("data")
    val data: Food,

    @field:SerializedName("error")
    val error: String
)

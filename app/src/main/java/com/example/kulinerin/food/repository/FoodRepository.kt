package com.example.kulinerin.food.repository

import com.example.kulinerin.api.ApiService
import com.example.kulinerin.food.database.FoodDatabase
import javax.inject.Inject

class FoodRepository @Inject constructor(private val foodDatabase: FoodDatabase, private val apiService: ApiService){

}
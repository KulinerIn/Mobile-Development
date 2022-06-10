package com.example.kulinerin.food.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kulinerin.api.ApiService
import com.example.kulinerin.food.model.pojo.Food
import com.example.kulinerin.food.model.pojo.FoodDetailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    private val _food = MutableLiveData<Food>()
    val food: LiveData<Food> = _food

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun getFoodDetail(foodId: Int) {
        val client = apiService.getFoodDetail(foodId)
        client.enqueue(
            object : Callback<FoodDetailResponse> {
                override fun onResponse(
                    call: Call<FoodDetailResponse>,
                    response: Response<FoodDetailResponse>
                ) {
                    val responseBody = response.body()
                    if (response.isSuccessful) {
                        if (responseBody != null) {
                            _food.value = responseBody.data
                        }
                    } else {
                        _message.value = response.message()
                    }
                }
                override fun onFailure(call: Call<FoodDetailResponse>, t: Throwable) {
                    _message.value = "Failed to create Retrofit instance"
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
    }

    companion object {
        private const val TAG = "FoodViewModel"
    }

}
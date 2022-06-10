package com.example.kulinerin.food.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kulinerin.R
import com.example.kulinerin.databinding.ActivityFoodDetailBinding
import com.example.kulinerin.food.model.pojo.Food
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodDetailBinding
    private val foodViewModel: FoodViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foodViewModel.food.observe(this) {
            setFoodData(it)
        }

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = "Loading..."
        foodViewModel.getFoodDetail(1)
    }

    private fun setFoodData(food: Food) {

        binding.toolbarLayout.title = food.name
        binding.content.textDescription?.text = food.description

        val ingredients = food.ingredient
        var ingredientText = ""
        for (ingredient in ingredients) {
            val text = ingredient.amount + " " + ingredient.name + "\n"
            ingredientText = ingredientText.plus(text)
        }

        Glide.with(binding.foodImage).load(food.image).into(binding.foodImage)
        binding.content.textIngredients?.text = ingredientText
        binding.content.textDirections?.text = food.recipe
    }

    companion object {
        private const val TAG = "FoodDetailActivity"
    }
}
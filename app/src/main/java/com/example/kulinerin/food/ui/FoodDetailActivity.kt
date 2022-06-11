package com.example.kulinerin.food.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kulinerin.R
import com.example.kulinerin.databinding.ActivityFoodDetailBinding
import com.example.kulinerin.food.model.pojo.Food
import com.example.kulinerin.ml.MlModel3
import dagger.hilt.android.AndroidEntryPoint
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.max

@AndroidEntryPoint
class FoodDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodDetailBinding
    private val foodViewModel: FoodViewModel by viewModels()

    private val imageSize : Int = 224

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = "Loading..."

        val image = intent.getSerializableExtra("Image") as File
        val img = BitmapFactory.decodeFile(image.path)

        val bitmap = Bitmap.createScaledBitmap(img, 224, 224, true)

        classifyImage(bitmap)
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

    private fun classifyImage (image : Bitmap){

        val model = MlModel3.newInstance(this)

        // Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)

        val byteBuffer = ByteBuffer.allocate(4 * imageSize * imageSize * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(imageSize * imageSize)
        image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
        var pixel = 0

        for (i in 0 until imageSize) {
            for (j in 0 until imageSize) {
                val `val` = intValues[pixel++] // RGB
                byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 255))
                byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 255))
                byteBuffer.putFloat((`val` and 0xFF) * (1f / 255))
            }
        }

        inputFeature0.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val confidences = outputFeature0.floatArray
        // find the index of the class with the biggest confidence.
        var maxPos = 0
        var maxConfidence  = 0F
        for (i in confidences.indices) {
            if (confidences[i] > maxConfidence) {
                maxConfidence = confidences[i]
                maxPos = i
            }
        }

        foodViewModel.food.observe(this) {
            setFoodData(it)
        }
        foodViewModel.getFoodDetail(maxPos+1)

        // Releases model resources if no longer used.
        model.close()
    }

    companion object {
        private const val TAG = "FoodDetailActivity"
    }
}
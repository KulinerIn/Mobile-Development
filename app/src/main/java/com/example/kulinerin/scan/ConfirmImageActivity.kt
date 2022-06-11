package com.example.kulinerin.scan

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kulinerin.databinding.ActivityConfirmImageBinding
import com.example.kulinerin.food.ui.FoodDetailActivity
import com.example.kulinerin.uriToFile
import com.yalantis.ucrop.UCrop
import java.io.File
import java.lang.StringBuilder
import java.util.*

class ConfirmImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmImageBinding
    private lateinit var resultUri: Uri

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Confirm Image"

        supportActionBar?.hide()

        val image = intent.getSerializableExtra("picture") as File

        val uri = Uri.fromFile(image)
        val destUri = StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString()

        val uCropOptions = UCrop.Options()
        uCropOptions.setBrightnessEnabled(false)
        uCropOptions.setSharpnessEnabled(false)
        uCropOptions.setContrastEnabled(false)
        uCropOptions.setSaturationEnabled(false)
        uCropOptions.setActiveControlsWidgetColor(Color.GREEN)

        UCrop.of(uri,Uri.fromFile(File(cacheDir,destUri)))
            .withAspectRatio(1F,1F)
            .withOptions(uCropOptions)
            .start(this@ConfirmImageActivity)

        binding.retakeBtn.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

        binding.uploadBtn.setOnClickListener {
            val intent = Intent(this,FoodDetailActivity::class.java)
            resultUri = Uri.fromFile(File(cacheDir,destUri))
            val resultFile = uriToFile(resultUri,applicationContext)
            intent.putExtra("Image",resultFile)
            startActivity(intent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val imgUri = data?.let { UCrop.getOutput(it) }

            binding.previewImageView.setImageURI(imgUri)
        }
    }

}
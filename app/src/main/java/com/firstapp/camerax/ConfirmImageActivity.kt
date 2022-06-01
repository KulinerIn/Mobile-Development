package com.firstapp.camerax

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.applyCanvas
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.firstapp.camerax.databinding.ActivityConfirmImageBinding
import java.io.File

class ConfirmImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmImageBinding

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Confirm Image"

        val isBackCamera = intent.getBooleanExtra("isCamera", true) as Boolean
        val image = intent.getSerializableExtra("picture") as File

        val result = rotateBitmap(
            BitmapFactory.decodeFile(image.path),
            isBackCamera
        )

        binding.previewImageView.setImageBitmap(result)

        binding.uploadBtn.setOnClickListener {
            val intent = Intent(this,ScanResultActivity::class.java)
            intent.putExtra("Image",image)
            startActivity(intent)
        }
    }

}
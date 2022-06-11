package com.example.kulinerin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.supportActionBar?.hide()

        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
            val splashIntent = Intent(this, HomeActivity::class.java)
            startActivity(splashIntent)
            finish()
        }, 2000)
    }
}
package com.example.homify
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button

 @SuppressLint("CustomSplashScreen")
 class SplashActivity : AppCompatActivity() {
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_splash)

         val getStartedButton = findViewById<Button>(R.id.btnGetStarted)

         getStartedButton.setOnClickListener {
             val intent = Intent(this, LoginActivity::class.java)
             startActivity(intent)
             finish() // Optional: finishes splash so user can't go back to it
         }
     }
}
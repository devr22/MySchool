package com.dev.myschool.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.dev.myschool.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var logo: ImageView
    private lateinit var message: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)

        logo = findViewById(R.id.main_logo)
        message = findViewById(R.id.main_message)

        val sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_anim)
        val bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim)

        logo.startAnimation(sideAnim)
        message.startAnimation(bottomAnim)

        Handler(Looper.getMainLooper()).postDelayed({
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
            finish()
        }, 3000)

    }
}
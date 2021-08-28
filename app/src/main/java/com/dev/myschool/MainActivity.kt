package com.dev.myschool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val registrationIntent = Intent(this, RegistrationActivity::class.java)
        startActivity(registrationIntent)
        finish()
    }
}
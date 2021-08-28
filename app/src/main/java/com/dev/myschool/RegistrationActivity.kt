package com.dev.myschool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dev.myschool.fragments.UserTypeFragment

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val userTypeFragment = UserTypeFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.registration_fragment_container, userTypeFragment)
            .commit()
    }
}
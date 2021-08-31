package com.dev.myschool.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dev.myschool.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

//    private lateinit var auth: FirebaseAuth
//    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginIntent)
        finish()

//        auth = FirebaseAuth.getInstance()
//        database = FirebaseFirestore.getInstance()
//
//        val user = auth.currentUser
//
//        if (user != null) {
//            database.collection("Users").document(user.uid).get()
//                .addOnCompleteListener {
//                    if (it.isSuccessful) {
//                        val doc = it.result
//                        if (doc.exists()) {
//                            if (doc.get("userType") == 1) {
//                                val intent = Intent(this, TeacherActivity::class.java)
//                                startActivity(intent)
//                                finish()
//                            } else if (doc.get("userType") == 2) {
//                                val intent = Intent(this, HomeActivity::class.java)
//                                startActivity(intent)
//                                finish()
//                            }
//                        }
//                    }
//                }
//        } else {
//            val loginIntent = Intent(this, LoginActivity::class.java)
//            startActivity(loginIntent)
//            finish()
//        }
    }
}
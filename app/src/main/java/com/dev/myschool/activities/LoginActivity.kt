package com.dev.myschool.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.dev.myschool.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var btnGoogleLogin: Button
    private lateinit var txtRegister: TextView
    private lateinit var txtForgotPwd: TextView
    private lateinit var etEmail: EditText
    private lateinit var etPwd: EditText
    private lateinit var tvWarning: TextView

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()

        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            login()
        }

        txtRegister.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initViews() {
        btnLogin = findViewById(R.id.signIn_login_btn)
        btnGoogleLogin = findViewById(R.id.signin_googleLogin_btn)
        txtRegister = findViewById(R.id.signIn_register_txt)
        txtForgotPwd = findViewById(R.id.signIn_forgotPwd_txt)
        etEmail = findViewById(R.id.signIn_email_et)
        etPwd = findViewById(R.id.signIn_pwd_et)
        tvWarning = findViewById(R.id.signIn_warning_tv)
    }

    private fun login() {
        val email = etEmail.text.toString().trim()
        val pwd = etPwd.text.toString().trim()

        auth.signInWithEmailAndPassword(email, pwd)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    tvWarning.visibility = View.INVISIBLE
                    Log.d(TAG, "signInWithEmail:success")
                    startHomeActivity()

                } else {
                    tvWarning.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener(this) {
                tvWarning.visibility = View.VISIBLE
                Toast.makeText(this, "" + it.message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun startHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null)
            startHomeActivity()
    }


    companion object {
        private const val TAG = "LoginActivity"
    }
}
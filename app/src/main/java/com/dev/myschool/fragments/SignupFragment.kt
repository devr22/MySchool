package com.dev.myschool.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.dev.myschool.R
import com.dev.myschool.activities.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class SignupFragment : Fragment() {

    private lateinit var btnPrev: Button
    private lateinit var btnSignup: Button
    private lateinit var btnGoogleSignup: Button
    private lateinit var txtLogin: TextView
    private lateinit var etEmail: EditText
    private lateinit var etPwd: EditText
    private lateinit var etConfirmPwd: EditText

    private lateinit var auth: FirebaseAuth

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_signup, container, false)
        initViews(view)

        auth = FirebaseAuth.getInstance()

        val userType = arguments?.getInt("UserType")
        Log.d(TAG, "User: $userType")

        btnPrev.setOnClickListener {
            callUserTypeFragment()
        }

        btnSignup.setOnClickListener {
            validateUserInput()
        }

        txtLogin.setOnClickListener {
            val intent = Intent(container?.context, LoginActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun initViews(view: View) {
        btnPrev = view.findViewById(R.id.signup_prev_btn)
        btnSignup = view.findViewById(R.id.signup_signup_btn)
        btnGoogleSignup = view.findViewById(R.id.signup_googleLogin_btn)
        txtLogin = view.findViewById(R.id.signup_login_txt)
        etEmail = view.findViewById(R.id.signup_email_et)
        etPwd = view.findViewById(R.id.signup_pwd_et)
        etConfirmPwd = view.findViewById(R.id.signup_confirmPwd_et)
    }

    private fun callUserTypeFragment() {
        val userTypeFragment = UserTypeFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.registration_fragment_container, userTypeFragment)
            .commit()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun validateUserInput() {
        val email = etEmail.text.toString().trim()
        val pwd = etPwd.text.toString().trim()
        val confirmPwd = etConfirmPwd.text.toString().trim()

        if (isEmailValid(email) && isPasswordValid(pwd, confirmPwd)) {
            createAccount(email, pwd)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isEmailValid(email: String): Boolean {
        return if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            true
        } else {
            etEmail.error = "Enter a valid email!"
            etEmail.focusable
            false
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isPasswordValid(pwd: String, confirmPwd: String): Boolean {
        return if (pwd.isNotEmpty() && pwd.length >= 6) {
            if (pwd == confirmPwd) {
                true
            } else {
                etConfirmPwd.error = "Entered text does not matches password"
                false
            }
        } else {
            etPwd.error = "Password length must be at least 6 characters"
            etPwd.focusable
            false
        }
    }

    private fun createAccount(email: String, pwd: String) {
        activity?.let { it ->
            auth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(it) {
                    if (it.isSuccessful) {
                        Log.d(TAG, "registerUser:success")
                        val user = auth.currentUser
                        if (user != null) {
                            Log.d(TAG, "User: ${user.uid}")
                        }
                    } else {
                        Log.d(TAG, "registerUser:failure", it.exception);
                        Toast.makeText(activity, "Registration Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
                .addOnFailureListener(it) {
                    Toast.makeText(activity, "" + it.message, Toast.LENGTH_SHORT).show();
                }
        }
    }

    companion object {
        private const val TAG = "SignupFragment"
    }

}
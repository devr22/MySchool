package com.dev.myschool.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.dev.myschool.HomeActivity
import com.dev.myschool.LoginActivity
import com.dev.myschool.R

class SignupFragment : Fragment() {

    private lateinit var btnPrev: Button
    private lateinit var btnSignup: Button
    private lateinit var btnGoogleSignup: Button
    private lateinit var txtLogin: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_signup, container, false)
        initViews(view)

        btnPrev.setOnClickListener {
            callUserTypeFragment()
        }

        btnSignup.setOnClickListener {
            val intent = Intent(container?.context, HomeActivity::class.java)
            startActivity(intent)
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
    }

    private fun callUserTypeFragment() {
        val userTypeFragment = UserTypeFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.registration_fragment_container, userTypeFragment)
            .commit()
    }

}
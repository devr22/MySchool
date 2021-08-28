package com.dev.myschool.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.dev.myschool.R

class UserTypeFragment : Fragment() {

    private lateinit var btnNext: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_type, container, false)

        initViews(view)

        btnNext.setOnClickListener {
            callSignupFragment()
        }

        return view
    }

    private fun initViews(view: View) {
        btnNext = view.findViewById(R.id.userType_next_btn)
    }

    private fun callSignupFragment() {
        val signupFragment = SignupFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.registration_fragment_container, signupFragment).addToBackStack(null)
            .commit()
    }

}
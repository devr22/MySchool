package com.dev.myschool.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.dev.myschool.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class UserTypeFragment : Fragment() {

    private lateinit var btnNext: Button
    private lateinit var txtRoles: TextView

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

        txtRoles.setOnClickListener {
            showBottomSheetDialog(container!!.context)
        }

        return view
    }

    private fun initViews(view: View) {
        btnNext = view.findViewById(R.id.userType_next_btn)
        txtRoles = view.findViewById(R.id.userType_role_txt)
    }

    private fun callSignupFragment() {
        val signupFragment = SignupFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.registration_fragment_container, signupFragment).addToBackStack(null)
            .commit()
    }

    private fun showBottomSheetDialog(context: Context) {
        val bottomSheetDialog = BottomSheetDialog(context)
        bottomSheetDialog.setContentView(R.layout.user_access_bottom_sheet)
        bottomSheetDialog.show()
    }

}
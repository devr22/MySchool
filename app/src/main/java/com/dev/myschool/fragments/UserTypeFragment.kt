package com.dev.myschool.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.dev.myschool.activities.LoginActivity
import com.dev.myschool.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar

class UserTypeFragment : Fragment() {

    private lateinit var btnNext: Button
    private lateinit var txtRoles: TextView
    private lateinit var txtLogin: TextView
    private lateinit var rgUser: RadioGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_type, container, false)

        initViews(view)

        btnNext.setOnClickListener {
            verifyUserInput()
        }

        txtRoles.setOnClickListener {
            showBottomSheetDialog(container!!.context)
        }

        txtLogin.setOnClickListener {
            val intent = Intent(container?.context, LoginActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun initViews(view: View) {
        btnNext = view.findViewById(R.id.userType_next_btn)
        txtRoles = view.findViewById(R.id.userType_role_txt)
        txtLogin = view.findViewById(R.id.userType_login_txt)
        rgUser = view.findViewById(R.id.userType_radioGroup)
    }

    private fun verifyUserInput() {
        when (rgUser.checkedRadioButtonId) {
            R.id.userType_teacher_rb -> {
                callSignupFragment(1)
            }
            R.id.userType_student_rb -> {
                callSignupFragment(2)
            }
            else -> {
                Toast.makeText(activity, "Select any one user type", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun callSignupFragment(user: Int) {
        val signupFragment = SignupFragment()
        val args = Bundle()
        args.putInt("UserType", user)
        signupFragment.arguments = args
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
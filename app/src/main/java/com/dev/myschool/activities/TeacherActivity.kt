package com.dev.myschool.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.dev.myschool.R
import com.dev.myschool.models.Subject
import com.dev.myschool.models.User
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TeacherActivity : AppCompatActivity() {

    private lateinit var collapsingToolbar: CollapsingToolbarLayout
    private lateinit var tvDisplayName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvUserType: TextView
    private lateinit var tvLogout: TextView
    private lateinit var tvCreateSubject: TextView
    private lateinit var etSubject: EditText
    private lateinit var etName: EditText
    private lateinit var etLink: EditText
    private lateinit var etTiming: EditText
    private lateinit var layoutCreateSubject: LinearLayout
    private lateinit var btnCreate: MaterialButton

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher)

        initViews()

        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        val user = auth.currentUser

        if (user != null) {
            database.collection("Users").document(user.uid).get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val doc = it.result
                        if (doc.exists()) {
                            val userDetail = doc.toObject(User::class.java)
                            if (userDetail != null) {
                                populateUserDetail(userDetail)
                            }
                        } else {
                            Toast.makeText(
                                this,
                                "Error while fetching your data from server: ${it.exception}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
        }

        tvCreateSubject.setOnClickListener {
            if (layoutCreateSubject.visibility == View.GONE) {
                layoutCreateSubject.visibility = View.VISIBLE
                tvCreateSubject.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_arrow_up,
                    0
                )
            } else {
                layoutCreateSubject.visibility = View.GONE
                tvCreateSubject.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_arrow_down,
                    0
                )
            }
        }

        btnCreate.setOnClickListener {
            if (
                etSubject.text.toString().trim().isNotEmpty() &&
                etName.text.toString().trim().isNotEmpty()
            ) {
                saveClassToDatabase()
            } else {
                Toast.makeText(this, "Subject and Teacher can't be empty!!", Toast.LENGTH_LONG)
                    .show()
            }
        }

        tvLogout.setOnClickListener {
            auth.signOut()
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
            finish()
        }

    }

    private fun initViews() {
        collapsingToolbar = findViewById(R.id.collapsing_toolbar)
        collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT)
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE)

        tvLogout = findViewById(R.id.teacher_logout_tv)
        tvDisplayName = findViewById(R.id.toolbar_displayName_tv)
        tvEmail = findViewById(R.id.toolbar_email_tv)
        tvUserType = findViewById(R.id.toolbar_userType_tv)
        tvCreateSubject = findViewById(R.id.teacher_createSubject_tv)
        etSubject = findViewById(R.id.teacher_subjectName_et)
        etName = findViewById(R.id.teacher_yourName_et)
        etLink = findViewById(R.id.teacher_classLink_et)
        etTiming = findViewById(R.id.teacher_timing_et)
        layoutCreateSubject = findViewById(R.id.teacher_createSubject_layout)
        btnCreate = findViewById(R.id.teacher_create_btn)
    }

    @SuppressLint("SetTextI18n")
    private fun populateUserDetail(user: User) {
        tvDisplayName.text = user.email[0].uppercaseChar().toString()
        tvEmail.text = user.email
        when (user.userType) {
            1 -> tvUserType.text = "Teacher"
            2 -> tvUserType.text = "Student"
            else -> tvUserType.text = "User Type Not determined"
        }
    }

    private fun saveClassToDatabase() {
        val docRef = database.collection("AvlSubjects").document()

        val subject = Subject(
            docRef.id,
            etSubject.text.toString().trim(),
            etName.text.toString().trim(),
            etLink.text.toString().trim(),
            etTiming.text.toString().trim()
        )

        docRef.set(subject)
            .addOnSuccessListener {
                Toast.makeText(this, "Created", Toast.LENGTH_LONG).show()
                makeInputFieldsEmpty()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
                makeInputFieldsEmpty()
            }
    }

    private fun makeInputFieldsEmpty() {
        etName.setText("")
        etLink.setText("")
        etSubject.setText("")
        etTiming.setText("")
    }

}
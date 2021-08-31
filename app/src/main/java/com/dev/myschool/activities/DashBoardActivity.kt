package com.dev.myschool.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dev.myschool.R
import com.dev.myschool.models.User
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DashBoardActivity : AppCompatActivity() {

    private lateinit var collapsingToolbar: CollapsingToolbarLayout
    private lateinit var tvDisplayName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvUserType: TextView
    private lateinit var tvAvlSubject: TextView
    private lateinit var tvEnrolledSubject: TextView
    private lateinit var tvAssignment: TextView
    private lateinit var tvTest: TextView
    private lateinit var tvCalendar: TextView
    private lateinit var tvLogout: TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

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

        tvAssignment.setOnClickListener {
            val intent = Intent(this, AssignmentActivity::class.java)
            startActivity(intent)
        }

        tvAvlSubject.setOnClickListener {
            val intent = Intent(this, AvlSubjectActivity::class.java)
            startActivity(intent)
        }

        tvEnrolledSubject.setOnClickListener {
            HomeActivity.homeActivity.finish()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        tvTest.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
        }

        tvLogout.setOnClickListener {
            HomeActivity.homeActivity.finish()
            FirebaseAuth.getInstance().signOut()
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
            finish()
        }

        tvCalendar.setOnClickListener {
            Toast.makeText(
                this,
                "Stay tuned, calendar for student will be available very soon...",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun initViews() {
        collapsingToolbar = findViewById(R.id.collapsing_toolbar)
        collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT)
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE)

        tvLogout = findViewById(R.id.dashboard_logout_tv)
        tvAvlSubject = findViewById(R.id.dashboard_avlSubject_tv)
        tvEnrolledSubject = findViewById(R.id.dashboard_enrolledSubject_tv)
        tvAssignment = findViewById(R.id.dashboard_assignment_tv)
        tvTest = findViewById(R.id.dashboard_test_tv)
        tvCalendar = findViewById(R.id.dashboard_calendar_tv)
        tvDisplayName = findViewById(R.id.toolbar_displayName_tv)
        tvEmail = findViewById(R.id.toolbar_email_tv)
        tvUserType = findViewById(R.id.toolbar_userType_tv)
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

    init {
        dashboardActivity = this
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var dashboardActivity: Activity
    }

}
package com.dev.myschool.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.myschool.R
import com.dev.myschool.adapters.SubjectAdapter
import com.dev.myschool.models.Subject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import de.hdodenhof.circleimageview.CircleImageView

class HomeActivity : AppCompatActivity() {
    private lateinit var ivProfile: CircleImageView
    private lateinit var rv: RecyclerView
    private lateinit var tvMsg: TextView

    private lateinit var adapterSubject: SubjectAdapter

    private lateinit var database: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()

        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()
        user = auth.currentUser!!

        getSubjectList()

        ivProfile.setOnClickListener {
            val intent = Intent(this, DashBoardActivity::class.java)
            startActivity(intent)
        }

        rv.layoutManager = LinearLayoutManager(this)
        rv.setHasFixedSize(true)


    }

    private fun initViews() {
        ivProfile = findViewById(R.id.home_profileImage)
        rv = findViewById(R.id.home_rv)
        tvMsg = findViewById(R.id.home_msg_tv)
    }

    @SuppressLint("SetTextI18n")
    private fun getSubjectList() {
        database.collection("Users").document(user.uid)
            .collection("Subjects").get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    tvMsg.visibility = View.VISIBLE
                } else {
                    tvMsg.visibility = View.GONE
                    val subjectList: ArrayList<Subject> = arrayListOf()
                    for (doc in it) {
                        val subject = doc.toObject(Subject::class.java)
                        subjectList.add(subject)
                    }

                    adapterSubject = SubjectAdapter(subjectList)
                    rv.adapter = adapterSubject
                }
            }
            .addOnFailureListener {
                tvMsg.text = "Error while fetching data from server!"
                tvMsg.visibility = View.VISIBLE
            }
    }

    companion object {
        private const val TAG = "HomeActivity"
    }

}
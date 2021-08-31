package com.dev.myschool.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.myschool.R
import com.dev.myschool.adapters.AvlSubjectAdapter
import com.dev.myschool.models.Subject
import com.dev.myschool.utils.EnrolledSubject
import com.google.firebase.firestore.FirebaseFirestore

class AvlSubjectActivity : AppCompatActivity() {

    private lateinit var ivClose: ImageView
    private lateinit var rv: RecyclerView
    private lateinit var tvMsg: TextView
    private lateinit var progress: ProgressBar

    private lateinit var adapterAvlSubject: AvlSubjectAdapter

    private lateinit var database: FirebaseFirestore
//    private lateinit var auth: FirebaseAuth
//    private lateinit var user: FirebaseUser

    private var avlSubjectList: ArrayList<Subject> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avl_subject)

        initViews()

//        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

//        user = auth.currentUser!!

        fetchAvlSubject()


        ivClose.setOnClickListener {
            DashBoardActivity.dashboardActivit.finish()
            val intent = Intent(this, DashBoardActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun initViews() {
        ivClose = findViewById(R.id.avlSubject_close_iv)
        rv = findViewById(R.id.avlSubject_rv)
        tvMsg = findViewById(R.id.avlSubject_msg_tv)
        progress = findViewById(R.id.avlSubject_progress)

        rv.layoutManager = LinearLayoutManager(this)
        rv.setHasFixedSize(true)
    }

    private fun fetchAvlSubject() {
        progress.visibility = View.VISIBLE
        database.collection("AvlSubjects").get()
            .addOnSuccessListener {
                progress.visibility = View.GONE
                if (it.isEmpty) {
                    tvMsg.visibility = View.VISIBLE
                } else {
                    tvMsg.visibility = View.GONE
                    for (doc in it) {
                        val subject = doc.toObject(Subject::class.java)
                        avlSubjectList.add(subject)
                    }
                    adapterAvlSubject = AvlSubjectAdapter(
                        avlSubjectList,
                        EnrolledSubject.enrolledSubjects,
                        this
                    )
                    rv.adapter = adapterAvlSubject
                }
            }
            .addOnFailureListener {
                progress.visibility = View.GONE
                tvMsg.visibility = View.VISIBLE
            }
    }

    companion object {
        private const val TAG = "AvlSubjectActivity"
    }

}
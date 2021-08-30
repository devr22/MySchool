package com.dev.myschool.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.myschool.R
import com.dev.myschool.adapters.AssignmentAdapter
import com.dev.myschool.adapters.TestAdapter
import com.dev.myschool.models.Assignment
import com.dev.myschool.models.Test
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class TestActivity : AppCompatActivity() {

    private lateinit var ivClose: ImageView
    private lateinit var tvUpcoming: TextView
    private lateinit var tvPast: TextView
    private lateinit var tvUpcomingLine: View
    private lateinit var tvPastLine: View
    private lateinit var rv: RecyclerView
    private lateinit var tvMsg: TextView

    private val upcomingList: ArrayList<Test> = arrayListOf()
    private val pastList: ArrayList<Test> = arrayListOf()

    private var adapterList: ArrayList<Test> = arrayListOf()

    private lateinit var adapterTest: TestAdapter

    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        initViews()

        database = FirebaseFirestore.getInstance()

        getTests()

        ivClose.setOnClickListener {
            DashBoardActivity.dashboardActivit.finish()
            val intent = Intent(this, DashBoardActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun initViews() {
        ivClose = findViewById(R.id.test_close_iv)
        tvUpcoming = findViewById(R.id.test_upcoming_tv)
        tvPast = findViewById(R.id.test_past_tv)
        tvUpcomingLine = findViewById(R.id.test_upcoming_line)
        tvPastLine = findViewById(R.id.test_past_line)
        rv = findViewById(R.id.test_rv)
        tvMsg = findViewById(R.id.test_msg_tv)

        rv.layoutManager = LinearLayoutManager(this)
    }

    private fun getTests() {
        database.collection("Test").get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    tvMsg.visibility = View.VISIBLE
                } else {
                    tvMsg.visibility = View.GONE
                    for (doc in it) {
                        val test = doc.toObject(Test::class.java)
                        if (test.timestamp >= Timestamp.now())
                            upcomingList.add(test)
                        else if (test.timestamp < Timestamp.now()){
                            test.testLink = "Not Active"
                            pastList.add(test)
                        }
                    }

                    populateTest()
                }
            }
            .addOnFailureListener {
                tvMsg.visibility = View.VISIBLE
            }
    }

    private fun populateTest() {
        tvUpcomingLine.visibility = View.VISIBLE
        tvPastLine.visibility = View.INVISIBLE

        bindAdapter(upcomingList, "No upcoming test for now !")

        tvUpcoming.setOnClickListener {
            tvUpcomingLine.visibility = View.VISIBLE
            tvPastLine.visibility = View.INVISIBLE
            bindAdapter(upcomingList, "No upcoming test for now !")
        }

        tvPast.setOnClickListener {
            tvUpcomingLine.visibility = View.INVISIBLE
            tvPastLine.visibility = View.VISIBLE
            bindAdapter(pastList, "No test found !")
        }
    }

    private fun bindAdapter(list: ArrayList<Test>, msg: String) {
        adapterList = list

        if (adapterList.isEmpty()) {
            tvMsg.text = msg
            tvMsg.visibility = View.VISIBLE
        } else {
            tvMsg.visibility = View.GONE
        }
        adapterTest = TestAdapter(adapterList, this)
        rv.adapter = adapterTest
    }

}
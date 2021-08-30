package com.dev.myschool.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.myschool.R
import com.dev.myschool.adapters.AssignmentAdapter
import com.dev.myschool.models.Assignment
import com.google.firebase.firestore.FirebaseFirestore

class AssignmentActivity : AppCompatActivity() {

    private lateinit var ivClose: ImageView
    private lateinit var tvTodo: TextView
    private lateinit var tvDone: TextView
    private lateinit var tvDoneLine: View
    private lateinit var tvMissing: TextView
    private lateinit var tvMissingLine: View
    private lateinit var tvTodoLine: View
    private lateinit var rv: RecyclerView
    private lateinit var tvMsg: TextView

    private val todoList: ArrayList<Assignment> = arrayListOf()
    private val doneList: ArrayList<Assignment> = arrayListOf()
    private val missingList: ArrayList<Assignment> = arrayListOf()

    private var adapterList: ArrayList<Assignment> = arrayListOf()

    private lateinit var adapterAssignment: AssignmentAdapter

    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assignment)

        initViews()

        database = FirebaseFirestore.getInstance()

        getAssignments()

        ivClose.setOnClickListener {
            DashBoardActivity.dashboardActivit.finish()
            val intent = Intent(this, DashBoardActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun initViews() {
        ivClose = findViewById(R.id.assignment_close_iv)
        tvTodo = findViewById(R.id.assignment_todo_tv)
        tvDone = findViewById(R.id.assignment_done_tv)
        tvMissing = findViewById(R.id.assignment_missing_tv)
        tvMissingLine = findViewById(R.id.assignment_missing_line)
        tvTodoLine = findViewById(R.id.assignment_todo_line)
        tvDoneLine = findViewById(R.id.assignment_done_line)
        rv = findViewById(R.id.assignment_rv)
        tvMsg = findViewById(R.id.assignment_msg_tv)

        rv.layoutManager = LinearLayoutManager(this)
    }

    @SuppressLint("SetTextI18n")
    private fun getAssignments() {
        database.collection("Assignments").get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    tvMsg.text = "No Assignment Found !"
                    tvMsg.visibility = View.VISIBLE
                } else {
                    tvMsg.visibility = View.GONE
                    for (doc in it) {
                        val assignment = doc.toObject(Assignment::class.java)
                        if (assignment.done)
                            doneList.add(assignment)
                        else if (assignment.missing)
                            missingList.add(assignment)
                        else if (!assignment.done && !assignment.missing)
                            todoList.add(assignment)
                    }
                    populateAssignment()
                }
            }
            .addOnFailureListener {
                tvMsg.text = "No Assignment Found !"
                tvMsg.visibility = View.VISIBLE
            }
    }

    @SuppressLint("SetTextI18n")
    private fun populateAssignment() {

        tvTodoLine.visibility = View.VISIBLE
        tvDoneLine.visibility = View.INVISIBLE
        tvMissingLine.visibility = View.INVISIBLE

        bindAdapter(todoList, "No Due Assignment")

        tvTodo.setOnClickListener {
            tvTodoLine.visibility = View.VISIBLE
            tvDoneLine.visibility = View.INVISIBLE
            tvMissingLine.visibility = View.INVISIBLE
            bindAdapter(todoList, "No Due Assignment")
        }

        tvDone.setOnClickListener {
            tvTodoLine.visibility = View.INVISIBLE
            tvDoneLine.visibility = View.VISIBLE
            tvMissingLine.visibility = View.INVISIBLE
            bindAdapter(doneList, "No Assignment Found !")
        }

        tvMissing.setOnClickListener {
            tvTodoLine.visibility = View.INVISIBLE
            tvDoneLine.visibility = View.INVISIBLE
            tvMissingLine.visibility = View.VISIBLE
            bindAdapter(missingList, "No Missing Assignment Found !")
        }
    }

    private fun bindAdapter(list: ArrayList<Assignment>, msg: String) {
        adapterList = list

        if (adapterList.isEmpty()) {
            tvMsg.text = msg
            tvMsg.visibility = View.VISIBLE
        } else {
            tvMsg.visibility = View.GONE
        }
        adapterAssignment = AssignmentAdapter(adapterList, this)
        rv.adapter = adapterAssignment
    }


}
package com.dev.myschool.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.myschool.R
import com.dev.myschool.adapters.SubjectMessageAdapter
import com.dev.myschool.models.SubjectMessage
import com.dev.myschool.utils.AppConstant
import com.dev.myschool.utils.EnrolledSubject
import com.google.firebase.firestore.FirebaseFirestore

class SubjectDetailActivity : AppCompatActivity() {

    private lateinit var tvToolbarTitle: TextView
    private lateinit var tvMsg: TextView
    private lateinit var rv: RecyclerView
    private lateinit var ivClose: ImageView
    private lateinit var ivUnEnroll: ImageView

    private lateinit var adapterSubjectMessage: SubjectMessageAdapter
    private val messageList: ArrayList<SubjectMessage> = arrayListOf()

    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject_detail)

        initViews()

        database = FirebaseFirestore.getInstance()

        database.collection("SubjectMessages").get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    tvMsg.visibility = View.VISIBLE
                } else {
                    tvMsg.visibility = View.GONE
                    for (doc in it) {
                        val message = doc.toObject(SubjectMessage::class.java)
                        messageList.add(message)
                    }
                    adapterSubjectMessage = SubjectMessageAdapter(messageList)
                    rv.adapter = adapterSubjectMessage
                }
            }
            .addOnFailureListener {
                tvMsg.visibility = View.VISIBLE
            }

        ivClose.setOnClickListener {
            HomeActivity.homeActivity.finish()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        ivUnEnroll.setOnClickListener {
            popupUnEnroll()
        }

    }

    private fun initViews() {
        tvToolbarTitle = findViewById(R.id.subDetail_toolbarTitle)
        ivClose = findViewById(R.id.subDetail_close_iv)
        tvMsg = findViewById(R.id.subDetail_msg_tv)
        rv = findViewById(R.id.subDetail_rv)
        ivUnEnroll = findViewById(R.id.subDetail_unEnroll_iv)

        rv.layoutManager = LinearLayoutManager(this)
        rv.setHasFixedSize(true)
    }

    private fun popupUnEnroll() {
        val dialogView = layoutInflater.inflate(R.layout.unenroll_popup, null)

        val unEnrollDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .show()

        val btnCancel = dialogView.findViewById<Button>(R.id.popup_cancel_btn)
        val btnUnEnroll = dialogView.findViewById<Button>(R.id.popup_unEnroll_btn)

        btnCancel.setOnClickListener {
            unEnrollDialog.dismiss()
        }

        btnUnEnroll.setOnClickListener {
            Toast.makeText(this, "Un enrolled", Toast.LENGTH_SHORT).show()
            unEnrollDialog.dismiss()
        }
    }

}
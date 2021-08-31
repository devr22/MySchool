package com.dev.myschool.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dev.myschool.R
import com.dev.myschool.models.Subject
import com.dev.myschool.utils.EnrolledSubject
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AvlSubjectAdapter(
    private val avlSubjectList: ArrayList<Subject>,
    private val enrolledSubject: ArrayList<String>,
    private val context: Context
) : RecyclerView.Adapter<AvlSubjectAdapter.MyViewHolder>() {

    val database = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvSubject: TextView = view.findViewById(R.id.avlSubject_name)
        val tvTeacher: TextView = view.findViewById(R.id.avlSubject_teacher)
        val btnAction: MaterialButton = view.findViewById(R.id.avlSubject_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.avl_subject_card, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvSubject.text = avlSubjectList[position].name.trim()
        holder.tvTeacher.text = avlSubjectList[position].teacher.trim()

        if (enrolledSubject.contains(avlSubjectList[position].id.trim())) {
            holder.btnAction.text = "Un Enroll"
        } else {
            holder.btnAction.text = "Enroll"
        }

        holder.btnAction.setOnClickListener {
            if (holder.btnAction.text == "Un Enroll") {
                holder.btnAction.text = "Enroll"
                database.collection("Users").document(user.uid!!)
                    .collection("Subjects").document(avlSubjectList[position].id.trim())
                    .delete()
                    .addOnSuccessListener {
                        Toast.makeText(context, "Un Enrolled", Toast.LENGTH_SHORT).show()
                        EnrolledSubject.enrolledSubjects.remove(avlSubjectList[position].id.trim())
                    }
                    .addOnFailureListener {
                        holder.btnAction.text = "Un Enroll"
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                    }
            } else {
                holder.btnAction.text = "Un Enroll"
                database.collection("Users").document(user.uid!!)
                    .collection("Subjects").document(avlSubjectList[position].id.trim())
                    .set(avlSubjectList[position])
                    .addOnSuccessListener {
                        Toast.makeText(context, "Enrolled", Toast.LENGTH_SHORT).show()
                        EnrolledSubject.enrolledSubjects.add(avlSubjectList[position].id.trim())
                    }
                    .addOnFailureListener {
                        holder.btnAction.text = "Enroll"
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    override fun getItemCount(): Int {
        return avlSubjectList.size
    }

}
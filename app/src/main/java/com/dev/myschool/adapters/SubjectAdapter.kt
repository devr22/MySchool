package com.dev.myschool.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.myschool.R
import com.dev.myschool.models.Subject

class SubjectAdapter(
    private val subjectList: ArrayList<Subject>,
    private val imageList: ArrayList<Int>
) :
    RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {

    class SubjectViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvSubject: TextView = view.findViewById(R.id.subject_name)
        val tvTeacher: TextView = view.findViewById(R.id.subject_teacher)
        val subjectCardLayout: RelativeLayout = view.findViewById(R.id.subjectCard_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.subject_card, parent, false)
        return SubjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.subjectCardLayout.setBackgroundResource(imageList[position % imageList.size])
        holder.tvSubject.text = subjectList[position].name
        holder.tvTeacher.text = subjectList[position].teacher
    }

    override fun getItemCount(): Int {
        return subjectList.size
    }

}
package com.dev.myschool.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.myschool.R
import com.dev.myschool.models.Subject

class SubjectAdapter(private val subjectList: ArrayList<Subject>) :
    RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.subject_card, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvSubject.text = subjectList[position].name
        holder.tvTeacher.text = subjectList[position].teacher
    }

    override fun getItemCount(): Int {
        return subjectList.size
    }

}

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvSubject: TextView = view.findViewById(R.id.subject_name)
    val tvTeacher: TextView = view.findViewById(R.id.subject_teacher)
}
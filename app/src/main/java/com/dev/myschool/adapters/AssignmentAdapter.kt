package com.dev.myschool.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.dev.myschool.R
import com.dev.myschool.models.Assignment

class AssignmentAdapter(
    private val assignmentList: ArrayList<Assignment>,
    private val context: Context
) :
    RecyclerView.Adapter<AssignmentAdapter.MyViewHolder>() {


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.assignment_name_tv)
        val tvDueDate: TextView = view.findViewById(R.id.assignment_dueDate_tv)
        val tvSubject: TextView = view.findViewById(R.id.assignment_subject_tv)
        val tvTask: TextView = view.findViewById(R.id.assignment_task_tv)
        val assignmentCard: CardView = view.findViewById(R.id.assignment_card)
        val expandableLayout: LinearLayout = view.findViewById(R.id.assignment_expandableLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.assignment_card, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvName.text = assignmentList[position].name.trim()
        holder.tvSubject.text = assignmentList[position].subject.trim()
        holder.tvTask.text = assignmentList[position].taskLink.trim()
        holder.tvDueDate.text = "Due: ${assignmentList[position].dueDate.toDate().toString().trim()}"

        holder.tvTask.setOnClickListener {
            val uri = Uri.parse(assignmentList[position].taskLink.trim())
            context.startActivity(Intent(Intent.ACTION_VIEW, uri))
        }

        holder.assignmentCard.setOnClickListener {
            if (holder.expandableLayout.visibility == View.VISIBLE)
                holder.expandableLayout.visibility = View.GONE
            else if (holder.expandableLayout.visibility == View.GONE)
                holder.expandableLayout.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return assignmentList.size
    }

}

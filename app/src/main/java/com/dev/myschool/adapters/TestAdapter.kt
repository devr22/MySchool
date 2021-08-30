package com.dev.myschool.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.dev.myschool.R
import com.dev.myschool.models.Test

class TestAdapter(
    private val testList: ArrayList<Test>,
    private val context: Context
) : RecyclerView.Adapter<TestAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.test_name_tv)
        val tvDueDate: TextView = view.findViewById(R.id.test_dueDate_tv)
        val tvSubject: TextView = view.findViewById(R.id.test_subject_tv)
        val tvTestLink: TextView = view.findViewById(R.id.test_testLink_tv)
        val tvMarks: TextView = view.findViewById(R.id.test_marks_tv)
        val testCard: CardView = view.findViewById(R.id.test_card)
        val expandableLayout: LinearLayout = view.findViewById(R.id.test_expandableLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.test_card, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvName.text = testList[position].name.trim()
        holder.tvDueDate.text = testList[position].timestamp.toDate().toString().trim()
        holder.tvSubject.text = testList[position].subject.trim()
        holder.tvTestLink.text = testList[position].testLink.trim()

        if (testList[position].obtainedMarks != null) {
            holder.tvMarks.text =
                "${testList[position].obtainedMarks}/${testList[position].fullMarks}"
        } else {
            holder.tvMarks.text = "F.M.: ${testList[position].fullMarks}"
        }

        holder.tvTestLink.setOnClickListener {
            val uri = Uri.parse(testList[position].testLink.trim())
            if (uri.toString() == "Not Active")
                Toast.makeText(context, "Can't be open", Toast.LENGTH_LONG).show()
            else
                context.startActivity(Intent(Intent.ACTION_VIEW, uri))
        }

        holder.testCard.setOnClickListener {
            if (holder.expandableLayout.visibility == View.VISIBLE)
                holder.expandableLayout.visibility = View.GONE
            else if (holder.expandableLayout.visibility == View.GONE)
                holder.expandableLayout.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return testList.size
    }

}
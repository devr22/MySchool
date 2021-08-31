package com.dev.myschool.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.myschool.R
import com.dev.myschool.models.SubjectMessage

class SubjectMessageAdapter(
    private val messageList: ArrayList<SubjectMessage>
) : RecyclerView.Adapter<SubjectMessageAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMsg: TextView = view.findViewById(R.id.subDetail_item_msg_tv)
        val tvTimestamp: TextView = view.findViewById(R.id.subDetail_item_timestamp_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.subject_detail_msg_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvMsg.text = messageList[position].message.trim()
        holder.tvTimestamp.text = messageList[position].timestamp.toDate().toString().trim()
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

}
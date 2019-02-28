package com.example.roomdb.Adapter

import android.content.Context
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.roomdb.Fragment.AfterLoginFragment
import com.example.roomdb.R
import com.example.roomdb.Tables.Task


class TaskAdapter(private val mCtx: Context, private val taskList: List<Task>, private val fcommunication : FragmentCommunication) : RecyclerView.Adapter<TaskAdapter.TasksViewHolder>() {

     var activity = AfterLoginFragment().activity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerviewitem, parent, false)
        return TasksViewHolder(view)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val t = taskList[position]
        holder.textViewTask.text = t.task
        holder.textViewDesc.text = t.desc
        holder.textViewFinishBy.text = t.finishBy

        if (t.isFinished)
            holder.textViewStatus.text = "Completed"
        else
            holder.textViewStatus.text = "Not Completed"
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    inner class TasksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var textViewStatus: TextView = itemView.findViewById(R.id.textViewStatus)
        var textViewTask: TextView = itemView.findViewById(R.id.textViewTask)
        var textViewDesc: TextView = itemView.findViewById(R.id.textViewDesc)
        var textViewFinishBy: TextView = itemView.findViewById(R.id.textViewFinishBy)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val task = taskList[adapterPosition]

            fcommunication.respond(task.email!!,task)


        }
    }

     interface FragmentCommunication {
         fun respond(email: String,task : Task)
     }

}
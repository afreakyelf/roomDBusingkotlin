package com.example.roomdb.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_update_task.*
import android.os.AsyncTask
import android.support.v7.app.AlertDialog
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import com.example.roomdb.Database.DatabaseClient
import com.example.roomdb.R
import com.example.roomdb.Tables.Task


class UpdateTaskFragment : Fragment() {
    private var mDatabaseName : String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_update_task,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val task = arguments?.getSerializable("task") as Task

        mDatabaseName = arguments?.getString("email")

        loadTask(task)

        button_update.setOnClickListener {
            updateTask(task)
        }

        button_delete.setOnClickListener {
            val builder = AlertDialog.Builder(context!!)
            builder.setTitle("Are you sure?")
            builder.setPositiveButton("Yes"
            ) { _, _ -> deleteTask(task) }
            builder.setNegativeButton("No"
            ) { _, _ -> }

            val ad = builder.create()
            ad.show()
        }

    }



    private fun updateTask(task: Task) {
        val sTask = name.text.toString()
        val sDesc = editTextDesc.text.toString()
        val sFinishBy = editTextFinishBy.text.toString()

        if (sTask.isEmpty()) {
            name.error = "Task required"
            name.requestFocus()
            return
        }

        if (sDesc.isEmpty()) {
            editTextDesc.error = "Desc required"
            editTextDesc.requestFocus()
            return
        }

        if (sFinishBy.isEmpty()) {
            editTextFinishBy.error = "Finish by required"
            editTextFinishBy.requestFocus()
            return
        }

        class UpdateTask:AsyncTask<Void,Void,Void>(){
            @SuppressLint("WrongThread")
            override fun doInBackground(vararg params: Void?): Void? {
                task.task = sTask
                task.desc = sDesc
                task.finishBy = sFinishBy
                task.isFinished = checkBoxFinished.isChecked

                DatabaseClient.getInstance(context!!).getLoginDatabase()
                    .taskDao()
                    .update(task)

                return null

            }


            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                Toast.makeText(context!!, "Updated", Toast.LENGTH_SHORT).show()

                val fragment = AfterLoginFragment()
                val fm  = activity?.supportFragmentManager
                val ft = fm?.beginTransaction()
                val bundle  = Bundle()
                bundle.putString("dbname",mDatabaseName)
                fragment.arguments = bundle
                ft?.replace(R.id.content_frame,fragment)
                ft?.addToBackStack(null)
                ft?.commit()
            }


        }

        UpdateTask().execute()

    }

    private fun loadTask(task: Task) {
        name.setText(task.task)
        editTextDesc.setText(task.desc)
        editTextFinishBy.setText(task.finishBy)
        checkBoxFinished.isChecked = task.isFinished
    }


    private fun deleteTask(task: Task) {

        class DeleteTask:AsyncTask<Void,Void,Void>(){
            override fun doInBackground(vararg params: Void?): Void? {
                DatabaseClient.getInstance(context!!).getLoginDatabase()
                    .taskDao()
                    .delete(task)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                Toast.makeText(context!!, "Deleted", Toast.LENGTH_LONG).show()

                val fragment = AfterLoginFragment()
                val fm  = activity?.supportFragmentManager
                val ft = fm?.beginTransaction()
                val bundle  = Bundle()
                bundle.putString("dbname",mDatabaseName)
                fragment.arguments = bundle
                ft?.replace(R.id.content_frame,fragment)
                ft?.addToBackStack(null)
                ft?.commit()
            }
        }

        DeleteTask().execute()

    }

}

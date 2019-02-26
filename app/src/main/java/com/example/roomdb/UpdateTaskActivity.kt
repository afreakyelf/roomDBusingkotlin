package com.example.roomdb

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_update_task.*
import android.os.AsyncTask
import android.support.v7.app.AlertDialog
import android.content.Intent

import android.widget.Toast





class UpdateTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_task)

        val task = intent.getSerializableExtra("task") as Task
        loadTask(task)

        button_update.setOnClickListener {
            updateTask(task)
        }

        button_delete.setOnClickListener {
            val builder = AlertDialog.Builder(this@UpdateTaskActivity)
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
        val sTask = editTextTask.text.toString()
        val sDesc = editTextDesc.text.toString()
        val sFinishBy = editTextFinishBy.text.toString()

        if (sTask.isEmpty()) {
            editTextTask.error = "Task required"
            editTextTask.requestFocus()
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

                DatabaseClient.getInstance(applicationContext).getAppDatabase()
                    .taskDao().update(task)

                return null

            }

            override fun onPostExecute(aVoid: Void) {
                super.onPostExecute(aVoid)
                Toast.makeText(applicationContext, "Updated", Toast.LENGTH_LONG).show()
                finish()
                startActivity(Intent(this@UpdateTaskActivity, MainActivity::class.java))
            }

        }

        UpdateTask().execute()

    }

    private fun loadTask(task: Task) {
        editTextTask.setText(task.task)
        editTextDesc.setText(task.desc)
        editTextFinishBy.setText(task.finishBy)
        checkBoxFinished.isChecked = task.isFinished
    }


    private fun deleteTask(task: Task) {

        class DeleteTask:AsyncTask<Void,Void,Void>(){
            override fun doInBackground(vararg params: Void?): Void? {
                DatabaseClient.getInstance(applicationContext)
                    .getAppDatabase().taskDao().delete(task)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                Toast.makeText(applicationContext, "Deleted", Toast.LENGTH_LONG).show()
                finish()
                startActivity(Intent(this@UpdateTaskActivity, MainActivity::class.java))
            }
        }

        DeleteTask().execute()

    }

}

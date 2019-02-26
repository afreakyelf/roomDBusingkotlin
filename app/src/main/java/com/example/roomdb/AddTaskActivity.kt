package com.example.roomdb

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_task.*

class AddTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        button_save.setOnClickListener {
            saveTask()
        }

    }

    private fun saveTask() {
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


        class SaveTask : AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg params: Void?): Void? {

                val task = Task()
                task.task = sTask
                task.desc = sDesc
                task.finishBy = sFinishBy
                task.isFinished = false

                DatabaseClient.getInstance(applicationContext).getAppDatabase()
                    .taskDao().insert(task)

                return null
            }


            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                finish()
                startActivity(Intent(applicationContext,MainActivity::class.java))
                Toast.makeText(applicationContext, "Saved", Toast.LENGTH_LONG).show()
            }
        }

        val st = SaveTask()
        st.execute()
    }
}

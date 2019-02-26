package com.example.roomdb

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview_tasks.layoutManager = LinearLayoutManager(this)

        floating_button_add.setOnClickListener {
            val intent = Intent(this@MainActivity, AddTaskActivity::class.java)
            startActivity(intent)
        }

        getTask()

    }

    private fun getTask() {

        class GetTask : AsyncTask<Void, Void, List<Task>>() {

            override fun doInBackground(vararg params: Void?): List<Task> {

                return DatabaseClient
                    .getInstance(applicationContext)
                    .getAppDatabase()
                    .taskDao()
                    .all
            }

            override fun onPostExecute(result: List<Task>?) {
                super.onPostExecute(result)
                val taskAdapter = TaskAdapter(this@MainActivity, result!!)
                recyclerview_tasks.adapter = taskAdapter
            }

        }

        val gt = GetTask()
        gt.execute()

    }

}
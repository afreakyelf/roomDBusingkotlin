package com.example.roomdb.Fragment

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.roomdb.Database.DatabaseClient
import com.example.roomdb.R
import com.example.roomdb.Tables.Task
import com.example.roomdb.Adapter.TaskAdapter
import kotlinx.android.synthetic.main.activity_after_login.*
import com.example.roomdb.Adapter.TaskAdapter.FragmentCommunication


class AfterLoginFragment : Fragment() {

    private var mDatabaseName : String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_after_login,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = "Welcome to RoomDB"


        recyclerview_tasks.layoutManager = LinearLayoutManager(context)

        mDatabaseName =arguments?.getString("dbname")

        floating_button_add.setOnClickListener {

            val fragment = AddTaskFragment()
            val fm  = activity?.supportFragmentManager
            val ft = fm?.beginTransaction()
            val bundle  = Bundle()
            bundle.putString("dbName",mDatabaseName)
            fragment.arguments = bundle
            ft?.replace(R.id.content_frame,fragment)
            ft?.addToBackStack(null)
            ft?.commit()

        }

        getTask()
    }



    private fun getTask() {

        class GetTask : AsyncTask<Void, Void, List<Task>>() {

            override fun doInBackground(vararg params: Void?): List<Task> {
                return DatabaseClient.getInstance(activity!!).getLoginDatabase()
                    .taskDao()
                    .findRepositoriesForUser(mDatabaseName!!)
            }

            override fun onPostExecute(result: List<Task>?) {
                super.onPostExecute(result)
                val taskAdapter = TaskAdapter(activity!!, result!!, communication)
                recyclerview_tasks.adapter = taskAdapter
            }

        }

        val gt = GetTask()
        gt.execute()

    }


    var communication: FragmentCommunication = object : FragmentCommunication {
        override fun respond(email: String, task: Task) {
            val fragment = UpdateTaskFragment()
            val fm  = activity?.supportFragmentManager
            val ft = fm?.beginTransaction()
            val bundle = Bundle()
            bundle.putString("email",email)
            bundle.putSerializable("task",task)
            fragment.arguments = bundle
            ft?.replace(R.id.content_frame,fragment)
            ft?.addToBackStack(null)
            ft?.commit()
        }
    }


}

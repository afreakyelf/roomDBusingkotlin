package com.example.roomdb.Fragment

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.roomdb.Database.DatabaseClient
import com.example.roomdb.R
import com.example.roomdb.Tables.Task
import kotlinx.android.synthetic.main.activity_add_task.*

class AddTaskFragment : Fragment() {
    private var mDatabaseName : String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_add_task,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = "Add Task"

        mDatabaseName = arguments?.getString("dbName")
        button_save.setOnClickListener {
            saveTask()
        }

    }

    private fun saveTask() {
        val sTask = name.text.toString()
        val sDesc = _regemail.text.toString()
        val sFinishBy = editTextFinishBy.text.toString()

        if (sTask.isEmpty()) {
            name.error = "Task required"
            name.requestFocus()
            return
        }

        if (sDesc.isEmpty()) {
            _regemail.error = "Desc required"
            _regemail.requestFocus()
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
                task.email = mDatabaseName
                task.isFinished = false

                DatabaseClient.getInstance(context!!).getLoginDatabase()
                    .taskDao().insert(task)

                return null
            }


            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)

                val fragment = AfterLoginFragment()
                val fm  = activity?.supportFragmentManager
                val ft = fm?.beginTransaction()
                val bundle  = Bundle()
                bundle.putString("dbname",mDatabaseName)
                fragment.arguments = bundle
                ft?.replace(R.id.content_frame,fragment)
                ft?.addToBackStack(null)
                ft?.commit()

                Toast.makeText(context, "Saved", Toast.LENGTH_LONG).show()
            }
        }

        val st = SaveTask()
        st.execute()
    }
}

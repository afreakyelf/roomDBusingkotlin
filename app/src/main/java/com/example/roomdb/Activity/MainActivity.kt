package com.example.roomdb.Activity

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import android.os.Handler
import com.example.roomdb.Fragment.AfterLoginFragment
import com.example.roomdb.Database.DatabaseClient
import com.example.roomdb.Fragment.ProfileFragment
import com.example.roomdb.R
import com.example.roomdb.Tables.Register


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var mDatabaseName : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbname  = intent?.extras?.getString("dbname")
        mDatabaseName = dbname

        toolbar.title = "Welcome to RoomDB"

        val fragment = AfterLoginFragment()
        val ft = supportFragmentManager.beginTransaction()
        val bundle  = Bundle()
        bundle.putString("dbname",mDatabaseName)
        fragment.arguments = bundle
        ft.replace(R.id.content_frame, fragment)
        ft.commit()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )


        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this@MainActivity)

        Handler().postDelayed(Runnable {
            setNavHeaderDetails(mDatabaseName)
        }, 500)


    }

    private fun setNavHeaderDetails(mDatabaseName: String?) {

        class LoadUser : AsyncTask<Void, Void, List<Register>>() {

            override fun doInBackground(vararg params: Void?): List<Register> {

                return DatabaseClient.getInstance(this@MainActivity)
                    .getLoginDatabase()
                    .registerDao()
                    .findRepositoriesForUser(mDatabaseName!!)
            }

            override fun onPostExecute(result: List<Register>?) {
                super.onPostExecute(result)
                if (!result.isNullOrEmpty()) {
                    nvname.text = result[0].name
                    nvemail.text = result[0].email
                }
            }
        }

        val gt = LoadUser()
        gt.execute()
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.profile -> {
                val fragment = ProfileFragment()
                val ft = supportFragmentManager.beginTransaction()
                val bundle  = Bundle()
                bundle.putString("dbname",mDatabaseName)
                fragment.arguments = bundle
                ft.replace(R.id.content_frame, fragment)
                ft.addToBackStack(null)
                ft.commit()
            }
            R.id.settings -> {

            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }




}
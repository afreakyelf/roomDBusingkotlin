package com.example.roomdb.Database

import android.arch.persistence.room.Room
import android.content.Context


class DatabaseClient private constructor(mCtx: Context) {

    private var loginDatabase : LoginDatabase? = null

    init {
        loginDatabase = Room.databaseBuilder(mCtx, LoginDatabase::class.java, "tea4d").build()
    }

    companion object {
        var mInstance: DatabaseClient? = null
        @Synchronized
        fun getInstance(mCtx: Context): DatabaseClient {
            if (mInstance == null) {
                mInstance =
                    DatabaseClient(mCtx)
            }
            return mInstance as DatabaseClient
        }
    }

    fun getLoginDatabase(): LoginDatabase {
        return loginDatabase!!
    }



}
package com.example.roomdb

import android.arch.persistence.room.Room
import android.content.Context


class DatabaseClient private constructor(mCtx: Context) {

    private var appDatabase: AppDatabase? = null

    init {
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase::class.java, "MyToDos").build()
    }

    companion object {
        var mInstance: DatabaseClient? = null
        @Synchronized
        fun getInstance(mCtx: Context): DatabaseClient {
            if (mInstance == null) {
                mInstance = DatabaseClient(mCtx)
            }
            return mInstance as DatabaseClient
        }
    }

    fun getAppDatabase(): AppDatabase {
        return appDatabase!!
    }

}
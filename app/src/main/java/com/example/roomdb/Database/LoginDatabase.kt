package com.example.roomdb.Database

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import com.example.roomdb.Dao.RegisterDao
import com.example.roomdb.Dao.TaskDao
import com.example.roomdb.Tables.Register
import com.example.roomdb.Tables.Task


@Database(entities = [Task::class, Register::class], version = 1, exportSchema = false)
abstract class LoginDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun registerDao() : RegisterDao
}
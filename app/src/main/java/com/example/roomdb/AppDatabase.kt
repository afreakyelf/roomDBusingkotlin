package com.example.roomdb

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database


@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
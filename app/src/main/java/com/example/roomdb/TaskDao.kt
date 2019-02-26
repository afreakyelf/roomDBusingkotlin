package com.example.roomdb

import android.arch.persistence.room.*


@Dao
interface TaskDao {

    @get:Query("SELECT * FROM task")
    val all: List<Task>

    @Insert
    fun insert(task: Task)

    @Delete
    fun delete(task: Task)

    @Update
    fun update(task: Task)

}
package com.example.roomdb.Dao

import android.arch.persistence.room.*
import com.example.roomdb.Tables.Task


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

    @Query("SELECT * FROM task WHERE email=:email")
    fun findRepositoriesForUser(email: String): List<Task>

}
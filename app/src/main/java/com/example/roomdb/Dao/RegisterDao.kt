package com.example.roomdb.Dao

import android.arch.persistence.room.*
import com.example.roomdb.Tables.Register


@Dao
interface RegisterDao {

    @get:Query("SELECT * FROM register")
    val all: List<Register>

    @Insert
    fun insert(register : Register)

    @Query("SELECT * FROM register WHERE email=:email")
    fun findRepositoriesForUser(email: String): List<Register>

    @Update
    fun update(register: Register)

}
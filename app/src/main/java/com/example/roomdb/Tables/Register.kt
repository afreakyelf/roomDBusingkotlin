package com.example.roomdb.Tables

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Query
import android.support.annotation.NonNull
import java.io.Serializable

@Entity(primaryKeys = ["email"])
class Register : Serializable {

    @NonNull
    @ColumnInfo(name = "email",index = true)
    var email: String? = null

    @ColumnInfo(name = "password")
    var password: String? = null

    @NonNull
    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "contact")
    var contact: String? = null

    @ColumnInfo(name = "gender")
    var gender: String? = null

    @ColumnInfo(name = "interests")
    var interests: String? = null

}
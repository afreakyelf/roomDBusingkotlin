package com.example.roomdb.Tables

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import com.example.roomdb.Tables.Register
import java.io.Serializable


@Entity(foreignKeys = [ForeignKey(
    entity = Register::class, parentColumns = arrayOf("email")
    , childColumns = arrayOf("email"), onDelete = CASCADE)])

class Task : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "task")
    var task: String? = null

    @ColumnInfo(name = "desc")
    var desc: String? = null

    @ColumnInfo(name = "email",index = true)
    var email: String? = null

    @ColumnInfo(name = "finish_by")
    var finishBy: String? = null

    @ColumnInfo(name = "finished")
    var isFinished: Boolean = false

}
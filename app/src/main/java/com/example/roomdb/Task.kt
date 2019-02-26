package com.example.roomdb

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity
class Task : Serializable {


    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "task")
    var task: String? = null

    @ColumnInfo(name = "desc")
    var desc: String? = null

    @ColumnInfo(name = "finish_by")
    var finishBy: String? = null

    @ColumnInfo(name = "finished")
    var isFinished: Boolean = false



}
package com.sesang06.foodtimer.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TimerEntity(
    val id: Int,
    val title: String,
    val description: String,
    val minutes: Int,
    val seconds: Int
)

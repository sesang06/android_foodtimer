package com.sesang06.foodtimer.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Timer(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "minutes") val minutes: Int,
    @ColumnInfo(name = "seconds") val seconds: Int,
    @ColumnInfo(name = "thumbnail") val thumbnail: Int
)


fun Timer.mapToDomain() = TimerEntity(
    uid.toInt(),
    title ?: "",
    description ?: "",
    minutes,
    seconds,
        TimerImage.valueOf(thumbnail) ?: TimerImage.DISH
)
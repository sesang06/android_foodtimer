package com.sesang06.foodtimer.database


data class TimerEntity(
    val id: Int,
    val title: String,
    val description: String,
    val minutes: Int,
    val seconds: Int
)

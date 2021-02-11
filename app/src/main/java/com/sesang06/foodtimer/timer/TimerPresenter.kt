package com.sesang06.foodtimer.timer

data class TimerPresenter(
        val title: String,
        val description: String,
        val minutes: Int,
        val seconds: Int
)

data class RunningTimerPresenter(
        val minutes: Int,
        val seconds: Int
)
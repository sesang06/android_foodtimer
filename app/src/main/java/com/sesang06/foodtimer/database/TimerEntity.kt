package com.sesang06.foodtimer.database

enum class TimerImage(val value: Int) {
    POT(0), BOWL(1), DISH(2), TOAST(3);

    companion object {
        fun valueOf(value: Int) = TimerImage.values().find { it.value == value }
    }
}

data class TimerEntity(
    val id: Int,
    val title: String,
    val description: String,
    val minutes: Int,
    val seconds: Int,
    val thumbnail: TimerImage
)

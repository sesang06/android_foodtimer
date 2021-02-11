package com.sesang06.foodtimer.timer

import com.sesang06.foodtimer.database.TimerDataSource
import com.sesang06.foodtimer.database.TimerEntity

class TimerUseCase(
        private val id: Int,
        private val timerDataSource: TimerDataSource
) {


    fun fetch(): TimerPresenter {

        val timer = timerDataSource.getTimer(id) ?: return TimerPresenter("", "" , 0, 0)

        return TimerPresenter(timer.title, timer.description, timer.minutes, timer.seconds)
    }

    fun editTime(minutes: Int, seconds : Int) : TimerPresenter {

        val timer = timerDataSource.getTimer(id) ?: return TimerPresenter("", "" , 0, 0)
        timerDataSource.updateTimer(TimerEntity(
                timer.id,
                timer.title,
                timer.description,
                minutes,
                seconds
        ))
        return TimerPresenter(timer.title, timer.description, minutes, seconds)
    }

}
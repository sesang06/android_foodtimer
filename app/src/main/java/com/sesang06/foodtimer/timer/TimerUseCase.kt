package com.sesang06.foodtimer.timer

import com.sesang06.foodtimer.R
import com.sesang06.foodtimer.database.TimerDataSource
import com.sesang06.foodtimer.database.TimerEntity
import com.sesang06.foodtimer.database.TimerImage

class TimerUseCase(
        private val id: Int,
        private val timerDataSource: TimerDataSource
) {


    fun fetch(): TimerPresenter {

        val timer = timerDataSource.getTimer(id) ?: return TimerPresenter("", "" , 0, 0, R.drawable.timer_bowl)

        val thumbnail: Int = when (timer.thumbnail) {
            TimerImage.BOWL -> R.drawable.timer_bowl
            TimerImage.DISH -> R.drawable.timer_dish
            TimerImage.POT -> R.drawable.timer_pot
            TimerImage.TOAST -> R.drawable.timer_toast
        }
        return TimerPresenter(timer.title, timer.description, timer.minutes, timer.seconds, thumbnail)
    }

    fun editTime(minutes: Int, seconds : Int) : TimerPresenter {

        val timer = timerDataSource.getTimer(id) ?: return TimerPresenter("", "" , 0, 0, R.drawable.timer_bowl)
        timerDataSource.updateTimer(TimerEntity(
                timer.id,
                timer.title,
                timer.description,
                minutes,
                seconds,
                timer.thumbnail
        ))
        val thumbnail: Int = when (timer.thumbnail) {
            TimerImage.BOWL -> R.drawable.timer_bowl
            TimerImage.DISH -> R.drawable.timer_dish
            TimerImage.POT -> R.drawable.timer_pot
            TimerImage.TOAST -> R.drawable.timer_toast
        }
        return TimerPresenter(timer.title, timer.description, minutes, seconds, thumbnail)
    }

}
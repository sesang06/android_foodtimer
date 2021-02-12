package com.sesang06.foodtimer.main

import com.sesang06.foodtimer.R
import com.sesang06.foodtimer.database.AppInstalledRepository
import com.sesang06.foodtimer.database.TimerDataSource
import com.sesang06.foodtimer.database.TimerEntity
import com.sesang06.foodtimer.database.TimerImage

class MainTimerUseCase(
        val appInstalledRepository: AppInstalledRepository,
        val timerDataSource: TimerDataSource
) {


    fun fetchData(): List<MainItem> {
        return timerDataSource.getAllTimer()
                .map {
                    val text: String
                    if (it.seconds == 0) {
                        text = "${it.minutes}분"
                    } else if (it.minutes == 0) {
                        text = "${it.seconds}초"
                    } else {
                        text = "${it.minutes}분 ${it.seconds}초"
                    }
                    val thumbnail: Int = when (it.thumbnail) {
                        TimerImage.BOWL -> R.drawable.main_bowl
                        TimerImage.DISH -> R.drawable.main_dish
                        TimerImage.POT -> R.drawable.main_pot
                        TimerImage.TOAST -> R.drawable.main_toast
                    }
                    MainItem(it.title, text, it.id, thumbnail)
                }
    }

    fun createDefaultData() {
        listOf(
                TimerEntity(
                        0,
                        "맛있는 계란 반숙",
                        "8분만에 즐기는 탁월한 선택",
                        8,
                        0,
                        TimerImage.POT
                ),
                TimerEntity(
                        0,
                        "꼬들꼬들 컵라면",
                        "3분만에 즐기는 간편한 야식",
                        3,
                        0,
                        TimerImage.BOWL
                ),
                TimerEntity(
                        0,
                        "파스타 알단테",
                        "알리오 올리오에 알맞은 적당한 알단테",
                        12,
                        20,
                        TimerImage.DISH
                ),
                TimerEntity(
                        0,
                        "바삭바삭 토스트",
                        "아침에 해먹는 간식",
                        3,
                        20,
                        TimerImage.TOAST
                )

        ).forEach {
            timerDataSource.insertTimer(it)
        }
    }

    fun loadDefaultDataIfNeeded() {
        if (appInstalledRepository.shouldInitData) {
            createDefaultData()
            appInstalledRepository.finishUpdate()
        }
    }

}
package com.sesang06.foodtimer.main

import com.sesang06.foodtimer.database.AppInstalledRepository
import com.sesang06.foodtimer.database.TimerDataSource
import com.sesang06.foodtimer.database.TimerEntity

class MainTimerUseCase(
        val appInstalledRepository: AppInstalledRepository,
        val timerDataSource: TimerDataSource
) {


    fun fetchData(): List<MainItem> {
        return timerDataSource.getAllTimer()
            .map {
                MainItem(it.title, it.minutes, it.seconds)
            }
    }

    fun createDefaultData() {
        listOf(
                TimerEntity(
                        0,
                        "계란 반숙",
                        "8분만에 즐기는 탁월한 선택",
                        8,
                        0
                ),
                TimerEntity(
                        0,
                        "컵라면",
                        "3분안에 즐기는 간편한 야식",
                        3,
                        0
                ),
                TimerEntity(
                        0,
                        "파스타",
                        "알리오 올리오에 알맞은 적당한 알단테",
                        8,
                        0
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
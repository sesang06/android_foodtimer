package com.sesang06.foodtimer.database

import android.content.Context

class TimerDataSource(context: Context) {

    private val db = provideDb(context)

    fun insertTimer(entity: TimerEntity) : TimerEntity {
        val id = db.timerDao().insert(
            Timer(
            0,
            entity.title,
            entity.description,
            entity.minutes,
            entity.seconds
        )
        )
        return TimerEntity(
            id.toInt(),
            entity.title,
            entity.description,
            entity.minutes,
            entity.seconds
        )
    }


    fun getTimer(id: Int): TimerEntity? {
        return db.timerDao().loadById(id.toLong())?.mapToDomain()
    }


    fun getAllTimer() : List<TimerEntity> {
       return db.timerDao().getAll().map { it.mapToDomain() }
    }


    fun deleteAll() {
        db.timerDao().nuke()
    }

    fun deleteTimer(id: Int) {
        db.timerDao().loadById(id.toLong())?.let {
            db.timerDao().delete(it)
        }
    }


    fun updateTimer(entity: TimerEntity) {

        db.timerDao().update(
            Timer(
                entity.id.toLong(),
                entity.title,
                entity.description,
                entity.minutes,
                entity.seconds
            )
        )
    }
}
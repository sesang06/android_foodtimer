package com.sesang06.foodtimer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Timer::class), version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun timerDao(): TimerDao
}


const val databaseName = "timer_database"


fun provideDb(context: Context) = Room.databaseBuilder(context, AppDatabase::class.java, databaseName)
    .fallbackToDestructiveMigration()
    .build()

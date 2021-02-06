package com.sesang06.foodtimer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Database(entities = arrayOf(Timer::class), version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun timerDao(): TimerDao
}


const val databaseName = "timer_database"


fun provideDb(context: Context) = Room.databaseBuilder(context, AppDatabase::class.java, databaseName)
    .fallbackToDestructiveMigration()
    .build()

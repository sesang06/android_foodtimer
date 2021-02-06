package com.sesang06.foodtimer.database

import androidx.room.*

@Dao
interface TimerDao {
    @Query("SELECT * FROM timer")
    fun getAll(): List<Timer>

    @Query("SELECT * FROM timer WHERE uid IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<Timer>


    @Query("SELECT * FROM timer WHERE uid = (:id) LIMIT 1")
    fun loadById(id: Long) : Timer

    @Insert
    fun insertAll(vararg timer: Timer): List<Long>

    @Insert
    fun insert(timer: Timer): Long

    @Update
    fun update(timer: Timer)

    @Delete
    fun delete(timer: Timer)
}

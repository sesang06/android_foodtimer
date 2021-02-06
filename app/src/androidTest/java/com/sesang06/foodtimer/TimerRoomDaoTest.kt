package com.sesang06.foodtimer

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.sesang06.foodtimer.database.Timer
import com.sesang06.foodtimer.database.provideDb
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TimerRoomDaoTest {

    val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    private val db = provideDb(appContext).apply {
        clearAllTables()
    }





    companion object {
        const val title = "egg"
        const val description = "egg is delicious"
        const val minutes = 10
        const val seconds = 40
    }

    fun generateTimer(): Long {
        val timer = Timer(0, title, description, minutes, seconds);
        return db.timerDao().insert(timer)
    }

    @Test
    fun createTimerTest() {


        val id = generateTimer()
        val datas = db.timerDao().getAll()

        Log.d(datas.toString(), "createTimerTest")
        var hasValue = false
        for (data in datas) {
            if (data.uid == id) {
                hasValue = true
                assert(data.title == "egg")
                assert(data.description == "egg is delicious")
                assert(data.seconds == 40)
                assert(data.minutes == 10)
            }
        }
        assert(hasValue)
        assert(datas.size > 0)
    }


    @Test
    fun loadAllTimerTest() {


        val idLists = mutableListOf<Long>()
        for (i in 1..10) {
            val id = generateTimer()
            idLists.add(id)
        }
        val datas = db.timerDao().getAll()

        assert(datas.size == idLists.size)
    }

    @Test
    fun querySpecificTest() {
        val id = generateTimer()

        val data = db.timerDao().loadById(id)

        assert(data != null)
        data?.let {

            assert(it.uid == id)
            assert(it.title == title)
            assert(it.minutes == minutes)
            assert(it.seconds == seconds)
        }

    }

    @Test
    fun deleteTimerTest() {
        val idLists = mutableListOf<Long>()
        for (i in 1..10) {
            val id = generateTimer()
            idLists.add(id)
        }
        val datas = db.timerDao().getAll()

        assert(datas.size == idLists.size)

        for (data in datas) {
            db.timerDao().delete(data)
        }
        val refreshed = db.timerDao().getAll()
        assert(refreshed.size == 0)

    }

    @Test
    fun modifyTimerTest() {
        val id = generateTimer()

        val data = db.timerDao().loadById(id)

        assert(data != null)
        if (data == null) return

        assert(data.uid == id)
        assert(data.title == title)
        assert(data.minutes == minutes)
        assert(data.seconds == seconds)


        val newTimer = Timer(data.uid, data.title, data.description, data.minutes, 0)

        db.timerDao().update(newTimer)


        val refeshed = db.timerDao().loadById(id)

        assert(refeshed != null)
        if (refeshed == null) return
        assert(refeshed.uid == id)
        assert(refeshed.title == title)
        assert(refeshed.description == description)
        assert(refeshed.minutes == minutes)
        assert(refeshed.seconds == 0)


    }


    @Test
    fun nukeTest() {

        for (i in 0..10) {
            val id = generateTimer()
        }
        val datas = db.timerDao().getAll()

        assert(datas.size > 0)

        db.timerDao().nuke()
        db.timerDao().nuke()

        val results = db.timerDao().getAll()
        assert(results.size == 0)

    }

}
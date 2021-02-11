package com.sesang06.foodtimer

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.sesang06.foodtimer.database.TimerDataSource
import com.sesang06.foodtimer.database.TimerEntity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TimerDataSourceTest {


    private companion object {
        const val title = "egg"
        const val description = "egg is delicious"
        const val minutes = 10
        const val seconds = 40
    }

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    private val dataSource = TimerDataSource(appContext)

    @Test
    fun insertTimerEntityTest() {
        val timer = TimerEntity(0, title, description, minutes, seconds)
        val result = dataSource.insertTimer(timer)
        assert(result.id != 0)
    }

    @Test
    fun selectTimerEntityTest() {
        val timer = TimerEntity(0, title, description, minutes, seconds)
        val result = dataSource.insertTimer(timer)

        val queried = dataSource.getTimer(result.id)

        assert(queried != null)

        queried?.let {

            assert(it.id == result.id)
            assert(it.title == result.title)
            assert(it.description == result.description)
            assert(it.minutes == result.minutes)
            assert(it.seconds == result.seconds)
        }
    }

    @Test
    fun getAllEntityTest() {

        for (i in 0..10) {
            val timer = TimerEntity(0, title, description, minutes, seconds)
            dataSource.insertTimer(timer)
        }
        val result = dataSource.getAllTimer()
        assert(result.size > 0)
    }

    @Test
    fun deleteAllTest() {
        for (i in 0..10) {
            val timer = TimerEntity(0, title, description, minutes, seconds)
            dataSource.insertTimer(timer)
        }
        val result = dataSource.getAllTimer()
        assert(result.size > 0)

        dataSource.deleteAll()
        val refreshed = dataSource.getAllTimer()
        assert(refreshed.isEmpty())
    }

    @Test
    fun deleteTimerEntityTests() {
        val timer = TimerEntity(0, title, description, minutes, seconds)
        val result = dataSource.insertTimer(timer)
        assert(result.id != 0)

        dataSource.deleteTimer(result.id)
        val deleted = dataSource.getTimer(result.id)
        assert(deleted == null)
    }

    @Test
    fun updateTimerEntityTests() {
        val timer = TimerEntity(0, title, description, minutes, seconds)
        val result = dataSource.insertTimer(timer)

        assert(result.id != 0)

        dataSource.updateTimer(TimerEntity(result.id, title, description, 0, seconds))

        val updated = dataSource.getTimer(result.id)
        assert(updated != null)
        updated?.let {
            assert(updated.minutes == 0)
            assert(updated.title == title)
        }
    }


}
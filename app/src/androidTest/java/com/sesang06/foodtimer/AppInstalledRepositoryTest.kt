package com.sesang06.foodtimer

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.sesang06.foodtimer.database.AppInstalledRepository
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppInstalledRepositoryTest {

    val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    val appInstallRepository = AppInstalledRepository(appContext)

    @Test
    fun installTest() {
        appInstallRepository.clearToInit()
        assert(appInstallRepository.shouldInitData)
        appInstallRepository.clearToInit()
    }

    @Test
    fun notFirstTest() {
        appInstallRepository.clearToInit()
        assert(appInstallRepository.shouldInitData)
        appInstallRepository.finishUpdate()
        assert(!appInstallRepository.shouldInitData)
        appInstallRepository.clearToInit()
    }



}
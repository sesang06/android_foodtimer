package com.sesang06.foodtimer.database

import android.content.Context
import androidx.preference.PreferenceManager

class AppInstalledRepository(val context: Context) {

    companion object {
        private const val APP_INSTALL = "TUTORIAL"
    }

    fun finishUpdate() {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
            .putBoolean(APP_INSTALL, true)
            .apply()
    }

    fun clearToInit() {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
            .remove(APP_INSTALL)
            .apply()
    }

    val shouldInitData: Boolean
        get() {
            return !(PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(APP_INSTALL, false))
        }

}
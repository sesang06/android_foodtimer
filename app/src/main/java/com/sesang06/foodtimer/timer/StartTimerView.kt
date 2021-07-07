package com.sesang06.foodtimer.timer

import android.content.Context
import android.util.AttributeSet
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.sesang06.foodtimer.R

open class StartTimerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    private val currentTimeTextView: TextView by lazy {
        findViewById<TextView>(R.id.tv_current_time_running_timer)
    }
    private val runningProgressBar: ProgressBar by lazy {
        findViewById<ProgressBar>(R.id.pb_running_timer)

    }
    private val currentStatusTextView: TextView by lazy {
        findViewById<TextView>(R.id.tv_current_status_running_timer)
    }

    init {
        inflate(context, R.layout.view_running_timer, this)
    }


    fun bind(minutes: Int, seconds: Int) {
        val secondDigits = seconds.toString().padStart(2, '0')
        val texts = "${minutes}:${secondDigits}"
        currentTimeTextView.text = texts
    }

    fun bind(progress: Int) {
        runningProgressBar.progress = progress
        currentStatusTextView.text = "${progress}%"
    }
}

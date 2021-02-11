package com.sesang06.foodtimer.timer

import android.content.Context
import android.util.AttributeSet
import android.widget.NumberPicker
import androidx.constraintlayout.widget.ConstraintLayout
import com.sesang06.foodtimer.R

class BeforeStartTimerView @JvmOverloads constructor(context: Context, attrs: AttributeSet?=null, defStyleAttr: Int=0)
    : ConstraintLayout(context, attrs, defStyleAttr){


    private val minutesPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.np_minutes).apply {
            this.minValue = 0
            this.maxValue = 59
        }
    }


    private val secondsPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.np_seconds).apply {
            this.minValue = 0
            this.maxValue = 59
        }
    }


    private var listener: ItemChangeListener? = null



    init{
        inflate(context, R.layout.view_beforestart_timer, this)
        minutesPicker.setOnValueChangedListener { numberPicker, oldValue, newValue ->
            val seconds = secondsPicker.value
            listener?.onTimeChanged(newValue, seconds)
        }
        secondsPicker.setOnValueChangedListener { numberPicker, oldValue, newValue ->
            val minutes = minutesPicker.value
            listener?.onTimeChanged(minutes, newValue)
        }
    }


    fun bind(minutes: Int, seconds: Int) {
        minutesPicker.value = minutes
        secondsPicker.value = seconds
    }


    fun setItemChangeListener(listener: ItemChangeListener) {
        this.listener = listener
    }

    interface ItemChangeListener {

        fun onTimeChanged(minutes: Int, seconds: Int)
    }
}

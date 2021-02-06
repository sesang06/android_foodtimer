package com.sesang06.foodtimer.timer

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.sesang06.foodtimer.R

open class StartTimerView @JvmOverloads constructor(context: Context, attrs: AttributeSet?=null, defStyleAttr: Int=0)
    : ConstraintLayout(context, attrs, defStyleAttr){


    init{
        inflate(context, R.layout.view_running_timer, this)
    }

}

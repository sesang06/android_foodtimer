package com.sesang06.foodtimer.timer

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sesang06.foodtimer.R
import com.sesang06.foodtimer.main.MainItem
import com.sesang06.foodtimer.main.MainItemAdapter


class TimerActivity : AppCompatActivity() {

    private val titleTextView: TextView by lazy {
        findViewById<TextView>(R.id.tv_title_timer)
    }

    private val descriptionTextView: TextView by lazy {
        findViewById<TextView>(R.id.tv_description_timer)
    }

    private val startButton: Button by lazy {
        findViewById<Button>(R.id.b_start_timer)
    }

    private val timePicker: TimePicker by lazy {
        findViewById<TimePicker>(R.id.tp_timer).apply {
            this.setIs24HourView(false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
    }


}
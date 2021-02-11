package com.sesang06.foodtimer.timer

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sesang06.foodtimer.R
import com.sesang06.foodtimer.database.AppInstalledRepository
import com.sesang06.foodtimer.database.TimerDataSource
import com.sesang06.foodtimer.main.*


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


    private val beforeStartTimerView: BeforeStartTimerView by lazy {
        findViewById<BeforeStartTimerView>(R.id.before_start_timer)
    }

    private val startTimerView: StartTimerView by lazy {
        findViewById<StartTimerView>(R.id.start_timer)
    }

    companion object {
        const val ID = "ID"
    }

    private lateinit var timerUseCase: TimerUseCase

    private lateinit var viewModel: TimerViewModel

    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        id = intent.extras?.getInt(ID) ?: 0


        val timerDataSource = TimerDataSource(context = this)

            timerUseCase = TimerUseCase(id, timerDataSource)
        val viewModelFactory = TimerViewModelFactory(
            id, timerUseCase
        )
        viewModel = ViewModelProviders.of(
            this, viewModelFactory)[TimerViewModel::class.java]


    }


    private fun bind() {

    }

}
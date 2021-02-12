package com.sesang06.foodtimer.timer

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
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

    private val thumbnailImageView: ImageView by lazy {
        findViewById<ImageView>(R.id.iv_thumbnail_timer)
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

    private var didInit = false

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

        bind()

    }


    private fun bind() {

        viewModel.state.observe(this, Observer { state ->
            when (state) {
                TimerViewModel.State.beforeStart -> {
                    beforeStartTimerView.visibility = View.VISIBLE
                    startTimerView.visibility = View.GONE
                    startButton.setBackgroundResource(R.drawable.bg_main_radius_30dp)
                    startButton.text = "시작"
                    startButton.setOnClickListener {
                        viewModel.startTimer()
                    }
                }
                TimerViewModel.State.running -> {
                    beforeStartTimerView.visibility = View.GONE
                    startTimerView.visibility = View.VISIBLE
                    startButton.setBackgroundResource(R.drawable.bg_alert_radius_30dp)
                    startButton.text = "정지"
                    startButton.setOnClickListener {
                        viewModel.stopTimer()
                    }
                }
                TimerViewModel.State.stop -> {
                    beforeStartTimerView.visibility = View.GONE
                    startTimerView.visibility = View.VISIBLE
                    startButton.setBackgroundResource(R.drawable.bg_main_radius_30dp)
                    startButton.text = "재시작"
                    startButton.setOnClickListener {
                        viewModel.restartTimer()
                    }
                }
                TimerViewModel.State.done -> {
                    beforeStartTimerView.visibility = View.GONE
                    startTimerView.visibility = View.VISIBLE
                    startButton.setBackgroundResource(R.drawable.bg_main_radius_30dp)
                    startButton.text = "재시작"
                    startButton.setOnClickListener {
                        viewModel.startTimer()
                    }
                }
            }

        })

        viewModel.timer.observe(this, Observer { timer ->
            titleTextView.text = timer.title
            descriptionTextView.text = timer.description
            Glide
                    .with(this)
                    .load(timer.thumbnail)
                    .centerCrop()
                    .transform(RoundedCorners(50))
                    .into(thumbnailImageView)
                    .clearOnDetach()
            if (!didInit) {
                didInit = true
                beforeStartTimerView.bind(timer.minutes, timer.seconds)
            }

        })
        beforeStartTimerView.setItemChangeListener( object: BeforeStartTimerView.ItemChangeListener {
            override fun onTimeChanged(minutes: Int, seconds: Int) {
                viewModel.editTimer(minutes, seconds)
            }
        })

        viewModel.runningTimer.observe(this, Observer { runningTimer ->
            startTimerView.bind(runningTimer.minutes, runningTimer.seconds)
        })

        viewModel.progress.observe(this, Observer {
            startTimerView.bind(it)
        })

        viewModel.onCreate()


    }

}
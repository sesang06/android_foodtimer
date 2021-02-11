package com.sesang06.foodtimer.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sesang06.foodtimer.R
import com.sesang06.foodtimer.database.AppInstalledRepository
import com.sesang06.foodtimer.database.TimerDataSource
import com.sesang06.foodtimer.timer.TimerActivity

class MainActivity : AppCompatActivity(), MainItemAdapter.ItemClickListener {

    private lateinit var mainItemRecylerView: RecyclerView

    private lateinit var mainItemAdapter: MainItemAdapter

    private lateinit var mainTimerUseCase: MainTimerUseCase

    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appInstalledRepository = AppInstalledRepository(context = this)
        val timerDataSource = TimerDataSource(context = this)
        mainTimerUseCase = MainTimerUseCase(appInstalledRepository, timerDataSource)

        val viewModelFactory = MainViewModelFactory(
                mainTimerUseCase
        )
        viewModel = ViewModelProviders.of(
            this, viewModelFactory)[MainViewModel::class.java]


        viewModel.onCreate()
        mainItemAdapter = MainItemAdapter(
            this, listOf()
        ).apply {
            setItemClickListener(this@MainActivity)
        }

        viewModel.mainItems.observe(this, Observer { items ->
            mainItemAdapter.setItems(items)
            mainItemAdapter.notifyDataSetChanged()
        })

        mainItemRecylerView = findViewById(R.id.rv_main)
        mainItemRecylerView.adapter = mainItemAdapter
        mainItemRecylerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mainItemAdapter.notifyDataSetChanged()
    }


    override fun onItemClick(item: MainItem) {
        val intent = Intent(this, TimerActivity::class.java)
        startActivity(intent)
    }
}